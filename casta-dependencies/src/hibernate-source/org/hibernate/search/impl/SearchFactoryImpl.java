/*
 * Copyright (C) 2007-2010 Castafiore
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */
 //$Id: SearchFactoryImpl.java 15623 2008-11-26 22:25:15Z epbernard $
package org.hibernate.search.impl;

import java.beans.Introspector;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.lucene.analysis.Analyzer;
import org.slf4j.Logger;

import org.hibernate.annotations.common.reflection.ReflectionManager;
import org.hibernate.annotations.common.reflection.XClass;
import org.hibernate.annotations.common.reflection.java.JavaReflectionManager;
import org.hibernate.annotations.common.util.StringHelper;
import org.hibernate.search.Environment;
import org.hibernate.search.SearchException;
import org.hibernate.search.Version;
import org.hibernate.search.annotations.Factory;
import org.hibernate.search.annotations.FullTextFilterDef;
import org.hibernate.search.annotations.FullTextFilterDefs;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Key;
import org.hibernate.search.backend.BackendQueueProcessorFactory;
import org.hibernate.search.backend.LuceneIndexingParameters;
import org.hibernate.search.backend.LuceneWork;
import org.hibernate.search.backend.OptimizeLuceneWork;
import org.hibernate.search.backend.Worker;
import org.hibernate.search.backend.WorkerFactory;
import org.hibernate.search.backend.configuration.ConfigurationParseHelper;
import org.hibernate.search.cfg.SearchConfiguration;
import org.hibernate.search.engine.DocumentBuilderIndexedEntity;
import org.hibernate.search.engine.FilterDef;
import org.hibernate.search.engine.SearchFactoryImplementor;
import org.hibernate.search.engine.EntityState;
import org.hibernate.search.engine.DocumentBuilderContainedEntity;
import org.hibernate.search.filter.CachingWrapperFilter;
import org.hibernate.search.filter.FilterCachingStrategy;
import org.hibernate.search.filter.MRUFilterCachingStrategy;
import org.hibernate.search.reader.ReaderProvider;
import org.hibernate.search.reader.ReaderProviderFactory;
import org.hibernate.search.store.DirectoryProvider;
import org.hibernate.search.store.DirectoryProviderFactory;
import org.hibernate.search.store.optimization.OptimizerStrategy;
import org.hibernate.search.util.LoggerFactory;

/**
 * @author Emmanuel Bernard
 */
public class SearchFactoryImpl implements SearchFactoryImplementor {

	static {
		Version.touch();
	}

	private static final Logger log = LoggerFactory.make();

	private final Map<Class<?>, DocumentBuilderIndexedEntity<?>> documentBuildersIndexedEntities = new HashMap<Class<?>, DocumentBuilderIndexedEntity<?>>();
	private final Map<Class<?>, DocumentBuilderContainedEntity<?>> documentBuildersContainedEntities = new HashMap<Class<?>, DocumentBuilderContainedEntity<?>>();
	//keep track of the index modifiers per DirectoryProvider since multiple entity can use the same directory provider
	private final Map<DirectoryProvider<?>, DirectoryProviderData> dirProviderData = new HashMap<DirectoryProvider<?>, DirectoryProviderData>();
	private final Worker worker;
	private final ReaderProvider readerProvider;
	private BackendQueueProcessorFactory backendQueueProcessorFactory;
	private final Map<String, FilterDef> filterDefinitions = new HashMap<String, FilterDef>();
	private final FilterCachingStrategy filterCachingStrategy;
	private Map<String, Analyzer> analyzers;
	private final AtomicBoolean stopped = new AtomicBoolean( false );
	private final int cacheBitResultsSize;

	private final PolymorphicIndexHierarchy indexHierarchy = new PolymorphicIndexHierarchy();

	/*
	 * Used as a barrier (piggyback usage) between initialization and subsequent usage of searchFactory in different threads
	 * this is due to our use of the initialize pattern is a few areas
	 * subsequent reads on volatiles should be very cheap on most platform especially since we don't write after init
	 *
	 * This volatile is meant to be written after initialization
	 * and read by all subsequent methods accessing the SearchFactory state
	 * read to be as barrier != 0. If barrier == 0 we have a race condition, but is not likely to happen.
	 */
	private volatile short barrier;

	/**
	 * Each directory provider (index) can have its own performance settings.
	 */
	private Map<DirectoryProvider, LuceneIndexingParameters> dirProviderIndexingParams =
			new HashMap<DirectoryProvider, LuceneIndexingParameters>();
	private final String indexingStrategy;


	public BackendQueueProcessorFactory getBackendQueueProcessorFactory() {
		if ( barrier != 0 ) {
		} //read barrier
		return backendQueueProcessorFactory;
	}

	public void setBackendQueueProcessorFactory(BackendQueueProcessorFactory backendQueueProcessorFactory) {
		//no need to set a barrier, we init in the same thread as the init one
		this.backendQueueProcessorFactory = backendQueueProcessorFactory;
	}

	public SearchFactoryImpl(SearchConfiguration cfg) {
		ReflectionManager reflectionManager = cfg.getReflectionManager();
		if ( reflectionManager == null ) {
			reflectionManager = new JavaReflectionManager();
		}
		this.indexingStrategy = defineIndexingStrategy( cfg ); //need to be done before the document builds
		initDocumentBuilders( cfg, reflectionManager );

		Set<Class<?>> indexedClasses = documentBuildersIndexedEntities.keySet();
		for ( DocumentBuilderIndexedEntity builder : documentBuildersIndexedEntities.values() ) {
			builder.postInitialize( indexedClasses );
		}
		//not really necessary today
		for ( DocumentBuilderContainedEntity builder : documentBuildersContainedEntities.values() ) {
			builder.postInitialize( indexedClasses );
		}
		this.worker = WorkerFactory.createWorker( cfg, this );
		this.readerProvider = ReaderProviderFactory.createReaderProvider( cfg, this );
		this.filterCachingStrategy = buildFilterCachingStrategy( cfg.getProperties() );
		this.cacheBitResultsSize = ConfigurationParseHelper.getIntValue(
				cfg.getProperties(), Environment.CACHE_DOCIDRESULTS_SIZE, CachingWrapperFilter.DEFAULT_SIZE
		);
		this.barrier = 1; //write barrier
	}

	private static String defineIndexingStrategy(SearchConfiguration cfg) {
		String indexingStrategy = cfg.getProperties().getProperty( Environment.INDEXING_STRATEGY, "event" );
		if ( !( "event".equals( indexingStrategy ) || "manual".equals( indexingStrategy ) ) ) {
			throw new SearchException( Environment.INDEXING_STRATEGY + " unknown: " + indexingStrategy );
		}
		return indexingStrategy;
	}

	public String getIndexingStrategy() {
		if ( barrier != 0 ) {
		} //read barrier
		return indexingStrategy;
	}

	public void close() {
		if ( barrier != 0 ) {
		} //read barrier
		if ( stopped.compareAndSet( false, true ) ) {
			try {
				worker.close();
			}
			catch ( Exception e ) {
				log.error( "Worker raises an exception on close()", e );
			}

			try {
				readerProvider.destroy();
			}
			catch ( Exception e ) {
				log.error( "ReaderProvider raises an exception on destroy()", e );
			}

			//TODO move to DirectoryProviderFactory for cleaner
			for ( DirectoryProvider dp : getDirectoryProviders() ) {
				try {
					dp.stop();
				}
				catch ( Exception e ) {
					log.error( "DirectoryProvider raises an exception on stop() ", e );
				}
			}
		}
	}

	public void addClassToDirectoryProvider(Class<?> clazz, DirectoryProvider<?> directoryProvider) {
		//no need to set a read barrier, we only use this class in the init thread
		DirectoryProviderData data = dirProviderData.get( directoryProvider );
		if ( data == null ) {
			data = new DirectoryProviderData();
			dirProviderData.put( directoryProvider, data );
		}
		data.classes.add( clazz );
	}

	public Set<Class<?>> getClassesInDirectoryProvider(DirectoryProvider<?> directoryProvider) {
		if ( barrier != 0 ) {
		} //read barrier
		return Collections.unmodifiableSet( dirProviderData.get( directoryProvider ).classes );
	}

	private void bindFilterDefs(XClass mappedXClass) {
		FullTextFilterDef defAnn = mappedXClass.getAnnotation( FullTextFilterDef.class );
		if ( defAnn != null ) {
			bindFilterDef( defAnn, mappedXClass );
		}
		FullTextFilterDefs defsAnn = mappedXClass.getAnnotation( FullTextFilterDefs.class );
		if ( defsAnn != null ) {
			for ( FullTextFilterDef def : defsAnn.value() ) {
				bindFilterDef( def, mappedXClass );
			}
		}
	}

	private void bindFilterDef(FullTextFilterDef defAnn, XClass mappedXClass) {
		if ( filterDefinitions.containsKey( defAnn.name() ) ) {
			throw new SearchException(
					"Multiple definition of @FullTextFilterDef.name=" + defAnn.name() + ": "
							+ mappedXClass.getName()
			);
		}

		FilterDef filterDef = new FilterDef( defAnn );
		try {
			filterDef.getImpl().newInstance();
		}
		catch ( IllegalAccessException e ) {
			throw new SearchException( "Unable to create Filter class: " + filterDef.getImpl().getName(), e );
		}
		catch ( InstantiationException e ) {
			throw new SearchException( "Unable to create Filter class: " + filterDef.getImpl().getName(), e );
		}
		for ( Method method : filterDef.getImpl().getMethods() ) {
			if ( method.isAnnotationPresent( Factory.class ) ) {
				if ( filterDef.getFactoryMethod() != null ) {
					throw new SearchException(
							"Multiple @Factory methods found" + defAnn.name() + ": "
									+ filterDef.getImpl().getName() + "." + method.getName()
					);
				}
				if ( !method.isAccessible() ) {
					method.setAccessible( true );
				}
				filterDef.setFactoryMethod( method );
			}
			if ( method.isAnnotationPresent( Key.class ) ) {
				if ( filterDef.getKeyMethod() != null ) {
					throw new SearchException(
							"Multiple @Key methods found" + defAnn.name() + ": "
									+ filterDef.getImpl().getName() + "." + method.getName()
					);
				}
				if ( !method.isAccessible() ) {
					method.setAccessible( true );
				}
				filterDef.setKeyMethod( method );
			}

			String name = method.getName();
			if ( name.startsWith( "set" ) && method.getParameterTypes().length == 1 ) {
				filterDef.addSetter( Introspector.decapitalize( name.substring( 3 ) ), method );
			}
		}
		filterDefinitions.put( defAnn.name(), filterDef );
	}


	public Map<Class<?>, DocumentBuilderIndexedEntity<?>> getDocumentBuildersIndexedEntities() {
		if ( barrier != 0 ) {
		} //read barrier
		return documentBuildersIndexedEntities;
	}

	@SuppressWarnings("unchecked")
	public <T> DocumentBuilderIndexedEntity<T> getDocumentBuilderIndexedEntity(Class<T> entityType) {
		if ( barrier != 0 ) {
		} //read barrier
		return ( DocumentBuilderIndexedEntity<T> ) documentBuildersIndexedEntities.get( entityType );
	}

	@SuppressWarnings("unchecked")
	public <T> DocumentBuilderContainedEntity<T> getDocumentBuilderContainedEntity(Class<T> entityType) {
		if ( barrier != 0 ) {
		} //read barrier
		return ( DocumentBuilderContainedEntity<T> ) documentBuildersContainedEntities.get( entityType );
	}

	public Set<DirectoryProvider<?>> getDirectoryProviders() {
		if ( barrier != 0 ) {
		} //read barrier
		return this.dirProviderData.keySet();
	}

	public Worker getWorker() {
		if ( barrier != 0 ) {
		} //read barrier
		return worker;
	}

	public void addOptimizerStrategy(DirectoryProvider<?> provider, OptimizerStrategy optimizerStrategy) {
		//no need to set a read barrier, we run this method on the init thread
		DirectoryProviderData data = dirProviderData.get( provider );
		if ( data == null ) {
			data = new DirectoryProviderData();
			dirProviderData.put( provider, data );
		}
		data.optimizerStrategy = optimizerStrategy;
	}

	public void addIndexingParameters(DirectoryProvider<?> provider, LuceneIndexingParameters indexingParams) {
		//no need to set a read barrier, we run this method on the init thread
		dirProviderIndexingParams.put( provider, indexingParams );
	}

	public OptimizerStrategy getOptimizerStrategy(DirectoryProvider<?> provider) {
		if ( barrier != 0 ) {
		} //read barrier
		return dirProviderData.get( provider ).optimizerStrategy;
	}

	public LuceneIndexingParameters getIndexingParameters(DirectoryProvider<?> provider) {
		if ( barrier != 0 ) {
		} //read barrier
		return dirProviderIndexingParams.get( provider );
	}

	public ReaderProvider getReaderProvider() {
		if ( barrier != 0 ) {
		} //read barrier
		return readerProvider;
	}

	public DirectoryProvider[] getDirectoryProviders(Class<?> entity) {
		if ( barrier != 0 ) {
		} //read barrier
		DocumentBuilderIndexedEntity<?> documentBuilder = getDocumentBuilderIndexedEntity( entity );
		return documentBuilder == null ? null : documentBuilder.getDirectoryProviders();
	}

	public void optimize() {
		if ( barrier != 0 ) {
		} //read barrier
		Set<Class<?>> clazzs = getDocumentBuildersIndexedEntities().keySet();
		for ( Class clazz : clazzs ) {
			optimize( clazz );
		}
	}

	public void optimize(Class entityType) {
		if ( barrier != 0 ) {
		} //read barrier
		if ( !getDocumentBuildersIndexedEntities().containsKey( entityType ) ) {
			throw new SearchException( "Entity not indexed: " + entityType );
		}
		List<LuceneWork> queue = new ArrayList<LuceneWork>( 1 );
		queue.add( new OptimizeLuceneWork( entityType ) );
		getBackendQueueProcessorFactory().getProcessor( queue ).run();
	}

	public Analyzer getAnalyzer(String name) {
		if ( barrier != 0 ) {
		} //read barrier
		final Analyzer analyzer = analyzers.get( name );
		if ( analyzer == null ) {
			throw new SearchException( "Unknown Analyzer definition: " + name );
		}
		return analyzer;
	}

	public Analyzer getAnalyzer(Class clazz) {
		if ( clazz == null ) {
			throw new IllegalArgumentException( "A class has to be specified for retrieving a scoped analyzer" );
		}

		DocumentBuilderIndexedEntity<?> builder = documentBuildersIndexedEntities.get( clazz );
		if ( builder == null ) {
			throw new IllegalArgumentException(
					"Entity for which to retrieve the scoped analyzer is not an @Indexed entity: " + clazz.getName()
			);
		}

		return builder.getAnalyzer();
	}

	private void initDocumentBuilders(SearchConfiguration cfg, ReflectionManager reflectionManager) {
		InitContext context = new InitContext( cfg );
		Iterator<Class<?>> iter = cfg.getClassMappings();
		DirectoryProviderFactory factory = new DirectoryProviderFactory();

		while ( iter.hasNext() ) {
			Class mappedClass = iter.next();
			if ( mappedClass == null ) {
				continue;
			}
			@SuppressWarnings( "unchecked" )
			XClass mappedXClass = reflectionManager.toXClass( mappedClass );
			if ( mappedXClass == null ) {
				continue;
			}

			if ( mappedXClass.isAnnotationPresent( Indexed.class ) ) {

				if ( mappedXClass.isAbstract() ) {
					log.warn( "Abstract classes can never insert index documents. Remove @Indexed." );
					continue;
				}

				DirectoryProviderFactory.DirectoryProviders providers = factory.createDirectoryProviders(
						mappedXClass, cfg, this, reflectionManager
				);
				//FIXME DocumentBuilderIndexedEntity needs to be built by a helper method receiving Class<T> to infer T properly
				//XClass unfortunately is not (yet) genericized: TODO?
				final DocumentBuilderIndexedEntity<?> documentBuilder = new DocumentBuilderIndexedEntity(
						mappedXClass, context, providers.getProviders(), providers.getSelectionStrategy(),
						reflectionManager
				);

				indexHierarchy.addIndexedClass( mappedClass );
				documentBuildersIndexedEntities.put( mappedClass, documentBuilder );
			}
			else {
				//FIXME DocumentBuilderIndexedEntity needs to be built by a helper method receiving Class<T> to infer T properly
				//XClass unfortunately is not (yet) genericized: TODO?
				final DocumentBuilderContainedEntity<?> documentBuilder = new DocumentBuilderContainedEntity(
						mappedXClass, context, reflectionManager
				);
				//TODO enhance that, I don't like to expose EntityState
				if ( documentBuilder.getEntityState() != EntityState.NON_INDEXABLE ) {
					documentBuildersContainedEntities.put( mappedClass, documentBuilder );
				}
			}
			bindFilterDefs( mappedXClass );
			//TODO should analyzer def for classes at tyher sqme level???
		}
		analyzers = context.initLazyAnalyzers();
		factory.startDirectoryProviders();
	}

	private static FilterCachingStrategy buildFilterCachingStrategy(Properties properties) {
		FilterCachingStrategy filterCachingStrategy;
		String impl = properties.getProperty( Environment.FILTER_CACHING_STRATEGY );
		if ( StringHelper.isEmpty( impl ) || "mru".equalsIgnoreCase( impl ) ) {
			filterCachingStrategy = new MRUFilterCachingStrategy();
		}
		else {
			try {
				Class filterCachingStrategyClass = org.hibernate
						.annotations
						.common
						.util
						.ReflectHelper
						.classForName( impl, SearchFactoryImpl.class );
				filterCachingStrategy = ( FilterCachingStrategy ) filterCachingStrategyClass.newInstance();
			}
			catch ( ClassNotFoundException e ) {
				throw new SearchException( "Unable to find filterCachingStrategy class: " + impl, e );
			}
			catch ( IllegalAccessException e ) {
				throw new SearchException( "Unable to instantiate filterCachingStrategy class: " + impl, e );
			}
			catch ( InstantiationException e ) {
				throw new SearchException( "Unable to instantiate filterCachingStrategy class: " + impl, e );
			}
		}
		filterCachingStrategy.initialize( properties );
		return filterCachingStrategy;
	}

	public FilterCachingStrategy getFilterCachingStrategy() {
		if ( barrier != 0 ) {
		} //read barrier
		return filterCachingStrategy;
	}

	public FilterDef getFilterDefinition(String name) {
		if ( barrier != 0 ) {
		} //read barrier
		return filterDefinitions.get( name );
	}

	private static class DirectoryProviderData {
		public final ReentrantLock dirLock = new ReentrantLock();
		public OptimizerStrategy optimizerStrategy;
		public Set<Class<?>> classes = new HashSet<Class<?>>( 2 );
	}

	public ReentrantLock getDirectoryProviderLock(DirectoryProvider<?> dp) {
		if ( barrier != 0 ) {
		} //read barrier
		return this.dirProviderData.get( dp ).dirLock;
	}

	public void addDirectoryProvider(DirectoryProvider<?> provider) {
		//no need to set a barrier we use this method in the init thread
		this.dirProviderData.put( provider, new DirectoryProviderData() );
	}

	public int getFilterCacheBitResultsSize() {
		if ( barrier != 0 ) {
		} //read barrier
		return cacheBitResultsSize;
	}

	public Set<Class<?>> getIndexedTypesPolymorphic(Class<?>[] classes) {
		if ( barrier != 0 ) {
		} //read barrier
		return indexHierarchy.getIndexedClasses( classes );
	}

	/**
	 * Helper class which keeps track of all super classes and interfaces of the indexed entities.
	 */
	private static class PolymorphicIndexHierarchy {
		private Map<Class<?>, Set<Class<?>>> classToIndexedClass;

		PolymorphicIndexHierarchy() {
			classToIndexedClass = new HashMap<Class<?>, Set<Class<?>>>();
		}

		void addIndexedClass(Class indexedClass) {
			addClass( indexedClass, indexedClass );
			Class superClass = indexedClass.getSuperclass();
			while ( superClass != null ) {
				addClass( superClass, indexedClass );
				superClass = superClass.getSuperclass();
			}
			for ( Class clazz : indexedClass.getInterfaces() ) {
				addClass( clazz, indexedClass );
			}
		}

		private void addClass(Class superclass, Class indexedClass) {
			Set<Class<?>> classesSet = classToIndexedClass.get( superclass );
			if ( classesSet == null ) {
				classesSet = new HashSet<Class<?>>();
				classToIndexedClass.put( superclass, classesSet );
			}
			classesSet.add( indexedClass );
		}

		Set<Class<?>> getIndexedClasses(Class<?>[] classes) {
			Set<Class<?>> idexedClasses = new HashSet<Class<?>>();
			for ( Class clazz : classes ) {
				Set<Class<?>> set = classToIndexedClass.get( clazz );
				if ( set != null ) {
					// at this point we don't have to care about including indexed subclasses of a indexed class
					// MultiClassesQueryLoader will take care of this later and optimise the queries
					idexedClasses.addAll( set );
				}
			}
			if ( log.isTraceEnabled() ) {
				log.trace( "Targeted indexed classes for {}: {}", Arrays.toString( classes ), idexedClasses );
			}
			return idexedClasses;
		}
	}
}
