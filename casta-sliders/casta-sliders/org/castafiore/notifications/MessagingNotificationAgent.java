package org.castafiore.notifications;

import org.castafiore.security.User;
import org.castafiore.wfs.types.Message;

public class MessagingNotificationAgent extends AbstractNotificationAgent{

	public String getName() {
		return "Messaging";
	}

	public void sendNotification(Message message, User from, User to) {
		//todo
		
	}

}
