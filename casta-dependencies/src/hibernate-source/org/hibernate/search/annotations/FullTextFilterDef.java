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
 // $Id: FullTextFilterDef.java 15625 2008-11-27 13:32:12Z hardy.ferentschik $
package org.hibernate.search.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Documented;

/**
 * Defines a FullTextFilter that can be optionally applied to
 * every FullText Queries
 * While not related to a specific indexed entity, the annotation has to be set on one of them
 *
 * @author Emmanuel Bernard
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( { ElementType.TYPE } )
@Documented
public @interface FullTextFilterDef {
	/**
	 * @return the filter name. Must be unique across all mappings for a given persistence unit
	 */
	String name();

	/**
	 * Either implements {@link org.apache.lucene.search.Filter}
	 * or contains a <code>@Factory</code> method returning one.
	 * The generated <code>Filter</code> must be thread-safe.
	 *
	 * If the filter accept parameters, an <code>@Key</code> method must be present as well.
	 *
	 * @return a class which either implements <code>Filter</code> directly or contains a method annotated with
	 * <code>@Factory</code>.
	 *
	 */
	Class<?> impl();

	/**
	 * @return The cache mode for the filter. Default to instance and results caching
	 */
	FilterCacheModeType cache() default FilterCacheModeType.INSTANCE_AND_DOCIDSETRESULTS;
}
