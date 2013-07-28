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
 // $Id: AnalyzerDef.java 15585 2008-11-18 15:16:27Z hardy.ferentschik $
package org.hibernate.search.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Documented;

/**
 * Reusable analyzer definition.
 * An analyzer definition defines:
 * <ul>
 * <li>one tokenizer</li>
 * </li>optionally one or more filters</li>
 * </ul>
 * Filters are applied in the order they are defined.
 * <p/>
 * Reuses the Solr Tokenizer and Filter architecture.
 *
 * @author Emmanuel Bernard
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.FIELD, ElementType.METHOD })
@Documented
public @interface AnalyzerDef {
	/**
	 * @return Reference name to be used on {#org.hibernate.search.annotations.Analyzer}
	 */
	String name();

	/**
	 * @return Tokenizer used.
	 */
	TokenizerDef tokenizer();

	/**
	 * @return Filters used. The filters are applied in the defined order
	 */
	TokenFilterDef[] filters() default { };
}
