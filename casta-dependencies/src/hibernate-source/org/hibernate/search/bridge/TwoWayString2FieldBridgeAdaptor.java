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
 //$Id: TwoWayString2FieldBridgeAdaptor.java 14713 2008-05-29 15:18:15Z sannegrinovero $
package org.hibernate.search.bridge;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

/**
 * Bridge to use a TwoWayStringBridge as a TwoWayFieldBridge
 *
 * @author Emmanuel Bernard
 */
//TODO use Generics to avoid double declaration of stringBridge 
public class TwoWayString2FieldBridgeAdaptor extends String2FieldBridgeAdaptor implements TwoWayFieldBridge {

	private final TwoWayStringBridge stringBridge;

	public TwoWayString2FieldBridgeAdaptor(TwoWayStringBridge stringBridge) {
		super( stringBridge );
		this.stringBridge = stringBridge;
	}

	public String objectToString(Object object) {
		return stringBridge.objectToString( object );
	}

	public Object get(String name, Document document) {
		Field field = document.getField( name );
		if (field == null) {
			return stringBridge.stringToObject( null );
		}
		else {
			return stringBridge.stringToObject( field.stringValue() );
		}
	}
}
