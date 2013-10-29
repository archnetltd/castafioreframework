package org.castafiore.sample.integrations.zinoui;

import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.js.JArray;
import org.castafiore.ui.js.JMap;

public class ZCalendar extends EXInput {

	
	private JMap options =new JMap();

	public ZCalendar(String name) {
		super(name);
	}

	

	public void setYear(Integer year) {
		options.put("year", year);
	}


	public void setMonth(Integer month) {
		options.put("month", month);
	}

	

	public void setDayNames(String[] dayNames) {
		JArray arr = new JArray();
		for(String d : dayNames){
			arr.add(d);
		}
		
		options.put("dayName", arr);
	}

	

	public void setDayNamesFull(String[] dayNamesFull) {
		JArray arr = new JArray();
		for(String d : dayNamesFull){
			arr.add(d);
		}
		
		options.put("dayNamesFull", arr);
	}

	

	public void setMonthNamesFull(String[] monthNamesFull) {
		JArray arr = new JArray();
		for(String d : monthNamesFull){
			arr.add(d);
		}
		
		options.put("monthNamesFull", arr);
	}

	

	public void setStartDay(Integer startDay) {
		options.put("startDay", startDay);
	}

	

	public void setWeekNumbers(Boolean weekNumbers) {
		options.put("weekNumbers", weekNumbers);
	}

	

	public void setSelectOtherMonths(Boolean selectOtherMonths) {
		options.put("selectOtherMonths", selectOtherMonths);
	}



	public void setShowOtherMonths(Boolean showOtherMonths) {
		options.put("showOtherMonths", showOtherMonths);
	}



	public void setShowNavigation(Boolean showNavigation) {
		options.put("showNavigation", showNavigation);
	}

	

	public void setMonths(Integer months) {
		options.put("months", months);
	}

	

	public void setInline(Boolean inline) {
		options.put("inline", inline);
	}

	

	public void setDisablePast(Boolean disablePast) {
		options.put("disablePast", disablePast);
	}

	

	public void setDateFormat(String dateFormat) {
		options.put("dateFormat", dateFormat);
	}

	

	public void setPosition(String position) {
		options.put("position", position);
	}

	

	public void onReady(ClientProxy proxy){
		proxy.getCSS("http://zinoui.com/1.3/themes/silver/zino.core.css").getCSS("http://zinoui.com/1.3/themes/silver/zino.calendar.css");
		proxy.getScript("http://zinoui.com/1.3/compiled/zino.calendar.min.js", proxy.clone().addMethod("zinoCalendar",options));
	}
	

}
