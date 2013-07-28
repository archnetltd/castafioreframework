package org.castafiore.finance.worksheet.inputs;

import org.castafiore.finance.worksheet.OSWorksheetCell;
import org.castafiore.finance.worksheet.OSWorksheetTypes;
import org.castafiore.ui.ex.form.EXLabel;

public class EXOSReadOnlyInput extends EXLabel implements OSWorksheetCell{

	public EXOSReadOnlyInput(String name, String value) {
		super(name, value);
	}

	@Override
	public OSWorksheetTypes getSupportedType() {
		return OSWorksheetTypes.TEXT;
	}

	
}
