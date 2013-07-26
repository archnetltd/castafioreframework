package org.castafiore.shoppingmall.orders;

import org.castafiore.shoppingmall.checkout.EXOrdersList;
import org.castafiore.shoppingmall.crm.OrderInfo;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;

public class EXOrdersPanel extends EXContainer {
	public EXOrdersPanel(String name) {
		super(name, "div");
	}
	public void showOrderList(){
		
		boolean found = false;
		for(Container c : getChildren()){
			
			if( c instanceof EXOrdersList){
				c.setDisplay(true);
				found = true;
			}else{
				c.setDisplay(false);
			}
		}
		if(!found){
			
			addChild(new EXOrdersList("").addClass("ex-content"));
			//List<OrderInfo> orders = new ArrayList<OrderInfo>();//MallUtil.getCurrentUser().getMerchant().getOrders();
			getDescendentOfType(EXOrdersList.class).setOrders(null, null, null, true,null);
		}
	}
	
	public void showOrderDetail(OrderInfo order){
		boolean found = false;
		for(Container c : getChildren()){
			if( c instanceof EXInvoice){
				c.setDisplay(true);
				found = true;
				((EXInvoice)c).setInvoice(order);
			}else{
				c.setDisplay(false);
			}
		}
		
		if(!found){
			EXInvoice invoice = new EXInvoice("EXInvoice");
			
			addChild(invoice);
			invoice.setInvoice(order);
		}
	}
}
