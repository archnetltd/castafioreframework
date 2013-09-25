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

package org.castafiore.ui.ex.list;

import org.castafiore.ui.Encoder;
import org.castafiore.ui.FormComponent;
import org.castafiore.ui.UIException;
import org.castafiore.utils.ExceptionUtil;

/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *         kureem@gmail.com Oct 22, 2008
 */
public class DefaultSelectEncoder implements Encoder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Object encode(String rawString, FormComponent component)
			throws UIException {

		if (component instanceof EXSelect) {
			DataModel<?> model = ((EXSelect) component).getModel();

			try {
				int selectedIndex = Integer.parseInt(rawString);

				return model.getValue(selectedIndex);
			} catch (Exception e) {

			}
		} else {
			ExceptionUtil
					.getRuntimeException("this encoder works only on EXSelect components");

		}

		return null;
	}

}
