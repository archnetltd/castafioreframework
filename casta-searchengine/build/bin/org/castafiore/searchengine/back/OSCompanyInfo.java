package org.castafiore.searchengine.back;

import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.ui.Container;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.scripting.EXXHTMLFragment;

public class OSCompanyInfo extends EXXHTMLFragment implements EventDispatcher{

	public OSCompanyInfo(String name) {
		super(name, "templates/back/OSCompanyInfo.xhtml");
		addChild(new EXInput("companyName"));
		addChild(new EXInput("companyNumber"));
		addChild(new EXInput("contactId"));
		addChild(new EXInput("companyPhone"));
		addChild(new EXInput("companyMobile"));
		addChild(new EXInput("companyEmail"));
		addChild(new EXInput("companyType"));
		
		addChild(new EXContainer("save", "button").setText("Save").addEvent(DISPATCHER, Event.CLICK));
		addChild(new EXContainer("cancel", "button").setText("Cancel").addEvent(DISPATCHER, Event.CLICK));
	}

	@Override
	public void executeAction(Container source) {
		// TODO Auto-generated method stub
		
	}

}
