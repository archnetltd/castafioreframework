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
 package org.castafiore.sms.ui;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.sms.SMSInstance;
import org.castafiore.sms.SendSMSSchedular;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.ex.form.button.EXIconButton;
import org.castafiore.ui.ex.form.button.Icons;
import org.castafiore.ui.ex.form.table.TableModel;

public class ListSMSInstanceTableModel implements TableModel {
	
	private List<SMSInstance> data = new ArrayList<SMSInstance>();
	
	
	
	public ListSMSInstanceTableModel() {
		super();
		data = SpringUtil.getBeanOfType(SendSMSSchedular.class).getSMS(SendSMSSchedular.STATUS_NEW);
	}

	private final static String[] LABELS = new String[]{"id", "Cron", "Message", "Response", "Status", "Edit", "Delete"};

	
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	
	public int getColumnCount() {
		return LABELS.length;
	}

	
	public String getColumnNameAt(int index) {
		return LABELS[index];
	}

	
	public int getRowCount() {
		return data.size();
	}

	
	public int getRowsPerPage() {
		return 10;
	}

	
	public Object getValueAt(int col, int row, int page) {
		int index = (page*getRowsPerPage()) + row;
		SMSInstance instance = data.get(index);
		if(col == 0){
			return instance.getId();
		}else if(col == 1){
			return instance.getCron();
		}else if(col == 2){
			String message = instance.getMessage();
			return message.substring(0, 10);
		}else if(col == 3){
			return instance.getResponse();
		}else if( col == 4){
			return instance.getStatus();
		}else if(col == 5){
			return new EXIconButton("edit", Icons.ICON_PENCIL);
		}else{
			return new EXIconButton("delete", Icons.ICON_MINUSTHICK);
		}
		// TODO Auto-generated method stub
		//return null;
	}

	
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return false;
	}

}
