/*
 * Copyright (C) 2007-2008 Castafiore
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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;

/**
 * 
 * @author Kureem Rossaye<br>
 *         kureem@gmail.com June 27 2008
 */
public class EventUtil {

	private final static java.util.Properties common = Properties
			.getProperties("common");

	private static List<String> events = null;

	private static List<String> accepts = new ArrayList<String>();

	static {
		/**
		 * 
		 event.type.1=blur event.type.2=change event.type.3=click
		 * event.type.20=dblclick event.type.4=focus event.type.6=hover
		 * event.type.7=keydown event.type.8=keypress event.type.9=keyup
		 * event.type.11=mousedown event.type.12=mousemove
		 * event.type.13=mouseout event.type.14=mouseover event.type.15=mouseup
		 * event.type.17=select event.type.21=ready
		 */

		if (events == null) {
			accepts.add("event.type.1");
			accepts.add("event.type.2");
			accepts.add("event.type.3");
			accepts.add("event.type.20");
			accepts.add("event.type.4");
			accepts.add("event.type.6");
			accepts.add("event.type.7");
			accepts.add("event.type.8");
			accepts.add("event.type.9");
			accepts.add("event.type.11");
			accepts.add("event.type.12");
			accepts.add("event.type.13");
			accepts.add("event.type.14");
			accepts.add("event.type.15");
			accepts.add("event.type.17");
			accepts.add("event.type.21");

			events = new ArrayList<String>();

			Iterator<Object> iter = common.keySet().iterator();
			while (iter.hasNext()) {
				String key = iter.next().toString();
				if (accepts.contains(key))
					events.add(common.getProperty(key));
			}

			Collections.sort(events);

		}
	}

	public static List<String> getEvents() {
		return events;
	}

	public static int getEventType(String name) {
		Iterator<Object> iter = common.keySet().iterator();
		while (iter.hasNext()) {
			String key = iter.next().toString();

			String value = common.getProperty(key);
			if (value.equalsIgnoreCase(name)) {
				return Integer.parseInt(key.replace("event.type.", ""));
			}
		}

		throw new RuntimeException("unable to find event with name: " + name);

	}

	public static String getEventName(int type) {
		return common.getProperty("event.type." + type);
	}

	public static Event getEvent(final String methodName,
			final Class<? extends Container> ancestor,
			final Class<? extends Container> ancestorToMask) {
		Event event = new Event() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void ClientAction(ClientProxy container) {
				if (ancestorToMask != null) {
					container.mask(container.getAncestorOfType(ancestorToMask));
				} else {
					container.mask();
				}
				container.makeServerRequest(this);

			}

			public boolean ServerAction(Container container,
					Map<String, String> request) throws UIException {
				try {

					// String clazz = container.getAttribute("ancestor");
					// Class<Container> cls = (Class<Container>)
					// Thread.currentThread().getContextClassLoader().loadClass(ancestor);
					Object panel = container.getAncestorOfType(ancestor);
					Method method = null;
					try {
						method = panel.getClass().getMethod(methodName,
								Container.class);
						method.invoke(panel, container);
					} catch (NoSuchMethodException nse) {
						panel.getClass()
								.getMethod(methodName, (Class<?>[]) null)
								.invoke(panel, (Object[]) null);
					}

				} catch (Exception e) {
					throw new UIException(e);
				}

				return true;
			}

			public void Success(ClientProxy container,
					Map<String, String> request) throws UIException {
				// TODO Auto-generated method stub

			}

		};
		return event;
	}

	public final static Event GENERIC_FORM_METHOD_EVENT = new Event() {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void ClientAction(ClientProxy container) {
			container.mask();
			container.makeServerRequest(this);

		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {

			try {
				String methodName = container.getAttribute("method");
				String clazz = container.getAttribute("ancestor");
				Class<Container> cls = extracted(clazz);
				Object panel = container.getAncestorOfType(cls);
				Method method = null;
				try {
					method = panel.getClass().getMethod(methodName,
							Container.class);
					method.invoke(panel, container);
				} catch (NoSuchMethodException nse) {
					panel.getClass().getMethod(methodName, (Class<?>[]) null)
							.invoke(panel, (Object[]) null);
				}

			} catch (Exception e) {
				throw new UIException(e);
			}

			return true;
		}

		@SuppressWarnings("unchecked")
		private Class<Container> extracted(String clazz)
				throws ClassNotFoundException {
			return (Class<Container>) Thread.currentThread()
					.getContextClassLoader().loadClass(clazz);
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			// TODO Auto-generated method stub

		}

	};

}
