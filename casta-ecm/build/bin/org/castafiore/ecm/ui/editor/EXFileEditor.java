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
 package org.castafiore.ecm.ui.editor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.castafiore.ecm.ui.fileexplorer.Explorer;
import org.castafiore.ecm.ui.fileexplorer.FileEditor;
import org.castafiore.ecm.ui.fileexplorer.icon.ICon;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;

import org.castafiore.ui.ex.dynaform.EXFieldSet;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.utils.EventUtil;
import org.castafiore.wfs.types.File;

public class EXFileEditor extends EXContainer{
	
	private Map<String, String> mapping = new HashMap<String, String>();

	public EXFileEditor() {
		super("EXFileEditor", "div");
		setStyle("padding", "10px");
		
		//Container fieldset = new EXContainer("fieldset", "fieldset");
		EXFieldSet fieldset = new EXFieldSet("fieldset", "Basic", false);
		addChild(fieldset);
		
		//addChild(fieldset.setStyle("border", "1px solid #CCCCCC").setStyle("padding", "1.4em"));
		mapping = BaseSpringUtil.getBean("fileEditorConfigMap");
		EXSelect fileTypes = new EXSelect("fileTypes", getModel());
		fileTypes.setAttribute("ancestor", getClass().getName());
		fileTypes.setAttribute("method", "selectEditor");
		fileTypes.addEvent(EventUtil.GENERIC_FORM_METHOD_EVENT, Event.CHANGE);
		addField("File Type :", fileTypes);

		addField("Name :", new EXInput("name"));

		
		
		fieldset.addButton(new EXButton("save", "Save"));
		fieldset.addButton(new EXButton("cancel", "Cancel"));
		
		
		//Container btns = new EXContainer("btns", "div").addClass("ui-widget-header").setStyle("padding", "7px 10px").setStyle("height", "32px").setStyle("margin", "10px 0px");
		//addChild(btns);
		
		//btns.addChild(new EXButton("save", "Save"));
		//btns.addChild(new EXButton("cancel", "Cancel").setStyle("float", "none").setStyle("display", "inline"));
		Container saveButton = getDescendentByName("save");
		saveButton.setAttribute("ancestor", getClass().getName()).setStyle("float", "none").setStyle("display", "inline");
		saveButton.setAttribute("method", "save");
		saveButton.addEvent(EventUtil.GENERIC_FORM_METHOD_EVENT, Event.CLICK);
		getDescendentByName("cancel").addEvent(EXPanel.CLOSE_EVENT, Event.CLICK);
		
		addChild(new EXContainer("editorContainer", "fieldset"));//.setStyle("border", "1px solid #CCCCCC").setStyle("padding", "1.4em").setStyle("overflow", "auto"));

	}
	public void addField(String label, StatefullComponent input){
		((EXFieldSet)getChild("fieldset")).addField(label,input);
		//getChild("fieldset").addChild(input);
	}
	public StatefullComponent getField(String name){
		return (StatefullComponent)getDescendentByName(name);
	}
	
	private  DefaultDataModel<Object> getModel(){
		try{
			DefaultDataModel<Object> model= new DefaultDataModel<Object>();			
			Iterator<String> fileTypes = mapping.keySet().iterator();
			while(fileTypes.hasNext()){
				model.getData().add(fileTypes.next());
			}
			return model;
		}catch(Exception e){
			throw new UIException(e);
		}
	}
	
	
	public void selectEditor(){
		try{
			String entity = getDescendentOfType(EXSelect.class).getValue().toString();
			//File file = (File)Thread.currentThread().getContextClassLoader().loadClass(entity).newInstance();
			newFile(entity, getAncestorOfType(Explorer.class).getCurrentAddress());
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	private void setEditor(FileEditor editor){
		Container c = getChild("editorContainer");
		c.getChildren().clear();
		c.setRendered(false);
		c.addChild(editor);
	}
	
	private FileEditor getEditor(){
		return getDescendentOfType(FileEditor.class);
	}
	
	
	public void save(){
		String name =((EXInput)getDescendentByName("name")).getValue().toString();
		File file = getEditor().save(name);
		//getEditor().i
		ICon icon = getAncestorOfType(Explorer.class).getView().getIcon(file.getAbsolutePath());
		if(icon == null){
			getAncestorOfType(Explorer.class).getView().addItem(file);
		}
	}
	
	public void openFile(File file,String directory, boolean isNew){
		try{
			String clazz = file.getClazz();
			String editor = mapping.get(clazz);
			FileEditor openner = (FileEditor)Thread.currentThread().getContextClassLoader().loadClass(editor).newInstance();
			
			openner.initialiseEditor(file, directory, isNew);
			setEditor(openner);
			if(!isNew){
				((EXInput)getDescendentByName("name")).setEnabled(false);
				((EXInput)getDescendentByName("name")).setValue(file.getName());
				getDescendentOfType(EXSelect.class).setValue(file.getClazz());
				getDescendentOfType(EXSelect.class).setAttribute("disabled", "true");
				
				
			}
			
		}catch(Exception e){
			throw new UIException(e);
		}
	}
	
	public void newFile(String clazz,String directory){
		try{
			//String clazz = file.getClazz();
			String editor = mapping.get(clazz);
			FileEditor openner = (FileEditor)Thread.currentThread().getContextClassLoader().loadClass(editor).newInstance();
			
			openner.initialiseEditor(null, directory, true);
			setEditor(openner);
			
		}catch(Exception e){
			throw new UIException(e);
		}
	}

}
