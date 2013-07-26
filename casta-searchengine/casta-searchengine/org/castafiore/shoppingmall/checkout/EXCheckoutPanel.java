package org.castafiore.shoppingmall.checkout;

import java.util.List;

import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.shoppingmall.crm.OrderInfo;
import org.castafiore.shoppingmall.orders.EXInvoice;
import org.castafiore.shoppingmall.orders.EXInvoicesList;
import org.castafiore.ui.Container;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.scripting.EXXHTMLFragment;

public class EXCheckoutPanel extends EXContainer implements EventDispatcher{
	public EXCheckoutPanel(String name) {
		super(name, "div");
		addChild(new EXContainer("title", "h3").addClass("MessageListTitle").setText("Checkout")
				.addChild(new EXContainer("back", "button").setText("Back").addEvent(DISPATCHER, Event.CLICK))
				.addChild(new EXContainer("loader", "img").setAttribute("src", "blueprint/ajax-loader.gif").setStyle("width", "220px").setStyle("height", "19px").setStyle("border", "none").setStyle("background", "none").setStyle("display", "none"))
		);
		
		
		addChild(new EXXHTMLFragment("info", "templates/CheckoutInfo.xhtml"));
		
		addChild(new EXInvoicesList("EXInvoicesList"));
		
		addChild(new EXInvoice("EXInvoice"));
	}
	

	
	public void init(List<OrderInfo> orders){
		
		getDescendentOfType(EXInvoicesList.class).setInvoices(orders);
		viewList();
	}
	
	public void viewInvoice(OrderInfo order){
		for(Container c : getChildren()){
			if(c instanceof EXInvoice){
				((EXInvoice)c).setInvoice(order);
				c.setDisplay(true);
			}else if(!c.getName().equals("title")){
				c.setDisplay(false);
			}else if(c.getName().equalsIgnoreCase("title")){
				c.getDescendentByName("back").setDisplay(true);
			}
		}
	}
	
	
	
	public void viewList(){
		for(Container c : getChildren()){
			if(c instanceof EXInvoice){
				c.setDisplay(false);
			}else if(!c.getName().equals("title")){
				c.setDisplay(true);
			}else if(c.getName().equalsIgnoreCase("title")){
				c.getDescendentByName("back").setDisplay(false);
				c.setText("Checkout");
			}
		}
	}

	@Override
	public void executeAction(Container source) {
		
		if(source.getName().equalsIgnoreCase("back")){
			viewList();
		}

	
	}
}
