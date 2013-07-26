package org.castafiore.searchengine;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;

public class EXBackButton extends EXContainer implements EventDispatcher{

	public EXBackButton(String name, Class<?> returnTo) {
		super(name, "button");
		setAttribute("returnTo", returnTo.getName());
		addEvent(DISPATCHER, Event.CLICK);
		setText("Back");
		
	}

	@Override
	public void executeAction(Container source) {
		try{
			String clazz = getAttribute("returnTo");
			getAncestorOfType(EXMall.class).getWorkingSpace().viewOnly(Thread.currentThread().getContextClassLoader().loadClass(clazz));
		}catch(Exception e){
			throw new UIException(e);
		}
		
	}

}
