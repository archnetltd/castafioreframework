package org.castafiore.site;

import org.castafiore.site.events.ChangePageEvent;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;

public class YoudoMenu extends EXContainer {
	
	private final static String[] MENU_ITEMS = new String[]{"Home", "About", "Get started", "Services", "Useful links", "Contact"};

	public YoudoMenu() {
		super("menu", "ul");
		addClass("navigation");
		
		for(String s : MENU_ITEMS){
			addChild(new EXContainer("li", "li").addChild(new EXContainer(s, "a").setAttribute("href", "#").setText(s).addEvent(new ChangePageEvent(), Event.CLICK)));
		}
		
	}

}
