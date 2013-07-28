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
 // $Id: IdHashShardingStrategy.java 15195 2008-09-14 09:00:05Z sannegrinovero $
package org.hibernate.search.store;

import java.util.Properties;
import java.io.Serializable;

import org.apache.lucene.document.Document;

/**
 * This implementation use idInString as the hashKey.
 * 
 * @author Emmanuel Bernard
 */
public class IdHashShardingStrategy implements IndexShardingStrategy {
	
	private DirectoryProvider<?>[] providers;
	public void initialize(Properties properties, DirectoryProvider<?>[] providers) {
		this.providers = providers;
	}

	public DirectoryProvider<?>[] getDirectoryProvidersForAllShards() {
		return providers;
	}

	public DirectoryProvider<?> getDirectoryProviderForAddition(Class<?> entity, Serializable id, String idInString, Document document) {
		return providers[ hashKey(idInString) ];
	}

	public DirectoryProvider<?>[] getDirectoryProvidersForDeletion(Class<?> entity, Serializable id, String idInString) {
		if ( idInString == null ) return providers;
		return new DirectoryProvider[] { providers[hashKey( idInString )] };
	}

	private int hashKey(String key) {
		// reproduce the hashCode implementation of String as documented in the javadoc
		// to be safe cross Java version (in case it changes some day)
		int hash = 0;
		int length = key.length();
		for ( int index = 0; index < length; index++ ) {
			hash = 31 * hash + key.charAt( index );
		}
		return Math.abs( hash % providers.length );
	}
}
