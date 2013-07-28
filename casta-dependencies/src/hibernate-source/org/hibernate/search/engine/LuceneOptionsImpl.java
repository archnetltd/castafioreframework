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
 // $Id: LuceneOptionsImpl.java 15292 2008-10-08 20:03:23Z epbernard $
package org.hibernate.search.engine;

import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.Field.TermVector;

import org.hibernate.search.bridge.LuceneOptions;

/**
 * A wrapper class for Lucene parameters needed for indexing.
 * This is a package level class
 *  
 * @author Hardy Ferentschik
 */
class LuceneOptionsImpl implements LuceneOptions {
	private final Store store;
	private final Index index;
	private final TermVector termVector;
	private final Float boost;

	public LuceneOptionsImpl(Store store, Index index, TermVector termVector, Float boost) {
		this.store = store;
		this.index = index;
		this.termVector = termVector;
		this.boost = boost;
	}

	public Store getStore() {
		return store;
	}

	public Index getIndex() {
		return index;
	}

	public TermVector getTermVector() {
		return termVector;
	}

	/**
	 * @return the boost value. If <code>boost == null</code>, the default boost value
	 * 1.0 is returned.
	 */
	public Float getBoost() {
		if ( boost != null ) {
			return boost;
		} else {
			return 1.0f;
		}
	}
}