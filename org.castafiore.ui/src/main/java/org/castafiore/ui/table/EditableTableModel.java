/*
 * 
 */
package org.castafiore.ui.table;

/**
 * Extends the default {@link TableModel} to add the ability to edit a value in
 * the model and reflect it on the table
 * 
 * @author arossaye
 * 
 */
public interface EditableTableModel extends TableModel {

	public void setValueAt(int col, int row, int page, Object value);

}
