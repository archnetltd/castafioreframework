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
 //$Id: Field.java 15389 2008-10-24 17:42:28Z epbernard $
/**
 * JavaDoc copy/pastle from the Apache Lucene project
 * Available under the ASL 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package org.hibernate.search.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Mark a property as indexable
 *
 * @author Emmanuel Bernard
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( { ElementType.METHOD, ElementType.FIELD } )
@Documented
public @interface Field {
	/**
	 * Field name, default to the JavaBean property name
	 */
	String name() default "";

	/**
	 * Should the value be stored in the document
	 * defaults to no.
	 */
	Store store() default Store.NO;

	/**
	 * Defines how the Field should be indexed
	 * defaults to tokenized
	 */
	Index index() default Index.TOKENIZED;

	/**
	 * Define term vector storage requirements,
	 * default to NO.
	 */
	TermVector termVector() default TermVector.NO;

	/**
	 * Define an analyzer for the field, default to
	 * the inherited analyzer
	 */
	Analyzer analyzer() default @Analyzer;


	/**
	 * Field bridge used. Default is autowired.
	 */
	Boost boost() default @Boost( value = 1.0F );

	/**
	 * Field bridge used. Default is autowired.
	 */
	FieldBridge bridge() default @FieldBridge;
}
