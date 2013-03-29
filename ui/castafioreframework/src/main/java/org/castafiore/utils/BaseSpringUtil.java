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
package org.castafiore.utils;

import java.util.HashMap;
import java.util.Map;

import org.castafiore.web.servlet.Castafiore;
import org.springframework.context.ApplicationContext;

public class BaseSpringUtil {

	public static ApplicationContext getApplicationContext() {

		return Castafiore.getCurrentContext();

	}

	public static <T extends Object> T getBeanOfType(Class<T> clazz) {

		if (containerBuffer.containsKey(clazz)) {
			return (T) getApplicationContext().getBean(
					containerBuffer.get(clazz));
		}
		String[] names = getApplicationContext().getBeanNamesForType(clazz);

		if (names != null && names.length > 0) {
			containerBuffer.put(clazz, names[0]);
			return (T) getApplicationContext().getBean(names[0]);
		}
		throw new RuntimeException("cannot find bean of type "
				+ clazz.getName() + " configured in any application context");
	}

	private static Map<Class<?>, String> containerBuffer = new HashMap<Class<?>, String>();

	public static <T> T getBean(String beanId) {

		return (T) getApplicationContext().getBean(beanId);

	}

}
