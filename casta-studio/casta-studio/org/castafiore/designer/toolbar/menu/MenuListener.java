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
 package org.castafiore.designer.toolbar.menu;

import java.io.InputStream;
import java.util.Map;

import org.castafiore.designable.portal.PageContainer;
import org.castafiore.designable.portal.PortalContainer;
import org.castafiore.designer.EXCSSEditor;
import org.castafiore.designer.EXDesigner;
import org.castafiore.designer.config.EXConfigVerticalBar;
import org.castafiore.designer.dataenvironment.ui.EXDataEnvironment;
import org.castafiore.designer.marshalling.DesignableDTO;
import org.castafiore.designer.marshalling.DesignableUtil;
import org.castafiore.designer.newpage.EXNewPage;
import org.castafiore.designer.newportal.EXNewPortal;
import org.castafiore.designer.projects.MyProjects;
import org.castafiore.designer.script.ui.EXScriptEditor;
import org.castafiore.ecm.ui.finder.EXFinder;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Application;
import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXTextArea;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.ui.ex.panel.Panel;
import org.castafiore.ui.menu.EXMenu;
import org.castafiore.ui.menu.EXMenuItem;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;

public class MenuListener implements org.castafiore.ui.menu.MenuListener{

	
	public void onSelectItem(EXMenuItem menuItem, EXMenu menu) {
		String name = menuItem.getName();
		if(name.equalsIgnoreCase("new page")){
			newPage(menuItem);
		}else if(name.equalsIgnoreCase("save page")){
			savePage(menuItem);
		}else if(name.equalsIgnoreCase("open page")){
			openPage(menuItem);
		}else if(name.equalsIgnoreCase("execute portal")){
			executePortal();
		}else if(name.equalsIgnoreCase("css editor")){
			cssEditor(menuItem);
		}else if(name.equalsIgnoreCase("save portal")){
			savePortal(menuItem);
		}else if(name.equalsIgnoreCase("new template")){
			selectNewTemplate(menuItem);
		}
		
	}
	
	
	public void selectNewTemplate(Container designer){
		Panel panel = SpringUtil.getBeanOfType(EXTemplatesWindow.class);
		designer.getAncestorOfType(EXDesigner.class).addPopup(panel);
	}
	
	public void handleMenu(String name, Container designer){
		if(name.equalsIgnoreCase("new page")){
			newPage(designer);
		}else if(name.equalsIgnoreCase("save page")){
			savePage(designer);
		}else if(name.equalsIgnoreCase("open page")){
			openPage(designer);
		}else if(name.equalsIgnoreCase("execute portal")){
			executePortal();
		}else if(name.equalsIgnoreCase("css editor")){
			cssEditor(designer);
		}else if(name.equalsIgnoreCase("save portal")){
			savePortal(designer);
		}else if(name.equalsIgnoreCase("open portal")){
			openPortal(designer);
		}else if(name.equalsIgnoreCase("script editor")){
			editScript(designer, false);
		}else if(name.equalsIgnoreCase("javascript editor")){
			editScript(designer, true);
		}else if(name.equalsIgnoreCase("close designer")){
			
		}else if(name.equalsIgnoreCase("new portal")){
			this.newPortal(designer);
		}else if(name.equalsIgnoreCase("preview portal")){
			preview(designer);
		}else if(name.equalsIgnoreCase("new page from template")){
			newPageFromTemplate(designer);
		}else if(name.equalsIgnoreCase("dataenvironment")){
			dataenv(designer);
		}else if(name.equalsIgnoreCase("new template")){
			selectNewTemplate(designer);
		}
	}
	
	public void dataenv(Container c){
		EXDesigner designer = c.getAncestorOfType(EXDesigner.class);
		PortalContainer pc = designer.getRootLayout();
		EXDataEnvironment env = new EXDataEnvironment("dataenv", pc);
		env.setStyle("width", "900px").setStyle("z-index", "1000");
		designer.addPopup(env);
	}
	
	
	public void closeDesigner(Container container){
		
		final EXDesigner designer = container.getAncestorOfType(EXDesigner.class);
		designer.remove();
		
	}
	
	public void newPageFromTemplate(Container container){
		EXDesigner designer = container.getAncestorOfType(EXDesigner.class);
		String path = designer.getCurrentDir() + "/" + designer.getCurrentName() + "/pages";
		
		EXFinder finder = new EXFinder("openPageSelector", EXDesigner.TABLE_FILTER, new EXFinder.SelectFileHandler() {
			
			@Override
			public void onSelectFile(File file, EXFinder finder) {
				final EXDesigner designer = finder.getAncestorOfType(EXDesigner.class);
				final PageContainer pc = designer.getRootLayout().getDescendentOfType(PageContainer.class);
				final BinaryFile bf = (BinaryFile)file;
				try{
					InputStream in = bf.getInputStream();
					
					final Container page = DesignableDTO.buildContainer(in, true);
					finder.remove();
					
					EXDynaformPanel panel = new EXDynaformPanel("name", "Name of Page");
					panel.setStyle("width", "450px");
					designer.addPopup(panel);
					panel.addField("Name of Page :", new EXInput("pageName"));
					panel.addButton(new EXButton("cancel", "Cancel"));
					panel.addButton(new EXButton("save", "Save"));
					panel.getDescendentByName("cancel").addEvent(Panel.CLOSE_EVENT, Event.CLICK);
					panel.getDescendentByName("save").addEvent(new Event(){

						@Override
						public void ClientAction(ClientProxy container) {
							container.mask().makeServerRequest(this);
							
						}

						@Override
						public boolean ServerAction(Container container,
								Map<String, String> request) throws UIException {
							try{
								//name of file
							String name = container.getAncestorOfType(EXDynaformPanel.class).getField("pageName").getValue().toString();
							//pages directory
							Directory pages = bf.getParent();
							
							pages = SpringUtil.getRepositoryService().getDirectory(pages.getAbsolutePath(), Util.getRemoteUser());
							//new file to save new page
							BinaryFile file = pages.createFile(name, BinaryFile.class);
							
							//change pagepath
							page.setAttribute("pagepath",file.getAbsolutePath());
							
							//generate xml
							String xml = DesignableUtil.generateXML(page, null);
							//write to file
							file.write(xml.getBytes());
							//save file
							file.save();
							
							//open file
							pc.setPage(page);
							designer.getDescendentOfType(EXConfigVerticalBar.class).getNode(pc).refresh();
							designer.getDescendentOfType(EXConfigVerticalBar.class).getNode(pc).open();
							try{
								designer.getDescendentByName(page.getAttribute("__oid") + "_c-item").getParent().setStyle("border", "double steelBlue");
							}catch(Exception e){
								
							}
							
							}catch(Exception e){
								throw new UIException(e);
							}
							
							container.getAncestorOfType(EXDynaformPanel.class).remove();
							return true;
						}

						@Override
						public void Success(ClientProxy container,
								Map<String, String> request) throws UIException {
							// TODO Auto-generated method stub
							
						}
						
					}, Event.CLICK);
					
					
				}catch(Exception e){
					throw new UIException(e);
				}
				
			}
		}, path);
		
		designer.addPopup(finder);
	}
	
	public void savePortal(Container container){
		container.getAncestorOfType(EXDesigner.class).save(null);
	}
	
	
	public void preview(Container source){
		EXPanel panel = new EXPanel("prew", "Copy and paste content in your website");
		
		Application app = source.getRoot();
		String contextPath = app.getContextPath();
		String serverPort = app.getServerPort();
		String servaerName = app.getServerName();
		EXDesigner d = source.getAncestorOfType(EXDesigner.class);
		String path =d.getCurrentDir() + "/" + d.getCurrentName(); 
		String url ="http://" + servaerName + ":" + serverPort  + contextPath + "/portal.jsp?portal=" + path;
		
		String script ="<iframe src=\""+url+"\" width=\"100%\" height=\"5000px\" frameborder=\"0\"></iframe>";
		
		EXTextArea area = new EXTextArea("");
		area.setValue(script);
		area.setStyle("border", "none").setStyle("padding", "5px").setStyle("margin", "0").setStyle("width", "392px").setStyle("height", "100px");
		
		panel.setStyle("width", "424px");
		
		panel.setBody(area);
		d.addPopup(panel);
	}
	
	public void openPage(Container container){
		EXDesigner designer = container.getAncestorOfType(EXDesigner.class);
		String path = designer.getCurrentDir() + "/" + designer.getCurrentName() + "/pages";
		
		EXFinder finder = new EXFinder("openPageSelector", EXDesigner.TABLE_FILTER, new EXFinder.SelectFileHandler() {
			
			@Override
			public void onSelectFile(File file, EXFinder finder) {
				EXDesigner designer = finder.getAncestorOfType(EXDesigner.class);
				PageContainer pc = designer.getRootLayout().getDescendentOfType(PageContainer.class);
				BinaryFile bf = (BinaryFile)file;
				try{
					InputStream in = bf.getInputStream();
					
					Container page = DesignableDTO.buildContainer(in, true);
					page.setAttribute("pagepath", bf.getAbsolutePath());
					pc.setPage(page);
					designer.getDescendentOfType(EXConfigVerticalBar.class).getNode(pc).refresh();
					try{
						designer.getDescendentByName(page.getAttribute("__oid") + "_c-item").getParent().setStyle("border", "double steelBlue");
					}catch(Exception e){
						
					}
					finder.remove();
				}catch(Exception e){
					throw new UIException(e);
				}
				
			}
		}, path);
		
		designer.addPopup(finder);
		
	}
	
	public void newPage(Container container){
		final EXDesigner designer = container.getAncestorOfType(EXDesigner.class);
		String file = designer.getCurrentDir() + "/" + designer.getCurrentName();
		PageContainer pc = designer.getDescendentOfType(PageContainer.class);
		if(pc != null){
			//BinaryFile dir = (BinaryFile)SpringUtil.getRepositoryService().getDirectory(file, Util.getRemoteUser());
			//EXNewPageForm form = new EXNewPageForm("", dir);
			EXNewPage newPage = new EXNewPage("");
			newPage.setAttribute("portalPath", file);
			designer.addPopup(newPage);
			newPage.getDescendentOfType(EXInput.class).setValue(pc.getWidth().getAmount() + "");
		}else{
			throw new UIException("There is no page container in this site. You should add a page container in the site in order to be able to create a page");
		}
		

	}
	
	
	public void savePage(Container container){
		try{
			EXDesigner designer = container.getAncestorOfType(EXDesigner.class);
			PageContainer pageContainer = designer.getRootLayout().getDescendentOfType(PageContainer.class);
			Container page =  pageContainer.getPage();
			String path = page.getAttribute("pagepath");
			
			BinaryFile file = (BinaryFile)BaseSpringUtil.getBeanOfType(RepositoryService.class).getFile(path, Util.getRemoteUser());
			String xml = DesignableUtil.generateXML(page, null);
			file.write(xml.getBytes());
		}catch(Exception e){
			throw new UIException(e);
		}
	}
	
	
	public void editScript(Container container, boolean javascript){
		EXPanel panel = new EXPanel("panel");
		panel.setWidth(Dimension.parse("600px"));
		EXDesigner des = container.getAncestorOfType(EXDesigner.class);
		String portalPath = des.getCurrentDir() + "/" + des.getCurrentName();;
		EXScriptEditor editor = new EXScriptEditor("", portalPath, javascript);
		//editor.setHeight(Dimension.parse("600px"));
		editor.setStyle("width", "100%");
		panel.setBody(editor);
		container.getAncestorOfType(PopupContainer.class).addPopup(panel);
		panel.setTitle(editor.getAttribute("current"));
		editor.openDefaultScript();
		
	}
	
	public void newPortal(Container container){
		final EXDesigner designer = container.getAncestorOfType(EXDesigner.class);
		//EXDynaformPanel panel = new EXDynaformPanel("newportal","New Portal");
		designer.addPopup(new EXNewPortal(""));

	}
	
	public void openPortal(Container container){
		EXDesigner designer = container.getAncestorOfType(EXDesigner.class);
		MyProjects projects = new MyProjects("df");
		projects.setStyle("z-index", "6000").setStyle("width", "600px");
		designer.addPopup(projects);
	}
	
	public void openPortal_(Container container){
		final EXDesigner designer = container.getAncestorOfType(EXDesigner.class);
		String path = "/root/users/" + Util.getLoggedOrganization();
		
		EXFinder finder = new EXFinder("openSite", EXDesigner.TABLE_FILTER, new EXFinder.SelectFileHandler() {
			
			@Override
			public void onSelectFile(File file, EXFinder finder) {
				EXDesigner designer = finder.getAncestorOfType(EXDesigner.class);
				
				try{
					//InputStream in = bf.getInputStream();
					BinaryFile bf = (BinaryFile)file;
					String name = file.getName();
					String dir = file.getParent().getAbsolutePath();
					designer.open(dir, name);
					
					
					
					
					PageContainer pc = designer.getRootLayout().getDescendentOfType(PageContainer.class);
					
					if(pc != null){
						BinaryFile home = (BinaryFile)((Directory)bf.getFile("pages")).getFile("home");
						
						InputStream in = home.getInputStream();
						
						Container page = DesignableDTO.buildContainer(in, true);
						page.setAttribute("pagepath", home.getAbsolutePath());
						pc.setPage(page);
						designer.getDescendentOfType(EXConfigVerticalBar.class).getNode(pc).refresh();
						try{
							designer.getDescendentByName(page.getAttribute("__oid") + "_c-item").getParent().setStyle("border", "double steelBlue");
						}catch(Exception e){
							
						}
					}
					finder.remove();
					
					
					
				}catch( ClassCastException ce){
					throw new UIException("Unknown format. Cannot open this file");
				}
				catch(Exception e){
					throw new UIException("Unknown format. Cannot open this file");
				}
				
			}
		},path);
		
	 	
		designer.addPopup(finder);
	}
	
	
	public void cssEditor(Container container){
		EXDesigner designer = container.getAncestorOfType(EXDesigner.class);
		String portalName = designer.getCurrentName();
		String currentDir = designer.getCurrentDir();
		
		//String cssPath = currentDir + "/" + portalName + "/" + StringUtil.split(portalName, ".")[0] + ".css";
		
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
		
		//EXCSSEditor editor = new EXCSSEditor("EXCSSEditor", cssPath);
		//designer.addPopup(editor);
	}
	
	public void executePortal(){
		
		
		
	}

}
