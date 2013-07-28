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
 package org.hibernate.search.backend;

/**
 * A visitor delegate to manipulate a LuceneWork
 * needs to implement this interface.
 * This pattern enables any implementation to virtually add delegate
 * methods to the base LuceneWork without having to change them.
 * This contract however breaks if more subclasses of LuceneWork
 * are created, as a visitor must support all existing types.
 * 
 * @author Sanne Grinovero
 *
 * @param <T> used to force a return type of choice.
 */
public interface WorkVisitor<T> {

	T getDelegate(AddLuceneWork addLuceneWork);
	T getDelegate(DeleteLuceneWork deleteLuceneWork);
	T getDelegate(OptimizeLuceneWork optimizeLuceneWork);
	T getDelegate(PurgeAllLuceneWork purgeAllLuceneWork);

}
