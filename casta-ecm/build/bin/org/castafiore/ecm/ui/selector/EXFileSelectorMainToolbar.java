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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;

public class EXFileSelectorMainToolbar extends EXFileSelectorToolBar {
	
	private List<String> history = new ArrayList<String>(5);

	public EXFileSelectorMainToolbar() {
		super("mainToolbar");
		addItem(new EXButtonToolbarItem("backButton", "ui-icon-circle-arrow-w"));
		getDescendentByName("backButton").addEvent(BACK, Event.CLICK);
		addItem(new EXInputItem("addressInput"));
		addItem(new EXButtonToolbarItem("forwardButton", "ui-icon-circle-arrow-e"));
		getDescendentByName("forwardButton").addEvent(FORWARD, Event.CLICK);
		addItem(new EXSeperatorItem());
		addItem(new EXInputItem("searchInput"));
		addItem(new EXButtonToolbarItem("searchButton", "ui-icon-search"));
		getDescendentByName("searchButton").addEvent(SEARCH, Event.CLICK);
		
		
	}
	
	public void setAddress(String address){
		((StatefullComponent)getDescendentByName("addressInput")).setValue(address);
	}
	
	
	public void back(){
		String address = ((StatefullComponent)getDescendentByName("addressInput")).getValue().toString();
		String as[] = StringUtil.split(address, "/");
		
		String newAddress = "";
		if(as != null && as.length > 1){
			history.add(address);
			for(int i = 0; i < as.length - 1; i ++){
				newAddress = newAddress + "/" + as[i];
				
				
			}
			Directory dir = BaseSpringUtil.getBeanOfType(RepositoryService.class).getDirectory(newAddress,  Util.getRemoteUser());
			getAncestorOfType(EXFileSelector.class).setDirectory(dir);
		}
	}
	
	public String getAddress(){
		return ((StatefullComponent)getDescendentByName("addressInput")).getValue().toString();
	}
	
	
	public void forward(){
		String newAddress = getAddress();
		if(history.size() > 0){
			newAddress = history.get(history.size()-1);
			history.remove(history.size()-1);
		}
		Directory dir = BaseSpringUtil.getBeanOfType(RepositoryService.class).getDirectory(newAddress, Util.getRemoteUser());
		getAncestorOfType(EXFileSelector.class).setDirectory(dir);
	}
	
	public void search(){
		String search = ((StatefullComponent)getDescendentByName("searchInput")).getValue().toString();
		if(StringUtil.isNotEmpty(search)){
			RepositoryService service = BaseSpringUtil.getBeanOfType(RepositoryService.class);
			//new QueryParameters().setf
			List<File> files = service.executeQuery(new QueryParameters().setEntity(File.class).setFullTextSearch(search), Util.getRemoteUser());
			getAncestorOfType(EXFileSelector.class).setSearchResult(files);
		}
	}
	
	public final static Event FORWARD = new Event(){

		public void ClientAction(ClientProxy container) {
			container.mask();
			container.makeServerRequest(this);
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			container.getAncestorOfType(EXFileSelectorMainToolbar.class).forward();
			return true;
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	public final static Event BACK = new Event(){

		public void ClientAction(ClientProxy container) {
			container.mask();
			container.makeServerRequest(this);
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			container.getAncestorOfType(EXFileSelectorMainToolbar.class).back();
			return true;
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	
	public final static Event SEARCH = new Event(){

		public void ClientAction(ClientProxy container) {
			container.mask();
			container.makeServerRequest(this);
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			container.getAncestorOfType(EXFileSelectorMainToolbar.class).search();
			return true;
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			// TODO Auto-generated method stub
			
		}
		
	};

}
