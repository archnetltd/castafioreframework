package org.castafiore.ui.ex.form.table;

import org.castafiore.ui.StatefullComponent;

public interface EditableCellRenderer extends CellRenderer{
	public StatefullComponent getInputAt(int row, int column,int page, EditableTableModel model, EXEditableTable table);
	
}
