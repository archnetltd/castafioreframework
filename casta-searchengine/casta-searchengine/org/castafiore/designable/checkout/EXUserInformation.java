package org.castafiore.designable.checkout;

import java.util.Map;

import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.castafiore.SimpleKeyValuePair;
import org.castafiore.security.Address;
import org.castafiore.security.User;
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
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.StringUtil;

public class EXUserInformation extends EXXHTMLFragment implements Event{

	public EXUserInformation(String name) {
		super(name, "templates/v2/EXUserInformation.xhtml");
		addClass("ui-widget-content");
		String[] fields = new String[]{"username", "password","firstname", "lastname", "company", "email", "addressline1", "addressline2", "city","country", "mobile", "postcode", "telephone", "fax"};
		addChild(new EXContainer("message", "h5").addClass("ui-state-error").setStyle("padding", "5px").setStyle("margin", "5px").setDisplay(false));
		for(String s : fields){
			if(s.equals("password")){
				EXInput input = new EXPassword("billing." + s);
				addChild(input.addClass("input-text"));
			}else if(s.equals("country")){
				try{
					addChild(new EXAutoComplete("billing." +s, "Mozambique", FinanceUtil.getCountryList()));
				}catch(Exception e){
					e.printStackTrace();
				}
			}else{
				EXInput input = new EXInput("billing." + s);
				addChild(input.addClass("input-text"));	
			}
			
		}
		
		addChild(new EXButton("registerUser", "Register").addEvent(this, Event.CLICK).setStyleClass("ui-state-default ui-corner-all fg-button-small").setStyle("float", "right").setStyle("margin-top", "10px").setStyle("width", "160px"));
		
		addChild(new EXButton("cancel", "Cancel").addEvent(this, Event.CLICK).setStyleClass("ui-state-default ui-corner-all fg-button-small").setStyle("float", "right").setStyle("margin-top", "10px").setStyle("width", "160px"));
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
	
	
	
	
	public void setMessage(String message){
		getChild("message").setDisplay(true).setText(message);
	}
	
	

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask(container.getAncestorOfType(EXBillingInformation.class)).makeServerRequest(this);
	}
	
	public void register(){
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
					
					SimpleKeyValuePair kb =  (SimpleKeyValuePair)getDescendentOfType(EXSelect.class).getValue();
					
					
					add.setCountry(kb.getValue());
					add.setCountryCode(kb.getKey());
					add.setLine1(getValue("addressline1"));
					add.setLine2(getValue("addressline2"));
					add.setCity(getValue("city"));
					
					u.addAddress(add);
					try{
						SpringUtil.getSecurityService().registerUser(u);
						EXCheckout co = getAncestorOfType(EXCheckout.class);
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
		
		if(container.getName().equals("register")){
			register();
			return true;
		}
		
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}
}
