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
 package org.castafiore.ecm.ui.selector;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.EXGrid;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.EventUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.iterator.FileFilter;
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.Drive;
import org.castafiore.wfs.types.File;


public class EXFileSelectorTable extends EXContainer  {

	
	private final static String[] LABELS = new String[]{"Name", "Owner", "Type", ""};
	private final static String[] WIDTHS = new String[]{"35%", "25%", "25%", "10%"};
	
	private FileFilter filter;
	
	public EXFileSelectorTable(String name, FileFilter filter) {
		super(name, "div");
		this.filter = filter;
		EXGrid header = new EXGrid("header", 4, 1);
		header.addClass("EXMiniExplorerFileListHeader ui-widget-header ui-corner-top");
		header.setAttribute("cellpadding", "0");
		header.setAttribute("cellspacing", "0");
		header.setAttribute("width", "97%");
		header.setAttribute("border", "0");
		
		for(int i = 0; i < header.getColumns(); i ++){
			header.getCell(i, 0).setText("<span class=\"YText\">"+LABELS[i]+"</span>");
			header.getCell(i, 0).setAttribute("width", WIDTHS[i]);
		}
		addChild(header);
		
		addChild(ComponentUtil.getContainer("body", "div", null, "EXMiniExplorerFileListBody ui-widget-content"));
		
		getChild("body").addChild(ComponentUtil.getContainer("bodyTable", "table", null, null));
		getDescendentByName("bodyTable").setAttribute("cellpadding", "0");
		getDescendentByName("bodyTable").setAttribute("cellspacing", "0");
		getDescendentByName("bodyTable").setAttribute("width", "100%");
		getDescendentByName("bodyTable").setAttribute("border", "0");
		getDescendentByName("bodyTable").setStyle("font-size", "12px");
	}
	
	public void selectFolderInTable(Container caller){
		String dir =caller.getParent().getAttribute("absolutepath");
		getAncestorOfType(EXFileSelector.class).setDirectory( BaseSpringUtil.getBeanOfType(RepositoryService.class).getDirectory(dir, Util.getRemoteUser()));
	}
	
	public void setDirectory(Directory dir){
		setAttribute("abspath", dir.getAbsolutePath());
		org.castafiore.wfs.iterator.FileIterator iter = dir.getFiles();
		clear();
		while(iter.hasNext()){
			addFile(iter.next());
		}
	}
	
	public void setSearchResult(List<File> files){
		
		Iterator<File> iter = files.iterator();
		clear();
		while(iter.hasNext()){
			addFile(iter.next());
		}
	}
	
	public boolean isDirectory(File file){
		return (file.getClazz().equals(Directory.class.getName()) || file.getClazz().equals(Drive.class.getName()));
	}
	
	public void addFile(File file){
		if(filter == null || filter.accept(file)){
			Container table = getDescendentByName("bodyTable");
			int size = table.getChildren().size();
			String cls = null;
			if((size % 2) == 0){
				cls = "ui-state-highlight";
			}
			Container tr = ComponentUtil.getContainer("icon", "tr", null, cls);
			tr.setAttribute("absolutepath", file.getAbsolutePath());
			table.addChild(tr);
			Container td = addTd(tr, "30%", file.getName());
			if(isDirectory(file)){
				td.setStyle("background-image", "url(castafiore/resource?spec=classpath:org/castafiore/resource/tree/img/folderopen.gif)");
				td.setStyle("background-position", "1% center");
				td.setStyle("background-repeat", "no-repeat");
				td.setStyle("padding-left", "18px");
				//td.setAttribute("method", "selectFolderInTable");
				//td.setAttribute("ancestor", getClass().getName());
				td.addEvent(EventUtil.getEvent("selectFolderInTable", getClass(), EXFileSelector.class), Event.CLICK);
				td.setStyle("cursor", "pointer");
				
				
			}
			
			addTd(tr, "20%", file.getOwner());
			
			String type =StringUtil.split(file.getClazz(), ".")[StringUtil.split(file.getClazz(), ".").length-1];
			if(file instanceof BinaryFile){
				type = ((BinaryFile)file).getMimeType();
			}
			Container tdIcon = addTd(tr, "20%", type);
			tdIcon.setText("");
			
			Container btn = ComponentUtil.getContainer("button", "div", "", "EXMiniFileExplorerButton ui-state-default");
			btn.setAttribute("abspath", file.getAbsolutePath());
			
			btn.addEvent(ON_SELECT, Event.CLICK);
			
			tdIcon.addChild(btn);
			btn.setText("<span class=\"ui-icon ui-icon-check\"></span>");
		}
		
	}
	
	public void clear(){
		Container table = getDescendentByName("bodyTable");
		table.getChildren().clear();
		table.setRendered(false);
	}
	private Container addTd(Container tr, String width, String label){
		Container td = ComponentUtil.getContainer("", "td", "<span class=\"YText\">"+label+"</span>", null);
		td.setAttribute("width", width);
		tr.addChild(td);
		return td;
	}

	public final static Event ON_SELECT = new Event(){

		public void ClientAction(ClientProxy container) {
			container.mask(container.getAncestorOfType(EXFileSelector.class));
			container.makeServerRequest(this);
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			container.getAncestorOfType(EXFileSelector.class).setCurrentDir(container.getAttribute("abspath"));
			container.getAncestorOfType(EXFileSelector.class).fireOnSelectFileHandlers(container.getAttribute("abspath"));
			return true;
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			// TODO Auto-generated method stub
			
		}
		
	};

}
