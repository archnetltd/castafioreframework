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
 // $Id: FilterDef.java 15567 2008-11-16 15:06:50Z sannegrinovero $
package org.hibernate.search.engine;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.search.SearchException;
import org.hibernate.search.annotations.FilterCacheModeType;
import org.hibernate.search.annotations.FullTextFilterDef;

/**
 * A wrapper class which encapsulates all required information to create a defined filter.
 * 
 * @author Emmanuel Bernard
 */
//TODO serialization
public class FilterDef {
	private Method factoryMethod;
	private Method keyMethod;
	private Map<String, Method> setters = new HashMap<String, Method>();
	private final FilterCacheModeType cacheMode;
	private final Class<?> impl;
	private final String name;

	public FilterDef(FullTextFilterDef def) {
		this.name = def.name();
		this.impl = def.impl();
		this.cacheMode = def.cache();
	}

	public String getName() {
		return name;
	}

	public FilterCacheModeType getCacheMode() {
		return cacheMode;
	}

	public Class<?> getImpl() {
		return impl;
	}

	public Method getFactoryMethod() {
		return factoryMethod;
	}

	public void setFactoryMethod(Method factoryMethod) {
		this.factoryMethod = factoryMethod;
	}

	public Method getKeyMethod() {
		return keyMethod;
	}

	public void setKeyMethod(Method keyMethod) {
		this.keyMethod = keyMethod;
	}

	public void addSetter(String name, Method method) {
		if ( method.isAccessible() ) method.setAccessible( true );
		setters.put( name, method );
	}

	public void invoke(String parameterName, Object filter, Object parameterValue) {
		Method method = setters.get( parameterName );
		if ( method == null ) throw new SearchException( "No setter " + parameterName + " found in " + this.impl );
		try {
			method.invoke( filter, parameterValue );
		}
		catch (IllegalAccessException e) {
			throw new SearchException( "Unable to set Filter parameter: " + parameterName + " on filter class: " + this.impl, e );
		}
		catch (InvocationTargetException e) {
			throw new SearchException( "Unable to set Filter parameter: " + parameterName + " on filter class: " + this.impl, e );
		}
	}
}
