package org.castafiore.shoppingmall.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXTextArea;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.ui.interceptors.Interceptor;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.StringUtil;

public class MallFormInterceptor implements Interceptor, Event{

	@Override
	public void ClientAction(ClientProxy container) {
	
		container.makeServerRequest(this);
	}
	
	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		if(container.getTag().equalsIgnoreCase("button")){
			
			try{
				
				if(StringUtil.isNotEmpty(container.getAttribute("validate"))){
					List<String> messages = validateForm(container.getAncestorOfType(MallForm.class));
					if(messages.size() > 0){
						setErrorMessage(container.getAncestorOfType(MallForm.class), messages);
					}else{
						container.getAncestorOfType(MallForm.class).getClass().getMethod(container.getName(), Container.class).invoke(container.getAncestorOfType(MallForm.class),container);
					}
				}else{
					container.getAncestorOfType(MallForm.class).getClass().getMethod(container.getName(), Container.class).invoke(container.getAncestorOfType(MallForm.class),container);
				}
			}catch(Exception e){
				e.printStackTrace();
				throw new UIException("Please add a method with name " + container.getName() + " in class " + container.getAncestorOfType(MallForm.class).getClass().getName() + " with 1 parameter of type " + Container.class.getName() + " to execute the button " + container.getName() );
			}
			
		}else{
			String message = validate(container.getName(), container.getAncestorOfType(MallForm.class));
			if(message != null)
				request.put("invalid", "true");
		}
		return true;
	}
	
	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
	}
		
	@Override
	public Interceptor next() {
		return null;
	}

	@Override
	public boolean onRender(Container container) {
		
		container.addClass("EXMallForm");
		if(container.getChild("error-ctn") == null)
			container.addChild(new EXContainer("error-ctn", "div").addChild(new EXContainer("error", "div").addClass("error").addChild(new EXContainer("ul", "ul"))).setDisplay(false));
		addEvents(container);
		return false;	
	}
	
	public List<String> validateForm(Container form){
		
		List<Container> result =new ArrayList<Container>();
		List<String> messages = new ArrayList<String>();
 		ComponentUtil.getDescendentsOfType(form, result, StatefullComponent.class);
		for(Container c : result){
			String message = validate(c.getName(), form);
			if(message != null){
				messages.add(message);
			}
		}
		return messages;
	}
	
	public void setErrorMessage(Container form, List<String> messages){
		Container error = form.getDescendentByName("error");
		Container ul = error.getChildByIndex(0);
		ul.getChildren().clear();
		ul.setRendered(false);
		for(String message : messages){
			ul.addChild(new EXContainer("li", "li").setText(message));
		}
		error.getParent().setDisplay(true);
		
		
	}
	
	public void addEvents(Container form){
		
		for(Container c : form.getChildren()){
			
			if(c.hasEvent(this.getClass(), BLUR) || c.hasEvent(this.getClass(), CHANGE) || c.hasEvent(this.getClass(), CLICK)){
				continue;
			}
				
			if(c instanceof EXInput || c instanceof EXTextArea)
				c.addEvent(this, Event.BLUR);
			else if(c instanceof EXSelect){
				c.addEvent(this, Event.CHANGE);
			}else if(c.getTag().equalsIgnoreCase("button")){
				if(c.getName().equals("cancel")){
					
					c.addEvent(EXPanel.CLOSE_EVENT, CLICK);
					
				}else
					c.addEvent(this, CLICK);
			}
		}
	}
	
	public String validate(String fieldName, Container form){
		StatefullComponent input = (StatefullComponent)form.getChild(fieldName);
		String valType = input.getAttribute("validation-method");
		boolean valid = true;
		if(StringUtil.isNotEmpty(valType)){
			if(valType.equalsIgnoreCase("empty")){
				valid = empty(input);
			}else if(valType.equalsIgnoreCase("null")){
				valid = Null(input);
			}else{
				try{
					valid = (Boolean)form.getClass().getMethod(valType, StatefullComponent.class).invoke(form,input);
				}catch(Exception e){
					e.printStackTrace();
					throw new UIException("Please add a method with name " + valType + " in class " + form.getClass().getName() + " with 1 parameter of type " + StatefullComponent.class.getName() + " to validate the field " + fieldName );
				}
			}
		}
		String message = null;
		if(valid){
			message = onValid(input, form);
		}else{
			message = onInvalid(input, form);
		}
		
		return message;
	}
	
	protected String onInvalid(StatefullComponent component, Container form){
		String message = component.getAttribute("error-message");
		if(message == null || message.trim().length() <=0){
			message = "??" + form.getName() + "." + component.getName() + "??";
		}
		
		form.getChild("error-ctn").setDisplay(true);
		component.addClass("error");
		
		Container ul = form.getDescendentByName("error").getChildByIndex(0);
		boolean found = false;
		for(Container c : ul.getChildren()){
			if(c.getText().equalsIgnoreCase(message)){
				found = true;
			}
		}
		
		if(!found){
			ul.addChild(new EXContainer("li", "li").setText(message));
		}
		
		return message;
	}
	
	
	protected String onValid(StatefullComponent component, Container form){
		
		component.removeClass("error");
		
		String message = component.getAttribute("error-message");
		if(message == null || message.trim().length() <=0){
			message = "??" + form.getName() + "." + component.getName() + "??";
		}
		
		Container ul = form.getDescendentByName("error").getChildByIndex(0);
		boolean found = false;
		for(Container c : ul.getChildren()){
			if(c.getText().equalsIgnoreCase(message)){
				c.remove();
				found = true;
				break;
			}
		}
		
		if(found){
			ul.setRendered(false);
		}
		
		if(ul.getChildren().size() ==0){
			form.getChild("error-ctn").setDisplay(false);
		}
		
		return null;
	}
	
	public boolean empty(StatefullComponent component){
		Object value = component.getValue();
		return value != null && value.toString().trim().length() >0;
	}
	
	public boolean Null(StatefullComponent component){
		return empty(component);
	}
	

}
