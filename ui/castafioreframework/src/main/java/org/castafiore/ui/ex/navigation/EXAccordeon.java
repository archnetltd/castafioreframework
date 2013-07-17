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
 package org.castafiore.ui.ex.navigation;

import java.util.Map;

import org.castafiore.JQContants;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.EXWidget;
import org.castafiore.ui.ex.tab.TabModel;
import org.castafiore.ui.ex.tab.TabPanel;
import org.castafiore.ui.ex.tab.TabRenderer;
import org.castafiore.utils.ComponentUtil;

public class EXAccordeon extends EXWidget implements JQContants, TabPanel {
	
	private TabModel model;

	public EXAccordeon(String name) {
		super(name, "div");
		addClass("ui-accordion");
		setReset();
	}

	public TabModel getModel() {
		return model;
	}

	public void setModel(TabModel model) {
		this.model = model;
		refresh();
	}
	
	public void refresh(){
		
		this.getChildren().clear();
		this.setRendered(false);
		
		int size = model.size();
		for(int i =0; i < size; i ++){
			if(i == model.getSelectedTab()){
				EXACCItem item = new EXACCItem("", model.getTabLabelAt(this, i, false), model.getTabContentAt(this, i), model.getSelectedTab() == i, i);
				addChild(item);
			}else{
				EXACCItem item = new EXACCItem("", model.getTabLabelAt(this, i, false), null, model.getSelectedTab() == i, i);
				addChild(item);
			}
		}
		
	}
	
	public static class EXACCItem extends EXContainer{

		public EXACCItem(String name, String label, Container content, boolean selected, int index) {
			super(name, "div");
			
			String headerStyle = ACC_HEADER_CLOSE_STYLE;
			if(selected){
				headerStyle = ACC_HEADER_OPEN_STYLE;
			}
			
			Container header = ComponentUtil.getContainer("header", "h3", null, headerStyle);
			addChild(header);
			header.addEvent(new TOGGLE(), Event.CLICK);
			header.setAttribute("hindex", index + "");
			if(selected){
				header.addChild(ComponentUtil.getContainer("arrow", "span", null, ACC_ARROW_OPEN_STYLE));
				setStyleClass("acc_is_open");
			}
			else{
				header.addChild(ComponentUtil.getContainer("arrow", "span", null, ACC_ARROW_CLOSE_STYLE));
				setStyleClass("acc_is_close");
			}
			Container a = ComponentUtil.getContainer("text", "a", label,null );
			header.addChild(a);
			
			Container contentContainer = ComponentUtil.getContainer("content", "div", null, ACC_CONTENT_STYLE);
			contentContainer.addClass("contentContainer");
			addChild(contentContainer);
			
			contentContainer.setDisplay(selected);
			
			if(content != null){
				contentContainer.addChild(content);
			}
			
		}
		
		private static class TOGGLE implements Event{

			public void ClientAction(ClientProxy container) {
				//set all open item's header to down
				String accId = container.getAncestorOfType(EXAccordeon.class).getId();
				ClientProxy downAllArrows = new ClientProxy("#" + accId +  " .ui-icon-triangle-1-s").setAttribute("class", ACC_ARROW_CLOSE_STYLE);
				ClientProxy hideAllContents =new ClientProxy("#" + accId +  " .acc_is_open .contentContainer").slideUp(100).mergeCommand(new ClientProxy("#" + accId +  " .acc_is_open").setAttribute("class", "acc_is_close"));
				ClientProxy closeAll  = hideAllContents.mergeCommand(downAllArrows);
				
				ClientProxy open = container.clone().setAttribute("class", ACC_HEADER_OPEN_STYLE).getParent().setAttribute("class", "acc_is_open").getDescendentByName("content").slideDown(100).getParent().getDescendentByName("arrow").setAttribute("class", ACC_ARROW_OPEN_STYLE);
				
				open = closeAll.mergeCommand(open);
				ClientProxy close = container.clone().setAttribute("class", ACC_HEADER_CLOSE_STYLE).getParent().getDescendentByName("content").slideUp(100).getParent().getDescendentByName("arrow").setAttribute("class", ACC_ARROW_CLOSE_STYLE);
				ClientProxy hidrate = container.clone().makeServerRequest(this);
				if(container.getAncestorOfType(EXACCItem.class).getDescendentByName("content").getChildren().size() > 0){
					container.IF(container.getParent().getDescendentByName("content").getStyle("display").equal("none"), open,close);
				}else{
					container.IF(container.getParent().getDescendentByName("content").getStyle("display").equal("none"), hidrate,close);
				}
			}

			
			public boolean ServerAction(Container container,Map<String, String> request) throws UIException {
				TabModel model =  container.getAncestorOfType(EXAccordeon.class).model;
				Container c = model.getTabContentAt(container.getAncestorOfType(EXAccordeon.class), Integer.parseInt(container.getAttribute("hindex")));
				container.getAncestorOfType(EXACCItem.class).getChild("content").addChild(c.setDisplay(true));
				container.setRendered(false);
				return true;
			}
			public void Success(ClientProxy container,Map<String, String> request) throws UIException {
				String accId = container.getAncestorOfType(EXAccordeon.class).getId();
				ClientProxy downAllArrows = new ClientProxy("#" + accId +  " .ui-icon-triangle-1-s").setAttribute("class", ACC_ARROW_CLOSE_STYLE);
				ClientProxy hideAllContents =new ClientProxy("#" + accId +  " .acc_is_open .contentContainer").slideUp(100).mergeCommand(new ClientProxy("#" + accId +  " .acc_is_open").setAttribute("class", "acc_is_close"));
				ClientProxy closeAll  = hideAllContents.mergeCommand(downAllArrows);
				
				ClientProxy open = container.clone().setAttribute("class", ACC_HEADER_OPEN_STYLE).getParent().setAttribute("class", "acc_is_open").getDescendentByName("content").slideDown(100).getParent().getDescendentByName("arrow").setAttribute("class", ACC_ARROW_OPEN_STYLE);
				
				open = closeAll.mergeCommand(open);
				container.mergeCommand(open);
			}
		}
		
	}

	@Override
	public TabRenderer getTabRenderer() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
