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

package org.castafiore.ecm.ui.fileexplorer.button;

import org.castafiore.ui.ex.EXContainer;
import org.castafiore.utils.ResourceUtil;
/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 22, 2008
 */
public class EXButtonWithLabel extends EXContainer {
	

	public EXButtonWithLabel(String name,String label, String iconName, String iconSize) {
		super(name, "button");
		setIcon(iconName, iconSize);
		
		setStyle("line-height", "16px");
		setStyle("margin", " 0px 7px");
		setStyle("padding", "5px 0px 5px 26px");
		setStyle("cursor", "pointer");
		setStyle("float", "none");
		setText(label);
		//<div  style="background: ;line-height: 16px;margin: 0px 7px;padding: 5px 0px 5px 26px">Supprimer</div>
		
		
	}
	
	
	
	public void setIcon(String iconName, String iconSize)
	{
		setStyle("background-image", "url("+ getURL(iconName, iconSize) +")");
		setStyle("background-position", "7px 6px");
		setStyle("background-repeat", "no-repeat");
	}
	
	private String getURL(String iconName,String iconSize)
	{
		return ResourceUtil.getIconUrl(iconName, iconSize);
	}
}
