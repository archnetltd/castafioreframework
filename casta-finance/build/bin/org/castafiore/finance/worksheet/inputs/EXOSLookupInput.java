package org.castafiore.finance.worksheet.inputs;

import java.util.List;

import org.castafiore.finance.worksheet.OSWorksheetCell;
import org.castafiore.finance.worksheet.OSWorksheetTypes;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;

public class EXOSLookupInput extends EXSelect implements OSWorksheetCell{

	public EXOSLookupInput(String name, List<Object> lookupData) {
		super(name, new DefaultDataModel<Object>(lookupData));
		setStyle("padding", "0");
		setStyle("margin", "-3px");
	}

	@Override
	public OSWorksheetTypes getSupportedType() {
		return OSWorksheetTypes.LOOKUP;
	}

}
