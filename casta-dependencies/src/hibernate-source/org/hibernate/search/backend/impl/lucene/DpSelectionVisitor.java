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

import org.hibernate.search.backend.AddLuceneWork;
import org.hibernate.search.backend.DeleteLuceneWork;
import org.hibernate.search.backend.LuceneWork;
import org.hibernate.search.backend.OptimizeLuceneWork;
import org.hibernate.search.backend.PurgeAllLuceneWork;
import org.hibernate.search.backend.WorkVisitor;
import org.hibernate.search.store.DirectoryProvider;
import org.hibernate.search.store.IndexShardingStrategy;

/**
 * This is the main client for IndexShardingStrategies.
 * Only implementation of WorkVisitor<DpSelectionDelegate>,
 * using a visitor/selector pattern for different implementations of addAsPayLoadsToQueue
 * depending on the type of LuceneWork.
 * 
 * @author Sanne Grinovero
 */
class DpSelectionVisitor implements WorkVisitor<DpSelectionDelegate> {
	
	private final AddSelectionDelegate addDelegate = new AddSelectionDelegate();
	private final DeleteSelectionDelegate deleteDelegate = new DeleteSelectionDelegate();
	private final OptimizeSelectionDelegate optimizeDelegate = new OptimizeSelectionDelegate();
	private final PurgeAllSelectionDelegate purgeDelegate = new PurgeAllSelectionDelegate();

	public DpSelectionDelegate getDelegate(AddLuceneWork addLuceneWork) {
		return addDelegate;
	}

	public DpSelectionDelegate getDelegate(DeleteLuceneWork deleteLuceneWork) {
		return deleteDelegate;
	}

	public DpSelectionDelegate getDelegate(OptimizeLuceneWork optimizeLuceneWork) {
		return optimizeDelegate;
	}

	public DpSelectionDelegate getDelegate(PurgeAllLuceneWork purgeAllLuceneWork) {
		return purgeDelegate;
	}
	
	private static class AddSelectionDelegate implements DpSelectionDelegate {

		public void addAsPayLoadsToQueue(LuceneWork work,
				IndexShardingStrategy shardingStrategy, QueueProcessors queues) {
			DirectoryProvider provider = shardingStrategy.getDirectoryProviderForAddition(
					work.getEntityClass(),
					work.getId(),
					work.getIdInString(),
					work.getDocument()
			);
			queues.addWorkToDpProcessor( provider, work );
		}

	}
	
	private static class DeleteSelectionDelegate implements DpSelectionDelegate {

		public void addAsPayLoadsToQueue(LuceneWork work,
				IndexShardingStrategy shardingStrategy, QueueProcessors queues) {
			DirectoryProvider[] providers = shardingStrategy.getDirectoryProvidersForDeletion(
					work.getEntityClass(),
					work.getId(),
					work.getIdInString()
			);
			for (DirectoryProvider provider : providers) {
				queues.addWorkToDpProcessor( provider, work );
			}
		}

	}
	
	private static class OptimizeSelectionDelegate implements DpSelectionDelegate {

		public void addAsPayLoadsToQueue(LuceneWork work,
				IndexShardingStrategy shardingStrategy, QueueProcessors queues) {
			DirectoryProvider[] providers = shardingStrategy.getDirectoryProvidersForAllShards();
			for (DirectoryProvider provider : providers) {
				queues.addWorkToDpProcessor( provider, work );
			}
		}

	}
	
	private static class PurgeAllSelectionDelegate implements DpSelectionDelegate {

		public void addAsPayLoadsToQueue(LuceneWork work,
				IndexShardingStrategy shardingStrategy, QueueProcessors queues) {
			DirectoryProvider[] providers = shardingStrategy.getDirectoryProvidersForDeletion(
					work.getEntityClass(),
					work.getId(),
					work.getIdInString()
			);
			for (DirectoryProvider provider : providers) {
				queues.addWorkToDpProcessor( provider, work );
			}
		}

	}

}
