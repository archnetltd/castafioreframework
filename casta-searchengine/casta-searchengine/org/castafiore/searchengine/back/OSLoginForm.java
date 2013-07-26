package org.castafiore.searchengine.back;

import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.security.User;
import org.castafiore.security.api.SecurityService;
import org.castafiore.security.logs.Log;
import org.castafiore.security.logs.Logger;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXPassword;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.utils.StringUtil;

public class OSLoginForm extends EXXHTMLFragment implements EventDispatcher{

	public OSLoginForm(String name) {
		super(name, "templates/back/OSLoginForm.xhtml");
		addChild(new EXInput("username").addClass("form-login"))
		.addChild(new EXPassword("password").addClass("form-login"))
		.addChild(new EXContainer("login", "a").setText("<img src=\"blueprint/images/login-btn.png\" width=\"103\" height=\"42\" style=\"margin-left:90px;\" />").addEvent(DISPATCHER, Event.CLICK));
	}
	public void login(Container source){
		doLogin();
	}
	
	public User  doLogin(){
		String username = ((EXInput)getChild("username")).getValue().toString();
		String password = ((EXPassword)getChild("password")).getValue().toString();
		boolean valid =true;
		if(!StringUtil.isNotEmpty(username)){
			valid = false;
			((EXInput)getChild("username")).addClass("ui-state-error");
			
		}
		if(!StringUtil.isNotEmpty(password)){
			valid = false;
			((EXPassword)getChild("password")).addClass("ui-state-error");
		}
		
		if(valid){
			SecurityService service = BaseSpringUtil.getBeanOfType(SecurityService.class);
			try{
				if(service.login(username, password)){
					
					//check if he is merchant
					User u = SpringUtil.getSecurityService().loadUserByUsername(username);
					if(u.isMerchant()){
						Logger.log("log into application", Log.WARNING);
						Container root = getRoot();
						root.getChildren().clear();
						root.setRendered(false);
						root.addChild(new OS("OS", u));
		
						
						return u;
					}
					else{
						Logger.log("try loging into application when user is not a merchant or part of a merchant organization", Log.WARNING);
						throw new UIException(username + " is not a merchant");
					}
				}else{
					Logger.log("logged with invalid username or password", Log.WARNING);
					throw new UIException("username or password is not correct. Please try again");
				}
			}catch(Exception e){
				Logger.log("tring to log, but unknow exception occured.", Log.WARNING);
				throw new UIException(e);
			}
		}else{
			return null;
		}
	}
	
	
	
	
	
	public void forgotPassword(Container source){
		
	}
	
	
	
	

	@Override
	public void executeAction(Container source) {
		
		if(source.getName().equals("login")){
			doLogin();
		}
	}
	

}
