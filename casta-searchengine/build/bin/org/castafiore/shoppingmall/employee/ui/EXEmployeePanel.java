package org.castafiore.shoppingmall.employee.ui;

import java.util.Map;

import org.castafiore.shoppingmall.employee.Employee;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.ui.tabbedpane.EXTabPanel;

public class EXEmployeePanel extends EXPanel implements Event{

	public EXEmployeePanel(String name,Employee employee) {
		super(name, "Employee registration form");
		EXTabPanel tpanel = new EXTabPanel("employee", new EXEmployeeViewModel(employee));
		setBody(tpanel);
		
		getFooterContainer().addChild(new EXContainer("save", "button").setText("Save").addEvent(this, CLICK));
		getFooterContainer().addChild(new EXContainer("cancel", "button").setText("Cancel").addEvent(CLOSE_EVENT, Event.CLICK));
		setStyle("width", "700px").setStyle("z-index", "6000");
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
