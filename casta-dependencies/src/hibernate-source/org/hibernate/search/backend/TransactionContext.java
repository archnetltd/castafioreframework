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
 // $Id: TransactionContext.java 15437 2008-10-29 16:51:52Z hardy.ferentschik $
package org.hibernate.search.backend;

import javax.transaction.Synchronization;

/**
 * Contract needed by Hibernate Search to batch changes per transaction.
 *
 * @author Navin Surtani  - navin@surtani.org
 */
public interface TransactionContext {
	/**
	 * @return A boolean indicating whether a transaction is in progress or not.
	 */
	public boolean isTransactionInProgress();

	/**
	 * @return a transaction object.
	 */
	public Object getTransactionIdentifier();

	/**
	 * Register the given synchronization.
	 * 
 	 * @param synchronization synchronization to register
	 */
	public void registerSynchronization(Synchronization synchronization);
}
