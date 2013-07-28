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
 //$Id: ContextHelper.java 15561 2008-11-13 15:39:23Z hardy.ferentschik $
package org.hibernate.search.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.event.PostInsertEventListener;
import org.hibernate.search.event.FullTextIndexEventListener;
import org.hibernate.search.engine.SearchFactoryImplementor;

/**
 * @author Emmanuel Bernard
 * @deprecated Use {@link org.hibernate.search.FullTextSession#getSearchFactory()} instead.
 */
public abstract class ContextHelper {

	public static SearchFactoryImplementor getSearchFactory(Session session) {
		return getSearchFactoryBySFI( (SessionImplementor) session );
	}

	
	public static SearchFactoryImplementor getSearchFactoryBySFI(SessionImplementor session) {
		PostInsertEventListener[] listeners = session.getListeners().getPostInsertEventListeners();
		FullTextIndexEventListener listener = null;
		//FIXME this sucks since we mandante the event listener use
		for ( PostInsertEventListener candidate : listeners ) {
			if ( candidate instanceof FullTextIndexEventListener ) {
				listener = (FullTextIndexEventListener) candidate;
				break;
			}
		}
		if ( listener == null ) throw new HibernateException(
				"Hibernate Search Event listeners not configured, please check the reference documentation and the " +
						"application's hibernate.cfg.xml" );
		return listener.getSearchFactoryImplementor();
	}
}
