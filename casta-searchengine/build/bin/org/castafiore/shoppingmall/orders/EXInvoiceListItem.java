package org.castafiore.shoppingmall.orders;

import java.text.SimpleDateFormat;

import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.shoppingmall.checkout.EXCheckoutPanel;
import org.castafiore.shoppingmall.crm.OrderInfo;
import org.castafiore.shoppingmall.list.AbstractListItem;
import org.castafiore.shoppingmall.merchant.FinanceUtil;
import org.castafiore.shoppingmall.user.ui.EXMerchantCardLink;
import org.castafiore.shoppingmall.util.list.ListItem;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.FileNotFoundException;

public class EXInvoiceListItem extends AbstractListItem implements  ListItem<OrderInfo>,EventDispatcher{
	
	private OrderInfo order = null;
	public EXInvoiceListItem(String name) {
		super(name);
		
		addChild(new EXContainer("td_inv", "td").setStyle("vertical-align","top" ).addChild(new EXContainer("inv","a").addEvent(DISPATCHER, DISPATCHER.CLICK).setAttribute("href", "#").setStyle("font-weight", "bold").setStyle("color", "#111")));
		addChild(new EXContainer("td_date", "td").setStyle("vertical-align","top" ).addChild(new EXContainer("date","div")));
		addChild(new EXContainer("td_merchant", "td").setStyle("vertical-align","top" ).addChild(new EXMerchantCardLink("merchant")));
		addChild(new EXContainer("td_state", "td").setStyle("vertical-align","top" ).addChild(new EXContainer("state","div")));
		addChild(new EXContainer("td_total", "td").setStyle("vertical-align","top" ).addChild(new EXContainer("total","div")));
	}
	
	@Override
	public OrderInfo getItem() {
		
		return order;
		

	}

	

	@Override
	public void setItem(OrderInfo order) {
		this.order = order;
		setAttribute("path", order.getAbsolutePath());
		getDescendentByName("inv").setText(order.getFsCode());
		getDescendentByName("date").setText(new SimpleDateFormat("dd/MM/yyyy").format(order.getDateOfTransaction()));		
		//getDescendentOfType(EXMerchantCardLink.class);//.setMerchantUsername(order.getOrderedFrom());
		getDescendentByName("total").setText(StringUtil.toCurrency(FinanceUtil.getCurrentCurrency(),order.getTotal()));
		setStyle("background-color", SpringUtil.getBeanOfType(OrdersWorkflow.class).getColor(order.getStatus()));
		getDescendentByName("state").setText(SpringUtil.getBeanOfType(OrdersWorkflow.class).getStatus(order.getStatus()));
		

	}
	
	public void refreshItem(){
		try{
			OrderInfo o = getItem();
			if(o != null)
				setItem(o);
			else{
				setStyle("visibility", "hidden");
			}
		}catch(FileNotFoundException e){
			
			setStyle("visibility", "hidden");
		}
	}
	
	public boolean isSameCode(OrderInfo order){
		return order.getFsCode().equals(getDescendentByName("inv").getText());
	}
	


	@Override
	public void executeAction(Container container) {
		if(container.getName().equalsIgnoreCase("customer")){
			//getAncestorOfType(EXMall.class).showMerchantCard(merchant)
		}else if(container.getName().equalsIgnoreCase("inv")){
			if(getAncestorOfType(EXCustomerViewOrdersPanel.class) != null)
				getAncestorOfType(EXCustomerViewOrdersPanel.class).viewInvoice(getItem());
			else
				getAncestorOfType(EXCheckoutPanel.class).viewInvoice(order);
		}
	}
}
