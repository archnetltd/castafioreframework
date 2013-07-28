package org.castafiore.shoppingmall.crm.subscriptions;

import java.text.SimpleDateFormat;

import org.castafiore.accounting.CashBookEntry;
import org.castafiore.accounting.ui.CashBookEntryCellRenderer;
import org.castafiore.accounting.ui.CashBookEntryModel;
import org.castafiore.security.User;
import org.castafiore.shoppingmall.reports.ReportsUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableModel;

public class PaymentsCellRenderer extends CashBookEntryCellRenderer {
	
	private String name;
	
	public final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy"); 
	
	
	public PaymentsCellRenderer(String name) {
		super();
		this.name = name;
	}

	@Override
	public Container getComponentAt(int row, int column, int page,
			TableModel model, EXTable table) {
		Payment p = (Payment)model.getValueAt(column, row, page);
		//String cDir = ((CashBookEntryModel)model).getCashBookDir();
		Container result = new  EXContainer("span", "span");
		if(column == 0){
			
			return result.setText(p.getDateOfTransaction() != null ? DATE_FORMAT.format(p.getDateOfTransaction()) : "" );
		}else if(column == 1){
			//result = new EXContainer("span", "a").setAttribute("cashbookdir", cDir).setAttribute("href", "#").setAttribute("path", p.getAbsolutePath()).addEvent(this, Event.CLICK);
			return result.setText(p.getTitle());
		}else if(column == 2){
			return result.setText(p.getTotal() !=null? p.getTotal().toString():"0");
		}else if(column == 3){
			return new EXContainer("", "a").setAttribute("href", ReportsUtil.getUrl(p, name)).setAttribute("target", "_blank").setText("Receipt");
		}
		
		return result;
	}

	@Override
	public void onChangePage(Container result, int row, int column,
			int page, TableModel model, EXTable table) {
		
		Payment p = (Payment)model.getValueAt(column, row, page);
		
		
		if(column == 0){
			result.setText(p.getDateOfTransaction() != null ? DATE_FORMAT.format(p.getDateOfTransaction()) : "" );
		}else if(column == 1){
			//result.setAttribute("path", p.getAbsolutePath()).addEvent(this, Event.CLICK);
			 result.setText(p.getTitle());
		}else if(column == 2){
			 result.setText(p.getTotal() !=null? p.getTotal().toString():"0");
		}else if(column == 3){
			result.setAttribute("href", ReportsUtil.getUrl(p, name)).setText("Receipt");
		}
		
		
	}

}
