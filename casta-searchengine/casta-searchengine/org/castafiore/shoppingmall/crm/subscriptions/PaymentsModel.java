package org.castafiore.shoppingmall.crm.subscriptions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.castafiore.accounting.CashBookEntry;
import org.castafiore.persistence.Dao;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.wfs.service.QueryParameters;
import org.hibernate.criterion.Restrictions;

public class PaymentsModel implements TableModel {



	private final static String labels[] = new String[] { "Date", "Title",
			"Amount", "Receipt" };

	private String cashBookDir;

	private int rowCount = -1;

	private int currentPage = 0;

	private List<Payment> buff = null;
	private String username ;
	


	public PaymentsModel(String cashBookDir, String username) {
		this.cashBookDir = cashBookDir;
		this.username = username;

	}

	@Override
	public int getRowCount() {
		if(rowCount == -1){
			String sql ="select count(*) from WFS_FILE where dtype='CashBookEntry' and accountCode='"+username+"' and absolutePath like '"+cashBookDir+"%'";
			rowCount = Integer.parseInt(SpringUtil.getBeanOfType(Dao.class).getReadOnlySession().createSQLQuery(sql).uniqueResult().toString());
		}
		return rowCount;
	}

	@Override
	public int getColumnCount() {
		return labels.length;
	}

	@Override
	public int getRowsPerPage() {
		return 20;
	}

	@Override
	public String getColumnNameAt(int index) {
		return labels[index];
	}

	@Override
	public Object getValueAt(int col, int row, int page) {

		if (col == 0 && row == 0) {
			loadPage(page);
		}

		Payment data = buff.get(row);

		if (col == labels.length - 1 && row == getRowsPerPage() - 1) {
			buff = null;
		}

		return data;
	}

	public void reLoad(){
		buff=null;
		rowCount =-1;
		loadPage(currentPage);
	}
	
	protected void loadPage(int page) {

		if (buff != null && page == this.currentPage) {
			return;
		}
		int pageSize = getRowsPerPage();

		int start = page * pageSize;

		String query = "select code,accountCode,dateOfTransaction,dateCreated,title,summary,total from WFS_FILE where dtype='CashBookEntry' and accountCode='"+username+"' and absolutePath like '"
				+ cashBookDir + "/%'";
		
		

		List l = SpringUtil.getBeanOfType(Dao.class).getReadOnlySession()
				.createSQLQuery(query).setFirstResult(start)
				.setMaxResults(pageSize).list();

		buff = new ArrayList<Payment>(pageSize);
		for (Object ao : l) {
			Object[] data = (Object[]) ao;
			Payment p = new Payment();
			p.setAccountCode((String) data[1]);
			p.setCode((String) data[0]);
			p.setDateCreated((Date) data[3]);
			p.setDateOfTransaction((Date) data[2]);
			p.setSummary((String) data[5]);
			p.setTitle((String) data[4]);
			p.setTotal((BigDecimal) data[6]);
			buff.add(p);
		}

		currentPage = page;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
}
