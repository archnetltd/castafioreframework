package org.castafiore.accounting.ui;

import java.text.SimpleDateFormat;
import java.util.Map;


import org.castafiore.accounting.CashBookEntry;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.table.CellRenderer;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.wfs.Util;

public class CashBookEntryCellRenderer implements CellRenderer, Event{

	@Override
	public Container getComponentAt(int row, int column, int page,
			TableModel model, EXTable table) {
		CashBookEntry p = (CashBookEntry)model.getValueAt(column, row, page);
		String cDir = ((CashBookEntryModel)model).getCashBookDir();
		Container result = new  EXContainer("span", "span");
		if(column == 0){
			return result.setText(p.getDateOfTransaction() != null ? new SimpleDateFormat("dd/MMM/yyyy").format(p.getDateOfTransaction()) : "" );
		}else if(column == 1){
			return result.setText(p.getAccount() != null ?  p.getAccount().getTitle() : "UnknownAccount" );
		}else if(column == 2){
			result = new EXContainer("span", "a").setAttribute("cashbookdir", cDir).setAttribute("href", "#").setAttribute("path", p.getAbsolutePath()).addEvent(this, Event.CLICK);
			return result.setText(p.getTitle());
		}else if(column == 3){
			return result.setText(p.getTotal() !=null? p.getTotal().toString():"0");
		}
		
		return result;
	}

	@Override
	public void onChangePage(Container result, int row, int column,
			int page, TableModel model, EXTable table) {
		
		CashBookEntry p = (CashBookEntry)model.getValueAt(column, row, page);
		
		
		if(column == 0){
			result.setText(p.getDateOfTransaction() != null ? new SimpleDateFormat("dd/MMM/yyyy").format(p.getDateOfTransaction()) : "" );
		}else if(column == 1){
			result.setText(p.getAccount() != null ?  p.getAccount().getTitle() : "UnknownAccount" );
		}else if(column == 2){
			result.setAttribute("path", p.getAbsolutePath()).addEvent(this, Event.CLICK);
			 result.setText(p.getTitle());
		}else if(column == 3){
			 result.setText(p.getTotal() !=null? p.getTotal().toString():"0");
		}
		
		
	}
	
	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		try{
			BCashBookEntryForm form = new BCashBookEntryForm("BCashBookEntryForm", container.getAttribute("cashbookdir"));
			form.setBookEntry((CashBookEntry)SpringUtil.getRepositoryService().getFile(container.getAttribute("path"), Util.getRemoteUser()));
			container.getAncestorOfType(PopupContainer.class).addPopup(form);
		}catch(Exception e){
			throw new UIException(e);
		}
			
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		
		
	}

}
