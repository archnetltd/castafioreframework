package org.castafiore.shoppingmall.pos;

import java.text.SimpleDateFormat;

import org.castafiore.shoppingmall.checkout.Order;
import org.castafiore.shoppingmall.orders.OrdersWorkflow;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.ex.dynaform.EXFieldSet;
import org.castafiore.ui.ex.form.EXLabel;
import org.castafiore.ui.ex.layout.EXBorderLayoutContainer;
import org.castafiore.ui.ex.toolbar.EXToolBar;
import org.castafiore.wfs.Util;
import org.castafiore.workflow.MerchantWorkflow;

public class EXPurchaseOrderView extends EXBorderLayoutContainer{

	public EXPurchaseOrderView(String name, Order order, OrdersWorkflow workflow) {
		super(name);
		
		for(int i =0; i <5;i++){
			getContainer(i).setStyle("padding", "0").setStyle("margin", "0").setStyle("vertical-align", "top");
		}
		String organization = Util.getLoggedOrganization();
		if(getRoot().getContextPath().contains("erevolution")){
			organization = "erevolution";
		}
		EXToolBar tb = new EXToolBar("actions");
		addChild(tb,TOP);
		workflow.addButtons(tb, order.getStatus(), "customer",organization, order.getAbsolutePath());
		
		EXFieldSet fs = new EXFieldSet("main", "Main Info", true);
		fs.addField("Date :", new EXLabel("date", ""));
		fs.addField("Order Number :", new EXLabel("order", ""));
		
		fs.addField("Supplier :", new EXLabel("supplier", ""));
			
		fs.addField("Agent :", new EXLabel("agent", ""));
		

		addChild(fs, EXBorderLayoutContainer.TOP);
		
		addChild(new EXItemsViewTable("items", order).setStyle("width", "650px"), CENTER);
		
		addChild(new EXPurchaseOrderFooter("footer"), BOTTOM);
		
		//getDescendentOfType(EXItemsTable.class).setModel(getDescendentOfType(EXItemsTable.class));
		setPurchaseOrder(order);
		
	}
	public void setPurchaseOrder(Order order){
		((StatefullComponent)getDescendentByName("date")).setValue(new SimpleDateFormat("dd-MM-yyyy").format(order.getDateOfTransaction()));
		((StatefullComponent)getDescendentByName("order")).setValue(order.getCode());
		((StatefullComponent)getDescendentByName("supplier")).setValue(order.getOrderedFrom());
		((StatefullComponent)getDescendentByName("agent")).setValue(order.getOwner());
		getDescendentOfType(EXItemsViewTable.class).setPurchaseOrder(order);
		getDescendentOfType(EXPurchaseOrderFooter.class).setOrder(order);
	}
}
