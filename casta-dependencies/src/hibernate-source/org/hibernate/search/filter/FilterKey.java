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
 // $Id: FilterKey.java 14878 2008-07-05 17:01:12Z epbernard $
package org.hibernate.search.filter;

/**
 * The key object must implement equals / hashcode so that 2 keys are equals if and only if
 * the given Filter types are the same and the set of parameters are the same.
 * <p/>
 * The FilterKey creator (ie the @Key method) does not have to inject <code>impl</code>
 * It will be done by Hibernate Search
 *
 * @author Emmanuel Bernard
 */
public abstract class FilterKey {
	 // FilterKey implementations do not have to be thread-safe as FilterCachingStrategy ensure
	 // a memory barrier between usages
	 //

	private Class impl;

	/**
	 * Represent the @FullTextFilterDef.impl class
	 */
	public Class getImpl() {
		return impl;
	}

	public void setImpl(Class impl) {
		this.impl = impl;
	}

	public abstract int hashCode();

	public abstract boolean equals(Object obj);
}
