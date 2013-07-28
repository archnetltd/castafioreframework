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
 package org.hibernate.search.backend.impl.lucene.works;

import java.io.IOException;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.Similarity;
import org.slf4j.Logger;

import org.hibernate.search.SearchException;
import org.hibernate.search.backend.AddLuceneWork;
import org.hibernate.search.backend.LuceneWork;
import org.hibernate.search.backend.Workspace;
import org.hibernate.search.backend.impl.lucene.IndexInteractionType;
import org.hibernate.search.engine.DocumentBuilderIndexedEntity;
import org.hibernate.search.util.LoggerFactory;
import org.hibernate.search.util.ScopedAnalyzer;

/**
 * Stateless implementation that performs a <code>AddLuceneWork</code>.
 *
 * @author Emmanuel Bernard
 * @author Hardy Ferentschik
 * @author John Griffin
 * @author Sanne Grinovero
 * @see LuceneWorkVisitor
 * @see LuceneWorkDelegate
 */
class AddWorkDelegate implements LuceneWorkDelegate {

	private static final Logger log = LoggerFactory.make();

	private final Workspace workspace;

	AddWorkDelegate(Workspace workspace) {
		this.workspace = workspace;
	}

	public IndexInteractionType getIndexInteractionType() {
		return IndexInteractionType.NEEDS_INDEXWRITER;
	}

	public void performWork(LuceneWork work, IndexWriter writer) {
		@SuppressWarnings("unchecked")
		DocumentBuilderIndexedEntity documentBuilder = workspace.getDocumentBuilder( work.getEntityClass() );
		Map<String, String> fieldToAnalyzerMap = ( ( AddLuceneWork ) work ).getFieldToAnalyzerMap();
		ScopedAnalyzer analyzer = ( ScopedAnalyzer ) documentBuilder.getAnalyzer();
		analyzer = updateAnalyzerMappings( analyzer, fieldToAnalyzerMap, workspace );
		Similarity similarity = documentBuilder.getSimilarity();
		if ( log.isTraceEnabled() ) {
			log.trace(
					"add to Lucene index: {}#{}:{}",
					new Object[] { work.getEntityClass(), work.getId(), work.getDocument() }
			);
		}
		try {
			//TODO the next two operations should be atomic to enable concurrent usage of IndexWriter
			// make a wrapping Similarity based on ThreadLocals? or having it autoselect implementation basing on entity?
			writer.setSimilarity( similarity );
			writer.addDocument( work.getDocument(), analyzer );
			workspace.incrementModificationCounter( 1 );
		}
		catch ( IOException e ) {
			throw new SearchException(
					"Unable to add to Lucene index: "
							+ work.getEntityClass() + "#" + work.getId(), e
			);
		}
	}

	/**
	 * Allows to override the otherwise static field to analyzer mapping in <code>scopedAnalyzer</code>.
	 *
	 * @param scopedAnalyzer The scoped analyzer created at startup time.
	 * @param fieldToAnalyzerMap A map of <code>Document</code> field names for analyzer names. This map gets creates
	 * when the Lucene <code>Document</code> gets created and uses the state of the entiy to index to determine analyzers
	 * dynamically at index time.
	 * @param workspace The current workspace.
	 * @return <code>scopedAnalyzer</code> in case <code>fieldToAnalyzerMap</code> is <code>null</code> or empty. Otherwise
	 * a clone of <code>scopedAnalyzer</code> is created where the analyzers get overriden according to <code>fieldToAnalyzerMap</code>.
	 */
	private ScopedAnalyzer updateAnalyzerMappings(ScopedAnalyzer scopedAnalyzer, Map<String, String> fieldToAnalyzerMap, Workspace workspace) {
		// for backwards compatability
		if ( fieldToAnalyzerMap == null || fieldToAnalyzerMap.isEmpty() ) {
			return scopedAnalyzer;
		}

		ScopedAnalyzer analyzerClone = scopedAnalyzer.clone();
		for ( Map.Entry<String, String> entry : fieldToAnalyzerMap.entrySet() ) {
			Analyzer analyzer = workspace.getAnalyzer( entry.getValue() );
			if ( analyzer == null ) {
				log.warn( "Unable to retrieve named analyzer: " + entry.getValue() );
			}
			else {
				analyzerClone.addScopedAnalyzer( entry.getKey(), analyzer );
			}
		}
		return analyzerClone;
	}

	public void performWork(LuceneWork work, IndexReader reader) {
		throw new UnsupportedOperationException();
	}
}
