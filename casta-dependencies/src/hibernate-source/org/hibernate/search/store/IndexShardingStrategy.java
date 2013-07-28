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
 // $Id: IndexShardingStrategy.java 14972 2008-07-28 17:09:56Z epbernard $
package org.hibernate.search.store;

import java.io.Serializable;
import java.util.Properties;

import org.apache.lucene.document.Document;

/**
 * Defines how a given virtual index shards data into different DirectoryProviders
 *
 * @author Emmanuel Bernard
 */
public interface IndexShardingStrategy {
	/**
	 * provides access to sharding properties (under the suffix sharding_strategy)
	 * and provide access to all the DirectoryProviders for a given index
	 */
	void initialize(Properties properties, DirectoryProvider<?>[] providers);

	/**
	 * Ask for all shards (eg to query or optimize)
	 */
	DirectoryProvider<?>[] getDirectoryProvidersForAllShards();

	/**
	 * return the DirectoryProvider where the given entity will be indexed
	 */
	DirectoryProvider<?> getDirectoryProviderForAddition(Class<?> entity, Serializable id, String idInString, Document document);
	/**
	 * return the DirectoryProvider(s) where the given entity is stored and where the deletion operation needs to be applied
	 * id and idInString can be null. If null, all the directory providers containing entity types should be returned
	 */
	DirectoryProvider<?>[] getDirectoryProvidersForDeletion(Class<?> entity, Serializable id, String idInString);
}
