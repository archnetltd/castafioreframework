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
 //$Id: QueueingProcessor.java 14713 2008-05-29 15:18:15Z sannegrinovero $
package org.hibernate.search.backend;

/**
 * Pile work operations
 * No thread safety has to be implemented, the queue being scoped already
 * The implementation must be "stateless" wrt the queue through (ie not store the queue state)
 *
 * FIXME this Interface does not make much sense, since the impl will not be changed
 *
 * @author Emmanuel Bernard
 */
public interface QueueingProcessor {
	/**
	 * Add a work
	 * TODO move that somewhere else, it does not really fit here
	 */
	void add(Work work, WorkQueue workQueue);

	/**
	 * prepare resources for a later performWorks call
	 */
	void prepareWorks(WorkQueue workQueue);

	/**
	 * Execute works
	 */
	void performWorks(WorkQueue workQueue);

	/**
	 * Rollback works
	 */
	void cancelWorks(WorkQueue workQueue);

	/**
	 * clean resources
	 * This method should log errors rather than raise an exception
	 */
	void close();
}
