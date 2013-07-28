package org.castafiore.shoppingmall.accounting.cashbook;

import java.util.Map;

import org.castafiore.finance.worksheet.OSWorksheetColumnRenderer;
import org.castafiore.finance.worksheet.columns.filter.OSDateColumnFilter;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;

public class CashBookColumnRenderer implements OSWorksheetColumnRenderer, Event{

	@Override
	public Container getColumnCell(String label, int index, boolean onPageChange) {
		return new EXContainer(label, "div").setText(label).setAttribute("col-index", index + "").addEvent(this, CLICK);
	}
	
	
	public void showDateFilter(Container source){
		if(source.getChild("filter") == null)
			source.addChild(new OSDateColumnFilter());
		
	}


	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}


	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		if("1".equals(container.getAttribute("col-index")))
			showDateFilter(container);
		
		return true;
	}


	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
