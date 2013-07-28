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

import jodd.util.StringUtil;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.ui.ex.tree.EXTree;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.iterator.FileFilter;
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;

public class EXFileSelector extends EXXHTMLFragment {
	
	private List<OnSelectFileHander> onSelectFileHandlers = new ArrayList<OnSelectFileHander>(1);
	
	public EXFileSelector(String name, FileFilter treeFilter, FileFilter tableFilter, String pointToDir) {
		this(name,treeFilter,tableFilter,pointToDir, "/root/users/" + Util.getRemoteUser() );
	}

	public EXFileSelector(String name, FileFilter treeFilter, FileFilter tableFilter, String pointToDir, String treeRootDir) {
		super(name, ResourceUtil.getDownloadURL("classpath", "org/castafiore/ecm/ui/selector/EXFileSelector.xhtml"));
		if(pointToDir == null){
			pointToDir = "/root/users/" + Util.getRemoteUser();
		}
		setAttribute("rootDir", pointToDir);
		try{
			
			Directory dir = BaseSpringUtil.getBeanOfType(RepositoryService.class).getDirectory(treeRootDir, Util.getRemoteUser());
			EXTree tree = new EXTree("tree", new FileSelectorFileTreeNode(dir, null,treeFilter));
			tree.setStyle("height", "327px");
		
			addChild(tree);
			
			addChild(new EXFileSelectorMainToolbar());
			
			addChild(new EXFileSelectorSecondaryToolbar());
			
			addChild(new EXFileSelectorTable("EXFileSelectorTable", tableFilter));
			
			
			
			EXButton cancelButton = new EXButton("cancelButton", "Cancel");
			addChild(cancelButton);
			cancelButton.addEvent(EXPanel.CLOSE_EVENT, Event.CLICK);
			cancelButton.setStyle("float", "right");
			cancelButton.addClass("fg-button-small");
			
			EXButton okButton = new EXButton("okButton", "Ok");
			addChild(okButton);
			okButton.addClass("fg-button-small");
			okButton.setStyle("float", "right");
			
			EXInput input = new EXInput("input");
			input.setStyle("float", "right");
			addChild(input);
			
			
			Container label = ComponentUtil.getContainer("inputLabel", "span", "File name :", null);
			addChild(label);
			
			
			
			if(pointToDir.equals(dir.getAbsolutePath())){
				setDirectory(dir);
			}else{
				try{
					File f = BaseSpringUtil.getBeanOfType(RepositoryService.class).getDirectory(pointToDir, Util.getRemoteUser());
					if(f.isDirectory() && !(f instanceof BinaryFile)){
						setDirectory((Directory)f);
					}else{
						setDirectory(f.getParent());
					}
						
				}catch(Exception e){
					e.printStackTrace();
					setDirectory(dir);
				}
			}
		}catch(Exception e){
			throw new UIException(e);
		}
	}
	
	public String getCurrentDir(){
		return getAttribute("currentdir");
	}
	
	public void setCurrentDir(String dir){
		setAttribute("currentdir", dir);
	}
	
	public EXFileSelector(String name) {
		this(name, null, null, null);
	}
	
	public void setDirectory(Directory dir){
		getDescendentOfType(EXFileSelectorTable.class).setDirectory(dir);
		getDescendentOfType(EXFileSelectorMainToolbar.class).setAddress(dir.getAbsolutePath());
		setCurrentDir(dir.getAbsolutePath());
		openTreeTo(dir);
	}
	
	private void openTreeTo(Directory dir){
		String absPath = dir.getAbsolutePath();
		String[] as = StringUtil.split(absPath, "/");
		StringBuilder b = new StringBuilder("/");
		EXTree tree = getDescendentOfType(EXTree.class);
		for(String s : as){
			if(org.castafiore.utils.StringUtil.isNotEmpty(s)){
				b.append(s);
				String sdir = b.toString();
				Container holder = tree.getDescendentByName(sdir);
				if(holder != null){
					tree.getNode(holder.getId()).Open();
				}
				b.append("/");
			}
		}
	}
	
	public void setSearchResult(List<File> files){
		getDescendentOfType(EXFileSelectorTable.class).setSearchResult(files);
		
		
	}
	
	
	public void addOnSelectFileHandler(OnSelectFileHander handler){
		this.onSelectFileHandlers.add(handler);
	}
	
	
	protected void fireOnSelectFileHandlers(String absolutePath){
		for(OnSelectFileHander handler : onSelectFileHandlers){
			handler.onSelectFile(absolutePath, this);
		}
	}
	
	public void refreshUI(Directory directory){
		EXTree tree = getDescendentOfType(EXTree.class);
		tree.remove();
		setRendered(false);
		tree = new EXTree("tree", new FileSelectorFileTreeNode(directory));
		tree.setStyle("height", "300px");
	
		addChild(tree);
		setDirectory(directory);
		
	}
	
	public void refreshTable(){
		setDirectory(BaseSpringUtil.getBeanOfType(RepositoryService.class).getDirectory(getCurrentDir(), Util.getRemoteUser()));
	}
	
	public interface OnSelectFileHander {
		
		public void onSelectFile(String abosolutePath, EXFileSelector selector);
		
	}
	
	
	public EXButton getOkButton(){
		return (EXButton)getChild("okButton");
	}
	
	public EXInput getInput(){
		return (EXInput)getChild("input");
	}
	
	
	
	

}
