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
 //$Id: FieldBridge.java 15528 2008-11-06 23:11:06Z sannegrinovero $
package org.hibernate.search.bridge;

import org.apache.lucene.document.Document;

/**
 * Link between a java property and a Lucene Document
 * Usually a Java property will be linked to a Document Field.
 * 
 * All implementations need to be threadsafe.
 *
 * @author Emmanuel Bernard
 */
public interface FieldBridge {

	/**
	 * Manipulate the document to index the given value.
	 * A common implementation is to add a Field <code>name</code> to the given document following
	 * the parameters (<code>store</code>, <code>index</code>, <code>boost</code>) if the
	 * <code>value</code> is not null
	 * @param luceneOptions Contains the parameters used for adding <code>value</code> to 
	 * the Lucene <code>document</code>.
	 */
	void set(String name, Object value, Document document, LuceneOptions luceneOptions);
}
