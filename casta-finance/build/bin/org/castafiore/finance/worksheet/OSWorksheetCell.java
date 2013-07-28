package org.castafiore.finance.worksheet;

import org.castafiore.ui.StatefullComponent;

public interface OSWorksheetCell extends StatefullComponent{
	
	public OSWorksheetTypes getSupportedType();
	
	public void setValue(Object value);
	
	public Object getValue();

}
