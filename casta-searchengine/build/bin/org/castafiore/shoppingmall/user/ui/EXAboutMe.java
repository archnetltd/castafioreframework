package org.castafiore.shoppingmall.user.ui;

import org.castafiore.searchengine.EXMall;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.security.User;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXLabel;
import org.castafiore.ui.ex.form.EXPassword;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.utils.StringUtil;

public class EXAboutMe extends BaseEXUserProfile{

	public EXAboutMe() {
		super("AboutMe", "templates/EXAboutMe.xhtml");
		addChild(new EXContainer("avatar", "img").setAttribute("src", "http://www.space.com/common/forums/images/avatars/gallery/All/Avatar_gear.jpg"))
		
		.addChild(new EXLabel("username", ""))
		.addChild(new EXPassword("password").setAttribute("validation-method", "empty").setAttribute("error-message", "Please enter your password"))
		.addChild(new EXInput("lastName").setAttribute("validation-method", "empty").setAttribute("error-message", "Please enter your last name"))
		.addChild(new EXInput("firstName").setAttribute("validation-method", "empty").setAttribute("error-message", "Please enter your first name"))
		.addChild(new EXInput("email").setAttribute("validation-method", "email").setAttribute("error-message", "Please enter your email"))
		.addChild(new EXInput("phone").setAttribute("validation-method", "phone").setAttribute("error-message", "Please enter your phone"))
		.addChild(new EXInput("mobile").setAttribute("validation-method", "phone").setAttribute("error-message", "Please enter your mobile number"));
		addChild(new EXSelect("gender", new DefaultDataModel().addItem("Male", "Female")));	
	
		
	}
	
	@Override
	public BaseEXUserProfile fill(){
		User u = MallUtil.getCurrentUser().getUser();;
		for(Container c : getChildren()){
			if(!c.getTag().equalsIgnoreCase("button")){
				String profile = c.getName();
				
				String value = "";
				if(profile.equalsIgnoreCase("username")){
					value = u.getUsername();
				}else if(profile.equalsIgnoreCase("password")){
					value = u.getPassword();
				}else if(profile.equalsIgnoreCase("firstName")){
					value = u.getFirstName();
				}else if(profile.equalsIgnoreCase("lastName")){
					value = u.getLastName();
				}else if(profile.equalsIgnoreCase("email")){
					value = u.getEmail();
				}else if(profile.equalsIgnoreCase("phone")){
					value = u.getPhone();
				}else if(profile.equalsIgnoreCase("mobile")){
					value = u.getMobile();
				}else if(profile.equalsIgnoreCase("gender")){
					value = u.getGender();
				}else if(profile.equalsIgnoreCase("avatar")){
					value = u.getAvatar();
				}
				
				if(c instanceof StatefullComponent){
					((StatefullComponent)c).setValue(value);
				}else if(c.getTag().equalsIgnoreCase("img")){
					c.setAttribute("src", value);
				}
			}
		}
		return this;
	}
	
	@Override
	public void save(Container source){
		User u = MallUtil.getCurrentUser().getUser();
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
					if(profile.equalsIgnoreCase("username")){
						u.setUsername(value);
					}else if(profile.equalsIgnoreCase("password")){
						u.setPassword(value);
					}else if(profile.equalsIgnoreCase("firstName")){
						u.setFirstName(value);
					}else if(profile.equalsIgnoreCase("lastName")){
						u.setLastName(value);
					}else if(profile.equalsIgnoreCase("email")){
						u.setEmail(value);
					}else if(profile.equalsIgnoreCase("phone")){
						u.setPhone(value);
					}else if(profile.equalsIgnoreCase("mobile")){
						u.setMobile(value);
					}else if(profile.equalsIgnoreCase("gender")){
						u.setGender(value);
					}else if(profile.equalsIgnoreCase("avatar")){
						u.setAvatar(value);
					}
				}
			}
		}
		
		SpringUtil.getSecurityService().saveOrUpdateUser(u);
		
		EXMall mall = getAncestorOfType(EXMall.class);
		if(mall != null){
			mall.showMessage("User info correctly saved");
		}else{
			getAncestorOfType(EXPanel.class).setTitle("<p style='color:orange'>User profile updated</p>");
		}
		
	}

}
