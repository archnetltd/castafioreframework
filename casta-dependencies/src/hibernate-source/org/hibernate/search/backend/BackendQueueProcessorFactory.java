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
 //$Id: BackendQueueProcessorFactory.java 15616 2008-11-25 17:56:50Z sannegrinovero $
package org.hibernate.search.backend;

import java.util.Properties;
import java.util.List;

import org.hibernate.search.engine.SearchFactoryImplementor;

/**
 * Interface for different types of queue processor factories. Implementations need a no-arg constructor.
 * The factory typically prepares or pools the resources needed by the queue processor.
 *
 * @author Emmanuel Bernard
 */
public interface BackendQueueProcessorFactory {
	
	/**
	 * Used at startup, called once as first method.
	 * @param props all configuration properties
	 * @param searchFactory the client
	 */
	void initialize(Properties props, SearchFactoryImplementor searchFactory);

	/**
	 * Return a runnable implementation responsible for processing the queue to a given backend.
	 *
	 * @param queue The work queue to process.
	 * @return <code>Runnable</code> which processes <code>queue</code> when started.
	 */
	Runnable getProcessor(List<LuceneWork> queue);
	
	/**
	 * Used to shutdown and eventually release resources.
	 * no other method should used after this one.
	 */
	void close();
	
}
