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

import org.castafiore.ui.Container;
import org.castafiore.ui.EXContainer;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.table.CellRenderer;
import org.castafiore.ui.table.RowDecorator;
import org.castafiore.ui.table.Table;
import org.castafiore.ui.table.TableColumnModel;
import org.castafiore.ui.table.TableModel;
import org.castafiore.utils.ResourceUtil;

/**
 * 
 * Default implementation of {@link Table}<br>
 * Content of table is delegated to {@link TableModel}<br>
 * Layout of cells is delegated to {@link CellRenderer}<br>
 * Layout of columns is delegated to {@link TableColumnModel}<br>
 * Can interact with rows using {@link RowDecorator}<br>
 * Note: This implementation is memory extensive. For less memory extensive
 * implementations, consider using a javascript library to render a table
 * 
 * @author Kureem Rossaye<br>
 *         kureem@gmail.com Oct 22, 2008
 */
public class EXTable extends EXContainer implements Table {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected TableModel tableModel_;

	protected CellRenderer celleRendere_ = new DefaultCellRenderer();

	protected TableColumnModel columnModel_ = new DefaultTableColumnModel();

	protected RowDecorator rowDecorator_ = new DefaultRowDecorator();

	protected int currentPage = 0;

	/**
	 * Creates a table with the specified name and {@link TableModel}
	 * 
	 * @param name
	 *            The name of the table
	 * @param model
	 *            The {@link TableModel}
	 */
	public EXTable(String name, TableModel model) {
		super(name, "table");
		this.tableModel_ = model;
		addClass("EXGrid");
		this.refresh();

	}

	/**
	 * Creates a table with the specified name, {@link TableModel} and
	 * {@link CellRenderer}
	 * 
	 * @param name
	 * @param model
	 * @param renderer
	 */
	public EXTable(String name, TableModel model, CellRenderer renderer) {
		super(name, "table");
		this.tableModel_ = model;
		this.celleRendere_ = renderer;

		addClass("EXGrid");
		this.refresh();

	}

	public void onReady(ClientProxy proxy) {
		proxy.getCSS(ResourceUtil.getDownloadURL("classpath",
				"org/castafiore/resource/table/table.css"));
	}

	/**
	 * Returns the number of pages expected to be created in the table.<br>
	 * If the table is not expected to be pagineable, return 1
	 * 
	 * @return
	 * 
	 */
	public int getPages() {
		int rows = this.tableModel_.getRowCount();

		int rPerPage = this.tableModel_.getRowsPerPage();

		int remainder = rows % rPerPage;

		int multiple = Math.round(rows / rPerPage);

		int pages = multiple;

		if (remainder != 0) {
			pages = pages + 1;
		}

		return pages;

	}

	private void createTable() {

		this.getChildren().clear();

		if (tableModel_ == null) {
			return;
		}

		Container thead = new EXContainer("thead", "thead");

		addChild(thead);

		Container header = new EXContainer("header", "tr");
		thead.addChild(header);

		for (int i = 0; i < this.tableModel_.getColumnCount(); i++) {

			Container column = this.columnModel_.getColumnAt(i, this,
					this.tableModel_);

			header.addChild(column);
		}

		int rows = tableModel_.getRowsPerPage();
		if (rows > tableModel_.getRowCount()) {
			rows = tableModel_.getRowCount();
		}
		Container tbody = new EXContainer("tbody", "tbody");
		addChild(tbody);
		for (int i = 0; i < rows; i++) {
			Container row = new EXContainer("" + i, "tr");

			tbody.addChild(row);
			for (int j = 0; j < this.tableModel_.getColumnCount(); j++) {
				Container td = new EXContainer("", "td");
				row.addChild(td);
			}

		}
		this.setRendered(false);
	}

	/**
	 * Sets the {@link TableModel} of the table<br>
	 * The {@link TableModel} is responsible for holding the content of the
	 * table
	 * 
	 * @param model
	 */
	public void setModel(TableModel model) {
		this.tableModel_ = model;
		this.refresh();
	}

	/**
	 * 
	 * @return The table model of the table
	 */
	public TableModel getModel() {
		return this.tableModel_;
	}

	/**
	 * The current page with starting index = 0
	 * 
	 * @return The current page
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * sets the current page. Same as changePage
	 * 
	 * @param currentPage
	 */
	public void setCurrentPage(int currentPage) {
		changePage(currentPage);
	}

	/**
	 * Change page to the specified value
	 * 
	 * @param page
	 *            The page to change
	 */
	public void changePage(int page) {
		if (tableModel_ == null) {
			return;
		}
		this.currentPage = page;
		Container tbody = getChild("tbody");
		int rowsPerpage = tableModel_.getRowsPerPage();

		int rowCount = tableModel_.getRowCount();

		int maxRow = (page + 1) * rowsPerpage;

		int rowsToShow = rowsPerpage;

		if (maxRow > rowCount)
			rowsToShow = rowCount - ((page) * rowsPerpage);

		for (int row = 0; row < rowsToShow; row++) {
			Container tr = tbody.getChildByIndex(row);

			for (int col = 0; col < tableModel_.getColumnCount(); col++) {
				Container td = null;
				if (tr.getChildren().size() <= col) {
					td = new EXContainer("", "td");

					tr.addChild(td);
				} else {
					td = tr.getChildByIndex(col);
				}

				Container component = null;
				if (td.hasChildren()) {
					component = td.getChildByIndex(0);
					celleRendere_.onChangePage(component, row, col, page,
							tableModel_, this);
				} else {
					component = celleRendere_.getComponentAt(row, col, page,
							tableModel_, this);
					td.addChild(component);
					td.setRendered(false);
				}

			}

			if (rowDecorator_ != null) {
				rowDecorator_.decorateRow(row, (EXContainer) tr, this,
						tableModel_);
			}
		}

		if (rowsToShow < rowsPerpage) {
			for (int i = rowsToShow; i < rowsPerpage; i++) {
				if (i < tbody.getChildren().size()) {
					Container row = tbody.getChildByIndex(i);

					row.getChildren().clear();
					row.setRendered(false);
				}
			}
		}
	}

	/**
	 * 
	 * @return The {@link CellRenderer} of the table
	 */
	public CellRenderer getCellRenderer() {
		return celleRendere_;
	}

	/**
	 * Sets the {@link CellRenderer} of the table
	 * 
	 * @param celleRendere_
	 */
	public void setCellRenderer(CellRenderer celleRendere_) {
		this.celleRendere_ = celleRendere_;
		refresh();
	}

	/**
	 * @return the {@link TableColumnModel} of the table
	 */
	public TableColumnModel getColumnModel() {
		return columnModel_;
	}

	/**
	 * Sets the {@link TableColumnModel} of the table
	 * 
	 * @param columnModel_
	 *            The {@link TableColumnModel}
	 */
	public void setColumnModel(TableColumnModel columnModel_) {
		this.columnModel_ = columnModel_;
		this.refresh();
	}

	/**
	 * Returns the {@link RowDecorator} of the table
	 * 
	 * @return
	 */
	public RowDecorator getRowDecorator() {
		return rowDecorator_;

	}

	/**
	 * The {@link RowDecorator} of the table
	 * 
	 * @param rowDecorator_
	 */
	public void setRowDecorator(RowDecorator rowDecorator_) {
		this.rowDecorator_ = rowDecorator_;
		this.refresh();
	}

	/**
	 * Recreates the table completely
	 */
	@Override
	public void refresh() {
		this.createTable();

		this.changePage(0);
	}

	
}
