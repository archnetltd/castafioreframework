package org.castafiore.finance.worksheet.columns.filter;

import java.util.Map;

import javax.naming.event.EventDirContext;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.ui.scripting.EXXHTMLFragment;

public class OSDateColumnFilter extends EXXHTMLFragment implements Event{

	public OSDateColumnFilter() {
		super("filter", "OSWorksheet/date/filter.xhtml");
		
		addChild(new EXContainer("okbutton", "button").setText("OK").addEvent(this, CLICK));
		
		addChild(new EXContainer("cancelbutton", "button").setText("Cancel").addEvent(this, CLICK));
		
		addChild(new EXSelect("month", new DefaultDataModel<Object>()
				.addItem("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December")));
		
		addChild(new EXInput("year"));
		
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		if(container.getName().equalsIgnoreCase("cancelbutton")){
			container.getParent().getParent().setRendered(false);
			container.getParent().remove();
		}else{
			container.getParent().getParent().setRendered(false);
			container.getParent().remove();
		}
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
