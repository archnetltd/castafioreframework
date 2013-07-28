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

package org.castafiore.ecm.ui.fileexplorer.views;

import java.util.List;

import org.castafiore.ecm.ui.fileexplorer.ExplorerView;
import org.castafiore.ecm.ui.fileexplorer.events.CreateDirEvent;
import org.castafiore.ecm.ui.fileexplorer.events.UploadFileEvent;
import org.castafiore.ecm.ui.fileexplorer.icon.EXIcon;
import org.castafiore.ecm.ui.fileexplorer.icon.ICon;
import org.castafiore.ui.Droppable;
import org.castafiore.ui.UIException;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.ViewModel;
import org.castafiore.ui.ex.contextmenu.ContextMenuModel;
import org.castafiore.ui.ex.toolbar.ToolBarItem;
import org.castafiore.ui.js.JMap;
import org.castafiore.wfs.types.File;
/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 22, 2008
 */
public class ThumbnailView extends EXContainer implements ExplorerView, Droppable{

	private ViewModel model = null;
	
	public ThumbnailView() {
		super("ThumbnailView", "ul");
		addClass("thumbnail");
		//setStyle("border", "solid 1px silver");
		addClass("ui-widget-content");
		addClass("ui-corner-all");
		setStyle("height", "510px");
		setStyle("margin", "3px 0 0 3px");
		setStyle("overflow", "auto");
		refresh();
	}

	public ViewModel getModel() {
		return model;
	} 

	public void setModel(ViewModel model) {
		this.model = model;
		this.refresh();
	}
	
	public ViewModel<ToolBarItem> getActionToolbarModel() {
		return null;
	}

	@Override
	public void refresh() {
		if(model != null)
		{
			this.getChildren().clear();
			this.setRendered(false);
			
			for(int i = 0; i < model.size(); i ++)
			{
				EXContainer li = new EXContainer("", "li");
				addChild(li);
				li.addChild(model.getComponentAt(i, this));
			}
			super.refresh();
		}
	}

	

	public int refreshView(String address) throws UIException {
		
		FilesViewModel model = new FilesViewModel(address);
		setModel(model);
		return model.size();
	}

	public void refreshView(org.castafiore.wfs.types.Directory address) throws UIException {
		FilesViewModel model = new FilesViewModel(address);
		setModel(model);
	}

	public String getIdentifierString() {
		return getClass().getName();
	}
	
	
	public ICon getIcon(String filePath) {
		return (ICon)getDescendentByName(filePath);
	}

	
	public void addItem(File f) {
		EXContainer li = new EXContainer("", "li");
		addChild(li);
		li.addChild(new EXIcon(f.getName(),f));
		
	}


	public void showFiles(List<File> files) {
		this.getChildren().clear();
		this.setRendered(false);
		for(File f : files){
			EXContainer li = new EXContainer("", "li");
			addChild(li);
			li.addChild(new EXIcon(f.getName(),f));
		}
		
		
	}


	public ContextMenuModel getContextMenuModel() {
		//return this;
		return null;
	}


	public Event getEventAt(int index) {
		if(index == 0){
			return new UploadFileEvent();
		}else if(index == 1){
			return new CreateDirEvent();
		}else{
			return null;
		}
	}


	public String getIconSource(int index) {
		return null;
	}


	public String getTitle(int index) {
		if(index == 0){
			return "Upload File";
		}else{
			return "New Folder";
		}
	}


	public int size() {
		return 2;
	}

	public String[] getAcceptClasses() {
		return new String[]{"sd"};
	}

	public JMap getDroppableOptions() {
		return new JMap().put("greedy", true);
	}

//	@Override
//	public String[] getSelectedFiles() {
//		return new String[]{};
//	}
}
