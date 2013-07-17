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

package org.castafiore.ui.ex.table;

import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.utils.ResourceUtil;
/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 22, 2008
 */
public class EXTable extends EXContainer implements Table
{

	protected TableModel tableModel_;

	protected CellRenderer celleRendere_ = new DefaultCellRenderer();
	
	protected TableColumnModel columnModel_ = new DefaultTableColumnModel();
	
	protected RowDecorator rowDecorator_ = new DefaultRowDecorator();
	
	protected int currentPage = 0;
	
	

	public EXTable(String name, TableModel model)
	{
		super(name, "table");
		this.tableModel_ = model;

		setAttribute("class", "EXGrid");
		addStyleSheet(ResourceUtil.getDownloadURL("classpath", "org/castafiore/resource/table/table.css"));
		this.refresh();

	}

	
	public EXTable(String name, TableModel model, CellRenderer renderer)
	{
		super(name, "table");
		this.tableModel_ = model;
		this.celleRendere_ = renderer;

		setAttribute("class", "EXGrid");
		addStyleSheet(ResourceUtil.getDownloadURL("classpath", "org/castafiore/resource/table/table.css"));
		this.refresh();

	}
	public int getPages()
	{
		int rows = this.tableModel_.getRowCount();

		int rPerPage = this.tableModel_.getRowsPerPage();

		int remainder = rows % rPerPage;

		int multiple = Math.round(rows / rPerPage);

		int pages = multiple;
 
		if (remainder != 0)
		{
			pages = pages + 1;
		}

		return pages;

	}

	private void createTable()
	{

		this.getChildren().clear();
		
		if(tableModel_ == null){
			return;
		}
 
		Container thead = new EXContainer("thead", "thead");

		addChild(thead);

		Container header = new EXContainer("header", "tr");
		thead.addChild(header);

		for (int i = 0; i < this.tableModel_.getColumnCount(); i++)
		{

			Container column = this.columnModel_.getColumnAt(i, this, this.tableModel_);
			
			header.addChild(column);
		}

		int rows = tableModel_.getRowsPerPage();
		if (rows > tableModel_.getRowCount())
		{
			rows = tableModel_.getRowCount();
		}
		Container tbody = new EXContainer("tbody", "tbody");
		addChild(tbody);
		for (int i = 0; i < rows; i++)
		{
			Container row = new EXContainer("" + i, "tr");
			
			tbody.addChild(row);
			for (int j = 0; j < this.tableModel_.getColumnCount(); j++)
			{
				Container td = new EXContainer("", "td");
				row.addChild(td);
			}
			
		}
		this.setRendered(false);
	}
	

	public void setModel(TableModel model)
	{
		this.tableModel_ = model;
		this.refresh();
	}

	public TableModel getModel()
	{
		return this.tableModel_;
	}

	public int getCurrentPage() {
		return currentPage;
	}


	public void setCurrentPage(int currentPage) {
		changePage(currentPage);
	}


	public void changePage(int page)
	{
		if(tableModel_ == null){
			return;
		}
		this.currentPage = page;
		Container tbody =  getChild("tbody");
		int rowsPerpage = tableModel_.getRowsPerPage();
		
		int rowCount = tableModel_.getRowCount();
		
		int maxRow = (page+1)*rowsPerpage;
		
		int rowsToShow = rowsPerpage;
		
		if(maxRow > rowCount)
			rowsToShow = rowCount - ((page)*rowsPerpage);
		
		for (int row = 0; row < rowsToShow; row++)
		{
			Container tr =  tbody.getChildByIndex(row);

			for (int col = 0; col < tableModel_.getColumnCount(); col++)
			{
				Container td = null;
				if(tr.getChildren().size() <= col)
				{
					td = new EXContainer("", "td");
					
					tr.addChild(td);
				}
				else
				{
					td = tr.getChildByIndex(col);
				}


				Container component = null;
				if (td.hasChildren())
				{
					component =  td.getChildByIndex(0);
					celleRendere_.onChangePage(component, row, col, page, tableModel_, this);
				}
				else
				{
					component = celleRendere_.getComponentAt(row, col, page, tableModel_, this);
					td.addChild(component);
					td.setRendered(false);
				}

			}
			
			if(rowDecorator_ != null){
				rowDecorator_.decorateRow(row, (EXContainer)tr, this, tableModel_);
			}
		}
		
		if(rowsToShow < rowsPerpage)
		{
			for(int i = rowsToShow; i < rowsPerpage; i ++)
			{
				if(i < tbody.getChildren().size())
				{
					Container row = tbody.getChildByIndex(i);
					
					row.getChildren().clear();
					row.setRendered(false);
				}
			}
		}
	}

	
	
	
	
	public CellRenderer getCellRenderer()
	{
		return celleRendere_;
	}

	public void setCellRenderer(CellRenderer celleRendere_)
	{
		this.celleRendere_ = celleRendere_;
		refresh();
	}

	
	
	
	public TableColumnModel getColumnModel() {
		return columnModel_;
	}


	public void setColumnModel(TableColumnModel columnModel_) {
		this.columnModel_ = columnModel_;
		this.refresh();
	}


	public RowDecorator getRowDecorator() {
		return rowDecorator_;
		
	}


	public void setRowDecorator(RowDecorator rowDecorator_) {
		this.rowDecorator_ = rowDecorator_;
		this.refresh();
	}


	@Override
	public void refresh()
	{
		this.createTable();

		this.changePage(0);
	}
	
	public static class MakeSortable implements Event{

		public void ClientAction(ClientProxy container) {
			container.addMethod("tablesorter");
			
		}

		public boolean ServerAction(Container container, Map<String, String> request) throws UIException {
			// TODO Auto-generated method stub
			return false;
		}

		public void Success(ClientProxy container, Map<String, String> request) throws UIException {
			// TODO Auto-generated method stub
			
		}
		
	}

}
