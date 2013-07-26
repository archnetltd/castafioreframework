package org.castafiore.shoppingmall.employee.ui.model;

import java.util.Calendar;

public class PointerEntry {

	private Calendar start;

	private Calendar end;

	public PointerEntry(Calendar start, Calendar end) {
		super();
		this.start = start;
		this.end = end;
	}

	public Calendar getStart() {
		return start;
	}

	public Calendar getEnd() {
		return end;
	}

}
