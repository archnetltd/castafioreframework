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
 package org.castafiore;

import org.castafiore.ui.Container;
import org.castafiore.ui.DescriptibleApplication;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.ex.EXApplication;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.table.CellRenderer;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.utils.ComponentUtil;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class EXApplicationListApp extends EXApplication implements ApplicationContextAware , TableModel, CellRenderer{
	
	private ApplicationContext context = null;
	
	//private Map<String, Application> apps = new HashMap<String, Application>();
	private String[] apps;

	public EXApplicationListApp() {
		super("apps");
		
		
	}
	
	public void init(){
		apps = context.getBeanNamesForType(DescriptibleApplication.class);
		
		EXTable table = new EXTable("table", this);
		table.setWidth(Dimension.parse("500px"));
		table.setCellRenderer(this);
		addChild(ComponentUtil.getContainer("", "h1", "List of registered applications", null));
		addChild(table);
	}

	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		this.context = context;
		
	}

	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	public int getColumnCount() {
		return 2;
	}

	public String getColumnNameAt(int index) {
		if(index == 0){
			return "Name";
		}else if (index == 1){
			return "Implementation Class";
		}else{
			return "Description";
		}
	}

	public int getRowCount() {
		return apps.length;
	}

	public int getRowsPerPage() {
		return getRowCount();
	}

	public Object getValueAt(int col, int row, int page) {
		if(col == 0){
			return apps[row];
		}else if(col == 1){
			//return apps.get(apps.keySet().toArray()[row]).getClass().getName();
			return context.getType(apps[row]).getName();
		}else{
			return "";
		}
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public Container getComponentAt(int row, int column, int page,
			TableModel model, EXTable table) {
		if(column == 0){
			Container a = new EXContainer("a", "a");
			String url = "/casta-ui/index.jsp?applicationid=" + model.getValueAt(column, row, page);
			a.setAttribute("href", url);
			a.setText(model.getValueAt(column, row, page).toString());
			return a;
		}else if(column == 1){
			Container p = new EXContainer("p", "p");
			p.setText(model.getValueAt(column, row, page).toString());
			return p;
		}else{
			return null;
		}
	}

	public void onChangePage(Container component, int row, int column,
			int page, TableModel model, EXTable table) {
		// TODO Auto-generated method stub
		
	}

}
