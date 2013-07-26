package org.castafiore.designable.checkout;

import java.util.List;
import java.util.Map;

import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.castafiore.SimpleKeyValuePair;
import org.castafiore.designable.AbstractXHTML;
import org.castafiore.security.Address;
import org.castafiore.security.User;
import org.castafiore.shoppingmall.checkout.BillingInformation;
import org.castafiore.shoppingmall.checkout.Order;
import org.castafiore.shoppingmall.merchant.FinanceUtil;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXAutoComplete;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXPassword;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.list.DefaultDataModel;

import org.castafiore.utils.StringUtil;

public class EXBillingInformation extends AbstractXHTML implements Event{

	
	
	public EXBillingInformation(String name) {
		super(name);
		addClass("ui-widget-content");
		String[] fields = new String[]{"username", "password","firstname", "lastname", "company", "email", "addressline1", "addressline2", "city","country", "mobile", "postcode", "telephone", "fax"};
		addChild(new EXButton("signIn", "Sign In").addEvent(this, Event.CLICK).setStyleClass("ui-state-default ui-corner-all fg-button-small").setStyle("float", "right").setStyle("margin-top", "10px").setStyle("width", "120px"));
		addChild(new EXContainer("message", "h5").addClass("ui-state-error").setStyle("padding", "5px").setStyle("margin", "5px").setDisplay(false));
		for(String s : fields){
			if(s.equals("password")){
				EXInput input = new EXPassword("billing." + s);
				addChild(input.addClass("input-text"));
			}else if(s.equals("country")){
				try{
					addChild(new EXAutoComplete("billing." +s, "Mauritius", FinanceUtil.getCountryList()));
					//addChild(new  EXSelect("billing." +s, new DefaultDataModel<Object>()));
					//getDescendentOfType(EXSelect.class).addEvent(this, READY);
					//addChild(new  EXSelect("billing." +s, new DefaultDataModel<Object>((List)FinanceUtil.getCountries())));
					//getDescendentOfType(EXSelect.class).setValue(FinanceUtil.getClientCountry());
				}catch(Exception e){
					e.printStackTrace();
				}
			}else{
				EXInput input = new EXInput("billing." + s);
				addChild(input.addClass("input-text"));	
			}
			
		}
		
		addChild(new EXButton("continue", "Continue Anonymously").addEvent(this, Event.CLICK).setStyleClass("ui-state-default ui-corner-all fg-button-small").setStyle("float", "right").setStyle("margin-top", "10px").setStyle("width", "160px"));
		
		addChild(new EXButton("registerAndContinue", "Register and continue").addEvent(this, Event.CLICK).setStyleClass("ui-state-default ui-corner-all fg-button-small").setStyle("float", "right").setStyle("margin-top", "10px").setStyle("width", "160px"));
	}
	
	
	public String getValue(String field){
		
		return ((StatefullComponent)getChild("billing." +field)).getValue().toString();
	}
	
public void setValue(String field, String value){
		
		 ((StatefullComponent)getChild("billing." +field)).setValue(value);
	}
	
	private boolean valField(String field){
		StatefullComponent stf = ((StatefullComponent)getChild("billing." + field ));
		if(!StringUtil.isNotEmpty(stf.getValue().toString())){
			stf.addClass("ui-state-error");
			return false;
		}else{
			return true;
		}
	}
	
	public boolean validate(){
		String[] fields = new String[]{"firstname", "lastname",  "email", "addressline1", "addressline2", "city",  "telephone"};
		boolean result = true;
		for(String s : fields){
			if(valField(s)){
				
			}else{
				if(result){
					result = false;
				}
			}
		}
		
		return result;
	}
	
	public BillingInformation createInfo(Order order){
		BillingInformation si = order.createFile("billing", BillingInformation.class);
		
		si.setAddressLine1(getValue("addressline1"));
		si.setAddressLine2(getValue("addressline2"));
		si.setCity(getValue("city"));
		si.setCompany(getValue("company"));
		si.setEmail(getValue("email"));
		si.setFirstName(getValue("firstname"));
		si.setLastName(getValue("lastname"));
		si.setZipPostalCode(getValue("postcode"));
		si.setPhone(getValue("telephone"));
		si.setFax(getValue("fax"));
		
		//SimpleKeyValuePair kb =  (SimpleKeyValuePair)getDescendentOfType(EXSelect.class).getValue();
		
		String country = getDescendentOfType(EXAutoComplete.class).getValue().toString();
		String code = FinanceUtil.getCode(country);
		si.setCountry(country + "," + code);
		si.setMobile(getValue("mobile"));
		return si;
	}
	
	
	public void setMessage(String message){
		getChild("message").setDisplay(true).setText(message);
	}
	
	public void signIn(String username){
		try{
			User u = SpringUtil.getSecurityService().loadUserByUsername(username);
			if(u != null){
				
				getAncestorOfType(EXCheckout.class).setAttribute("customer", username);
				setValue("firstname", u.getFirstName());
				setValue("lastname", u.getLastName());
				setValue("email", u.getEmail());
				u = SpringUtil.getSecurityService().hydrate(u);
				Address defAddress = u.getDefaultAddress();
				if(defAddress != null){
					setValue("addressline1", u.getDefaultAddress().getLine1());
					setValue("addressline2", u.getDefaultAddress().getLine2());
					setValue("city", u.getDefaultAddress().getCity());
					setValue("postcode", u.getDefaultAddress().getPostalCode());
					setValue("country", u.getDefaultAddress().getCountry());
				}
				
				setValue("telephone", u.getPhone());
				setValue("fax", u.getFax());
				getChild("registerAndContinue").setDisplay(false);
				getChild("continue").setText("Continue");
				
			}
		}catch(UsernameNotFoundException nfe){
			setMessage("The user specified does not exist");
		}
		
	}
	
	public void signIn(){
		String username = ((EXInput)getChild("billing.username")).getValue().toString();
		String password = ((EXInput)getChild("billing.password")).getValue().toString();
		
		boolean valid = true;
		if(!StringUtil.isNotEmpty(username)){
			getChild("billing.username").addClass("ui-state-error");
			valid = false;
		}
		
		if(!StringUtil.isNotEmpty(password)){
			getChild("billing.password").addClass("ui-state-error");
			valid = false;
		}
		
		if(valid){
			try{
				User u = SpringUtil.getSecurityService().loadUserByUsername(username);
				if(u != null){
					if(u.getPassword().equals(password)){
						//fill secondart
						getAncestorOfType(EXCheckout.class).setAttribute("customer", username);
						setValue("firstname", u.getFirstName());
						setValue("lastname", u.getLastName());
						setValue("email", u.getEmail());
						u = SpringUtil.getSecurityService().hydrate(u);
						Address defAddress = u.getDefaultAddress();
						if(defAddress != null){
							setValue("addressline1", u.getDefaultAddress().getLine1());
							setValue("addressline2", u.getDefaultAddress().getLine2());
							setValue("city", u.getDefaultAddress().getCity());
							setValue("postcode", u.getDefaultAddress().getPostalCode());
							setValue("country", u.getDefaultAddress().getCountry());
						}
						
						setValue("telephone", u.getPhone());
						setValue("fax", u.getFax());
						getChild("registerAndContinue").setDisplay(false);
						getChild("continue").setText("Continue");
						
					}else{
						setMessage("The password does not match");
						//message "The password does not match";
					}
				}
			}catch(UsernameNotFoundException nfe){
				setMessage("The user specified does not exist");
			}
		}
		
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask(container.getAncestorOfType(EXBillingInformation.class)).makeServerRequest(this);
	}
	
	public void registerAndContinue(){
		//check username and password
		
		String username = ((EXInput)getChild("billing.username")).getValue().toString();
		String password = ((EXInput)getChild("billing.password")).getValue().toString();
		
		boolean valid = true;
		if(!StringUtil.isNotEmpty(username)){
			getChild("billing.username").addClass("ui-state-error");
			valid = false;
		}
		
		if(!StringUtil.isNotEmpty(password)){
			getChild("billing.password").addClass("ui-state-error");
			valid = false;
		}
		
		
		if(valid){
			try{
				User u = SpringUtil.getSecurityService().loadUserByUsername(username);
				setMessage("The username already exist. Please choose another one");
			}catch(UsernameNotFoundException nfe){
				if(validate()){
					
					//create and register user here
					//"firstname", "lastname", "company", "email", "addressline1", "addressline2", "city", "postcode", "telephone", "fax"};
					
					User em = SpringUtil.getSecurityService().loadUserByEmail(getValue("email"));
					if(em != null){
						setMessage("There is already a user with the specified email in our database. Please choose another one");
						return;
					}
					
					User u = new User();
					u.setUsername(username);
					u.setPassword(password);
					u.setFirstName(getValue("firstname"));
					u.setLastName(getValue("lastname"));
					u.setEmail(getValue("email"));
					u.setPhone(getValue("telephone"));
					u.setFax(getValue("fax"));
					
					Address add = new Address();
					add.setDefaultAddress(true);
					add.setCity(getValue("city"));
					
					//SimpleKeyValuePair kb =  (SimpleKeyValuePair)getDescendentOfType(EXSelect.class).getValue();
					String country = ((StatefullComponent)getDescendentByName("billing.country")).getValue().toString();
					
					
					add.setCountry(country);
					//add.setCountryCode(kb.getKey());
					add.setLine1(getValue("addressline1"));
					add.setLine2(getValue("addressline2"));
					add.setCity(getValue("city"));
					
					u.addAddress(add);
					try{
						SpringUtil.getSecurityService().registerUser(u);
						
						
						EXCheckout co = getAncestorOfType(EXCheckout.class);
						Container shipping =co.getChild("shipping");
						if(shipping.getChildren().size() ==1){
							shipping.addChild(new EXShippingInformation(""));
						}else{
							shipping.getChildByIndex(1).setDisplay(true);
						}
						
						setDisplay(false);
					}catch(Exception e){
						setMessage("An unknow error has occured while registering user. Please try again later");
					}
				}
			}
		}
		
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		
		if(container.getName().equals("signIn")){
			signIn();
			return true;
		}else if(container.getName().equals("registerAndContinue")){
			registerAndContinue();
			return true;
		}else{
		
			if(validate()){
				EXCheckout co = getAncestorOfType(EXCheckout.class);
				Container shipping =co.getChild("shipping");
				if(shipping.getChildren().size() ==1){
					shipping.addChild(new EXShippingInformation("").setStyle("width", "674px"));
				}else{
					shipping.getChildByIndex(1).setDisplay(true);
				}
				
				setDisplay(false);
			}
		}
		
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
