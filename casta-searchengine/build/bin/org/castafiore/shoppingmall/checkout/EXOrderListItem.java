package org.castafiore.shoppingmall.checkout;

import java.text.SimpleDateFormat;
import java.util.Map;

import org.castafiore.shoppingmall.crm.OrderInfo;
import org.castafiore.shoppingmall.list.AbstractListItem;
import org.castafiore.shoppingmall.merchant.FinanceUtil;
import org.castafiore.shoppingmall.orders.EXOrdersPanel;
import org.castafiore.shoppingmall.orders.OrdersUtil;
import org.castafiore.shoppingmall.orders.OrdersWorkflow;
import org.castafiore.shoppingmall.user.ui.EXUserInfoLink;
import org.castafiore.shoppingmall.util.list.ListItem;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;

public class EXOrderListItem extends AbstractListItem implements  ListItem<OrderInfo>,Event {
	private OrderInfo info;
	public EXOrderListItem(String name) {
		super(name);
		super.getChildren().clear();
		addChild(new EXContainer("td_inv", "td").setStyle("vertical-align","top" ).addChild(new EXContainer("inv","a").addEvent(this, CLICK).setAttribute("href", "#").setStyle("font-weight", "bold").setStyle("color", "#111")));
		addChild(new EXContainer("td_date", "td").setStyle("vertical-align","top" ).addChild(new EXContainer("date","div")));
		addChild(new EXContainer("td_customer", "td").setStyle("vertical-align","top" ).addChild(new EXContainer("customer", "label").setStyle("font-weight", "bold").setStyle("color", "#111")));
		addChild(new EXContainer("td_status", "td").setStyle("vertical-align","top" ).addChild(new EXContainer("status", "div").setStyle("font-weight", "bold").setStyle("color", "#111")));
		addChild(new EXContainer("td_total", "td").setStyle("vertical-align","top" ).addChild(new EXContainer("total","div")));
	}
	
	@Override
	public OrderInfo getItem() {
		//Order order = (Order)SpringUtil.getRepositoryService().getFile(getAttribute("path"), Util.getRemoteUser());
		return info;
	
	}

	

	@Override
	public void setItem(OrderInfo order) {
		this.info = order;
		setAttribute("path", order.getAbsolutePath());
		getDescendentByName("inv").setText(order.getFsCode());
		getDescendentByName("date").setText(new SimpleDateFormat("dd/MM/yyyy").format(order.getDateOfTransaction()));
		//BillingInformation bi = order.getBillingInformation();
		getDescendentByName("customer").setText(info.getFirstName() + " " + info.getLastName());
		getDescendentByName("total").setText(StringUtil.toCurrency(FinanceUtil.getCurrentCurrency(),order.getTotal()));
		getDescendentByName("status").setText(SpringUtil.getBeanOfType(OrdersWorkflow.class).getStatus(order.getStatus()));
		setStyle("background-color", SpringUtil.getBeanOfType(OrdersWorkflow.class).getColor(order.getStatus()));

	}
	
	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		if(container.getName().equalsIgnoreCase("customer")){
			//go to customer card
		}else if(container.getName().equalsIgnoreCase("inv")){
			EXOrdersPanel panel = getAncestorOfType(EXOrdersPanel.class);
			if(panel != null){
				panel.showOrderDetail(getItem());
			}
		}
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub		
	}
}
