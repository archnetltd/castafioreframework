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

package org.castafiore.ui.events;

import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.js.JMap;
 
/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *         kureem@gmail.com Oct 22, 2008
 */
public class SimpleResizableEvent implements Event {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMap options = null;

	public SimpleResizableEvent(JMap options) {
		super();
		this.options = options;
	}

	public void ClientAction(ClientProxy application) {

		application.resizeable(options);
	}

	public boolean ServerAction(Container component,
			Map<String, String> requestParameters) throws UIException {

		return false;
	}

	public void Success(ClientProxy component,
			Map<String, String> requestParameters) throws UIException {

	}

}
