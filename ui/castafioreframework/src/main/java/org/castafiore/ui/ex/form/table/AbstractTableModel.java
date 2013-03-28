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

package org.castafiore.ui.ex.form.table;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.castafiore.ui.Container;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.table.model.Column;
import org.castafiore.utils.ExceptionUtil;
/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 22, 2008
 */
public abstract class AbstractTableModel<T> implements TableModel {
	
	private List<Column> columns = new ArrayList<Column>();
	
	private List<Method> methods = new ArrayList<Method>();
	
	private Class<T> classType_ = null;
	
	public final static CellRenderer cellRenderer = new CellRenderer(){

		public Container getComponentAt(int row, int column, int page, TableModel model, EXTable table) {
			
			AbstractTableModel<Object> tModel = (AbstractTableModel<Object>)model;
			Object value = model.getValueAt(column, row, page);
			if(column < tModel.methods.size())
			{
				
				EXContainer span = new EXContainer("", "span");
				span.setText(value.toString());
				return span;
			}
			else
			{
				EXContainer div = tModel.getActionButtonAt(column - tModel.methods.size());
				
				Event evt = tModel.getEventAt(column - tModel.methods.size(), row,page);
				
				div.addEvent(evt, Event.CLICK);
				return div;
				
			}
			
			
		}

		public void onChangePage(Container component, int row, int column, int page, TableModel model, EXTable table) {
			
			AbstractTableModel tModel = (AbstractTableModel)model;
			
			if(column < tModel.methods.size())
			{
				Object newValue = model.getValueAt(column, row, page);
				EXContainer span = (EXContainer)component;
				span.setText(newValue.toString());
				span.setRendered(false);
			}
			
			
		}
		
	};
	
	
	public AbstractTableModel(Class<T> classType)
	{
		this.classType_ = classType;
		loadFields();
	}

	
	public abstract int actionSize();
	
	public abstract String getActionNameAt(int index);
	
	public int getColumnCount() {
		return columns.size() + actionSize();
	}
	
	

	public String getColumnNameAt(int index) {
		if(index < columns.size())
			return columns.get(index).label();
		
		return getActionNameAt(index-columns.size());
	}
 
	public abstract List<Object> getData();
	
	public int getRowCount() {
		
		return getData().size();
	}

	public int getRowsPerPage() {
		return 10;
	}
	 
	private void loadFields()
	{
		Method[] methods = classType_.getMethods();
		List<Column> tmp = new ArrayList<Column>();
		List<Method> tmpMethods = new ArrayList<Method>();
		for(Method m : methods)
		{
			
			Column f = m.getAnnotation(Column.class);
			if(f != null)
			{
				int position = f.position();
				
				while(position >= tmp.size())
				{
					tmp.add(null);
					tmpMethods.add(null);
				}
				tmp.add(position,f);
				tmpMethods.add(position,m);
				
			} 
		}
		
		for(Column f : tmp)
		{
			if(f != null)
			{
				columns.add(f);
			}
		
		}
		for(Method m : tmpMethods)
		{
			if(m != null)
			{
				this.methods.add(m);
			}
		
		}
	}

	public abstract Event getEventAt(int index, int row, int page);
	public abstract EXContainer getActionButtonAt(int index);
	
	public Object getValueAt(int col, int row, int page) {
		try
		{
			int realRow = getRowsPerPage()*page + row;
			Object o = getData().get(realRow);
			
			if(col < methods.size())
			{
				Method m = methods.get(col);
				
				if(m.getName().startsWith("is"))
				{
					return m.invoke(o, null);
				}
				else if(m.getName().startsWith("get"))
				{
					return m.invoke(o, null);
				}
				else if(m.getName().startsWith("set"))
				{
					return classType_.getMethod("get" + m.getName().substring(3), null).invoke(o, null);
				}
				else
				{
					return classType_.getMethod("getId", null).invoke(o, null);
				}
			}
			else
			{
				return classType_.getMethod("getId", null).invoke(o, null);
			}
		} 
		catch(Exception e)
		{
			throw ExceptionUtil.getRuntimeException("unable to get data for row :" + row + " and column:" + col, e);
		}
	}

	public void setValueAt(int col, int row, int page, Object newValue) {
		// TODO Auto-generated method stub
		
	}

	public Class<T> getClassType() {
		return classType_;
	}

	
	
	public Class<?> getColumnClass(int columnIndex) {
		if(this.getRowCount() > 0)
		{
			Object o = getValueAt(columnIndex, 0, 0);
			if( o != null)
			{
				return o.getClass();
			}
		}
		return null;
	}

	
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		
		return false;
	}
	
	public abstract void flush();
	

}
