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
 package org.castafiore.community.ui.users;

import org.castafiore.community.ui.CommunityEvents;
import org.castafiore.ui.Container;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.table.DefaultCellRenderer;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ResourceUtil;



public class UsersCellRenderer extends DefaultCellRenderer {
	
	public Container getComponentAt(int row, int column,int page, TableModel model,
			EXTable table) {
		Object value = model.getValueAt(column, row, page);
		//table.addStyleSheet(ResourceUtil.getDownloadURL("classpath", "org/castafiore/resource/table/table.css"));
		if(column <= 3){
			EXContainer span = new EXContainer("", "span");
			if(value == null)
			{
				span.setText(" ");
			}
			else
			{
				span.setText(value.toString());
			}
			return span;
		}else{
			EXContainer span = new EXContainer("", "span");
			Container c = ComponentUtil.getContainer("", "div", null, "ui-state-default ui-corner-all UITableIcon");
			span.addClass("ui-icon").setStyle("margin-top", "0px");
			span.setAttribute("username", value.toString());
			if(column == 4){
				span.addClass("ui-icon-pencil");
				span.addEvent(CommunityEvents.SHOW_USER_FORM_EVENT, Event.CLICK);
			}else{
				span.addClass("ui-icon-circle-close");
				c.addEvent(CommunityEvents.DELETE_USER_EVENT, Event.CLICK);
			}
			c.addChild(span);
			c.setAttribute("username", value.toString());
			return c;
		}
		//return new EXEditableLabel(row + "-" + column,value.toString());
	}

	public void onChangePage(Container component,  int row,	int column,int page, TableModel model, EXTable table) 
	{
		Object newValue = model.getValueAt(column, row, page);
		EXContainer span = (EXContainer)component;
		if(column <= 3){
			if(newValue == null)
			{
				span.setText(" ");
			}
			else
			{
				span.setText(newValue.toString());
			}
			span.setRendered(false);
		}else{
			span.setAttribute("username", newValue.toString());
		}
		
	}

}
