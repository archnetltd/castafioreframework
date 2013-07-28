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
 //$Id: StringBridge.java 15574 2008-11-17 11:23:26Z hardy.ferentschik $
package org.hibernate.search.bridge;

/**
 * Transform an object into a string representation.
 * 
 * All implementations are required to be threadsafe.
 * Usually this is easily achieved avoiding the usage
 * of class fields, unless they are either immutable
 * or needed to store parameters.
 *
 * @author Emmanuel Bernard
 */
public interface StringBridge {
	
	/**
	 * Converts the object representation to a string.
	 *
	 * @param object The object to transform into a string representation.
	 * @return String representation of the given object to be stored in Lucene index. The return string must not be
	 * <code>null</code>. It can be empty though.
	 */
	String objectToString(Object object);
}
