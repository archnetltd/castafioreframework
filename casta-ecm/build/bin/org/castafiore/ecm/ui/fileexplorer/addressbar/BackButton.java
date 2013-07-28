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
 package org.castafiore.ecm.ui.fileexplorer.addressbar;

import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.form.button.EXIconButton;
import org.castafiore.ui.ex.form.button.Icons;
import org.castafiore.ui.ex.toolbar.EXToolBar;

public class BackButton extends EXIconButton{

	public BackButton(String name)
	{
		super(name, "", Icons.ICON_ARROW_1_W);
		setStyle("height", "10px");
		//setStyle("width", "0").setStyle("height", "0");
		addEvent(new Event(){

			public void ClientAction(ClientProxy application) {
				application.mask();
				application.makeServerRequest(this);
				
			}

			public boolean ServerAction(Container component, Map<String, String> requestParameters) throws UIException {
				
				EXAddressBar bar= component.getAncestorOfType(EXToolBar.class).getDescendentOfType(EXAddressBar.class);
				bar.back();
				return true;
			}

			public void Success(ClientProxy component, Map<String, String> requestParameters) throws UIException {
				
			}
			
		}, Event.CLICK);
	}
}
