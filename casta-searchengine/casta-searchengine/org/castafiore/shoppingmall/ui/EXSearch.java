package org.castafiore.shoppingmall.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.castafiore.shoppingmall.checkout.EXOrdersList;
import org.castafiore.shoppingmall.crm.OrderInfo;
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

public class EXSearch extends EXFieldSet implements Event{

	public EXSearch(String name) {
		super(name, "Filter", true);
		displayBody(false);
		addField("Order Number :" ,new EXInput("invNumber"));
		
		
		
		List<String> dict =new ArrayList<String>();
		dict.add("Cash");
		dict.add("Standing Order");
		dict.add("Bank Transfer");
		dict.add("Cheque");
		
		
		addField("Payment method :" ,new EXAutoComplete("paymentMethod","",dict));
		
		addField("From :",new EXDatePicker("from"));
		addField("To :",new EXDatePicker("to"));
		
		getField("from").setRawValue("");
		getField("to").setRawValue("");
		
		OrdersWorkflow wf =SpringUtil.getBeanOfType(OrdersWorkflow.class); 
		int[] states = wf.getAvailableStates();
		for(int i : states){
			String label = wf.getStatus(i);
			addField(label + " :", new EXCheckBox("status_" + i,true));
		}
		addButton(new EXButton("searchorders", "Search"));
		addButton(new EXButton("showAllorders", "Show All"));
		getDescendentByName("searchorders").addEvent(this, CLICK);
		getDescendentByName("showAllorders").addEvent(this, CLICK);
		
		
		
		//addChild(new EXContainer("searchorders", "button").setText("Search").addEvent(this, CLICK));
		//addChild(new EXContainer("showAllorders", "button").setText("Show All").addEvent(this, CLICK));
		
	}
	
	
	
	private void search(){
		String invNumber = ((StatefullComponent)getDescendentByName("invNumber")).getValue().toString();
		String paymentmethod = ((StatefullComponent)getDescendentByName("paymentMethod")).getValue().toString();
		Date from = (Date)((StatefullComponent)getDescendentByName("from")).getValue();
		Date to = (Date)((StatefullComponent)getDescendentByName("to")).getValue();
		
		OrderInfo ex = new OrderInfo();
		ex.setFsCode(invNumber);
		ex.setPaymentMethod(paymentmethod);
		
		
		final List<Integer> statuss = new ArrayList<Integer>();
		ComponentUtil.iterateOverDescendentsOfType(this, EXCheckBox.class, new ComponentVisitor() {
			
			@Override
			public void doVisit(Container c) {
				if(((EXCheckBox)c).isChecked()){
					statuss.add(new Integer( c.getName().replace("status_", "").trim()));
				}
				
			}
		});

		//List<OrderInfo> files = new OrderService().searchByExample(ex, from, to, true,statuss, 0, 20);
		getAncestorOfType(EXOrdersList.class).setOrders(ex, from, to, true,statuss);
		getAncestorOfType(EXFieldSet.class).setTitle(ex.getCount() + " Orders found");
	}
	
	private void showAll(){
		
		
		List<Integer> statuss = new ArrayList<Integer>();
		int[] ss = SpringUtil.getBeanOfType(OrdersWorkflow.class).getAvailableStates();
		for(int i : ss){
			statuss.add(i);
		}
		OrderInfo ex = new OrderInfo();
		//List<OrderInfo> files = new OrderService().searchByExample(new OrderInfo(), null, null, false,statuss, 0, 20);
		getAncestorOfType(EXOrdersList.class).setOrders(ex, null, null, false,statuss);
		getAncestorOfType(EXFieldSet.class).setTitle(ex.getCount() + " Orders found");
	}


	@Override
	public void ClientAction(ClientProxy container) {
		container.mask(container.getAncestorOfType(EXOrdersList.class)).makeServerRequest(this);
		
	}


	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		if(container.getName().equals("searchorders")){
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
