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
 // $Id: LoaderHelper.java 14954 2008-07-17 20:43:10Z sannegrinovero $
package org.hibernate.search.engine;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.common.util.ReflectHelper;

/**
 * @author Emmanuel Bernard
 */
public abstract class LoaderHelper {
	private static final List<Class> objectNotFoundExceptions;

	static {
		objectNotFoundExceptions = new ArrayList<Class>(2);
		try {
			objectNotFoundExceptions.add(
					ReflectHelper.classForName( "org.hibernate.ObjectNotFoundException" )
			);
		}
		catch (ClassNotFoundException e) {
			//leave it alone
		}
		try {
			objectNotFoundExceptions.add(
					ReflectHelper.classForName( "javax.persistence.EntityNotFoundException" )
			);
		}
		catch (ClassNotFoundException e) {
			//leave it alone
		}
	}

	public static boolean isObjectNotFoundException(RuntimeException e) {
		boolean objectNotFound = false;
		Class exceptionClass = e.getClass();
		for ( Class clazz : objectNotFoundExceptions) {
			if ( clazz.isAssignableFrom( exceptionClass ) ) {
				objectNotFound = true;
				break;
			}
		}
		return objectNotFound;
	}
}
