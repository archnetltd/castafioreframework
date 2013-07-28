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
 package org.hibernate.search.backend.impl.lucene.works;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.hibernate.search.backend.LuceneWork;
import org.hibernate.search.backend.impl.lucene.IndexInteractionType;

/**
 * @author Sanne Grinovero
 */
public interface LuceneWorkDelegate {
	
	/**
	 * @return the IndexInteractionType needed to accomplish this work (reader or writer)
	 * 	or have a chance to express any preference for performance optimizations.
	 */
	IndexInteractionType getIndexInteractionType();

	/**
	 * Will perform work on an IndexWriter.
	 * @param work the LuceneWork to apply to the IndexWriter.
	 * @param writer the IndexWriter to use.
	 * @throws UnsupportedOperationException when the work is not compatible with an IndexWriter.
	 */
	void performWork(LuceneWork work, IndexWriter writer);
	
	/**
	 * Will perform this work on an IndexReader.
	 * @param work the LuceneWork to apply to the IndexReader.
	 * @param reader the IndexReader to use.
	 * @throws UnsupportedOperationException when the work is not compatible with an IndexReader.
	 */
	void performWork(LuceneWork work, IndexReader reader);
	
}
