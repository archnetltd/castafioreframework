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
 package org.castafiore.designer.events;

import java.util.Map;

import org.castafiore.designer.config.ui.EXEventConfigForm;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;

public class ApplyEventTemplate implements Event {

	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}

	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		EXEventConfigForm form = container.getAncestorOfType(EXEventConfigForm.class);
		
		form.refreshTemplate();
		
		return true;
	}

	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
