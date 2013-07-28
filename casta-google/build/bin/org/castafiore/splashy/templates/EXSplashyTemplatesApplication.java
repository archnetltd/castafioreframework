package org.castafiore.splashy.templates;

import org.castafiore.catalogue.Product;
import org.castafiore.facebook.ui.EXFriendsList;
import org.castafiore.facebook.ui.EXInviteFriends;
import org.castafiore.shoppingmall.contacts.ui.EXInviteFriend;
import org.castafiore.splashy.EXFree;
import org.castafiore.ui.RefreshSentive;
import org.castafiore.ui.UIException;
import org.castafiore.ui.ex.EXWebServletAwareApplication;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;

import com.restfb.DefaultFacebookClient;

public class EXSplashyTemplatesApplication extends EXWebServletAwareApplication implements RefreshSentive{

	public EXSplashyTemplatesApplication() {
		super("splashytemplates");
		 addChild(new EXTemplates("templates"));
	}
	
	public void preview(Product product){
		getChildren().clear();
		setRendered(false);
		addChild(new EXPreview("preview", product));
	}
	
	public void edit(Product product){
		//either login or register
		if(Util.getRemoteUser() == null){
			//goToLogin();
			goToRegister();
		}else{
			try{
			getResponse().sendRedirect("os.html");
			}catch(Exception e){
				throw new UIException(e);
			}
		}
	}
	
	public void inviteFriends(){
		this.getChildren().clear();
		addChild(new EXInviteFriends("fdsdf"));
		
		
	}
	
	public void goToLogin(){
		getChildren().clear();
		setRendered(false);
		addChild(new EXSplashyLogin("login"));
	}
	
	
	public void goToRegister(){
		this.getChildren().clear();
		this.setRendered(false);
		addChild(new EXFree("free"));
	}
	
	public void list(){
		this.getChildren().clear();
		setRendered(false);
		 addChild(new EXTemplates("templates"));
		 
	}

	@Override
	public void onRefresh() {
		
		if(StringUtil.isNotEmpty(getRequest().getQueryString()) && getRequest().getQueryString().contains("fromLogin"))
			goToLogin();
		else if(StringUtil.isNotEmpty(getConfigContext().get("facebook"))){
			inviteFriends();
		}else{
			if(getDescendentOfType(EXSplashyLogin.class) != null && StringUtil.isNotEmpty(getRequest().getQueryString())){
				this.getChildren().clear();
				addChild(new EXTemplates("templates"));
			}
		}
			
		
		
	}

}
