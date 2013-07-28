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
 package org.castafiore.webos;



import org.castafiore.ecm.ui.fileexplorer.icon.ICon;
import org.castafiore.ecm.ui.fileexplorer.icon.IconImageProvider;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Draggable;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.js.JMap;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.utils.EventUtil;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.File;
import org.castafiore.wfs.types.Shortcut;

public class EXDesktopShortcut extends EXContainer implements Draggable, ICon {

	public EXDesktopShortcut(String name,Shortcut shortcut) {
		super(name, "div");
		File f = shortcut.getReferencedFile();
		setAttribute("path", shortcut.getAbsolutePath());
		String iconImage =  BaseSpringUtil.getBeanOfType(IconImageProvider.class).getIConImage(f);
		iconImage = ResourceUtil.getIconUrl(iconImage, "large");
		setAttribute("path", f.getAbsolutePath());
		setText("<div style=\"background: " +
				"transparent url("+iconImage+") no-repeat scroll 0% 50%;margin:15px;\" class=\"feimg\" >" +
						"<div style=\"padding-top:44px;font-weight:bold;text-align:center;width:48px;height:12px;font-size:11px;\">"+f.getName()+"</div></div>");
		//setText("<span style=\"display: block;\">"+f.getName()+"</span><img src=\""+ResourceUtil.getIconUrl(iconImage, "large")+"\" /> ");
		addEvent(EventUtil.GENERIC_FORM_METHOD_EVENT, Event.DOUBLE_CLICK).setAttribute("ancestor", getClass().getName()).setAttribute("method", "onClick");
	}

	
	public JMap getDraggableOptions() {
		return new JMap().put("opacity", "0.5").put("delay", 250);
	}

	
	public File getFile() {
		return SpringUtil.getRepositoryService().getFile(getAttribute("path"), Util.getRemoteUser());
	}

	
	public void setFile(File file) {
		setAttribute("path", file.getAbsolutePath());
		
	}
	
	public void onClick(){
		File f = getFile();
		getAncestorOfType(WebOS.class).OnFileSelected(f);
	}
	
	

}
