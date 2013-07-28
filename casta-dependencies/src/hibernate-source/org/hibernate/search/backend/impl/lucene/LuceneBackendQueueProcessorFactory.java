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
 //$Id: LuceneBackendQueueProcessorFactory.java 15618 2008-11-25 18:38:50Z sannegrinovero $
package org.hibernate.search.backend.impl.lucene;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.List;
import java.util.concurrent.ExecutorService;

import org.hibernate.search.backend.BackendQueueProcessorFactory;
import org.hibernate.search.backend.LuceneWork;
import org.hibernate.search.backend.impl.BatchedQueueingProcessor;
import org.hibernate.search.engine.SearchFactoryImplementor;
import org.hibernate.search.store.DirectoryProvider;

/**
 * This will actually contain the Workspace and LuceneWork visitor implementation,
 * reused per-DirectoryProvider.
 * Both Workspace(s) and LuceneWorkVisitor(s) lifecycle are linked to the backend
 * lifecycle (reused and shared by all transactions).
 * The LuceneWorkVisitor(s) are stateless, the Workspace(s) are threadsafe.
 * 
 * @author Emmanuel Bernard
 * @author Sanne Grinovero
 */
public class LuceneBackendQueueProcessorFactory implements BackendQueueProcessorFactory {

	private SearchFactoryImplementor searchFactoryImp;
	
	/**
	 * Contains the Workspace and LuceneWork visitor implementation,
	 * reused per-DirectoryProvider.
	 * Both Workspace(s) and LuceneWorkVisitor(s) lifecycle are linked to the backend
	 * lifecycle (reused and shared by all transactions);
	 * the LuceneWorkVisitor(s) are stateless, the Workspace(s) are threadsafe.
	 */
	private final Map<DirectoryProvider,PerDPResources> resourcesMap = new HashMap<DirectoryProvider,PerDPResources>();

	/**
	 * copy of BatchedQueueingProcessor.sync
	 */
	private boolean sync;

	public void initialize(Properties props, SearchFactoryImplementor searchFactoryImplementor) {
		this.searchFactoryImp = searchFactoryImplementor;
		this.sync = BatchedQueueingProcessor.isConfiguredAsSync( props );
		for (DirectoryProvider dp : searchFactoryImplementor.getDirectoryProviders() ) {
			PerDPResources resources = new PerDPResources( searchFactoryImplementor, dp );
			resourcesMap.put( dp, resources );
		}
	}

	public Runnable getProcessor(List<LuceneWork> queue) {
		return new LuceneBackendQueueProcessor( queue, searchFactoryImp, resourcesMap, sync );
	}

	public void close() {
		// needs to stop all used ThreadPools
		for (PerDPResources res : resourcesMap.values() ) {
			ExecutorService executor = res.getExecutor();
			executor.shutdown();
		}
	}
	
}
