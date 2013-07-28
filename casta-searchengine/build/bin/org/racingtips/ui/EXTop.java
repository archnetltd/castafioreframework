package org.racingtips.ui;

import java.util.Map;

import org.castafiore.shoppingmall.ui.MallLoginSensitive;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.scripting.EXXHTMLFragment;

public class EXTop extends EXXHTMLFragment implements Event, MallLoginSensitive{

	public EXTop() {
		super("EXTop", "templates/racingtips/EXTop.xhtml");
		addClass("head-row1");
		addChild(new EXContainer("register", "a").setAttribute("href", "#").setText("Register").addEvent(this, CLICK));
		addChild(new EXContainer("signIn", "a").setAttribute("href", "#").setText("Sign In").addEvent(this, CLICK));
		addChild(new EXContainer("advertiseWithUs", "a").setAttribute("href", "#").setText("Advertise WithUs").addEvent(this, CLICK));
		addChild(new EXContainer("newUser", "strong").setText("New User?"));
	}
	
	
	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		if(container.getName().equalsIgnoreCase("register")){
			getAncestorOfType(EXPortal.class).showPage(EXRegister.class, true);
		}else if(container.getName().equalsIgnoreCase("signIn")){
			getAncestorOfType(EXPortal.class).showPage(EXLogin.class, true);
		}else{
			
		}
		
		
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		
		
	}


	@Override
	public void onLogin(String username) {
		getChild("signIn").setText("Log out").setAttribute("href", "flogout.jsp").getEvents().clear();
		getChild("register").setDisplay(false);
		getChild("newUser").setText("Welcome " + username);
		
	}

}
