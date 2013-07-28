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

package org.castafiore.ecm.ui.fileexplorer;

import org.castafiore.ecm.ui.config.PathViewConfig;
import org.castafiore.ecm.ui.editor.EXFileEditor;
import org.castafiore.ecm.ui.fileexplorer.accordeon.FileExplorerAccordeonModel;
import org.castafiore.ecm.ui.fileexplorer.addressbar.EXAddressBar;
import org.castafiore.ecm.ui.fileexplorer.tree.EXFileTreeNode;
import org.castafiore.ecm.ui.fileexplorer.views.ThumbnailView;
import org.castafiore.security.User;
import org.castafiore.security.ui.SecurityAble;
import org.castafiore.security.ui.SecurityInterceptorHandler;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.UIException;
import org.castafiore.ui.ex.Corners;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.ViewModel;
import org.castafiore.ui.ex.layout.EXBorderLayoutContainer;
import org.castafiore.ui.ex.panel.EXOverlayPopupPlaceHolder;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.ui.ex.toolbar.EXToolBar;
import org.castafiore.ui.ex.toolbar.ToolBarItem;
import org.castafiore.ui.ex.tree.EXTree;
import org.castafiore.ui.navigation.EXAccordeon;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.futil;
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;
import org.castafiore.wfs.types.Shortcut;
import org.castafiore.wfs.types.Value;

/**
 * 
 *  @author Kureem Rossaye<br> kureem@gmail.com
 * Oct 22, 2008
 */
public class EXFileExplorer extends EXBorderLayoutContainer implements SecurityAble, Explorer {

	/**
	 * this should be a valid path for clipboard
	 */
	
	
	private Object clipBoard;

	private boolean initialised = false;	
	
	private EXToolBar addressToolBar;
	
	private ExplorerView defaultView;
	
	private String startDir;
		
	private RepositoryService repositoryService;
	
	private PathViewConfig pathViewConfig;
	
	private FileEditorFactory fileEditorFactory;
	
	//private EXplorerMenu menu;
	
	private String[] selectedFiles = null;

	public EXFileExplorer(String name) {
		super(name);
		setStyle("min-width", "600px");
		setStyle("min-height", "400px");
		getContainer(CENTER).setAttribute("width", "100%");
		getChild("popupContainer").remove();
		addChild(new EXOverlayPopupPlaceHolder("popupContainer"));
		
	}
	
	public void addPopup(Container popup) {
		getChild("popupContainer").addChild(popup.setStyle("z-index", "4000"));
		
	}
	
	public void setStartDir(String startDir) {
		this.startDir = startDir;
	}

	public void setDefaultView(ExplorerView defaultView) {
		this.defaultView = defaultView;
	}

	/*public void setAddress(Directory directory) throws UIException {
		setAddress(directory.getAbsolutePath());
		
	}*/

	public String getCurrentAddress() {
		return  getAddressBar().getAddress();
	}

	public RepositoryService getRepositoryService() {
		return repositoryService;
	}

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	public PathViewConfig getPathViewConfig() {
		return pathViewConfig;
	}

	public void setPathViewConfig(PathViewConfig pathViewConfig) {
		this.pathViewConfig = pathViewConfig;
	}

	public FileEditorFactory getFileEditorFactory() {
		return fileEditorFactory;
	}

	public void setFileEditorFactory(FileEditorFactory fileEditorFactory) {
		this.fileEditorFactory = fileEditorFactory;
	}
	
	public void setSelectedFiles(String[] paths){
		this.selectedFiles = paths;
	}
	
	public String[] getSelectedFiles(){
		return selectedFiles;
	}
	
	public Object getClipBoard() {
		return clipBoard;
	}

	public void setClipBoard(Object clipBoard) {
		this.clipBoard = clipBoard;
	}
	
	public String getStartDir() {
		if(startDir == null)
		{
			startDir = "/root/users/" + Util.getRemoteUser();
		}
		return startDir;
	}
	
	/**
	 * returns the current view
	 * @return
	 */
	public ExplorerView getView() {
		return getDescendentOfType(ExplorerView.class);
	}

	/**
	 * returns the address bar
	 * @return
	 */
	public EXAddressBar getAddressBar() {
		return (EXAddressBar) getDescendentByName("bar");
	}
	
	/**
	 * returns the address toolbar
	 * @return
	 */
	public EXToolBar getAddressToolBar() {
		if(addressToolBar != null){
			addressToolBar.setCorners(Corners.TOP);
			addressToolBar.setStyle("height", "20px");
		}
		return addressToolBar;
	}
	
	/**
	 * sets the address toolbar
	 * @param addressToolBar
	 */
	public void setAddressToolBar(EXToolBar addressToolBar) {
		this.addressToolBar = addressToolBar;
	}

	/**
	 * returns the access permission
	 */
	public String getAccessPermission() {
		return "*";
	}

	public String getRemoteUser() {
		return Util.getRemoteUser();
	}

	public EXTree getTree(){
		File startDir = BaseSpringUtil.getBeanOfType(RepositoryService.class).getFile(getStartDir(), Util.getRemoteUser());
		EXTree tree = new EXTree("s", new EXFileTreeNode(startDir,null));
		tree.setHeight(Dimension.parse("510px"));
		tree.addClass("ui-widget-content");
		tree.setStyle("margin", "10px 0px 10px 10px");
		tree.setWidth(Dimension.parse("204px"));
		tree.setStyle("border", "solid 1px silver");
		return tree;
	}
	
	public void init()throws UIException {
		if(!initialised)
		{
			try{
			addClass("explorer");
			EXAccordeon accordeon = new EXAccordeon("s");
			accordeon.setHeight(Dimension.parse("490px"));
			accordeon.setModel(new FileExplorerAccordeonModel());
			accordeon.setWidth(Dimension.parse("204px"));
			accordeon.addClass("ui-widget-content");
		//	accordeon.addClass("ui-corner-all");
			//accordeon.setStyle("padding", "5px");
			//accordeon.setStyle("margin", "10px 0px 10px 10px");
			
			accordeon.setStyle("border", "solid 1px silver").setStyle("padding", "10px");
			//addChild(getMenu(), TOP);
			addChild(getAddressToolBar(), TOP);
			addChild(accordeon, LEFT);
			getContainer(EXBorderLayoutContainer.CENTER).setStyle("vertical-align", "top");
			getContainer(EXBorderLayoutContainer.CENTER).setStyle("width", "100%");
			setAddress_(getStartDir());
			initialised = true;
			}catch(Exception e){
				throw new UIException(e);
			}
		}
		
	}

	/**
	 * reset the current address to the current address
	 */
	public void refresh() {
		try
		{
			setAddress_(getAddressBar().getAddress());
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public void setAddress_(String dir)throws UIException {
		getAddressBar().setAddress(dir);

		ExplorerView view = getView();
		try
		{
			int count =view.refreshView(dir);
			getAddressToolBar().getDescendentByName("count").setText(count + (count==0?" Item" : " Items"));
		}
		catch(Exception e)
		{
			ExplorerView def = getDefaultView();
			setView(def);
		}
	}
	
	/*public void setAddress(String dir)throws UIException {
		setAddress_(dir);
	}*/

	/**
	 * simply replaces the current view with the new one
	 * if parameter has the same identifier string as current view, we only refresh the current view
	 * @param view
	 * @throws UIException
	 */
	public void setView(ExplorerView view, Directory directory)throws UIException
	{
		ExplorerView currentView = getView();
		if(currentView == null || !currentView.getIdentifierString().equals(view.getIdentifierString()))
		{
			//clear center container
			EXContainer center = (EXContainer)getContainer(CENTER);
			center.getChildren().clear();
			center.setRendered(false);
			//add specified view in center container
			center.addChild(view);
			
			//refresh action tool bar
			refreshActionToolbar(view);		
			int count = 0;
			if(directory != null)
				count = view.refreshView(directory.getAbsolutePath());
			else
				count = view.refreshView(getAddressBar().getAddress());
			
			getAddressToolBar().getDescendentByName("count").setText(count + (count==0?" Item" : " Items"));
		}
		else if(currentView != null && currentView.getIdentifierString().equals(view.getIdentifierString()))
		{
			int count = 0;
			if(directory != null){
				count = currentView.refreshView(directory.getAbsolutePath());
				 getAddressBar().setAddress(directory.getAbsolutePath());
			}
			else
				count = currentView.refreshView(getAddressBar().getAddress());
			
			getAddressToolBar().getDescendentByName("count").setText(count + (count==0?" Item" : " Items"));
		}
		
		else
		{
			System.out.println("i have no idea when it can come here");
		}
		
	}
	
	private void refreshActionToolbar(ExplorerView view)
	{
		EXToolBar toolbar = (EXToolBar)getContainer(TOP).getChild("actiontoolbar");
		ViewModel<ToolBarItem> actiontoolbarModel = view.getActionToolbarModel();
		if(actiontoolbarModel != null)
		{
			if(toolbar == null)
			{
				toolbar = new EXToolBar("actiontoolbar", actiontoolbarModel);
				getContainer(TOP).addChild(toolbar);
				
			}
			else
			{
				toolbar.setModel(actiontoolbarModel);
			}
		}
	}
	
	public void setView(ExplorerView view)throws UIException
	{
		setView(view, null);
		
	}
	
	
	/**
	 * returns the default view when first initialising the explorer
	 * @return
	 * @throws UIException
	 */
	public ExplorerView getDefaultView()throws UIException
	{
		String defview = ThumbnailView.class.getName();
		String path =  "/root/users/"+Util.getLoggedOrganization()+"/Applications/e-Shop/" + Util.getLoggedOrganization() + "/preferences/defaultview";
		if(SpringUtil.getRepositoryService().itemExists(path)){
			Value val = (Value)futil.get(path);
			defview = val.getString();
		}
		if(defaultView == null){
			try{
			return (ExplorerView)Thread.currentThread().getContextClassLoader().loadClass(defview).newInstance();
			}catch(Exception e){
				return new ThumbnailView();
			}
		}
		else{
			return defaultView;
		}
	}
	
	
	

	public void OnFileSelected(File file) throws UIException {
		/**
		 * first priority, if shortcut, retrieve reference file, and iterate
		 */
		boolean hasaccess = false;

		
		
		
		if(file.getAbsolutePath().startsWith("/root/users")){
			String[] parts = StringUtil.split(file.getAbsolutePath(), "/");
			if(parts.length >=3){
				String user = parts[2];
				if(!Util.getRemoteUser().equalsIgnoreCase(user)){
					//if not his directory, he should be part of the organization at least
					try{
					if(SpringUtil.getSecurityService().isUserAllowed("*:" + Util.getLoggedOrganization(), Util.getRemoteUser())){
						hasaccess = true;
					}}catch(Exception e){
						throw new UIException(e);
					}
				}else{
					//meaning in his directory
					hasaccess = true;
				}
			}
		}

		if(Util.getRemoteUser().equalsIgnoreCase("archnetltd")){
			hasaccess = true;
		}
		
		if(!hasaccess){
			return;
		}
		if(file instanceof Shortcut){

			Shortcut shortcut = (Shortcut)file;
			
			String path = shortcut.getReference();
			
			File realFile = file.getSession().getFile( path );
			OnFileSelected(realFile);
			return;
		}
		
		try
		{
			
//			if(Util.getLoggedOrganization().equalsIgnoreCase("ebenbpo")){
//				throw new UIException("Cannot download or view any file until all payment to archnetltd has been made");
//			}
			EXFileEditor editor = new EXFileEditor();
			
			EXPanel panel = new EXPanel("fileEditorPanel", file.getAbsolutePath());
			panel.setBody(editor);
			panel.setWidth(Dimension.parse("900px"));
			editor.openFile(file, getCurrentAddress(), false);
			//view.init(SpringUtil.getRepositoryService().getDirectory(container.getAncestorOfType(Explorer.class).getCurrentAddress(), Util.getRemoteUser()));
			addPopup(panel);
			//Container openner = fileEditorFactory.getFileEditor(file,this, FileEditorFactory.EDIT_MODE);
			//addPopup(openner);
			return;
		}
		catch(Exception e)
		{
			
		}
		if(file.isDirectory())
		{
			
			setAddress_(file.getAbsolutePath());

		}
		else
		{
			throw new UIException("unable to find a suitable editor for file of type :" + file.getClazz());
		}		
	}

	public SecurityInterceptorHandler getInterceptor() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	
}