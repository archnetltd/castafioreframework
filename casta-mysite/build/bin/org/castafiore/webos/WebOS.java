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

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.castafiore.designer.events.GroovyEvent;
import org.castafiore.ecm.ui.fileexplorer.EXFileExplorer;
import org.castafiore.ecm.ui.fileexplorer.Explorer;
import org.castafiore.ecm.ui.fileexplorer.ExplorerView;

import org.castafiore.ecm.ui.fileexplorer.icon.ICon;
import org.castafiore.security.User;
import org.castafiore.security.ui.EXLoginForm;
import org.castafiore.security.ui.OnLoginHandler;
import org.castafiore.security.ui.OnRegisterUserHandler;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Application;
import org.castafiore.ui.Container;
import org.castafiore.ui.Droppable;
import org.castafiore.ui.RefreshSentive;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXApplication;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.ViewModel;
import org.castafiore.ui.ex.toolbar.ToolBarItem;
import org.castafiore.ui.js.JMap;

import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.EventUtil;
import org.castafiore.utils.IOUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.futil;
import org.castafiore.wfs.iterator.FileIterator;
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;
import org.castafiore.wfs.types.Shortcut;

public class WebOS extends EXApplication implements Droppable , Event, Explorer, ExplorerView, OnLoginHandler, OnRegisterUserHandler, RefreshSentive{
	
	//public final static String WEB_OS_ROOT_DIR = "/root/webos";
	
	private String loggedUser = null;
	
	private Object clibBoard;
	
	private String[] selectedFiles;

	public WebOS() {
		super("webos");
		setData();
		addEvent(this, Event.DND_DROP);
		loggedUser =Util.getRemoteUser();
	}
	
	public void open(File file){
		
	}

	public void setData(){
		try{
			//String username = Util.getRemoteUser();
			if(false){
				this.getChildren().clear();
				this.setRendered(false);
				EXLoginForm loginForm = new EXLoginForm("loginForm").addOnLoginHandler(this).addOnRegisterUserHandler(this);
				addChild(loginForm);
			}else{
				addChild(new EXWebOSMenu());
			}
			
		}catch(Exception e){
			
		}
	}

	
	public String[] getAcceptClasses() {
		return  new String[]{ "file", "folder"};
	}

	
	public JMap getDroppableOptions() {
		return new JMap().put("greedy", false);
	}

	
	public void ClientAction(ClientProxy container) {
		JMap option = new JMap().put("source", ClientProxy.getDragSourceId());
		container.makeServerRequest(option,this);
		
	}

	
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		ICon source = (ICon)getDescendentById(request.get("source"));
		File f = source.getFile();
		
		Directory current = futil.getDirectory(getCurrentAddress());
		Shortcut shortcut = current.createFile("Shortcut to " + f.getName(), Shortcut.class);//new Shortcut();
		
		shortcut.setReference(f.getAbsolutePath());
		
		current.save();
		
		ICon icon = new EXDesktopShortcut(getCurrentAddress() + "/" + shortcut.getName(), shortcut);
		addChild(icon);
		
		return true;
	}

	
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

	
	public void OnFileSelected(File file) throws UIException {
		
		try{
			if(file instanceof Shortcut){
				Shortcut sc = (Shortcut)file;
				File ref = sc.getReferencedFile();
				if(ref instanceof BinaryFile){
					if(ref.getName().endsWith(".ptl")){
						
					}else if(ref.getName().endsWith(".app")){
						BinaryFile app = (BinaryFile)ref;
						String script = IOUtil.getStreamContentAsString(app.getInputStream());
						Map variable = new HashMap();
						variable.put("root", this);
						Binding binding = new Binding(variable);
						GroovyShell shell = new GroovyShell(binding);
						shell.evaluate(script);
						return;
					}
				}
				
			}
			
			EXFileExplorer fileExplorer = getDescendentOfType(EXFileExplorer.class);
			if(fileExplorer != null){
				fileExplorer.OnFileSelected(file);
			}else{
				getDescendentOfType(EXWebOSMenu.class).loadFileExplorer();
				OnFileSelected(file);
			}
		}catch(Exception e){
			throw new UIException(e);
		}
		
		
	}

	
	public Object getClipBoard() {
		return clibBoard;
	}

	
	public String getCurrentAddress() {
		return "/root/users/" + Util.getLoggedOrganization() + "/Desktop";
	}

	
	public String[] getSelectedFiles() {
		return selectedFiles;
	}

	
	public ExplorerView getView() {
		return this;
	}

	
	public void setClipBoard(Object o) {
		this.clibBoard = o;
		
	}

	
	public void setSelectedFiles(String[] paths) {
		
		this.selectedFiles = paths;
	}

	
	public void setView(ExplorerView view) {
		// TODO Auto-generated method stub
		//view.
	}

	
	public void setView(ExplorerView view, Directory address) {
		refreshView(address);
		
	}

	
	public void addPopup(Container popup) {
		addChild(popup);
		
	}

	
	public void addItem(File icon) {
		addChild(new EXDesktopShortcut(icon.getName(), (Shortcut)icon));
		
	}

	
	public ViewModel<ToolBarItem> getActionToolbarModel() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public ICon getIcon(String filePath) {
		return (ICon)getDescendentByName(filePath);
	}

	
	public String getIdentifierString() {
		return "webos:desktop";
	}

	
	public int refreshView(String address) throws UIException {
		Directory dir = SpringUtil.getRepositoryService().getDirectory(address, Util.getRemoteUser());
		refreshView(dir);
		return 0;
	}

	
	public void refreshView(Directory address) throws UIException {
		FileIterator desktopIcons = address.getFiles(Shortcut.class);
		List<Container> icons =  new ArrayList<Container>();
		ComponentUtil.getDescendentsOfType(this, icons, ICon.class);
		for(Container  c : icons){
			c.setDisplay(false);
		}
		
		while(desktopIcons.hasNext()){
			Shortcut next = (Shortcut)desktopIcons.next();
			ICon icon = (ICon)getDescendentByName(next.getAbsolutePath());
			if(icon != null){
				icon.setDisplay(true);
			}else{
				addChild(new EXDesktopShortcut(next.getAbsolutePath(), next));
			}
		}
	}

	
	public void showFiles(List<File> files) {
		List<Container> icons =  new ArrayList<Container>();
		ComponentUtil.getDescendentsOfType(this, icons, ICon.class);
		for(Container  c : icons){
			c.setDisplay(false);
		}
		for(File f : files){
			getDescendentByName(f.getAbsolutePath()).setDisplay(true);
		}
		
	}

	
	public void onReady(ClientProxy proxy) {
		if(Util.getRemoteUser() == null){
			proxy.redirectTo("youdo.jsp");
		}
		super.onReady(proxy);
		String js = "$('#" + proxy.getId() + "').width($(document).width())";
		js = js + ".height($(document).height())";
		proxy.appendJSFragment(js);
	}

	
	public void onLogin(Application app, String username) {
		app.getChildren().clear();
		app.setRendered(false);
		app.addChild(new EXWebOSMenu());
		//refreshView("/root/")
		WebOS os = (WebOS)app;
		//os.setv
		os.setView(os,SpringUtil.getRepositoryService().getDirectory("/root/users/" + Util.getRemoteUser() + "/desktop", Util.getRemoteUser()));
		loggedUser = username;
	}
	
	public void logOut(){
		this.getChildren().clear();
		setRendered(false);
		loggedUser = null;
		setData();
	}

	public void onRegisterUser(Application root, User user) {
		try{
			RepositoryService service = SpringUtil.getRepositoryService();
			Directory dir = service.getDirectory("/root/users/" + user.getUsername() , user.getUsername());
			Directory portalData = dir.createFile("portal-data", Directory.class);//new Directory();
			//portalData.setName("portal-data");
			portalData.makeOwner(user.getUsername());
			//dir.addChild(portalData);
			
			BinaryFile ptl = portalData.createFile("portal-template.ptl", BinaryFile.class);//new BinaryFile();
			//ptl.setName("portal-template.ptl");
			String portaltemplate = IOUtil.getStreamContentAsString(Thread.currentThread().getContextClassLoader().getResourceAsStream("developer-portal.ptl"));
			portaltemplate.replace("${username}", user.getUsername());
			ptl.write(portaltemplate.getBytes());
			ptl.makeOwner(user.getUsername());
			//portalData.addChild(ptl);
			Directory pages = ptl.createFile("pages", Directory.class);//new Directory();
			//pages.setName("pages");
			pages.makeOwner(user.getUsername());
			//ptl.addChild(pages);
			
			BinaryFile page = pages.createFile("home", BinaryFile.class);//new BinaryFile();
			//page.setName("home");
			String pageTemplate = IOUtil.getStreamContentAsString(Thread.currentThread().getContextClassLoader().getResourceAsStream("home.pg"));
			pageTemplate.replace("${username}", user.getUsername());
			page.write(pageTemplate.getBytes());
			page.makeOwner(user.getUsername());
			
			
			
			dir.save();
			
		}catch(Exception e){
			throw new UIException(e);
		}
		
		
	}

	@Override
	public void onRefresh() {
		
		
	}
	
	
}
