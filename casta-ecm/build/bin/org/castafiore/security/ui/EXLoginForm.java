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
 package org.castafiore.security.ui;



import java.util.ArrayList;
import java.util.List;

import org.castafiore.security.User;
import org.castafiore.security.api.SecurityService;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Application;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.UIException;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXPassword;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.EventUtil;
import org.castafiore.utils.StringUtil;

public class EXLoginForm extends EXDynaformPanel {
	
	private List<OnLoginHandler> onLoginHandlers = new ArrayList(1);
	
	private List<OnRegisterUserHandler> onRegisterUserHandlers = new ArrayList<OnRegisterUserHandler>();
	
	

	public EXLoginForm(String name) {
		super(name, "Login");
		addField("Username <span style='color:red'>*</span>:", new EXInput("username"));
		addField("Password<span style='color:red'>*</span>:", new EXPassword("password"));
		
		addButton(new EXButton("login", "Login"));
		addButton(new EXButton("register", "New User?"));
		addButton(new EXButton("save", "Register and Login"));
		getDescendentByName("login").setAttribute("ancestor", getClass().getName());
		getDescendentByName("login").addClass("fg-small-button").removeClass("fg-button").setAttribute("method", "doLogin");
		getDescendentByName("login").addEvent(EventUtil.GENERIC_FORM_METHOD_EVENT, Event.CLICK).setStyle("float", "right");
		getDescendentByName("register").addClass("fg-small-button").removeClass("fg-button").setAttribute("ancestor", getClass().getName());
		getDescendentByName("register").setAttribute("method", "loadRegister").setStyle("float", "right");
		getDescendentByName("register").addEvent(EventUtil.GENERIC_FORM_METHOD_EVENT, Event.CLICK);
		getDescendentByName("save").addClass("fg-small-button").removeClass("fg-button").setDisplay(false).setStyle("float", "right");
		getDescendentByName("save").setAttribute("ancestor", getClass().getName());
		getDescendentByName("save").setAttribute("method", "doRegisterUser");
		getDescendentByName("save").addEvent(EventUtil.GENERIC_FORM_METHOD_EVENT, Event.CLICK);
		
		this.setShowCloseButton(false);
		
	}
	
	public EXLoginForm setRegister(boolean register){
		getDescendentByName("register").setDisplay(register);
		return this;
		
	}
	
	public void loadRegister(){
		addField("First name <span style='color:red'>*</span> :", new EXInput("firstname"));
		addField("Last name <span style='color:red'>*</span> :", new EXInput("lastname"));
		addField("Email : <span style='color:red'>*</span> :", new EXInput("email"));
		getDescendentByName("login").setDisplay(false);
		getDescendentByName("register").setDisplay(false);
		getDescendentByName("save").setDisplay(true);
		setWidth(Dimension.parse("400px"));
		ComponentUtil.applyStyleOnAll(this, EXInput.class, "width", "255px");
	}
	
	public void doRegisterUser(){
		try{
			setTitle("Fill in the form to register yourself");
			String username = getField("username").getValue().toString();
			String password = getField("password").getValue().toString();
			
			String firstName = getField("firstname").getValue().toString();
			String email = getField("email").getValue().toString();
			String lastname = getField("lastname").getValue().toString();

			boolean valid = true;
			if(!StringUtil.isNotEmpty(username)){
				getField("username").addClass("ui-state-error");
				valid = false;
			}else{
				getField("username").removeClass("ui-state-error");
			}
			if(!StringUtil.isNotEmpty(password)){
				getField("password").addClass("ui-state-error");
				valid= false;
			}else{
				getField("password").removeClass("ui-state-error");
			}
			
			
			if(!StringUtil.isNotEmpty(firstName)){
				valid = false;
				getField("firstname").addClass("ui-state-error");
			}else{
				getField("firstname").removeClass("ui-state-error");
			}
			if(!StringUtil.isNotEmpty(lastname)){
				valid = false;
				getField("lastname").addClass("ui-state-error");
			}else{
				getField("lastname").removeClass("ui-state-error");
			}
			if(!StringUtil.isNotEmpty(email)){
				valid = false;
				getField("email").addClass("ui-state-error");
			}else{
				getField("email").removeClass("ui-state-error");
			}
			
			
			
			if(valid){
			
				User user = new User();
				user.setUsername(username);
				user.setPassword(password);
				user.setFirstName(firstName);
				user.setLastName(lastname);
				user.setEmail(email);
				user.setEnabled(true);
				
				SecurityService service = SpringUtil.getSecurityService();
				service.registerUser(user);
				service.assignSecurity(user.getUsername(), "member", "users");
				fireOnRegisterUserHandler(user);
				
				doLogin();
			}else{
				throw new UIException("There are errors in the registration form. Please fill in the form correctly");
			}
			
		}catch(UIException e){
			throw  e;
		}catch(Exception e){
			throw new UIException(e);
		}
	}
	
	public EXLoginForm addOnLoginHandler(OnLoginHandler handler){
		onLoginHandlers.add(handler);
		return this;
	}
	
	public void clearOnloginHandlers(){
		onLoginHandlers.clear();
	}
	
	
	public EXLoginForm addOnRegisterUserHandler(OnRegisterUserHandler handler){
		this.onRegisterUserHandlers.add(handler);
		return this;
	}
	
	
	protected void fireOnRegisterUserHandler(User user){
		Application app = getRoot();
		for(OnRegisterUserHandler handler : onRegisterUserHandlers){
			handler.onRegisterUser(app, user);
		}
	}
	
	protected void fireOnLoginHandlers(String username){
		Application app = getRoot();
		for(OnLoginHandler handler : onLoginHandlers){
			handler.onLogin(app, username);
		}
	}
	public void doLogin(){
		String username = getField("username").getValue().toString();
		String password = getField("password").getValue().toString();
		boolean valid =true;
		if(!StringUtil.isNotEmpty(username)){
			valid = false;
			getField("username").addClass("ui-state-error");
		}
		if(!StringUtil.isNotEmpty(password)){
			valid = false;
			getField("password").addClass("ui-state-error");
		}
		
		if(valid){
			SecurityService service = BaseSpringUtil.getBeanOfType(SecurityService.class);
			try{
				if(service.login(username, password)){
					Application app = getRoot();
					this.fireOnLoginHandlers(username);
					app.setRendered(false);
				}else{
					throw new UIException("username or password is not correct. Please try again");
				}
			}catch(Exception e){
				throw new UIException(e);
			}
		}
	}

}
