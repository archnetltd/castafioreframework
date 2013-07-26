package org.castafiore.shoppingmall.pos;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.castafiore.persistence.Dao;
import org.castafiore.shoppingmall.checkout.Order;
import org.castafiore.shoppingmall.orders.OrdersWorkflow;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.dynaform.EXFieldSet;
import org.castafiore.ui.ex.form.EXAutoComplete;
import org.castafiore.ui.ex.form.EXCheckBox;
import org.castafiore.ui.ex.form.EXDatePicker;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ComponentVisitor;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.castafiore.workflow.FlexibleWorkflow;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class EXSearchPurchases extends EXFieldSet implements Event{
	
	private OrdersWorkflow workflow  ;//new FlexibleWorkflow("purchases");
	public EXSearchPurchases(String name, OrdersWorkflow workflow) {
		super(name, "Search Purchases", true);
		this.workflow = workflow;
		displayBody(false);
		addField("Order Number :" ,new EXInput("invNumber"));
		Session s = SpringUtil.getBeanOfType(Dao.class).getReadOnlySession();
		List ss = s.createSQLQuery("select distinct paymentMethod from WFS_FILE").list();
		
		List<String> dict = ss;
		
		
		addField("Payment method :" ,new EXAutoComplete("paymentMethod","",dict));
		
		addField("From :",new EXDatePicker("from"));
		addField("To :",new EXDatePicker("to"));
		
		getField("from").setRawValue("");
		getField("to").setRawValue("");
		
		
		int[] states = workflow.getAvailableStates();
		for(int i : states){
			String label = workflow.getStatus(i);
			addField(label + " :", new EXCheckBox("status_" + i,true));
		}
		addButton(new EXButton("search", "Search"));
		addButton(new EXButton("showAll", "Show All"));
		getDescendentByName("search").addEvent(this, CLICK);
		getDescendentByName("showAll").addEvent(this, CLICK);
		
	}
	public void search(){
		QueryParameters params =new QueryParameters()
		.setEntity(Order.class).addRestriction(Restrictions.eq("orderedBy", Util.getLoggedOrganization())).addOrder(org.hibernate.criterion.Order.desc("dateCreated"));
		
		
		String invNumber = ((StatefullComponent)getDescendentByName("invNumber")).getValue().toString();
		String paymentmethod = ((StatefullComponent)getDescendentByName("paymentMethod")).getValue().toString();
		Date from = (Date)((StatefullComponent)getDescendentByName("from")).getValue();
		Date to = (Date)((StatefullComponent)getDescendentByName("to")).getValue();
		
		final List<Integer> statuss = new ArrayList<Integer>();

		ComponentUtil.iterateOverDescendentsOfType(this, EXCheckBox.class, new ComponentVisitor() {
			
			@Override
			public void doVisit(Container c) {
				if(((EXCheckBox)c).isChecked()){
					statuss.add(new Integer( c.getName().replace("status_", "").trim()));
				}
				
			}
		});
		if(StringUtil.isNotEmpty(invNumber)){
			params.addRestriction(Restrictions.ilike("code", invNumber));
		}
		
		if(StringUtil.isNotEmpty(paymentmethod)){
			params.addRestriction(Restrictions.eq("paymentMethod", paymentmethod));
		}
		if(from == null){
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, 2000);
			
			from = cal.getTime();
		}
		
		if(to == null){
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, 3000);
			to = cal.getTime();
		}
		
		params.addRestriction(Restrictions.between("dateOfTransaction", from, to));
		if(statuss.size() > 0)
			params.addRestriction(Restrictions.in("status", statuss));
		
		List files = SpringUtil.getRepositoryService().executeQuery(params, Util.getRemoteUser());
		getAncestorOfType(EXPurchasesList.class).setOrders(files);
		getAncestorOfType(EXFieldSet.class).setTitle(files.size() + " Orders found");
		//setOrders(files);
	}
	
	private void showAll(){
		QueryParameters params =new QueryParameters()
		.setEntity(Order.class)
		.addRestriction(Restrictions.eq("orderedBy", Util.getLoggedOrganization())).addOrder(org.hibernate.criterion.Order.desc("dateCreated"));
		
		List<Integer> statuss = new ArrayList<Integer>();
		int[] ss = SpringUtil.getBeanOfType(OrdersWorkflow.class).getAvailableStates();
		for(int i : ss){
			statuss.add(i);
		}
		params.addRestriction(Restrictions.in("status", statuss));
		List files = SpringUtil.getRepositoryService().executeQuery(params, Util.getRemoteUser());
		getAncestorOfType(EXPurchasesList.class).setOrders(files);
		getAncestorOfType(EXFieldSet.class).setTitle(files.size() + " Orders found");
	}


	@Override
	public void ClientAction(ClientProxy container) {
		container.mask(container.getAncestorOfType(EXPurchasesList.class)).makeServerRequest(this);
		
	}


	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		if(container.getName().equals("search")){
			search();
		}else{
			showAll();
		}return true;
	}


	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
