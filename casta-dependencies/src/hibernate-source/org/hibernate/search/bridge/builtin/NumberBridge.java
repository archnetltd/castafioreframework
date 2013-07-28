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
 //$Id: NumberBridge.java 14707 2008-05-29 11:50:15Z hardy.ferentschik $
package org.hibernate.search.bridge.builtin;

import org.hibernate.search.bridge.TwoWayStringBridge;

/**
 * Base class for numbers - integer, double, etc.
 * 
 * @author Emmanuel Bernard
 */
public abstract class NumberBridge implements TwoWayStringBridge {
	public String objectToString(Object object) {
		return object != null ?
				object.toString() :
				null;
	}
}
