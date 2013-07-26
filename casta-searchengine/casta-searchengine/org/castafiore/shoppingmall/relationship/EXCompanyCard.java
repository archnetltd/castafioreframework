package org.castafiore.shoppingmall.relationship;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.castafiore.persistence.Dao;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.dynaform.EXFieldSet;
import org.castafiore.ui.ex.form.EXLabel;
import org.castafiore.ui.ex.form.table.EXPagineableTable;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableModel;

public class EXCompanyCard extends EXContainer implements TableModel{
	Merchant merchant = null;

	public EXCompanyCard(String name, Merchant m) {
		super(name, "div");
		this.merchant = m;
		loadData();
		EXFieldSet fs = new EXFieldSet("ci", "Company Info", true);
		fs.addField("Name :", new EXLabel("companyName", m.getCompanyName()));
		fs.addField("Logo :", new EXLabel("logo", "<img src=" + m.getLogoUrl() + "></img>"));
		
		fs.addField("BRN :", new EXLabel("brn", m.getBusinessRegistrationNumber()));
		fs.addField("VAT :", new EXLabel("vat", m.getVatRegistrationNumber()));
		
		fs.addField("Address 1 :", new EXLabel("addLine1", m.getAddressLine1()));
		fs.addField("Address 2 :", new EXLabel("addLine2", m.getAddressLine2()));
		
		fs.addField("City :", new EXLabel("city", m.getCity()));
		fs.addField("Zip Code :", new EXLabel("zip", m.getZipPostalCode()));

		fs.addField("Email :", new EXLabel("email", m.getEmail()));
		fs.addField("Fax :", new EXLabel("fax", m.getFax()));
		
		fs.addField("Mobile :", new EXLabel("mobile", m.getMobilePhone()));
		fs.addField("Phone :", new EXLabel("phone", m.getPhone()));
		
		
		
		addChild(fs);
		
		
		addChild(new EXPagineableTable("pds", new EXTable("table", this)));
		
		
		
	}

	private static String[] labels = new String[]{"Order #", "Date", "From", "Amount"};
	
	private List data = null;
	
	private void loadData(){
		String sql = "select code, dateOfTransaction, orderedFrom, total from WFS_FILE where orderedBy='" + merchant.getUsername() + "'";
		
		data = SpringUtil.getBeanOfType(Dao.class).getReadOnlySession().createSQLQuery(sql).list();
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
		return data.size();
	}

	@Override
	public int getRowsPerPage() {
		return 10;
	}

	@Override
	public Object getValueAt(int col, int row, int page) {
		int rr = row + (page*getRowsPerPage());
		Object[] r = (Object[])data.get(rr);
		if(col == 1){
			return new SimpleDateFormat("dd MM yyy").format( new Date(((Timestamp)r[col]).getTime()));
		}else
			return r[col];
		
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return false;
	}

}
