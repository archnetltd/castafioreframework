package org.castafiore.shoppingmall.ng.v2.registrations;

import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXUpload;
import org.castafiore.ui.scripting.EXXHTMLFragment;

public class EXCategories extends EXXHTMLFragment implements Event{

	public EXCategories(String name) {
		super(name, "templates/v2/registrations/EXCategories.xhtml");
		
	//	addChild(new EXUpload("company.logo"));
	//	addChild(new EXUpload("company.banner"));
		
addChild(new EXCompanyCategories());
		
		addChild(new EXContainer("addCategory", "button").setText("Add New").addEvent(this, CLICK));
	}

	
	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		container.getParent().getDescendentOfType(EXCompanyCategories.class).addRawLine();
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}
}
