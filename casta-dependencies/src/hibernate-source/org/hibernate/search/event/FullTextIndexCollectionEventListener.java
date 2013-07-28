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
 // $Id: FullTextIndexCollectionEventListener.java 14954 2008-07-17 20:43:10Z sannegrinovero $
package org.hibernate.search.event;

import org.hibernate.event.PostCollectionRecreateEvent;
import org.hibernate.event.PostCollectionRecreateEventListener;
import org.hibernate.event.PostCollectionRemoveEvent;
import org.hibernate.event.PostCollectionRemoveEventListener;
import org.hibernate.event.PostCollectionUpdateEvent;
import org.hibernate.event.PostCollectionUpdateEventListener;

/**
 * @author Emmanuel Bernard
 * @deprecated As of release 3.1.0, replaced by {@link FullTextIndexEventListener}
 */
@SuppressWarnings("serial")
@Deprecated 
public class FullTextIndexCollectionEventListener extends FullTextIndexEventListener
		implements PostCollectionRecreateEventListener,
		PostCollectionRemoveEventListener,
		PostCollectionUpdateEventListener {

	/**
	 * @deprecated As of release 3.1.0, replaced by {@link FullTextIndexEventListener#onPostRecreateCollection(PostCollectionRecreateEvent)}
	 */
	@Deprecated 	
	public void onPostRecreateCollection(PostCollectionRecreateEvent event) {
		processCollectionEvent( event );
	}

	/**
	 * @deprecated As of release 3.1.0, replaced by {@link FullTextIndexEventListener#onPostRemoveCollection(PostCollectionRemoveEvent)}
	 */
	@Deprecated 	
	public void onPostRemoveCollection(PostCollectionRemoveEvent event) {
		processCollectionEvent( event );
	}

	/**
	 * @deprecated As of release 3.1.0, replaced by {@link FullTextIndexEventListener#onPostUpdateCollection(PostCollectionUpdateEvent)}
	 */
	@Deprecated 	
	public void onPostUpdateCollection(PostCollectionUpdateEvent event) {
		processCollectionEvent( event );
	}
}
