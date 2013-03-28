/*
 * 
 */
package org.castafiore.ui.ex.form.table;

import org.castafiore.ui.Container;
import org.castafiore.ui.Scrollable;
import org.castafiore.ui.ex.EXContainer;

public class EXScrollableTable extends EXContainer implements Scrollable{
	
	private int page = 0;

	public EXScrollableTable(String name,EXTable table) {
		super(name, "div");
		addChild(table);
		setStyle("overflow", "scroll");
	}

	@Override
	public void addPage() {
		
		try{
		EXTable table = getDescendentOfType(EXTable.class);
		TableModel tableModel_ = table.getModel();
		RowDecorator rowDecorator_ = table.getRowDecorator();
		Container tbody = table.getDescendentByName("tbody");
		int rowsPerpage = tableModel_.getRowsPerPage();
		int rowCount = tableModel_.getRowCount();
		
		page = page+1;
		int maxRow = (page)*rowsPerpage;
		int rowsToShow = rowsPerpage;
		
		if(maxRow > rowCount)
			rowsToShow = rowCount - ((page-1)*rowsPerpage);
		
		for (int i = 0; i < rowsToShow; i++)
		{
			EXContainer row = new EXContainer("" + i, "tr");
			
			tbody.addChild(row);
			for (int j = 0; j < tableModel_.getColumnCount(); j++)
			{
				EXContainer td = new EXContainer("", "td");
				row.addChild(td);
				Container c = table.getCellRenderer().getComponentAt(i, j, page, tableModel_, table);
				td.addChild(c);
			}
			if(rowDecorator_ != null){
				rowDecorator_.decorateRow(i, row, table, tableModel_);
			}
		}
		}catch(IndexOutOfBoundsException ai){
			
		}
	
		
	}

	@Override
	public int getDirections() {
		return SCROLL_VERTICAL;
	}

	@Override
	public boolean isScrollable() {
		// TODO Auto-generated method stub
		return true;
	}

}
