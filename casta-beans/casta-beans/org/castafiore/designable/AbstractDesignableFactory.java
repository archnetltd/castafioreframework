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
 package org.castafiore.designable;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.map.ListOrderedMap;
import org.castafiore.designer.config.ConfigForm;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.js.JMap;

public abstract class AbstractDesignableFactory extends EXContainer implements DesignableFactory {
	
	private String icon = "icons/application.png";

	public AbstractDesignableFactory(String name) {
		super(name, "div");
		addClass("components");
		addClass("item");
		addClass("ui-state-hover");
		setStyle("padding", "0 5px");
		setStyle("margin", "0 5px").setStyle("text-align", "center").setStyle("min-width", "70px");

		
		
		//A start drag event that sets the draggable component wo o
		addEvent(new Event(){

			public void ClientAction(ClientProxy container) {
				container.setStyle("opacity", "0.1");
				
			}

			public boolean ServerAction(Container container,
					Map<String, String> request) throws UIException {
				return false;
			}

			public void Success(ClientProxy container,
					Map<String, String> request) throws UIException {
				
			}
			
		}, Event.START_DRAG);
		
		
		addEvent(new Event(){

			public void ClientAction(ClientProxy container) {
				container.setStyle("opacity", "1");
				
			}

			public boolean ServerAction(Container container,
					Map<String, String> request) throws UIException {
				return false;
			}

			public void Success(ClientProxy container,
					Map<String, String> request) throws UIException {
			}
			
		}, Event.END_DRAG);
		
	}

	public JMap getDraggableOptions() {
		return new JMap().put("revert", true);
	}
	
	public abstract Container getInstance();

	public abstract String getCategory();
	
	

	
	public Map<String, ConfigForm> getAdvancedConfigs(){
		Map<String, ConfigForm> forms = new ListOrderedMap();
		return forms;
	}
	protected void assertPresent(Map<String, String> attributes,String key, CheckFormat checkFormat){
		
		if(attributes.containsKey(key.toLowerCase())){
			if(checkFormat != null){
				if(!checkFormat.doCheckFormat(attributes.get(key))){
					throw new InvalidDesignableException("There was an error in configuration: key " + key + " is not in correct format in designable " + getUniqueId());
				}
			}
		}else{
			throw new InvalidDesignableException("There was an error in configuration: key " + key + " is mandatory in designable " + getUniqueId());
		}
		
		
	}
	
	public void checkRequiredAttributes(Map<String, String> attributes){
		if(getRequiredAttributes() != null){
			for(String s : getRequiredAttributes()){
				assertPresent(attributes, s);
			}
		}
		
	}
	
	protected void assertPresent(Map<String, String> attributes,String key){
		this.assertPresent(attributes, key, null);
	}
	
	public interface CheckFormat{
		public boolean doCheckFormat(String value);
	}
	
	public void refresh(Container c){
		
		Map<String, String> attrs = new HashMap<String, String>();
		String[] aattrs = c.getAttributeNames();
		for(String s : aattrs){
			attrs.put(s, c.getAttribute(s));
			applyAttribute(c, s, c.getAttribute(s));
		}
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	
}
