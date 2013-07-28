package org.castafiore.swing.authentication;

public class AuthenticationService {
	
	public String authenticate(String username, String password){
		System.out.println("sending username:" + username + " password: " + password  + " to the server for authentication");
		return "mytoken";
	}

}
 