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
 package org.castafiore.beans.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.castafiore.beans.info.BeanInfoService;
import org.castafiore.beans.info.IBeanInfo;
import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.layout.EXBorderLayoutContainer;
import org.castafiore.ui.ex.layout.EXUnOrderedList;
import org.castafiore.ui.tabbedpane.TabModel;
import org.castafiore.ui.tabbedpane.TabPanel;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.utils.ComponentUtil;

public class BeanInfoAccModel implements TabModel, Event {
	
	private final static String[] LABELS = new String[]{"Studio Beans"};
	
	private final static  List<Map<String, String>> TYPES = new ArrayList<Map<String,String>>();
	
	static{
		Map<String, String> studioBeanTypes = new HashMap<String, String>();
		studioBeanTypes.put("Designable factories", "org.castafiore.designer.DesignableFactory");
		studioBeanTypes.put("Event macro", "org.castafiore.designer.macro.EventMacro");
		TYPES.add(studioBeanTypes);
	}

	public int getSelectedTab() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Container getTabContentAt(TabPanel pane, int index) {
		
		Map<String, String> types = TYPES.get(index);
		
		//Container c = ComponentUtil.getContainer("", "div", null, null);
		EXContainer list = new EXContainer("", "ul");
		list.addClass("ui-widget");
		list.addClass("ui-list-widget");
		list.setWidth(Dimension.parse("100%"));
		
		Iterator<String> iter = types.keySet().iterator();
		int count = 0;
		while(iter.hasNext()){
			EXContainer li = new EXContainer("", "li");
			String label = iter.next();
			String clazz = types.get(label);
			Container button = ComponentUtil.getContainer("", "a", label, null);
			button.setAttribute("href", "#");
			button.setAttribute("clazz", clazz);
			li.addChild(button);
			button.addEvent(this, Event.CLICK);
			
			list.addChild(li);
			count++;
		}
		return list;
	}

	public String getTabLabelAt(TabPanel pane, int index, boolean selected) {
		return LABELS[index];
	}

	public int size() {
		// TODO Auto-generated method stub
		return 1;
	}

	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}

	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		try{
			String clazz = container.getAttribute("clazz");
			Class cls = Thread.currentThread().getContextClassLoader().loadClass(clazz);
			Map<String, IBeanInfo> infos = BaseSpringUtil.getBeanOfType(BeanInfoService.class).getBeanInfo(cls);
			
			BeansTableModel model =  new BeansTableModel(infos);
			
		 	Container c  = container.getAncestorOfType(EXBeanInfo.class).getContainer(EXBorderLayoutContainer.CENTER);
		 	c.getChildren().clear();
		 	c.setRendered(false);
		 	
		 	EXTable table = new EXTable("", model);
		 	
		 	c.addChild(table);
		}
		catch(Exception e){
			throw new UIException(e);
		}
		return true;
	}

	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
