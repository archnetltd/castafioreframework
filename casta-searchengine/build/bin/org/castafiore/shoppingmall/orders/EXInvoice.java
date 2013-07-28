package org.castafiore.shoppingmall.orders;

import java.util.List;

import org.castafiore.inventory.orders.OrderService;
import org.castafiore.shoppingmall.crm.OrderInfo;
import org.castafiore.shoppingmall.crm.OrderLine;
import org.castafiore.shoppingmall.merchant.FinanceUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXCheckBox;
import org.castafiore.ui.ex.panel.EXOverlayPopupPlaceHolder;
import org.castafiore.utils.StringUtil;

public class EXInvoice extends EXContainer implements PopupContainer{
	
	public EXInvoice(String name) {
		super(name, "table");
		addClass("MallTable ex-content");
		EXContainer head = new EXContainer("", "thead");
		addChild(head);
		addInvoiceHeader(head);
		EXContainer tr = new EXContainer("", "tr");
		head.addChild(tr);
		
		tr.addChild(new EXContainer("", "th").setStyle("width", "15px").addChild(new EXCheckBox("selectAll")));
		tr.addChild(new EXContainer("", "th").setStyle("width", "180px").setText("Code"));
		tr.addChild(new EXContainer("", "th").setStyle("width", "300px").setText("Title"));
		tr.addChild(new EXContainer("th_qty", "th").setText("Qty"));
		tr.addChild(new EXContainer("th_price", "th").setText("Price"));
		tr.addChild(new EXContainer("th_total", "th").setText("Total"));
		EXContainer body = new EXContainer("body", "tbody");
		addChild(body);
		addTotalFooter();
	}
	
	
	protected void addInvoiceHeader(Container head){
		Container tr = new EXContainer("", "tr").addClass("store-head");
		head.addChild(tr);
		Container td = new EXContainer("", "th").setAttribute("colspan", "6").addChild(new EXInvoiceHeader("InvoiceHeader")).addChild(new EXOverlayPopupPlaceHolder("overlay"));
		tr.addChild(td);
	}
	
	protected  void addTotalFooter(){
		Container tfoot = new EXContainer("", "tfoot");
		addChild(tfoot);
		String[] broken = new String[]{"Sub total", "Tax",  "Total"};
		String[] names = new String[] {"subTotal", "tax", "totalTotal"};
		int count = 0;
		for(String b : broken){
			Container tr = new EXContainer("", "tr").addClass("invoice").addClass(names[count]);
			tr.addChild(new EXContainer("", "td").setAttribute("colspan", "5").setStyle("text-align", "right").setText(b));
			tr.addChild(new EXContainer(names[count], "td"));
			tfoot.addChild(tr);
			count++;
		}
		
	}
	
//	public void confirmOrder(){
//		MallUtil.getCurrentUser().confirmOrder(getOrder());
//	}
	
	
	
	public OrderInfo getOrder(){
		return getDescendentOfType(EXInvoiceHeader.class).getOrder();
	}
	
	public void setInvoice(OrderInfo order){
		getChild("body").getChildren().clear();
		getChild("body").setRendered(false);
		getDescendentOfType(EXInvoiceHeader.class).setInvoice(order);
		List<OrderLine> lines = new OrderService().getLines(order.getFsCode());
		
		//FileIterator<SalesOrderEntry> entries = order.getFiles(SalesOrderEntry.class);
		//while(entries.hasNext()){
		for(OrderLine entry : lines){
			
			EXInvoiceEntry item = new EXInvoiceEntry("");
			item.setItem(entry);
			getChild("body").addChild(item);
			if((getChild("body").getChildren().size() % 2) == 0){
				item.addClass("odd");
			}else{
				item.addClass("even");
			}
		}
		getDescendentByName("subTotal").setText(StringUtil.toCurrency(FinanceUtil.getCurrentCurrency(),order.getSubTotal()));
		getDescendentByName("tax").setText(StringUtil.toCurrency(FinanceUtil.getCurrentCurrency(),order.getTax()));
		getDescendentByName("totalTotal").setText(StringUtil.toCurrency(FinanceUtil.getCurrentCurrency(),order.getTotal()));
		
	}


	@Override
	public void addPopup(Container popup) {
		getDescendentByName("overlay").addChild(popup);
		
	}

}
