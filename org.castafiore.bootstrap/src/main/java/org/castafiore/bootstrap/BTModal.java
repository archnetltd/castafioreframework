package org.castafiore.bootstrap;

import org.castafiore.ui.Container;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.panel.Panel;
import org.castafiore.ui.js.Function;


 
public class BTModal extends EXContainer implements Panel{
	
	private final static int ON_SHOW = Event.MISC +1;
	
	private final static int ON_SHOWN = Event.MISC +2;
	
	private final static int ON_HIDE = Event.MISC + 3;
	
	private final static int ON_HIDDEN = Event.MISC + 4;

	public BTModal(String name, String title) {
		super(name, "div");
		addClass("modal").addClass("hide").addClass("fade");
		Container header = new EXContainer("header", "div");
		header.addClass("modal-header");
		addChild(header);
		header.addChild(new EXContainer("close", "button").setAttribute("data-dismiss", "modal").setAttribute("aria-hidden", "true").setText("X"));
		header.getChild("close").setReadOnlyAttribute("type", "button");
		
		header.addChild(new EXContainer("title", "h3").setText(title));
		
		addChild(new EXContainer("body-container", "div").addClass("modal-body"));
		
		addChild(new EXContainer("footer", "div").addClass("modal-footer"));
		setAttribute("show", "true");
	}
	
	
	public Panel setShow(boolean show){
		setAttribute("show", show + "");
		return this;
	}
	
	public Panel setKeyboard(boolean keyboard){
		setAttribute("keyboard", keyboard + "");
		return this;
	}
	
	public Panel setOnShow(Event onShow){
		confEvent(onShow, ON_SHOW);
		return this;
	}
	
	public Panel setOnShown(Event onShown){
		confEvent(onShown, ON_SHOWN);
		return this;
	}
	
	public Panel setOnHidden(Event onHidden){
		confEvent(onHidden, ON_HIDDEN);
		return this;
	}
	
	
	public Panel setOnHide(Event onHide){
		
		confEvent(onHide , ON_HIDE);
		return this;
	}
	
	private void confEvent(Event evt,int type){
		if(evt == null){
			if(getEvents().containsKey(type))getEvents().remove(type);
		}else{
			addEvent(evt, type);
		}
	}
	


	public Panel setTitle(String title) {
		getChild("header").getChild("title").setText(title);
		return this;
	}

	public Panel setBody(Container container) {
		 Container b = getChild("body-container");
		 b.getChildren().clear();
		 b.setRendered(false);
		 b.addChild(container);
		 return this;
	}

	public Container getBody() {
		return getChild("body-container").getChildByIndex(0);
	}

	public Panel setShowHeader(boolean showHeader) {
		getChild("header").setDisplay(showHeader);
		return this;
	}

	public Panel setShowFooter(boolean display) {
		getChild("footer").setDisplay(display);
		return this;
	}

	public Panel setShowCloseButton(boolean b) {
		getChild("header").getChild("close").setDisplay(b);
		return this;
	}


	public void onReady(ClientProxy proxy) {
		
		super.onReady(proxy);
		
		
		putEvent(ON_HIDDEN, proxy);
		putEvent(ON_HIDE, proxy);
		putEvent(ON_SHOW, proxy);
		putEvent(ON_SHOWN, proxy);
	}
	
	private void putEvent(int type, ClientProxy main){
		if(getEvents().containsKey(type)){
			Event e =  getEvents().get(type).get(0);
			ClientProxy clone = main.clone();
			e.ClientAction(clone);
			main.addMethod("on", "hide", new Function(clone));
		}
	}
	
	

}
