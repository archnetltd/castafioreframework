package org.castafiore.splashy.templates;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.castafiore.searchengine.back.OS;
import org.castafiore.security.User;
import org.castafiore.security.api.SecurityService;
import org.castafiore.security.logs.Log;
import org.castafiore.security.logs.Logger;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.EXWebServletAwareApplication;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXPassword;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.utils.StringUtil;

public class EXSplashyLogin extends EXXHTMLFragment implements Event{

	public EXSplashyLogin(String name) {
		super(name, ResourceUtil.getDownloadURL("classpath", "org/castafiore/splashy/templates/EXSplashyLogin.xhtml"));
		addClass("container_12");
		addChild(new EXInput("username"));
		addChild(new EXPassword("password"));
		
		addChild(new EXContainer("save", "a").setStyle("float", "right").setStyle("margin-right", "25px").addClass("pro_btn").setText("Submit").addEvent(this, CLICK));
		
		addChild(new EXContainer("message", "p").setStyle("display", "none").setStyleClass("pro_info pro_info_warning grid_6"));
		
		addChild(new EXContainer("register", "a").setStyle("float", "right").setStyle("margin-right", "25px").addClass("pro_btn").setText("Register").addEvent(this, CLICK));
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
					if(u.isMerchant()){
						Logger.log("log into application", Log.WARNING);
//						Container root = getRoot();
//						root.getChildren().clear();
//						root.setRendered(false);
//						root.addChild(new OS("OS", u));
		
						
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
	/**
	 * ask for login directly from link.
	 * go to edit template
	 * ask for login from edit template
	 * 
	 */
	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		if(container.getName().equalsIgnoreCase("save")){
			try{
			User u = doLogin();
			request.put("redirect", "os.html");
			}catch(Exception e){
				getChild("message").setText(e.getMessage()).setDisplay(true);
			}
		}else{
			container.getAncestorOfType(EXSplashyTemplatesApplication.class).list();
		}
		
		
		
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		if(request.containsKey("redirect")){
			container.redirectTo("os.html");
		}
		
	}

}
