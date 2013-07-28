package org.castafiore.sms;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import org.smslib.OutboundMessage;
import org.smslib.Service;
import org.smslib.http.ClickatellHTTPGateway;
import org.smslib.modem.SerialModemGateway;
import org.smslib.smsserver.SMSServer;

public class SMSDevice {
	
	private String myNumber = "7159028";
	
	private String toNumber = "7159028";
	
	public void sendSMS( Reader reader, Writer writer)throws Exception{
		
		SMSRequest request = new SMSRequest(myNumber, toNumber, reader );
		
		request.setFrom(myNumber);
		
		request.setTo(toNumber);
		
		
		
		
		
		SMSUtil.getContainer().getSMSRequestDispatcher().doDispatch(request, writer);
		writer.flush();
		writer.close();
	}
	
	private void sendSMSM(String number, String message)throws Exception{
		OutboundMessage msg;
		Service service =null;
	
		
		if(service == null ){
			
			service = new Service();
			ClickatellHTTPGateway gateway = new ClickatellHTTPGateway("ARB423", "3227703", "kureem", "marijuana01");
			//SerialModemGateway gateway = new SerialModemGateway("Kureem", "COM6", 9600, "Nokia", "2630");
			gateway.setInbound(true);
			gateway.setOutbound(true);
			service.addGateway(gateway);
			service.startService();
		}
		// Send a message synchronously.
		msg = new OutboundMessage(number, message);
		service.sendMessage(msg);
	}
	
	public static void main(String[] args)throws Exception {
		String message = "search:socks";
		Reader reader = new InputStreamReader(System.in);
		Writer writer = new OutputStreamWriter(System.out);
		//new SMSDevice().sendSMS(reader, writer);
		new SMSDevice().sendSMSM("2307159028", "mmomma");
	
	}
}
