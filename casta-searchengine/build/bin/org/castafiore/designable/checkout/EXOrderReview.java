package org.castafiore.designable.checkout;

import groovy.text.SimpleTemplateEngine;
import groovy.text.Template;
import groovy.text.TemplateEngine;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.castafiore.catalogue.Product;
import org.castafiore.designable.CartDetailModel;
import org.castafiore.designable.CartItem;
import org.castafiore.designable.CurrencySensitive;
import org.castafiore.designable.EXMiniCart;
import org.castafiore.searchengine.EXSearchEngineApplication;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.security.User;
import org.castafiore.shoppingmall.checkout.Order;
import org.castafiore.shoppingmall.checkout.OrderEntry;
import org.castafiore.shoppingmall.delivery.Delivery;
import org.castafiore.shoppingmall.merchant.FinanceUtil;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.shoppingmall.merchant.ShoppingMallMerchant;
import org.castafiore.shoppingmall.orders.OrdersUtil;
import org.castafiore.shoppingmall.orders.OrdersWorkflow;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ComponentVisitor;
import org.castafiore.utils.IOUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.codehaus.groovy.control.CompilationFailedException;
import org.hibernate.criterion.Restrictions;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class EXOrderReview extends EXContainer implements Event, CurrencySensitive{

	public EXOrderReview(String name, EXMiniCart cart, double delivery) {
		super(name, "fieldset");
		addClass("ui-widget-content");
		addChild(new EXContainer("message", "h4"));
		EXTable table = new EXTable("", new CartDetailModel(cart));
		table.setCellRenderer(new OrderReviewCellRenderer());
		addChild(table);
		
		double sTotal = cart.getSubTotal().doubleValue();
		double total = cart.getTotal().doubleValue();
		double vat = total - sTotal;
		//double delivery = 0;
		//delivery = getAncestorOfType(EXCheckout.class).getDescendentOfType(EXShippingMethod.class).getPrice().doubleValue();
		total = total + delivery;
	 	
		EXXHTMLFragment footer = new EXXHTMLFragment("foo", "templates/designable/EXOrderFooter.xhtml");
		footer.addChild(new EXContainer("rtsubTotal", "span").setText(StringUtil.toCurrency(FinanceUtil.getCurrentCurrency(),sTotal)));

		footer.addChild(new EXContainer("rtvat", "span").setText(StringUtil.toCurrency(FinanceUtil.getCurrentCurrency(),vat)));
		
		footer.addChild(new EXContainer("rttotal", "span").setText(StringUtil.toCurrency(FinanceUtil.getCurrentCurrency(),total)));
		
		footer.addChild(new EXContainer("rtdelivery", "span").setText(StringUtil.toCurrency(FinanceUtil.getCurrentCurrency(),delivery)));
		addChild(footer);
		
		addChild(new EXButton("back", "Back").addEvent(this, Event.CLICK).setStyleClass("ui-state-default ui-corner-all fg-button-small").setStyle("float", "left").setStyle("margin-top", "10px"));
		addChild(new EXButton("continue", "Place Order").addEvent(this, Event.CLICK).setStyleClass("ui-state-default ui-corner-all fg-button-small").setStyle("float", "right").setStyle("margin-top", "10px").setStyle("width", "120px"));
	}
	
	public void reInit( EXMiniCart cart){
		getDescendentOfType(EXTable.class).setModel(new CartDetailModel(cart));
		double sTotal = cart.getSubTotal().doubleValue();
		double total = cart.getTotal().doubleValue();
		double vat = total - sTotal;
		double delivery = 0;
		
		delivery = getAncestorOfType(EXCheckout.class).getDescendentOfType(EXShippingMethod.class).getPrice().doubleValue();
		
		total = total + delivery;
	 	
		Container footer = getChild("foo");
		footer.addChild(footer.getChild("rtsubTotal").setText(StringUtil.toCurrency(FinanceUtil.getCurrentCurrency(),sTotal)));

		footer.addChild(footer.getChild("rtvat").setText(StringUtil.toCurrency(FinanceUtil.getCurrentCurrency(),vat)));
		
		footer.addChild(footer.getChild("rttotal").setText(StringUtil.toCurrency(FinanceUtil.getCurrentCurrency(),total)));
		
		footer.addChild(footer.getChild("rtdelivery").setText(StringUtil.toCurrency(FinanceUtil.getCurrentCurrency(), delivery)));
		addChild(footer);
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask(container.getAncestorOfType(EXOrderReview.class)).makeServerRequest(this);
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		EXCheckout co = getAncestorOfType(EXCheckout.class);
		if(container.getName().equals("back")){
			Container c =co.getChild("payment");
			c.getChildByIndex(1).setDisplay(true);
			setDisplay(false);
		}else{
			if(container.getText().equals("Close")){
				request.put("torem", container.getAncestorOfType(EXCheckout.class).getId());
				Container c = getAncestorOfType(EXCheckout.class);
				Container parent = c.getParent();
				c.remove();
				parent.setRendered(false);
			}else{
				placeOrder();
			}
		}
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		if(request.containsKey("torem")){
			container.mergeCommand(new ClientProxy("#" + request.get("torem")).fadeOut(100));
		}
		
	}
	
	
	public void placeOrder(){
		String path = (String)getRoot().getConfigContext().get("portalPath");
		String username = null;
		if(path != null){
			username = MallUtil.getEcommerceMerchant();
		}
		else
			username = getAncestorOfType(EXCheckout.class).getAttribute("merchant");
		EXCheckout co = getAncestorOfType(EXCheckout.class);
		EXBillingInformation bi = co.getDescendentOfType(EXBillingInformation.class);
		Merchant merchant = MallUtil.getMerchant(username);
		ShoppingMallMerchant m = merchant.getManager();
	 	Order order = m.createOrder(username);
	 	EXPaymentInformation pi = co.getDescendentOfType(EXPaymentInformation.class);
	 	
	 	EXShippingMethod ship = co.getDescendentOfType(EXShippingMethod.class);
	 	EXMiniCart cart = (EXMiniCart)getRoot().getDescendentById(getAncestorOfType(EXCheckout.class).getAttribute("cartid"));
	 	cart.createItems(order);
	 	
	 	
	 	
	 	co.getDescendentOfType(EXShippingInformation.class).createInfo(order);
	 	
	 	bi.createInfo(order);
	 	
	 	Delivery delivery = order.createFile("delivery", Delivery.class);
	 	BigDecimal deliveryPrice = ship.getPrice();
	 	
	 	delivery.setPrice(deliveryPrice);
	 	delivery.setWeight(ship.getTotalWeight());
	 	pi.fillOrder(order);
	 	order.setStatus(11);
	 	
	 	
	 	
	 	List<OrderEntry> entries = order.getEntries();
	 	
	 	BigDecimal weight = new BigDecimal(0);
		for(OrderEntry entry : entries){
			String code = entry.getProductCode();
			BigDecimal qty = entry.getQuantity();
			Product p = (Product)SpringUtil.getRepositoryService().executeQuery(new QueryParameters().setEntity(Product.class).addRestriction(Restrictions.eq("code", code)), Util.getRemoteUser()).get(0);
			BigDecimal currentQty = p.getCurrentQty();
			if(currentQty != null){
				p.setCurrentQty(currentQty.subtract(qty));
				p.save();
				weight = weight.add(p.getWeight().multiply(qty));
			}
		}
	 	delivery.setWeight(weight);
		order.update();
	 	order.save();
		
	 	String m1 = IOUtil.getStreamContentAsString(Thread.currentThread().getContextClassLoader().getResourceAsStream("org/castafiore/designable/checkout/m1.xhtml"));
	 	String customer = bi.getValue("lastname") + " " + bi.getValue("firstname");
	 	sendMail(order, merchant, customer, "New order from " + merchant.getTitle(), merchant.getEmail(), bi.getValue("email"), m1);
	 	
	 	String m2 = IOUtil.getStreamContentAsString(Thread.currentThread().getContextClassLoader().getResourceAsStream("org/castafiore/designable/checkout/m2.xhtml"));
	 	sendMail(order, merchant, customer, "New order", merchant.getEmail(), merchant.getEmail(), m2);
	 	
	 	getChild("message").setText("Thank you for your purchase. We will contact you as soon as possible to confirm the delivery.");
	 	getChild("back").setDisplay(false);
	 	
	 	if(getAncestorOfType(EXSearchEngineApplication.class) != null){
	 		getChild("continue").setText("Close");
	 	}else{
	 		getChild("continue").setDisplay(false);
	 	}
	 	//EXMiniCart cart = (EXMiniCart)getRoot().getDescendentById(getAncestorOfType(EXCheckout.class).getAttribute("cartid"));
//	 	ComponentUtil.iterateOverDescendentsOfType(getRoot(), EXMiniCart.class, new ComponentVisitor() {
//			
//			@Override
//			public void doVisit(Container c) {
//				((EXMiniCart)c).setItems(new ArrayList<CartItem>())
//				
//			}
//		});
	 	cart.setItems(new ArrayList<CartItem>());
	 	cart.update();
	 	//send mails.
	}
	
	
	public static void sendMail( Order order, Merchant merchant,String customer, String subject, String from, String to, String content){
		try{
			Map rep = new HashMap();
			rep.put("input", order);
		//rep.put("$merchant", merchant.getCompanyName());
			content = SpringUtil.getBeanOfType(OrdersWorkflow.class).getHtml(new SimpleTemplateEngine(), rep);
		//content = OrdersUtil.compile(content, rep);
		
			JavaMailSender sender = SpringUtil.getBeanOfType(JavaMailSender.class);
			MimeMessage message = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setSubject(subject);
			helper.setFrom(from);
			helper.setTo(to);
			helper.setText(content, true);
			sender.send(message);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	@Override
	public void changeCurrency() throws Exception {
		EXMiniCart cart = (EXMiniCart)getRoot().getDescendentById(getAncestorOfType(EXCheckout.class).getAttribute("cartid"));
		reInit(cart);
		
	}

}
