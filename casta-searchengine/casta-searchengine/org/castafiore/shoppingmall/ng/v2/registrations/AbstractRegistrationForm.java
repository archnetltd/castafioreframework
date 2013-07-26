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
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.StringUtil;

public abstract class AbstractRegistrationForm extends EXXHTMLFragment implements Event{
	
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	private Pattern pattern = Pattern.compile(EMAIL_PATTERN);;

	public AbstractRegistrationForm(String name, String templateLocation) {
		super(name, templateLocation);
		
	}
	
	
	protected void setMessage(String message){
		getAncestorOfType(EXAccordeonPanel.class).setMessage(message, 0);
	}
	
	
	public abstract boolean validate(StatefullComponent field);
	
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

}
