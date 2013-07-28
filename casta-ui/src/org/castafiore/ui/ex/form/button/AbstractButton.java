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
 package org.castafiore.ui.ex.form.button;

import org.castafiore.FGConstants;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.ex.EXWidget;

public abstract class AbstractButton extends EXWidget implements FGConstants , Button{
	
	
	private final static String[] corners = new String[]{"top", "right", "bottom", "left", "all"};
	
	private boolean disabled = false;
	

	public AbstractButton(String name, String tagName) {
		super(name, tagName);
		addClass(BUTTON);
		addClass("ui-corner-all");
		addClass("ui-state-default");
		
		
	}
	
	
	public void setPriority(int i){
		removeClass("ui-priority-primary");
		removeChild("ui-priority-secondary");
		if(i == 1){
			addClass("ui-priority-primary");
		}else if(i == 2){
			addClass("ui-priority-secondary");
		}
	}
	

	public void setDisabled(boolean disabled){
		removeClass("ui-state-disabled");
		if(disabled){
			addClass("ui-state-disabled");
			setRendered(false);
		}
		this.disabled = disabled;
		
	}
	
	
	
	public boolean isDisabled() {
		return disabled;
	}
	
	public void onReady(ClientProxy proxy){
		if(!disabled)
			proxy.hover(proxy.clone().addClass("ui-state-hover"), proxy.clone().removeClass("ui-state-hover"));
	}

}
