package org.castafiore.finance.worksheet.inputs;

import org.castafiore.finance.worksheet.OSWorksheetTypes;

public class EXOSCurrencyInput extends EXOSStringInput{

	public EXOSCurrencyInput(String name, String value) {
		super(name, value);
		
	}

	@Override
	public OSWorksheetTypes getSupportedType() {
		return OSWorksheetTypes.CURRENCY;
	}
}
