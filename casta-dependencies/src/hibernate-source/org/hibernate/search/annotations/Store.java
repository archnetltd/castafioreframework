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
 //$Id: Store.java 15154 2008-09-04 16:05:47Z epbernard $
package org.hibernate.search.annotations;

/**
 * Whether or not the value is stored in the document
 *
 * @author Emmanuel Bernard
 */
public enum Store {
	/** does not store the value in the index */
	NO,
	/** stores the value in the index */
	YES,
	/** stores the value in the index in a compressed form */
	COMPRESS
}
