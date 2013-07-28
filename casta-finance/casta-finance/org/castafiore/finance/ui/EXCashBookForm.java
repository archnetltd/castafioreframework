package org.castafiore.finance.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXAutoComplete;
import org.castafiore.ui.ex.form.EXDatePicker;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.button.EXButton;

public class EXCashBookForm extends EXDynaformPanel implements Event{

	public EXCashBookForm(String name) {
		super(name, "Enter an expense entry");
		addField("Date :", new EXDatePicker("date"));
		addField("Type", new EXAutoComplete(name, "", getExpenseTypes()));
		addField("Description :", new EXInput("description"));
		addField("Amount :", new EXInput("amount"));
		
		addButton(new EXButton("save", "Save"));
		addButton(new EXButton("cancel", "Cancel"));
		getDescendentByName("cancel").addEvent(CLOSE_EVENT, Event.CLICK);
	}
	
	public List<String> getExpenseTypes(){
		return new ArrayList<String>();
	}
	
	

	@Override
	public void ClientAction(ClientProxy container) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
