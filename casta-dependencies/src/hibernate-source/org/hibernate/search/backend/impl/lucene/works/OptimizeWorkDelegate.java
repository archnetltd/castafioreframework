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

import java.io.IOException;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.slf4j.Logger;

import org.hibernate.search.SearchException;
import org.hibernate.search.backend.LuceneWork;
import org.hibernate.search.backend.Workspace;
import org.hibernate.search.backend.impl.lucene.IndexInteractionType;
import org.hibernate.search.util.LoggerFactory;

/**
 * Stateless implementation that performs a OptimizeLuceneWork.
 *
 * @author Emmanuel Bernard
 * @author Hardy Ferentschik
 * @author John Griffin
 * @author Sanne Grinovero
 * @see LuceneWorkVisitor
 * @see LuceneWorkDelegate
 */
class OptimizeWorkDelegate implements LuceneWorkDelegate {

	private static final Logger log = LoggerFactory.make();

	private final Workspace workspace;

	OptimizeWorkDelegate(Workspace workspace) {
		this.workspace = workspace;
	}

	public IndexInteractionType getIndexInteractionType() {
		return IndexInteractionType.NEEDS_INDEXWRITER;
	}

	public void performWork(LuceneWork work, IndexWriter writer) {
		log.trace( "optimize Lucene index: {}", work.getEntityClass() );
		try {
			writer.optimize();
			workspace.optimize();
		}
		catch ( IOException e ) {
			throw new SearchException( "Unable to optimize Lucene index: " + work.getEntityClass(), e );
		}
	}

	public void performWork(LuceneWork work, IndexReader reader) {
		throw new UnsupportedOperationException();
	}

}
