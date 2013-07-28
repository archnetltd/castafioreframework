package org.castafiore.security.ui;

import java.io.Serializable;

import org.castafiore.security.User;
import org.castafiore.ui.Application;

public interface OnRegisterUserHandler extends Serializable{
	
	
	public void onRegisterUser(Application root, User user);

}
