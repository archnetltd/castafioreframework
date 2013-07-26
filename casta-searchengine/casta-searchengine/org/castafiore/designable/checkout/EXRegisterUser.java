package org.castafiore.designable.checkout;

import java.util.Iterator;
import java.util.Map;

import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.castafiore.SimpleKeyValuePair;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.searchengine.back.OSBarItem;
import org.castafiore.security.Address;
import org.castafiore.security.User;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.types.Article;
import org.castafiore.wfs.types.Directory;

public class EXRegisterUser extends EXContainer implements Event{

	public EXRegisterUser(String name) {
		super(name, "div");
		
		setStyle("margin-top", "30px");
		Container billing = new EXContainer("billing", "div").addClass("cart-widget");
		billing.addChild(new EXContainer("head", "div").addChild(new EXContainer("", "h4").addClass("ui-widget-header").setStyle("padding", "7px").addClass("ui-corner-top").setText("Enter personal information")));
		addChild(billing);
		billing.addChild(new EXUserInformation("").setStyle("width", "707px"));
		getDescendentOfType(EXUserInformation.class).getChild("registerUser").getEvents().clear();
		getDescendentOfType(EXUserInformation.class).getChild("cancel").getEvents().clear();
		
		
		getDescendentOfType(EXUserInformation.class).getChild("cancel").addEvent(this, CLICK).removeClass("fg-button-small");
		getDescendentOfType(EXUserInformation.class).getChild("registerUser").addEvent(this, CLICK).removeClass("fg-button-small");
		
		
		setStyle("z-index", "4000");
		setStyle("width", "709px");
	}
	
	public String getValue(String field){
		
		return ((StatefullComponent) getDescendentOfType(EXUserInformation.class).getChild("billing." +field)).getValue().toString();
	}
	
	public void setValue(String field, String value){
		
		 ((StatefullComponent)getDescendentOfType(EXUserInformation.class).getChild("billing." +field)).setValue(value);
	}
	
	private boolean valField(String field){
		StatefullComponent stf = ((StatefullComponent)getDescendentOfType(EXUserInformation.class).getChild("billing." + field ));
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
		getDescendentOfType(EXUserInformation.class).getChild("message").setDisplay(true).setText(message);
	}
	
	


	
	public void register(){
		//check username and password
		
		String username = ((EXInput)getDescendentOfType(EXUserInformation.class).getChild("billing.username")).getValue().toString();
		String password = ((EXInput)getDescendentOfType(EXUserInformation.class).getChild("billing.password")).getValue().toString();
		
		boolean valid = true;
		if(!StringUtil.isNotEmpty(username)){
			getDescendentOfType(EXUserInformation.class).getChild("billing.username").addClass("ui-state-error");
			valid = false;
		}
		
		if(!StringUtil.isNotEmpty(password)){
			getDescendentOfType(EXUserInformation.class).getChild("billing.password").addClass("ui-state-error");
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
					u.setMerchant(true);
					Address add = new Address();
					add.setDefaultAddress(true);
					add.setCity(getValue("city"));

					add.setCountry(getValue("country"));
					add.setLine1(getValue("addressline1"));
					add.setLine2(getValue("addressline2"));
					add.setCity(getValue("city"));
					u.setMerchant(false);
					u.addAddress(add);
					try{
						SpringUtil.getSecurityService().registerUser(u);
						
//						Merchant merchant = MallUtil.getCurrentMall().createMerchant(u.getUsername());//new Merchant();
//						merchant.setTitle(getValue("company"));
//						merchant.setEmail(u.getEmail());
//						merchant.setMobilePhone(u.getPhone());
//						merchant.setUsername(u.getUsername());
//						merchant.setCompanyName(getValue("company"));
//						merchant.setAddressLine1(getValue("addressline1"));
//						merchant.setAddressLine2(getValue("addressline2"));
//						merchant.setCity(getValue("city"));
//						merchant.setPlan("Free");
//						merchant.setStatus(12);
//						
//						Directory apps = merchant.createFile("MyApplications", Directory.class);
//						
//						
//						Map<String, OSBarItem> myapps = SpringUtil.getApplicationContext().getBeansOfType(OSBarItem.class);
//						Iterator<String> iter = myapps.keySet().iterator();
//						while(iter.hasNext()){
//							OSBarItem item = myapps.get(iter.next());
//							Article app = apps.createFile(item.getAppName(), Article.class);
//							app.setTitle(item.getTitle());
//							app.setSummary(item.getIcon());
//						}
//						
//						
//						merchant.save();
						
						setMessage("You have been successfully registered. Now close this window, then login with the username and password you chose");
						
						//EXCheckout co = getAncestorOfType(EXCheckout.class);
					}catch(Exception e){
						setMessage("An unknow error has occured while registering user. Please try again later");
					}
				}
			}
		}
		
		
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		if(container.getName().equalsIgnoreCase("cancel")){
			remove();
		}else{
			register();
		}
		
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
