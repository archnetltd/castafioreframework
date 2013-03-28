/*
 * 
 */
package org.castafiore.ui.ex.form;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EXDateTimePicker extends AbstractStatefullComponent {

	public EXDateTimePicker(String name) {
		super(name, "div");
		addChild(new EXDatePicker("date"));
		addChild(new EXTimePicker("time"));
	}

	@Override
	public String getRawValue() {

		return "";
	}

	@Override
	public void setRawValue(String rawValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getValue() {
		Date date = (Date)getDescendentOfType(EXDatePicker.class).getValue();
		String time = new SimpleDateFormat("hh:mm aa").format(getDescendentOfType(EXTimePicker.class).getValue());
		
		try{
		return new SimpleDateFormat("dd-MM-yyyy hh:mm aa").parse(new SimpleDateFormat("dd-MM-yyyy").format(date) + " " + time);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		//return super.getValue();
	}

	@Override
	public void setValue(Object value) {
		getDescendentOfType(EXDatePicker.class).setValue(value);
		getDescendentOfType(EXTimePicker.class).setValue(value);
	}
	
	

}
