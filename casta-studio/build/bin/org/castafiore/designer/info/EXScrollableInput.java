package org.castafiore.designer.info;

import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.utils.StringUtil;

public class EXScrollableInput extends EXInput{
	
	private Container container;
	
	private String styleName ;

	public EXScrollableInput(String styleName, Container item) {
		super(styleName);
		this.container = item;
		this.styleName = styleName;
		setContainer(item);
		
		addEvent(new Event(){

			@Override
			public void ClientAction(ClientProxy container) {
				container.appendJSFragment("var me = $(this);if(event.keyCode == 40){me.val(me.val() -1);}else if(event.keyCode == 38){me.val(parseInt(me.val()) +1);}else if(event.keyCode == 33){me.val(parseInt(me.val()) +16);}else if(event.keyCode == 34){me.val(me.val() -16);}");
				
			}

			@Override
			public boolean ServerAction(Container container,
					Map<String, String> request) throws UIException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void Success(ClientProxy container,
					Map<String, String> request) throws UIException {
				// TODO Auto-generated method stub
				
			}
			
		}, Event.KEY_PRESS);
		
		
		
		addEvent(new Event(){

			@Override
			public void ClientAction(ClientProxy container) {
				container.makeServerRequest(this);
				
			}

			@Override
			public boolean ServerAction(Container container,
					Map<String, String> request) throws UIException {
				EXScrollableInput input = container.getAncestorOfType(EXScrollableInput.class);
				String styleVal = input.getValue().toString().trim();
				if(StringUtil.isNotEmpty(styleVal)){
					input.container.setStyle(input.styleName.toLowerCase(), styleVal + "px");
					
				}else{
					input.container.setStyle(input.styleName.toLowerCase(), styleVal);
				}
				request.put("selid", input.container.getId());
				return true;
			}

			@Override
			public void Success(ClientProxy container,
					Map<String, String> request) throws UIException {
				container.appendJSFragment("remakeSelector('"+request.get("selid")+"');");
				
			}
			
			
		}, Event.KEY_UP);
		
	}

	public void setContainer(Container item){
		try{
			setValue(item.getStyle(styleName.toLowerCase()).replace("px", "").trim());
			this.container = item;
		}catch(Exception e){
			setValue("");
		}
	}
	
	

}
