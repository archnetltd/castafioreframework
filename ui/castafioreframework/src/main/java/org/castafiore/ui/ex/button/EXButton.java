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

package org.castafiore.ui.ex.button;

import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.ex.EXContainer;


/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 22, 2008
 */
public class EXButton extends EXContainer implements  Button {

	public EXButton(String name, String label) {
		super(name, "button");
		
		setText(label);
		addClass("ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only").setAttribute("role", "button").setAttribute("aria-disabled", "false");
	
	}

	@Override
	public void onReady(ClientProxy proxy) {
		super.onReady(proxy);
		proxy.html("<span class='ui-button-text'>"+ getText() + "</span>").mouseover(proxy.clone().addClass("ui-state-hover")).mouseout(proxy.clone().removeClass("ui-state-hover").removeClass("ui-state-active").mousedown(proxy.clone().addClass("ui-state-active")));
	}
	
	
	
	
}
