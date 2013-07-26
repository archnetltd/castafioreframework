package org.castafiore.accounting.ui;

import org.castafiore.accounting.Account;
import org.castafiore.inventory.AbstractModel;
import org.castafiore.wfs.service.QueryParameters;

public class AccountModel extends AbstractModel {

	private String labels[] = new String[]{"Code", "Title", "In/Out"};
	
	private String cashBookPath;
	
	
	
	
	public String getCashBookPath() {
		return cashBookPath;
	}

	public AccountModel(String cashBookPath) {
		super();
		this.cashBookPath = cashBookPath;
	}

	@Override
	public String[] getColumns() {
		return labels;
	}

	@Override
	public QueryParameters getParams() {
		return new QueryParameters().setEntity(Account.class).addSearchDir(cashBookPath);
	}

}
