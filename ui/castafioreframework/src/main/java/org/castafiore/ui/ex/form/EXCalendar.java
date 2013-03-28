/*
 * Copyright (C) 2007-2008 Castafiore
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */

package org.castafiore.ui.ex.form;

import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.js.JMap;
import org.castafiore.ui.js.Var;
/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 22, 2008
 */
public class EXCalendar extends EXContainer implements Event {
	
	private boolean enableOnChangeMonth = false;
	
	private boolean enableOnSelectDate = true;
	
	private String format = "dd mm yy";
	
	public boolean isEnableOnChangeMonth() {
		return enableOnChangeMonth;
	}

	public void setEnableOnChangeMonth(boolean enableOnChangeMonth) {
		this.enableOnChangeMonth = enableOnChangeMonth;
	}

	public boolean isEnableOnSelectDate() {
		return enableOnSelectDate;
	}

	public void setEnableOnSelectDate(boolean enableOnSelectDate) {
		this.enableOnSelectDate = enableOnSelectDate;
	}

	
	
	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public EXCalendar(String name) {
		super(name, "div");
		addEvent(this, Event.READY);
	}

	public void onChangeMonth(String year, String month){
		//System.out.println(year);
		//System.out.println(month);
	}
	
	
	public void onSelectDate(String dateText){
		//System.out.println(dateText);
	}


	public void ClientAction(ClientProxy container) {
		JMap options = new JMap();
		
		JMap onChangeMonthOptions = new JMap();
		onChangeMonthOptions.put("year", new Var("year")).put("month", new Var("month"));
		ClientProxy onChangeMonthBody = container.clone().makeServerRequest(onChangeMonthOptions, this);
		
		JMap onSelectDateOptions = new JMap().put("dateText", new Var("dateText"));
		ClientProxy onSelectDateFunction = container.clone().mask().makeServerRequest(onSelectDateOptions,this);
		
		
		
		if(this.enableOnChangeMonth)
			options.put("onChangeMonthYear", onChangeMonthBody,"year", "month", "inst");
		
		if(this.enableOnSelectDate)
			options.put("onSelect", onSelectDateFunction, "dateText");
		
		options.put("dateFormat", format);
		container.addMethod("datepicker", options);
	}


	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		if(request.containsKey("year")){
			onChangeMonth(request.get("year"), request.get("month"));
		}else{
			onSelectDate(request.get("dateText"));
		}
		return true;
	}


	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}
	

	
	
	

}
