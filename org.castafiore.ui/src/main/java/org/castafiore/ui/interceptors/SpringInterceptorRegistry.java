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
package org.castafiore.ui.interceptors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.castafiore.ui.Container;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringInterceptorRegistry implements InterceptorRegistry,
		ApplicationContextAware {

	private ApplicationContext context_ = null;

	private Map<Class<?>, Interceptor> registry = null;

	private void init() {
		if (registry == null) {
			registry = new HashMap<Class<?>, Interceptor>();
			Map<String, Interceptor> beans = context_
					.getBeansOfType(Interceptor.class);

			// System.out.println(beans);

			Iterator<String> iter = beans.keySet().iterator();

			while (iter.hasNext()) {
				String sClass = iter.next().toString();

				try {
					Class<?> c = Thread.currentThread().getContextClassLoader()
							.loadClass(sClass);

					registry.put(c, (Interceptor) beans.get(sClass));
				} catch (ClassNotFoundException nfe) {

				}

			}
		}
	}

	public Interceptor[] getInterceptors(Container container) {
		init();
		List<Interceptor> interceptors = new ArrayList<Interceptor>(2);
		Iterator<Class<?>> interfaces = registry.keySet().iterator();

		while (interfaces.hasNext()) {
			Class<?> c = interfaces.next();

			if (c.isAssignableFrom(container.getClass())) {
				interceptors.add(registry.get(c));
			}
		}

		return interceptors.toArray(new Interceptor[interceptors.size()]);
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.context_ = applicationContext;

	}

}
