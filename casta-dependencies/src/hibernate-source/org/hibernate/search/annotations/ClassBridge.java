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
 // $Id: ClassBridge.java 14713 2008-05-29 15:18:15Z sannegrinovero $
package org.hibernate.search.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation allows a user to apply an implementation
 * class to a Lucene document to manipulate it in any way
 * the user sees fit.
 *
 * @author John Griffin
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.TYPE )
@Documented
public @interface ClassBridge {
	/**
	 * Field name, default to the JavaBean property name.
	 */
	String name() default "";

	/**
	 * Should the value be stored in the document.
	 * defaults to no.
	 */
	Store store() default Store.NO;

	/**
	 * Define an analyzer for the field, default to
	 * the inherited analyzer.
	 */
	Analyzer analyzer() default @Analyzer;

	/**
	 * Defines how the Field should be indexed
	 * defaults to tokenized.
	 */
	Index index() default Index.TOKENIZED;

	/**
	 * Define term vector storage requirements,
	 * default to NO.
	 */
	TermVector termVector() default TermVector.NO;

	/**
	 * A float value of the amount of Lucene defined
	 * boost to apply to a field.
	 */
	Boost boost() default @Boost(value=1.0F);

	/**
	 * User supplied class to manipulate document in
	 * whatever mysterious ways they wish to.
	 */
	public Class impl() default void.class;

	/**
	 * Array of fields to work with. The impl class
	 * above will work on these fields.
	 */
	public Parameter[] params() default {};

}