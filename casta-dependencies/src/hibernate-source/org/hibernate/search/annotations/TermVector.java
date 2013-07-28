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
 // $Id:$
package org.hibernate.search.annotations;

/**
 * Defines the term vector storing strategy
 *
 * @author John Griffin
 */
public enum TermVector {
	/**
	 * Store term vectors.
	 */
	YES,
	/**
	 * Do not store term vectors.
	 */
	NO,
	/**
	 * Store the term vector + Token offset information
	 */
	WITH_OFFSETS,
	/**
	 * Store the term vector + token position information
	 */
	WITH_POSITIONS,
	/**
	 * Store the term vector + Token position and offset information
	 */
	WITH_POSITION_OFFSETS
}

