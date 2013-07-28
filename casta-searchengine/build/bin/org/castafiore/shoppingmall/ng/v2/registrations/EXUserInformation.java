package org.castafiore.shoppingmall.ng.v2.registrations;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.castafiore.security.User;
import org.castafiore.shoppingmall.ng.v2.EXAccordeonPanel;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.dynaform.EXFieldSet;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXPassword;
import org.castafiore.utils.StringUtil;

public class EXUserInformation extends EXContainer implements Event{
	
	

	public EXUserInformation(String name) {
		
		super(name, "div");
		
		//EXFieldSet fs = new EXFieldSet("up", "Choose username and password", true);
		
		//addChild(fs);
		
		EXFieldSet fs1 = new EXFieldSet("fs", "Enter Company information", true);
		fs1.setStyle("border", "none");
		fs1.getChild("thead").setStyle("display", "none");
		fs1.addField("Username : ",new EXInput("user.username"));
		fs1.addField("Password : ",new EXPassword("user.password"));
		fs1.getField("user.username").addEvent(this, BLUR);
		fs1.getField("user.password").addEvent(this, BLUR);
		
		fs1.addField("First name :", new EXInput("user.firstName"));
		fs1.addField("Last name :", new EXInput("user.lastName"));
		
		fs1.addField("Company name :", new EXInput("company.name"));
		fs1.getField("company.name").addEvent(this, BLUR);
		
		fs1.addField("Email :", new EXInput("company.email"));
		fs1.getField("company.email").addEvent(this, BLUR);
		
		fs1.addField("Address line 1:", new EXInput("company.addressline1"));
		fs1.getField("company.addressline1").addEvent(this, BLUR);
		
		fs1.addField("Address line 2:", new EXInput("company.addressline2"));
		
		
		fs1.addField("City :", new EXInput("company.city"));
		
		
		fs1.addField("Postal Code :", new EXInput("company.postcode"));
		
		
		fs1.addField("Mobile :", new EXInput("company.mobile"));
		fs1.getField("company.mobile").addEvent(this, BLUR);
		
		fs1.addField("Land line :", new EXInput("company.telephone"));
		
		addChild(fs1);
		
		
		//EXFieldSet fs3 = new EXFieldSet("dfs", "Enter contact information", true);
		
		
		//addChild(fs3);
		
	}
private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	private Pattern pattern = Pattern.compile(EMAIL_PATTERN);;

	
	
	
	protected void setMessage(String message){
		getAncestorOfType(EXAccordeonPanel.class).setMessage(message, 0);
	}
	
	
	
	
	public boolean validateEmpty(StatefullComponent field){
		String value = field.getValue().toString();
		if(StringUtil.isNotEmpty(value)){
			return true;
		}else{
			setMessage("Please enter a value for this field");
			return false;
		}
	}
	
	
	public boolean validateEmail(StatefullComponent field){
		String email = field.getValue().toString();
		
		if(!StringUtil.isNotEmpty(email)){
			return true;
		}
		
		Matcher matcher = pattern.matcher(email);
		if(matcher.matches()){
			try{
				if(SpringUtil.getSecurityService().loadUserByEmail(email) != null){
					setMessage("This email address already exists in our database. Please choose another one");
					return false;
				}
			}catch(Exception e){
				
			}
			
			return true;
		}else {
			setMessage("Please enter a valid email address");
			return false;
			
		}
	}
	
	
	public boolean validateUsername(StatefullComponent field){
		String username = field.getValue().toString();
		if(StringUtil.isNotEmpty(username)){
			try{
				User user = SpringUtil.getSecurityService().loadUserByUsername(username);
				if(user != null){
					
					setMessage("This username is already used. Please try another one");
					return false;
				}
			}catch(Exception e){
				
			}
		}else{
			
			setMessage("Please choose a username first");
			return false;
		}
		return true;
	}


	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}


	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		if(container instanceof StatefullComponent){
			boolean valid = validate((StatefullComponent)container);
			if(valid){
				container.removeClass("ui-state-eror");
			}else{
				container.addClass("ui-stat-error");
				request.put("iid", container.getId());
			}
		}
		
		return true;
	}


	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		if(request.containsKey("iid")){
			container.mergeCommand(new ClientProxy("#" + request.get("iid")).addMethod("focus"));
		}
		
	}
	public boolean validate(StatefullComponent field){
		if(field.getName().equalsIgnoreCase("user.username")){
			return validateUsername(field);
		}else if(field.getName().equalsIgnoreCase("user.email")){
			return validateEmail(field);
		}else{
			return validateEmpty(field);
		}
	}

}
