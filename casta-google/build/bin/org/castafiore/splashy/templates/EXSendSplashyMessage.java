package org.castafiore.splashy.templates;

import java.util.HashMap;
import java.util.Map;

import org.castafiore.designer.Studio;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXTextArea;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.utils.StringUtil;

public class EXSendSplashyMessage extends EXXHTMLFragment implements Event{

	public EXSendSplashyMessage(String name) {
		super(name, ResourceUtil.getDownloadURL("classpath", "org/castafiore/splashy/templates/EXSendSplashyMessage.xhtml"));
		addChild(new EXContainer("success", "div").addClass("success").setText("Contact form submitted! <strong><br>We will be in touch soon.</strong>"));
		addChild(new EXInput("name", "Name"));
		addChild(new EXInput("email", "E-mail"));
		addChild(new EXInput("telephone", "Telephone number"));
		addChild(new EXTextArea("message", "Message"));
		
		addChild(new EXContainer("reset", "a").addClass("button-3").setText("reset").addEvent(this, CLICK));
		addChild(new EXContainer("submit", "a").addClass("button-3").setText("submit").addEvent(this, CLICK));
		
		String[] as = new String[]{"name", "email", "telephone","message"};
		for(String s : as){
			addChild(new EXContainer(s + "_empty", "span").addClass("empty").setText("*This field is required"));
		}
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		if(container.getName().equalsIgnoreCase("submit")){
			String name = ((StatefullComponent)getChild("name")).getValue().toString();
			String email = ((StatefullComponent)getChild("email")).getValue().toString();
			String telephone = ((StatefullComponent)getChild("telephone")).getValue().toString();
			String message = ((StatefullComponent)getChild("message")).getValue().toString();
			
			boolean valid = true;
			if(!StringUtil.isNotEmpty(name)){
				getChild("name_empty").setStyle("display", "block");
				valid= false;
			}else{
				getChild("name_empty").setStyle("display", "none");
			}
			
			if(!StringUtil.isNotEmpty(email)){
				getChild("email_empty").setStyle("display", "block");
				valid= false;
			}else{
				getChild("email_empty").setStyle("display", "none");
			}
			
			if(!StringUtil.isNotEmpty(telephone)){
				getChild("telephone_empty").setStyle("display", "block");
				valid= false;
			}else{
				getChild("telephone_empty").setStyle("display", "none");
			}
			
			if(!StringUtil.isNotEmpty(message)){
				getChild("message_empty").setStyle("display", "block");
				valid= false;
			}else{
				getChild("message_empty").setStyle("display", "none");
			}
			
			if(valid){
				//
				try{
					
				Studio.sendMail("/root/users/splashy/mailtemplate.xhtml", "Contact from WEB Splashy", email, "kureem@gmail.com", new HashMap<String, String>());
				getChild("success").setDisplay(true);
				}catch(Exception e){
					throw new UIException(e);
				}
			}
		}else{
			String[] as = new String[]{"name", "email", "telephone","message"};
			for(String s : as){
				((StatefullComponent)getChild(s)).setValue("");
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
