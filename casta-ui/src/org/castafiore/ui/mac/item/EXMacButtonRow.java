package org.castafiore.ui.mac.item;

import org.castafiore.ui.Container;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;

public class EXMacButtonRow extends EXContainer implements MacColumnItem {

	public EXMacButtonRow(String name) {
		super(name, "li");
	}
	
	public EXMacButtonRow addButton(String name, String title, String iconCss, Event event){
		Container button = new EXContainer(name, "div").setText("<span class=\"ui-icon "+iconCss+"\">").addEvent(event, Event.CLICK);
		addChild(button);
		return this;
	}
	
	public Container getButton(String name){
		return getChild(name);
	}

}
