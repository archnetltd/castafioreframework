package org.castafiore.finance.worksheet;

import java.util.Map;

import org.castafiore.finance.worksheet.inputs.EXOSStringInput;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;

public class EXOSWorksheetROCell extends EXContainer implements Event {
	private  OSWorksheetCell editCell = null;

	public EXOSWorksheetROCell(String name, String value, OSWorksheetCell edit) {
		super(name, "div");
		setText(value);
		this.editCell = edit;
		if(editCell == null){
			editCell = new EXOSStringInput("", "");
		}
		editCell.addEvent(this, Event.BLUR);
		addEvent(this, Event.DOUBLE_CLICK);
		
	}

	public EXOSWorksheetROCell(String name, String value) {
		this(name, value, null);
	}
	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		if(container instanceof EXOSWorksheetROCell){
			String text = getText();
			editCell.setRendered(false);
			editCell.setValue(text);
			addChild(editCell);
			setText("");
		}else{
			setText(editCell.getValue().toString());
			editCell.remove();
		}
		
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
