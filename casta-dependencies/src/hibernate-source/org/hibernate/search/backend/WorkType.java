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
 //$Id: WorkType.java 15376 2008-10-23 02:15:50Z epbernard $
package org.hibernate.search.backend;

/**
 * Enumeration of the different types of Lucene work. This enumeration is used to specify the type
 * of index operation to be executed. 
 * 
 * @author Emmanuel Bernard
 * @author Hardy Ferentschik
 * @author John Griffin
 */
public enum WorkType {
	ADD(true),
	UPDATE(true),
	DELETE(false),
	COLLECTION(true),
	/**
	 * Used to remove a specific instance
	 * of a class from an index.
	 */
	PURGE(false),
	/**
	 * Used to remove all instances of a
	 * class from an index.
	 */
	PURGE_ALL(false),
	
	/**
	 * This type is used for batch indexing.
	 */
	INDEX(true);

	private final boolean searchForContainers;

	private WorkType(boolean searchForContainers) {
		this.searchForContainers = searchForContainers;
	}

	/**
	 * When references are changed, either null or another one, we expect dirty checking to be triggered (both sides
	 * have to be updated)
	 * When the internal object is changed, we apply the {Add|Update}Work on containedIns
	 */
	public boolean searchForContainers() {
		return this.searchForContainers;
	}
}
