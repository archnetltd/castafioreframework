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
 // $Id: SearchFactoryImplementor.java 15612 2008-11-25 13:29:35Z hardy.ferentschik $
package org.hibernate.search.engine;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

import org.hibernate.search.SearchFactory;
import org.hibernate.search.backend.BackendQueueProcessorFactory;
import org.hibernate.search.backend.LuceneIndexingParameters;
import org.hibernate.search.backend.Worker;
import org.hibernate.search.filter.FilterCachingStrategy;
import org.hibernate.search.store.DirectoryProvider;
import org.hibernate.search.store.optimization.OptimizerStrategy;

/**
 * Interface which gives access to the different directory providers and their configuration.
 *
 * @author Emmanuel Bernard
 * @author Hardy Ferentschik
 */
public interface SearchFactoryImplementor extends SearchFactory {
	BackendQueueProcessorFactory getBackendQueueProcessorFactory();

	void setBackendQueueProcessorFactory(BackendQueueProcessorFactory backendQueueProcessorFactory);

	Map<Class<?>, DocumentBuilderIndexedEntity<?>> getDocumentBuildersIndexedEntities();

	<T> DocumentBuilderIndexedEntity<T> getDocumentBuilderIndexedEntity(Class<T> entityType);

	<T> DocumentBuilderContainedEntity<T> getDocumentBuilderContainedEntity(Class<T> entityType);

	Worker getWorker();

	void addOptimizerStrategy(DirectoryProvider<?> provider, OptimizerStrategy optimizerStrategy);

	OptimizerStrategy getOptimizerStrategy(DirectoryProvider<?> provider);

	FilterCachingStrategy getFilterCachingStrategy();

	FilterDef getFilterDefinition(String name);

	LuceneIndexingParameters getIndexingParameters(DirectoryProvider<?> provider);

	void addIndexingParameters(DirectoryProvider<?> provider, LuceneIndexingParameters indexingParams);

	String getIndexingStrategy();

	void close();

	void addClassToDirectoryProvider(Class<?> clazz, DirectoryProvider<?> directoryProvider);

	Set<Class<?>> getClassesInDirectoryProvider(DirectoryProvider<?> directoryProvider);

	Set<DirectoryProvider<?>> getDirectoryProviders();

	ReentrantLock getDirectoryProviderLock(DirectoryProvider<?> dp);

	void addDirectoryProvider(DirectoryProvider<?> provider);
	
	int getFilterCacheBitResultsSize();

	Set<Class<?>> getIndexedTypesPolymorphic(Class<?>[] classes);
}
