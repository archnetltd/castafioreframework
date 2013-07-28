package org.castafiore.notifications;

import org.castafiore.security.User;
import org.castafiore.wfs.types.Message;

/**
 * Mail agent
 * Messaging agent
 * SMSAgent
 * @author kureem
 *
 */
public interface NotificationAgent {
	
	/**
	 * Returns a unique name for this agent
	 * @return
	 */
	public String getName();
	
	
	/**
	 * Registers a user saying that this user can be notified using this agent
	 * @param username
	 */
	public void registerUser(User user);
	
	
	/**
	 * Checks if this user can be notified using this agent
	 * @param username
	 * @return
	 */
	public boolean isUserRegistered(User user);
	
	
	/**
	 * Sends the notification
	 * @param message
	 */
	public void sendNotification(Message message, User from, User to);

}
