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
 // $Id: ContextHolder.java 15628 2008-11-29 14:06:12Z sannegrinovero $
package org.hibernate.search.event;

import java.util.WeakHashMap;

import org.hibernate.cfg.Configuration;
import org.hibernate.search.cfg.SearchConfigurationFromHibernateCore;
import org.hibernate.search.impl.SearchFactoryImpl;

/**
 * Holds already built SearchFactory per Hibernate Configuration object
 * concurrent threads do not share this information
 *
 * @author Emmanuel Bernard
 */
public class ContextHolder {
	private static final ThreadLocal<WeakHashMap<Configuration, SearchFactoryImpl>> contexts =
			new ThreadLocal<WeakHashMap<Configuration, SearchFactoryImpl>>();

	//code doesn't have to be multithreaded because SF creation is not.
	//this is not a public API, should really only be used during the SessionFactory building
	public static SearchFactoryImpl getOrBuildSearchFactory(Configuration cfg) {
		WeakHashMap<Configuration, SearchFactoryImpl> contextMap = contexts.get();
		if ( contextMap == null ) {
			contextMap = new WeakHashMap<Configuration, SearchFactoryImpl>( 2 );
			contexts.set( contextMap );
		}
		SearchFactoryImpl searchFactory = contextMap.get( cfg );
		if ( searchFactory == null ) {
			searchFactory = new SearchFactoryImpl( new SearchConfigurationFromHibernateCore( cfg ) );
			contextMap.put( cfg, searchFactory );
		}
		return searchFactory;
	}
}
