/*
 * Copyright (C) 2007-2008 Castafiore
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

package org.castafiore.ui.ex.list;

import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ComponentVisitor;
/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 22, 2008
 */
public class EXList<T> extends AbstractEXList<T> implements ListItemRenderer<T>{
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EXList(String name, DataModel<T> model) {
		super(name, "ul", model);
		setStyle("padding", "0").setStyle("margin", "0");
		super.setRenderer(this);
	}

	public EXList(String name, DataModel<T> model, T value) {
		super(name, "ul", model);
		super.setRenderer(this);
		setValue(value);
		setModel(model);
	}

	

	@Override
	public ListItem<T> getCellAt(int index, T value, DataModel<T> model,	List<T> holder) {
		EXListItem<T> item = new EXListItem<T>("", "a");
		item.setData(value);
		item.setText(value.toString());
		return item;
	}

	@Override
	public void addItem(final ListItem<T> item) {
		addChild(new EXContainer(getChildren().size() + "", "li").setStyle("list-style","none").addChild(item));
		item.addEvent(new Event() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void Success(ClientProxy container, Map<String, String> request)
					throws UIException {
			}
			
			
			@Override
			public boolean ServerAction(final Container container, Map<String, String> request)
					throws UIException {
				final EXList exList = container.getAncestorOfType(EXList.class);
				exList.setAttribute("value", container.getParent().getName());
				ComponentUtil.iterateOverDescendentsOfType(exList,EXListItem.class,new ComponentVisitor() {
					
					@Override
					public void doVisit(Container c) {
						exList.selectItem((EXListItem)c, c.getName().equalsIgnoreCase(container.getName()));
						
					}
				});
				return true;
			}
			
			@Override
			public void ClientAction(ClientProxy container) {
				container.mask().makeServerRequest(this);
				
			}
		}, Event.CLICK);
	}

	@Override
	public ListItem<T> getItem(int index) {
		return (ListItem<T>)getChildByIndex(index).getChildByIndex(0);
	}

	@Override
	public int getSize() {
		return getChildren().size();
	}

	@Override
	public void selectItem(ListItem<T> child, boolean selected) {
		if(selected)
			child.addClass("ui-state-active");
		else
			child.removeClass("ui-state-active");
		
	}	
}
