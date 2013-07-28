package org.castafiore.finance.worksheet;

import org.castafiore.ui.Scrollable;
import org.castafiore.ui.ex.EXContainer;

public class EXOSWorksheetScrollContainer extends EXContainer implements Scrollable {

	public EXOSWorksheetScrollContainer(String name, EXOSWorksheet worksheet) {
		super(name, "div");
		setStyle("width", "550px");
		setStyle("height", "400px");
		setStyle("overflow", "scroll");
		addChild(worksheet);
	}

	@Override
	public void addPage() {
		getDescendentOfType(EXOSWorksheet.class).addPage();
		
	}

	@Override
	public int getDirections() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isScrollable() {
		return getDescendentOfType(EXOSWorksheet.class).scrollable();
	}

}
