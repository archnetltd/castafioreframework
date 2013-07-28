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
 // $Id: EmptyDocIdBitSet.java 15628 2008-11-29 14:06:12Z sannegrinovero $
package org.hibernate.search.filter;

import java.io.Serializable;

import org.apache.lucene.search.DocIdSet;
import org.apache.lucene.search.DocIdSetIterator;

/**
 * A DocIdSet which is always empty.
 * Stateless and ThreadSafe.
 * 
 * @author Sanne Grinovero
 */
public final class EmptyDocIdBitSet extends DocIdSet implements Serializable {

	private static final long serialVersionUID = 6429929383767238322L;

	public static final DocIdSet instance = new EmptyDocIdBitSet();
	
	private static final DocIdSetIterator iterator = new EmptyDocIdSetIterator();
	
	private EmptyDocIdBitSet(){
		// is singleton
	}

	@Override
	public final DocIdSetIterator iterator() {
		return iterator;
	}

	/**
	 * implements a DocIdSetIterator for an empty DocIdSet
	 * As it is empty it also is stateless and so it can be reused.
	 */
	private static final class EmptyDocIdSetIterator extends DocIdSetIterator {

		@Override
		public final int doc() {
			throw new IllegalStateException( "Should never be called" );
		}

		@Override
		public final boolean next() {
			return false;
		}

		@Override
		public final boolean skipTo(int target) {
			return false;
		}

	}
	
}
