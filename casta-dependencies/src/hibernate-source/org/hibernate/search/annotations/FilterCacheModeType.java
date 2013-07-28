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
 package org.hibernate.search.annotations;

/**
 * Cache mode strategy for <code>FullTextFilterDef</code>s.
 *
 * @see FullTextFilterDef
 * @author Emmanuel Bernard
 */
public enum FilterCacheModeType {
	/**
	 * No filter instance and no result is cached by Hibernate Search.
	 * For every filter call, a new filter instance is created.
	 */
	NONE,

	/**
	 * The filter instance is cached by Hibernate Search and reused across
	 * concurrent <code>Filter.getDocIdSet()</code> calls.
	 * Results are not cached by Hibernate Search.
	 *
	 * @see org.apache.lucene.search.Filter#bits(org.apache.lucene.index.IndexReader)

	 */
	INSTANCE_ONLY,

	/**
	 * Both the filter instance and the <code>DocIdSet</code> results are cached.
	 * The filter instance is cached by Hibernate Search and reused across
	 * concurrent <code>Filter.getDocIdSet()</code> calls.
	 * <code>DocIdSet</code> results are cached per <code>IndexReader</code>.
	 *
	 * @see org.apache.lucene.search.Filter#bits(org.apache.lucene.index.IndexReader) 
	 */
	INSTANCE_AND_DOCIDSETRESULTS

}
