package org.castafiore.shoppingmall.user.ui;

import org.castafiore.searchengine.MallUtil;
import org.castafiore.security.User;
import org.castafiore.security.api.UserProfileService;
import org.castafiore.shoppingmall.ui.MallForm;
import org.castafiore.shoppingmall.user.ShoppingMallUserManager;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.StringUtil;

public class BaseEXUserProfile extends EXXHTMLFragment implements MallForm{

	public BaseEXUserProfile(String name, String templateLocation) {
		super(name, templateLocation);
		addChild(new EXContainer("save", "button").setText("Save").addClass("ui-widget-header").setAttribute("validate", "true"))
		.addChild(new EXContainer("cancel", "button").addClass("ui-widget-header").setText("Cancel"));
	}
	public BaseEXUserProfile fill(){
		User u = MallUtil.getCurrentUser().getUser() ;
		UserProfileService profileService = SpringUtil.getBeanOfType(UserProfileService.class);
		for(Container c : getChildren()){
			if(!c.getTag().equalsIgnoreCase("button")){
				String profile = c.getName();
				
				String value = profileService.getUserProfileValue(u.getUsername(), getName(), profile);
				if(c instanceof StatefullComponent){
					((StatefullComponent)c).setValue(value);
				}else if(c.getTag().equalsIgnoreCase("img")){
					c.setAttribute("src", value);
				}
			}
		}
		return this;
	}
	
	
	public boolean phone(StatefullComponent input){
		String value = input.getValue().toString();
		return value.length()==7;
	}
	
	public boolean email(StatefullComponent input){
		String value = input.getValue().toString();
		return value.contains("@") && value.contains(".");
	}
	
	public void save(Container source){
		for(Container c : getChildren()){
			if(!c.getTag().equalsIgnoreCase("button")){
				String profile = c.getName();
				String value = "";
				if(c instanceof StatefullComponent){
					value = ((StatefullComponent)c).getValue().toString();
				}else if(c.getTag().equalsIgnoreCase("img")){
					value = c.getAttribute("src");
				}
				
				if(StringUtil.isNotEmpty(value)){
					User u = MallUtil.getCurrentUser().getUser();
					UserProfileService profileService = SpringUtil.getBeanOfType(UserProfileService.class);
					profileService.saveUserProfileValue(u.getUsername(), getName(), profile, value);
				}
				
				
			}
		}
		getAncestorOfType(EXPanel.class).setTitle("<p style='color:orange'>User profile updated</p>");
	}
	
	public void cancel(Container cancel){
		getAncestorOfType(EXPanel.class).remove();
	}

	

}
