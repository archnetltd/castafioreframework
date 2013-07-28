/*
 * Copyright (C) 2007-2010 Castafiore
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */
 package org.castafiore.designer.config.ui;

import java.util.List;
import java.util.Map;

import org.castafiore.designer.designable.ConfigValues;
import org.castafiore.designer.events.GroovyEvent;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXTextArea;
import org.castafiore.ui.ex.form.list.DataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.ui.js.JMap;
import org.castafiore.ui.js.Var;
import org.castafiore.utils.EventUtil;
import org.castafiore.utils.JavascriptUtil;

public class EXEventConfigForm extends EXContainer implements ConfigPanel , Event{

	private Container container;

	public EXEventConfigForm() {
		super("", "div");
		
		EXSelect select = new EXSelect("eventType", new EventDataModel());
		select.addEvent(this, Event.CHANGE);
		select.addClass("evt-combo");
		EXContainer div1 = new EXContainer("", "div");
		div1.addClass("evt");
		addChild(div1);
		div1.addChild(select);
		EXContainer div2 = new EXContainer("div2", "div");
		div2.addClass("evt-alternate");
		addChild(div2);
		
		
		
//		EXContainer div3 = new EXContainer("", "div");
//		div3.addClass("evt-alternate");
//		addChild(div3);
//		Container btn = new EXContainer("save", "button").setText("Save").setAttribute("style", "position: relative; top: -8px; left: 20px; height: 22px; width: 100px; line-height: 11px;");
//		btn.addEvent(this, CLICK);
//		div3.addChild(btn);
	}

	public static class EventDataModel implements DataModel {

		public int getSize() {
			return EventUtil.getEvents().size();
		}

		public Object getValue(int index) {
			return EventUtil.getEvents().get(index);
		}

	}

	public void applyConfigs() {
		// TODO Auto-generated method stub

	}

	public Container getContainer() {
		return container;
	}

	public void setContainer(Container container, String[] requiredAttributes, ConfigValues vals) {

		this.container = container;
		EXTextArea eta = new EXTextArea("eventcode");
		//eta.setStyle("width", "425px");
		eta.addEvent(this, Event.BLUR);
		//Event.BLUR
		eta.addClass("evt-textarea");
		Container div2 = getChild("div2");
		div2.getChildren().clear();
		div2.setRendered(false);
		div2.addChild(eta);
		setRendered(false);
		refreshTemplate();

	}
	
	public void applyTemplate(){
		EXSelect eventType = getDescendentOfType(EXSelect.class);

		String value = eventType.getValue().toString();

		int currentType = EventUtil.getEventType(value);
		List<Event> events = container.getEvents().get(currentType);
		String template  = getDescendentOfType(EXTextArea.class).getValue().toString();
		if(template.trim().length() > 0){
			if(events != null){
				for (Event evt : events) {
					if (evt instanceof GroovyEvent) {
						((GroovyEvent) evt).setTemplate(template);
						
						return;
					}
				}
			}
			
			container.addEvent(new GroovyEvent(template, currentType), currentType);
			container.setRendered(false);
		}else{
			container.removeEvent(GroovyEvent.class, currentType);
			container.setRendered(false);
		}
	}

	public String refreshTemplate() {
		EXSelect eventType = getDescendentOfType(EXSelect.class);
		
		
		List<String> lEvents = EventUtil.getEvents();
		for(int i = 0; i < lEvents.size(); i ++){
			int iEvent = EventUtil.getEventType(lEvents.get(i));
			if(container.hasEvent(GroovyEvent.class, iEvent)){
				eventType.getChildByIndex(i).setStyle("font-weight", "bold");
			}else{
				eventType.getChildByIndex(i).setStyle("font-weight", "normal");
			}
		}
		
		String value = eventType.getValue().toString();
		int iEvent = EventUtil.getEventType(value);
		List<Event> events = container.getEvents().get(iEvent);
		if(events != null){
			for (Event evt : events) {
				if (evt instanceof GroovyEvent) {
					String template = ((GroovyEvent) evt).getTemplate();
					getDescendentOfType(EXTextArea.class).setValue(template);
					return template;
				}
			}
		}

		getDescendentOfType(EXTextArea.class).setValue("");
		return "";
		//setRendered(false);
		
		
	}
	
	
//	public void onReady(ClientProxy proxy){
//		proxy.appendJSFragment("editAreaLoader.init({\"id\":\""+ getDescendentOfType(EXTextArea.class).getId()+"\",\"syntax\":\"css\",\"start_highlight\":true,\"display\" : \"later\",\"allow_resize\":\"both\",\"language\":\"en\",\"allow_toggle\":false})");
//	}


	@Override
	public void ClientAction(ClientProxy container) {
		JMap options = new JMap();//.put("data", new Var("editAreaLoader.getValue(\""+getDescendentOfType(EXTextArea.class).getId()+"\")"));
		container.makeServerRequest(options, this);
		
	}


	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		try{
			
			//getDescendentOfType(EXTextArea.class).setValue(request.get("data"));
			if(container instanceof EXSelect){
				//EXEventConfigForm form = container.getAncestorOfType(EXEventConfigForm.class);
				
				String tpl = refreshTemplate();
				request.put("tpl", JavascriptUtil.javaScriptEscape( tpl));
			}else{
				applyTemplate();
			}
			}catch(Exception e){
				throw new UIException(e);
			}
		return true;
	}


	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
//		if(request.containsKey("tpl")){
//			container.appendJSFragment("editAreaLoader.setValue(\""+getDescendentOfType(EXTextArea.class).getId()+"\",\"  "+request.get("tpl")+"\")");
//		}
		
	}

}
