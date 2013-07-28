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
 // $Id: NoOpOptimizerStrategy.java 14012 2007-09-16 19:57:36Z hardy.ferentschik $
package org.hibernate.search.store.optimization;

import java.util.Properties;

import org.hibernate.search.store.DirectoryProvider;
import org.hibernate.search.engine.SearchFactoryImplementor;
import org.hibernate.search.backend.Workspace;

/**
 * @author Emmanuel Bernard
 */
public class NoOpOptimizerStrategy implements OptimizerStrategy {
	public void initialize(DirectoryProvider directoryProvider, Properties indexProperties, SearchFactoryImplementor searchFactoryImplementor) {
	}

	public void optimizationForced() {
	}

	public boolean needOptimization() {
		return false;
	}

	public void addTransaction(long operations) {
	}

	public void optimize(Workspace workspace) {
	}
}
