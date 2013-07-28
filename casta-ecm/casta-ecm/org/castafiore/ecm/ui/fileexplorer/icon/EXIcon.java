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

package org.castafiore.ecm.ui.fileexplorer.icon;

import java.util.Map;

import org.castafiore.ecm.ui.fileexplorer.ExplorerView;
import org.castafiore.ecm.ui.fileexplorer.events.OpenFileEvent;
import org.castafiore.ecm.ui.fileexplorer.events.SelectFileEvent;
import org.castafiore.ecm.ui.fileexplorer.icon.dnd.DeactiveDragIconEvent;
import org.castafiore.ecm.ui.fileexplorer.icon.dnd.DropIconEvent;
import org.castafiore.ecm.ui.fileexplorer.icon.dnd.OverDragIconEvent;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.Draggable;
import org.castafiore.ui.Droppable;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.contextmenu.ContextMenuModel;
import org.castafiore.ui.ex.contextmenu.lazy.LazyContextMenuAble;
import org.castafiore.ui.ex.form.button.AbstractButton;
import org.castafiore.ui.js.JMap;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.File;
import org.castafiore.wfs.types.Shortcut;
/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 22, 2008
 */
public class EXIcon extends AbstractButton implements Draggable, Droppable , ICon{
	final static String[] CONTEXT_MENU_TITLES = new String[]{"Open", "Cut", "Delete", "Shortcut", "Properties", "Explore", "Checkout", "CheckIn"};
	
	//private File file;
	
	public EXIcon(String name, File file) {
		super(file.getAbsolutePath(), "div");
		addClass("feicon");
		
		EXContainer thumbnail = new EXContainer("thumbnail", "div");
		thumbnail.setStyleClass("fethumbnail");
		
		addChild(thumbnail);
		
		EXContainer imgCtn = new EXContainer("imgCtn", "div");
		imgCtn.setStyleClass("feimgCtn");
		thumbnail.addChild(imgCtn);
		
		EXContainer img = new EXContainer("img", "div");
		img.setStyleClass("feimg");
		imgCtn.addChild(img);
		
		
		EXContainer labelContainer = new EXContainer("labelContainer", "div");
		addChild(labelContainer);
		
		EXContainer exLabel = new EXContainer("label", "div");
		labelContainer.addChild(exLabel);

		setFile(file);
	}
	

	
	public String[] getAcceptClasses() {
		return  new String[]{ "file", "folder"};
	}


	


	public boolean isDroppableEnabled() {
		return true;
	}


	public File getFile() {
		return  SpringUtil.getRepositoryService().getFile(getName(), Util.getRemoteUser());
		//return file;
	}


	public void setFile(final File file) {
		//this.file = file;
		setName(file.getAbsolutePath());
		removeClass("folder");
		removeClass("file");
		getEvents().clear();
		
		addEvent(new SelectFileEvent(), Event.MOUSE_DOWN);
		addEvent(new DeactiveDragIconEvent(), Event.DND_OUT);
		
		addEvent(new OpenFileEvent(){

			@Override
			public void ClientAction(ClientProxy application) {
				application.mask(application.getAncestorOfType(ExplorerView.class));
				application.makeServerRequest(new JMap().put("path", file.getAbsolutePath()),this);
				
			}
			
		}, Event.DOUBLE_CLICK);
		if(file.isDirectory())
		{
			addClass("folder");
			addEvent(new DropIconEvent(), Event.DND_DROP);
			addEvent(new OverDragIconEvent(), Event.DND_OVER);
		}
		else
		{
			addClass("file");
		}
		
		
		//seting the label
		EXContainer exLabel = (EXContainer)getDescendentByName("label");
		String truncatedLabel = file.getName();
		if(truncatedLabel.length() > 10)
		{
			truncatedLabel = truncatedLabel.substring(0, 10) + "...";
		}
		
		
		exLabel.setText(truncatedLabel );
		setAttribute("title", file.getName());
		exLabel.setRendered(false);
		
		
		//setting the image
		EXContainer img = (EXContainer)getDescendentByName("img");
		String iconImage = getIconImage(file);
		img.setStyle("background", "transparent url("+ResourceUtil.getIconUrl(iconImage, "large")+") no-repeat scroll 0%");
		
		
		if(file.isLocked()){
			Container span = getDescendentByName("locked");
			if(span == null){
				span =new EXContainer("locked", "span");
				span.setText("LOCKED");
				span.setStyle("position", "relative");
				span.setStyle("top", "18px");
				span.setStyle("left", "4px");
				span.setStyle("font-weight", "bold");
				span.setStyle("color", "red");
				img.addChild(span);
			}
			span.setDisplay(true);
			
		}
		else{
			if(getDescendentByName("locked") != null){
				getDescendentByName("locked").setDisplay(false);
			}
		}
	} 
	
	
	public String getIconImage(File f)
	{
		if(f instanceof Shortcut){
			File file = ((Shortcut)f).getReferencedFile();
			return BaseSpringUtil.getBeanOfType(IconImageProvider.class).getIConImage(file);
		}else{
			return BaseSpringUtil.getBeanOfType(IconImageProvider.class).getIConImage(f);
		}
	}



	public ContextMenuModel getContextMenuModel() {
		final File f = this.getFile();
		ContextMenuModel model = new ContextMenuModel(){
			
			public Event getEventAt(int index) {
//				if(index == 0)
//					return new OpenFileEvent(){
//
//						@Override
//						public void ClientAction(ClientProxy application) {
//							
//							application.mask();
//							application.makeServerRequest(new JMap().put("path", f.getAbsolutePath()),this);
//						}
//					
//				};
//				else if(index == 1)
//					return new CopyFileEvent(){
//
//						public void ClientAction(ClientProxy application) {
//							
//							if(f instanceof CopyAble){
//								application.mask();
//								application.makeServerRequest( new JMap().put("path", f.getAbsolutePath()),this);
//							}
//							
//						}
//					
//				};
//				else if(index == 2)
//					return new DeleteFileEvent(){
//
//						@Override
//						public void ClientAction(ClientProxy application) {
//							application.mask();
//							application.makeServerRequest( new JMap().put("path", f.getAbsolutePath()),this);
//						}
//					
//				};
//				else if(index == 3)
//					return new CreateShortcutEvent(){
//						@Override
//						public void ClientAction(ClientProxy application) {
//							application.mask();
//							application.makeServerRequest(new JMap().put("path", f.getAbsolutePath()),this);
//						}
//				};
//				else if(index == 4)
//					return new ShowFilePropertiesEvent(){
//						@Override
//						public void ClientAction(ClientProxy application) {
//							application.mask();
//							application.makeServerRequest(new JMap().put("path", f.getAbsolutePath()),this);
//						}
//				};
//				else if(index == 5)
//					return new OpenDirEvent(){
//						@Override
//						public void ClientAction(ClientProxy application) {
//							application.mask();
//							application.makeServerRequest(new JMap().put("path", f.getAbsolutePath()),this);
//						}
//					
//				};
//				else if(index == 6){
//					return new CheckoutFileEvent();
//				}else {
//					return new CheckInFileEvent();
//				}
					
				return null;
					
			}

			public String getIconSource(int index) {
				// TODO Auto-generated method stub
				return "#";
			}

			public String getTitle(int index) {
				return CONTEXT_MENU_TITLES[index];
			}

			public int size() {
				if(true)
					return CONTEXT_MENU_TITLES.length;
				else
					return CONTEXT_MENU_TITLES.length -1;
			}
			
		};
		return model;
	}
	
	
	public void onSelect(Map<String, String> parameters)
	{
		
	}



	public JMap getDraggableOptions() {
		return new JMap().put("revert", "invalid").put("appendTo", "body").put("helper", "clone").put("opacity", "0.5").put("delay", 250);
	}
	
	public JMap getDroppableOptions(){
		return new JMap();
	}
}
