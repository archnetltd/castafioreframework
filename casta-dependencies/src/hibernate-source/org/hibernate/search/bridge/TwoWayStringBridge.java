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
 //$Id: TwoWayStringBridge.java 15574 2008-11-17 11:23:26Z hardy.ferentschik $
package org.hibernate.search.bridge;

/**
 * <code>StringBridge<code> allowing a translation from the string representation back to the <code>Object</code>.
 * <code>objectToString( stringToObject( string ) )</code> and <code>stringToObject( objectToString( object ) )</code>
 * should be "idempotent". More precisely:
 * <ul>
 * <li><code>objectToString( stringToObject( string ) ).equals(string)</code>, for non <code>null</code> string.</li>
 * <li><code>stringToObject( objectToString( object ) ).equals(object)</code>, for non <code>null</code> object. </li>
 * </ul>
 * 
 * As for all Bridges implementations must be threasafe.
 * 
 * @author Emmanuel Bernard
 */
public interface TwoWayStringBridge extends StringBridge {

	/**
	 * Convert the index string representation to an object.
	 *
	 * @param stringValue The index value.
	 * @return Takes the string representation from the Lucene index and transforms it back into the original
	 * <code>Object</code>.
	 */
	Object stringToObject(String stringValue);
}
