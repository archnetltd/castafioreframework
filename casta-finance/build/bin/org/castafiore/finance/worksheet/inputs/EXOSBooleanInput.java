package org.castafiore.finance.worksheet.inputs;

import org.castafiore.finance.worksheet.OSWorksheetCell;
import org.castafiore.finance.worksheet.OSWorksheetTypes;
import org.castafiore.ui.ex.form.EXCheckBox;

public class EXOSBooleanInput extends EXCheckBox implements OSWorksheetCell{

	public EXOSBooleanInput(String name, boolean checked) {
		super(name, checked);
		// TODO Auto-generated constructor stub
	}

	@Override
	public OSWorksheetTypes getSupportedType() {
		return OSWorksheetTypes.BOOLEAN;
	}

}
