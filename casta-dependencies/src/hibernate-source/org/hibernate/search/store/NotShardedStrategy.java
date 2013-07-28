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
 // $Id: NotShardedStrategy.java 14972 2008-07-28 17:09:56Z epbernard $
package org.hibernate.search.store;

import java.util.Properties;
import java.io.Serializable;

import org.apache.lucene.document.Document;
import org.hibernate.annotations.common.AssertionFailure;

/**
 * @author Emmanuel Bernard
 */
public class NotShardedStrategy implements IndexShardingStrategy {
	private DirectoryProvider<?>[] directoryProvider;
	public void initialize(Properties properties, DirectoryProvider<?>[] providers) {
		this.directoryProvider = providers;
		if ( directoryProvider.length > 1) {
			throw new AssertionFailure("Using SingleDirectoryProviderSelectionStrategy with multiple DirectryProviders");
		}
	}

	public DirectoryProvider<?>[] getDirectoryProvidersForAllShards() {
		return directoryProvider;
	}

	public DirectoryProvider<?> getDirectoryProviderForAddition(Class<?> entity, Serializable id, String idInString, Document document) {
		return directoryProvider[0];
	}

	public DirectoryProvider<?>[] getDirectoryProvidersForDeletion(Class<?> entity, Serializable id, String idInString) {
		return directoryProvider;
	}

}
