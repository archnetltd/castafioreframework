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

import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.AbstractStatefullComponent;
/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 22, 2008
 */
public abstract class AbstractEXList<T> extends AbstractStatefullComponent implements List<T>
{
	private DataModel<T> model_;
	
	private ListItemRenderer<T> renderer_ = null;
	
	public AbstractEXList(String name, String tagName,DataModel<T> model) {
		super(name, tagName);
		this.setModel(model);	
		
	}

	@Override
	public void refresh() {
		if(this.renderer_ == null)
			return;
		this.getChildren().clear();
		this.setRendered(false);
		int size = model_.getSize();
		for(int i = 0; i < size; i ++)
		{
			T value = model_.getValue(i);
			ListItem<T> container = renderer_.getCellAt(i, value, model_,this);
			addItem(container);
		}
		
	}
 
	public ListItemRenderer<T> getRenderer() {
		return renderer_;
	}

	public void setRenderer(ListItemRenderer<T> renderer_) {
		this.renderer_ = renderer_;
		refresh();
	}

	public DataModel<T> getModel() {
		return model_;
	}

	public void setModel(DataModel<T> model_) {
		this.model_ = model_;
		refresh();
	}

	@Override
	public Object getValue() {
		try
		{
			return model_.getValue(Integer.parseInt(getRawValue()));
		}
		catch(Exception e)
		{
			
		}
		
		return null;
	}

	public abstract  void selectItem(ListItem<T>  item, boolean selected);
	
	@Override
	public void setValue(Object value) {
		
		
		try
		{
			for(int i = 0; i < model_.getSize(); i ++)
			{
				ListItem<T> selectedChild = getItem(i);
				boolean selected = model_.getValue(i).equals(value);
				selectedChild.setSelected(selected);
				selectItem(selectedChild, selected);
				if(selected)
					setRawValue(i + "");
			}
		}
		catch(Exception e)
		{
			
		}
	}


	public String getRawValue() {
		return getAttribute("value");
	}


	public void setRawValue(String rawValue) {
		
		setAttribute("value", rawValue);
	}
}
