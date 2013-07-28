package org.castafiore.shoppingmall.ng.v2;

import java.util.Map;

import org.castafiore.designable.checkout.EXRegisterUser;
import org.castafiore.searchengine.back.EXEmallConfigLayer;
import org.castafiore.security.User;
import org.castafiore.security.api.SecurityService;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXPassword;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;

public class EXLogin extends EXXHTMLFragment implements Event{

	public EXLogin(String name) {
		super(name, "templates/v2/EXLogin.xhtml");
		addClass("login");
		addChild(new EXInput("username"));
		addChild(new EXPassword("password"));
		
		addChild(new EXContainer("loginBtn", "a").setAttribute("href", "#").setText("<img src=\"emimg/item/images/login/login-btn.png\"></img>").addEvent(this, CLICK));
		
		addChild(new EXContainer("newUser", "a").setAttribute("href", "#").setText("<img src=\"emimg/item/images/login/nu.png\"></img>").addEvent(this, CLICK));
		addChild(new EXContainer("forgotPassword", "a").setAttribute("href", "fp").setText("<img src=\"emimg/item/images/login/nr.png\"></img>").addEvent(this, CLICK));
		
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
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
					Container parent = getParent();
					Container myspace = new EXContainer("personalSpace", "img").setAttribute("src", "http://icons.iconarchive.com/icons/kyo-tux/aeon/72/Folder-Blue-Configure-icon.png");
					parent.addChild(myspace);
					myspace.setStyle("margin", "5px");
					if(u.isMerchant()){
						myspace.setStyle("margin", "21px 0");
					}
					myspace.setAttribute("title", "Click on image to enter your personal space");
					myspace.addEvent(this, CLICK);
					myspace.setStyle("cursor", "pointer");
					if(!u.isMerchant()){
						myspace.setAttribute("src", "http://icons.iconarchive.com/icons/kyo-tux/aeon/128/Folder-Blue-Configure-icon.png");
					}
					this.remove();
					if(u.isMerchant()){
						Container img = new EXContainer("configure", "img").setAttribute("src", "http://icons.iconarchive.com/icons/zerode/plump/72/Folder-Settings-Tools-icon.png");
						parent.addChild(img);
						img.setStyle("margin", "54px 0 0 14px");
						img.setAttribute("title", "Click on image to configure your shops and products");
						img.addEvent(this, CLICK);
						img.setStyle("cursor", "pointer");
						
						
					}
					
					Container logout = new EXContainer("logout", "a").setStyle("margin", "31px").setAttribute("href", "logout.jsp").addChild(new EXContainer("", "img").setAttribute("src", "http://icons.iconarchive.com/icons/custom-icon-design/pretty-office-6/64/logout-icon.png"));
					parent.addChild(logout);
					//logout.setStyle("margin", "5px");
					logout.setAttribute("title", "Logout");
					
					logout.setStyle("cursor", "pointer");
					
					return null;
				}else{
					
					throw new UIException("username or password is not correct. Please try again");
				}
			}catch(Exception e){
				
				throw new UIException(e);
			}
		}else{
			return null;
		}
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		
		if(container.getName().equalsIgnoreCase("loginBtn")){
			doLogin();
			return true;
		}else if(container.getName().equalsIgnoreCase("configure")){
			container.getAncestorOfType(PopupContainer.class).addPopup(new EXEmallConfigLayer("sdfsd", Util.getLoggedOrganization()));
			return true;
		}
		
		EXRegisterUser u = new EXRegisterUser("sd");
		container.getAncestorOfType(PopupContainer.class).addPopup(u);
		
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
