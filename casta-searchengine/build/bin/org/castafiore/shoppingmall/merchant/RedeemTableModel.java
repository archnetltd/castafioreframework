package org.castafiore.shoppingmall.merchant;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.castafiore.searchengine.MallUtil;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class RedeemTableModel implements TableModel {
	
	private List<CreditRedeem> redeems = new ArrayList<CreditRedeem>();

	private String[] labels = new String[]{"Date", "Amount", "Redeemed by", "Name on cheque"};
	
	private final static SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
	
	
	public RedeemTableModel(){
		QueryParameters params = new QueryParameters().setEntity(CreditRedeem.class).addRestriction(Restrictions.eq("merchant", MallUtil.getCurrentMerchant().getUsername()));
		params.addOrder(Order.desc("dateCreated"));
		List list = SpringUtil.getRepositoryService().executeQuery(params, Util.getRemoteUser());
		redeems = list;
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public int getColumnCount() {
		return labels.length;
	}

	@Override
	public String getColumnNameAt(int index) {
		return labels[index];
	}

	@Override
	public int getRowCount() {
		return redeems.size();
	}

	@Override
	public int getRowsPerPage() {
		return 10;
	}

	@Override
	public Object getValueAt(int col, int row, int page) {
		CreditRedeem red = redeems.get((page*getRowsPerPage()) + row);
		if(col == 0){
			return format.format(red.getDateCreated().getTime());
		}else if(col == 1){
			return StringUtil.toCurrency(FinanceUtil.getCurrentCurrency(),red.getAmount());
		}else if(col == 2){
			return red.getRedeemedBy();
		}else
		{
			return red.getBankAccountName();
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return false;
	}

}
