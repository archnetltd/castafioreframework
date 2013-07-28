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
 // $Id: InitContext.java 15532 2008-11-07 16:06:17Z hardy.ferentschik $
package org.hibernate.search.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.search.Similarity;

import org.hibernate.search.Environment;
import org.hibernate.search.SearchException;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.cfg.SearchConfiguration;
import org.hibernate.search.util.DelegateNamedAnalyzer;
import org.hibernate.util.ReflectHelper;

/**
 * Provides access to some default configuration settings (eg default <code>Analyzer</code> or default
 * <code>Similarity</code>) and checks whether certain optional libraries are available.
 *
 *
 * @author Emmanuel Bernard
 * @author Hardy Ferentschik
 */
public class InitContext {
	private final Map<String, AnalyzerDef> analyzerDefs = new HashMap<String, AnalyzerDef>();
	private final List<DelegateNamedAnalyzer> lazyAnalyzers = new ArrayList<DelegateNamedAnalyzer>();
	private final Analyzer defaultAnalyzer;
	private final Similarity defaultSimilarity;
	private final boolean solrPresent;
	private final boolean jpaPresent;

	public InitContext(SearchConfiguration cfg) {
		defaultAnalyzer = initAnalyzer(cfg);
		defaultSimilarity = initSimilarity(cfg);
		solrPresent = isPresent( "org.apache.solr.analysis.TokenizerFactory" );
		jpaPresent = isPresent( "javax.persistence.Id" );
	}

	public void addAnalyzerDef(AnalyzerDef ann) {
		//FIXME somehow remember where the analyzerDef comes from and raise an exception if an analyzerDef
		//with the same name from two different places are added
		//multiple adding from the same place is required to deal with inheritance hierarchy processed multiple times
		if ( ann != null && analyzerDefs.put( ann.name(), ann ) != null ) {
			//throw new SearchException("Multiple AnalyzerDef with the same name: " + name);
		}
	}

	public Analyzer buildLazyAnalyzer(String name) {
		final DelegateNamedAnalyzer delegateNamedAnalyzer = new DelegateNamedAnalyzer( name );
		lazyAnalyzers.add(delegateNamedAnalyzer);
		return delegateNamedAnalyzer;
	}

	public List<DelegateNamedAnalyzer> getLazyAnalyzers() {
		return lazyAnalyzers;
	}

	/**
	 * Initializes the Lucene analyzer to use by reading the analyzer class from the configuration and instantiating it.
	 *
	 * @param cfg
	 *            The current configuration.
	 * @return The Lucene analyzer to use for tokenisation.
	 */
	private Analyzer initAnalyzer(SearchConfiguration cfg) {
		Class analyzerClass;
		String analyzerClassName = cfg.getProperty( Environment.ANALYZER_CLASS);
		if (analyzerClassName != null) {
			try {
				analyzerClass = ReflectHelper.classForName(analyzerClassName);
			} catch (Exception e) {
				return buildLazyAnalyzer( analyzerClassName );
			}
		} else {
			analyzerClass = StandardAnalyzer.class;
		}
		// Initialize analyzer
		Analyzer defaultAnalyzer;
		try {
			defaultAnalyzer = (Analyzer) analyzerClass.newInstance();
		} catch (ClassCastException e) {
			throw new SearchException("Lucene analyzer does not implement " + Analyzer.class.getName() + ": "
					+ analyzerClassName, e);
		} catch (Exception e) {
			throw new SearchException("Failed to instantiate lucene analyzer with type " + analyzerClassName, e);
		}
		return defaultAnalyzer;
	}

	/**
	 * Initializes the Lucene similarity to use.
	 *
	 * @param cfg the search configuration.
	 * @return returns the default similarity class.
	 */
	private Similarity initSimilarity(SearchConfiguration cfg) {
		Class similarityClass;
		String similarityClassName = cfg.getProperty(Environment.SIMILARITY_CLASS);
		if (similarityClassName != null) {
			try {
				similarityClass = ReflectHelper.classForName(similarityClassName);
			} catch (Exception e) {
				throw new SearchException("Lucene Similarity class '" + similarityClassName + "' defined in property '"
						+ Environment.SIMILARITY_CLASS + "' could not be found.", e);
			}
		}
		else {
			similarityClass = null;
		}

		// Initialize similarity
		if ( similarityClass == null ) {
			return Similarity.getDefault();
		}
		else {
			Similarity defaultSimilarity;
			try {
				defaultSimilarity = (Similarity) similarityClass.newInstance();
			} catch (ClassCastException e) {
				throw new SearchException("Lucene similarity does not extend " + Similarity.class.getName() + ": "
						+ similarityClassName, e);
			} catch (Exception e) {
				throw new SearchException("Failed to instantiate lucene similarity with type " + similarityClassName, e);
			}
			return defaultSimilarity;
		}
	}

	public Analyzer getDefaultAnalyzer() {
		return defaultAnalyzer;
	}

	public Similarity getDefaultSimilarity() {
		return defaultSimilarity;
	}

	public Map<String, Analyzer> initLazyAnalyzers() {
		Map<String, Analyzer> initializedAnalyzers = new HashMap<String, Analyzer>( analyzerDefs.size() );

		for (DelegateNamedAnalyzer namedAnalyzer : lazyAnalyzers) {
			String name = namedAnalyzer.getName();
			if ( initializedAnalyzers.containsKey( name ) ) {
				namedAnalyzer.setDelegate( initializedAnalyzers.get( name ) );
			}
			else {
				if ( analyzerDefs.containsKey( name ) ) {
					final Analyzer analyzer = buildAnalyzer( analyzerDefs.get( name ) );
					namedAnalyzer.setDelegate( analyzer );
					initializedAnalyzers.put( name, analyzer );
				}
				else {
					throw new SearchException("Analyzer found with an unknown definition: " + name);
				}
			}
		}

		//initialize the remaining definitions
		for ( Map.Entry<String, AnalyzerDef> entry : analyzerDefs.entrySet() ) {
			if ( ! initializedAnalyzers.containsKey( entry.getKey() ) ) {
				final Analyzer analyzer = buildAnalyzer( entry.getValue() );
				initializedAnalyzers.put( entry.getKey(), analyzer );
			}
		}
		return Collections.unmodifiableMap( initializedAnalyzers );
	}

	private Analyzer buildAnalyzer(AnalyzerDef analyzerDef) {
		if ( ! solrPresent ) {
			throw new SearchException( "Use of @AnalyzerDef while Solr is not present in the classpath. Add apache-solr-analyzer.jar" );
		}
		// SolrAnalyzerBuilder references Solr classes.
		// InitContext should not (directly or indirectly) load a Solr class to avoid hard dependency
		// unless necessary
		// the curent mecanism (check sor class presence and call SolrAnalyzerBuilder if needed
		// seems to be sufficient on Apple VM (derived from Sun's
		// TODO check on other VMs and be ready for a more reflexive approach
		return SolrAnalyzerBuilder.buildAnalyzer( analyzerDef );
	}

	public boolean isJpaPresent() {
		return jpaPresent;
	}

	private boolean isPresent(String classname) {
		try {
			ReflectHelper.classForName( classname, InitContext.class );
			return true;
		}
		catch ( Exception e ) {
			return false;
		}
	}
}
