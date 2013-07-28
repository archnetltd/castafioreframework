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
 //$Id: Search.java 14845 2008-07-03 12:41:06Z hardy.ferentschik $
package org.hibernate.search;

import org.hibernate.Session;
import org.hibernate.search.impl.FullTextSessionImpl;

/**
 * Helper class to get a FullTextSession out of a regular session.
 * 
 * @author Emmanuel Bernard
 * @author Hardy Ferentschik
 */
public final class Search {

	private Search() {
	}

	public static FullTextSession getFullTextSession(Session session) {
		if (session instanceof FullTextSessionImpl) {
			return (FullTextSession) session;
		}
		else {
			return new FullTextSessionImpl(session);
		}
	}
	
	/**
	 * @deprecated As of release 3.1.0, replaced by {@link #getFullTextSession(Session)}
	 */
	@Deprecated 
	public static FullTextSession createFullTextSession(Session session) {
		return getFullTextSession(session);
	}
}