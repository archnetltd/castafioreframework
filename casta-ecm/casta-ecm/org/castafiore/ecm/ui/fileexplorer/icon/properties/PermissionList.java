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
 package org.castafiore.ecm.ui.fileexplorer.icon.properties;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.contextmenu.ContextMenuAble;
import org.castafiore.ui.ex.contextmenu.ContextMenuModel;
import org.castafiore.ui.input.Decoder;
import org.castafiore.ui.input.Encoder;
import org.castafiore.utils.ResourceUtil;

public class PermissionList extends EXContainer implements StatefullComponent {
	
	private String[] permissions;

	public PermissionList(String name, String[] permissions) {
		super(name,"div");
		
		
		addStyleSheet(ResourceUtil.getDownloadURL("classpath", "org/castafiore/ecm/ui/fileexplorer/icon/properties/PermissionList.css"));
		setStyle("overflow-y", "auto");
		setStyle("border", "solid 1px silver");
		setStyle("font-size", "12px");
		setHeight(Dimension.parse("100px"));
		setWidth(Dimension.parse("300px"));
		setPermissions(permissions);
	}
	
	public String[] getPermissions() {
		return permissions;
	}

	public void setPermissions(String[] permissions) {
		this.permissions = permissions;
		init();
	}
	
	
	
	public void addItem(String item)
	{
		addChild(new Item("", item));
	}

	public void init()
	{
		this.getChildren().clear();
		
		this.setRendered(false);
		
		if(permissions != null)
		{
			for(String sitem : permissions)
			{
				Item item = new Item("", sitem);
				addChild(item);
			}
		}
	}
	
	public class Item extends EXContainer implements ContextMenuAble
	{

		public Item(String name, String item) {
			super(name, "div");
			setStyleClass("item");
			setText(item);
			addEvent(new Event(){

				public void ClientAction(ClientProxy container) {
					container.getAncestorOfType(PermissionList.class).setAttribute("value", container.text());
					
				}

				public boolean ServerAction(Container container,
						Map<String, String> request) throws UIException {
					// TODO Auto-generated method stub
					return false;
				}

				public void Success(ClientProxy container,
						Map<String, String> request) throws UIException {
					// TODO Auto-generated method stub
					
				}
				
			}, Event.CLICK);
			
		}

		public ContextMenuModel getContextMenuModel() {
			ContextMenuModel model = new ContextMenuModel(){

				public Event getEventAt(int index) {
					return new Event(){

						public void ClientAction(ClientProxy container) {
							// TODO Auto-generated method stub
							
						}

						public boolean ServerAction(Container container, Map<String, String> request) throws UIException {
							// TODO Auto-generated method stub
							return false;
						}

						public void Success(ClientProxy container, Map<String, String> request) throws UIException {
							// TODO Auto-generated method stub
							
						}
						
					};
				}

				public String getIconSource(int index) {
					return "#";
				}

				public String getTitle(int index) {
					return "Delete";
				}

				public int size() {
					return 1;
				}
				
			};
			
			return model;
		}
		
	}

	public Decoder getDecoder() {
		// TODO Auto-generated method stub
		return null;
	}

	public Encoder getEncoder() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getRawValue() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getValue() {
		List<String> value = new ArrayList<String>();
		for(Container child : getChildren())
		{
			if(child instanceof Item)
			{
				value.add(child.getText());
			}
		}
		return value;
	}

	public void setDecoder(Decoder decoder) {
		// TODO Auto-generated method stub
		
	}

	public void setEncoder(Encoder encoder) {
		// TODO Auto-generated method stub
		
	}

	public void setRawValue(String rawValue) {
		// TODO Auto-generated method stub
		
	}

	public void setValue(Object value) {
		this.getChildren().clear();
		if(value instanceof List)
		{
			List l = (List)value;
			for(Object o : l)
			{
				addItem(l.toString());
			}
		}
		setRendered(false);
		
	}


	

}
