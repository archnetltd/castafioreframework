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
 // $Id: EventSourceTransactionContext.java 14954 2008-07-17 20:43:10Z sannegrinovero $
package org.hibernate.search.backend.impl;

import java.io.Serializable;
import javax.transaction.Synchronization;

import org.hibernate.Transaction;
import org.hibernate.search.backend.TransactionContext;
import org.hibernate.event.EventSource;

/**
 * Implementation of the transactional context on top of an EventSource (Session)
 * 
 * @author Navin Surtani  - navin@surtani.org
 */
public class EventSourceTransactionContext implements TransactionContext, Serializable {
	EventSource eventSource;

	public EventSourceTransactionContext(EventSource eventSource) {
		this.eventSource = eventSource;
	}

	public Object getTransactionIdentifier() {
		return eventSource.getTransaction();
	}

	public void registerSynchronization(Synchronization synchronization) {
		Transaction transaction = eventSource.getTransaction();
		transaction.registerSynchronization( synchronization );
	}

	public boolean isTransactionInProgress() {
		return eventSource.isTransactionInProgress();
	}

}
