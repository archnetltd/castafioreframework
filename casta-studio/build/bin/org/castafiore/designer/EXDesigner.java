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
 package org.castafiore.designer;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

import org.castafiore.designable.DesignableFactory;
import org.castafiore.designable.layout.DesignableLayoutContainer;
import org.castafiore.designable.portal.PageContainer;
import org.castafiore.designable.portal.PortalContainer;
import org.castafiore.designer.config.ConfigForm;
import org.castafiore.designer.config.EXConfigVerticalBar;
import org.castafiore.designer.config.ui.EXConfigItem;
import org.castafiore.designer.config.ui.EXMiniConfig;
import org.castafiore.designer.info.EXInfoPanel;
import org.castafiore.designer.marshalling.DesignableDTO;
import org.castafiore.designer.marshalling.DesignableUtil;
import org.castafiore.designer.portalmenu.EXSimplePortalMenu;
import org.castafiore.designer.service.DesignableService;
import org.castafiore.designer.toolbar.menu.MenuListener;
import org.castafiore.ecm.ui.finder.EXFinder;
import org.castafiore.ecm.ui.selector.EXFileSelector;
import org.castafiore.ecm.ui.selector.EXFileSelector.OnSelectFileHander;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Application;
import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.layout.EXBorderLayoutContainer;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.ui.js.JMap;
import org.castafiore.ui.js.Var;
import org.castafiore.ui.scripting.TemplateComponent;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ComponentVisitor;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.FileNotFoundException;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.futil;
import org.castafiore.wfs.iterator.FileFilter;
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;

public class EXDesigner extends EXBorderLayoutContainer implements Event{
	
	/**
	 * this represents the base directory to search a portal
	 */
	private String currentDir = null;
	
	/**
	 * this represents the name of the portal opened
	 */
	private String currentName = null;
	
	private String selectorDir = "/root";
	
	private String copy = null;
	

	public EXDesigner() {
		
		
		//change to NG
		super("EXDragAndDropDemo", "designer/sliced/designer.xhtml");
		//super("EXDragAndDropDemo", ResourceUtil.getDownloadURL("classpath", "org/castafiore/designer/EXDesigner.xhtml"));
		//addPopup(new EXInfoPanel(""));
		getDescendentByName("EXBorderLayoutContainerleft").setStyle("vertical-align", "top").setStyle("padding", "0").setStyle("background", "silver");
		EXConfigVerticalBar vb = new EXConfigVerticalBar();
		
		//vb.setResizable(true);
		vb.addClass("EXConfigVerticalBar");
		vb.setStyle("width", "200px");
		Container configBarContainer = ComponentUtil.getContainer("configBarContainer", "div");
		configBarContainer.setStyle("margin", "0");
		addChild(configBarContainer, LEFT);
		configBarContainer.addChild(vb);
		
		Var v = new Var("($(window).height() - 103) + 'px'");
		Var v1 = new Var("($(window).width() ) + 'px'");
		Container portalWrapper = new EXContainer("portalWrapper", "div").setStyle("overflow", "auto").setStyle("width", "974px").setStyle("height", v);
		//portalWrapper.setStyle("margin", "0").setStyle("padding", "10px").setStyle("height", "580px").setStyle("background-color", "black").setStyle("border", "ridge 6px silver");
		getContainer(CENTER).setAttribute("width", "100%").addClass("np");
		addChild(portalWrapper, CENTER);
		
		//addChild(new EXDesignerToolbar("toolbar"), TOP);
		//addChild(new EXDesignableFactoryToolBar(), TOP);
		addEvent(this, MISC);
		try{
		setAttribute("appid", getRoot().getName());
		}catch(Exception e){
			
		}
		setAttribute("eventid", this.hashCode() + "");
		
		addChild(new EXContainer("close", "div").addClass("ui-icon").addClass("ui-corner-all").addClass("ui-icon-close").addClass("ui-designer-close").addEvent(EXDesignableFactoryToolBar.CLOSE_EVENT, Event.CLICK));
	}
	
	public String weAre(String param){
		return "we are the champion";
	}
	
	public void copy(Container c){
		copy =DesignableUtil.generateXML(c, null);
		copy = copy.replace("__oid", "b");
	}
	
	public boolean paste(DesignableLayoutContainer parent)throws Exception{
		if(copy != null){
			Container component = DesignableUtil.buildContainer(new ByteArrayInputStream(copy.getBytes()), true);
			addComponent(parent, component);
			return true;
		}
		
		return false;
	}
	
	public void addComponent(DesignableLayoutContainer layoutContainer, Container component){
		String layoutData = layoutContainer.getPossibleLayoutData(component);
		component.setAttribute("layoutdata", layoutData);
		layoutContainer.addChild(component, layoutData);
		if(layoutContainer instanceof TemplateComponent){
			layoutContainer.setRendered(false);
		}
		
		getDescendentOfType(EXConfigVerticalBar.class).getNode(layoutContainer).refresh();
		getDescendentOfType(EXConfigVerticalBar.class).getNode(layoutContainer).open();
		layoutContainer.onAddComponent(component);
		selectItem(component);
	}
	
	
	public String getSelectorDir() {
		return selectorDir;
	}

	public void setSelectorDir(String selectorDir) {
		this.selectorDir = selectorDir;
	}

	public void setRootLayout(PortalContainer root, String dir, String name){
		PortalContainer container = getRootLayout();
		if(container != null){
			container.getParent().setRendered(false);
			container.remove();
		}
		Container portalLayoutContainer = ComponentUtil.getContainer("portalLayoutContainer", "div");
		if(getDescendentByName("portalLayoutContainer") != null)
			portalLayoutContainer =   getDescendentByName("portalLayoutContainer");
		else{
			getContainer(CENTER).getChild("portalWrapper").addChild(portalLayoutContainer);
			//portalLayoutContainer.setHeight(Dimension.parse("1500px"));
		}
		
		
		portalLayoutContainer.addChild(root);
		EXConfigVerticalBar vb = getContainer(LEFT).getDescendentOfType(EXConfigVerticalBar.class);
		vb.setComponent(root);
		vb.refresh();
		this.currentName = name;
		setCurrentDir(dir);
//		EXCSSEditor editor = getDescendentOfType(EXCSSEditor.class);
//		if(editor == null){
//			String portalName =getCurrentName();
//			String currentDir = getCurrentDir();
//			
//			
//			
//			String cssPath = currentDir + "/" + portalName + "/" + StringUtil.split(portalName, ".")[0] + ".css";
//			addPopup(new EXCSSEditor("editor", cssPath).setDisplay(false));
//		}
//		
//		EXConfigPanel panel = getDescendentOfType(EXConfigPanel.class);
//		if(panel == null){
//			panel = new EXConfigPanel();
//			addPopup(panel);
//		}
//			
//		String uniqueId = root.getAttribute("des-id");
//		String[] req = new String[]{};
//		Map<String, String[]> map   = new HashMap<String, String[]>();
//		if(StringUtil.isNotEmpty(uniqueId)){
//			
//			//DesignableFactory des =  container.getAncestorOfType(EXDesigner.class).getDescendentOfType(EXDesignableFactoryToolBar.class).getDesignable(uniqueId);
//			DesignableFactory des = BaseSpringUtil.getBeanOfType(DesignableService.class).getDesignable(uniqueId);
//		
//			//advancedConfigs = des.getAdvancedConfigs();
//			if(des == null)
//				throw new UIException("the designable factory " + uniqueId + " is probably not  configured");
//			req = des.getRequiredAttributes();
//			if(req == null){
//				req = new String[]{};
//			}
//			try{
//				Advised advised = (Advised) des;
//				Class<?> cls = advised.getTargetSource().getTargetClass();
//
//				//Class<?> cls = des.getClass().getSuperclass();
//				ConfigValues values =cls.getMethod("getRequiredAttributes", new Class[]{}).getAnnotation(ConfigValues.class);
//			
//				if(values != null){
//					ConfigValue[] cValues =  values.configs();
//					if(cValues != null ){
//						for(ConfigValue co : cValues){
//							map.put(co.attribute(), co.values());
//						}
//					}
//				}
//			}catch(Exception e){
//				e.printStackTrace();
//			}
//		}
//		panel.setContainer(root, req, map);
//		panel.setDisplay(false);
		
		
	}
	
	public PortalContainer getRootLayout(){
		return getContainer(CENTER).getChild("portalWrapper").getDescendentOfType(PortalContainer.class);
	} 
	
	public String getCurrentDir() {
		return currentDir;
	}
	
	

	public String getCurrentName() {
		return currentName;
	}
	
	
	public void openPage(BinaryFile bf){
		
		PageContainer pc = getRootLayout().getDescendentOfType(PageContainer.class);
		
		try{
			InputStream in = bf.getInputStream();
			
			Container page = DesignableDTO.buildContainer(in, true);
			page.setAttribute("pagepath", bf.getAbsolutePath());
			pc.setPage(page);
			getDescendentOfType(EXConfigVerticalBar.class).getNode(pc).refresh();
			getDescendentOfType(EXConfigVerticalBar.class).getNode(pc).open();
			try{
				getDescendentByName(page.getAttribute("__oid") + "_c-item").getParent().setStyle("border", "double steelBlue");
			}catch(Exception e){
				
			}
			
		}catch(Exception e){
			throw new UIException(e);
		}
	}

	public void setCurrentDir(String currentDir) {
		this.currentDir = currentDir;
		if(currentDir != null){
			Container buttonRun = getDescendentByName("runPortal");
			if(buttonRun != null){
				buttonRun.setDisplay(true);
				buttonRun.setAttribute("href", "portal.jsp?portal=" +currentDir + "/" + currentName);
				buttonRun.setAttribute("target", "_blank");
			}
		}
	}
	

	public void save(String name){
		try{
			PortalContainer lc = getRootLayout();
			
			
			RepositoryService service = BaseSpringUtil.getBeanOfType(RepositoryService.class);
			if(name != null){
				currentName = name;
			}
			
			if(this.currentDir != null){
				
				//overwriting already opened file
				BinaryFile bf = null;
				lc.setDefinitionPath(currentDir + "/"+  currentName);
				String xml = DesignableUtil.generateXML(lc, null);
				try{
					bf = (BinaryFile)service.getFile(currentDir + "/"+  currentName, Util.getRemoteUser());
					bf.write(xml.getBytes());
				}catch(FileNotFoundException nfe){
					Directory fCurrentDir = futil.getDirectory(currentDir);
					bf = fCurrentDir.createFile(currentName, BinaryFile.class);
					bf.write(xml.getBytes());
					//add directory for pages
					Directory dir =  bf.createFile("pages", Directory.class);

					//add empty css file
					
					String cssName = StringUtil.split(currentName, ".")[0] + ".css";
					BinaryFile css = bf.createFile(cssName, BinaryFile.class);
					
					css.write(" ".getBytes());

					Container link = ComponentUtil.getContainer("", "link", null, null);
					link.setAttribute("rel", "stylesheet");
					link.setAttribute("href", ResourceUtil.getDownloadURL("ecm", css.getAbsolutePath()));
					link.setAttribute("type", "text/css");
					lc.addChild(link);
					//update everything
					fCurrentDir.save();
					
				}
				
			}else{
				
				//open fileExplorer
				EXPanel panel = new EXPanel("miniFileExplorer", "Select a folder to save");
				panel.setWidth(Dimension.parse("670px"));
				panel.setBody(new EXFileSelector("myFileSelector", TREE_FILTER, TREE_FILTER, this.selectorDir));
				//Application app = getRoot();
				addPopup(panel);
				panel.getDescendentOfType(EXFileSelector.class).addOnSelectFileHandler(SELECT_FOLDER_TO_SAVE_HANDLER);
				panel.getDescendentOfType(EXFileSelector.class).getOkButton().addEvent(SELECT_FOLDER_EVENT, Event.CLICK);
				//what to do next??
				
				
			}
		}catch(Exception e)
		{
			throw new UIException(e);
		}
	}
	
	
	public void open(){
		EXPanel panel = new EXPanel("miniFileExplorer", "Select a file to open");
		panel.setWidth(Dimension.parse("670px"));
		panel.setBody(new EXFileSelector("myFileSelector", TREE_FILTER, TABLE_FILTER, this.selectorDir));
		addPopup(panel);
		panel.getDescendentOfType(EXFileSelector.class).addOnSelectFileHandler(SELECT_FILE_TO_OPEN);
		panel.setResizable(true, new JMap().put("maxHeight", 345).put("minHeight", 345));
	}
	
	public void open(BinaryFile bf)throws Exception{
		//BinaryFile bf = (BinaryFile)BaseSpringUtil.getBeanOfType(RepositoryService.class).getFile(absolutePath, Util.getRemoteUser());
		String currentDir = bf.getParent().getAbsolutePath();
		String currentName = bf.getName();
		String cssName = StringUtil.split(bf.getName(), ".")[0] + ".css";
		InputStream in = bf.getInputStream();
		PortalContainer ds = (PortalContainer)DesignableDTO.buildContainer(in, true);
		ds.setDefinitionPath(bf.getAbsolutePath());
		Container link = ComponentUtil.getContainer("", "link", null, null);
		link.setAttribute("rel", "stylesheet");
		BinaryFile cssFile = null;
		cssFile = (BinaryFile)bf.getFile(cssName);
		if(cssFile == null){
			
			cssFile = bf.createFile(cssName, BinaryFile.class);
			cssFile.write(" ".getBytes());
			bf.save();
		}
		link.setAttribute("href", ResourceUtil.getDownloadURL("ecm", cssFile.getAbsolutePath()));
		link.setAttribute("type", "text/css");
		ds.addChild(link);
		setRootLayout(ds, currentDir, currentName);
		
	}
	
	public BinaryFile open(String currentDir, String currentName){
		try{
			String cssName = StringUtil.split(currentName, ".")[0] + ".css";
			String absolutePath = currentDir + "/" + currentName;
			BinaryFile bf = (BinaryFile)BaseSpringUtil.getBeanOfType(RepositoryService.class).getFile(absolutePath, Util.getRemoteUser());
			InputStream in = bf.getInputStream();
			PortalContainer ds = (PortalContainer)DesignableDTO.buildContainer(in, true);
			ds.setDefinitionPath(absolutePath);
			Container link = ComponentUtil.getContainer("", "link", null, null);
			link.setAttribute("rel", "stylesheet");
			BinaryFile cssFile = null;
			cssFile = (BinaryFile)bf.getFile(cssName);
			if(cssFile == null){
				
				cssFile = bf.createFile(cssName, BinaryFile.class);
				cssFile.write(" ".getBytes());
				bf.save();
			}
			link.setAttribute("href", ResourceUtil.getDownloadURL("ecm", cssFile.getAbsolutePath()));
			link.setAttribute("type", "text/css");
			ds.addChild(link);
			setRootLayout(ds, currentDir, currentName);
			return bf;
		}catch(Exception e){
			throw new UIException(e);
		}
	}
	
	public void open(PortalContainer pc){
		setRootLayout(pc, null, null);
	}
	
	
	public final static FileFilter TREE_FILTER = new FileFilter(){

		public boolean accept(File pathname) {
			if(pathname.isDirectory() && !(pathname instanceof BinaryFile)){
				return true;
			}else{
				return false;
			}
		}
	};
	
	public final static FileFilter TABLE_FILTER = new FileFilter(){

		public boolean accept(File pathname) {
			if(pathname instanceof BinaryFile){
				return true;
			}else{
				return false;
			}
				
		}
		
	};
	
	public final static FileFilter FOLDER_FILTER = new FileFilter(){

		public boolean accept(File pathname) {
			if(pathname.getClazz().equalsIgnoreCase(Directory.class.getName())){
				return true;
			}else{
				return false;
			}
				
		}
		
	};
	
	
	public final static OnSelectFileHander SELECT_FOLDER_TO_SAVE_HANDLER = new OnSelectFileHander(){

		public void onSelectFile(String abosolutePath, EXFileSelector selector) {
			
			selector.setDirectory(BaseSpringUtil.getBeanOfType(RepositoryService.class).getDirectory(abosolutePath, Util.getRemoteUser()));
			
		}
		
	};
	
	public final static Event SELECT_FOLDER_EVENT = new Event(){

		public void ClientAction(ClientProxy container) {
			container.mask();
			container.makeServerRequest(this);
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			try{
				EXFileSelector selector = container.getAncestorOfType(EXFileSelector.class);
				String value = selector.getInput().getValue().toString();
				if(StringUtil.isNotEmpty(value)){
					
					EXDesigner designer = container.getAncestorOfType(EXDesigner.class);
					String currentDir = selector.getCurrentDir();
					designer.setCurrentDir(currentDir );
					designer.save(value);
					
					PortalContainer root = designer.getRootLayout();
					root.getDescendentOfType(EXSimplePortalMenu.class).saveCurrentPages(currentDir + "/" + value);
					
					
					selector.getAncestorOfType(EXPanel.class).remove();
				}else{
					selector.getInput().addClass("ui-state-error");
				}
			}catch(Exception e){
				throw new UIException(e);
			}
			return true;
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	
	public final static OnSelectFileHander SELECT_FILE_TO_OPEN = new OnSelectFileHander(){

		public void onSelectFile(String abosolutePath, EXFileSelector selector) {
			try{
				Application root = selector.getRoot();
				EXDesigner designer = root.getDescendentOfType(EXDesigner.class);
				String parent = "";
				String[] parts = StringUtil.split(abosolutePath, "/");
				if(parts.length > 1){
					for(int i = 0; i < parts.length -1; i ++){
						parent = parent + "/" + parts[i];
					}
				}
				
				String name = parts[parts.length-1];
				String cssName = StringUtil.split(name, ".")[0] + ".css";
				
				
				
				BinaryFile bf = (BinaryFile)BaseSpringUtil.getBeanOfType(RepositoryService.class).getFile(abosolutePath, Util.getRemoteUser());
				InputStream in = bf.getInputStream();
				
				PortalContainer ds = (PortalContainer)DesignableDTO.buildContainer(in, true);
				ds.setDefinitionPath(abosolutePath);
				
				Container link = ComponentUtil.getContainer("", "link", null, null);
				link.setAttribute("rel", "stylesheet");
				BinaryFile cssFile = null;
				cssFile = (BinaryFile)bf.getFile(cssName);
				if(cssFile == null){
					cssFile = bf.createFile(cssName, BinaryFile.class);
					cssFile.write(" ".getBytes());
					bf.save();
				}
				link.setAttribute("href", ResourceUtil.getDownloadURL("ecm", cssFile.getAbsolutePath()));
				link.setAttribute("type", "text/css");
				ds.addChild(link);
				
				designer.setRootLayout(ds, parent, name);
				selector.getAncestorOfType(EXPanel.class).remove();
			}catch(Exception e){
				throw new UIException(e);
			}
			
		}
		
	};
	
	
	public void handleMenuItem(Map<String, String> request){
		new MenuListener().handleMenu(request.get("menuitem"), this);
	}

	@Override
	public void ClientAction(ClientProxy container) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		if(request.containsKey("ismenu")){
			handleMenuItem(request);
			return true;
		}
		
		String selItem = request.get("selItem");
		String style = request.get("style");
		String value = request.get("value");
		
		Container item = getDescendentById(selItem);
		
		if(item != null){
			
			if(style.equalsIgnoreCase("background-image")){
				String url = ResourceUtil.extractUrlFromStyle(item.getStyle(style));
				if(url.startsWith("castafiore/resource?spec=ecm:")){
					url = url.replace("castafiore/resource?spec=ecm:", "");
				}else{
					url = null;
				}
				showFinderForBackgroundImage("/root/users/" + Util.getRemoteUser(), url);
			}else if(style.equalsIgnoreCase("inner-text")){
				showConfigForm(item);
			}else if(style.equalsIgnoreCase("info-panel")){
				//showConfigForm(startDir, opentoDir, item)
				showHideInfoPanel(item);
			}else if(style.equalsIgnoreCase("selectitem")){
				selectItem(item);
			}else if(style.equalsIgnoreCase("dimension")){
				item.setStyle("width", request.get("width").toString() + "px");
				item.setStyle("height", request.get("height").toString() + "px");
			}
			
			else{
				item.setStyle(style, value);
			}
			
			
			
		}else{
			//throw new UIException("Please select a component");
		}
		return true;
	}
	public void showHideInfoPanel(Container selectedItem){
		EXInfoPanel panel = getDescendentOfType(EXInfoPanel.class);
		if(panel == null){
			addPopup(new EXInfoPanel("", selectedItem));
		}else{
			if(panel.isVisible()){
				panel.setDisplay(false);
			}else{
				panel.setDisplay(true);
			}
		}
	}
	
	public void selectItem(Container item){
		//set infopanel
		//remake toolbar
		//show info somewhere
		//make appearance in vertical toolbar.
		//
		setAttribute("selitem", item.getId());
		EXInfoPanel panel = getDescendentOfType(EXInfoPanel.class);
		if(panel != null){
			panel.setItem(item);
		}
		
		EXMiniConfig config = getDescendentOfType(EXMiniConfig.class);
		if(config != null){
			config.setContainer(item);
		}
		final String id =item.getAttribute("__oid") + "_c-item";
		ComponentUtil.iterateOverDescendentsOfType(getChild("EXBorderLayoutContainerleft"), EXConfigItem.class, new ComponentVisitor() {
			
			@Override
			public void doVisit(Container c) {
				String st = c.getStyleClass();
				
					
				if(c.getName().equalsIgnoreCase(id)){
					c.addClass("selectedconfigbar");
				}else{
					if(st.contains("selectedconfigbar")){
						c.removeClass("selectedconfigbar");
					}
				}
				
				
			}
		});
		
		EXConfigItem citem = (EXConfigItem)getChild("EXBorderLayoutContainerleft").getDescendentByName(id);
		if(citem != null){
			citem.getAncestorOfType(EXConfigVerticalBar.class).open();
		}
	}
	
	public Container getSelectedItem(){
		String id = getAttribute("selitem");
		return getDescendentById(id);
		
		
	}
	
	public void showFinderForBackgroundImage(String startDir, String opentoDir){
		
		EXFinder finder = new EXFinder("finderForImage", EXDesigner.TABLE_FILTER, new EXFinder.SelectFileHandler() {
			
			@Override
			public void onSelectFile(File file, EXFinder finder) {
				 Container c  =getSelectedItem();
				 if(c!= null)
					 c.setStyle("background-image", "url('"+ResourceUtil.getDownloadURL("ecm", file.getAbsolutePath())+"')");
				 else
					 throw new UIException("Please select a component");
			}
		}, startDir);
		
		finder.open(opentoDir);
		
		addPopup(finder);
		//finder.getDescendentOfType(EXFinderColumn.class).setThumbnailView();
	}
	
	public void showConfigForm(Container item){
		String uniqueId = item.getAttribute("des-id");
		
		DesignableFactory factory  = SpringUtil.getBeanOfType(DesignableService.class).getDesignable(uniqueId);
		Map<String, ConfigForm> configs = factory.getAdvancedConfigs();
		if(configs.keySet().size() > 0){
			ConfigForm form = configs.get(configs.keySet().iterator().next());
			form.setContainer(item);
			if(form instanceof EXPanel){
			
				addPopup(form);
			}else{
				EXPanel panel = new EXPanel("", "Configuration of " + item.getName());
				panel.setBody(form);
				addPopup(panel);
			}
		}
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}
	
	//public final static ON_SELECT_DIR

}
