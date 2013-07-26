package org.castafiore.shoppingmall.orders;

import java.text.SimpleDateFormat;

import org.castafiore.shoppingmall.checkout.Order;
import org.castafiore.shoppingmall.crm.OrderInfo;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.wfs.Util;

public class EXInvoiceHeader extends EXXHTMLFragment{
	
	private OrderInfo order;

	public EXInvoiceHeader(String name) {
		super(name, "templates/EXInvoiceHeader.xhtml");
		addChild(new EXContainer("buttons", "div").addClass("invoicebuttons"));
		addChild(new EXContainer("invoiceNumber", "span"));
		addChild(new EXContainer("dateCreated", "span"));
		addChild(new EXContainer("status", "span"));
		
		EXCustomerInfo info = new EXCustomerInfo("merchantInfo");
		//info.setTemplateLocation("templates/EXMerchantInfoInvoice.xhtml");
		addChild(info);
		
		
		addChild(new EXContainer("messages", "div").addClass("messages"));
	}
	
	
	public void setInvoice(OrderInfo order){
		getChild("invoiceNumber").setText(order.getFsCode());
		getChild("dateCreated").setText(new SimpleDateFormat("dd/MM/yyyy").format(order.getDateOfTransaction()));
		//Merchant merchant = MallUtil.getCurrentMall().getMerchant(order.getOrderedFrom());
		getChild("messages").setDisplay(false);
		getDescendentOfType(EXCustomerInfo.class).setOrder(order);

		
		String status = SpringUtil.getBeanOfType(OrdersWorkflow.class).getStatus(order.getStatus());
		getChild("status").setText(status);
		
		Container buttons = getChild("buttons");
		buttons.getChildren().clear();
		buttons.setRendered(false);
		String actor = "merchant";
		
		String organization = Util.getLoggedOrganization();
		if(getRoot().getContextPath().contains("erevolution")){
			organization = "erevolution";
		}
		//Order o = new Order();
		
		SpringUtil.getBeanOfType(OrdersWorkflow.class).addButtons(buttons, order.getStatus(), actor,organization, order.getAbsolutePath());
		buttons.setText(null);
		this.order = order;
	}
	
	public void setMessage(String message){
		getChild("messages").setText(message).setDisplay(true);
		
	}
	
	
	public OrderInfo getOrder(){
		return order;
	}

}
