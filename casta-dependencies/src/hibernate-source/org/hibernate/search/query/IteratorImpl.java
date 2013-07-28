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
 //$Id: IteratorImpl.java 14713 2008-05-29 15:18:15Z sannegrinovero $
package org.hibernate.search.query;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.hibernate.search.engine.Loader;
import org.hibernate.search.engine.EntityInfo;

/**
 * @author Emmanuel Bernard
 */
//TODO load the next batch-size elements to benefit from batch-size 
public class IteratorImpl implements Iterator {

	private final List<EntityInfo> entityInfos;
	private int index = 0;
	private final int size;
	private Object next;
	private int nextObjectIndex = -1;
	private final Loader loader;

	public IteratorImpl(List<EntityInfo> entityInfos, Loader loader) {
		this.entityInfos = entityInfos;
		this.size = entityInfos.size();
		this.loader = loader;
	}

	//side effect is to set up next
	public boolean hasNext() {
		if ( nextObjectIndex == index ) return next != null;
		next = null;
		nextObjectIndex = -1;
		do {
			if ( index >= size ) {
				nextObjectIndex = index;
				next = null;
				return false;
			}
			next = loader.load( entityInfos.get( index ) );
			if ( next == null ) {
				index++;
			}
			else {
				nextObjectIndex = index;
			}
		}
		while ( next == null );
		return true;
	}

	public Object next() {
		//hasNext() has side effect
		if ( !hasNext() ) throw new NoSuchElementException( "Out of boundaries" );
		index++;
		return next;
	}

	public void remove() {
		//TODO this is theoretically doable
		throw new UnsupportedOperationException( "Cannot remove from a lucene query iterator" );
	}
}
