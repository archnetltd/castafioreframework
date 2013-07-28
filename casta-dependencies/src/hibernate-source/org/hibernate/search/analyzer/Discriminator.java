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
 // $Id: Discriminator.java 15637 2008-12-02 14:28:28Z hardy.ferentschik $
package org.hibernate.search.analyzer;

/**
 * Allows to choose a by name defines analyzer at runtime.
 *
 * @author Hardy Ferentschik
 */
public interface Discriminator {

	/**
	 * Allows to specify the analyzer to be used for the given field based on the specified entity state.
	 *
	 * @param value The value of the field the <code>@AnalyzerDiscriminator</code> annotation was placed on. <code>null</code>
	 * if the annotation was placed on class level.
	 * @param entity The entity to be indexed.
	 * @param field The document field.
	 * @return The name of a defined analyzer to be used for the specified <code>field</code> or <code>null</code> if the
	 * default analyzer for this field should be used.
	 * @see org.hibernate.search.annotations.AnalyzerDef
	 */
	String getAnanyzerDefinitionName(Object value, Object entity, String field);
}
