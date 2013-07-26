package org.castafiore.inventory.orders;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.DateTools.Resolution;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumberTools;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.castafiore.persistence.Dao;
import org.castafiore.shoppingmall.checkout.BillingInformation;
import org.castafiore.shoppingmall.crm.ContractDetail;
import org.castafiore.shoppingmall.crm.Dependent;
import org.castafiore.shoppingmall.crm.OrderInfo;
import org.castafiore.shoppingmall.crm.OrderLine;
import org.castafiore.shoppingmall.crm.PaymentDetail;
import org.castafiore.shoppingmall.crm.subscriptions.Payment;
import org.castafiore.shoppingmall.frontend.FSCodeVO;
import org.castafiore.shoppingmall.reports.ReportsUtil;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.UIException;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.BinaryFile;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

public class OrderService {
	
	//public final static String indexdir = "c:\\java\\index";//"/usr/local/software/tomcat6/vroom";
	public final static String indexdir = "/usr/local/software/tomcat6/vroom";
private static Directory index = null;
	
	private static StandardAnalyzer analyzer = new StandardAnalyzer();

	private final static String[] FIELDS = new String[] { "code", "firstName",
			"lastName", "addressLine1", "addressLine2", "city", "nic", "email" };

	private static DefaultDataModel<Object> POS = null;

	public List<OrderInfo> getMerchantOrder(String merchant) {
		return new ArrayList<OrderInfo>();
	}

	private static List<OrderInfo> searchByExample(OrderInfo ex, Date from, Date to,
			boolean bydatecreated, int page, int paseSize) {
		return searchByExample(ex, from, to, bydatecreated, null, page,
				paseSize);
	}
	public static void indexOrder(String abs){
		
		try{
		
		
		IndexWriter w = null;
		try{
			if(index == null){
				index = FSDirectory.getDirectory(indexdir);
			}
			File dir = new File(indexdir);
			w = new IndexWriter(index, analyzer,MaxFieldLength.UNLIMITED);
			 
			while(true){
				List<String> ss = new ArrayList<String>();
				ss.add(abs);
				
				List<OrderInfo> orders = hydrate(ss);

				for(OrderInfo o : orders){
					addDocument(o, w);
				}
				
				break;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(w != null){
				w.close();
			}
		}
		
		w.close();
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void indexOrders()throws IOException, IllegalAccessException{ 
		IndexWriter w = null;
		try{
			if(index == null){
				index = FSDirectory.getDirectory(indexdir);
			}
			File dir = new File(indexdir);
			if(dir.list() != null && dir.list().length > 0){
				return;
			}
			w = new IndexWriter(index, analyzer,MaxFieldLength.UNLIMITED);
			
			int page = 0;
			 
			while(true){
				List ss = SpringUtil.getBeanOfType(Dao.class).getReadOnlySession().createSQLQuery("select absolutePath from WFS_FILE where dtype='Order'").setFirstResult(page*500).setMaxResults(500).list();
				List<OrderInfo> orders = hydrate(ss);
				//List<OrderInfo> orders = searchByExample(new OrderInfo(), null, null,false, page, 500);
				
				
				for(OrderInfo o : orders){
					addDocument(o, w);
				}
				
				if(orders.size() < 500){
					break;
				}else{
					page = page +1;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(w != null){
				w.close();
			}
		}
		
		w.close();
		
		
	}
	
	
	
	public static List<OrderInfo> fullTextSearch(String term, int page, int pageSize)throws Exception{
		indexOrders();
		//List<OrderInfo> result = new ArrayList<OrderInfo>();
		String query = "";
		boolean and = false;
		List<String> fields = new ArrayList<String>();
		for(java.lang.reflect.Field f : OrderInfo.class.getDeclaredFields()){
			
			fields.add(f.getName());
			
			if(f.getType().isAssignableFrom(String.class)){
				
				if(query.length() > 0)
					query = query + (and?" AND ":" OR ");
				
				
					query = query + f.getName() + ":" + term + "*";
				
			}
		}
		
		
		
		
		Query q = new MultiFieldQueryParser(fields.toArray(new String[fields.size()]),analyzer).parse(query);
		
		int hitsPerPage = 100;
	    IndexSearcher searcher = new IndexSearcher(index);
	    //TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
	    TopDocCollector collector = new TopDocCollector(hitsPerPage);
	    searcher.search(q, collector);
	    ScoreDoc[] hits = collector.topDocs().scoreDocs;
	   List<String> ids = new ArrayList<String>();
	    for(int i=0;i<hits.length;++i) {
	        int docId = hits[i].doc;
	        Document d = searcher.doc(docId);
	        String path = d.get("absolutePath");
	        if(!ids.contains(path)){
		        
		       
		        //result.add(hydrate(d));
		        ids.add(path);
	        }
	       
	     }
	    
	    
	    return hydrate(ids);
	    
	   // return result;
	}
	
	public List<OrderInfo> fullTextSearchByExample(OrderInfo ex, Date from, Date to,
			boolean bydatecreated, int page, int paseSize, boolean and)throws Exception{
		indexOrders();
		List<OrderInfo> result = new ArrayList<OrderInfo>();
		String query = "";
		List<String> fields = new ArrayList<String>();
		for(java.lang.reflect.Field f : OrderInfo.class.getDeclaredFields()){
			Object o = f.get(ex);
			fields.add(f.getName());
			
			if(o != null){
				
				if(query.length() > 0)
					query = query + (and?" AND ":" OR ");
				
				if(o instanceof String){
					query = query + f.getName() + ":" + o.toString();
				}else if(o instanceof BigDecimal){
					query = query + f.getName() + ":" + NumberTools.longToString(((BigDecimal)o).longValue());
				}else if(o instanceof Date){
					query = query + f.getName() + ":" + DateTools.dateToString(((Date)o), Resolution.SECOND);
				}
			}
		}
		
		if(from != null && to!= null){
			query = query +  (query.length() > 0?(and?" AND ":" OR "): "") + (bydatecreated?"dateCreated": "dateOfTransaction") + ":[" + DateTools.dateToString(from, Resolution.SECOND) + " TO " + DateTools.dateToString(to, Resolution.SECOND) + "]";
		}
		
		
		Query q = new MultiFieldQueryParser(fields.toArray(new String[fields.size()]),analyzer).parse(query);
		
		int hitsPerPage = 10;
	    IndexSearcher searcher = new IndexSearcher(index);
	    //TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
	    TopDocCollector collector = new TopDocCollector(hitsPerPage);
	    searcher.search(q, collector);
	    ScoreDoc[] hits = collector.topDocs().scoreDocs;
	   
	    for(int i=0;i<hits.length;++i) {
	        int docId = hits[i].doc;
	        Document d = searcher.doc(docId);
	        
	        result.add(hydrate(d));
	       
	     }
	    
	    return result;
	}
	
	
	
	public static OrderInfo hydrate(Document doc)throws Exception{
		List fields  = doc.getFields();
		OrderInfo info = new OrderInfo();
		for(Object o : fields){
			Field f = (Field)o;
			java.lang.reflect.Field field = OrderInfo.class.getField(f.name());
			Class<?> clazz = field.getType();
			try{
				
				
				if(clazz.isAssignableFrom(Date.class)){
					Date d = DateTools.stringToDate(f.stringValue());
					field.set(info, d);
				}else if(clazz.isAssignableFrom(BigDecimal.class)){
					BigDecimal d = new BigDecimal(NumberTools.stringToLong(f.stringValue()));
					field.set(info, d);
				}else if(clazz.isAssignableFrom(Integer.class)){
					int d = Integer.parseInt(f.stringValue());
					field.set(info, d);
				}else{
					field.set(info, f.stringValue());
				}
			}catch(Exception e){
				System.out.println(f.toString());
			}
		}
		
		return info;
	}
	
	public static void reIndex()throws IOException, IllegalAccessException{
		if(index != null)
		index.close();
		
		File dir = new File(indexdir);
		if(dir.list() != null){
			for (File f : dir.listFiles()){
				f.delete();
			}
		}
		
		index = null;
		indexOrders();
		
	}
	
	
	private static void addDocument(OrderInfo info,IndexWriter w)throws IllegalAccessException, IOException{
		 Document doc = new Document();
		 java.lang.reflect.Field[] fields = OrderInfo.class.getDeclaredFields();
		 
		 for(java.lang.reflect.Field f : fields){
			 Object value = f.get(info);
			 if(value != null){
				if(value instanceof Date){
					value = DateTools.dateToString((Date)value	, Resolution.SECOND);
				}else if(value instanceof BigDecimal){
					value =NumberTools.longToString(((BigDecimal)value).longValue());
				}
				doc.add(new Field(f.getName(), value.toString(), Field.Store.YES, Field.Index.ANALYZED));
				w.addDocument(doc);
			 }
		 }
		    
	}

	public PaymentDetail getPaymentDetail(String fsCode) {

		PaymentDetail detail = new PaymentDetail();
		OrderInfo order = getOrder(fsCode);

		Calendar rstart_ = Calendar.getInstance();
		if (order == null) {
			throw new UIException("Cannot find order with the specified fscode");
		}
		int totalInstallments = ReportsUtil.getTotalInstallments(order
				.getStartInstallmentDate());
		BigDecimal totalPayment = ReportsUtil.getTotalPaymentsMade(order
				.getFsCode());
		BigDecimal installment = order.getInstallment();

		if (installment == null) {
			installment = BigDecimal.ZERO;
		}
		BigDecimal totalToPay = installment.multiply(new BigDecimal(
				totalInstallments));
		BigDecimal arrear = totalToPay.subtract(totalPayment);

		if (installment.doubleValue() == 0) {
			installment = new BigDecimal(300);
		}

		detail.setArrear(arrear);
		detail.setFsCode(fsCode);
		detail.setInstallment(installment);
		detail.setInstallmentsPaid(totalInstallments);

		if (arrear.doubleValue() > 0) {
			BigDecimal monthsNotPaid = BigDecimal.ZERO;
			double remainder = arrear.doubleValue() % installment.doubleValue();
			BigDecimal b = arrear.subtract(new BigDecimal(remainder));

			if (b.doubleValue() > 0) {
				monthsNotPaid = b.divide(installment);
			}
			if (remainder > 0) {
				monthsNotPaid = monthsNotPaid.add(new BigDecimal(1));
			}

			BigDecimal monthPaid = new BigDecimal(totalInstallments)
					.subtract(monthsNotPaid);
			Calendar start = Calendar.getInstance();
			start.setTime(order.getDateOfTransaction());
			if (start.get(Calendar.DATE) >= 15) {
				start.add(Calendar.MONTH, 1);
			}
			start.set(Calendar.DATE, 1);
			start.set(Calendar.HOUR, 1);
			start.set(Calendar.MINUTE, 1);
			start.set(Calendar.SECOND, 1);
			for (int i = 1; i < monthPaid.doubleValue(); i++) {
				start.add(Calendar.MONTH, 1);
			}

			rstart_ = (Calendar) start.clone();
			rstart_.add(Calendar.MONTH, 1);

		} else {
			double remainder = totalPayment.doubleValue()
					% installment.doubleValue();
			BigDecimal b = totalPayment.subtract(new BigDecimal(remainder));
			BigDecimal monthPaid = BigDecimal.ZERO;
			if (b.doubleValue() > 0) {
				monthPaid = b.divide(installment);
			}
			if (remainder > 0) {
				monthPaid = monthPaid.add(new BigDecimal(1));
			}

			Calendar start = Calendar.getInstance();
			start.setTime(order.getDateOfTransaction());
			if (start.get(Calendar.DATE) >= 15) {
				start.add(Calendar.MONTH, 1);
			}
			start.set(Calendar.DATE, 1);
			start.set(Calendar.HOUR, 1);
			start.set(Calendar.MINUTE, 1);
			start.set(Calendar.SECOND, 1);
			for (int i = 1; i < monthPaid.doubleValue(); i++) {
				start.add(Calendar.MONTH, 1);
			}
			rstart_ = (Calendar) start.clone();
			rstart_.add(Calendar.MONTH, 1);

		}

		detail.setStartPaymentMonth(rstart_.getTimeInMillis());

		return detail;

	}

	public String savePayment(String accCode, BigDecimal ttl,
			String paymentMethod, String chequeNo, String pos,
			String description) throws UIException {

		try {

			String query = "insert into WFS_FILE (clazz, dateCreated, lastModified, name, parent_id, summary, title, code, paymentMethod,total, accountCode, dateOfTransaction, DTYPE, absolutePath, size, status, commentable, dislikeit, likeit,ratable, pointOfSale) values "
					+ ""
					+ "('org.castafiore.accounting.CashBookEntry',now(),now(),:name, '/root/users/elieandsons/Applications/e-Shop/elieandsons/DefaultCashBook', :summary,:title,:code,:paymentMethod,:total,:accCode,:date,'CashBookEntry', :absolutePath,1,1,true,1,1, true,:pointOfSale);";
			String name = System.currentTimeMillis() + "";

			// 0=name
			// 1=summary
			// 2=title
			// 3=code
			// 4=paymentMethod
			// 5=total
			// 6=accCode
			// 7=date transaction
			// 8=absolutepath
			// 9=pointOfSale

			String code = SpringUtil.getRepositoryService().getNextSequence(
					"Payments", "elieandsons", "elieandsons")
					+ "";

			SpringUtil
					.getBeanOfType(Dao.class)
					.getSession()
					.createSQLQuery(query)
					.setParameter("name", name)
					.setParameter("summary", description)
					.setParameter("title", description)
					.setParameter("code", code)
					.setParameter("paymentMethod", paymentMethod)
					.setParameter("accCode", accCode)
					.setParameter("total", ttl)
					.setParameter("date",
							new Timestamp(System.currentTimeMillis()))
					.setParameter(
							"absolutePath",
							"/root/users/elieandsons/Applications/e-Shop/elieandsons/DefaultCashBook/"
									+ name).setParameter("pointOfSale", pos)
					.executeUpdate();

			return code;

		} catch (Exception e) {
			throw new UIException(e);
		}

	}

	public BigDecimal getInstallment(String fsCode) {
		String q = "select installment from WFS_FILE where dtype='Order' and code='"
				+ fsCode + "'";

		List l = SpringUtil.getBeanOfType(Dao.class).getReadOnlySession()
				.createSQLQuery(q).list();
		if (l.size() > 0) {
			return (BigDecimal) l.get(0);
		} else {
			return new BigDecimal(0);
		}

	}

	public List<Payment> searchPaymentByExample(Payment payment, int page,
			int pageSize) {

		String query = "select code,accountCode,dateOfTransaction,dateCreated,title,summary,total, paymentMethod from WFS_FILE where dtype='CashBookEntry' and 0=0";

		if (StringUtil.isNotEmpty(payment.getAccountCode())) {
			query = query + " and accountCode='" + payment.getAccountCode()
					+ "'";
		}

		if (StringUtil.isNotEmpty(payment.getCode())) {
			query = query + " and code='" + payment.getCode() + "'";
		}

		if (StringUtil.isNotEmpty(payment.getSummary())) {
			query = query + " and summary='" + payment.getSummary() + "'";
		}

		if (StringUtil.isNotEmpty(payment.getTitle())) {
			query = query + " and title='" + payment.getTitle() + "'";
		}

		int count = Integer
				.parseInt(SpringUtil
						.getBeanOfType(Dao.class)
						.getReadOnlySession()
						.createSQLQuery(
								query.replace(
										"code,accountCode,dateOfTransaction,dateCreated,title,summary,total, paymentMethod",
										"count(*)")).uniqueResult().toString());

		List l = SpringUtil.getBeanOfType(Dao.class).getReadOnlySession()
				.createSQLQuery(query).setFirstResult(page * pageSize)
				.setMaxResults(pageSize).list();

		List<Payment> result = new ArrayList<Payment>(pageSize);
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
			p.setPaymentMethod((String) data[7]);
			p.setCount(count);
			result.add(p);
		}

		return result;
	}

	public DefaultDataModel<Object> getPointOfSales() {
		if (POS == null) {
			try {
				BinaryFile bf = (BinaryFile) SpringUtil.getRepositoryService()
						.getFile("/root/users/elieandsons/pos.txt",
								Util.getRemoteUser());

				InputStream inf = bf.getInputStream();

				BufferedReader in = new BufferedReader(new InputStreamReader(
						inf));
				POS = new DefaultDataModel<Object>();
				String inputLine;
				while ((inputLine = in.readLine()) != null)
					POS.addItem(inputLine);

				in.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return POS;
	}

	
	
	
	public List<OrderInfo> searchOrders(List<String> fsCodes){
		if(fsCodes.size() == 0){
			return new ArrayList<OrderInfo>(0);
		}
		String query = "select absolutepath from WFS_FILE where dtype='Order' and code in (:codes)";
		List abss = SpringUtil.getBeanOfType(Dao.class).getReadOnlySession().createSQLQuery(query).setParameterList("codes", fsCodes).list();
		return hydrate(abss);
	}
	
//	public List<OrderInfo> fullTextSearch(String term, int page, int pageSize) {
//
//		StringBuilder q1 = new StringBuilder(
//				"select absolutePath from WFS_FILE where ");
//		Map<String, Object> params = new LinkedHashMap<String, Object>();
//
//		q1.append("(LOWER(addressLine1) LIKE '%"
//				+ term
//				+ "%' or (dtype!='BillingInformation' and addressLine1=null)) or ");
//
//		q1.append("(LOWER(addressLine2)LIKE '%"
//				+ term
//				+ "%' or (dtype!='BillingInformation' and addressLine2=null)) or ");
//
//		q1.append("(LOWER(city) LIKE '%" + term
//				+ "%' or (dtype!='BillingInformation' and city=null)) or ");
//
//		q1.append("(LOWER(firstName) LIKE '%"
//				+ term
//				+ "%' or (dtype!='BillingInformation' and firstName=null)) or ");
//
//		q1.append("(LOWER(code) LIKE '%" + term
//				+ "%' or (dtype!='Order' and code=null)) and ");
//
//		q1.append("(LOWER(lastName) LIKE '%" + term
//				+ "%' or (dtype!='BillingInformation' and lastName=null)) or ");
//
//		q1.append("(LOWER(mobile) LIKE '%" + term
//				+ "%' or (dtype!='BillingInformation' and mobile=null)) or ");
//
//		q1.append("(LOWER(phone) LIKE '%" + term
//				+ "%' or (dtype!='BillingInformation' and phone=null))");
//
//		//q1.append("0<>0");
//		System.out.println(q1.toString());
//
//		
//		
//		SQLQuery q = SpringUtil.getBeanOfType(Dao.class).getReadOnlySession()
//				.createSQLQuery(q1.toString());
//		Iterator<String> iter = params.keySet().iterator();
//		while (iter.hasNext()) {
//			String key = iter.next();
//			Object param = params.get(key);
//			q.setParameter(key, param);
//			
//		}
//		
//
//		q.setFirstResult(pageSize * page);
//		q.setMaxResults(pageSize);
//		List abss = q.list();
//		return hydrate(abss);
//	}
	
	
	

	public static List<OrderInfo> searchByExample(OrderInfo ex, Date from, Date to,
			boolean bydatecreated, List status, int page, int paseSize) {

		if (ex == null) {
			ex = new OrderInfo();
			ex.setCount(0);
			return new ArrayList<OrderInfo>();
		}

		StringBuilder q1 = new StringBuilder(
				"select absolutePath from WFS_FILE where dtype in('Order','BillingInformation', 'SalesOrderEntry') and ");
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		if (StringUtil.isNotEmpty(ex.getAbsolutePath())) {

		}

		if (StringUtil.isNotEmpty(ex.getAddressLine1())) {
			q1.append("(addressLine1='"
					+ ex.getAddressLine1()
					+ "' or (dtype!='BillingInformation' and addressLine1=null)) and ");
		}
		if (StringUtil.isNotEmpty(ex.getAddressLine2())) {
			q1.append("(addressLine2='"
					+ ex.getAddressLine2()
					+ "' or (dtype!='BillingInformation' and addressLine2=null)) and ");
		}

		if (StringUtil.isNotEmpty(ex.getCity())) {
			q1.append("(city='" + ex.getCity()
					+ "' or (dtype!='BillingInformation' and city=null)) and ");
		}

		if (StringUtil.isNotEmpty(ex.getCompany())) {
			q1.append("(company='"
					+ ex.getCompany()
					+ "' or (dtype!='BillingInformation' and company=null)) and ");
		}

		if (StringUtil.isNotEmpty(ex.getCountry())) {
			q1.append("(country='"
					+ ex.getCountry()
					+ "' or (dtype!='BillingInformation' and country=null)) and ");
		}

		if (StringUtil.isNotEmpty(ex.getEmail())) {
			q1.append("(email='" + ex.getEmail()
					+ "' or (dtype!='BillingInformation' and email=null)) and ");
		}

		if (StringUtil.isNotEmpty(ex.getFax())) {
			q1.append("(fax='" + ex.getFax()
					+ "' or (dtype!='BillingInformation' and fax=null)) and ");
		}

		if (StringUtil.isNotEmpty(ex.getFirstName())) {
			q1.append("(firstName='"
					+ ex.getFirstName()
					+ "' or (dtype!='BillingInformation' and firstName=null)) and ");
		}

		if (StringUtil.isNotEmpty(ex.getFsCode())) {
			q1.append("(code='" + ex.getFsCode()
					+ "' or (dtype!='Order' and code=null)) and ");
		}

		if (StringUtil.isNotEmpty(ex.getPaymentMethod())) {
			q1.append("(paymentMethod='" + ex.getPaymentMethod()
					+ "' or (dtype!='Order' and paymentMethod=null)) and ");
		}

		if (StringUtil.isNotEmpty(ex.getLastName())) {
			q1.append("(lastName='"
					+ ex.getLastName()
					+ "' or (dtype!='BillingInformation' and lastName=null)) and ");
		}

		if (StringUtil.isNotEmpty(ex.getMobile())) {
			q1.append("(mobile='"
					+ ex.getMobile()
					+ "' or (dtype!='BillingInformation' and mobile=null)) and ");
		}

		if (StringUtil.isNotEmpty(ex.getPhone())) {
			q1.append("(phone='" + ex.getPhone()
					+ "' or (dtype!='BillingInformation' and phone=null)) and ");
		}

		if (StringUtil.isNotEmpty(ex.getTitle())) {
			q1.append("(title='" + ex.getTitle()
					+ "' or (dtype!='SalesOrderEntry' and title=null)) and ");
		}

		if (StringUtil.isNotEmpty(ex.getZipPostalCode())) {
			q1.append("(zipPostalCode='"
					+ ex.getZipPostalCode()
					+ "' or (dtype!='BillingInformation' and zipPostalCode=null)) and ");
		}

		if (StringUtil.isNotEmpty(ex.getDateCreated())) {
			q1.append("(dateCreated=:dateCreated or (dtype!='Order' and dateCreated=null)) and ");
			params.put("dateCreated", ex.getDateCreated());
		}

		if (StringUtil.isNotEmpty(ex.getDateOfTransaction())) {
			q1.append("(dateOfTransaction=:dateOfTransaction or (dtype!='Order' && dateOfTransaction=null)) and ");
			params.put("dateOfTransaction", ex.getDateOfTransaction());
		}

		if (StringUtil.isNotEmpty(ex.getInstallment())) {
			q1.append("(installment=:installment or (dtype!='Order' and installment=null)) and ");
			params.put("installment", ex.getInstallment());
		}

		if (StringUtil.isNotEmpty(ex.getStatus()) && (ex.getStatus() > -1)) {
			q1.append("(status=:status or (dtype!='Order' and status=null)) and ");
			params.put("status", ex.getStatus());
		}

		if (status != null && status.size() > 0) {
			q1.append("(status in ("
					+ status.toString().replace("[", "").replace("]", "")
					+ ") or (dtype!='Order' and status=null)) and ");

		}

		if (StringUtil.isNotEmpty(from) && StringUtil.isNotEmpty(to)) {
			if (bydatecreated) {
				if (StringUtil.isNotEmpty(ex.getDateCreated())) {
					q1.append("((dateCreated > :from and dateCreated < :to) or (dtype!='Order' and dateCreated=null)) and ");
				} else {
					q1.append("((dateOfTransaction > :from and dateOfTransaction < :to) or (dtype!='Order' and dateOfTransaction=null)) and ");
				}
			}
			params.put("from", from);
			params.put("to", to);

		}

		q1.append("0=0");
		System.out.println(q1.toString());

		String countQ = q1.toString().replace("select absolutePath",
				"select count(*)");
		SQLQuery qQ = SpringUtil.getBeanOfType(Dao.class).getReadOnlySession()
				.createSQLQuery(countQ.toString());
		SQLQuery q = SpringUtil.getBeanOfType(Dao.class).getReadOnlySession()
				.createSQLQuery(q1.toString());
		Iterator<String> iter = params.keySet().iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			Object param = params.get(key);
			q.setParameter(key, param);
			qQ.setParameter(key, param);
		}
		int count = Integer.parseInt(qQ.uniqueResult().toString());

		q.setFirstResult(paseSize * page*3);
		q.setMaxResults(paseSize*3);
		List abss = q.list();
		int minsize = "/root/users/elieandsons/Applications/e-Shop/Orders/".length();
		List<String> ss = new ArrayList<String>(paseSize);
		for(Object s : abss){
			int last = s.toString().lastIndexOf('/');
			if(last > minsize){
				ss.add(s.toString().substring(0,last));
			}else{
				ss.add(s.toString());
			}
			
		}
		ex.setCount(count);
		return hydrate(ss);
	}

	private static List<OrderInfo> hydrate(List<String> abss) {
		if (abss.size() == 0) {
			return new ArrayList<OrderInfo>(0);
		}
		Map<String, List<Object[]>> r = new HashMap<String, List<Object[]>>();
		String q2 = "SELECT dtype, status,code,dateCreated,dateOfTransaction,firstName,lastName,email,mobile,phone,title, installment, company, fax,addressLine1,addressLine2,city,country,zipPostalCode, absolutePath,paymentMethod, total, bankName, chequeNo  FROM WFS_FILE WHERE dtype in('Order', 'SalesOrderEntry', 'BillingInformation') and (";
		for (String s : abss) {
			q2 = q2 + " absolutePath like '" + s + "%'";
			if (abss.indexOf(s) < abss.size() - 1) {
				q2 = q2 + " or ";
			}
			r.put(s, new ArrayList<Object[]>());
		}

		q2 = q2 + ") order by absolutePath";
		List ll = SpringUtil.getBeanOfType(Dao.class).getReadOnlySession()
				.createSQLQuery(q2).list();

		for (Object o : ll) {
			Object[] ao = (Object[]) o;
			add(r, ao);
		}

		List<OrderInfo> res = new ArrayList<OrderInfo>();
		Iterator<String> iter = r.keySet().iterator();
		while (iter.hasNext()) {
			String abs = iter.next();
			res.add(createInfo(r.get(abs), abs));
		}

		return res;
	}

	public OrderInfo getOrder(String code) {

		String q1 = "select absolutePath from WFS_FILE where code='" + code
				+ "' and dtype='Order'";
		Session s = SpringUtil.getBeanOfType(Dao.class).getReadOnlySession();
		String abs = (String) s.createSQLQuery(q1).uniqueResult();

		List<String> abss = new ArrayList<String>(1);
		abss.add(abs);
		List<OrderInfo> r = hydrate(abss);
		if (r.size() > 0) {
			return r.get(0);
		} else {
			return null;
		}

	}

	public String getNameForFSCode(String fsCode) {
		String query = "select firstName, lastName from WFS_FILE where dtype='Order' and code='"
				+ fsCode + "'";

		Session s = SpringUtil.getBeanOfType(Dao.class).getReadOnlySession();
		Object[] abs = (Object[]) s.createSQLQuery(query).uniqueResult();

		return abs[0] + " " + abs[1];

	}

	public List<FSCodeVO> getFSCodes() {
		String query = "select code, firstName, lastName, dtype,absolutePath from WFS_FILE where dtype='Order' or dtype='BillingInformation' order by absolutePath";

		Session s = SpringUtil.getBeanOfType(Dao.class).getReadOnlySession();
		List l = s.createSQLQuery(query).list();
		List<FSCodeVO> result = new ArrayList<FSCodeVO>(l.size());
		Map<String, FSCodeVO> data = new HashMap<String, FSCodeVO>();
		for (Object o : l) {
			Object ao[] = (Object[]) o;

			String dtype = (String) ao[3];
			if ("Order".equals(dtype)) {
				FSCodeVO vo = new FSCodeVO();
				vo.setFsCode((String) ao[0]);
				data.put((String) ao[4], vo);
			}
		}

		for (Object o : l) {
			Object ao[] = (Object[]) o;

			String dtype = (String) ao[3];
			if ("BillingInformation".equals(dtype)) {
				String abs = ((String) ao[4]).replace("/billing", "");

				FSCodeVO vo = data.get(abs);
				if (vo != null) {
					String firstName = (String) ao[1];
					String lastName = (String) ao[2];
					String customer = firstName;
					if (lastName != null) {
						customer = customer + " " + lastName;
					}

					vo.setCustomer(customer);
				}
			}
		}

		Iterator<String> iter = data.keySet().iterator();
		while (iter.hasNext()) {
			FSCodeVO vo = data.get(iter.next());
			result.add(vo);
		}

		return result;

	}

	private static OrderInfo createInfo(List l, String abs) {
		OrderInfo info = new OrderInfo();
		for (Object o : l) {
			Object[] ao = (Object[]) o;
			String dtype = (String) ao[0];
 
			if ("Order".equals(dtype)) {
				info.setDateCreated((Date) ao[3]);
				info.setDateOfTransaction((Date) ao[4]);
				info.setFsCode(ao[2]!=null?(String) ao[2]:"");
				info.setInstallment((BigDecimal) ao[11]);
				info.setStatus((Integer) ao[1]);
				info.setTitle(ao[10]!=null?(String) ao[10]:"");
				info.setAbsolutePath(ao[19]!=null?(String) ao[19]:"");
				info.setPaymentMethod(ao[20]!=null?(String) ao[20]:"");
				info.setTotal((BigDecimal) ao[21]);
				info.setTax(new BigDecimal(15));
				info.setBankName(ao[22]!=null?(String)ao[22]:"");
				info.setAccountNumber(ao[23]!=null?(String)ao[23]:"");
				

			} else if ("BillingInformation".equals(dtype)) {
				info.setEmail(ao[7]!=null?(String) ao[7]:"");
				info.setFirstName(ao[5]!=null?(String) ao[5]:"");
				info.setLastName(ao[6]!=null?(String) ao[6]:"");
				info.setMobile(ao[8]!=null?(String) ao[8]:"");
				info.setPhone(ao[9]!=null?(String) ao[9]:"");
				info.setAbsolutePath(abs);
				info.setAddressLine1(ao[14]!=null?(String) ao[14]:"");
				info.setAddressLine2(ao[15]!=null?(String) ao[15]:"");
				info.setCity(ao[16]!=null?(String) ao[16]:"");
				info.setCompany(ao[12]!=null?(String) ao[12]:"");
				info.setCountry(ao[17]!=null?(String) ao[17]:"");
				info.setFax(ao[13]!=null?(String) ao[13]:"");
				info.setZipPostalCode(ao[18]!=null?(String) ao[18]:"");

			} else if ("SalesOrderEntry".equals(dtype)) {

			}
		}
		return info;
	}

	public List<OrderLine> getLines(String order) {
		String ql = "select absolutePath from WFS_FILE where dtype='Order' and code='"
				+ order + "'";

		String abs = SpringUtil.getBeanOfType(Dao.class).getReadOnlySession()
				.createSQLQuery(ql).uniqueResult().toString();

		String q2 = "select productCode,title, price, quantity, taxRate, total, subTotal, options from WFS_FILE where dtype='SalesOrderEntry' and absolutePath like '"
				+ abs + "/%'";

		List l = SpringUtil.getBeanOfType(Dao.class).getReadOnlySession()
				.createSQLQuery(q2).list();
		List<OrderLine> result = new ArrayList<OrderLine>(l.size());
		for (Object o : l) {
			Object[] ao = (Object[]) o;
			OrderLine line = new OrderLine();
			line.setCode(ao[0]!=null?(String) ao[0]:"");
			line.setOptions(ao[7]!=null?(String) ao[7]:"");
			line.setTitle((String) ao[1]);
			line.setPrice((BigDecimal) ao[2]);
			line.setQty((BigDecimal) ao[3]);
			line.setSubTotal((BigDecimal) ao[6]);
			line.setTaxRate((BigDecimal) ao[4]);
			line.setTotal((BigDecimal) ao[5]);
			result.add(line);
		}
		return result;
	}
	public ContractDetail loadContract(String orderAbs) {
		String q = "select " +
				"firstName, " +
				"lastName, " +
				"addressLine1," +
				"addressLine2," +
				"mobile," +
				"email," +
				"phone, " +
				"value,"+ 
				"code," +
				"total," +
				"installment," +
				"joiningFee," +
				"pointOfSale," +
				"owner," +
				"chequeNo," +
				"bankName,"+ 
				"productCode," +
				"title, " +
				"nic, " +
				"dateOfTransaction, " +
				"dtype, " +
				"name, " +
				"paymentMethod from WFS_FILE where absolutePath like '"
				+ orderAbs + "%'";

		List l = SpringUtil.getBeanOfType(Dao.class).getReadOnlySession()
				.createSQLQuery(q).list();
		ContractDetail detail = new ContractDetail();

		for (Object o : l) {
			Object ao[] = (Object[]) o;

			String dtype = (String) ao[20];
			String name = (String) ao[21];
			if ("BillingInformation".equals(dtype)) {
				detail.setContactFirstName(ao[0]!=null?(String) ao[0]:"");
				detail.setContactLastName(ao[1]!=null?(String) ao[1]:"");
				detail.setContactAddressLine1(ao[2]!=null?(String) ao[2]:"");
				detail.setContactAddressLine2(ao[3]!=null?(String) ao[3]:"");
				detail.setContactMobile(ao[4]!=null?(String) ao[4]:"");
				detail.setContactEmail(ao[5]!=null?(String) ao[5]:"");
				detail.setContactTel(ao[6]!=null?(String) ao[6]:"");
				detail.setContactNIC(ao[18]!=null?(String) ao[18]:"");
			} else if ("Order".equals(dtype)) {
				detail.setFsCode(ao[8]!=null?(String) ao[8]:"");
				detail.setTotal((BigDecimal) ao[9]);
				detail.setInstallment((BigDecimal) ao[10]);
				detail.setJoiningFee((BigDecimal) ao[11]);
				detail.setPointOfSale(ao[12]!=null?(String) ao[12]:"");
				detail.setSalesAgent(ao[13]!=null?(String) ao[13]:"");
				detail.setAccountNumber(ao[14]!=null?(String) ao[14]:"");
				detail.setBankName(ao[15]!=null?(String) ao[15]:"");
				detail.setPaymentMethod(ao[22]!=null?(String) ao[22]:"");
				Date dateOfTransaction = (Date) ao[19];
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.MONTH, 6);
				Date effectiveDate = cal.getTime();
				detail.setEffectiveDate(effectiveDate);
				detail.setRegistrationDate(dateOfTransaction);
			} else if ("SalesOrderEntry".equals(dtype)) {
				detail.setCode(ao[16]!=null?(String) ao[16]:"");
				detail.setPlanDetail(ao[17]!=null?(String) ao[17]:"");
			} else if ("Value".equals(dtype) && "applicationForm".equals(name)) {
				String value = (String) ao[7];
				Map<String, String> d = BillingInformation.buildMap(value);
				detail.setPrincipalFirstName(d.get("pfullName")!=null? d.get("pfullName"):"");
				detail.setPrincipalLastName(d.get("pSurname")!=null? d.get("pSurname"):"");
				detail.setPrincipalAddressLine1(d.get("presi")!=null? d.get("presi"):"");
				detail.setPrincipalAddressLine2(d.get("presi2")!=null? d.get("presi2"):"");

				detail.setPrincipalEmail(d.get("email")!=null? d.get("email"):"");
				detail.setPrincipalMobile(d.get("pcell")!=null? d.get("pcell"):"");
				detail.setPrincipalNIC(d.get("pidnumber")!=null? d.get("pidnumber"):"");
				detail.setPrincipalTel(d.get("ptelhome")!=null? d.get("ptelhome"):"");
				detail.setSpouseLastName(d.get("sfullName")!=null? d.get("sfullName"):"");
				detail.setSpouseNIC(d.get("sidnumber")!=null? d.get("sidnumber"):"");

				for (int i = 1; i <= 7; i++) {
					String dn = d.get("c" + i + "name");
					//String dg = d.get("c" + i + "gender");
					Dependent dep = new Dependent();
					//dep.setGender(dg);
					dep.setName(dn);
					if (StringUtil.isNotEmpty(dn)) {
//						String did = "";
//						for (int j = 1; j <= 14; j++) {
//							did = did + d.get(("c" + i) + j);
//						}
						dep.setNic(d.get("c" + i + "1"));
						detail.getDependants().add(dep);
					} else {
						break;
					}
				}

			}
		}

		return detail;
	}

	public List<OrderInfo> searchOrders(String term) {

		System.out.println("Start Searching term at "
				+ System.currentTimeMillis());

		List<String> result = new ArrayList<String>();
		if (!StringUtil.isNotEmpty(term)) {
			return new ArrayList<OrderInfo>(0);
		}
		try {

			String[] terms = StringUtil.split(term, ",");
			String query = "select absolutepath from WFS_FILE where dtype ='Order' and (";
			for (String field : FIELDS) {
				for (String t : terms) {
					if (StringUtil.isNotEmpty(t))
						query = query + "LOWER(" + field + ")" + " like '%"
								+ t.trim() + "%' or ";
				}

			}

			query = query + " 0=-1 )";

			System.out.println(query);

			BasicDataSource ds = SpringUtil
					.getBeanOfType(BasicDataSource.class);
			Connection con = null;
			ResultSet rs = null;

			PreparedStatement stmpt = null;
			try {
				con = ds.getConnection();

				stmpt = con.prepareStatement(query);
				stmpt.setMaxRows(10);
				rs = stmpt.executeQuery();

				while (rs.next()) {
					String code = rs.getString(1);
					if (StringUtil.isNotEmpty(code)) {
						result.add(code);
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (stmpt != null) {
					stmpt.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (con != null) {
					con.close();
				}
			}

		} catch (Exception e) {
			throw new UIException(e);
		}
		if (result.size() == 0) {
			return new ArrayList<OrderInfo>(0);
		}

		return hydrate(result);

	}

	private static void add(Map<String, List<Object[]>> r, Object[] ao) {
		String abs = (String) ao[19];

		Iterator<String> iter = r.keySet().iterator();
		while (iter.hasNext()) {
			String p = iter.next();
			if (abs.startsWith(p)) {
				r.get(p).add(ao);
				return;
			}
		}

	}

}
