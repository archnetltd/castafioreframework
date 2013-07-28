package org.castafiore.finance.worksheet;

import java.io.Serializable;

public interface OSWorksheetCellRenderer extends Serializable {
	
	public OSWorksheetCell getCell(String title,int colIndex, int rowIndex, Object value);

}
