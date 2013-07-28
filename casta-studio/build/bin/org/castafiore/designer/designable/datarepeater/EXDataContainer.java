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
 package org.castafiore.designer.designable.datarepeater;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.designer.designable.table.JSONTableModel;
import org.castafiore.designer.layout.EXDroppableGroovyTemplateLayout;
import org.castafiore.ui.ex.form.table.TableModel;

public class EXDataContainer extends EXDroppableGroovyTemplateLayout implements DataContainer {
	
	private TableModel model;

	public EXDataContainer(String name) {
//		super(name, ResourceUtil.getDownloadURL("classpath", "org/castafiore/designer/layout/DefaultTemplate.xhtml"));
//		setAttribute("templatelocation", ResourceUtil.getDownloadURL("classpath", "org/castafiore/designer/layout/DefaultTemplate.xhtml"));
		super(name);
	}

	public EXDataContainer(String name, String defaultTemplate) {
		super(name);
		setAttribute("templatelocation", defaultTemplate);
		setTemplateLocation(defaultTemplate);
	}
	public TableModel getModel() {
		return model;
	}

	public void setModel(TableModel model) {
		this.model = model;
		setRendered(false);
		
	}
	
	
	public List getData(){
		if(model instanceof JSONTableModel){
			return ((JSONTableModel)model).getData();
		}else{
			return new ArrayList();
		}
	}

	@Override
	public void changePage(int page) {
		setRendered(false);
		
	}

	@Override
	public int getCurrentPage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPages() {
		// TODO Auto-generated method stub
		return 1;
	}

}
