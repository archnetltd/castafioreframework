package org.castafiore.notifications;

import java.io.IOException;

import org.castafiore.security.User;
import org.castafiore.security.api.UserProfileService;
import org.castafiore.wfs.types.Message;
import org.marre.SmsSender;
import org.marre.sms.SmsException;

public class SMSNotificationAgent extends AbstractNotificationAgent{
	
	private UserProfileService userProfileService;

	@Override
	public String getName() {
		return "SMS";
	}

	@Override
	public void sendNotification(Message message, User from, User to) {
		SmsSender smsSender;
		try 
		{
			smsSender = SmsSender.getClickatellSender("kureem", "Nlmpx3", "3253111");
			String msg = message.getTitle();
			String sender = userProfileService.getUserProfileValue(from.getUsername(), "Business", "mobile");
			String reciever = userProfileService.getUserProfileValue(to.getUsername(), "Personal", "mobile");
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
