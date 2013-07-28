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
 //$Id: TwoWayFieldBridge.java 15560 2008-11-13 15:38:44Z hardy.ferentschik $
package org.hibernate.search.bridge;

import org.apache.lucene.document.Document;

/**
 * A <code>FieldBridge</code> able to convert the index representation back into an object without losing information.
 * Any bridge expected to process a document id should implement this interface.
 *
 * @author Emmanuel Bernard
 */
// FIXME rework the interface inheritance there are some common concepts with StringBridge
public interface TwoWayFieldBridge extends FieldBridge {
	/**
	 * Build the element object from the <code>Document</code>
	 *
	 * @param name field name
	 * @param document document
	 *
	 * @return The return value is the entity property value.
	 */
	Object get(String name, Document document);

	/**
	 * Convert the object representation to a string.
	 *
	 * @param object The object to index.
	 * @return string (index) representationT of the specified object. Must not be <code>null</code>, but
	 *         can be empty.
	 */
	String objectToString(Object object);
}
