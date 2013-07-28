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
 package org.hibernate.search.backend.impl.lucene;

/**
 * Constants to make the LuceneWorkDelegates advertise the type
 * of resource they are going to need to perform the work.
 * 
 * NEEDS_INDEXREADER is missing to make sure there always is an optimal
 * solution, as some operations can be done both through an IndexReader
 * and an IndexWriter, but as of Lucene 2.4 there are no operations which
 * can't be done using an IndexWriter.
 * 
 * @author Sanne Grinovero
 */
public enum IndexInteractionType {
	
	/**
	 * The workType needs an IndexWriter.
	 */
	NEEDS_INDEXWRITER,
	/**
	 * An IndexWriter should work best but it's possible
	 * to use an IndexReader instead.
	 */
	PREFER_INDEXWRITER,
	/**
	 * An IndexReader should work best but it's possible
	 * to use an IndexWriter instead.
	 */
	PREFER_INDEXREADER

}
