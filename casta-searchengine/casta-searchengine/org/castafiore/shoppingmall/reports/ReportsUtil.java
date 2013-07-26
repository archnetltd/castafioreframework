package org.castafiore.shoppingmall.reports;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jodd.datetime.JDateTime;
import jodd.datetime.TimeUtil;

import org.apache.commons.collections.map.ListOrderedMap;
import org.castafiore.accounting.CashBookEntry;
import org.castafiore.persistence.Dao;
import org.castafiore.security.User;
import org.castafiore.shoppingmall.checkout.BillingInformation;
import org.castafiore.shoppingmall.checkout.Order;
import org.castafiore.shoppingmall.crm.subscriptions.Payment;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.form.table.EXTableWithExport;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.utils.IOUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.castafiore.wfs.types.File;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;



public class ReportsUtil {
	
	
	
public static Dat  calculate(double totalPayments, Date startPayment ,double installment){
		
		Calendar start = Calendar.getInstance();
		start.setTime((Date)startPayment.clone());
		
		if(start.get(Calendar.DATE) >=15){
			start.add(Calendar.MONTH, 1);
		}
		start.set(Calendar.DATE,1);
		
		double totalInstallmentsPaid = 0;
		if(installment > 0)
			totalInstallmentsPaid = (totalPayments-(totalPayments%installment))/installment;
		
		double currentInstallment = 0;
		
		while(start.getTimeInMillis() < Calendar.getInstance().getTimeInMillis()){
			currentInstallment++;
			start.add(Calendar.MONTH, 1);
		}
		if(new JDateTime().getDayOfMonth() <15)
			currentInstallment--;
		double arrear= (currentInstallment-totalInstallmentsPaid)*installment;
		
		Calendar nextPaymentDate = Calendar.getInstance();
		nextPaymentDate.setTime((Date)startPayment.clone());
		if(nextPaymentDate.get(Calendar.DATE) >=15){
			nextPaymentDate.add(Calendar.MONTH, 1);
		}
		nextPaymentDate.set(Calendar.DATE, 1);
		
		
		nextPaymentDate.add(Calendar.MONTH, new Double(totalInstallmentsPaid).intValue());
		
		Dat re = new Dat();
		re.nextPaymentDate = nextPaymentDate;
		re.arrear = arrear;
		re.totalInstallmentsPaid = totalInstallmentsPaid;
		re.currentInstallment = currentInstallment;
		return re;
		
		
	}
	
	public static class Dat{
		public Calendar nextPaymentDate;
		public double arrear;
		public double  totalInstallmentsPaid;
		public double currentInstallment;
		
		
	}
	
	
	public static String getUrl(CashBookEntry entry, User user){
		return getUrl(entry, user.toString());
	}
	
	public static String getUrl(CashBookEntry entry, String name){
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("receipt", entry.getCode());
		params.put("name", name);
		params.put("date", new SimpleDateFormat("dd/MMM/yyyy").format(entry.getDateCreated().getTime()));
		params.put("price", StringUtil.toCurrency("MUR",entry.getTotal()));
		params.put("description", entry.getSummary());
		params.put("fsNumber", entry.getAccountCode());
		
		return ReportsUtil.getReceiptUrl(params, "http://68.68.109.26/elie/insreceipt.xls");
	}
	
	public static String getUrl(Payment entry, String name){
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("receipt", entry.getCode());
		params.put("name", name);
		params.put("date", new SimpleDateFormat("dd/MMM/yyyy").format(entry.getDateCreated().getTime()));
		params.put("price", StringUtil.toCurrency("MUR",entry.getTotal()));
		params.put("description", entry.getSummary());
		params.put("fsNumber", entry.getAccountCode());
		
		return ReportsUtil.getReceiptUrl(params, "http://68.68.109.26/elie/insreceipt.xls");
	}
	
	public static String getReceiptUrl( Map<String, String> params, String template){
		
		StringBuilder b = new StringBuilder();
		Iterator<String> iter = params.keySet().iterator();
		b.append("export/?type=excelreceipt&template=" + template);
		while(iter.hasNext()){
			String key = iter.next();
			b.append("&" + key + "=" + params.get(key));
		}
		
		return b.toString();
	}

	public static Order getOrder(String code){
		QueryParameters param = new QueryParameters().setEntity(Order.class).addRestriction(Restrictions.eq("code", code));
		List<File> res = SpringUtil.getRepositoryService().executeQuery(param, Util.getRemoteUser()); 
		if(res.size() > 0){
			Order order = (Order)res.get(0);
			return order;
		}
		return null;
	}
	public static Container getLatePayments(){
		Session session = SpringUtil.getBeanOfType(Dao.class).getReadOnlySession();
		
		String sql = "select f.code, f.dateOfTransaction, u.firstName, u.lastName, u.email, u.mobile, f.installment from WFS_FILE as f, SECU_USER as u where f.dtype='Order' and f.code=u.username";
		Map<String,BigDecimal> totalPayments = getAllTotalPayments();
		List result = session.createSQLQuery(sql).list();
		final List<Object[]> table = new ArrayList<Object[]>();
		
		
		for(Object o : result){
			Object[] ar = (Object[])o;
			String code = ar[0].toString();
			Timestamp transactionDate = (Timestamp)ar[1];
			int currentInstallment = getMonthDiff(new Date( transactionDate.getTime()));
			BigDecimal totalPayment = BigDecimal.ZERO;
			if(totalPayments.containsKey(code)){
				totalPayment = totalPayments.get(code);
			}
			String name = "";
			if(ar[2] != null){
				name = ar[2].toString();
			}
			if(ar[3] != null){
				name = name+" " + ar[3].toString();
			}
			String email =ar[4] != null?ar[4].toString() :"";
			String mobile =ar[4] != null?ar[5].toString() :"";
			BigDecimal installment = (BigDecimal)ar[6];
			if(installment == null){
				installment = BigDecimal.ZERO;
			}
			BigDecimal totalToPay = installment.multiply(new BigDecimal(currentInstallment));
			
			BigDecimal arrear = totalToPay.subtract(totalPayment);
			
			if(arrear.doubleValue() > 0){
				Object[] data = new Object[9];
				
				data[0] = code;
				data[1] = transactionDate;
				data[2] = name;
				data[3] = mobile;
				data[4] = email;
				data[5] = currentInstallment;
				data[6] = installment;
				data[7] = totalPayment;
				data[8] = arrear;
				table.add(data);
			}
			
		}		
		
		final String[] cols = new String[]{"FS No","Reg. Date","Name", "Mobile", "Email", "Inst. No","Monthly due","Total Paid", "Arrears"};
		final SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
		
		TableModel model = new TableModel() {
			
			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public Object getValueAt(int col, int row, int page) {
				int realRow = (getRowsPerPage()*page) + row;
				Object o = table.get(realRow)[col];
				if(o instanceof Timestamp){
					Calendar cal = Calendar.getInstance();
					cal.setTimeInMillis(((Timestamp)o).getTime());
					return format.format(cal.getTime());
				}
				return table.get(realRow)[col];
//				if(col == 5){
//					Timestamp o =(Timestamp)((Object[])result.get(realRow))[1];
//					int inst = -1;
//					Calendar cal = Calendar.getInstance();
//					cal.setTimeInMillis(o.getTime());
//					
//					Calendar now = Calendar.getInstance();
//					while(now.getTimeInMillis()> cal.getTimeInMillis()){
//						inst = inst + 1;
//						cal.add(Calendar.MONTH, 1);
//					}
//					return inst;
//				}else if(col ==2){
//					String fn =(String)((Object[])result.get(realRow))[2];
//					if(fn == null){
//						fn = "";
//					}
//					String ln =(String)((Object[])result.get(realRow))[3];
//					if(ln == null){
//						ln = "";
//					}
//					return fn + " " + ln;
//				}else if(col == 0){
//					String fsnumber =(String)((Object[])result.get(realRow))[0];
//					return fsnumber;
//				}else if(col == 1){
//					Timestamp o =(Timestamp)((Object[])result.get(realRow))[1];
//					return format.format(  new Date( ((Timestamp)o).getTime()));
//				}else if(col == 3){
//					String mobile =(String)((Object[])result.get(realRow))[5];
//					return mobile;
//				}else if(col == 4){
//					String email =(String)((Object[])result.get(realRow))[4];
//					return email;
//				}else{
//				
//					Timestamp o =(Timestamp)((Object[])result.get(realRow))[1];
//					BigDecimal bd = (BigDecimal)((Object[])result.get(realRow))[6];
//					if(bd == null){
//						bd = BigDecimal.ZERO;
//					}
//					int inst = -1;
//					Calendar cal = Calendar.getInstance();
//					cal.setTimeInMillis(o.getTime());
//					
//					Calendar now = Calendar.getInstance();
//					while(now.getTimeInMillis()> cal.getTimeInMillis()){
//						inst = inst + 1;
//						cal.add(Calendar.MONTH, 1);
//					}
//					
//					return inst * bd.intValue();
//					
//					
//				}
			}
			
			@Override
			public int getRowsPerPage() {
				// TODO Auto-generated method stub
				return 20;
			}
			
			@Override
			public int getRowCount() {
				return table.size();
			}
			
			@Override
			public String getColumnNameAt(int index) {
				return cols[index];
			}
			
			@Override
			public int getColumnCount() {
				return cols.length;
			}
			
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return String.class;
			}
		};
		
		
		
		EXTableWithExport export = new EXTableWithExport("salesReport", model);
		return export;
	}
	
	public static Container getDeathReport(Date startDate, Date endDate, String filterType){
		String sql = "select value from WFS_FILE where dtype='Value' and name='applicationForm'";
		
		final List valss = SpringUtil.getBeanOfType(Dao.class).getReadOnlySession().createSQLQuery(sql).list();
		
		final List<Map<String,String>> vals = new ArrayList<Map<String,String>>(valss.size());
		
		for(Object o : valss){
			Map<String,String> data = BillingInformation.buildMap(o.toString());
			if(data.get("status").equalsIgnoreCase("21"))
				vals.add(data);
		}
		
		
		final String[] fields = new String[]{"Date terminated", "Death Date", "FS Code", "Principal Name", "Plan", "Total Paid", "Cost of Funeral"};
		
		TableModel model = new TableModel() {
			
			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public Object getValueAt(int col, int row, int page) {
				int realRow = (getRowsPerPage()*page) + row;
				Map<String,String> data = vals.get(realRow);
				
				if(col ==0){
					return data.get("dateTerminated");
				}else if(col == 1){
					return data.get("deathDate");
				}else if(col == 2){
					return data.get("fsNumber");
				}else if(col == 3){
					return data.get("pfullName") + " " + data.get("pSurname");
				}else if(col == 4){
					return data.get("plan");
				}else if(col == 5){
					return data.get("totalPayments");
				}else{
					return data.get("costOfFuneral");
				}
				
				//return ((Object[])result.get(realRow))[col];
			}
			
			@Override
			public int getRowsPerPage() {
				// TODO Auto-generated method stub
				return 20;
			}
			
			@Override
			public int getRowCount() {
				return vals.size();
			}
			
			@Override
			public String getColumnNameAt(int index) {
				return fields[index];
			}
			
			@Override
			public int getColumnCount() {
				return fields.length;
			}
			
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return String.class;
			}
		};
		EXTableWithExport export = new EXTableWithExport("deathReport", model);
		return export;
		
	}
	
	
	
	public static Container getSalesBySalesmanReport(Date startDate, Date endDate, String filterType){
		
		String col = "dateoftransaction";
		if(filterType.equalsIgnoreCase("Date of data entry")){
			col = "dateCreated";
		}
		
		Session session = SpringUtil.getBeanOfType(Dao.class).getReadOnlySession();
		String s = getSQL(col);
		
		
		
		
		Timestamp sd = new Timestamp(startDate.getTime());
		Timestamp ed = new Timestamp(endDate.getTime());
		final List result = session.createSQLQuery(s).setParameter(0, sd).setParameter(1, ed).list();
		
		final String[] cols = new String[]{"Total contracts", "Sales agent", "Total income","Total joining fee"};
//		System.out.println(result);
//		for(Object o : result){
//			Object[] arr = (Object[])o;
//			for(Object a : arr){
//				System.out.println(a);
//			}
//		}
		
		TableModel model = new TableModel() {
			
			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public Object getValueAt(int col, int row, int page) {
				int realRow = (getRowsPerPage()*page) + row;
				
				return ((Object[])result.get(realRow))[col];
			}
			
			@Override
			public int getRowsPerPage() {
				// TODO Auto-generated method stub
				return 20;
			}
			
			@Override
			public int getRowCount() {
				return result.size();
			}
			
			@Override
			public String getColumnNameAt(int index) {
				return cols[index];
			}
			
			@Override
			public int getColumnCount() {
				return cols.length;
			}
			
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return String.class;
			}
		};
		
		
		
		EXTableWithExport export = new EXTableWithExport("salesReport", model);
		return export;
		
		
		
		
		//Object[]
		
		//List result = SpringUtil.getRepositoryService().executeQuery(new QueryParameters().setEntity(Order.class).addRestriction(Restrictions.eq("owner", username)), username)
	}
	
	
	private static String getSQL(String col){
		try{
			
			return IOUtil.getFileContenntAsString("/usr/local/software/sql.sql");
			
		}catch(Exception e){
			e.printStackTrace();
			//return "SELECT f.pointOfSale, SUM(f.total) FROM WFS_FILE as f WHERE f.dtype='Order' AND f.dateoftransaction between ? and ?  GROUP BY f.pointOfSale ORDER BY f.pointOfSale";
			String s ="select count(*) ,f.owner, SUM(f.total), SUM(f.joiningfee) from WFS_FILE as f where dtype='Order' " +
			"AND f."+col+" between ? and ? " +
			" group by f.owner;";
			return s;
		}
	}
	
	
	public static BigDecimal getTotalPaymentsMade(String order){
		Session session = SpringUtil.getBeanOfType(Dao.class).getReadOnlySession();
		String sql = "select SUM(total) from WFS_FILE where dtype='CashBookEntry' and accountCode='"+order+"'";
		final List result = session.createSQLQuery(sql).list();
		if(result.size() > 0 && result.get(0) != null){
			return (BigDecimal)result.get(0);
		}else{
			return BigDecimal.ZERO;
		}
	}
	
	public static Map<String,BigDecimal> getAllTotalPayments(){
		Map<String,BigDecimal> res = new HashMap<String, BigDecimal>();
		Session session = SpringUtil.getBeanOfType(Dao.class).getReadOnlySession();
		String sql = "select sum(total), accountcode from WFS_FILE where dtype='CashBookEntry' group by accountcode";
		final List result = session.createSQLQuery(sql).list();
		for(Object o : result){
			Object[] ar = (Object[])o;
			if(ar[1] != null && ar[0] != null){
				String code = ar[1].toString();
				BigDecimal total = (BigDecimal)ar[0];
				res.put(code, total);
			}
		}
		return res;
	}
	
	public static int getTotalInstallments(Order order){
		
		
		
		return getTotalInstallments(order.getStartInstallmentDate());
		
	}
	
public static int getTotalInstallments(Date startInstallmentDate){
		
		
		
		
		return getMonthDiff(startInstallmentDate);
		
	}
	
public static int getMonthDiff(Date trans){
		
		JDateTime now = new JDateTime();
		JDateTime start = new JDateTime(trans.getTime());
		
		if(now.getDayOfMonth() < 15){
			now.addMonth(-1);
		}
		
		if(start.getDayOfMonth() >=15){
			start.addMonth(1);
		}
		start.setTime(0, 0, 0);
		start.setDay(1);
		now.setTime(23, 59, 59);
		now.setDay(TimeUtil.getMonthLength(now.getYear(), now.getMonth()));
		
	
		
		
		
//		//Date trans = order.getDateOfTransaction();
//		Calendar now = Calendar.getInstance();
//		if(now.get(``))
//		
//		Calendar start = Calendar.getInstance();
//		start.setTime(trans);
//		int count = -1;
//		if(start.get(Calendar.DATE) < 15){
//			count=count+1;
//		}
//		
//		if(true){
//			count = count-1;
//		}
//		now.set(Calendar.DATE, 1);
//		now.set(Calendar.HOUR, 1);
//		now.set(Calendar.MINUTE, 1);
//		now.set(Calendar.SECOND, 1);
//		start.set(Calendar.DATE, 1);
//		start.set(Calendar.HOUR, 1);
//		start.set(Calendar.MINUTE, 1);
//		start.set(Calendar.SECOND, 1);
//		
		int count = 0;
		
		while(start.getTimeInMillis() < now.getTimeInMillis()){
			count = count+1;
			start.addMonth( 1);
		}
		
		return count;
		
	}
	
	
public static Container getSalesBySourceReport(Date startDate, Date endDate,String filterType){
		 
	String col = "dateoftransaction";
	if(filterType.equalsIgnoreCase("Date of data entry")){
		col = "dateCreated";
	}
		Session session = SpringUtil.getBeanOfType(Dao.class).getReadOnlySession();
		
		String sql = "SELECT f.source, COUNT(*) FROM WFS_FILE as f WHERE f.dtype='Order' AND f."+col+" between ? and ?  GROUP BY f.source ORDER BY f.source";
		
		Timestamp sd = new Timestamp(startDate.getTime());
		Timestamp ed = new Timestamp(endDate.getTime());
		final List result = session.createSQLQuery(sql).setParameter(0, sd).setParameter(1, ed).list();
		
		final String[] cols = new String[]{"Source", "Total Contracts", };
		System.out.println(result);
		for(Object o : result){
			Object[] arr = (Object[])o;
			for(Object a : arr){
				System.out.println(a);
			}
		}
		
		TableModel model = new TableModel() {
			
			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public Object getValueAt(int col, int row, int page) {
				int realRow = (getRowsPerPage()*page) + row;
				
				return ((Object[])result.get(realRow))[col];
			}
			
			@Override
			public int getRowsPerPage() {
				// TODO Auto-generated method stub
				return 20;
			}
			
			@Override
			public int getRowCount() {
				return result.size();
			}
			
			@Override
			public String getColumnNameAt(int index) {
				return cols[index];
			}
			
			@Override
			public int getColumnCount() {
				return cols.length;
			}
			
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return String.class;
			}
		};
		return new EXTableWithExport("salesByPosReport", model);
		
	}
	
	
public static Container getSalesByPOSReport(Date startDate, Date endDate, String filterType){
		 
	String col = "dateoftransaction";
	if(filterType.equalsIgnoreCase("Date of data entry")){
		col = "dateCreated";
	}
		Session session = SpringUtil.getBeanOfType(Dao.class).getReadOnlySession();
		
		String sql ="SELECT f.pointOfSale, SUM(f.total), count(*) as totalContracts FROM WFS_FILE as f WHERE f.dtype='Order' AND f."+col+" between ? and ?  GROUP BY f.pointOfSale ORDER BY f.pointOfSale";
		
		Timestamp sd = new Timestamp(startDate.getTime());
		Timestamp ed = new Timestamp(endDate.getTime());
		final List result = session.createSQLQuery(sql).setParameter(0, sd).setParameter(1, ed).list();
		
		final String[] cols = new String[]{"Point Of Sale", "Total", "Total Contracts"};
		
		
		TableModel model = new TableModel() {
			
			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public Object getValueAt(int col, int row, int page) {
				int realRow = (getRowsPerPage()*page) + row;
				
				return ((Object[])result.get(realRow))[col];
			}
			
			@Override
			public int getRowsPerPage() {
				// TODO Auto-generated method stub
				return 20;
			}
			
			@Override
			public int getRowCount() {
				return result.size();
			}
			
			@Override
			public String getColumnNameAt(int index) {
				return cols[index];
			}
			
			@Override
			public int getColumnCount() {
				return cols.length;
			}
			
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return String.class;
			}
		};
		return new EXTableWithExport("salesByPosReport", model);
		
	}

	public static Container getDailySalesReport(Date startDate, Date endDate, String filterType){
		String col = "dateoftransaction";
		if(filterType.equalsIgnoreCase("Date of data entry")){
			col = "dateCreated";
		}
		Session session = SpringUtil.getBeanOfType(Dao.class).getReadOnlySession();
		String sql ="select f.dateOfTransaction, f.total from WFS_FILE as f where f."+col+" between ? and ? and f.dtype='Order' order by f.dateOfTransaction ";
		Timestamp sd = new Timestamp(startDate.getTime());
		Timestamp ed = new Timestamp(endDate.getTime());
		final List result = session.createSQLQuery(sql).setParameter(0, sd).setParameter(1, ed).list();
		
		
		final Map<String,Integer> totals = new HashMap<String, Integer>();
		final Map accm = new ListOrderedMap();
		SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
		for(Object o : result){
			Object[] ar = (Object[])o;
			
			
			Timestamp date = (Timestamp)ar[0];
			BigDecimal total = (BigDecimal)ar[1];
			String s = format.format(new Date( date.getTime()));
			
			if(accm.containsKey(s)){
				BigDecimal i = (BigDecimal)accm.get(s);
				i = i.add(total);
				accm.put(s, i);
			}else{
				accm.put(s, total);
			}
			
			if(totals.containsKey(s)){
				totals.put(s, totals.get(s) +1);
			}else{
				totals.put(s, 1);
			}
		}
		
		
		final String[] cols = new String[]{"Date", "Total", "Total Contract"};
		
		TableModel model = new TableModel() {
			
			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public Object getValueAt(int col, int row, int page) {
				int realRow = (getRowsPerPage()*page) + row;
				
				if(col == 0)
					return accm.keySet().toArray()[realRow];
				
				else if(col == 1)
					return accm.get(accm.keySet().toArray()[realRow]);
				else
					return totals.get(accm.keySet().toArray()[realRow]);
				//return ((Object[])result.get(realRow))[col];
			}
			
			@Override
			public int getRowsPerPage() {
				// TODO Auto-generated method stub
				return 20;
			}
			
			@Override
			public int getRowCount() {
				return accm.size();
			}
			
			@Override
			public String getColumnNameAt(int index) {
				return cols[index];
			}
			
			@Override
			public int getColumnCount() {
				return cols.length;
			}
			
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return String.class;
			}
		};
		
		return new EXTableWithExport("salesByPosReport", model);
	}
	public static Container getSalesByMonth(Date startDate, Date endDate, String filterType){
		
		String col = "dateoftransaction";
		if(filterType.equalsIgnoreCase("Date of data entry")){
			col = "dateCreated";
		}
		Session session = SpringUtil.getBeanOfType(Dao.class).getReadOnlySession();
		String sql ="select f.dateOfTransaction, f.total from WFS_FILE as f where f.dtype='Order' and f."+col+" between ? and ? order by f.dateOfTransaction";
		Timestamp sd = new Timestamp(startDate.getTime());
		Timestamp ed = new Timestamp(endDate.getTime());
		final List result = session.createSQLQuery(sql).setParameter(0, sd).setParameter(1, ed).list();
		
		
		final Map<String,Integer> totals = new HashMap<String, Integer>();
		final Map accm = new ListOrderedMap();
		SimpleDateFormat format = new SimpleDateFormat("MMM yyyy");
		for(Object o : result){
			Object[] ar = (Object[])o;
			
			
			Timestamp date = (Timestamp)ar[0];
			BigDecimal total = (BigDecimal)ar[1];
			String s = format.format(new Date( date.getTime()));
			
			if(accm.containsKey(s)){
				BigDecimal i = (BigDecimal)accm.get(s);
				i = i.add(total);
				accm.put(s, i);
			}else{
				accm.put(s, total);
			}
			
			if(totals.containsKey(s)){
				totals.put(s, totals.get(s) +1);
			}else{
				totals.put(s, 1);
			}
		}
		
		
		final String[] cols = new String[]{"Month", "Total", "Total Contracts"};
		
		TableModel model = new TableModel() {
			
			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public Object getValueAt(int col, int row, int page) {
				int realRow = (getRowsPerPage()*page) + row;
				
				if(col == 0)
					return accm.keySet().toArray()[realRow];
				
				else if(col == 1)
					return accm.get(accm.keySet().toArray()[realRow]);
				else
					return totals.get(accm.keySet().toArray()[realRow]);

			}
			
			@Override
			public int getRowsPerPage() {
				// TODO Auto-generated method stub
				return 20;
			}
			
			@Override
			public int getRowCount() {
				return accm.size();
			}
			
			@Override
			public String getColumnNameAt(int index) {
				return cols[index];
			}
			
			@Override
			public int getColumnCount() {
				return cols.length;
			}
			
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return String.class;
			}
		};
		
		return new EXTableWithExport("salesByPosReport", model);
		
	}

}
