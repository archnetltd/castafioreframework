package org.castafiore.notifications;

import java.io.IOException;

import org.marre.SmsSender;
import org.marre.sms.SmsException;

public class SMSTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SmsSender smsSender;
		try 
		{
			smsSender = SmsSender.getClickatellSender("kureem", "Nlmpx3", "3253111");
			String msg = "Hello world";
			String reciever = "2307159028";
			String sender = "2307159028";
			smsSender.connect();
			smsSender.sendTextSms(msg, reciever, sender);
			smsSender.disconnect();
		} catch (SmsException e) {
			e.printStackTrace();
		}catch(IOException oe){
			oe.printStackTrace();
		}

	}

}
