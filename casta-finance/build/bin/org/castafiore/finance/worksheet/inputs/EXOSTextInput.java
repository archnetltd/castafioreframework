package org.castafiore.finance.worksheet.inputs;

import org.castafiore.finance.worksheet.OSWorksheetTypes;

public class EXOSTextInput extends EXOSStringInput{

	public EXOSTextInput(String name, String value) {
		super(name, value);
		
	}
	
	@Override
	public OSWorksheetTypes getSupportedType() {
		return OSWorksheetTypes.TEXT;
	}

}
