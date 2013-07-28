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
 // $Id: Search.java 14992 2008-07-29 18:50:51Z epbernard $
package org.hibernate.search.jpa;

import javax.persistence.EntityManager;

import org.hibernate.search.jpa.impl.FullTextEntityManagerImpl;

/**
 * Helper class that should be used when building a FullTextEntityManager
 *
 * @author Emmanuel Bernard
 * @author Hardy Ferentschik
 */
public final class Search {
	
	private Search() {
	}

	/**
	 * Build a full text capable EntityManager
	 * The underlying EM implementation has to be Hibernate EntityManager
	 */
	public static FullTextEntityManager getFullTextEntityManager(EntityManager em) {
		if ( em instanceof FullTextEntityManagerImpl ) {
			return (FullTextEntityManager) em;
		}
		else {
			return new FullTextEntityManagerImpl(em);
		}
	}

	/**
	 * @deprecated As of release 3.1.0, replaced by {@link #getFullTextEntityManager}
	 */
	@Deprecated
	public static FullTextEntityManager createFullTextEntityManager(EntityManager em) {
		return getFullTextEntityManager(em);
	}
}