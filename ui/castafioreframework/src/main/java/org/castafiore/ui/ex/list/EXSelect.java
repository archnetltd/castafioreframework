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


/**
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * June 27 2008
 */ 
public class EXSelect extends AbstractEXList<Object> implements ListItemRenderer<Object> {
	
	
	public EXSelect(String name, DataModel<Object> model) {
		super(name, "select", model);	
		super.setRenderer(this);
		
		if(model.getSize() > 0)
			setValue(model.getValue(0));
		
	}

	public EXSelect(String name, DataModel<Object> model, Object value) {
		super(name, "select", model);	
		super.setRenderer(this);
		setValue(value);
		
	}
	
	

	@Override
	public void selectItem(ListItem<Object> item, boolean selected) {
		if(selected)
			item.setAttribute("selected", "selected");
		else
			item.setAttribute("selected",(String)null);
	}

	@Override
	public ListItem<Object> getCellAt(int index, Object value,
			DataModel<Object> model, List<Object> holder) {
		EXListItem<Object> option = new EXListItem<Object>("", "option");
		option.setAttribute("value", index + "");
		option.setText(value.toString());
		option.setData(value);
		return option;
	}

	@Override
	public void addItem(ListItem<Object> item) {
		addChild(item);
		
	}

	@Override
	public ListItem<Object> getItem(int index) {
		return (ListItem<Object>)getChildByIndex(index);
	}

	@Override
	public int getSize() {
		return getChildren().size();
	}

	
	
	

	
	



	

}
