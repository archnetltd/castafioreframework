package org.castafiore.site;

import java.util.Map;

import org.castafiore.designer.newportal.EXNewPortal;
import org.castafiore.searchengine.back.OS;
import org.castafiore.security.User;
import org.castafiore.security.api.SecurityService;
import org.castafiore.site.wizard.EXWiz;
import org.castafiore.site.wizard.GetStartedPage;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Application;
import org.castafiore.ui.Container;
import org.castafiore.ui.RefreshSentive;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXPassword;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.utils.StringUtil;

public class YoudoPortal extends EXXHTMLFragment implements Event, RefreshSentive{

	public YoudoPortal() {
		super("portal", "youdo-templates/portal.xhtml");
		//top menu
		addChild(new YoudoMenu());
		
		//slider
		//addChild(new EXXHTMLFragment("slider", "youdo-templates/slider.xhtml"));
		
		//services
		//addChild(new EXXHTMLFragment("services", "youdo-templates/services.xhtml"));
		
		
		addChild(new EXContainer("pageContainer", "div"));
		buildLogin();
		gotoPage("Home");
	}
	
	public void gotoPage(String name){
		if(name.equalsIgnoreCase("home")){
			if(getDescendentByName("slider")==null){
				addChild(new EXXHTMLFragment("slider", "youdo-templates/slider.xhtml"));
				
				//services
				addChild(new EXXHTMLFragment("services", "youdo-templates/services.xhtml"));
			}
			
			
		}else{
			if(getDescendentByName("slider")!=null){
				getDescendentByName("slider").remove();
			}
			
				
			if(getDescendentByName("services")!=null){
				getDescendentByName("services").remove();
			}
		}
		
		Container pageContainer=	 getDescendentByName("pageContainer");
		pageContainer.getChildren().clear();
		pageContainer.setRendered(false);
		
		if(name.equalsIgnoreCase("get started")){
			//pageContainer.addChild(new GetStartedPage(""));
			pageContainer.addChild(new EXWiz(""));
		}else{
			pageContainer.addChild(new EXXHTMLFragment("page", "youdo-templates/pages/" + name + ".xhtml"));
		}
	}
	
	
	
	
	public void buildLogin(){
		EXInput username = new EXInput("username");
		username.setStyle("margin", "7px 0 0 22px");
		addChild(username);
		
		EXPassword password = new EXPassword("password");
		password.setStyle("margin", "7px 0 0 22px");
		addChild(password);
		
		Container a = new EXContainer("login", "a").setAttribute("href", "#").setStyle("margin", "22px 0 -7px 22px").setText("Login");
		a.addEvent(this, Event.CLICK);
		addChild(a);
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
					if(u.isMerchant())
						return u;
					else{
						throw new UIException(username + " is not a merchant");
					}
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
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}




	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		User u = doLogin();
		if(u != null){
			Container root = getRoot();
			root.getChildren().clear();
			root.setRendered(false);
			root.addChild(new OS("OS", u));
		}
		return true;
	}




	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		
	}




	@Override
	public void onRefresh() {
		

	}

}
