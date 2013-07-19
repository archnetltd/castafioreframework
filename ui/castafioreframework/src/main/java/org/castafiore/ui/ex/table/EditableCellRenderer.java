/*
 * 
 */
package org.castafiore.ui.ex.table;

import org.castafiore.ui.StatefullComponent;
/**
 * Extends {@link CellRenderer} to add editable ability to a cell.
 * @author arossaye
 *
 */
public interface EditableCellRenderer extends CellRenderer{
	
	/**
	 * Returns a form component for the specified co-ordinate of the table
	 * @param row The row of the table
	 * @param column The column of the table
	 * @param page The page of the table
	 * @param model The model applied on the table
	 * @param table The table itself
	 * @return A form component to be used to edit the current content of the cell
	 */
	public StatefullComponent getInputAt(int row, int column,int page, EditableTableModel model, EXEditableTable table);
	
}
