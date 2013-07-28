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
 // $Id: OptimizerStrategy.java 14012 2007-09-16 19:57:36Z hardy.ferentschik $
package org.hibernate.search.store.optimization;

import java.util.Properties;

import org.hibernate.search.backend.Workspace;
import org.hibernate.search.engine.SearchFactoryImplementor;
import org.hibernate.search.store.DirectoryProvider;

/**
 * @author Emmanuel Bernard
 */
public interface OptimizerStrategy {
	public void initialize(DirectoryProvider directoryProvider, Properties indexProperties, SearchFactoryImplementor searchFactoryImplementor);

	/**
	 * has to be called in a thread safe way
	 */
	void optimizationForced();

	/**
	 * has to be called in a thread safe way
	 */
	boolean needOptimization();

	/**
	 * has to be called in a thread safe way
	 */
	public void addTransaction(long operations);

	/**
	 * has to be called in a thread safe way
	 */
	void optimize(Workspace workspace);

}
