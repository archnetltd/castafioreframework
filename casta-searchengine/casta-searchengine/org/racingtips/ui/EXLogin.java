package org.racingtips.ui;

import java.util.Map;

import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.castafiore.security.User;
import org.castafiore.security.api.SecurityService;
import org.castafiore.shoppingmall.ui.MallLoginSensitive;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXPassword;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ComponentVisitor;

public class EXLogin extends EXXHTMLFragment implements Event, ComponentVisitor, MallLoginSensitive {

	public EXLogin() {
		super("EXLogin", "templates/racingtips/EXLogin.xhtml");
		addClass("bg-cont");
		addChild(new EXContainer("message", "label").setStyle("color", "red"));
		addChild(new EXInput("username").addClass("form-text").addClass("required").setStyle("width", "350px"));
		
		addChild(new EXPassword("password").addClass("form-text").addClass("required").setStyle("width", "350px"));
		
		addChild(new EXContainer("login", "button").setText("Log in").addClass("form-submit").addEvent(this, CLICK));
		
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
		
		SecurityService service = SpringUtil.getSecurityService();
		try{
			boolean login = service.login(username, password);
			if(!login){
				getChild("message").setText("The username and password do not match. Please choose another username and or password");
			}else{
				Container root = getRoot();
				ComponentUtil.iterateOverDescendentsOfType(root, MallLoginSensitive.class, this);
			}
		}catch(UsernameNotFoundException nf){
			User user =new User();
			user.setUsername(username);
			user.setPassword(password);
			service.saveOrUpdateUser(user);
			getChild("message").setText("The username and password do not match. Please choose another username and or password");
		}catch(Exception e){
			getChild("message").setText("An unknow error has occured. Please try again later");
		}
		
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		
		
	}

	@Override
	public void doVisit(Container c) {
		if(c instanceof MallLoginSensitive){
			((MallLoginSensitive)c).onLogin(((EXInput)getChild("username")).getValue().toString());
		}
		
	}

	@Override
	public void onLogin(String username) {
		try{
			String clazz = getAttribute("from");
			Class c = Thread.currentThread().getContextClassLoader().loadClass(clazz);
			getAncestorOfType(EXPortal.class).showPage(c, true);
		}catch(Exception e){
			throw new UIException(e);
		}
		
	}

}
