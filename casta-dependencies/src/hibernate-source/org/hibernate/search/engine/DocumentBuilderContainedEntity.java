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
 //$Id: DocumentBuilderContainedEntity.java 15637 2008-12-02 14:28:28Z hardy.ferentschik $
package org.hibernate.search.engine;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Field;
import org.apache.lucene.search.Similarity;
import org.slf4j.Logger;

import org.hibernate.Hibernate;
import org.hibernate.annotations.common.AssertionFailure;
import org.hibernate.annotations.common.reflection.ReflectionManager;
import org.hibernate.annotations.common.reflection.XAnnotatedElement;
import org.hibernate.annotations.common.reflection.XClass;
import org.hibernate.annotations.common.reflection.XMember;
import org.hibernate.annotations.common.reflection.XProperty;
import org.hibernate.annotations.common.util.StringHelper;
import org.hibernate.search.SearchException;
import org.hibernate.search.analyzer.Discriminator;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.AnalyzerDefs;
import org.hibernate.search.annotations.AnalyzerDiscriminator;
import org.hibernate.search.annotations.Boost;
import org.hibernate.search.annotations.ClassBridge;
import org.hibernate.search.annotations.ClassBridges;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TermVector;
import org.hibernate.search.backend.LuceneWork;
import org.hibernate.search.backend.WorkType;
import org.hibernate.search.bridge.BridgeFactory;
import org.hibernate.search.bridge.FieldBridge;
import org.hibernate.search.bridge.LuceneOptions;
import org.hibernate.search.impl.InitContext;
import org.hibernate.search.util.LoggerFactory;
import org.hibernate.search.util.ReflectionHelper;
import org.hibernate.search.util.ScopedAnalyzer;

/**
 * Set up and provide a manager for classes which are indexed via <code>@IndexedEmbedded</code>, but themselves do not
 * contain the <code>@Indexed</code> annotation.
 *
 * @author Gavin King
 * @author Emmanuel Bernard
 * @author Sylvain Vieujot
 * @author Richard Hallier
 * @author Hardy Ferentschik
 */
public class DocumentBuilderContainedEntity<T> implements DocumentBuilder {
	private static final Logger log = LoggerFactory.make();

	protected final PropertiesMetadata metadata = new PropertiesMetadata();
	protected final XClass beanClass;
	protected Set<Class<?>> mappedSubclasses = new HashSet<Class<?>>();
	protected ReflectionManager reflectionManager; //available only during initializationa and post-initialization
	protected int level = 0;
	protected int maxLevel = Integer.MAX_VALUE;
	protected final ScopedAnalyzer analyzer = new ScopedAnalyzer();
	protected Similarity similarity;
	protected boolean isRoot;
	protected EntityState entityState;

	/**
	 * Constructor used on contained entities not annotated with <code>@Indexed</code> themselves.
	 *
	 * @param clazz The class for which to build a <code>DocumentBuilderContainedEntity</code>.
	 * @param context Handle to default configuration settings.
	 * @param reflectionManager Reflection manager to use for processing the annotations.
	 */
	public DocumentBuilderContainedEntity(XClass clazz, InitContext context, ReflectionManager reflectionManager) {

		if ( clazz == null ) {
			throw new AssertionFailure( "Unable to build a DocumentBuilderContainedEntity with a null class" );
		}

		this.entityState = EntityState.CONTAINED_IN_ONLY;
		this.beanClass = clazz;
		this.reflectionManager = reflectionManager;

		init( clazz, context );

		if ( metadata.containedInGetters.size() == 0 ) {
			this.entityState = EntityState.NON_INDEXABLE;
		}
	}

	protected void init(XClass clazz, InitContext context) {
		metadata.boost = getBoost( clazz );
		metadata.analyzer = context.getDefaultAnalyzer();

		Set<XClass> processedClasses = new HashSet<XClass>();
		processedClasses.add( clazz );
		initializeClass( clazz, metadata, true, "", processedClasses, context );

		this.analyzer.setGlobalAnalyzer( metadata.analyzer );

		// set the default similarity in case that after processing all classes there is still no similarity set
		if ( this.similarity == null ) {
			this.similarity = context.getDefaultSimilarity();
		}
	}

	public boolean isRoot() {
		return isRoot;
	}

	private void initializeClass(XClass clazz, PropertiesMetadata propertiesMetadata, boolean isRoot, String prefix,
								 Set<XClass> processedClasses, InitContext context) {
		List<XClass> hierarchy = new ArrayList<XClass>();
		for ( XClass currClass = clazz; currClass != null; currClass = currClass.getSuperclass() ) {
			hierarchy.add( currClass );
		}

		/*
		* Iterate the class hierarchy top down. This allows to override the default analyzer for the properties if the class holds one
		*/
		for ( int index = hierarchy.size() - 1; index >= 0; index-- ) {
			XClass currClass = hierarchy.get( index );

			initalizeClassLevelAnnotations( currClass, propertiesMetadata, isRoot, prefix, context );

			// rejecting non properties (ie regular methods) because the object is loaded from Hibernate,
			// so indexing a non property does not make sense
			List<XProperty> methods = currClass.getDeclaredProperties( XClass.ACCESS_PROPERTY );
			for ( XProperty method : methods ) {
				initializeMemberLevelAnnotations(
						method, propertiesMetadata, isRoot, prefix, processedClasses, context
				);
			}

			List<XProperty> fields = currClass.getDeclaredProperties( XClass.ACCESS_FIELD );
			for ( XProperty field : fields ) {
				initializeMemberLevelAnnotations(
						field, propertiesMetadata, isRoot, prefix, processedClasses, context
				);
			}
		}
	}

	/**
	 * Check and initialize class level annotations.
	 *
	 * @param clazz The class to process.
	 * @param propertiesMetadata The meta data holder.
	 * @param isRoot Flag indicating if the specified class is a root entity, meaning the start of a chain of indexed
	 * entities.
	 * @param prefix The current prefix used for the <code>Document</code> field names.
	 * @param context Handle to default configuration settings.
	 */
	private void initalizeClassLevelAnnotations(XClass clazz, PropertiesMetadata propertiesMetadata, boolean isRoot, String prefix, InitContext context) {

		// check for a class level specified analyzer
		Analyzer analyzer = getAnalyzer( clazz, context );
		if ( analyzer != null ) {
			propertiesMetadata.analyzer = analyzer;
		}

		// check for AnalyzerDefs annotations
		checkForAnalyzerDefs( clazz, context );

		// Check for any ClassBridges annotation.
		ClassBridges classBridgesAnn = clazz.getAnnotation( ClassBridges.class );
		if ( classBridgesAnn != null ) {
			ClassBridge[] cbs = classBridgesAnn.value();
			for ( ClassBridge cb : cbs ) {
				bindClassBridgeAnnotation( prefix, propertiesMetadata, cb, context );
			}
		}

		// Check for any ClassBridge style of annotations.
		ClassBridge classBridgeAnn = clazz.getAnnotation( ClassBridge.class );
		if ( classBridgeAnn != null ) {
			bindClassBridgeAnnotation( prefix, propertiesMetadata, classBridgeAnn, context );
		}

		checkForAnalyzerDiscriminator( clazz, propertiesMetadata );

		// Get similarity
		//TODO: similarity form @IndexedEmbedded are not taken care of. Exception??
		if ( isRoot ) {
			checkForSimilarity( clazz );
		}
	}

	/**
	 * Check for field and method level annotations.
	 */
	protected void initializeMemberLevelAnnotations(XProperty member, PropertiesMetadata propertiesMetadata, boolean isRoot,
													String prefix, Set<XClass> processedClasses, InitContext context) {
		checkDocumentId( member, propertiesMetadata, isRoot, prefix, context );
		checkForField( member, propertiesMetadata, prefix, context );
		checkForFields( member, propertiesMetadata, prefix, context );
		checkForAnalyzerDefs( member, context );
		checkForAnalyzerDiscriminator( member, propertiesMetadata );
		checkForIndexedEmbedded( member, propertiesMetadata, prefix, processedClasses, context );
		checkForContainedIn( member, propertiesMetadata );
	}

	protected Analyzer getAnalyzer(XAnnotatedElement annotatedElement, InitContext context) {
		org.hibernate.search.annotations.Analyzer analyzerAnn =
				annotatedElement.getAnnotation( org.hibernate.search.annotations.Analyzer.class );
		return getAnalyzer( analyzerAnn, context );
	}

	protected Analyzer getAnalyzer(org.hibernate.search.annotations.Analyzer analyzerAnn, InitContext context) {
		Class analyzerClass = analyzerAnn == null ? void.class : analyzerAnn.impl();
		if ( analyzerClass == void.class ) {
			String definition = analyzerAnn == null ? "" : analyzerAnn.definition();
			if ( StringHelper.isEmpty( definition ) ) {
				return null;
			}
			else {

				return context.buildLazyAnalyzer( definition );
			}
		}
		else {
			try {
				return ( Analyzer ) analyzerClass.newInstance();
			}
			catch ( ClassCastException e ) {
				throw new SearchException(
						"Lucene analyzer does not implement " + Analyzer.class.getName() + ": " + analyzerClass.getName(),
						e
				);
			}
			catch ( Exception e ) {
				throw new SearchException(
						"Failed to instantiate lucene analyzer with type " + analyzerClass.getName(), e
				);
			}
		}
	}

	private void checkForAnalyzerDefs(XAnnotatedElement annotatedElement, InitContext context) {
		AnalyzerDefs defs = annotatedElement.getAnnotation( AnalyzerDefs.class );
		if ( defs != null ) {
			for ( AnalyzerDef def : defs.value() ) {
				context.addAnalyzerDef( def );
			}
		}
		AnalyzerDef def = annotatedElement.getAnnotation( AnalyzerDef.class );
		context.addAnalyzerDef( def );
	}

	private void checkForAnalyzerDiscriminator(XAnnotatedElement annotatedElement, PropertiesMetadata propertiesMetadata) {
		AnalyzerDiscriminator discriminiatorAnn = annotatedElement.getAnnotation( AnalyzerDiscriminator.class );
		if ( discriminiatorAnn != null ) {
			if ( propertiesMetadata.discriminator != null ) {
				throw new SearchException(
						"Multiple AnalyzerDiscriminator defined in the same class hierarchy: " + beanClass.getName()
				);
			}
						
			Class<? extends Discriminator> discriminatorClass = discriminiatorAnn.impl();
			try {
				propertiesMetadata.discriminator = discriminatorClass.newInstance();
			}
			catch ( Exception e ) {
				throw new SearchException(
						"Unable to instantiate analyzer discriminator implementation: " + discriminatorClass.getName()
				);
			}

			if ( annotatedElement instanceof XMember ) {
				propertiesMetadata.discriminatorGetter = ( XMember ) annotatedElement;
			}
		}
	}

	public Similarity getSimilarity() {
		return similarity;
	}

	private void checkForFields(XProperty member, PropertiesMetadata propertiesMetadata, String prefix, InitContext context) {
		org.hibernate.search.annotations.Fields fieldsAnn =
				member.getAnnotation( org.hibernate.search.annotations.Fields.class );
		if ( fieldsAnn != null ) {
			for ( org.hibernate.search.annotations.Field fieldAnn : fieldsAnn.value() ) {
				bindFieldAnnotation( member, propertiesMetadata, prefix, fieldAnn, context );
			}
		}
	}

	private void checkForSimilarity(XClass currClass) {
		org.hibernate.search.annotations.Similarity similarityAnn = currClass.getAnnotation( org.hibernate.search.annotations.Similarity.class );
		if ( similarityAnn != null ) {
			if ( similarity != null ) {
				throw new SearchException(
						"Multiple Similarities defined in the same class hierarchy: " + beanClass.getName()
				);
			}
			Class similarityClass = similarityAnn.impl();
			try {
				similarity = ( Similarity ) similarityClass.newInstance();
			}
			catch ( Exception e ) {
				log.error(
						"Exception attempting to instantiate Similarity '{}' set for {}",
						similarityClass.getName(), beanClass.getName()
				);
			}
		}
	}

	private void checkForField(XProperty member, PropertiesMetadata propertiesMetadata, String prefix, InitContext context) {
		org.hibernate.search.annotations.Field fieldAnn =
				member.getAnnotation( org.hibernate.search.annotations.Field.class );
		if ( fieldAnn != null ) {
			bindFieldAnnotation( member, propertiesMetadata, prefix, fieldAnn, context );
		}
	}

	private void checkForContainedIn(XProperty member, PropertiesMetadata propertiesMetadata) {
		ContainedIn containedAnn = member.getAnnotation( ContainedIn.class );
		if ( containedAnn != null ) {
			ReflectionHelper.setAccessible( member );
			propertiesMetadata.containedInGetters.add( member );
		}
	}

	private void checkForIndexedEmbedded(XProperty member, PropertiesMetadata propertiesMetadata, String prefix, Set<XClass> processedClasses, InitContext context) {
		IndexedEmbedded embeddedAnn = member.getAnnotation( IndexedEmbedded.class );
		if ( embeddedAnn != null ) {
			int oldMaxLevel = maxLevel;
			int potentialLevel = embeddedAnn.depth() + level;
			if ( potentialLevel < 0 ) {
				potentialLevel = Integer.MAX_VALUE;
			}
			maxLevel = potentialLevel > maxLevel ? maxLevel : potentialLevel;
			level++;

			XClass elementClass;
			if ( void.class == embeddedAnn.targetElement() ) {
				elementClass = member.getElementClass();
			}
			else {
				elementClass = reflectionManager.toXClass( embeddedAnn.targetElement() );
			}
			if ( maxLevel == Integer.MAX_VALUE //infinite
					&& processedClasses.contains( elementClass ) ) {
				throw new SearchException(
						"Circular reference. Duplicate use of "
								+ elementClass.getName()
								+ " in root entity " + beanClass.getName()
								+ "#" + buildEmbeddedPrefix( prefix, embeddedAnn, member )
				);
			}
			if ( level <= maxLevel ) {
				processedClasses.add( elementClass ); //push

				ReflectionHelper.setAccessible( member );
				propertiesMetadata.embeddedGetters.add( member );
				PropertiesMetadata metadata = new PropertiesMetadata();
				propertiesMetadata.embeddedPropertiesMetadata.add( metadata );
				metadata.boost = getBoost( member, null );
				//property > entity analyzer
				Analyzer analyzer = getAnalyzer( member, context );
				metadata.analyzer = analyzer != null ? analyzer : propertiesMetadata.analyzer;
				String localPrefix = buildEmbeddedPrefix( prefix, embeddedAnn, member );
				initializeClass( elementClass, metadata, false, localPrefix, processedClasses, context );
				/**
				 * We will only index the "expected" type but that's OK, HQL cannot do downcasting either
				 */
				if ( member.isArray() ) {
					propertiesMetadata.embeddedContainers.add( PropertiesMetadata.Container.ARRAY );
				}
				else if ( member.isCollection() ) {
					if ( Map.class.equals( member.getCollectionClass() ) ) {
						//hum subclasses etc etc??
						propertiesMetadata.embeddedContainers.add( PropertiesMetadata.Container.MAP );
					}
					else {
						propertiesMetadata.embeddedContainers.add( PropertiesMetadata.Container.COLLECTION );
					}
				}
				else {
					propertiesMetadata.embeddedContainers.add( PropertiesMetadata.Container.OBJECT );
				}

				processedClasses.remove( elementClass ); //pop
			}
			else if ( log.isTraceEnabled() ) {
				String localPrefix = buildEmbeddedPrefix( prefix, embeddedAnn, member );
				log.trace( "depth reached, ignoring {}", localPrefix );
			}

			level--;
			maxLevel = oldMaxLevel; //set back the the old max level
		}
	}

	protected void checkDocumentId(XProperty member, PropertiesMetadata propertiesMetadata, boolean isRoot, String prefix, InitContext context) {
		Annotation documentIdAnn = member.getAnnotation( DocumentId.class );
		if ( documentIdAnn != null ) {
			log.warn(
					"@DocumentId specified on an entity which is not indexed by itself. Annotation gets ignored. Use @Field instead."
			);
		}
	}

	/**
	 * Determines the property name for the document id. It is either the name of the property itself or the
	 * value of the name attribute of the <code>idAnnotation</code>.
	 *
	 * @param member the property used as id property.
	 * @param idAnnotation the id annotation
	 *
	 * @return property name to be used as document id.
	 */
	protected String getIdAttributeName(XProperty member, Annotation idAnnotation) {
		String name = null;
		try {
			Method m = idAnnotation.getClass().getMethod( "name" );
			name = ( String ) m.invoke( idAnnotation );
		}
		catch ( Exception e ) {
			// ignore
		}

		return ReflectionHelper.getAttributeName( member, name );
	}

	private void bindClassBridgeAnnotation(String prefix, PropertiesMetadata propertiesMetadata, ClassBridge ann, InitContext context) {
		String fieldName = prefix + ann.name();
		propertiesMetadata.classNames.add( fieldName );
		propertiesMetadata.classStores.add( getStore( ann.store() ) );
		propertiesMetadata.classIndexes.add( getIndex( ann.index() ) );
		propertiesMetadata.classTermVectors.add( getTermVector( ann.termVector() ) );
		propertiesMetadata.classBridges.add( BridgeFactory.extractType( ann ) );
		propertiesMetadata.classBoosts.add( ann.boost().value() );

		Analyzer analyzer = getAnalyzer( ann.analyzer(), context );
		if ( analyzer == null ) {
			analyzer = propertiesMetadata.analyzer;
		}
		if ( analyzer == null ) {
			throw new AssertionFailure( "Analyzer should not be undefined" );
		}
		this.analyzer.addScopedAnalyzer( fieldName, analyzer );
	}

	private void bindFieldAnnotation(XProperty member, PropertiesMetadata propertiesMetadata, String prefix, org.hibernate.search.annotations.Field fieldAnn, InitContext context) {
		ReflectionHelper.setAccessible( member );
		propertiesMetadata.fieldGetters.add( member );
		String fieldName = prefix + ReflectionHelper.getAttributeName( member, fieldAnn.name() );
		propertiesMetadata.fieldNames.add( fieldName );
		propertiesMetadata.fieldStore.add( getStore( fieldAnn.store() ) );
		propertiesMetadata.fieldIndex.add( getIndex( fieldAnn.index() ) );
		propertiesMetadata.fieldBoosts.add( getBoost( member, fieldAnn ) );
		propertiesMetadata.fieldTermVectors.add( getTermVector( fieldAnn.termVector() ) );
		propertiesMetadata.fieldBridges.add( BridgeFactory.guessType( fieldAnn, member, reflectionManager ) );

		// Field > property > entity analyzer
		Analyzer analyzer = getAnalyzer( fieldAnn.analyzer(), context );
		if ( analyzer == null ) {
			analyzer = getAnalyzer( member, context );
		}
		if ( analyzer != null ) {
			this.analyzer.addScopedAnalyzer( fieldName, analyzer );
		}
	}

	protected Float getBoost(XProperty member, org.hibernate.search.annotations.Field fieldAnn) {
		float computedBoost = 1.0f;
		Boost boostAnn = member.getAnnotation( Boost.class );
		if ( boostAnn != null ) {
			computedBoost = boostAnn.value();
		}
		if ( fieldAnn != null ) {
			computedBoost *= fieldAnn.boost().value();
		}
		return computedBoost;
	}

	private String buildEmbeddedPrefix(String prefix, IndexedEmbedded embeddedAnn, XProperty member) {
		String localPrefix = prefix;
		if ( ".".equals( embeddedAnn.prefix() ) ) {
			//default to property name
			localPrefix += member.getName() + '.';
		}
		else {
			localPrefix += embeddedAnn.prefix();
		}
		return localPrefix;
	}

	protected Field.Store getStore(Store store) {
		switch ( store ) {
			case NO:
				return Field.Store.NO;
			case YES:
				return Field.Store.YES;
			case COMPRESS:
				return Field.Store.COMPRESS;
			default:
				throw new AssertionFailure( "Unexpected Store: " + store );
		}
	}

	protected Field.TermVector getTermVector(TermVector vector) {
		switch ( vector ) {
			case NO:
				return Field.TermVector.NO;
			case YES:
				return Field.TermVector.YES;
			case WITH_OFFSETS:
				return Field.TermVector.WITH_OFFSETS;
			case WITH_POSITIONS:
				return Field.TermVector.WITH_POSITIONS;
			case WITH_POSITION_OFFSETS:
				return Field.TermVector.WITH_POSITIONS_OFFSETS;
			default:
				throw new AssertionFailure( "Unexpected TermVector: " + vector );
		}
	}

	protected Field.Index getIndex(Index index) {
		switch ( index ) {
			case NO:
				return Field.Index.NO;
			case NO_NORMS:
				return Field.Index.NOT_ANALYZED_NO_NORMS;
			case TOKENIZED:
				return Field.Index.ANALYZED;
			case UN_TOKENIZED:
				return Field.Index.NOT_ANALYZED;
			default:
				throw new AssertionFailure( "Unexpected Index: " + index );
		}
	}

	protected Float getBoost(XClass element) {
		if ( element == null ) {
			return null;
		}
		Boost boost = element.getAnnotation( Boost.class );
		return boost != null ?
				boost.value() :
				null;
	}

	//TODO could we use T instead of EntityClass?
	public void addWorkToQueue(Class<T> entityClass, T entity, Serializable id, WorkType workType, List<LuceneWork> queue, SearchFactoryImplementor searchFactoryImplementor) {
		/**
		 * When references are changed, either null or another one, we expect dirty checking to be triggered (both sides
		 * have to be updated)
		 * When the internal object is changed, we apply the {Add|Update}Work on containedIns
		 */
		if ( workType.searchForContainers() ) {
			processContainedIn( entity, queue, metadata, searchFactoryImplementor );
		}
	}

	private void processContainedIn(Object instance, List<LuceneWork> queue, PropertiesMetadata metadata, SearchFactoryImplementor searchFactoryImplementor) {
		for ( int i = 0; i < metadata.containedInGetters.size(); i++ ) {
			XMember member = metadata.containedInGetters.get( i );
			Object value = ReflectionHelper.getMemberValue( instance, member );
			if ( value == null ) {
				continue;
			}

			if ( member.isArray() ) {
				for ( Object arrayValue : ( Object[] ) value ) {
					//highly inneficient but safe wrt the actual targeted class
					Class<?> valueClass = Hibernate.getClass( arrayValue );
					DocumentBuilderIndexedEntity<?> builderIndexedEntity = searchFactoryImplementor.getDocumentBuilderIndexedEntity(
							valueClass
					);
					if ( builderIndexedEntity == null ) {
						continue;
					}
					processContainedInValue(
							arrayValue, queue, valueClass,
							builderIndexedEntity, searchFactoryImplementor
					);
				}
			}
			else if ( member.isCollection() ) {
				Collection collection;
				if ( Map.class.equals( member.getCollectionClass() ) ) {
					//hum
					collection = ( ( Map ) value ).values();
				}
				else {
					collection = ( Collection ) value;
				}
				for ( Object collectionValue : collection ) {
					//highly inneficient but safe wrt the actual targeted class
					Class<?> valueClass = Hibernate.getClass( collectionValue );
					DocumentBuilderIndexedEntity<?> builderIndexedEntity = searchFactoryImplementor.getDocumentBuilderIndexedEntity(
							valueClass
					);
					if ( builderIndexedEntity == null ) {
						continue;
					}
					processContainedInValue(
							collectionValue, queue, valueClass,
							builderIndexedEntity, searchFactoryImplementor
					);
				}
			}
			else {
				Class<?> valueClass = Hibernate.getClass( value );
				DocumentBuilderIndexedEntity<?> builderIndexedEntity = searchFactoryImplementor.getDocumentBuilderIndexedEntity(
						valueClass
				);
				if ( builderIndexedEntity == null ) {
					continue;
				}
				processContainedInValue( value, queue, valueClass, builderIndexedEntity, searchFactoryImplementor );
			}
		}
		//an embedded cannot have a useful @ContainedIn (no shared reference)
		//do not walk through them
	}

	private void processContainedInValue(Object value, List<LuceneWork> queue, Class<?> valueClass,
										 DocumentBuilderIndexedEntity builderIndexedEntity, SearchFactoryImplementor searchFactoryImplementor) {
		Serializable id = ( Serializable ) ReflectionHelper.getMemberValue( value, builderIndexedEntity.idGetter );
		builderIndexedEntity.addWorkToQueue( valueClass, value, id, WorkType.UPDATE, queue, searchFactoryImplementor );
	}

	public Analyzer getAnalyzer() {
		return analyzer;
	}

	public void postInitialize(Set<Class<?>> indexedClasses) {
		if ( entityState == EntityState.NON_INDEXABLE ) {
			throw new AssertionFailure( "A non indexed entity is post processed" );
		}
		//this method does not requires synchronization
		Class plainClass = reflectionManager.toClass( beanClass );
		Set<Class<?>> tempMappedSubclasses = new HashSet<Class<?>>();
		//together with the caller this creates a o(2), but I think it's still faster than create the up hierarchy for each class
		for ( Class currentClass : indexedClasses ) {
			if ( plainClass != currentClass && plainClass.isAssignableFrom( currentClass ) ) {
				tempMappedSubclasses.add( currentClass );
			}
		}
		this.mappedSubclasses = Collections.unmodifiableSet( tempMappedSubclasses );
		Class superClass = plainClass.getSuperclass();
		this.isRoot = true;
		while ( superClass != null ) {
			if ( indexedClasses.contains( superClass ) ) {
				this.isRoot = false;
				break;
			}
			superClass = superClass.getSuperclass();
		}
		this.reflectionManager = null;
	}

	public EntityState getEntityState() {
		return entityState;
	}

	public Set<Class<?>> getMappedSubclasses() {
		return mappedSubclasses;
	}

	/**
	 * Wrapper class containing all the meta data extracted out of the entities.
	 */
	protected static class PropertiesMetadata {
		public Float boost;
		public Analyzer analyzer;
		public Discriminator discriminator;
		public XMember discriminatorGetter;
		public final List<String> fieldNames = new ArrayList<String>();
		public final List<XMember> fieldGetters = new ArrayList<XMember>();
		public final List<FieldBridge> fieldBridges = new ArrayList<FieldBridge>();
		public final List<Field.Store> fieldStore = new ArrayList<Field.Store>();
		public final List<Field.Index> fieldIndex = new ArrayList<Field.Index>();
		public final List<Float> fieldBoosts = new ArrayList<Float>();
		public final List<Field.TermVector> fieldTermVectors = new ArrayList<Field.TermVector>();
		public final List<XMember> embeddedGetters = new ArrayList<XMember>();
		public final List<PropertiesMetadata> embeddedPropertiesMetadata = new ArrayList<PropertiesMetadata>();
		public final List<Container> embeddedContainers = new ArrayList<Container>();
		public final List<XMember> containedInGetters = new ArrayList<XMember>();
		public final List<String> classNames = new ArrayList<String>();
		public final List<Field.Store> classStores = new ArrayList<Field.Store>();
		public final List<Field.Index> classIndexes = new ArrayList<Field.Index>();
		public final List<FieldBridge> classBridges = new ArrayList<FieldBridge>();
		public final List<Field.TermVector> classTermVectors = new ArrayList<Field.TermVector>();
		public final List<Float> classBoosts = new ArrayList<Float>();

		public enum Container {
			OBJECT,
			COLLECTION,
			MAP,
			ARRAY
		}

		protected LuceneOptions getClassLuceneOptions(int i) {
			return new LuceneOptionsImpl(
					classStores.get( i ),
					classIndexes.get( i ), classTermVectors.get( i ), classBoosts.get( i )
			);
		}

		protected LuceneOptions getFieldLuceneOptions(int i) {
			LuceneOptions options;
			options = new LuceneOptionsImpl(
					fieldStore.get( i ),
					fieldIndex.get( i ), fieldTermVectors.get( i ), fieldBoosts.get( i )
			);
			return options;
		}
	}
}