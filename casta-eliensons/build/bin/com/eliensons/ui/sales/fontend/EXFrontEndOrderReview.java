package com.eliensons.ui.sales.fontend;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.castafiore.accounting.Account;
import org.castafiore.accounting.CashBook;
import org.castafiore.designable.CartItem;
import org.castafiore.designable.EXMiniCart;
import org.castafiore.designable.checkout.EXCheckout;
import org.castafiore.persistence.Dao;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.checkout.BillingInformation;
import org.castafiore.shoppingmall.checkout.Order;
import org.castafiore.shoppingmall.delivery.Delivery;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.shoppingmall.merchant.ShoppingMallMerchant;
import org.castafiore.spring.SpringUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.File;

import com.eliensons.ui.plans.EXElieNSonsApplicationForm;
import com.eliensons.ui.sales.EXElieNSonsMiniCart;
import com.eliensons.ui.sales.EXElieNSonsOrderReview;

public class EXFrontEndOrderReview extends EXElieNSonsOrderReview {

	public EXFrontEndOrderReview(String name, EXMiniCart cart, double delivery) {
		super(name, cart, delivery);
		
	}
	
	
	private String getNextCode(){
		String sql ="select MAX(code) from WFS_FILE where dtype='Order'";
		
		Object result = SpringUtil.getBeanOfType(Dao.class).getReadOnlySession().createSQLQuery(sql).uniqueResult();
		String ny =Calendar.getInstance().get(Calendar.YEAR) + "";
		if(result != null){
			String last = result.toString();
			String[] parts = StringUtil.split(last, "/");
			int start = 1;
			if(parts.length >1){
				String ye = parts[1];
				
				String sStart = "0001";
				if(!ye.equals(ny)){
					ye = ny;
				}else{
					start = new Integer(parts[0].replace("FS", "").trim()) + 1;
					sStart = start + "";
					while(sStart.length() < 4){
						sStart = "0" + sStart;
					}
				}
				
				return "FS" + sStart + "/" + ny; 
			}
		}
		
		return "Unknown";
		
//		QueryParameters params = new QueryParameters();
//		params.setEntity(Value.class).addSearchDir(codes.getAbsolutePath()).addOrder(Order.desc("value")).setFirstResult(0).setMaxResults(1);
//		
//		
//		List result = SpringUtil.getRepositoryService().executeQuery(params, Util.getRemoteUser());
//		if(result.size() > 0){
//			String name =((Value)result.get(0)).getName();
//			if(name.endsWith(currentMonth)){
//				String[] parts = StringUtil.split(name, "/");
//				cur = Integer.parseInt(parts[1]);
//				
//			}
//		}
	}
	
	public void placeOrder(){
		String username = "elieandsons";
		
		EXCheckout co = getAncestorOfType(EXCheckout.class);
		EXElieNSonsApplicationForm bi = co.getDescendentOfType(EXElieNSonsApplicationForm.class);
		//EXPaymentInformation pi = co.getDescendentOfType(EXPaymentInformation.class);
		EXElieNSonsMiniCart cart = (EXElieNSonsMiniCart)getRoot().getDescendentOfType(EXMiniCart.class);
		//EXSelect agent = (EXSelect)cart.getAncestorOfType(EXElieNSonsSales.class).getDescendentByName("agent");
		String agent = cart.getAgent();
	 	//User uAgent = (User)agent.getValue();
		
		Merchant merchant = MallUtil.getMerchant(username);
		ShoppingMallMerchant m = merchant.getManager();
		
	 	Order order = m.createOrder(username);
	 	order.setDateOfTransaction(cart.getInvoiceDate());
	 	order.setPointOfSale(cart.getPos());
	 	order.setSource("Web");
	 	
	 	//String customerusername = co.getAttribute("customer");
	 	
	 	
	 	cart.createItems(order);
	 	order.setOwner(agent);
	 	
	 	
	 	 String icode = getNextCode();
	 	
	 //	String icode = ((EXInput)cart.getAncestorOfType(EXElieNSonsSales.class).getDescendentByName("invoiceNumber")).getValue().toString();
	 	order.setCode(icode);
	 	
	 	//co.getDescendentOfType(EXShippingInformation.class).createInfo(order);
	 	
	 	BillingInformation bif = bi.createInfo(order);
	 	//bif.setUsername();
	 
	 	Delivery delivery = order.createFile("delivery", Delivery.class);
	 	BigDecimal deliveryPrice = new BigDecimal(0);
	 	
	 	delivery.setPrice(deliveryPrice);
	 	delivery.setWeight(new BigDecimal(0));
	 	
	 	//pi.fillOrder(order);
	 	order.setStatus(10);
	 	BigDecimal weight = new BigDecimal(0);
	 	delivery.setWeight(weight);
		order.update();
		
		
	 	
	 	//String m1 = IOUtil.getStreamContentAsString(Thread.currentThread().getContextClassLoader().getResourceAsStream("org/castafiore/designable/checkout/m1.xhtml"));
	 	
	 	//String customer = bi.getValue("pSurname") + " " + bi.getValue("pfullName");
	 	//sendMail(order, merchant, customer, "New order from " + merchant.getTitle(), merchant.getEmail(), bi.getValue("email"), m1);
	 	getChild("message").setText("Order correctly processed");
	 	getChild("back").setDisplay(false);
	 	getChild("continue").setText("Close");
	 	cart.setItems(new ArrayList<CartItem>());
	 	
	 	//use code of invoice as username
	 	merchant.subscribe(bif, icode);
	 	
	 	
	 	order.setOrderedBy(bif.getUsername());
	 	order.save();
	 	
	 	//subscribe user
	 	
	 	//create an account for user
	 	CashBook book = MallUtil.getCurrentMerchant().createCashBook("DefaultCashBook");
	 	Account p = book.createAccount(new Date().getTime() + StringUtil.nextString(10).replace("/", "_"));
	 	//use
	 	p.setCode(icode);
	 	p.setTitle("Installments by " + bif.getFirstName() + " " + bif.getLastName() + " for invoice " + order.getCode());
	 	p.setSummary(bif.getUsername() + " " + bif.getFirstName() + " " + bif.getLastName() + " " + order.getEntries().get(0).getTitle());
	 	p.setAccType("Income");
	 	p.setCategory("Installment");
	 	p.setOwner(bif.getUsername());
	 	p.setCategory_4(order.getChequeNo());
	 	BigDecimal defaultValue = new BigDecimal(300);
	 	p.setDefaultValue(defaultValue);
	 	
	 	
	 	
	 	book.save();
	 	
	 	//delete invoice
	 	try{
	 		File f = (File)SpringUtil.getRepositoryService().getFile(merchant.getAbsolutePath() + "/invoiceCodes/" + order.getCode(), Util.getRemoteUser());
	 		f.remove();
	 		merchant.save();
	 	}catch(Exception e){
	 		e.printStackTrace();
	 	}
	 	
	 	getDescendentByName("continue").setText("Print Receipt").setAttribute("order", order.getAbsolutePath());
	 	
	}

}
