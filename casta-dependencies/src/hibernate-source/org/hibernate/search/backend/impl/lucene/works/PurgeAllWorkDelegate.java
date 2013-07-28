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
import org.apache.lucene.index.Term;
import org.slf4j.Logger;

import org.hibernate.search.SearchException;
import org.hibernate.search.backend.LuceneWork;
import org.hibernate.search.backend.impl.lucene.IndexInteractionType;
import org.hibernate.search.engine.DocumentBuilder;
import org.hibernate.search.util.LoggerFactory;

/**
* Stateless implementation that performs a PurgeAllLuceneWork.
* @see LuceneWorkVisitor
* @see LuceneWorkDelegate
* @author Emmanuel Bernard
* @author Hardy Ferentschik
* @author John Griffin
* @author Sanne Grinovero
*/
class PurgeAllWorkDelegate implements LuceneWorkDelegate {
	
	private static final Logger log = LoggerFactory.make();

	PurgeAllWorkDelegate() {
	}

	public IndexInteractionType getIndexInteractionType() {
		return IndexInteractionType.PREFER_INDEXREADER;
	}

	public void performWork(LuceneWork work, IndexWriter writer) {
		log.trace( "purgeAll Lucene index using IndexWriter for type: {}", work.getEntityClass() );
		try {
			Term term = new Term( DocumentBuilder.CLASS_FIELDNAME, work.getEntityClass().getName() );
			writer.deleteDocuments( term );
		}
		catch (Exception e) {
			throw new SearchException( "Unable to purge all from Lucene index: " + work.getEntityClass(), e );
		}
	}

	public void performWork(LuceneWork work, IndexReader reader) {
		log.trace( "purgeAll Lucene index using IndexReader for type: {}", work.getEntityClass() );
		try {
			Term term = new Term( DocumentBuilder.CLASS_FIELDNAME, work.getEntityClass().getName() );
			reader.deleteDocuments( term );
		}
		catch (Exception e) {
			throw new SearchException( "Unable to purge all from Lucene index: " + work.getEntityClass(), e );
		}
	}
	
}
