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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.hibernate.search.backend.LuceneWork;
import org.hibernate.search.store.DirectoryProvider;

/**
 * Container used to split work by DirectoryProviders and execute
 * them concurrently.
 * @author Sanne Grinovero
 */
class QueueProcessors {
	
	private final Map<DirectoryProvider, PerDPResources> resourcesMap;
	private final Map<DirectoryProvider, PerDPQueueProcessor> dpProcessors = new HashMap<DirectoryProvider, PerDPQueueProcessor>();
	
	QueueProcessors(Map<DirectoryProvider, PerDPResources> resourcesMap) {
		this.resourcesMap = resourcesMap;
	}

	void addWorkToDpProcessor(DirectoryProvider dp, LuceneWork work) {
		if ( ! dpProcessors.containsKey( dp ) ) {
			dpProcessors.put( dp, new PerDPQueueProcessor( resourcesMap.get( dp ) ) );
		}
		PerDPQueueProcessor processor = dpProcessors.get( dp );
		processor.addWork ( work );
	}
	
	/**
	 * Run all index modifications queued so far
	 * @param sync when true this method blocks until all job is done.
	 * @throws InterruptedException only relevant when sync is true.
	 */
	void runAll(boolean sync) throws InterruptedException {
		if ( sync ) {
			runAllWaiting();
		}
		else {
			runAllAsync();
		}
	}
	
	/**
	 * Runs all PerDPQueueProcessor and don't wait fot them to finish.
	 */
	private void runAllAsync() {
		// execute all work in parallel on each DirectoryProvider;
		// each DP has it's own ExecutorService.
		for ( PerDPQueueProcessor process : dpProcessors.values() ) {
			ExecutorService executor = process.getOwningExecutor();
			executor.execute( process );
		}
	}

	/**
	 * Runs all PerDPQueueProcessor and waits until all have been processed.
	 * @throws InterruptedException
	 */
	private void runAllWaiting() throws InterruptedException {
		List<Future<Object>> futures = new ArrayList<Future<Object>>( dpProcessors.size() );
		// execute all work in parallel on each DirectoryProvider;
		// each DP has it's own ExecutorService.
		for ( PerDPQueueProcessor process : dpProcessors.values() ) {
			ExecutorService executor = process.getOwningExecutor();
			//wrap each Runnable in a Future
			FutureTask<Object> f = new FutureTask<Object>( process, null );
			futures.add( f );
			executor.execute( f );
		}
		// and then wait for all tasks to be finished:
		for ( Future<Object> f : futures ) {
            if ( !f.isDone() ) {
                try {
                    f.get(); 
                } catch(CancellationException ignore) {
                	//ignored, as in java.util.concurrent.AbstractExecutorService.invokeAll(Collection<Callable<T>> tasks)
                } catch(ExecutionException ignore) {
                	//ignored, as in java.util.concurrent.AbstractExecutorService.invokeAll(Collection<Callable<T>> tasks)
                }
            }
        }
	}

}
