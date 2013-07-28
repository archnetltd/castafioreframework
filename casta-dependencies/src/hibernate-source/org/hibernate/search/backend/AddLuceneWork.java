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
 //$Id: AddLuceneWork.java 15637 2008-12-02 14:28:28Z hardy.ferentschik $
package org.hibernate.search.backend;

import java.io.Serializable;
import java.util.Map;

import org.apache.lucene.document.Document;

/**
 * @author Emmanuel Bernard
 */
public class AddLuceneWork extends LuceneWork implements Serializable {

	private static final long serialVersionUID = -2450349312813297371L;

	private final Map<String, String> fieldToAnalyzerMap;

	public AddLuceneWork(Serializable id, String idInString, Class entity, Document document) {
		this( id, idInString, entity, document, false );
	}

	public AddLuceneWork(Serializable id, String idInString, Class entity, Document document, boolean batch) {
		this( id, idInString, entity, document, null, batch );
	}

	public AddLuceneWork(Serializable id, String idInString, Class entity, Document document, Map<String, String> fieldToAnalyzerMap) {
		this( id, idInString, entity, document, fieldToAnalyzerMap, false );
	}

	public AddLuceneWork(Serializable id, String idInString, Class entity, Document document, Map<String, String> fieldToAnalyzerMap, boolean batch) {
		super( id, idInString, entity, document, batch );
		this.fieldToAnalyzerMap = fieldToAnalyzerMap;
	}

	public Map<String, String> getFieldToAnalyzerMap() {
		return fieldToAnalyzerMap;
	}

	@Override
	public <T> T getWorkDelegate(final WorkVisitor<T> visitor) {
		return visitor.getDelegate( this );
	}

}
