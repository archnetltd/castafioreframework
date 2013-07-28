package org.castafiore.shoppingmall.imports;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.format.CellTextFormatter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.castafiore.accounting.Account;
import org.castafiore.accounting.CashBook;
import org.castafiore.catalogue.Product;
import org.castafiore.persistence.Dao;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.security.Address;
import org.castafiore.security.User;
import org.castafiore.shoppingmall.checkout.BillingInformation;
import org.castafiore.shoppingmall.checkout.Order;
import org.castafiore.shoppingmall.checkout.SalesOrderEntry;
import org.castafiore.shoppingmall.delivery.Delivery;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.shoppingmall.merchant.MerchantSubscription;
import org.castafiore.shoppingmall.merchant.ShoppingMallMerchant;
import org.castafiore.spring.SpringUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.iterator.FileIterator;
import org.castafiore.wfs.service.QueryParameters;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;
import org.castafiore.wfs.types.Value;
import org.hibernate.Session;
import org.hibernate.StatelessSession;

public class OrderImporterTemplate extends AbstractExcelImporterTemplate{
	
	
	private Merchant root=null;; 
	
	private Map<String, Product> products = null;
	
	private String merchant;
	
	private Directory orders;
	
	private Directory users;

	public OrderImporterTemplate(){	}

	@Override
	public void init() {
		super.init();
		
		if(root == null){
			root = MallUtil.getMerchant(merchant);
			QueryParameters params = new QueryParameters().setEntity(Product.class).addSearchDir(ShoppingMallMerchant.MY_PRODUCTS_DIR.replace("$user", merchant));
			List<File> productss  = SpringUtil.getRepositoryService().executeQuery(params, merchant);
			products = new HashMap<String, Product>();
			for(File f : productss){
				
				products.put(((Product)f).getCode(), (Product)f);
			}
			orders = SpringUtil.getRepositoryService().getDirectory(ShoppingMallMerchant.ORDERS_DIR.replace("$user", merchant), merchant);
			
			users = SpringUtil.getRepositoryService().getDirectory("/root/users", merchant);
		}
		
	}
	

	private Product getProduct(String code){
		return products.get(code);
	}

	protected String gc(Row r, int c, String def){
		Cell ce = r.getCell(c);
		if(ce != null){
			if(ce.getCellType() == Cell.CELL_TYPE_STRING){
				if(StringUtil.isNotEmpty(ce.getStringCellValue())){
					return ce.getStringCellValue();
				} 
			}else{
				if(StringUtil.isNotEmpty(ce.getNumericCellValue())){
					
					if(HSSFDateUtil.isCellDateFormatted(ce)){
						if(StringUtil.isNotEmpty(ce.getDateCellValue())){
							Date d = ce.getDateCellValue();
							if(d.getYear() < 2000){
								d.setYear(d.getYear() + 2000);
							}
							return new SimpleDateFormat("dd.MM.yyyy").format(d);
						} 
					}else
						return ce.getNumericCellValue() + "";
				}
			}
		}
		return def;
	}
	
	@Override
	public Map<String, String> transform(Row r) {
		Map<String,String> data = new HashMap<String, String>();
		data.put("plan", gc(r,12, ""));
		data.put("plandetail", gc(r,13, ""));
		data.put("code", r.getCell(0).getStringCellValue());
		data.put("pointOfSale", "Other");
		data.put("dateOfTransaction", gc(r,24, ""));
		data.put("salesAgent", "elieandsons");
		data.put("dateFormat", "dd-MMM-yy");
		data.put("productQty", "1");
		data.put("productCode", gc(r,12, ""));
		
		data.put("pos", "Other");
		data.put("accountNumber", gc(r,20, ""));
		data.put("bankName", gc(r,21, ""));
		data.put("email", "");
		data.put("effDate", gc(r,24, ""));
		
		data.put("ptitle","");
		data.put("pfullName", gc(r,2, ""));
		data.put("pSurname", gc(r,1, ""));
		data.put("pdob", gc(r,3, ""));
		data.put("pgender", "");
		data.put("pstatus", "");
		data.put("pidnumber", gc(r,11,""));
		data.put("presi", gc(r,5,""));
		data.put("presi2", gc(r,6,""));
		data.put("ptelhome", gc(r,8,""));
		data.put("pcell", gc(r,10,""));
		
		
		data.put("ctitle","");
		data.put("cfullName", gc(r,2, ""));
		data.put("cSurname", gc(r,1, ""));
		data.put("cdob", gc(r,3, ""));
		data.put("cgender", "");
		data.put("cstatus", "");
		data.put("cidnumber", gc(r,11,""));
		data.put("cresi", gc(r,5,""));
		data.put("cresi2", gc(r,6,""));
		data.put("telHome", gc(r,8,""));
		data.put("telOffice", gc(r,9,""));
		data.put("cell", gc(r,10,""));
		
		data.put("stitle", "");
		data.put("sfullName", gc(r,25,""));
		data.put("sSurname", gc(r,1, ""));
		data.put("sdob", "");
		data.put("sgender", "");
		data.put("sstatus", "");
		data.put("sidnumber", "");
		
		
		
		data.put("c1surname", gc(r,27,""));
		data.put("c2surname", gc(r,28,""));
		data.put("c3surname", gc(r,29,""));
		data.put("c4surname", gc(r,30,""));
		data.put("c5surname", gc(r,31,""));
		data.put("c6surname", gc(r,32,""));
		data.put("default", r.getCell(14).getNumericCellValue() + "");
		data.put("installment", r.getCell(14).getNumericCellValue() + "");
		data.put("deposit", r.getCell(15).getNumericCellValue() + "");
		data.put("joining", r.getCell(16).getNumericCellValue() + "");
		data.put("advance", r.getCell(17).getNumericCellValue() + "");
		
		//data.put("installment", gc(r,16))
		String pt = gc(r,18, "");
		if(pt.equalsIgnoreCase("Standing Order")){
			pt = "Standing Order";
		}else{
			pt = "Cash";
		}
		data.put("paymentMethod", pt);
		
		return data;
	}

	static String[] fields = new String[]{
		"pos", "salesAgent", "accountNumber", "bankName","effDate",
		"ptitle","pfullName","pSurname","pdob","pgender","pstatus","email","ptelhome","pcell","pidnumber","presi","presi2",
		"ctitle","cfullName","cSurname","cidnumber","cemail","telHome","cell","cresi","cresi2","cdob",
		"stitle","sfullName","sSurname","sdob","sgender","sstatus","sidnumber",
		
		"c1surname","c1name","c11","c12","c13","c14","c15","c16","c17","c18","c19","c110","c111","c112","c113","c114","c1gender",
		"c2surname","c2name","c21","c22","c23","c24","c25","c26","c27","c28","c29","c210","c211","c212","c213","c214","c2gender",
		"c3surname","c3name","c31","c32","c33","c34","c35","c36","c37","c38","c39","c310","c311","c312","c313","c314","c3gender",
		"c4surname","c4name","c41","c42","c43","c44","c45","c46","c47","c48","c49","c410","c411","c412","c413","c414","c4gender",
		"c5surname","c5name","c51","c52","c53","c54","c55","c56","c57","c58","c59","c510","c511","c512","c513","c514","c5gender",
		"c6surname","c6name","c61","c62","c63","c64","c65","c66","c67","c68","c69","c610","c611","c612","c613","c614","c6gender"
	};
	
	public List<String> errors = new ArrayList<String>();

	private void importOrder(Map<String, String> data)throws Exception{
		
		if(data.containsKey("Info ->") || data.containsKey("Error ->")){
			errors.add(data.toString());
			return;
		}
		
		BigDecimal installment = new BigDecimal(data.get("installment"));
		BigDecimal joining = new BigDecimal(data.get("joining"));
		BigDecimal deposit = new BigDecimal(data.get("deposit"));
		BigDecimal advance = new BigDecimal(data.get("advance"));
		BigDecimal totalPrice = joining.add(deposit.add(advance));
		
		StatelessSession session = SpringUtil.getBeanOfType(Dao.class).getHibernateTemplate().getSessionFactory().openStatelessSession();
		
		//item 1
		Order order = orders.createFile(new Date().getTime() + "", Order.class);
		order.setCode(new Date().getTime() + "");
		order.setOrderedFrom(merchant);
		order.setStatus(9);
		order.setSource("Unknown");
		order.setInstallment(installment);
		order.setBankName(data.get("bankName"));
		order.setChequeNo(data.get("accountNumber"));
		
		order.setJoiningFee(joining);
	 	String productCode = data.get("productCode");
	 	String productQty = data.get("productQty");
	 	String productOptions = data.get("productOptions");
	 	//String sAgent = data.get("salesAgent");
	 	String dateFormat = data.get("dateFormat");
		String sInvoiceDate = data.get("dateOfTransaction");
		String pos = data.get("pointOfSale");
		String code = data.get("code");
		String gen = code;
		
		if("H".equalsIgnoreCase(productCode))
			productCode = "D";
		
	 	Product p = getProduct(productCode); 
	 	//User uAgent = getUser(sAgent);	
	 	SimpleDateFormat format = new SimpleDateFormat(dateFormat);
	 	try{
		
		Date dateOfTransaction = format.parse(sInvoiceDate);		
	 	order.setDateOfTransaction(dateOfTransaction);
	 	
	 	if(data.containsKey("3monthpromotion")){
	 		Calendar cal = Calendar.getInstance();
	 		cal.setTime(dateOfTransaction);
	 		cal.add(Calendar.MONTH, 3);
	 		order.setStartInstallmentDate(cal.getTime());
	 	}
	 	
	 	}catch(Exception e){
	 		order.setDateOfTransaction(new Date());
	 	}
	 	order.setPointOfSale(pos);
	 	//item :2
		SalesOrderEntry entry = order.createFile(productCode, SalesOrderEntry.class);
		entry.setProduct(p,new BigDecimal(productQty), productOptions);
		
		entry.setTotal(totalPrice);
	 	order.setOwner("elieandsons");
	 	order.setCode(code);
	 	//item :3
		BillingInformation bi = order.createFile("billing", BillingInformation.class);
		bi.setCountry("Mauritius");
		bi.setEmail(data.get("email"));
		bi.setFirstName(data.get("pSurname"));
		bi.setLastName(data.get("pfullName"));
		bi.setPhone(data.get("telHome"));
		bi.setMobile(data.get("cell"));
		bi.setAddressLine1(data.get( "cresi"));
		bi.setNic(data.get("pidnumber"));
		bi.setTitle(data.get("ptitle"));
		bi.setGender(data.get("pgender"));
		bi.setAddressLine2(data.get("cresi2"));
		bi.setUsername(gen);
		Calendar cal = Calendar.getInstance();
		try{
			cal.setTime(format.parse(data.get("pdob")));
		}catch(Exception e){
			
		}
		bi.setDateOfBirth(cal);
		bi.setMaritalStatus(data.get("pstatus"));
		//item: 4
		Value val = bi.createFile("applicationForm", Value.class);
		for(String s : fields){
			String value = data.get(s);	
			val.setString(val.getString() + "-:;:-" + s + ":" + value);
		}
		
		
		//item 5
	 	Delivery delivery = order.createFile("delivery", Delivery.class);
	 	BigDecimal deliveryPrice = new BigDecimal(0);
	 	delivery.setPrice(deliveryPrice);
	 	delivery.setWeight(new BigDecimal(0));
	 	String pt = data.get("paymentMethod");
		order.setPaymentMethod(pt);
	 	order.setStatus(11);
	 	BigDecimal weight = new BigDecimal(0);
	 	delivery.setWeight(weight);
	 	
		order.update();
		order.setOrderedBy(bi.getUsername());
		session.insert(order);
		session.insert(delivery);
		session.insert(bi);
		session.insert(val);
		session.insert(entry);
		
		
		
		FileIterator<Directory> cats = root.getSubscriptionCategories();
		Directory dir = cats.get(0);
		
		
		
		
		//item 6
		MerchantSubscription ms =dir.createFile(bi.getUsername(), MerchantSubscription.class);
		ms.setAddressLine1(bi.getAddressLine1());
		ms.setAddressLine2(bi.getAddressLine2());
		ms.setCity(bi.getCity());
		ms.setCompany(bi.getCompany());
		ms.setCountry(bi.getCountry());
		ms.setEmail(bi.getEmail());
		ms.setFax(bi.getFax());
		ms.setFirstName(bi.getFirstName());
		ms.setLastName(bi.getLastName());
		ms.setMobile(bi.getMobile());
		ms.setPhone(bi.getPhone());
		ms.setZipPostalCode(bi.getZipPostalCode());
		ms.setMerchantUsername(gen);
		ms.setOwner(gen);
		ms.setTitle(bi.getTitle());
		ms.setGender(bi.getGender());
		ms.setMaritalStatus(bi.getMaritalStatus());
		ms.setSubscriber(gen);
		session.insert(ms);
		//item :7
		User u = new User();
		
		//item :8
		Address a = new Address();
		a.setCity(ms.getCity());
		a.setCountry(ms.getCountry());
		a.setDefaultAddress(true);
		a.setLine1(ms.getAddressLine1());
		a.setLine2(ms.getAddressLine2());
		a.setPostalCode(ms.getZipPostalCode());
		u.addAddress(a);
		u.setEmail(ms.getEmail());
		u.setFax(ms.getFax());
		u.setFirstName(ms.getFirstName());
		u.setGender(ms.getGender());
		u.setLastName(ms.getLastName());
		u.setMaritalStatus(ms.getMaritalStatus());
		u.setMobile(ms.getMobile());
		//the subscribed user do not form part of the organization
		//u.setOrganization(Util.getLoggedOrganization());
		u.setPhone(ms.getPhone());
		u.setTitle(ms.getTitle());
		u.setDateOfBirth(ms.getDateOfBirth());
		u.setNic(ms.getNic());
		u.setUsername(gen);
		u.setPassword(gen);
	 	//root.subscribe(bi);
	 	session.insert(u);
	 	session.insert(a);
	 	
	 	//item :9
	 	CashBook book = root.createCashBook("DefaultCashBook");
	 	
	 	//item :10
	 	Account account = book.createAccount(new Date().getTime() + StringUtil.nextString(10).replace("/", "_"));
	 	account.setCode(order.getCode());
	 	account.setTitle("Installments by " + bi.getFirstName() + " " + bi.getLastName() + " for invoice " + order.getCode());
	 	account.setSummary(bi.getUsername() + " " + bi.getFirstName() + " " + bi.getLastName() + " " + order.getEntries().get(0).getTitle());
	 	account.setAccType("Income");
	 	account.setCategory("Installment");
	 	account.setOwner(bi.getUsername());
	 	account.setCategory_4(order.getChequeNo());
	 	
	 	account.setDefaultValue(installment);
	 	session.insert(account);
	 	
	 	//create CashBookEntry with installment
	 	
	 	Directory user = users.createFile(gen, Directory.class);//new Directory();
		user.setOwner(gen);
		
		session.insert(user);
	 	
	}
	
	
	public String getMerchant() {
		return merchant;
	}

	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}

	@Override
	public void doImportInstance(Map<String, String> data) {
		try{
			importOrder(data);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	@Override
	public void flush() {
		//root.save();
		Session s = SpringUtil.getBeanOfType(Dao.class).getSession();
	
		s.flush();
		s.clear();
		System.gc();
	}



	

}
