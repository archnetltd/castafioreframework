package org.castafiore.site.wizard;

import java.util.Map;

import org.castafiore.security.User;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXPassword;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.StringUtil;

public class EXUserInfo extends EXXHTMLFragment implements Event{

	
	public EXUserInfo(String name) {
		super(name, "webos/gs/UserInfo.xhtml");
		addChild(new EXContainer("errormessage", "div").addClass("ui-state-error").setStyle("padding", "0 10px").setDisplay(false));
		String[]names = new String[]{"cousername", "copassword", "firstName", "lastName", "email", "addressLine1", "addressLine2", "companyName", "city", "phone", "fax"};
		
		for(String s : names){
			if(s.equals("copassword")){
				addChild(new EXPassword(s));
			}else{
				EXInput in = new EXInput(s);
				addChild(in);
				if(in.getName().equals("cousername") || in.getName().equals("email")){
					in.addEvent(this, Event.BLUR);
				}
			}
			
			
		}
		
		getChild("addressLine1").setStyle("width", "400px");
		getChild("addressLine2").setStyle("width", "400px");
		getChild("city").setStyle("width", "400px");
		
	}
	
	
	public String getValue(String field){
		
		return ((StatefullComponent)getChild(field)).getValue().toString();
	}
	
	private boolean valField(String field){
		StatefullComponent stf = ((StatefullComponent)getChild(field));
		if(!StringUtil.isNotEmpty(stf.getValue().toString())){
			stf.addClass("ui-state-error");
			
			setErrorMessage("<label>Please fill in the required field</label>");
			
			return false;
		}else{
			if(field.equals("cousername")){
				try{
					User u = SpringUtil.getSecurityService().loadUserByUsername(stf.getValue().toString());
					if(u != null){
						setErrorMessage(getChild("errormessage").getText() + "<br><label>This username already exists in our database. Please choose another one</label>");
						return true;
					}else{
						getChild("errormessage").setDisplay(false);
						
					}
				}catch(Exception e){
					getChild("errormessage").setDisplay(false);
				}
			}else if( field.equals("email")){
				if(stf.getValue().toString().contains("@") && stf.getValue().toString().contains(".")){
					getChild("errormessage").setDisplay(false);
					
				}else{
					setErrorMessage("<label>Please enter a valid email address</label>");
				}
			}
			return true;
		}
	}
	
	public boolean validate(){
		String[] fields = new String[]{"cousername", "copassword", "firstName", "lastName", "email", "addressLine1", "addressLine2", "companyName", "phone"};
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
	
	public void setErrorMessage(String message){
		getChild("errormessage").setText( message).setDisplay(true);
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		String value = ((EXInput)container).getValue().toString();
		if(StringUtil.isNotEmpty(value)){
			if(container.getName().equals("cousername")){
				try{
					User u = SpringUtil.getSecurityService().loadUserByUsername(value);
					if(u != null){
						setErrorMessage("<label>This username already exists in our database. Please choose another one</label>");
						return true;
					}else{
						getChild("errormessage").setDisplay(false);
						
					}
				}catch(Exception e){
					getChild("errormessage").setDisplay(false);
				}
			}else if(container.getName().equals("email")){
				if(value.contains("@") && value.contains(".")){
					getChild("errormessage").setDisplay(false);
					
				}else{
					setErrorMessage("<label>Please enter a valid email address</label>");
				}
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
