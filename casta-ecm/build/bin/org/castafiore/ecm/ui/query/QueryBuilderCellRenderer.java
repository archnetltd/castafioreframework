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
 package org.castafiore.ecm.ui.query;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.form.EXAutoComplete;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.ui.ex.form.table.CellRenderer;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.EventUtil;

public class QueryBuilderCellRenderer implements CellRenderer {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static String[] OPERATORS = new String[]{"=", "<", ">", "<=", ">=", "!=", "like", "between"};

	public Container getComponentAt(int row, int column, int page,
			TableModel model, EXTable table) {
		if(column == 0){
			Container c = ComponentUtil.getContainer("", "span", model.getValueAt(column, row, page).toString(), "ui-priority-primary");
			c.setStyle("margin", "0 5px 0 3px");
			return c;
		}else if(column == 1){
			Object val = model.getValueAt(column, row, page);
			Container c =  ComponentUtil.getContainer("dataType", "span", EXQueryBuilder.getType(val), "ui-priority-secondary");
			c.setStyle("margin", "0 5px 0 3px");
			return c;
		}else if(column == 2){
			List<String> options = new ArrayList<String>();
			options.add("None");
			options.add("ASC");
			options.add("DESC");
			EXAutoComplete complete = new EXAutoComplete("", "None", options);
			complete.setWidth(Dimension.parse("50px"));
			complete.setStyle("border", "solid 1px silver");
			complete.setStyle("margin", "0 5px 0 3px");
			return complete;
			
		}else if(column == 3){
			List<Object> data = new ArrayList<Object>();
			Object val = model.getValueAt(1, row, page);
			String type = EXQueryBuilder.getType(val);
			for(String s : OPERATORS){
				if(type.equals(EXQueryBuilder.TEXT_TYPE)){
					if(!s.equals("between"))
						data.add(s);
				}else if(type.equals(EXQueryBuilder.BOOLEAN_TYPE)){
					if(s.equals("=")){
						data.add(s);
					}
				}else if(!s.equals("like")){
					data.add(s);
				}
					
			}
			
			EXSelect select = new EXSelect("", new DefaultDataModel(data));
			select.setStyle("border", "solid 1px silver");
			select.setStyle("margin", "0 5px 0 3px");
			select.setAttribute("method", "changeOperator");
			select.setAttribute("ancestor", EXQueryBuilder.class.getName());
			select.addEvent(EventUtil.GENERIC_FORM_METHOD_EVENT, Event.CHANGE);
			select.setWidth(Dimension.parse("90px"));
			return select;
		}else{
			
			Container c =  ComponentUtil.getContainer("inputContainer", "div");
			EXInput input = new EXInput("");
			input.setStyle("border", "solid 1px silver");
			input.setStyle("margin", "0 5px 0 3px");
			input.setStyle("width", "110px");
			c.addChild(input);
			return c;
		}
	}

	public void onChangePage(Container component, int row, int column,
			int page, TableModel model, EXTable table) {
		// TODO Auto-generated method stub
		
	}

}
