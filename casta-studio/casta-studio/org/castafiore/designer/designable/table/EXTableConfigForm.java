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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.castafiore.SimpleKeyValuePair;
import org.castafiore.designer.config.ConfigForm;
import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.EXGrid;
import org.castafiore.ui.ex.EXGrid.EXRow;
import org.castafiore.ui.ex.form.EXCheckBox;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.list.DataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.types.File;
import org.castafiore.wfs.types.FileImpl;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.type.Type;
import org.json.JSONArray;
import org.json.JSONObject;

public class EXTableConfigForm extends EXContainer implements ConfigForm{
	
	private Container containerToConfigure;
	
	private final static String[] HIDDEN_FIELDS = new String[]{
	"absolutePath",
	"clazz",				
	"containsText",				
	"dateCreated",				
	"editPermissions",				
	"hidden",				
	"lastModified",
	"owner",				
	"readOnly",				
	"readPermissions",				
	"size",
	};

	public EXTableConfigForm() {
		super("EXTableConfigForm", "div");
		addClass("ui-widget");
		setWidth(Dimension.parse("310px"));
		setHeight(Dimension.parse("230px"));
		setStyle("overflow-x", "hidden");
		setStyle("overflow-y", "auto");
		addChild(ComponentUtil.getContainer("", "label", "Entity type :", null));
		addChild(new EXSelect("entity", new ENTITY_TYPE()));
		addChild(ComponentUtil.getContainer("", "br", null,null));
		addChild(ComponentUtil.getContainer("", "label", "Rows Per page :", null));
		addChild(new EXInput("rowsperpage"));
		
		initGrid(FileImpl.class.getName());
		
		getChild("entity").addEvent(CHANGE_FILE, Event.CHANGE);
		getChild("entity").setStyle("border", "solid 1px silver");
		getChild("entity").setStyle("margin-left", "23px");
		
		getChild("rowsperpage").setStyle("border", "solid 1px silver");
		getChild("rowsperpage").setStyle("margin", "3px");
		getChild("rowsperpage").setStyle("width", "30px");
		setStyle("font-size", "12px");
		
		addChild(ComponentUtil.getContainer("saveButton", "div", null, "ui-icon ui-state-active ui-corner-all casta-tick-button"));
		getChild("SaveButton").addEvent(APPLYCONFIG_EVENT, Event.CLICK);
	}
	
	
	private void initGrid(String clzz){
		
		EXGrid grid = (EXGrid)getChild("grid");
		
		
		if(grid != null)
			grid.remove();
		grid = new EXGrid("grid", 5,1);
		grid.setAttribute("cellpadding", "0");
		grid.setAttribute("cellspacing", "0");
		grid.setStyle("border-collapse", "collapse");
		grid.setStyle("font-size", "10px");
		
		grid.getCell(0, 0).addChild(new EXCheckBox("checkall"));
		grid.getCell(0, 0).setAttribute("width", "1");
		grid.getChildByIndex(0).addClass("ui-widget-header");
		grid.getCell(1, 0).setText("Property");
		grid.getCell(2, 0).setText("Label");
		grid.getCell(3, 0).setText("Width");
		grid.getCell(3, 0).setAttribute("align", "center");
		grid.getCell(4, 0).setText("Edit");
		grid.getCell(4, 0).setAttribute("width", "1");
		grid.setAttribute("width", "100%");
		addChild(grid);
		
		
		SessionFactory fact = BaseSpringUtil.getBeanOfType(SessionFactory.class);
		ClassMetadata meta = fact.getClassMetadata(clzz);
		String[] properties = meta.getPropertyNames();
		int counter = 0;
		for(String property :properties){
			Type type = meta.getPropertyType(property);
			if(!type.isAssociationType() && ! type.isCollectionType() && !type.isComponentType() && !type.isEntityType()){
				
				if(!ArrayUtils.contains(HIDDEN_FIELDS, property)){
				
					EXRow row = grid.addRow();
					if((counter % 2) == 0){
						row.addClass("ui-state-highlight");
					}
					
					row.addInCell(0,new EXCheckBox(""));
					row.getChildByIndex(1).setText(property);
					row.addInCell(2,new EXInput(""));
					row.addInCell(3,new EXInput(""));
					row.addInCell(4,new EXCheckBox(""));
					row.getChildByIndex(2).setAttribute("width", "90");
					row.getChildByIndex(2).getChildByIndex(0).setStyle("width", "88px");
					row.getChildByIndex(2).getChildByIndex(0).setStyle("border", "solid 1px silver");
					row.getChildByIndex(2).getChildByIndex(0).setStyle("height", "12px");
					
					row.getChildByIndex(3).setAttribute("width", "40");
					row.getChildByIndex(3).setAttribute("align", "center");
					row.getChildByIndex(3).getChildByIndex(0).setStyle("width", "38px");
					row.getChildByIndex(3).getChildByIndex(0).setStyle("border", "solid 1px silver");
					row.getChildByIndex(3).getChildByIndex(0).setStyle("height", "12px");
					
					counter++;
				}
			}
		}
		
	}

	public void applyConfigs() {
		if(containerToConfigure != null){
			containerToConfigure.setAttribute("datamodel", getObject().toString());
		}
	}

	public Container getContainer() {
		applyConfigs();
		return containerToConfigure;
	}

	public void setContainer(Container container) {
		try{
			this.containerToConfigure = container;
			String stringRep = container.getAttribute("datamodel");
			JSONObject object = new JSONObject(stringRep);
			setObject(object);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	protected void setObject(JSONObject object){
		try{
			String entity = object.getString("entity");
			String pageSize = object.getString("pageSize");
			
			JSONArray properties = object.getJSONArray("properties");
			JSONArray labels = object.getJSONArray("labels");
			JSONArray widths = object.getJSONArray("widths");
			JSONArray editables = object.getJSONArray("editables");
			
			
			((StatefullComponent)getDescendentByName("entity")).setValue(new SimpleKeyValuePair(entity, StringUtil.split(entity, ".")[StringUtil.split(entity, ".").length-1]));
			((StatefullComponent)getDescendentByName("rowsperpage")).setValue(pageSize);
			
			initGrid(entity);
			EXGrid grid = (EXGrid)getDescendentByName("grid");
			
			for(int i = 1;  i< grid.getRows(); i ++){
				
				String property = grid.getCell(1, i).getText();
				int propIndex = contains(properties, property);
				if(propIndex >=0){
					grid.getCell(0, i).getDescendentOfType(EXCheckBox.class).setChecked(true);
					grid.getCell(2, i).getDescendentOfType(StatefullComponent.class).setValue(labels.getString(propIndex ));
					grid.getCell(3, i).getDescendentOfType(StatefullComponent.class).setValue(widths.getString(propIndex ));
					grid.getCell(4, i).getDescendentOfType(EXCheckBox.class).setChecked(editables.getBoolean(propIndex));
				}else{
					
				}
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	
	private int contains(JSONArray array, String value)throws Exception{
		for(int i = 0; i < array.length(); i++){
			if(array.getString(i).equals(value)){
				return i;
			}
		}
		return -1;
	}
	
	protected JSONObject getObject(){
		try{
			JSONObject object = new JSONObject();
			String clazz =  ((SimpleKeyValuePair)((StatefullComponent)getDescendentByName("entity")).getValue()).getKey();
			String pageSize = ((StatefullComponent)getDescendentByName("rowsperpage")).getValue().toString();
			
			object.put("entity", clazz);
			object.put("pageSize", pageSize);
			
			EXGrid grid = (EXGrid)getDescendentByName("grid");
			List<String> properties = new ArrayList<String>();
			List<String> labels = new ArrayList<String>();
			List<String> widths = new ArrayList<String>();
			List<Boolean> editables = new ArrayList<Boolean>();
			
			
			
			for(int i = 1;  i< grid.getRows(); i ++){
				boolean isSelected = grid.getCell(0, i).getDescendentOfType(EXCheckBox.class).isChecked();
				if(isSelected){
					
					String property = grid.getCell(1, i).getText();
					String label = grid.getCell(2, i).getDescendentOfType(StatefullComponent.class).getValue().toString();
					String width = grid.getCell(3, i).getDescendentOfType(StatefullComponent.class).getValue().toString();
					boolean editable = grid.getCell(4, i).getDescendentOfType(EXCheckBox.class).isChecked();
					
					properties.add(property);
					labels.add(label);
					widths.add(width);
					editables.add(editable);
					
					
				}
			}
			
			object.put("properties", properties);
			object.put("labels", labels);
			object.put("widths", widths);
			object.put("editables", editables);
			
			return object;
		}catch(Exception e){
			throw new UIException(e);
		}
		
		
	}
	
	private static class ENTITY_TYPE implements DataModel{

		//private Object[] clss = null;
		
		private List<SimpleKeyValuePair> clss = new ArrayList<SimpleKeyValuePair>();
		
		public ENTITY_TYPE(){
			try{
				SessionFactory factory = BaseSpringUtil.getBeanOfType(SessionFactory.class);
				Object[] clss = factory.getAllClassMetadata().keySet().toArray();
				for(int index = 0; index < clss.length; index++){
					try{
						Object o =  Thread.currentThread().getContextClassLoader().loadClass(clss[index].toString()).newInstance();
						if(o instanceof File){
							SimpleKeyValuePair kv = new SimpleKeyValuePair(clss[index].toString(), StringUtil.split(clss[index].toString(), ".")[StringUtil.split(clss[index].toString(), ".").length-1]);
							this.clss.add(kv);
						}
					}
					catch(Exception e){
						
					}
				}
			}catch(Exception e){
				throw new UIException(e);
			}
		}
		
		
		public int getSize() {
			return clss.size();
		}

		public Object getValue(int index) {
			
			return clss.get(index);
		}
		
	}
	
	
	
	
	private final static Event CHANGE_FILE = new Event(){

		public void ClientAction(ClientProxy container) {
			container.makeServerRequest(this);
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			 SimpleKeyValuePair kv = (SimpleKeyValuePair)((EXSelect)container).getValue();
			 String clazz = kv.getKey();
			 
			 container.getAncestorOfType(EXTableConfigForm.class).initGrid(clazz);
			 
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

}
