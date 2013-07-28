package org.castafiore.finance.worksheet;

public interface OSWorksheetDataModel {
	
	public Object getValueAt(String title,int colIndex, int rowIndex, OSWorksheetTypes defaultType);
	
	public int size();
	
	public boolean scrollable();

}
