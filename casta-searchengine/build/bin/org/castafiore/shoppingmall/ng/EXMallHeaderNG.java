package org.castafiore.shoppingmall.ng;

import java.util.Map;

import org.castafiore.security.ui.EXLoginForm;
import org.castafiore.security.ui.OnLoginHandler;
import org.castafiore.shoppingmall.product.ui.EXMiniCarts;
import org.castafiore.shoppingmall.ui.MallLoginSensitive;
import org.castafiore.ui.Application;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ComponentVisitor;

public class EXMallHeaderNG extends EXXHTMLFragment implements Event, OnLoginHandler{

	public EXMallHeaderNG(String name) {
		super(name, "templates/ng/EXMallHeaderNG.xhtml");
		//addChild(new EXMallMenu("menu"));
		addChild(new EXSearchProductBarNG("searchBar"));//margin: 17px; display: block; float: right; position: relative; top: 25px;
		addChild(new EXMiniCarts("carts"));
		addChild(new EXContainer("login", "a").setAttribute("title", "My personal space").setStyle("display", "none").setStyle("float", "right").setStyle("position", "relative").setStyle("top", "24px").setStyle("margin", "0px").setStyle("left", "15px").addEvent(this, Event.CLICK).setAttribute("href", "#").addChild(new EXContainer("", "img").setAttribute("src", "icons-2/fugue/bonus/icons-32/monitor.png")));
		addChild(new EXContainer("loginLink", "a").setAttribute("title", "Walk into eMall of Mauritius").setAttribute("href", "#").setText("<img src=\"http://icons.iconarchive.com/icons/oxygen-icons.org/oxygen/72/Apps-preferences-system-login-icon.png\"/>").addEvent(this, CLICK));
		//  top: 50px;
		addChild(new EXContainer("messages", "a").setAttribute("title", "Messages").setStyle("display", "none").setStyle("float", "right").setStyle("margin", "0px").setStyle("position", "relative").setStyle("top", "32px").setStyle("left", "63px").addEvent(this, Event.CLICK).setAttribute("href", "#").addChild(new EXContainer("", "img").setAttribute("src", "http://icons.iconarchive.com/icons/custom-icon-design/pretty-office-2/64/new-message-icon.png")));
		addChild(new EXContainer("backButton", "button").addClass("ui-state-default").setStyle("margin", "0 5px").setStyle("padding", "3px").addEvent(this, CLICK).setText("<img src=\"http://icons.iconarchive.com/icons/oxygen-icons.org/oxygen/24/Actions-arrow-left-icon.png\" style=\"width: 24px;height: 24px\"></img>"));
		addChild(new EXContainer("logout", "a").setAttribute("title", "Walk out of eMall of Mauritius").setStyle("display", "none").setStyle("float", "right").setStyle("margin", "0px").setStyle("position", "relative").setStyle("top", "3px").setStyle("left", "-18px").addEvent(this, Event.CLICK).setAttribute("href", "logout.jsp").addChild(new EXContainer("", "img").setAttribute("src", "http://icons.iconarchive.com/icons/fatcow/farm-fresh/32/door-out-icon.png")));
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mergeCommand(new ClientProxy("#loader").setStyle("display", "block")).makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		if(container.getName().equals("loginLink")){
			
			//EXPanel panel = new EXPanel("", "Login form");
			
			org.castafiore.shoppingmall.user.ui.EXLoginForm form = new org.castafiore.shoppingmall.user.ui.EXLoginForm("");
			form.setTemplateLocation("templates/ng/EXLoginFormNG.xhtml");
			//panel.setBody(form).setStyle("width", "500px");
			//panel.setStyle("z-index", "3000");
			form.setStyle("z-index", "3000");
			form.setStyle("width", "934px").setStyle("height", "697px").setStyle("margin", "0px").addClass("loginFormNG");
			form.getChild("walkIn").setText(" ");
			form.getChild("forgotPassword").setText("Here");
			
			container.getRoot().getDescendentOfType(PopupContainer.class).addPopup(form);
			form.setStyle("top", "1px");
			
		}else if(container.getName().equals("messages")){
			getRoot().getDescendentOfType(EXMallNG.class).addPopup(new EXMyMessagesNG(""));
		}else if(container.getName().equals("backButton")){
			getRoot().getDescendentOfType(EXMallNG.class).getDescendentOfType(EXCatalogueNG.class).back();
		}else{
			getRoot().getDescendentOfType(EXMallNG.class).addPopup(new EXMySpaceNG(""));
		}
		
		//getRoot().addChild(new EXMyAccountPanel("", "My Account"));
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		container.mergeCommand(new ClientProxy("#loader").setStyle("display", "none"));
		
	}

	@Override
	public void onLogin(Application app, final String username) {
		EXLoginForm form = app.getDescendentOfType(EXLoginForm.class);
	
		form.getParent().setRendered(false);
		form.remove();
		getDescendentByName("loginLink").setDisplay(false);
		getDescendentByName("login").setStyle("display", "block");
		getDescendentByName("messages").setStyle("display", "block");
		getDescendentByName("logout").setStyle("display", "block");
		ComponentUtil.iterateOverDescendentsOfType(app, MallLoginSensitive.class, new ComponentVisitor() {
			
			@Override
			public void doVisit(Container c) {
				((MallLoginSensitive)c).onLogin(username);
				
			}
		});
		
	}

}
