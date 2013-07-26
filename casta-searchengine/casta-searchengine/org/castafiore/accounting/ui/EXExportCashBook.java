package org.castafiore.accounting.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.castafiore.accounting.Account;
import org.castafiore.accounting.CashBookEntry;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.dynaform.EXFieldSet;
import org.castafiore.ui.ex.form.EXDatePicker;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.table.EXPagineableTable;
import org.castafiore.ui.ex.form.table.EXTableWithExport;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;

public class EXExportCashBook extends EXContainer implements TableModel, Event{

	
	
	private String cashBookDir;
	
	private List<CashBookEntry> entries = new ArrayList<CashBookEntry>();
	public EXExportCashBook(String name, String dir) {
		super(name, "div");
		EXFieldSet fs = new EXFieldSet("fs", "Export range", false);
		fs.addField("From : ", new EXDatePicker("from"));
		fs.addField("To : ", new EXDatePicker("to"));
		addChild(fs);
		EXButton btn = new EXButton("export", "Export Cashbook");
		btn.addEvent(this, CLICK);
		fs.addButton(btn);
		EXTableWithExport export = new EXTableWithExport("ss", this);
		addChild(export);
		this.cashBookDir = dir;
		
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public int getColumnCount() {
		return fields.length;
	}

	@Override
	public String getColumnNameAt(int index) {
		return fields[index];
	}

	@Override
	public int getRowCount() {
		return entries.size();
	}

	@Override
	public int getRowsPerPage() {
		return 20;
	}

	private String[] fields = new String[]{"Transaction Id", "Date","Chq. No", "Title", "Amount", "Description", "Point of sale", "Account Code", "Account title", "Default Value", "Account Type"};
	
	@Override
	public Object getValueAt(int col, int row, int page) {
		int rrow = (page*getRowsPerPage()) + row;
		
		CashBookEntry acc = entries.get(rrow);
		
		Account ac = acc.getAccount();
		
		if(ac == null){
			ac = new Account();
		}
		if(col == 0){
			return acc.getCode();
		}else if(col == 1){
			return acc.getDateOfTransaction();
		}else if(col == 2){
			return acc.getChequeNo();
		}else if(col == 3){
			return acc.getTitle();
		}else if(col == 4){
			return acc.getTotal().doubleValue();
		}else if(col == 5){
			return acc.getSummary();
		}else if(col == 6){
			return acc.getPointOfSale();
		}else if(col == 7){
			return acc.getAccountCode();
		}else if(col == 8){
			return ac.getTitle();
		}else if(col == 9){
			return ac.getDefaultValue().doubleValue();
		}else if(col == 10){
			return ac.getAccType();
		}else{
			return "Income";
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		
		return false;
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		Date from = (Date)getDescendentOfType(EXFieldSet.class).getField("from").getValue();
		Date to = (Date)getDescendentOfType(EXFieldSet.class).getField("to").getValue();
		
		QueryParameters params = new QueryParameters().setEntity(CashBookEntry.class).addSearchDir(cashBookDir);
		
		List r = SpringUtil.getRepositoryService().executeQuery(params, Util.getRemoteUser());
		
		entries = r;
		
		this.getDescendentOfType(EXPagineableTable.class).refresh();
		
		
		return true;
		
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
