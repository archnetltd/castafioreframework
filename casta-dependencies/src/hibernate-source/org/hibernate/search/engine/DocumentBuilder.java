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
 // $Id: DocumentBuilder.java 15607 2008-11-24 15:46:29Z hardy.ferentschik $
package org.hibernate.search.engine;

import org.hibernate.search.ProjectionConstants;

/**
 * Interface created to keep backwards compatibility.
 *
 * @author Hardy Ferentschik
 */
public interface DocumentBuilder {

	/**
	 * Lucene document field name containing the fully qualified classname of the indexed class.
	 *
	 */
	String CLASS_FIELDNAME = ProjectionConstants.OBJECT_CLASS;
}
