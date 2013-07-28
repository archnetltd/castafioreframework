/*
 * Copyright (C) 2007-2010 Castafiore
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
 package org.castafiore.sms.ui;

import org.castafiore.sms.SMSService;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXTextArea;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.EventUtil;

public class EXCreateSMSInstance extends EXDynaformPanel {

	public EXCreateSMSInstance(String name) {
		super(name, "Create new sms instance");
		addField("Cron :", new EXInput("cron"));
		addField("Recipient :", new EXInput("recipient"));
		addField("Message :", new EXTextArea("message"));
		addField("Test sms number :", new EXInput("test"));
		
		addButton(new EXButton("doTest", "Test"));
		addButton(new EXButton("save", "Save"));
		addButton(new EXButton("cancel", "Cancel"));
		getDescendentByName("doTest").setAttribute("method", "sendTestSMS").setAttribute("ancestor", getClass().getName()).addEvent(EventUtil.GENERIC_FORM_METHOD_EVENT, Event.CLICK);
		setWidth(Dimension.parse("350px"));
		ComponentUtil.applyStyleOnAll(this, StatefullComponent.class, "width", "200px");
	}
	
	public void sendTestSMS(){
		try{
			String number = getField("test").getValue().toString();
			String message = getField("message").getValue().toString();
			SMSService service = SpringUtil.getBeanOfType(SMSService.class);
			service.sendMessage(message, number);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
			
	}

}
