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
 package org.castafiore.sms;

import java.util.List;

import org.smslib.OutboundMessage;
import org.smslib.Service;
import org.smslib.modem.SerialModemGateway;

public class SMSServiceImpl implements SMSService{

	
	private Service service;

	@Override
	public List<String> getUnReadMessages() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public synchronized void sendMessage(String message, String number) throws Exception {
		//Service service;
		OutboundMessage msg;
		if(service == null ){
		
			service = new Service();
			SerialModemGateway gateway = new SerialModemGateway("Kureem", "COM9", 9600, "Nokia", "2630");
			gateway.setInbound(true);
			gateway.setOutbound(true);
			gateway.setSmscNumber("7159028");
			
			gateway.setSimPin("0000");
			service.addGateway(gateway);
			service.startService();
		}
		// Send a message synchronously.
		msg = new OutboundMessage(number, message);
		service.sendMessage(msg);
		//service.stopService();
		
	}

}
