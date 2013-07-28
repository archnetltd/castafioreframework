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
 //$Id: ParameterizedBridge.java 15528 2008-11-06 23:11:06Z sannegrinovero $
package org.hibernate.search.bridge;

import java.util.Map;

/**
 * Allow parameter injection to a given bridge.
 * 
 * Implementors need to be threadsafe, but the
 * setParameterValues method doesn't need any
 * guard as initialization is always safe.
 *
 * @author Emmanuel Bernard
 */
public interface ParameterizedBridge {
	//TODO inject Properties? since the annotations cannot support Object attribute?
	void setParameterValues(Map parameters);
}
