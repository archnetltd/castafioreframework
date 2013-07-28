package org.racingtips.ui;

import java.util.Map;

import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.castafiore.security.User;
import org.castafiore.security.api.SecurityService;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXCheckBox;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXPassword;
import org.castafiore.ui.scripting.EXXHTMLFragment;

public class EXRegister extends EXXHTMLFragment implements Event{

	public EXRegister() {
		super("EXRegister", "templates/racingtips/EXRegister.xhtml");
		addClass("bg-cont");
		addChild(new EXContainer("message", "label").setStyle("color", "red"));
		addChild(new EXInput("username").addClass("form-text").addClass("required").setAttribute("size", "60").setAttribute("maxlength", "60"));
		
		addChild(new EXPassword("password").addClass("form-text").addClass("required").setAttribute("size", "60").setAttribute("maxlength", "60"));
		
		addChild(new EXInput("email").addClass("form-text").addClass("required").setAttribute("size", "60").setAttribute("maxlength", "60"));
		
		addChild(new EXContainer("createNewAccount", "button").setText("Create new account").addClass("form-submit").addEvent(this, CLICK));
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		
		
		
		String username = ((EXInput)getChild("username")).getValue().toString();
		String password = ((EXInput)getChild("password")).getValue().toString();
		String email = ((EXInput)getChild("email")).getValue().toString();
		
		SecurityService service = SpringUtil.getSecurityService();
		try{
			service.loadUserByUsername(username);
			getChild("message").setText("This username already exists.<br>Please choose another username");
		}catch(UsernameNotFoundException nf){
			User user =new User();
			user.setUsername(username);
			user.setPassword(password);
			user.setEmail(email);
			service.saveOrUpdateUser(user);
			getChild("message").setText("You have been successfully registered.<br>You can now login with your username and password");
		}catch(Exception e){
			getChild("message").setText("An unknow error has occured. Please try again later");
		}
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		
		
	}

}
