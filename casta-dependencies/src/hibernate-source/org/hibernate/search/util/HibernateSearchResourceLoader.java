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
 package org.hibernate.search.util;

import java.io.InputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.solr.common.ResourceLoader;
import org.apache.solr.util.plugin.ResourceLoaderAware;

import org.hibernate.annotations.common.util.ReflectHelper;
import org.hibernate.search.SearchException;

/**
 * @author Emmanuel Bernard
 */
public class HibernateSearchResourceLoader implements ResourceLoader {
	public InputStream openResource(String resource) throws IOException {
		return Thread.currentThread().getContextClassLoader().getResourceAsStream( resource );
	}

	public List<String> getLines(String resource) throws IOException {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader( new InputStreamReader( openResource( resource ) ) );
			List<String> results = new ArrayList<String>();
			String line = reader.readLine();
			while ( line != null ) {
				//comment or empty line
				if ( line.length() != 0 && !line.startsWith( "#" ) ) {
					results.add( line );
				}
				line = reader.readLine();
			}
			return Collections.unmodifiableList( results );
		}
		finally {
			try {
				if (reader != null) reader.close();
			}
			catch ( Exception e ) {
				//we don't really care if we can't close
			}
		}
	}

	public Object newInstance(String cname, String... subpackages) {
		if (subpackages != null && subpackages.length > 0)
			throw new UnsupportedOperationException( "newInstance(classname, packages) not implemented" );

		final Class<?> clazz;
		try {
			clazz = ReflectHelper.classForName( cname );
		}
		catch ( ClassNotFoundException e ) {
			throw new SearchException("Unable to find class " + cname, e);
		}
		try {
			final Object instance = clazz.newInstance();
			if (instance instanceof ResourceLoaderAware) {
				( ( ResourceLoaderAware) instance ).inform( this );
			}
			return instance;
		}
		catch ( InstantiationException e ) {
			throw new SearchException("Unable to instanciate class with no-arg constructor: " + cname, e);
		}
		catch ( IllegalAccessException e ) {
			throw new SearchException("Unable to instanciate class with no-arg constructor: " + cname, e);
		}
	}
}
