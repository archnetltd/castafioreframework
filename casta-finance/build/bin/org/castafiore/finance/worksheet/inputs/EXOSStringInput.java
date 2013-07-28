package org.castafiore.finance.worksheet.inputs;

import org.castafiore.finance.worksheet.OSWorksheetCell;
import org.castafiore.finance.worksheet.OSWorksheetTypes;
import org.castafiore.ui.ex.form.EXInput;

public class EXOSStringInput extends EXInput implements OSWorksheetCell{

	public EXOSStringInput(String name, String value) {
		super(name, value);
	}

	@Override
	public OSWorksheetTypes getSupportedType() {
		return OSWorksheetTypes.STRING;
	}

}
