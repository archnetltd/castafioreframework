/*
 * 
 */
package org.castafiore.ui.ex.table;

import org.castafiore.ui.Container;
import org.castafiore.ui.EXContainer;
import org.castafiore.ui.Scrollable;
import org.castafiore.ui.table.RowDecorator;
import org.castafiore.ui.table.TableModel;

/**
 * Wrapper around a {@link EXTable} to make it Sortable
 * 
 * @author arossaye
 * 
 */
public class EXScrollableTable extends EXContainer implements Scrollable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int page = 0;

	/**
	 * Constructs a scrollable table with the specified name and {@link EXTable}
	 * 
	 * @param name
	 * @param table
	 */
	public EXScrollableTable(String name, EXTable table) {
		super(name, "div");
		addChild(table);
		setStyle("overflow", "scroll");
	}

	/**
	 * Adds a page to the table<br>
	 * Event executed when scrolling
	 */
	public void addPage() {

		try {
			EXTable table = getDescendentOfType(EXTable.class);
			TableModel tableModel_ = table.getModel();
			RowDecorator rowDecorator_ = table.getRowDecorator();
			Container tbody = table.getDescendentByName("tbody");
			int rowsPerpage = tableModel_.getRowsPerPage();
			int rowCount = tableModel_.getRowCount();

			page = page + 1;
			int maxRow = (page) * rowsPerpage;
			int rowsToShow = rowsPerpage;

			if (maxRow > rowCount)
				rowsToShow = rowCount - ((page - 1) * rowsPerpage);

			for (int i = 0; i < rowsToShow; i++) {
				EXContainer row = new EXContainer("" + i, "tr");

				tbody.addChild(row);
				for (int j = 0; j < tableModel_.getColumnCount(); j++) {
					EXContainer td = new EXContainer("", "td");
					row.addChild(td);
					Container c = table.getCellRenderer().getComponentAt(i, j,
							page, tableModel_, table);
					td.addChild(c);
				}
				if (rowDecorator_ != null) {
					rowDecorator_.decorateRow(i, row, table, tableModel_);
				}
			}
		} catch (IndexOutOfBoundsException ai) {

		}

	}

	public int getDirections() {
		return SCROLL_VERTICAL;
	}

	public boolean isScrollable() {
		return true;
	}

}
