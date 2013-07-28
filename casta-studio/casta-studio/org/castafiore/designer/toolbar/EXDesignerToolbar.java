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
 package org.castafiore.designer.toolbar;

import java.io.InputStream;
import java.util.Map;

import org.castafiore.designable.portal.PageContainer;
import org.castafiore.designer.EXCSSEditor;
import org.castafiore.designer.EXDesigner;
import org.castafiore.designer.config.EXConfigVerticalBar;
import org.castafiore.designer.layout.EXDroppableXYLayoutContainer;
import org.castafiore.designer.marshalling.DesignableDTO;
import org.castafiore.designer.marshalling.DesignableUtil;
import org.castafiore.designer.wizard.EXWizard;
import org.castafiore.designer.wizard.layout.EXLayoutGenerator;
import org.castafiore.ecm.ui.selector.EXFileSelector;
import org.castafiore.ecm.ui.selector.EXFileSelector.OnSelectFileHander;
import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.form.EXTextArea;
import org.castafiore.ui.ex.form.button.EXButtonSet;
import org.castafiore.ui.ex.form.button.EXIconButton;
import org.castafiore.ui.ex.form.button.Icons;
import org.castafiore.ui.ex.msgbox.EXPrompt;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.ui.ex.toolbar.EXToolBar;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.utils.EventUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.futil;
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;

public class EXDesignerToolbar extends EXToolBar {

	public EXDesignerToolbar(String name) {
		super(name);
		
		EXButtonSet portalSet = new EXButtonSet("portalSet");
		portalSet.setTouching(true);
		EXIconButton newPortal = new EXIconButton("newPortal", Icons.ICON_DOCUMENT);
		newPortal.addEvent(NEW_PORTAL_EVENT, Event.CLICK);
		portalSet.addItem(newPortal);
		newPortal.setAttribute("title", "Create a new Portal");
		EXIconButton openPortal = new EXIconButton("openPortal", Icons.ICON_FOLDER_OPEN);
		portalSet.addItem(openPortal);
		openPortal.setAttribute("title", "Open a Portal");
		openPortal.addEvent(OPEN_PORTAL_EVENT, Event.CLICK);
		EXIconButton savePortal = new EXIconButton("savePortal", Icons.ICON_DISK);
		portalSet.addItem(savePortal);
		savePortal.setAttribute("title", "Save current portal");
		savePortal.addEvent(SAVE_PORTAL_EVENT, Event.CLICK);
		
		addItem(portalSet);
		
		
		EXButtonSet pageSet = new EXButtonSet("pagelSet");
		pageSet.setTouching(true);
		EXIconButton newPage = new EXIconButton("newPage", Icons.ICON_DOCUMENT);
		pageSet.addItem(newPage);
		newPage.addEvent(NEW_PAGE_EVENT, Event.CLICK);
		newPage.setAttribute("title", "Add a new page to portal");
		EXIconButton openPage = new EXIconButton("openPage", Icons.ICON_FOLDER_OPEN);
		pageSet.addItem(openPage);
		openPage.setAttribute("title", "Open a page in current portal");
		openPage.addEvent(OPEN_PAGE_EVENT, Event.CLICK);
		EXIconButton savePage = new EXIconButton("savePage", Icons.ICON_DISK);
		pageSet.addItem(savePage);
		savePage.setAttribute("title", "Save current page");
		savePage.addEvent(SAVE_PAGE_EVENT, Event.CLICK);
		
		addItem(pageSet);
		
		
		EXIconButton cssEditor = new EXIconButton("cssEditor", "Css Editor");
		addItem(cssEditor);
		cssEditor.addEvent(SHOW_CSS_EDITOR, Event.CLICK);
		
		
		EXIconButton split = new EXIconButton("split", "Split screen");
		split.addEvent(EventUtil.GENERIC_FORM_METHOD_EVENT, Event.CLICK);
		split.setAttribute("method", "splitMode");
		split.setAttribute("ancestor", getClass().getName());
		addItem(split);
		
		
		
		
		EXIconButton switchMode = new EXIconButton("switchMode", "Switch to Edit mode");
		switchMode.setAttribute("mode", "view");
		 switchMode.addEvent(EventUtil.getEvent("switchMode", getClass(), EXDesigner.class), Event.CLICK);
		 addItem(switchMode);
		 
		 EXIconButton run = new EXIconButton("runPortal", "Execute portal");
			addItem(run);
			run.setDisplay(false);
		
	}
	

	
	public void switchMode(Container caller){
		String mode = caller.getAttribute("mode");
		if(mode.equals("edit")){
			caller.setAttribute("mode", "view");
			((EXIconButton)caller).setLabel("Switch to Edit mode");
			getAncestorOfType(EXDesigner.class).getDescendentByName("configBarContainer").setDisplay(false);
			getAncestorOfType(EXDesigner.class).getDescendentByName("portalLayoutContainer").setDisplay(true);
			
		}else{
			caller.setAttribute("mode", "edit");
			((EXIconButton)caller).setLabel("Switch to View mode");
			getAncestorOfType(EXDesigner.class).getDescendentByName("configBarContainer").setDisplay(true);
			getAncestorOfType(EXDesigner.class).getDescendentByName("portalLayoutContainer").setDisplay(false);
		}
	}
	
	public void splitMode(Container caller){
		getAncestorOfType(EXDesigner.class).getDescendentByName("configBarContainer").setDisplay(true);
		getAncestorOfType(EXDesigner.class).getDescendentByName("portalLayoutContainer").setDisplay(true);
	}

	private final static Event SHOW_CSS_EDITOR = new Event(){

		public void ClientAction(ClientProxy container) {
			container.mask();
			container.makeServerRequest(this);
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			EXDesigner designer = container.getAncestorOfType(EXDesigner.class);
			String portalName = designer.getCurrentName();
			String currentDir = designer.getCurrentDir();
			
			String cssPath = currentDir + "/" + portalName + "/" + StringUtil.split(portalName, ".")[0] + ".css";
			EXCSSEditor editor = null;
			editor = (designer.getAncestorOfType(EXDesigner.class).getDescendentOfType(EXCSSEditor.class));
			if(editor == null){
				editor =new EXCSSEditor("EXCSSEditor", cssPath);
				designer.addPopup(editor);
			}else{
				//editor.setAttribute("ecmPath", cssPath);
				editor.setDisplay(true);
				//String css = editor.loadCss();
				//request.put("setval", css);
				//request.put("edit_id", editor.getDescendentOfType(EXTextArea.class).getId());
			}
			return true;
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			// TODO Auto-generated method stub
			if(request.containsKey("setval")){
				String css = request.get("setval");
				//editAreaLoader.setValue("example_1", "new_value");
				container.appendJSFragment("editAreaLoader.setValue(\""+request.get("edit_id")+"\", \""+css+"\");");
			}
			
		}
		
	};
	
	
	private final static Event FULL_SCREEN = new Event(){

		public void ClientAction(ClientProxy container) {
			container.makeServerRequest(this);
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			Container c = container.getAncestorOfType(EXDesigner.class).getContainer(EXDesigner.CENTER);
			c.setStyle("position", "absolute");
			c.setStyle("top", "0px");
			c.setStyle("left", "0px");
			c.setStyle("width", "100%");
			c.setStyle("z-index", "5000");
			container.getAncestorOfType(EXToolBar.class).getDescendentByName("normalScreen").setDisplay(true);
			return true;
			
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	
	private final static Event NORMAL_SCREEN = new Event(){

		public void ClientAction(ClientProxy container) {
			container.makeServerRequest(this);
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			Container c = container.getAncestorOfType(EXDesigner.class).getContainer(EXDesigner.CENTER);
			c.setStyle("position", "static");
			//c.setStyle("top", "0px");
			//c.setStyle("left", "0px");
			//c.setStyle("width", "100%");
			//c.setStyle("z-index", "5000");
			container.setDisplay(false);
			return true;
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	private final static Event NEW_PORTAL_EVENT = new Event(){

		public void ClientAction(ClientProxy container) {
			container.makeServerRequest(this,"Create new Portal?");
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			
			EXPanel panel = new EXPanel("newPortalwizard", "Wizard to create a new portal");
			panel.setWidth(Dimension.parse("878px"));
			panel.setDraggable(true);
			panel.setShowCloseButton(true);
			EXWizard wizard = new EXWizard();
			wizard.setWindow(new EXLayoutGenerator());
			panel.setBody(wizard);
			//Application application = container.getRoot();
			//application.addChild(panel);
			container.getParent().addChild(panel);
			return true;
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	
	private final static Event SAVE_PORTAL_EVENT = new Event(){

		public void ClientAction(ClientProxy container) {
			container.mask();
			container.makeServerRequest(this);
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			
			container.getAncestorOfType(EXDesigner.class).save(null);
			return true;
			 
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	
	private final static Event OPEN_PORTAL_EVENT = new Event(){

		public void ClientAction(ClientProxy container) {
			container.mask();
			container.makeServerRequest(this);
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			try
			{
				
				container.getAncestorOfType(EXDesigner.class).open();
				
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
	
	
	
	
	private final static Event NEW_PAGE_EVENT = new Event(){

		public void ClientAction(ClientProxy container) {
			container.mask();
			container.makeServerRequest(this);
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			final EXDesigner designer = container.getAncestorOfType(EXDesigner.class);
			EXPrompt prompt = new EXPrompt("newFolder", "Create new page","Enter the name of the page:"){

				@Override
				public boolean onOk(Map<String, String> request) {
					String pageName = getInputValue();
					
					if(StringUtil.isNotEmpty(pageName)){
						try{
				 			
							EXDroppableXYLayoutContainer page = (EXDroppableXYLayoutContainer)DesignableUtil.getInstance("core:xylayout");
							page.setName(pageName);
							
							page.setAttribute("Text", ""); 
							
							page.setStyle("width", "100%");
							page.setStyle("min-height", "500px");
							page.setStyle("background-color", "beige");
							PageContainer pc = designer.getRootLayout().getDescendentOfType(PageContainer.class);
							
							pc.setPage(page);
							
							designer.getDescendentOfType(EXConfigVerticalBar.class).getNode(pc).refresh();
							designer.getDescendentOfType(EXConfigVerticalBar.class).getNode(pc).open();
						
							String file = designer.getCurrentDir() + "/" + designer.getCurrentName() + "/pages";
							Directory fFile = futil.getDirectory(file);
							
							BinaryFile bf = fFile.createFile(pageName, BinaryFile.class);
							
							bf.write(DesignableUtil.generateXML(page, null).getBytes());
							
							fFile.save();
							
							page.setAttribute("pagepath", bf.getAbsolutePath());
							return true;
						}catch(Exception e){
							throw new UIException(e);
						}
					
						
					}
					else
					{
						
						getInput().addClass("ui-state-error");
						return false;
						
					}
					
				}
				
			};
			designer.addPopup(prompt);
			
			return true;
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	
	private final static Event OPEN_PAGE_EVENT = new Event(){

		public void ClientAction(ClientProxy container) {
			container.mask();
			container.makeServerRequest(this);
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			EXDesigner designer = container.getAncestorOfType(EXDesigner.class);
			String path = designer.getCurrentDir() + "/" + designer.getCurrentName() + "/pages";
			
			EXFileSelector selector = new EXFileSelector("openPageSelector", EXDesigner.TREE_FILTER, EXDesigner.TABLE_FILTER, designer.getSelectorDir());
			Directory dir = BaseSpringUtil.getBeanOfType(RepositoryService.class).getDirectory(path, Util.getRemoteUser());
			selector.refreshUI(dir);
			selector.addOnSelectFileHandler(new OnSelectFileHander() {
				
				public void onSelectFile(String abosolutePath, EXFileSelector selector) {
					EXDesigner designer = selector.getAncestorOfType(EXDesigner.class);
					PageContainer pc = designer.getRootLayout().getDescendentOfType(PageContainer.class);
					BinaryFile bf = (BinaryFile)BaseSpringUtil.getBeanOfType(RepositoryService.class).getFile(abosolutePath, Util.getRemoteUser());
					try{
						InputStream in = bf.getInputStream();
						
						Container page = DesignableDTO.buildContainer(in, true);
						page.setAttribute("pagepath", bf.getAbsolutePath());
						pc.setPage(page);
						designer.getDescendentOfType(EXConfigVerticalBar.class).getNode(pc).refresh();
						selector.getAncestorOfType(EXPanel.class).remove();
					}catch(Exception e){
						throw new UIException(e);
					}
					
				}
			});
			EXPanel panel = new EXPanel("miniFileExplorer", "Select a page to open");
			panel.setWidth(Dimension.parse("670px"));
			panel.setBody(selector);
			designer.addPopup(panel);
			return true;
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	private final static Event SAVE_PAGE_EVENT = new Event(){

		public void ClientAction(ClientProxy container) {
			container.mask();
			container.makeServerRequest(this);
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			try{
				EXDesigner designer = container.getAncestorOfType(EXDesigner.class);
				PageContainer pageContainer = designer.getRootLayout().getDescendentOfType(PageContainer.class);
				Container page =  pageContainer.getPage();
				String path = page.getAttribute("pagepath");
				
				BinaryFile file = (BinaryFile)BaseSpringUtil.getBeanOfType(RepositoryService.class).getFile(path, Util.getRemoteUser());
				//OutputStream out = file.getOutputStream();
				String xml = DesignableUtil.generateXML(page, null);
				file.write(xml.getBytes());
				//out.flush();
				//out.close();
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

}
