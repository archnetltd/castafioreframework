package org.castafiore.finance.worksheet.inputs;

import java.util.Map;

import org.castafiore.finance.worksheet.OSWorksheetCell;
import org.castafiore.finance.worksheet.OSWorksheetTypes;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.form.EXLabel;
import org.castafiore.ui.js.Function;
import org.castafiore.ui.js.JMap;
import org.castafiore.ui.js.Var;

public class EXOSDateStringInput extends EXLabel implements OSWorksheetCell, Event {

	public EXOSDateStringInput(String name) {
		super(name, "");
		setStyle("position", "relative");
		addEvent(this, READY);
	}
	
	

	@Override
	public OSWorksheetTypes getSupportedType() {
		return OSWorksheetTypes.DATE;
	}

	@Override
	public void ClientAction(ClientProxy proxy) {
		JMap options = new JMap();
		
		ClientProxy body =proxy.clone().makeServerRequest(new JMap().put("datesel", new Var("dateText")),this);
		options.put("onSelect", body, "dateText");
		proxy.addMethod("datepicker", options);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		String date = request.get("datesel");
		this.getParent().setText(date);
		this.remove();
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
