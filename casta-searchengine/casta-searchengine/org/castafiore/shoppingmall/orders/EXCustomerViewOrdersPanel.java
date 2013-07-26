package org.castafiore.shoppingmall.orders;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.inventory.orders.OrderService;
import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.shoppingmall.crm.OrderInfo;
import org.castafiore.ui.Container;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.wfs.Util;

public class EXCustomerViewOrdersPanel extends EXContainer implements EventDispatcher{
	
	public EXCustomerViewOrdersPanel(String name, String title) {
		super(name, "div");
		//setStyle("margin-left", "-10px");
		addChild(new EXContainer("title", "h3").addClass("MessageListTitle").setText(title)
				.addChild(new EXContainer("back", "button").setText("Back").addEvent(DISPATCHER, Event.CLICK))
		);
		addChild(new EXFilterOptions("FilterOptions"));
		
		addChild(new EXInvoicesList("EXInvoicesList"));
		
		addChild(new EXInvoice("EXInvoice"));
	}
	
	
	public void init(String status){
		List<OrderInfo> orders =new OrderService().getMerchantOrder(Util.getLoggedOrganization()); // MallUtil.getCurrentUser().getInvoices();
		if(status.equals("-1")){
			getDescendentOfType(EXInvoicesList.class).setInvoices(orders);
			viewList();
		}else{
			List<OrderInfo> subOrders = new ArrayList<OrderInfo>();
			for(OrderInfo o : orders){
				if((o.getStatus() + "").equals(status)){
					subOrders.add(o);
				}
			}
			getDescendentOfType(EXInvoicesList.class).setInvoices(subOrders);
			viewList();
		}
		
		
	}
	
	
	public void init(List<OrderInfo> orders, String title){
		getDescendentByName("title").setText(title);
		getDescendentOfType(EXInvoicesList.class).setInvoices(orders);
		viewList();
	}
	
	public void viewInvoice(OrderInfo order){
		if(order != null){
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
	}
	
	
	public void viewList(){
		for(Container c : getChildren()){
			if(c instanceof EXInvoice){
				//((EXInvoice)c).setInvoice(order);
				c.setDisplay(false);
			}else if(!c.getName().equals("title")){
				c.setDisplay(true);
				
				if( c instanceof EXInvoicesList){
					EXInvoice exInvoice = getDescendentOfType(EXInvoice.class);
					if(exInvoice != null){
						OrderInfo o =  exInvoice.getOrder();
						if(o != null){
							EXInvoiceListItem item= ((EXInvoicesList)c).getItem(o.getAbsolutePath());
							if(item != null){
								item.refreshItem();
							}
						}
					}
					//refresh invoice modified
				}
			}else if(c.getName().equalsIgnoreCase("title")){
				c.getDescendentByName("back").setDisplay(false);
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
