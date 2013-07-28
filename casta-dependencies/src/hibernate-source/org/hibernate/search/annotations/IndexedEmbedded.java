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
 //$Id: IndexedEmbedded.java 15375 2008-10-23 00:04:14Z epbernard $
package org.hibernate.search.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Documented;

@Retention( RetentionPolicy.RUNTIME )
@Target( { ElementType.FIELD, ElementType.METHOD } )
@Documented
/**
 * Specifies that an association (@*To*, @Embedded, @CollectionOfEmbedded) is to be indexed
 * in the root entity index
 * It allows queries involving associated objects restrictions
 */
public @interface IndexedEmbedded {
	/**
	 * Field name prefix
	 * Default to 'propertyname.'
	 */
	String prefix() default ".";

	/**
	 * Stop indexing embedded elements when depth is reached
	 * depth=1 means the associated element is index, but not its embedded elements
	 * Default: infinite (an exception will be raised if a class circular reference occurs while infinite is chosen)
	 */
	int depth() default Integer.MAX_VALUE;

	/**
	 * Overrides the type of an association. If a collection, overrides the type of the collection generics
	 */
	Class<?> targetElement() default void.class;
}
