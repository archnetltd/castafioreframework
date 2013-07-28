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
 //$Id: PostTransactionWorkQueueSynchronization.java 14794 2008-06-21 13:47:31Z epbernard $
package org.hibernate.search.backend.impl;

import javax.transaction.Status;
import javax.transaction.Synchronization;

import org.hibernate.search.backend.QueueingProcessor;
import org.hibernate.search.backend.Work;
import org.hibernate.search.backend.WorkQueue;
import org.hibernate.search.util.WeakIdentityHashMap;

/**
 * Execute some work inside a transaction synchronization
 *
 * @author Emmanuel Bernard
 */
public class PostTransactionWorkQueueSynchronization implements Synchronization {
	private final QueueingProcessor queueingProcessor;
	private boolean consumed;
	private final WeakIdentityHashMap queuePerTransaction;
	private WorkQueue queue = new WorkQueue();

	/**
	 * in transaction work
	 */
	public PostTransactionWorkQueueSynchronization(QueueingProcessor queueingProcessor, WeakIdentityHashMap queuePerTransaction) {
		this.queueingProcessor = queueingProcessor;
		this.queuePerTransaction = queuePerTransaction;
	}

	public void add(Work work) {
		queueingProcessor.add( work, queue );
	}

	public boolean isConsumed() {
		return consumed;
	}

	public void beforeCompletion() {
		queueingProcessor.prepareWorks(queue);
	}

	public void afterCompletion(int i) {
		try {
			if ( Status.STATUS_COMMITTED == i ) {
				queueingProcessor.performWorks(queue);
			}
			else {
				queueingProcessor.cancelWorks(queue);
			}
		}
		finally {
			consumed = true;
			//clean the Synchronization per Transaction
			//not needed stricto sensus but a cleaner approach and faster than the GC
			if (queuePerTransaction != null) queuePerTransaction.removeValue( this ); 
		}
	}

	public void flushWorks() {
		WorkQueue subQueue = queue.splitQueue();
		queueingProcessor.prepareWorks( subQueue );
		queueingProcessor.performWorks( subQueue );
	}
}
