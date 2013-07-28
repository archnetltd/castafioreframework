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
 //$Id: DirectoryProvider.java 14932 2008-07-15 21:27:03Z sannegrinovero $
package org.hibernate.search.store;

import java.util.Properties;

import org.apache.lucene.store.Directory;
import org.hibernate.search.engine.SearchFactoryImplementor;


/**
 * Set up and provide a Lucene <code>Directory</code>
 * <code>equals()</code> and <code>hashCode()</code> must guaranty equality
 * between two providers pointing to the same underlying Lucene Store.
 * Besides that, hashCode ordering is used to avoid deadlock when locking a directory provider.
 * 
 * This class must be thread safe regarding <code>getDirectory()</code> calls
 *
 * @author Emmanuel Bernard
 * @author Sylvain Vieujot
 */
public interface DirectoryProvider<TDirectory extends Directory> {
	/**
	 * get the information to initialize the directory and build its hashCode/equals method
	 */
	void initialize(String directoryProviderName, Properties properties, SearchFactoryImplementor searchFactoryImplementor);

	/**
	 * Executed after initialize, this method set up the heavy process of starting up the DirectoryProvider
	 * IO processing as well as background processing are expected to be set up here
	 *
	 */
	void start();

	/**
	 * Executed when the search factory is closed. This method should stop any background process as well as
	 * releasing any resource.
	 * This method should avoid raising exceptions and log potential errors instead
	 */
	void stop();

	/**
	 * Returns an initialized Lucene Directory. This method call <b>must</b> be threadsafe
	 */
	TDirectory getDirectory();
}

