package org.castafiore.shoppingmall.orders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.shoppingmall.crm.OrderInfo;
import org.castafiore.ui.Container;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXCheckBox;

public class EXInvoicesList extends EXContainer implements EventDispatcher{
	
	private List<OrderInfo> allOrders = new ArrayList<OrderInfo>();
	
	private int pageSize = 10;
	
	private int curPage = 0;
	
	public EXInvoicesList(String name) {
		super(name, "table");
		addClass("MallTable");
		EXContainer head = new EXContainer("", "thead");
		addChild(head);
		EXContainer tr = new EXContainer("", "tr");
		head.addChild(tr);
		
		tr.addChild(new EXContainer("", "th").setStyle("width", "15px").addChild(new EXCheckBox("selectAll")));
		tr.addChild(new EXContainer("inv", "th").setStyle("width", "180px").setText("Inv. No").addEvent(DISPATCHER, Event.CLICK));
		tr.addChild(new EXContainer("date", "th").setStyle("width", "180px").setText("Date").addEvent(DISPATCHER, Event.CLICK));
		tr.addChild(new EXContainer("merchant", "th").setStyle("width", "180px").setText("Shop").addEvent(DISPATCHER, Event.CLICK));
		tr.addChild(new EXContainer("state", "th").setStyle("width", "180px").setText("Status").addEvent(DISPATCHER, Event.CLICK));
		tr.addChild(new EXContainer("total", "th").setText("Total").addEvent(DISPATCHER, Event.CLICK));
		EXContainer body = new EXContainer("body", "tbody");
		addChild(body);
		
		for(int i = 0; i < pageSize; i++){
			EXInvoiceListItem item = new EXInvoiceListItem("");
			item.setStyle("visibility", "hidden");
			body.addChild(item);
			if((i % 2) == 0){
				item.addClass("even");
			}else{
				item.addClass("odd");
			}
		}
		addFooter();
	}
	
	private void hideAll(){
		for(Container c : getChild("body").getChildren()){
			c.setStyle("visibility", "hidden");
			c.setAttribute("path", (String)null);
		}
	}
	
	private void addFooter(){
		EXContainer tfoot = new EXContainer("foot", "tfoot");
		EXContainer tr = new EXContainer("tr", "tr");
		tfoot.addChild(tr);
		Container td = new EXContainer("pages", "td").setAttribute("colspan", "6").setStyle("text-align", "center");
		//Container moreItems = new EXContainer("moreItems", "a").setAttribute("href", "#more").setStyle("display", "inline").setStyle("float", "none").addClass("button").setText("More Items").addEvent(DISPATCHER, Event.CLICK);
		tr.addChild(td);
		//td.addChild(moreItems);
		addChild(tfoot);	
	}
	
	private int getPages(){
		int count = allOrders.size();
		count = count -(count%pageSize);
		int pages = count/pageSize;
		return pages+1;
	}
	
	private List<OrderInfo> getPage(){
		int start = curPage*pageSize;
		int end = (curPage+1)*pageSize;
		if(end >= allOrders.size()){
			end = allOrders.size();
		}
		return allOrders.subList(start, end);
	}
	public void setInvoices(List<OrderInfo> orders_){
		this.allOrders = orders_;
		setInvoices(0);
		int pages = getPages();
		Container ps = getChild("foot").getDescendentByName("pages");
		ps.getChildren().clear();
		ps.setRendered(false);
		if(pages > 1){
			for(int i = 0; i < pages; i++){
				Container a = new EXContainer("pp", "a").addClass("mall-paginator").setText((i + 1) + "").setAttribute("p", i + "").setAttribute("href", "#II").addEvent(DISPATCHER, Event.CLICK);
				ps.addChild(a);
			}
		}
	}
	public void setInvoices(int page){
		this.curPage = page;
		hideAll();
		List<OrderInfo> orders = getPage();
		Container body = getChild("body");
		int index =0;
		for(OrderInfo order : orders){
			EXInvoiceListItem item = (EXInvoiceListItem)body.getChildByIndex(index);
			item.setStyle("visibility", "visible");
			item.setItem(order);
			index++;
		}
	}
	
	public EXInvoiceListItem getItem(String path){
		Container body =getChild("body");
		for(Container c : body.getChildren()){
			if(path.equals(c.getAttribute("path"))){
				return (EXInvoiceListItem)c;
			}
		}
		return null;
	}
	
	public void deleteOrder(OrderInfo order){
		for(Container c : getChild("body").getChildren()){
			if(c instanceof EXInvoiceListItem && ((EXInvoiceListItem)c).isSameCode(order)){
				c.remove();
				break;
			}
		}
		getChild("body").setRendered(false);
	}
	
	@Override
	public void executeAction(Container source) {
		if(source.getName().equals("pp")){
			setInvoices(Integer.parseInt(source.getAttribute("p")));
			return;
		}
		
		Container body = getChild("body");
		List<Container> children = body.getChildren();
		Collections.sort(children, new OrdersComparator(source));
		body.setRendered(false);
		for(int size = 0; size< children.size(); size++){
			if((size % 2) == 0){
				children.get(size).setStyleClass("even");
			}else{
				children.get(size).setStyleClass("odd");
			}
		}		
	}

	public class OrdersComparator implements Comparator<Container>{

		private Container source_;
		public OrdersComparator(Container source){
			source_ = source;
		}
		
		@Override
		public int compare(Container o1, Container o2) {
			String s1;
			
			String s2;
			
			s1 = o1.getDescendentByName(source_.getName()).getText();
			s2 = o2.getDescendentByName(source_.getName()).getText();
			return s1.compareTo(s2);
		}
		
	}
}
