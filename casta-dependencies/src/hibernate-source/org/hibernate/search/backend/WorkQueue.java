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
 //$Id: WorkQueue.java 14922 2008-07-11 15:18:24Z hardy.ferentschik $
package org.hibernate.search.backend;

import java.util.List;
import java.util.ArrayList;

import org.hibernate.annotations.common.AssertionFailure;

/**
 * @author Emmanuel Bernard
 */
public class WorkQueue {
	private List<Work> queue;

	private List<LuceneWork> sealedQueue;

	public WorkQueue(int size) {
		queue = new ArrayList<Work>(size);
	}

	private WorkQueue(List<Work> queue) {
		this.queue = queue;
	}

	public WorkQueue() {
		this(10);
	}


	public void add(Work work) {
		queue.add(work);
	}


	public List<Work> getQueue() {
		return queue;
	}

	public WorkQueue splitQueue() {
		WorkQueue subQueue = new WorkQueue( queue );
		this.queue = new ArrayList<Work>( queue.size() );
		return subQueue;
	}


	public List<LuceneWork> getSealedQueue() {
		if (sealedQueue == null) throw new AssertionFailure("Access a Sealed WorkQueue which has not been sealed");
		return sealedQueue;
	}

	public void setSealedQueue(List<LuceneWork> sealedQueue) {
		//invalidate the working queue for serializability
		queue = null;
		this.sealedQueue = sealedQueue;
	}

	public void clear() {
		queue.clear();
		if (sealedQueue != null) sealedQueue.clear();
	}

	public int size() {
		return queue.size();
	}
}
