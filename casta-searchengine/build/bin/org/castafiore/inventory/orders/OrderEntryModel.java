package org.castafiore.inventory.orders;

import org.castafiore.inventory.AbstractModel;
import org.castafiore.shoppingmall.checkout.OrderEntry;
import org.castafiore.wfs.service.QueryParameters;

public class OrderEntryModel extends AbstractModel{

	private String labels[] = new String[]{"Code", "Title", "Price", "Qty", "Total"};
	
	private String orderPath;
	
	
	public OrderEntryModel(String orderPath) {
		super();
		this.orderPath = orderPath;
	}

	@Override
	public String[] getColumns() {
		return labels;
	}

	@Override
	public QueryParameters getParams() {
		return new QueryParameters().setEntity(OrderEntry.class).addSearchDir(orderPath);
	}

}
