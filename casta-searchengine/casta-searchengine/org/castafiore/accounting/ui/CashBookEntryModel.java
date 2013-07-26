package org.castafiore.accounting.ui;

import org.castafiore.accounting.CashBookEntry;
import org.castafiore.inventory.AbstractModel;
import org.castafiore.wfs.service.QueryParameters;

public class CashBookEntryModel extends AbstractModel {

	private String labels[] = new String[] { "Date", "Account", "Title", "Amount" };

	private String cashBookDir;

	public CashBookEntryModel(String cashBookDir) {
		super();
		this.cashBookDir = cashBookDir;
	}

	public String getCashBookDir() {
		return cashBookDir;
	}

	@Override
	public String[] getColumns() {
		return labels;
	}

	@Override
	public QueryParameters getParams() {
		return new QueryParameters().setEntity(CashBookEntry.class)
				.addSearchDir(cashBookDir);
	}

}
