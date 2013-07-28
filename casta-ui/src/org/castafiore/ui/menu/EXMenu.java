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
 package org.castafiore.ui.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.castafiore.ui.Application;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.button.EXIconButton;
import org.castafiore.ui.ex.form.button.Icons;
import org.castafiore.ui.ex.toolbar.ToolBarItem;
import org.castafiore.ui.ex.tree.TreeNode;
import org.castafiore.ui.js.JMap;
import org.castafiore.ui.js.Var;
import org.castafiore.utils.ComponentUtil;

public class EXMenu extends EXIconButton implements ToolBarItem {
	
	public final static int STYLE_IPOD = 0;
	
	public final static int FLY_OUT_STYLE = 1;
	
	private int menuStyle = FLY_OUT_STYLE;
	
	private List<MenuListener> listeners = new ArrayList<MenuListener>(2);

	public EXMenu(String name, TreeNode<EXMenuItem> node) {
		super(name, Icons.ICON_TRIANGLE_1_S);
		
		
		EXMenuItem item = node.getComponent();
		String buttonLabel = item.getText();
		//EXIconButton button = new EXIconButton(name,buttonLabel, Icons.ICON_TRIANGLE_1_S);
		setIconPosition(2);
		setText(buttonLabel);
		//addChild(button);
		
		
		Container div = ComponentUtil.getContainer("", "div", null, getId() + "_items");
		div.setDisplay(false);
		addChild(div);
		Container ul = ComponentUtil.getContainer("", "ul", null, null);
		div.addChild(ul);
		
		int size = node.childrenCount();
		for(int i = 0; i < size; i ++){
			ul.addChild(new EXMenuNode("", node.getNodeAt(i)));
		}
		
		addEvent(new selectMenu(), Event.READY);
		
	}
	
	
	public int getMenuStyle() {
		return menuStyle;
	}
	public void setMenuStyle(int menuStyle) {
		this.menuStyle = menuStyle;
	}
	public void addMenuListener(MenuListener menuListener){
		this.listeners.add(menuListener);
	}
	public void clearMenuListeners(){
		this.listeners.clear();
	}
	
	public void fireMenuListeners(EXMenuItem item){
		for(MenuListener listener : listeners){
			listener.onSelectItem(item, this);
		}
	}

	
	private class EXMenuNode extends EXContainer{

		public EXMenuNode(String name, TreeNode<EXMenuItem> node) {
			super(name, "li");
			addChild(node.getComponent());
			Container ul = ComponentUtil.getContainer("", "ul", null, null);
			int size = node.childrenCount();
			
			if(size > 0){
				addChild(ul);
				
				
				for(int i = 0; i < size; i ++){
					ul.addChild(new EXMenuNode("", node.getNodeAt(i)));
				}
			}
			
		}
		
	}
	
	
	
	private  class selectMenu implements Event{
		
		


		public void ClientAction(ClientProxy container) {
			JMap params = new JMap();
			params.put("content", new ClientProxy("."  + container.getId() + "_items").html() );
			
			if(menuStyle == STYLE_IPOD)
				params.put("backLink", false);
			else
				params.put("flyOut", true);
			
			
			params.put("onSelect", container.clone().mask().makeServerRequest(new JMap().put("item",new Var("item.id") ),this ), "item")  ;
					
					
					container.getScript("js/fg.menu.js", container.clone().addMethod("menu", params));
			
			//container.addMethod("menu", params);
			
		} 


		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			
			String id = request.get("item");
			Application root = container.getRoot();
			EXMenuItem item =  (EXMenuItem)root.getDescendentById(id);
			((EXMenu)container).fireMenuListeners(item);
			return true;
		}

	
		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			// TODO Auto-generated method stub
			
		}
		
	};

}
