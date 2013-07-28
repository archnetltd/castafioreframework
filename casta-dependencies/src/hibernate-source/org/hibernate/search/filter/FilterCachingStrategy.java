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
 // $Id: FilterCachingStrategy.java 14878 2008-07-05 17:01:12Z epbernard $
package org.hibernate.search.filter;

import java.util.Properties;

import org.apache.lucene.search.Filter;

/**
 * Defines the caching filter strategy
 * implementations of getCachedFilter and addCachedFilter must be thread-safe 
 *
 * @author Emmanuel Bernard
 */
public interface FilterCachingStrategy {
	/**
	 * initialize the strategy from the properties
	 * The Properties must not be changed
	 */
	void initialize(Properties properties);
	/**
	 * Retrieve the cached filter for a given key or null if not cached
	 */
	Filter getCachedFilter(FilterKey key);

	/**
	 * Propose a candidate filter for caching
	 */
	void addCachedFilter(FilterKey key, Filter filter);
}
