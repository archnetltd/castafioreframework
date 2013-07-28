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
 package org.castafiore.designer.config.ui;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.collections.map.ListOrderedMap;
import org.castafiore.designable.DesignableFactory;
import org.castafiore.designable.layout.DesignableLayoutContainer;
import org.castafiore.designer.EXDesigner;
import org.castafiore.designer.config.ConfigForm;
import org.castafiore.designer.config.DesignerInput;
import org.castafiore.designer.config.ui.input.ApplyAttributeDesignerInput;
import org.castafiore.designer.config.ui.input.ApplyAttributeDesignerPopupHolder;
import org.castafiore.designer.config.ui.input.ApplyAttributeDesignerSelect;
import org.castafiore.designer.config.ui.input.EXSetDatasourceDesignerInput;
import org.castafiore.designer.config.ui.input.GenericAttributeDesignerInput;
import org.castafiore.designer.designable.ConfigValue;
import org.castafiore.designer.designable.ConfigValues;
import org.castafiore.designer.layout.ChildrenAttributeConfigurationListener;
import org.castafiore.designer.service.DesignableService;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.ui.ex.form.table.Table;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.utils.StringUtil;

public class EXPropertiesConfigForm extends EXContainer implements ConfigPanel {

	private Container container;
	
	public EXPropertiesConfigForm() {
		super("EXPropertiesConfigForm", "div");
		addChild(new Menu());
		addChild(new ConfigArea());
	}
	
	public void init(String[] advancedConfigs, ConfigValues values){
		
		Map<String, String[][]> pValues = new HashMap<String, String[][]>();
		try{
		if(values != null){
			ConfigValue[] cValues =  values.configs();
			if(cValues != null ){
				for(ConfigValue co : cValues){
					String[][] ss = new String[2][];
					ss[0] = co.values();
					ss[1] = new String[]{co.default_()};
					pValues.put(co.attribute(), ss);
				}
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		getDescendentOfType(Menu.class).getChildren().clear();
		getDescendentOfType(Menu.class).setRendered(false);
		
		Map<String, DesignerInput> configs = new ListOrderedMap();
		configs.put("name", new NameInput("nnn"));
		configs.put("class",new GenericAttributeDesignerInput("class"));
		configs.put("label",new GenericAttributeDesignerInput("label"));
		configs.put("DataSource",new EXSetDatasourceDesignerInput());
		
		
		
		//datasource
		//datafield
		
		//configs.put("stylesheets(s1,s2,..)", new ExStylesheetDesignerInput("stylesheets"));
		//configs.put("label", new  LabelInput("lll", ""));
		
		if(advancedConfigs != null){
			for(String s : advancedConfigs){
				if(pValues.containsKey(s)){
					String[][] ss = pValues.get(s);
					String[] vals = ss[0];
					String def = ss[1][0];
					if(vals.length >1){
					
						configs.put(s, new ApplyAttributeDesignerSelect(s, vals,def));
					}else if(vals.length == 1){
						String val = vals[0];
						if(val.startsWith("search:")){
							String key = val.replace("search:", "");
							configs.put(s, new ApplyAttributeDesignerPopupHolder(s,key, def));
						}
					}else if(vals.length == 0){
						configs.put(s, new ApplyAttributeDesignerInput(s, def));
					}
				}else{
					configs.put(s, new ApplyAttributeDesignerInput(s, ""));
				}
			}
		}
			
		
		Iterator<String> iterConfigs = configs.keySet().iterator();
		while(iterConfigs.hasNext()){
			configs.get(iterConfigs.next()).initialise(container);
		}
		
		
		addMenuItem("Main", configs);
		setConfigs(configs);
		
		DesignableFactory factory = BaseSpringUtil.getBeanOfType(DesignableService.class).getDesignable(container.getAttribute("des-id"));
		Map<String, ConfigForm> forms = factory.getAdvancedConfigs();
		if(forms != null){
			Iterator<String> iters = forms.keySet().iterator();
			while(iters.hasNext()){
				String label = iters.next();
				ConfigForm form = forms.get(label);
				addMenuItem(label, form);
			}
		}
		
	}
	
	public Container getContainer() {
		return container;
	}

	public void setContainer(Container container, String[] requiredAttributes,ConfigValues vals) {
		this.container = container;
		init(requiredAttributes, vals);
	}
	
	
	public class NameInput extends EXInput implements DesignerInput{

		public NameInput(String name) {
			super(name);
		}

		public void applyConfigOnContainer(Container c) {
			
			String val = getValue().toString();
			if(val.length() > 0){
				c.setName(getValue().toString());
				getAncestorOfType(EXPanel.class).setTitle("Configure " + c.getName());
				EXConfigItem item = (EXConfigItem)getAncestorOfType(EXDesigner.class).getDescendentByName(c.getAttribute("__oid")+ "_c-item");
				item.setText(c.getName());
			}
			
		}

		public void initialise(Container c) {
			setValue(c.getName());
			
		}

		public void applyConfigOnContainer(String stringRepresentation,
				Container c) {
			setValue(stringRepresentation);
			c.setName(stringRepresentation);
			
		}

		public String getStringRepresentation() {
			return getValue().toString();
		}
		
	}
	
	
	
	
	public void addMenuItem(String label, Map<String, DesignerInput> configs){
		MenuItem item = new MenuItem(label,configs);
		
		getDescendentOfType(Menu.class).addItem(item);
	}
	
	public void addMenuItem(String label, ConfigForm form){
		MenuItem item = new MenuItem(label,form);
		
		getDescendentOfType(Menu.class).addItem(item);
	}

	public class Menu extends EXContainer{

		public Menu() {
			super("menu", "div");
			addClass("menu-x");
			setStyle("min-height", "230px");
		}
		
		public void addItem(MenuItem item){
			addChild(item);
		}
		
		
	}
	
	public void setConfigs(final Map<String, DesignerInput> configs){
		getDescendentOfType(ConfigArea.class).setConfigs(configs, container);
	}
	public void setConfigForm(final ConfigForm form){
		getDescendentOfType(ConfigArea.class).setConfigForm(form, container);
	}
	
	public class MenuItem extends EXContainer{
		
		public MenuItem(String label , final Map<String, DesignerInput> configs) {
			super("", "div");
			addClass("menuBar");
			setText(label);
			addEvent(new Event(){

				public void ClientAction(ClientProxy container) {
					container.getAncestorOfType(EXConfigPanel.class).mask();
					container.makeServerRequest(this);	
				}

				public boolean ServerAction(Container container,Map<String, String> request) throws UIException {
					container.getAncestorOfType(EXPropertiesConfigForm.class).setConfigs(configs);
					return true;
				}

				public void Success(ClientProxy container,
						Map<String, String> request) throws UIException {
				}
				
			}, Event.CLICK);
		}
		
		public MenuItem(String label , final ConfigForm form) {
			super("", "div");
			addClass("menuBar");
			setText(label);
			addEvent(new Event(){

				public void ClientAction(ClientProxy container) {
					container.makeServerRequest(this);	
				}

				public boolean ServerAction(Container container,Map<String, String> request) throws UIException {
					container.getAncestorOfType(EXPropertiesConfigForm.class).setConfigForm(form);
					return true;
				}

				public void Success(ClientProxy container,
						Map<String, String> request) throws UIException {
				}
				
			}, Event.CLICK);
		}
	}
	
	public class ConfigArea extends EXContainer{

		public ConfigArea() {
			super("ConfigArea", "ul");
			addClass("ConfigArea");
		}
		
		
		public void setConfigForm(ConfigForm form, Container container){
			this.getChildren().clear();
			this.setRendered(false);
			form.setRendered(false);
			form.setContainer(container);
			EXContainer li = new EXContainer("", "li");
			addChild(li);
			li.addChild(form);
			
			
			//addChild(form);
		}
		
		public void setConfigs(Map<String, DesignerInput> configs, Container c){
			this.getChildren().clear();
			this.setRendered(false);
			Iterator<String> iterator = configs.keySet().iterator();
			
			while(iterator.hasNext()){
				String name = iterator.next();
				DesignerInput input  = configs.get(name);
				input.addClass("input");
				Event e = new Event(){
					public void ClientAction(ClientProxy container) {
						container.makeServerRequest(this);				
					}

					public boolean ServerAction(Container container,
							Map<String, String> request) throws UIException {
						DesignerInput comp = (DesignerInput)container;
						Container c =container.getAncestorOfType(EXPropertiesConfigForm.class).getContainer();
						comp.applyConfigOnContainer(c);
						DesignableLayoutContainer lc = c.getParent().getAncestorOfType(DesignableLayoutContainer.class);
						if(lc instanceof ChildrenAttributeConfigurationListener){
							((ChildrenAttributeConfigurationListener)lc).applyAttribute(c, comp.getName(), comp.getValue().toString());
						}
						return true;
					}

					public void Success(ClientProxy container,Map<String, String> request) throws UIException {
					}
				};
				if(input instanceof EXSelect){
					input.addEvent(e, Event.CHANGE);
				}else{
					input.addEvent(e, Event.BLUR);
				}
				
				EXContainer div = new EXContainer("", "div");
				div.setStyle("display", "inline");
				EXContainer label = new EXContainer("label", "label");
				label.addClass("label");
				label.setText(name);
				div.addChild(label);
				div.addChild(input);
				
				EXContainer li = new EXContainer("", "li");
				li.addChild(div);
				addChild(li);
			}
		}
	}

}
