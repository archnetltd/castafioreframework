/*
 * 
 */
package org.castafiore.ui.ex.table;

public interface EditableTableModel extends TableModel{
	
	public void setValueAt(int col, int row, int page, Object value);

}
