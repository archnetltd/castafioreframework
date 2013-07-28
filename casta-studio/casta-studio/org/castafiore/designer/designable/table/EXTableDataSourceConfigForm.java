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
 package org.castafiore.designer.designable.table;

import java.util.Map;

import org.castafiore.SimpleKeyValuePair;
import org.castafiore.designable.DesignableFactory;
import org.castafiore.designer.EXDesigner;
import org.castafiore.designer.config.ConfigForm;
import org.castafiore.designer.service.DesignableService;
import org.castafiore.ecm.ui.finder.EXFinder;
import org.castafiore.ecm.ui.finder.EXFinder.SelectFileHandler;
import org.castafiore.ecm.ui.query.EXQueryBuilder;
import org.castafiore.ecm.ui.selector.EXFileSelector;
import org.castafiore.ecm.ui.selector.EXFileSelector.OnSelectFileHander;
import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXTextArea;
import org.castafiore.ui.ex.form.list.DataModel;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.ui.js.JMap;
import org.castafiore.ui.mac.EXMacFinder;
import org.castafiore.ui.mac.EXMacFinderColumn;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.EventUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.futil;
import org.castafiore.wfs.iterator.FileFilter;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;
import org.json.JSONObject;

public class EXTableDataSourceConfigForm extends EXContainer implements ConfigForm, FileFilter , SelectFileHandler{
	
	private Container containerToConfigure = null;

	public EXTableDataSourceConfigForm() {
		super("EXTableDataSourceConfigForm", "div");
		
		addClass("EXTableDataSourceConfigForm");
		addClass("ui-widget");
		setWidth(Dimension.parse("310px"));
		addChild(ComponentUtil.getContainer("", "label", "Entity type :", "label1"));
		
		EXSelect select = new EXSelect("entityType", new DefaultDataModel(futil.getEntityTypes()));
		select.setValue(futil.getEntityTypes().get(0));
		addChild(select);
		addChild(ComponentUtil.getBR());
		addChild(ComponentUtil.getContainer("", "label", "Type :", "label1"));
		addChild(new EXSelect("type", TYPE_MODEL));
		getChild("type").addEvent(CHANGE_TYPE_EVENT, Event.CHANGE);
		addChild(ComponentUtil.getBR());
		addChild(ComponentUtil.getContainer("typeLabel", "label", "Path :", "label2"));
		addChild(ComponentUtil.getBR());
		EXTextArea content = new EXTextArea("content");
		content.setAttribute("method","showExplorer");
		content.setAttribute("ancestor", getClass().getName());
		content.addEvent(EventUtil.GENERIC_FORM_METHOD_EVENT, Event.DOUBLE_CLICK);
		addChild(content);
		addChild(ComponentUtil.getContainer("saveButton", "div", null, "ui-icon ui-state-active ui-corner-all casta-tick-button"));
		getChild("SaveButton").addEvent(APPLYCONFIG_EVENT, Event.CLICK);
		
	}
	


	public void applyConfigs() {
		if(this.containerToConfigure != null){
			String rep = getObject().toString();
			containerToConfigure.setAttribute("datasource", rep);
		}
		
		DesignableFactory factory = BaseSpringUtil.getBeanOfType(DesignableService.class).getDesignable(containerToConfigure.getAttribute("des-id"));
		factory.refresh(containerToConfigure);
		
	}
	
	public void showExplorer(){
		
		EXFinder finder = new EXFinder("miniFileExplorer",null,this, "/root/users/" + Util.getRemoteUser());
		
		getAncestorOfType(PopupContainer.class).addPopup(finder);
		//showQueryBuilder();
	}
	
	public void showQueryBuilder(){
		EXMacFinder finder = new EXMacFinder("finder");
		EXMacFinderColumn start = new EXMacFinderColumn("start");
		EXQueryBuilder builder = new EXQueryBuilder("builder");
		finder.setStyle("z-index", "5000");
		start.setViewModel(builder);
		
		finder.addColumn(start);
		getAncestorOfType(PopupContainer.class).addPopup(finder);
		finder.setStyle("width", "903px");
	}
	

	public Container getContainer() {
		applyConfigs();
		return containerToConfigure;
	}

	public void setContainer(Container container) {
		try{
			this.containerToConfigure = container;
			String rep = container.getAttribute("datasource");
			JSONObject object = new JSONObject(rep);
			setObject(object);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	protected JSONObject getObject(){
		try{
			JSONObject object = new JSONObject();
			object.put("type", this.getSelectedType());
			object.put("entitytype", this.getSelectedEntity());
			object.put("value", getDescendentOfType(EXTextArea.class).getValue().toString());
			return object;
		}catch(Exception e){
			throw new UIException(e);
		}
	}
	
	protected void setObject(JSONObject object){
		try{
			String type = object.getString("type");
			String entitytype = object.getString("entitytype");
			String value = object.getString("value");
			((EXSelect)getDescendentByName("type")).setValue(type);
			((EXSelect)getDescendentByName("entityType")).setValue(new SimpleKeyValuePair(entitytype, StringUtil.split(entitytype, ".")[StringUtil.split(entitytype, ".").length-1]));
			getDescendentOfType(EXTextArea.class).setValue(value);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String getSelectedType(){
		return ((EXSelect)getChild("type")).getValue().toString();
	
	}
	
	private final static DataModel TYPE_MODEL = new DataModel(){

		public int getSize() {
			return 2;
		}

		public Object getValue(int index) {
			if(index == 0){
				return "Files";
			}else {
				return "Folders";
			}
		}	
	};
	
	private final static Event CHANGE_TYPE_EVENT = new Event(){

		public void ClientAction(ClientProxy container) {
			container.makeServerRequest(this);
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			
			
			//String sample = "/root/demo/documents";
			EXTableDataSourceConfigForm form = container.getAncestorOfType(EXTableDataSourceConfigForm.class);
			String type = form.getSelectedType();
			form.getChild("typeLabel").setText(type);
//			if(type.equalsIgnoreCase("Query")){
//				sample = "from "+form.getSelectedEntity() +" where parent.absolutePath='/root/demo/documents'";
//			}
//			form.getDescendentOfType(EXTextArea.class).setValue(sample);
			return true;
			
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			// TODO Auto-generated method stub
			
		}
		
	};

	private final static Event APPLYCONFIG_EVENT = new Event(){

		public void ClientAction(ClientProxy container) {
			container.makeServerRequest(this, "Apply configurations?");
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			container.getAncestorOfType(ConfigForm.class).applyConfigs();
			return true;
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			// TODO Auto-generated method stub
			
		}
		
	};

	public String getSelectedEntity(){
		SimpleKeyValuePair kv = (SimpleKeyValuePair) ((EXSelect)getChild("entityType")).getValue();
		return kv.getKey();
	}
	

	public boolean accept(File pathname) {
		String type = getSelectedType();
		if(type.equalsIgnoreCase("folders")){
			if(pathname.getClazz().equals(Directory.class.getName())){
				return true;
			}else
			{
				return false;
			}
		}
		return true;
	}



	




	@Override
	public void onSelectFile(File file, EXFinder finder) {
		
		String type = getSelectedType();
		String currentValue = getDescendentOfType(EXTextArea.class).getValue().toString();
		
		if(currentValue.length() > 0){
			currentValue = currentValue + ";";
		}
		currentValue = currentValue +   file.getAbsolutePath();
		
		getDescendentOfType(EXTextArea.class).setValue(currentValue);
	}
	
	
}
