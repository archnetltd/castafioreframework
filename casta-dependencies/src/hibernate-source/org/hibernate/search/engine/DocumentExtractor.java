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
 //$Id: DocumentExtractor.java 15606 2008-11-24 11:39:52Z hardy.ferentschik $
package org.hibernate.search.engine;

import java.io.IOException;
import java.io.Serializable;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.FieldSelectorResult;
import org.apache.lucene.document.MapFieldSelector;
import org.apache.lucene.document.FieldSelector;

import org.hibernate.search.ProjectionConstants;
import org.hibernate.search.SearchException;
import org.hibernate.search.query.QueryHits;

/**
 * Helper class to extract <code>EntityInfo</code>s out of the <code>QueryHits</code>.
 *
 * @author Emmanuel Bernard
 * @author John Griffin
 * @author Hardy Ferentschik
 */
public class DocumentExtractor {
	private final SearchFactoryImplementor searchFactoryImplementor;
	private final String[] projection;
	private final QueryHits queryHits;
	private FieldSelector fieldSelector;
	private boolean allowFieldSelection;

	public DocumentExtractor(QueryHits queryHits, SearchFactoryImplementor searchFactoryImplementor, String[] projection, Set<String> idFieldNames, boolean allowFieldSelection) {
		this.searchFactoryImplementor = searchFactoryImplementor;
		this.projection = projection;
		this.queryHits = queryHits;
		this.allowFieldSelection = allowFieldSelection;
		initFieldSelection( projection, idFieldNames );
	}

	private void initFieldSelection(String[] projection, Set<String> idFieldNames) {
		// if we need to project DOCUMENT do not use fieldSelector as the user might want anything
		int projectionSize = projection != null && projection.length != 0 ? projection.length : 0;
		if ( projectionSize != 0 ) {
			for ( String property : projection ) {
				if ( ProjectionConstants.DOCUMENT.equals( property ) ) {
					allowFieldSelection = false;
					return;
				}
			}
		}

		// set up the field selector. CLASS_FIELDNAME and id fields are needed on top of any projected fields
		Map<String, FieldSelectorResult> fields = new HashMap<String, FieldSelectorResult>( 1 + idFieldNames.size() + projectionSize );
		fields.put( DocumentBuilder.CLASS_FIELDNAME, FieldSelectorResult.LOAD );
		for ( String idFieldName : idFieldNames ) {
			fields.put( idFieldName, FieldSelectorResult.LOAD );
		}
		if ( projectionSize != 0 ) {
			for ( String projectedField : projection ) {
				fields.put( projectedField, FieldSelectorResult.LOAD );
			}
		}
		this.fieldSelector = new MapFieldSelector( fields );
	}

	private EntityInfo extract(Document document) {
		Class clazz = DocumentBuilderIndexedEntity.getDocumentClass( document );
		Serializable id = DocumentBuilderIndexedEntity.getDocumentId( searchFactoryImplementor, clazz, document );
		Object[] projected = null;
		if ( projection != null && projection.length > 0 ) {
			projected = DocumentBuilderIndexedEntity.getDocumentFields(
					searchFactoryImplementor, clazz, document, projection
			);
		}
		return new EntityInfo( clazz, id, projected );
	}

	public EntityInfo extract(int index) throws IOException {
		Document doc;
		if ( allowFieldSelection ) {
			doc = queryHits.doc( index, fieldSelector );
		}
		else {
			doc = queryHits.doc( index );
		}

		EntityInfo entityInfo = extract( doc );
		Object[] eip = entityInfo.projection;

		// TODO - if we are only looking for score (unlikely), avoid accessing doc (lazy load)
		if ( eip != null && eip.length > 0 ) {
			for ( int x = 0; x < projection.length; x++ ) {
				if ( ProjectionConstants.SCORE.equals( projection[x] ) ) {
					eip[x] = queryHits.score( index );
				}
				else if ( ProjectionConstants.ID.equals( projection[x] ) ) {
					eip[x] = entityInfo.id;
				}
				else if ( ProjectionConstants.DOCUMENT.equals( projection[x] ) ) {
					eip[x] = doc;
				}
				else if ( ProjectionConstants.DOCUMENT_ID.equals( projection[x] ) ) {
					eip[x] = queryHits.docId( index );
				}
				else if ( ProjectionConstants.BOOST.equals( projection[x] ) ) {
					eip[x] = doc.getBoost();
				}
				else if ( ProjectionConstants.EXPLANATION.equals( projection[x] ) ) {
					eip[x] = queryHits.explain( index );
				}
				else if ( ProjectionConstants.OBJECT_CLASS.equals( projection[x] ) ) {
						eip[x] = entityInfo.clazz;
				}
				else if ( ProjectionConstants.THIS.equals( projection[x] ) ) {
					//THIS could be projected more than once
					//THIS loading delayed to the Loader phase
					entityInfo.indexesOfThis.add( x );
				}
			}
		}
		return entityInfo;
	}
}
