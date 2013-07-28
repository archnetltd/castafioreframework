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
 //$Id: LuceneBackendQueueProcessor.java 15618 2008-11-25 18:38:50Z sannegrinovero $
package org.hibernate.search.backend.impl.lucene;

import java.util.List;
import java.util.Map;

import org.hibernate.search.backend.LuceneWork;
import org.hibernate.search.engine.DocumentBuilderIndexedEntity;
import org.hibernate.search.engine.SearchFactoryImplementor;
import org.hibernate.search.store.DirectoryProvider;
import org.hibernate.search.store.IndexShardingStrategy;
import org.hibernate.search.util.LoggerFactory;
import org.slf4j.Logger;

/**
 * Apply the operations to Lucene directories.
 *
 * @author Emmanuel Bernard
 * @author Hardy Ferentschik
 * @author John Griffin
 * @author Sanne Grinovero
 */
class LuceneBackendQueueProcessor implements Runnable {
	
	private final List<LuceneWork> queue;
	private final SearchFactoryImplementor searchFactoryImplementor;
	private final Map<DirectoryProvider,PerDPResources> resourcesMap;
	private final boolean sync;
	
	private static final DpSelectionVisitor providerSelectionVisitor = new DpSelectionVisitor();
	private static final Logger log = LoggerFactory.make();

	LuceneBackendQueueProcessor(List<LuceneWork> queue,
			SearchFactoryImplementor searchFactoryImplementor,
			Map<DirectoryProvider,PerDPResources> resourcesMap,
			boolean syncMode) {
		this.sync = syncMode;
		this.queue = queue;
		this.searchFactoryImplementor = searchFactoryImplementor;
		this.resourcesMap = resourcesMap;
	}

	public void run() {
		QueueProcessors processors = new QueueProcessors( resourcesMap );
		// divide the queue in tasks, adding to QueueProcessors by affected Directory.
		for ( LuceneWork work : queue ) {
			final Class<?> entityType = work.getEntityClass();
			DocumentBuilderIndexedEntity<?> documentBuilder = searchFactoryImplementor.getDocumentBuilderIndexedEntity( entityType );
			IndexShardingStrategy shardingStrategy = documentBuilder.getDirectoryProviderSelectionStrategy();
			work.getWorkDelegate( providerSelectionVisitor ).addAsPayLoadsToQueue( work, shardingStrategy, processors );
		}
		try {
			//this Runnable splits tasks in more runnables and then runs them:
			processors.runAll( sync );
		} catch (InterruptedException e) {
			log.error( "Index update task has been interrupted", e );
		}
	}
	
}
