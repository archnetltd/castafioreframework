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
 //$Id: ReflectionHelper.java 15605 2008-11-23 10:48:59Z hardy.ferentschik $
package org.hibernate.search.util;

import java.lang.reflect.Modifier;

import org.hibernate.annotations.common.reflection.XMember;
import org.hibernate.util.StringHelper;

/**
 * @author Emmanuel Bernard
 * @author Hardy Ferentschik
 */
public abstract class ReflectionHelper {

	private ReflectionHelper() {
	}

	/**
	 * Get attribute name out of member unless overriden by <code>name</code>.
	 *
	 * @param member <code>XMember</code> from which to extract the name.
	 * @param name Override value which will be returned in case it is not empty.
	 *
	 * @return attribute name out of member unless overriden by <code>name</code>.
	 */
	public static String getAttributeName(XMember member, String name) {
		return StringHelper.isNotEmpty( name ) ?
				name :
				member.getName(); //explicit field name
	}

	public static void setAccessible(XMember member) {
		if ( !Modifier.isPublic( member.getModifiers() ) ) {
			member.setAccessible( true );
		}
	}

	public static Object getMemberValue(Object bean, XMember getter) {
		Object value;
		try {
			value = getter.invoke( bean );
		}
		catch ( Exception e ) {
			throw new IllegalStateException( "Could not get property value", e );
		}
		return value;
	}
}
