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
 //$Id: String2FieldBridgeAdaptor.java 14924 2008-07-11 16:40:35Z hardy.ferentschik $
package org.hibernate.search.bridge;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.hibernate.util.StringHelper;

/**
 * Bridge to use a StringBridge as a FieldBridge.
 *
 * @author Emmanuel Bernard
 */
public class String2FieldBridgeAdaptor implements FieldBridge {
	private final StringBridge stringBridge;

	public String2FieldBridgeAdaptor(StringBridge stringBridge) {
		this.stringBridge = stringBridge;
	}

	public void set(String name, Object value, Document document, LuceneOptions luceneOptions) {
		String indexedString = stringBridge.objectToString( value );
		//Do not add fields on empty strings, seems a sensible default in most situations
		//TODO if Store, probably also save empty ones
		if ( StringHelper.isNotEmpty( indexedString ) ) {
			Field field = new Field( name, indexedString, luceneOptions.getStore(), luceneOptions.getIndex(), luceneOptions.getTermVector() );
			field.setBoost( luceneOptions.getBoost() );
			document.add( field );
		}
	}

}
