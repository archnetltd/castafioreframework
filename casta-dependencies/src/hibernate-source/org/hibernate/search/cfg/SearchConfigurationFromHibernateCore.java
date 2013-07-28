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
 // $Id: SearchConfigurationFromHibernateCore.java 14954 2008-07-17 20:43:10Z sannegrinovero $
package org.hibernate.search.cfg;

import java.util.Iterator;
import java.util.Properties;
import java.util.NoSuchElementException;

import org.hibernate.annotations.common.reflection.ReflectionManager;
import org.hibernate.annotations.common.reflection.java.JavaReflectionManager;
import org.hibernate.mapping.PersistentClass;

/**
 * Search configuration implementation wrapping an Hibernate Core configuration
 *
 * @author Emmanuel Bernard
 */
public class SearchConfigurationFromHibernateCore implements SearchConfiguration {
	private final org.hibernate.cfg.Configuration cfg;
	private ReflectionManager reflectionManager;

	public SearchConfigurationFromHibernateCore(org.hibernate.cfg.Configuration cfg) {
		if ( cfg == null ) throw new NullPointerException( "Configuration is null" );
		this.cfg = cfg;
	}

	public Iterator<Class<?>> getClassMappings() {
		return new ClassIterator( cfg.getClassMappings() );
	}

	public Class<?> getClassMapping(String name) {
		return cfg.getClassMapping( name ).getMappedClass();
	}

	public String getProperty(String propertyName) {
		return cfg.getProperty( propertyName );
	}

	public Properties getProperties() {
		return cfg.getProperties();
	}

	public ReflectionManager getReflectionManager() {
		if ( reflectionManager == null ) {
			try {
				//TODO introduce a ReflectionManagerHolder interface to avoid reflection
				//I want to avoid hard link between HAN and Validator for such a simple need
				//reuse the existing reflectionManager one when possible
				reflectionManager =
						(ReflectionManager) cfg.getClass().getMethod( "getReflectionManager" ).invoke( cfg );

			}
			catch (Exception e) {
				reflectionManager = new JavaReflectionManager();
			}
		}
		return reflectionManager;
	}

	private static class ClassIterator implements Iterator<Class<?>> {
		private Iterator hibernatePersistentClassIterator;
		private Class<?> future;

		private ClassIterator(Iterator hibernatePersistentClassIterator) {
			this.hibernatePersistentClassIterator = hibernatePersistentClassIterator;
		}

		public boolean hasNext() {
			//we need to read the next non null one. getMappedClass() can return null and should be ignored
			if ( future != null) return true;
			do {
				if ( ! hibernatePersistentClassIterator.hasNext() ) {
					future = null;
					return false;
				}
				final PersistentClass pc = (PersistentClass) hibernatePersistentClassIterator.next();
				future = pc.getMappedClass();
			}
			while ( future == null );
			return true;
		}

		public Class<?> next() {
			//run hasNext to init the next element
			if ( ! hasNext() ) throw new NoSuchElementException();
			Class<?> result = future;
			future = null;
			return result;
		}

		public void remove() {
			throw new UnsupportedOperationException( "Cannot modify Hibenrate Core metadata" );
		}
	}
}
