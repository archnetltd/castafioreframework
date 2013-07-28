package org.castafiore.inventory.orders;

import org.castafiore.inventory.AbstractModel;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.merchant.ShoppingMallMerchant;
import org.castafiore.wfs.service.QueryParameters;
import org.hibernate.criterion.Order;

public class SalesOrderModel extends  AbstractModel{

	private String[] labels = new String[]{"Inv No.", "Date", "Sub total", "Vat", "Total"};
	
	public QueryParameters getParams(){
		return new QueryParameters().setEntity(SalesOrder.class).addSearchDir(ShoppingMallMerchant.ORDERS_DIR.replace("$user", MallUtil.getCurrentUser().getUser().getUsername())).addOrder(Order.desc("dateCreated"));
	}

	@Override
	public String[] getColumns() {
		return labels;
	}

}
