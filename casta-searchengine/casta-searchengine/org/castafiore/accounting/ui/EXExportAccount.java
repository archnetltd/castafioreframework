package org.castafiore.accounting.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.castafiore.accounting.Account;
import org.castafiore.searchengine.MallUtil;
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

public class EXExportAccount extends EXContainer implements TableModel, Event{

	private String[] fields = new String[]{"Code", "Title", "Default Value", "Total Transaction"};
	
	private String cashBookDir;
	
	private List<Account> accounts = new ArrayList<Account>();
	public EXExportAccount(String name, String cbDir) {
		super(name, "div");
		EXFieldSet fs = new EXFieldSet("fs", "Export range", false);
		fs.addField("From : ", new EXDatePicker("from"));
		fs.addField("To : ", new EXDatePicker("to"));
		addChild(fs);
		EXButton btn = new EXButton("export", "Export accounts");
		btn.addEvent(this, CLICK);
		fs.addButton(btn);
		EXTableWithExport export = new EXTableWithExport("ss", this);
		addChild(export);
		this.cashBookDir = cbDir;
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
		return accounts.size();
	}

	@Override
	public int getRowsPerPage() {
		return 20;
	}

	@Override
	public Object getValueAt(int col, int row, int page) {
		int rrow = (page*getRowsPerPage()) + row;
		
		Account acc = accounts.get(rrow);
		if(col == 0){
			return acc.getCode();
		}else if(col == 1){
			return acc.getTitle();
		}else if(col == 2){
			return acc.getDefaultValue();
		}else{
			return "0";
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
		
		QueryParameters params = new QueryParameters().setEntity(Account.class).addSearchDir(cashBookDir);
		
		List r = SpringUtil.getRepositoryService().executeQuery(params, Util.getRemoteUser());
		
		accounts = r;
		
		this.getDescendentOfType(EXPagineableTable.class).refresh();
		
		
		return true;
		
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	

}
