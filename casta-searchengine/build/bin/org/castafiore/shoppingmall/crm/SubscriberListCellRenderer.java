package org.castafiore.shoppingmall.crm;

import org.castafiore.ui.Container;
import org.castafiore.ui.ex.form.table.CellRenderer;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableModel;

public class SubscriberListCellRenderer implements CellRenderer {

	@Override
	public Container getComponentAt(int row, int column, int page,
			TableModel model, EXTable table) {
		//List<MerchantSubscription> users = (List<MerchantSubscription>)model.getValueAt(column, row, page);
		
		OrderInfo subscription = (OrderInfo)model.getValueAt(column, row, page);
		
		if(subscription != null){
			EXSubscriberTableListItem item = new EXSubscriberTableListItem("");
			item.setOrderInfo(subscription);
			return item;
		}else{
			return  new EXSubscriberTableListItem("").setDisplay(false);
		}
		

	}

	@Override
	public void onChangePage(Container c, int row, int column,
			int page, TableModel model, EXTable table) {
		
		
		OrderInfo subscription = (OrderInfo)model.getValueAt(column, row, page);
		EXSubscriberTableListItem item = (EXSubscriberTableListItem)c;
		item.setDisplay(true);
		if(subscription != null)
			item.setOrderInfo(subscription);
		
	}

	

}
