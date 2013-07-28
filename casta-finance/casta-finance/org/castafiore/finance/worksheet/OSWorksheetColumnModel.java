package org.castafiore.finance.worksheet;

import java.io.Serializable;

public interface OSWorksheetColumnModel extends Serializable{
	
	public int getSize();
	
	public String getTitle(int index);
	
	public WorksheetCellSpan getSpan(int index);
	
	public OSWorksheetTypes getDefaultType(int index);

}
