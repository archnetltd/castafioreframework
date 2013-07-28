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

import org.hibernate.search.backend.AddLuceneWork;
import org.hibernate.search.backend.DeleteLuceneWork;
import org.hibernate.search.backend.OptimizeLuceneWork;
import org.hibernate.search.backend.PurgeAllLuceneWork;
import org.hibernate.search.backend.WorkVisitor;
import org.hibernate.search.backend.Workspace;

/**
 * @author Sanne Grinovero
 */
public class LuceneWorkVisitor implements WorkVisitor<LuceneWorkDelegate> {
	
	private final AddWorkDelegate addDelegate;
	private final DeleteWorkDelegate deleteDelegate;
	private final OptimizeWorkDelegate optimizeDelegate;
	private final PurgeAllWorkDelegate purgeAllDelegate;
	
	public LuceneWorkVisitor(Workspace workspace) {
		if ( workspace.getEntitiesInDirectory().size() == 1 ) {
			this.deleteDelegate = new DeleteExtWorkDelegate( workspace );
		}
		else {
			this.deleteDelegate = new DeleteWorkDelegate( workspace );
		}
		this.purgeAllDelegate = new PurgeAllWorkDelegate();
		this.addDelegate = new AddWorkDelegate( workspace );
		this.optimizeDelegate = new OptimizeWorkDelegate( workspace );
	}

	public LuceneWorkDelegate getDelegate(AddLuceneWork addLuceneWork) {
		return addDelegate;
	}

	public LuceneWorkDelegate getDelegate(DeleteLuceneWork deleteLuceneWork) {
		return deleteDelegate;
	}

	public LuceneWorkDelegate getDelegate(OptimizeLuceneWork optimizeLuceneWork) {
		return optimizeDelegate;
	}

	public LuceneWorkDelegate getDelegate(PurgeAllLuceneWork purgeAllLuceneWork) {
		return purgeAllDelegate;
	}
	
}
