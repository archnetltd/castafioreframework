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
 package org.castafiore.ecm.ui.fileexplorer.tree;

import org.castafiore.ecm.ui.fileexplorer.events.OpenFileEvent;
import org.castafiore.ecm.ui.fileexplorer.events.SelectFileEvent;
import org.castafiore.ecm.ui.fileexplorer.icon.ICon;
import org.castafiore.ecm.ui.fileexplorer.icon.dnd.DeactiveDragIconEvent;
import org.castafiore.ecm.ui.fileexplorer.icon.dnd.DropIconEvent;
import org.castafiore.ecm.ui.fileexplorer.icon.dnd.OverDragIconEvent;
import org.castafiore.ui.Draggable;
import org.castafiore.ui.Droppable;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.tree.EXTreeComponent;
import org.castafiore.ui.js.JMap;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;

public class EXFileTreeComponent extends EXTreeComponent implements Draggable, Droppable, ICon{

	
	
	public EXFileTreeComponent(File file) {
		super(file.getAbsolutePath(), file.getName(), getIcon(file));
		setStyle("cursor", "pointer");
		
		setAttribute("c:path", file.getAbsolutePath());
		
		if(isLeaf(file)){
			addClass("file");
		}else{
			addClass("folder");
			addEvent(new DropIconEvent(), Event.DND_DROP);
			addEvent(new OverDragIconEvent(), Event.DND_OVER);
		}
		
		
		addEvent(new OpenFileEvent(){

			@Override
			public void ClientAction(ClientProxy application) {
				application.makeServerRequest(new JMap().put("path", application.getName()), this);
				
			}
			
		}, Event.CLICK);
		
		addEvent(new SelectFileEvent(), Event.MOUSE_DOWN);
		
		
		addEvent(new DeactiveDragIconEvent(), Event.DND_OUT);
	}
	
	public static String getIcon(File file){
		
		String icon = "http://www.extjs.com/deploy/dev/resources/images/default/tree/folder.gif";
		if(isLeaf(file)){
			icon = "http://www.extjs.com/deploy/dev/resources/images/default/tree/leaf.gif";
		}
		return icon;
	}
	
	private static boolean isLeaf(File file) {
		if (! (file instanceof Directory) || (file instanceof BinaryFile)){
			return true;
		}else{
			return false;
		}
	}

	

	public String[] getAcceptClasses() {
		return  new String[]{ "file", "folder"};
	}

	public JMap getDraggableOptions() {
		return new JMap().put("revert", "invalid").put("appendTo", "body").put("helper", "clone").put("opacity", "0.5").put("delay", 250);
	}
	
	public JMap getDroppableOptions(){
		return new JMap();
	}

	public File getFile() {
		return BaseSpringUtil.getBeanOfType(RepositoryService.class).getFile(getName(), Util.getRemoteUser());
	}

	public void setFile(File file) {
		setAttribute("c:path", file.getAbsolutePath());
		setIconUrl(getIcon(file));
		setLabel(file.getName());
		setName(file.getAbsolutePath());
		
	}

}
