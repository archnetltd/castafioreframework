package org.castafiore.shoppingmall.user.ui;

import org.castafiore.searchengine.EXMall;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.security.Address;
import org.castafiore.security.User;
import org.castafiore.shoppingmall.user.ShoppingMallUserManager;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.panel.EXPanel;

public class EXContactInfo extends BaseEXUserProfile{

	public EXContactInfo() {
		super("ContactInfo", "/templates/EXContactInfo.xhtml");
		addChild(new EXInput("addressLine1").setAttribute("validation-method", "empty").setAttribute("error-message", "Please enter your address"))
		.addChild(new EXInput("addressLine2"))
		.addChild(new EXInput("addressLine3"))
		.addChild(new EXInput("postalCode"))
		.addChild(new EXInput("country"));
	}
	
	@Override
	public BaseEXUserProfile fill(){
		User u = MallUtil.getCurrentUser().getUser();
		u = SpringUtil.getSecurityService().hydrate(u);
		Address add = u.getDefaultAddress();
		if(add == null){
			add = new Address();
			add.setName("default");
			//u.addAddress(add);
		}
		for(Container c : getChildren()){
			if(!c.getTag().equalsIgnoreCase("button")){
				String profile = c.getName();
				
				String value = "";
				if(profile.equalsIgnoreCase("addressLine1")){
					value = add.getLine1();
				}else if(profile.equalsIgnoreCase("addressLine2")){
					value = add.getLine2();
				}else if(profile.equalsIgnoreCase("addressLine3")){
					value = add.getCity();
				}else if(profile.equalsIgnoreCase("postalCode")){
					value = add.getPostalCode();
				}else if(profile.equalsIgnoreCase("country")){
					value = add.getCountry();
				}
				
				if(c instanceof StatefullComponent){
					((StatefullComponent)c).setValue(value);
				}
			}
		}
		return this;
	}
	
	@Override
	public void save(Container source){
		
		User u = MallUtil.getCurrentUser().getUser();
		Address add = u.getDefaultAddress();
		if(add == null){
			add = new Address();
			u.addAddress(add);
			add.setDefaultAddress(true);
		}
		for(Container c : getChildren()){
			if(!c.getTag().equalsIgnoreCase("button")){
				String profile = c.getName();
				
				String value = "";
				if(c instanceof StatefullComponent){
					value = ((StatefullComponent)c).getValue().toString();
				}
				if(profile.equalsIgnoreCase("addressLine1")){
					add.setLine1(value);
				}else if(profile.equalsIgnoreCase("addressLine2")){
					add.setLine2(value);
				}else if(profile.equalsIgnoreCase("addressLine3")){
					add.setCity(value);
				}else if(profile.equalsIgnoreCase("postalCode")){
					add.setPostalCode(value);
				}else if(profile.equalsIgnoreCase("country")){
					add.setCountry(value);
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
