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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections.map.ListOrderedMap;
import org.castafiore.designer.designable.ConfigValues;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXColorPicker;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.list.DataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ComponentVisitor;
import org.castafiore.utils.StringUtil;

public class EXStyleConfigForm extends EXContainer implements ConfigPanel {
	
	public final static String[] ITEMS= new String[]{"Box Model", "Background", "Display/Position", "Border", "Font","Text", "Table/List", "Layout", "Column", "Animations"};
	
	public final static String[] TYPES= new String[]{"box", "background", "position", "border", "font","text","tablelist", "layout", "column", "animations"};
	
	private Container container;
	
	private Properties props = null;

	public EXStyleConfigForm() {
		super("EXStyleConfigForm","div");
		addChild(new Menu());
		addChild(new ConfigArea());
		try
		{
			props = new Properties();
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("org/castafiore/designer/resources/style-conf.properties"));
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
		setAttribute("type", "0");
	}
	
	private Map<String, String> getProperties(int type){
		String sType = TYPES[type];
		
		ListOrderedMap result = new ListOrderedMap();
		Iterator<Object> iters = props.keySet().iterator();
		List<String> keys = new ArrayList<String>();
		
		while(iters.hasNext()){
			Object next = iters.next();
			keys.add(next.toString());
			
		}
		
		Collections.sort(keys);
		
		for(String next : keys){
			if(next.startsWith(sType + ".")){
				result.put(next, props.get(next).toString());
			}
		}
		
		//Collections.sort(result.keyList());
		return result;
	}
	
	public void setConfigs(int type){
		setAttribute("type", type + "");
		Map<String, String> properties = getProperties(type);
		getDescendentOfType(ConfigArea.class).setConfigs(properties, container);
	}
	
	public void setConfigs(){
		int type = 0;
		if(StringUtil.isNotEmpty(getAttribute("type"))){
			type = Integer.parseInt(getAttribute("type"));
		}
		setAttribute("type", type + "");
		Map<String, String> properties = getProperties(type);
		getDescendentOfType(ConfigArea.class).setConfigs(properties, container);
	}
	
	public void applyConfigs() {
	}

	public Container getContainer() {
		return container;
	}

	public void setContainer(Container container, String[] requiredAttributes, ConfigValues vals) {
		this.container = container;
		setConfigs();
	}
	
	
	public class Menu extends EXContainer{

		public Menu() {
			super("menu", "div");
			addClass("menu-x");
			setStyle("float", "left");
			for(int i = 0; i < ITEMS.length; i ++){
				addChild(new MenuItem(i));
			}
		}
		
		public void addItem(MenuItem item){
			addChild(item);
		}
	}
	
	public class MenuItem extends EXContainer{

		private int type;
		
		public MenuItem(int index) {
			super("", "div");
			this.type = index;
			addClass("menuBar");
			if(index == 0){
				addClass("selected");
			}
			setText(ITEMS[index]);
			addEvent(new Event(){

				public void ClientAction(ClientProxy container) {
					container.getAncestorOfType(EXConfigPanel.class).mask();
					container.makeServerRequest(this);
					
				}

				public boolean ServerAction(Container container,
						Map<String, String> request) throws UIException {
					
					ComponentUtil.iterateOverDescendentsOfType(container.getAncestorOfType(Menu.class), MenuItem.class, new ComponentVisitor() {
						
						@Override
						public void doVisit(Container c) {
							c.removeClass("selected");
							
						}
					});
					container.addClass("selected");
					container.getAncestorOfType(EXStyleConfigForm.class).setConfigs(type);
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
			setStyle("float", "left");
			
		}
		
		public void setConfigs(Map<String, String> configs, Container c){
			
			
			Iterator<String> iterator = configs.keySet().iterator();
			boolean update = false;
			
			if(iterator.hasNext()){
				String key = configs.keySet().toArray()[0].toString();
				String name = StringUtil.split(key, ".")[1];
				if(getDescendentByName(name) != null){
					update = true;
				}
			}
			if(update){
				while(iterator.hasNext()){
					String key = iterator.next();
					String name = StringUtil.split(key, ".")[1];
					StatefullComponent input=  (StatefullComponent)getDescendentByName(name);
					String val = c.getStyle(name);
					input.setValue(val);
				}
			}
			
			if(!update){
				this.getChildren().clear();
				this.setRendered(false);
				while(iterator.hasNext()){
					String key = iterator.next();
					String value = configs.get(key);
					String name = StringUtil.split(key, ".")[1];
					StatefullComponent input ;
					if(value.equals("DIMENSION")){
						input = new EXInput(name,c.getStyle(name));
					}else if(value.equals("FREE")){
						input = new EXInput(name,c.getStyle(name));
					}else if(value.equals("COLOR")){
						input = new EXColorPicker(name,c.getStyle(name));	
					}else if(value.equals("URL")){
						input = new EXInput(name,c.getStyle(name));
					}
					else{
						final String[] psValues = StringUtil.split(value, ",");
						
						DataModel model = new DataModel(){
	
							public int getSize() {
								return psValues.length;
							}
	
							public Object getValue(int index) {
								return psValues[index];
							}
						};
						
						input = new EXSelect(name,model,c.getStyle(name));
					}
					input.addClass("input");
					input.setStyle("width", "197px");
					input.addEvent(new Event(){
	
						public void ClientAction(ClientProxy container) {
							container.makeServerRequest(this);
							
						}
	
						public boolean ServerAction(Container container,
								Map<String, String> request) throws UIException {
							StatefullComponent comp = (StatefullComponent)container;
							String value = comp.getValue().toString();
							container.getAncestorOfType(EXStyleConfigForm.class).getContainer().setStyle(comp.getName(), value);
							return true;
						}
	
						public void Success(ClientProxy container,
								Map<String, String> request) throws UIException {
						}
						
					}, Event.BLUR);
					
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


	

}
