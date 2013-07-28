package org.castafiore.notifications;

import java.util.List;

import org.castafiore.security.User;

public interface NotificationManager {
	
	/**
	 * Returns all Notification agents configured
	 * @return
	 */
	public List<NotificationAgent> getAgents();
	
	
	/**
	 * Returns all notification agents configured by this user.
	 * @param username
	 * @return
	 */
	public List<NotificationAgent> getAgents(User user);

}
