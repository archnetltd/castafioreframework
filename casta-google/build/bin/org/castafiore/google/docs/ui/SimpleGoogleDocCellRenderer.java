package org.castafiore.google.docs.ui;

import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.table.CellRenderer;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableModel;

import com.google.gdata.data.docs.DocumentListEntry;

public class SimpleGoogleDocCellRenderer implements CellRenderer{

	@Override
	public Container getComponentAt(int row, int column, int page,
			TableModel model, EXTable table) {
		DocumentListEntry entry = (DocumentListEntry)model.getValueAt(column, row, page);
		String title = entry.getTitle().getPlainText();
		String link = entry.getDocumentLink().getHref();
		Container a = new EXContainer("", "a").setText(title).setAttribute("href", link);
		return a;
	}

	@Override
	public void onChangePage(Container component, int row, int column,
			int page, TableModel model, EXTable table) {
		// TODO Auto-generated method stub
		DocumentListEntry entry = (DocumentListEntry)model.getValueAt(column, row, page);
		component.setText(entry.getTitle().getPlainText());
		component.setAttribute("href", entry.getDocumentLink().getHref());
	}

}
