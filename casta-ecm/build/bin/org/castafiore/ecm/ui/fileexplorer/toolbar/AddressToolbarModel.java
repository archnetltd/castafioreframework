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
 package org.castafiore.ecm.ui.fileexplorer.toolbar;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.castafiore.ecm.ui.editor.EXFileEditor;
import org.castafiore.ecm.ui.fileexplorer.EXFileExplorer;
import org.castafiore.ecm.ui.fileexplorer.Explorer;
import org.castafiore.ecm.ui.fileexplorer.ExplorerView;
import org.castafiore.ecm.ui.fileexplorer.addressbar.BackButton;
import org.castafiore.ecm.ui.fileexplorer.addressbar.EXAddressBar;
import org.castafiore.ecm.ui.fileexplorer.events.SearchFilesEvent;
import org.castafiore.ecm.ui.fileexplorer.icon.ICon;
import org.castafiore.ecm.ui.fileexplorer.views.ListView;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.ViewModel;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.button.EXButtonSet;
import org.castafiore.ui.ex.form.button.EXIconButton;
import org.castafiore.ui.ex.form.button.Icons;
import org.castafiore.ui.ex.layout.EXBorderLayoutContainer;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.ui.ex.toolbar.ToolBarItem;
import org.castafiore.ui.ex.tree.EXTree;
import org.castafiore.ui.navigation.EXAccordeon;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.utils.IOUtil;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;

public class AddressToolbarModel implements ViewModel<ToolBarItem> {
	
	public static String getExportUrl(String[] selected){
		
		if(selected == null || selected.length <= 0){
			return "#";
		}
		StringBuilder b = new StringBuilder();
		String path = selected[0];
		String dir =	ResourceUtil.getParentPath(path);
		
		
		b.append("export/?type=zipdownload&dir=" + dir).append("&names=");
		for(String s : selected){
			try{
			String name = URLEncoder.encode(s.replace(dir + "/", "").trim(), "UTF-8");
			b.append(name).append(";");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return b.toString().substring(0, b.toString().lastIndexOf(';'));
	}
	
	public ToolBarItem getComponentAt(int index, Container parent) {
		
		if(index == 0){
			
			EXIconButton listView = new EXIconButton("", Icons.ICON_ARROW_1_E);
			listView.setStyle("width", "0").setStyle("height", "10px");
			//EXToolBar.EXToolbarButton listView = new EXToolBar.EXToolbarButton("" ,ResourceUtil.getIconUrl("BlueCircleRightArrow.gif", "small"));
			listView.addEvent(new Event(){

				public void ClientAction(ClientProxy container) {
					container.mask();
					container.makeServerRequest(this);
					
				}

				public boolean ServerAction(Container container,
						Map<String, String> request) throws UIException {
					String address = container.getAncestorOfType(Explorer.class).getCurrentAddress();
					
					Directory dir = BaseSpringUtil.getBeanOfType(RepositoryService.class).getDirectory(address, Util.getRemoteUser());
					if(dir != null)
					{
						container.getAncestorOfType(Explorer.class).OnFileSelected(dir);
					}
					return true;
				}

				public void Success(ClientProxy container,
						Map<String, String> request) throws UIException {
					// TODO Auto-generated method stub
					
				}
				
			}, Event.CLICK);
			EXButtonSet address = new EXButtonSet("add");
			address.addItem(new BackButton("backbutton")).addItem(new EXAddressBar("bar")).addItem(listView);
			address.setTouching(true);
			return address;
		}
		
		else if(index == 1)
		{
			
			EXIconButton treeView = new EXIconButton("", Icons.ICON_FOLDER_OPEN);
			
			treeView.addEvent(new Event(){

				public void ClientAction(ClientProxy container) {
					container.mask();
					container.makeServerRequest(this);
					
				}

				public boolean ServerAction(Container container,
						Map<String, String> request) throws UIException {
					Container c =  container.getAncestorOfType(EXFileExplorer.class).getContainer(EXBorderLayoutContainer.LEFT);
					
					EXTree tree = c.getDescendentOfType(EXTree.class);
					EXAccordeon acc = c.getDescendentOfType(EXAccordeon.class);
					if(tree == null){
						tree = container.getAncestorOfType(EXFileExplorer.class).getTree();
						c.addChild(tree);
						tree.setDisplay(false);
					}
					if(tree.isVisible()){
						tree.setDisplay(false);
						acc.setDisplay(true);
					}else{
						tree.setDisplay(true);
						acc.setDisplay(false);
					}
					
					return true;
				}

				public void Success(ClientProxy container,
						Map<String, String> request) throws UIException {
					// TODO Auto-generated method stub
					
				}
				
			}, Event.CLICK);
			
			
			
			EXIconButton deleteButton = new EXIconButton("", Icons.ICON_TRASH);
			deleteButton.addEvent(new Event(){

				
				public void ClientAction(ClientProxy container) {
					container.makeServerRequest(this, "Do you want to delete this file?");
					
				}

				
				public boolean ServerAction(Container container,
						Map<String, String> request) throws UIException {
					Explorer fileExplorer = container.getAncestorOfType(Explorer.class);
					
					RepositoryService service = SpringUtil.getRepositoryService();
					if(fileExplorer.getSelectedFiles() != null){
						
						for(String fileName : fileExplorer.getSelectedFiles()){
						
							if(service.itemExists(fileName)){
								File f = service.getFile(fileName, Util.getRemoteUser());
								f.remove();
								ExplorerView view = fileExplorer.getView();
								ICon ic = view.getIcon(fileName);
								ic.setDisplay(false);
								if(fileExplorer.getView() instanceof ListView){
									ic.getParent().getParent().setDisplay(false);
								}
							}
	
						}
						
					}
					//fileExplorer.getView().refreshView(fileExplorer.getCurrentAddress());
					container.getAncestorOfType(EXFileExplorer.class).setSelectedFiles(null);
					//FileExplorerUtil.refreshNodeInTree(fileExplorer, path);
					
					
					return true;
					
				}

				
				public void Success(ClientProxy container,
						Map<String, String> request) throws UIException {
				}
				
				
				
			}, Event.CLICK);
			
			EXIconButton newDocument = new EXIconButton("", Icons.ICON_NOTE);
			newDocument.addEvent(new Event(){

				public void ClientAction(ClientProxy container) {
					container.mask();
					container.makeServerRequest(this);
					
				}

				public boolean ServerAction(Container container,
						Map<String, String> request) throws UIException {
					
					//EXDocumentFileView view = new EXDocumentFileView();
					EXFileEditor editor = new EXFileEditor();
					EXPanel panel = new EXPanel("fileEditorPanel", "Create new file");
					panel.setBody(editor);
					panel.setWidth(Dimension.parse("900px"));
					//view.init(SpringUtil.getRepositoryService().getDirectory(container.getAncestorOfType(Explorer.class).getCurrentAddress(), Util.getRemoteUser()));
					container.getAncestorOfType(PopupContainer.class).addPopup(panel);
					editor.selectEditor();
					return true;
				}

				public void Success(ClientProxy container,
						Map<String, String> request) throws UIException {
					// TODO Auto-generated method stub
					
				}
				
				
				
			}, Event.CLICK);
			
			
			
			EXIconButton export = new EXIconButton("", Icons.ICON_ARROWTHICK_1_NE);
			export.setAttribute("title", "Download selected files");
			export.addEvent(new Event(){

				public void ClientAction(ClientProxy container) {
					container.mask();
					container.makeServerRequest(this);
					
				}

				public boolean ServerAction(Container container,
						Map<String, String> request) throws UIException {
					Container editor = new EXContainer("", "div");
					Directory dir = SpringUtil.getRepositoryService().getDirectory(container.getAncestorOfType(EXFileExplorer.class).getCurrentAddress(), Util.getRemoteUser());
					BinaryFile bzip = dir.createFile(System.currentTimeMillis() + ".zip", BinaryFile.class);
					try{
						OutputStream out = bzip.getOutputStream();
						ZipOutputStream zout = new ZipOutputStream(out);
						for(String s : container.getAncestorOfType(EXFileExplorer.class).getSelectedFiles()){
							BinaryFile bf = (BinaryFile)SpringUtil.getRepositoryService().getFile(s, Util.getRemoteUser());
							zout.putNextEntry(new ZipEntry(s));
							zout.write(IOUtil.getStreamContentAsBytes(bf.getInputStream()));
							zout.closeEntry();
						}
						
						zout.flush();
						zout.close();
						dir.save();
					}catch(Exception e){
						e.printStackTrace();
					}
					String link = ResourceUtil.getDownloadURL("ecm", bzip.getAbsolutePath()); //getExportUrl(container.getAncestorOfType(EXFileExplorer.class).getSelectedFiles());
					Container uilink = new EXContainer("", "a").setText("Download selected files").setAttribute("href",link).setAttribute("target", "_blank");
					editor.addChild(uilink);
					EXPanel panel = new EXPanel("fileEditorPanel", "Export selected files");
					panel.setBody(editor);
					panel.setWidth(Dimension.parse("400px"));
					
					container.getAncestorOfType(PopupContainer.class).addPopup(panel);
					
					return true;
				}

				public void Success(ClientProxy container,
						Map<String, String> request) throws UIException {
					// TODO Auto-generated method stub
					
				}
				
				
				
			}, Event.CLICK);
			
			EXButtonSet set1 = new EXButtonSet("set1");
			set1.setTouching(true);
			//set1.addItem(treeView);
			set1.addChild(newDocument);
			set1.addItem(deleteButton);
			//set1.addItem(export);
			return set1;
			
			
		}else if(index == 2){
			return getSearchButtonSet();
		}
		else if(index == 3)
		{
			EXButton btn = new EXButton("count", "0 Items");
			btn.setStyleClass("").setStyle("float", "right !important").setStyle("margin", "-4px").getEvents().clear();
			return btn;
			
		}else
			return null;
	}
	
	
	private EXButtonSet getSearchButtonSet(){
		SearchInput searchInput = new SearchInput();
		searchInput.setWidth(Dimension.parse("300px"));
		searchInput.setStyle("border-style", "none");
		searchInput.setHeight(Dimension.parse("20px"));
		searchInput.setStyle("margin", "0").setStyle("padding", "0");
		
		EXIconButton searchButton = new EXIconButton("searchButton", Icons.ICON_SEARCH);
		searchButton.addEvent(new SearchFilesEvent(), Event.CLICK);
		EXButtonSet set = new EXButtonSet("searchButtonSet");
		set.addItem(searchInput);
		set.addItem(searchButton);
		set.setTouching(true);
		return set;
		
		
	}

	public int size() {
		return 4;
	}
	
	public int bufferSize() {
		return size();
	}
	
	
	public class SearchInput extends EXInput implements ToolBarItem{

		public SearchInput() {
			super("SearchInput");
			setWidth(Dimension.parse("300px"));
			setStyle("border-style", "none");
			setHeight(Dimension.parse("20px"));
		}
		
	}

}
 