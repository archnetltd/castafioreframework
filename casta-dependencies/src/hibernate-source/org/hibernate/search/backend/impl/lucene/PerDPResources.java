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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.hibernate.search.backend.Workspace;
import org.hibernate.search.backend.impl.lucene.works.LuceneWorkVisitor;
import org.hibernate.search.engine.SearchFactoryImplementor;
import org.hibernate.search.store.DirectoryProvider;

class PerDPResources {
	
	private final ExecutorService executor;
	private final LuceneWorkVisitor visitor;
	private final Workspace workspace;
	
	PerDPResources(SearchFactoryImplementor searchFactoryImp, DirectoryProvider dp) {
		workspace = new Workspace( searchFactoryImp, dp );
		visitor = new LuceneWorkVisitor( workspace );
		executor = Executors.newFixedThreadPool( 1 );
	}

	public ExecutorService getExecutor() {
		return executor;
	}

	public LuceneWorkVisitor getVisitor() {
		return visitor;
	}

	public Workspace getWorkspace() {
		return workspace;
	}
	
}
