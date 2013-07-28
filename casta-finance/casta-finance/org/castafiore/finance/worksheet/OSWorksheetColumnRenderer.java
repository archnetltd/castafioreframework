package org.castafiore.finance.worksheet;

import java.io.Serializable;

import org.castafiore.ui.Container;

public interface OSWorksheetColumnRenderer extends Serializable{
	
	public Container getColumnCell(String label, int index,  boolean onPageChange);

}
