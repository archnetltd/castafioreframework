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
 //$Id: Index.java 11625 2007-06-04 16:21:54Z epbernard $
package org.hibernate.search.annotations;

/**
 * Defines how an Field should be indexed
 */
public enum Index {
	/**
	 * Do not index the field value. This field can thus not be searched,
	 * but one can still access its contents provided it is
	 * {@link Store stored}.
	 */
	NO,
	/**
	 * Index the field's value so it can be searched. An Analyzer will be used
	 * to tokenize and possibly further normalize the text before its
	 * terms will be stored in the index. This is useful for common text.
	 */
	TOKENIZED,
	/**
	 * Index the field's value without using an Analyzer, so it can be searched.
	 * As no analyzer is used the value will be stored as a single term. This is
	 * useful for unique Ids like product numbers.
	 */
	UN_TOKENIZED,
	/**
	 * Index the field's value without an Analyzer, and disable
	 * the storing of norms.  No norms means that index-time boosting
	 * and field length normalization will be disabled.  The benefit is
	 * less memory usage as norms take up one byte per indexed field
	 * for every document in the index.
	 */
	NO_NORMS
}
