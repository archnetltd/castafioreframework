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
 //$Id: TransactionalWorker.java 15605 2008-11-23 10:48:59Z hardy.ferentschik $
package org.hibernate.search.backend.impl;

import java.util.Properties;

import javax.transaction.Synchronization;

import org.hibernate.search.backend.QueueingProcessor;
import org.hibernate.search.backend.Work;
import org.hibernate.search.backend.WorkQueue;
import org.hibernate.search.backend.Worker;
import org.hibernate.search.backend.TransactionContext;
import org.hibernate.search.engine.SearchFactoryImplementor;
import org.hibernate.search.util.WeakIdentityHashMap;

/**
 * Queue works per transaction.
 * If out of transaction, the work is executed right away
 * <p/>
 * When <code>hibernate.search.worker.type</code> is set to <code>async</code>
 * the work is done in a separate thread (threads are pooled)
 *
 * @author Emmanuel Bernard
 */
public class TransactionalWorker implements Worker {
	//not a synchronized map since for a given transaction, we have not concurrent access
	protected final WeakIdentityHashMap<Object, Synchronization> synchronizationPerTransaction = new WeakIdentityHashMap<Object, Synchronization>();
	private QueueingProcessor queueingProcessor;

	public void performWork(Work work, TransactionContext transactionContext) {
		if ( transactionContext.isTransactionInProgress() ) {
			Object transaction = transactionContext.getTransactionIdentifier();
			PostTransactionWorkQueueSynchronization txSync = ( PostTransactionWorkQueueSynchronization )
					synchronizationPerTransaction.get( transaction );
			if ( txSync == null || txSync.isConsumed() ) {
				txSync = new PostTransactionWorkQueueSynchronization(
						queueingProcessor, synchronizationPerTransaction
				);
				transactionContext.registerSynchronization( txSync );
				synchronizationPerTransaction.put( transaction, txSync );
			}
			txSync.add( work );
		}
		else {
			WorkQueue queue = new WorkQueue( 2 ); //one work can be split
			queueingProcessor.add( work, queue );
			queueingProcessor.prepareWorks( queue );
			queueingProcessor.performWorks( queue );
		}
	}

	public void initialize(Properties props, SearchFactoryImplementor searchFactory) {
		this.queueingProcessor = new BatchedQueueingProcessor( searchFactory, props );
	}

	public void close() {
		queueingProcessor.close();
	}

	public void flushWorks(TransactionContext transactionContext) {
		if ( transactionContext.isTransactionInProgress() ) {
			Object transaction = transactionContext.getTransactionIdentifier();
			PostTransactionWorkQueueSynchronization txSync = ( PostTransactionWorkQueueSynchronization )
					synchronizationPerTransaction.get( transaction );
			if ( txSync != null && !txSync.isConsumed() ) {
				txSync.flushWorks();
			}
		}
	}

}
