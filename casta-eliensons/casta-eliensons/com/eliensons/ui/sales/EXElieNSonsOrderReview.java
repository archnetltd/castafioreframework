package com.eliensons.ui.sales;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.castafiore.accounting.Account;
import org.castafiore.accounting.CashBook;
import org.castafiore.accounting.CashBookEntry;
import org.castafiore.designable.CartItem;
import org.castafiore.designable.EXMiniCart;
import org.castafiore.designable.checkout.EXCheckout;
import org.castafiore.designable.checkout.EXOrderReview;
import org.castafiore.designable.checkout.EXPaymentInformation;
import org.castafiore.inventory.orders.OrderService;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.security.User;
import org.castafiore.shoppingmall.checkout.BillingInformation;
import org.castafiore.shoppingmall.checkout.Order;
import org.castafiore.shoppingmall.checkout.SalesOrderEntry;
import org.castafiore.shoppingmall.delivery.Delivery;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.shoppingmall.merchant.ShoppingMallMerchant;
import org.castafiore.shoppingmall.reports.EXJasperReports;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.File;

import com.eliensons.ui.plans.EXElieNSonsApplicationForm;
import com.eliensons.ui.plans.ElieAndSonsJasperSource;

public class EXElieNSonsOrderReview extends EXOrderReview{

	public EXElieNSonsOrderReview(String name, EXMiniCart cart, double delivery) {
		super(name, cart, delivery);
		//getDescendentByName("rtdelivery").setDisplay(false);
		((EXXHTMLFragment)getDescendentByName("foo")).setTemplateLocation("templates/EXElieNSonsOrderFooter.xhtml");
		//List<CartItem> items= cart.getItems();
		
		// TODO Auto-generated constructor stub
		getDescendentOfType(EXTable.class).setModel(new ElieNSonsCartDetailModel(cart));
		getDescendentOfType(EXTable.class).setCellRenderer(new ElieNSonsCartDetailCellRenderer());
	}
	
	
	public void createOrder(Map<String, String> data){
		String username = "elieandsons";
		
		Merchant merchant = MallUtil.getMerchant(username);
		ShoppingMallMerchant m = merchant.getManager();
		Date invoiceDate = new Date(Long.parseLong(data.get("dateTransaction")));
		String pos = data.get("pos");
		BigDecimal installment = new BigDecimal(data.get("installment"));
		String source = "Other";
		BigDecimal joiningFee = new BigDecimal(data.get("joiningFee"));
		String agent = data.get("salesagent");
		Date startInstallmentDate = new Date(Long.parseLong(data.get("dateStartInstallment")));
		String icode = data.get("code");
		
	 	Order order = m.createOrder(username);
	 	order.setDateOfTransaction(invoiceDate);
	 	order.setPointOfSale(pos);
	 	order.setSource(source);
	 	order.setInstallment(installment);
	 	order.setJoiningFee(joiningFee);
	 	order.setStartInstallmentDate(startInstallmentDate);
	 	
	 	String productCode = data.get("planCode");
	 	String productDetail = data.get("planDetails");
		SalesOrderEntry entry = order.createFile(productCode, SalesOrderEntry.class);
		
		entry.setProductCode(productCode);
		entry.setCreditor(username);
		entry.setCurrency("MUR");
		
		entry.setDebitor(username);
		
		entry.setTitle(productDetail);
		entry.setSummary(productDetail);
		entry.setTaxRate(BigDecimal.ZERO);
		BigDecimal subTotalFraction = new BigDecimal(1);//.subtract(taxFraction);
		BigDecimal totalPrice = new BigDecimal(data.get("total"));
		entry.setTotal(totalPrice);
		BigDecimal subTotal = subTotalFraction.multiply(totalPrice);
		entry.setSubTotal(subTotal );
		entry.setPrice(totalPrice);
		entry.setQuantity(BigDecimal.ONE);
		entry.setWeight(BigDecimal.ZERO);
		entry.setHeight(BigDecimal.ZERO);
		entry.setLength(BigDecimal.ZERO);
		entry.setWidth(BigDecimal.ZERO);
		entry.setOptions("");
	 	
	 	order.setOwner(agent);

	 	order.setCode(icode);
	 	
	 	BillingInformation bif = order.createFile("billing", BillingInformation.class);
		bif.setCountry("Mauritius");
		bif.setEmail("");
		bif.setFirstName(data.get("contactFirstName"));
		bif.setLastName(data.get("contactLastName"));
		bif.setPhone(data.get("contactPhone"));
		bif.setMobile(data.get("contactMobile"));
		bif.setAddressLine1(data.get("contactAddress1"));
		bif.setNic(data.get("contactId"));
		bif.setTitle("");
		bif.setAddressLine2(data.get("contactAddress2"));

	 	bif.setOtherProperty("timeIn", new SimpleDateFormat("dd-MM-yyyy hh:mm").format(new Date(System.currentTimeMillis())));
	 	bif.setOtherProperty("timeOut", new SimpleDateFormat("dd-MM-yyyy hh:mm").format(new Date(System.currentTimeMillis())));
	 	

	 	String pt = data.get("paymentMethod");//getDescendentOfType(EXSelect.class).getValue().toString();
		order.setPaymentMethod(pt);
	
	 	order.setStatus(11);
		order.update();

	 	merchant.subscribe(bif, icode);

	 	order.setOrderedBy(bif.getUsername());
	 	order.save();

	 	CashBook book = merchant.createCashBook("DefaultCashBook");
	 	Account p = book.createAccount(new Date().getTime() + StringUtil.nextString(10).replace("/", "_"));
	 	//use
	 	p.setCode(icode);
	 	p.setTitle("Installments by " + bif.getFirstName() + " " + bif.getLastName() + " for invoice " + order.getCode());
	 	p.setSummary(bif.getUsername() + " " + bif.getFirstName() + " " + bif.getLastName() + " " + order.getEntries().get(0).getTitle());
	 	p.setAccType("Income");
	 	p.setCategory("Installment");
	 	p.setOwner(bif.getUsername());
	 	p.setCategory_4(order.getChequeNo());
	 	p.setDefaultValue(installment);
	 	
	 	
	 	
	 	CashBookEntry centry = book.createEntry(icode);
	 	centry.setAccount(p);
	 	centry.setTitle("First Installment");
	 	centry.setSummary("First Installment");
	 	centry.setDetail("First installment");
	 	centry.setCode(icode);
	 	Calendar cal = Calendar.getInstance();
	 	if(cal.get(Calendar.DATE) > 15){
	 		cal.add(cal.get(Calendar.MONTH), 1);
	 	}
	 	cal.set(Calendar.DATE, 1);
	 	centry.setDateOfTransaction(cal.getTime());
	 		centry.setTotal(installment);
	 	centry.setPointOfSale(pos);
	 	
	 	book.save();
	 	
	}
	
	
	public void placeOrder(){
		String username = "elieandsons";
		
		EXCheckout co = getAncestorOfType(EXCheckout.class);
		EXElieNSonsApplicationForm bi = co.getDescendentOfType(EXElieNSonsApplicationForm.class);
		EXPaymentInformation pi = co.getDescendentOfType(EXPaymentInformation.class);
		EXElieNSonsMiniCart cart = (EXElieNSonsMiniCart)getRoot().getDescendentOfType(EXMiniCart.class);
		BigDecimal installment = cart.getInstallment();
		EXSelect agent = (EXSelect)cart.getAncestorOfType(EXElieNSonsSales.class).getDescendentByName("agent");
	 	User uAgent = (User)agent.getValue();
		
		Merchant merchant = MallUtil.getMerchant(username);
		ShoppingMallMerchant m = merchant.getManager();
		
	 	Order order = m.createOrder(username);
	 	order.setDateOfTransaction(cart.getInvoiceDate());
	 	order.setPointOfSale(cart.getPos());
	 	order.setSource(cart.getSource());
	 	order.setInstallment(installment);
	 	order.setJoiningFee(cart.getJoiningFee());
	 	order.setStartInstallmentDate(cart.getStatePaymentDate());
	 	//String customerusername = co.getAttribute("customer");
	 	
	 	
	 	cart.createItems(order);
	 	order.setOwner(uAgent.getUsername());
	 	
	 	
	 	 
	 	
	 	String icode = ((EXInput)cart.getAncestorOfType(EXElieNSonsSales.class).getDescendentByName("invoiceNumber")).getValue().toString();
	 	order.setCode(icode);
	 	
	 	//co.getDescendentOfType(EXShippingInformation.class).createInfo(order);
	 	
	 	BillingInformation bif = bi.createInfo(order);
	 	bif.setOtherProperty("timeIn", new SimpleDateFormat("dd-MM-yyyy hh:mm").format(new Date(cart.getTimeIn())));
	 	bif.setOtherProperty("timeOut", new SimpleDateFormat("dd-MM-yyyy hh:mm").format(new Date(System.currentTimeMillis())));
	 	//bif.setOtherProperty("fsNumber", order.getCode());
	 	//bif.setOtherProperty("firstPaymentDate", new SimpleDateFormat("dd-MM-yyyy").format(order.getStartInstallmentDate()));
	 	
	 	
	 	//bif.setUsername();
	 
	 	Delivery delivery = order.createFile("delivery", Delivery.class);
	 	BigDecimal deliveryPrice = new BigDecimal(0);
	 	
	 	delivery.setPrice(deliveryPrice);
	 	delivery.setWeight(new BigDecimal(0));
	 	
	 	pi.fillOrder(order);
	 	order.setStatus(11);
	 	BigDecimal weight = new BigDecimal(0);
	 	delivery.setWeight(weight);
		order.update();
		
		
	 	
	 	//String m1 = IOUtil.getStreamContentAsString(Thread.currentThread().getContextClassLoader().getResourceAsStream("org/castafiore/designable/checkout/m1.xhtml"));
	 	
	 	//String customer = bi.getValue("cSurname") + " " + bi.getValue("cfullName");
	 	//sendMail(order, merchant, customer, "New order from " + merchant.getTitle(), merchant.getEmail(), bi.getValue("cemail"), m1);
	 	getChild("message").setText("Order correctly processed");
	 	getChild("back").setDisplay(false);
	 	getChild("continue").setText("Close");
	 	
	 	
	 	//use code of invoice as username
	 	merchant.subscribe(bif, icode);
	 	
	 	
	 	order.setOrderedBy(bif.getUsername());
	 	order.save();
	 	
	 	//subscribe user
	 	
	 	//create an account for user
	 	CashBook book = merchant.createCashBook("DefaultCashBook");
	 	Account p = book.createAccount(new Date().getTime() + StringUtil.nextString(10).replace("/", "_"));
	 	//use
	 	p.setCode(icode);
	 	p.setTitle("Installments by " + bif.getFirstName() + " " + bif.getLastName() + " for invoice " + order.getCode());
	 	p.setSummary(bif.getUsername() + " " + bif.getFirstName() + " " + bif.getLastName() + " " + order.getEntries().get(0).getTitle());
	 	p.setAccType("Income");
	 	p.setCategory("Installment");
	 	p.setOwner(bif.getUsername());
	 	p.setCategory_4(order.getChequeNo());
	 //	BigDecimal defaultValue = new BigDecimal(300);
	 	p.setDefaultValue(installment);
	 	
	 	
	 	
	 	CashBookEntry entry = book.createEntry(icode);
	 	entry.setAccount(p);
	 	entry.setTitle("First Installment");
	 	entry.setSummary("First Installment");
	 	entry.setDetail("First installment");
	 	entry.setCode(icode);
	 	Calendar cal = Calendar.getInstance();
	 	if(cal.get(Calendar.DATE) > 15){
	 		cal.add(cal.get(Calendar.MONTH), 1);
	 	}
	 	cal.set(Calendar.DATE, 1);
	 	entry.setDateOfTransaction(cal.getTime());
	 	if(cart.isPayingAdvance())
	 		entry.setTotal(installment);
	 	else
	 		entry.setTotal(BigDecimal.ZERO);
	 	
	 	entry.setPointOfSale(cart.getPos());
	 	
	 	book.save();
	 	
	 	
	 	//delete invoice
	 	try{
	 		File f = (File)SpringUtil.getRepositoryService().getFile(merchant.getAbsolutePath() + "/invoiceCodes/" + order.getCode(), Util.getRemoteUser());
	 		f.remove();
	 		merchant.save();
	 	}catch(Exception e){
	 		e.printStackTrace();
	 	}
	 	
	 	Container cont = getDescendentByName("continue");
	 	Container parent = cont.getParent();
	 	cont.remove();
	 	try{
	 	//parent.addChild(new EXContainer("continue", "a").setText("Download contract").setAttribute("href", ElieNSonsUtil.printReceipt(order)).setAttribute("target", "_blank"));
	 		EXJasperReports report = new EXJasperReports("continue", "a", new ElieAndSonsJasperSource(order));
	 		report.setText("Download Contract").setAttribute("href", ResourceUtil.getMethodUrl(report)).setAttribute("target", "_blank");
	 		parent.addChild(report);
	 		
	 		//parent.addChild(new EXJasperReports("continue", "a", new ElieAndSonsJasperSource(order)).setText("Download Contract").setAttribute("href", ));
	 	}catch(Exception e){
	 		e.printStackTrace();
	 		parent.addChild(new EXContainer("continue", "a").setText("There is a problem with the contract. We will contact you later").setAttribute("href", "#").setAttribute("target", "_blank"));
	 	}
	 	cart.setItems(new ArrayList<CartItem>());
	 	
	 	//index
	 	OrderService.indexOrder(order.getAbsolutePath());
	}
	
	
	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		if(container.getText().equalsIgnoreCase("Print Receipt")){
			//print receipt
//			try{
//			Order o = (Order)SpringUtil.getRepositoryService().getFile(container.getAttribute("order"), Util.getRemoteUser());
//			Workbook wb = ElieNSonsUtil.printReceipt(o);
//			
//			BinaryFile receipt = o.createFile("receipt.xls", BinaryFile.class);
//			OutputStream out = receipt.getOutputStream();
//			wb.write(out);
//			out.flush();
//			out.close();
//			o.save();
//			Container parent = container.getParent();
//			container.remove();
//			parent.addChild(new EXContainer(container.getName(), "a").setAttribute("href", ResourceUtil.getDownloadURL("ecm", receipt.getAbsolutePath())).setAttribute("target", "_blank").setText("Download receipt"));
//			parent.setRendered(false);
//			}catch(Exception e){
//				e.printStackTrace();
//			}
			
			
			
			
			
		}else{
			return super.ServerAction(container, request);
		}
		return true;
	}
	

}
