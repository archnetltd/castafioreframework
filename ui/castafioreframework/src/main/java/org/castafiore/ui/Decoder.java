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

package org.castafiore.ui;

import java.io.Serializable;

/**
 * A decoder in form components that converts object into string to be
 * serialized and rendered on the browser<br>
 * Implementations of this interface has role to convert the specified value for
 * the specified form component into a valid string
 * 
 * @author Kureem Rossaye<br>
 *         kureem@gmail.com June 27 2008
 */
public interface Decoder extends Serializable {

	/**
	 * Converts the object into a valid string to be rendered on the browser
	 * 
	 * @param value
	 *            The value to convert
	 * @param component
	 *            The form component holding this value
	 * @return The string to be rendered on the browser
	 * @throws UIException
	 *             Thrown if ever there is an exception
	 */
	public String decode(Object value, StatefullComponent component)
			throws UIException;

}
