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
 // $Id: MRUFilterCachingStrategy.java 14772 2008-06-17 13:03:13Z sannegrinovero $
package org.hibernate.search.filter;

import java.util.Properties;

import org.apache.lucene.search.Filter;
import org.hibernate.search.Environment;
import org.hibernate.search.backend.configuration.ConfigurationParseHelper;
import org.hibernate.util.SoftLimitMRUCache;

/**
 * Keep the most recently used Filters in the cache
 * The cache is at least as big as <code>hibernate.search.filter.cache_strategy.size</code>
 * Above this limit, Filters are kept as soft references
 *
 * @author Emmanuel Bernard
 */
public class MRUFilterCachingStrategy implements FilterCachingStrategy {
	private static final int DEFAULT_SIZE = 128;
	private SoftLimitMRUCache cache;
	private static final String SIZE = Environment.FILTER_CACHING_STRATEGY + ".size";

	public void initialize(Properties properties) {
		int size = ConfigurationParseHelper.getIntValue( properties, SIZE, DEFAULT_SIZE );
		cache = new SoftLimitMRUCache( size );
	}

	public Filter getCachedFilter(FilterKey key) {
		return (Filter) cache.get( key );
	}

	public void addCachedFilter(FilterKey key, Filter filter) {
		cache.put( key, filter );
	}
}
