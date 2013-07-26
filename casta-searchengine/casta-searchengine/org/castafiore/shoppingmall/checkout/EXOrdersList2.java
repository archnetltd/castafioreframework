package org.castafiore.shoppingmall.checkout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.castafiore.inventory.orders.OrderService;
import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.crm.OrderInfo;
import org.castafiore.shoppingmall.ui.EXSearch;
import org.castafiore.spring.SpringUtil;


import org.castafiore.ui.Container;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXCheckBox;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.castafiore.wfs.types.File;
import org.hibernate.criterion.Restrictions;

/**
 * This order list is for management by merchants
 * color of row represents state
 * @author kureem
 *
 */
public class EXOrdersList2 extends EXContainer implements EventDispatcher{
	
	private List<OrderInfo> allOrders = new ArrayList<OrderInfo>();
	
	private int pageSize = 10;
	
	private int curPage = 0;
	
	public EXOrdersList2(String name) {
		super(name, "table");
		addClass("MallTable");
		EXContainer head = new EXContainer("", "thead");
		addChild(head);
		Container filter = new EXContainer("", "tr").setAttribute("colspan", "5");
		filter.setStyle("background-color", "silver");
		filter.addChild(new EXContainer("th", "td").setAttribute("colspan", "5").addChild(new EXSearch("search")));
		head.addChild(filter);
		
		EXContainer tr = new EXContainer("", "tr");
		head.addChild(tr);
		
		//tr.addChild(new EXContainer("", "th").setStyle("width", "15px").setText("#"));
		tr.addChild(new EXContainer("inv", "th").setStyle("width", "180px").setText("Inv. No").addEvent(DISPATCHER, Event.CLICK));
		tr.addChild(new EXContainer("date", "th").setStyle("width", "180px").setText("Date").addEvent(DISPATCHER, Event.CLICK));
		tr.addChild(new EXContainer("customer", "th").setStyle("width", "300px").setText("Customer").addEvent(DISPATCHER, Event.CLICK));
		tr.addChild(new EXContainer("status", "th").setStyle("width", "300px").setText("Status").addEvent(DISPATCHER, Event.CLICK));
		tr.addChild(new EXContainer("total", "th").setText("Total").addEvent(DISPATCHER, Event.CLICK));
		EXContainer body = new EXContainer("body", "tbody");
		addChild(body);
		
		for(int i = 0; i < pageSize; i++){
			EXOrderListItem item = new EXOrderListItem("");
			item.setStyle("visibility", "hidden");
			body.addChild(item);
//			if((i % 2) == 0){
//				item.addClass("even");
//			}else{
//				item.addClass("odd");
//			}
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
		tr.addChild(td);
		addChild(tfoot);	
	}
	
	
	public void filtreByState(int state){
		QueryParameters params = null;
		
		if(state != -1){
			params =new QueryParameters()
			.setEntity(Order.class)
			.addRestriction(Restrictions.eq("orderedFrom", Util.getLoggedOrganization())).addRestriction(Restrictions.eq("status", state)).addOrder(org.hibernate.criterion.Order.desc("dateCreated"));
			List files = SpringUtil.getRepositoryService().executeQuery(params, Util.getRemoteUser());
			setOrders(files);
		}else{
//			params =new QueryParameters()
//			.setEntity(Order.class)
//			.addRestriction(Restrictions.eq("orderedFrom", Util.getRemoteUser())).addRestriction(Restrictions.ne("status", state)).addOrder(org.hibernate.criterion.Order.desc("dateCreated"));
			setOrders(new OrderService().getMerchantOrder(Util.getLoggedOrganization()));
		}
		
		
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
	private void setInvoices(int page){
		this.curPage = page;
		hideAll();
		List<OrderInfo> orders = getPage();
		Container body = getChild("body");
		int index =0;
		for(OrderInfo order : orders){
			EXOrderListItem item = (EXOrderListItem)body.getChildByIndex(index);
			item.setStyle("visibility", "visible");
			item.setItem(order);
			index++;
		}
	}
	
	private void setInvoices(List<OrderInfo> orders_){
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
	
	public void setOrders(List<OrderInfo> orders){
		setInvoices(orders);
		
//		getChild("body").getChildren().clear();
//		getChild("body").setRendered(false);
//		
//		for(Order order : orders){
//			EXOrderListItem item = new EXOrderListItem("");
//			item.setItem(order);
//			int size = getChild("body").getChildren().size();
//			if((size % 2) == 0){
//				item.addClass("even");
//			}else{
//				item.addClass("odd");
//			}
//			getChild("body").addChild(item);
//		}
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
//		for(int size = 0; size< children.size(); size++){
//			if((size % 2) == 0){
//				children.get(size).setStyleClass("even");
//			}else{
//				children.get(size).setStyleClass("odd");
//			}
//		}
		
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
