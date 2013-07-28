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
 // $Id: SearchConfiguration.java 14954 2008-07-17 20:43:10Z sannegrinovero $
package org.hibernate.search.cfg;

import java.util.Iterator;
import java.util.Properties;

import org.hibernate.annotations.common.reflection.ReflectionManager;

/**
 * Provides configuration to Hibernate Search
 *
 * @author Navin Surtani  - navin@surtani.org
 */
public interface SearchConfiguration {
	/**
	 * Returns an iterator over the list of indexed classes
	 *
	 * @return iterator of indexed classes.
	 */
	Iterator<Class<?>> getClassMappings();

	/**
	 * Returns a {@link java.lang.Class} from a String parameter.
	 * @param name
	 * @return An iterator of Classes.
	 */

	Class<?> getClassMapping(String name);

	/**
	 * Gets a configuration property from its name
	 * or null if not present
	 *
	 * @param propertyName - as a String.
	 * @return the property as a String
	 */
	String getProperty(String propertyName);

	/**
	 * Gets properties as a java.util.Properties object.
	 *
	 * @return a java.util.Properties object.
	 * @see java.util.Properties object
	 */
	Properties getProperties();

	/**
	 * Returns a reflection manager if already available in the environment
	 * null otherwise
	 *
     * @return ReflectionManager
	 */
	ReflectionManager getReflectionManager();


}
