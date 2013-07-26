package org.castafiore.shoppingmall.pos;

import java.util.List;
import java.util.Map;

import org.castafiore.shoppingmall.checkout.Order;
import org.castafiore.shoppingmall.orders.OrdersWorkflow;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.layout.EXBorderLayoutContainer;
import org.castafiore.ui.ex.panel.EXOverlayPopupPlaceHolder;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.ui.ex.toolbar.EXToolBar;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.hibernate.criterion.Restrictions;

public class EXPurchaseOrdersPanel extends EXBorderLayoutContainer implements Event{
	private OrdersWorkflow workflow_;
	public EXPurchaseOrdersPanel(String name, OrdersWorkflow workflow) {
		super(name);
		workflow_ = workflow;
		for(int i =0; i <5;i++){
			getContainer(i).setStyle("padding", "0").setStyle("margin", "0").setStyle("vertical-align", "top");
		}
		
		getChild("popupContainer").remove();
		addChild(new EXOverlayPopupPlaceHolder("popupContainer"));
		EXToolBar tb = new EXToolBar("");
		tb.addItem(new EXButton("new", "New Purchase Order"));
		addChild(tb,TOP);
		tb.getDescendentByName("new").addEvent(this, CLICK);
		
		tb.addItem(new EXButton("refresh", "Refresh List"));
		tb.getDescendentByName("refresh").addEvent(this, CLICK);
		
		
		
		
		showOrderList();
	}

	public void showOrderList() {
		Container me = getContainer(CENTER);
		boolean found = false;
		for (Container c : me.getChildren()) {

			if (c instanceof EXPurchasesList) {
				c.setDisplay(true);
				found = true;
			} else {
				c.setDisplay(false);
			}
		}
		if (!found) {

			me.addChild(new EXPurchasesList("", workflow_).addClass("ex-content"));
			
			QueryParameters params = new QueryParameters().setEntity(Order.class).addRestriction(Restrictions.eq("orderedBy", Util.getLoggedOrganization()));
			
			List l = SpringUtil.getRepositoryService().executeQuery(params, Util.getRemoteUser());
			List<Order> orders = l;
			getDescendentOfType(EXPurchasesList.class).setOrders(orders);
		}
	}

	public void showOrderDetail(Order order) {
		boolean found = false;
		Container me = getContainer(CENTER);
		for (Container c : me.getChildren()) {
			if (c instanceof EXPurchaseOrderView) {
				c.setDisplay(true);
				found = true;
				((EXPurchaseOrderView) c).setPurchaseOrder(order);
			} else {
				c.setDisplay(false);
			}
		}

		if (!found) {
			EXPurchaseOrderView invoice = new EXPurchaseOrderView("EXInvoice",order, workflow_);

			me.addChild(invoice);

		}
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		if(container.getName().equalsIgnoreCase("refresh")){
			showOrderList();
			getDescendentOfType(EXSearchPurchases.class).search();
			return true;
		}else{
		
			EXPanel panel = new EXPanel("pos", "Create new Purchase Order");
			panel.setStyle("width", "1000px");
			panel.setBody(new EXPointOfSales("ppp", EXPointOfSales.MODE_PURCHASES));
			panel.setStyle("z-index", "4000");
			container.getAncestorOfType(PopupContainer.class).addPopup(panel);
		}
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}
}
