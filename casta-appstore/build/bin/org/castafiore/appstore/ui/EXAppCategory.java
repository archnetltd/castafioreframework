package org.castafiore.appstore.ui;

import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.scripting.EXXHTMLFragment;

public class EXAppCategory extends EXXHTMLFragment implements Event{

	public EXAppCategory(String name) {
		super(name, "templates/EXAppCategory.xhtml");
		addChild(new EXContainer("title", "label").setStyle("font-weight", "bold"));
		addEvent(this, CLICK);
	}
	
	
	public void setAppCategory(String category){
		getChild("title").setText(category);
	
	}


	public void showCategory(){
		String category = getChild("title").getText();
		getAncestorOfType(EXAppStore.class).showCategory(category);
	}
	
	
	@Override
	public void ClientAction(ClientProxy container) {
		container.mask(container.getAncestorOfType(EXAppStore.class)).makeServerRequest(this);
		
	}


	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		showCategory();
		return true;
	}


	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
