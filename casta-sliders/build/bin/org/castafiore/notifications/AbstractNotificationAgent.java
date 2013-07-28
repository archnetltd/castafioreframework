package org.castafiore.notifications;

import org.castafiore.security.User;
import org.castafiore.security.api.UserProfileService;
import org.castafiore.wfs.types.Message;

public abstract class AbstractNotificationAgent implements NotificationAgent{

	private UserProfileService userProfileService;
	
	public UserProfileService getUserProfileService() {
		return userProfileService;
	}

	public void setUserProfileService(UserProfileService userProfileService) {
		this.userProfileService = userProfileService;
	}

	

	public abstract String getName();

	public boolean isUserRegistered(User user) {
		String value = userProfileService.getUserProfileValue(user.getUsername(), "Notifications", getName());
		if(value != null && value.equalsIgnoreCase("true")){
			return true;
		}else{
			return false;
		}
	}

	public void registerUser(User user) {
		userProfileService.saveUserProfileValue(user.getUsername(), "Notifications", "Email", "true");
		
	}
	
	
	public void unRegisterUser(User user){
		userProfileService.saveUserProfileValue(user.getUsername(), "Notifications", "Email", "false");
	}

	public abstract void sendNotification(Message message, User from, User to) ;
}
