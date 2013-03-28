/*
 * 
 */
package org.castafiore.ui.ex.form;

import java.util.Date;

import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.ex.EXContainer;

public class EXTimeRangerPicker extends AbstractStatefullComponent {

	public EXTimeRangerPicker(String name) {
		super(name, "div");
		addChild(new EXDateTimePicker("from")).addChild(new EXContainer("lab", "label").setText(" to ")).addChild(new EXDateTimePicker("to"));
	}

	@Override
	public String getRawValue() {
		return "";
	}

	@Override
	public void setRawValue(String rawValue) {

	}

	@Override
	public Object getValue() {
		Date from = (Date)((StatefullComponent)getDescendentByName("from")).getValue();
		Date to = (Date)((StatefullComponent)getDescendentByName("to")).getValue();
		return new Date[]{from,to};
	}

	@Override
	public void setValue(Object value) {
		Date[] dates = (Date[])value;
		((StatefullComponent)getDescendentByName("from")).setValue(dates[0]);
		((StatefullComponent)getDescendentByName("to")).setValue(dates[1]);
	}
	
	

}
