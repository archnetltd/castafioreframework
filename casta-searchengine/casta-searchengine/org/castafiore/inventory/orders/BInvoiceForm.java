package org.castafiore.inventory.orders;

import java.util.List;
import java.util.Map;

import org.castafiore.SimpleKeyValuePair;
import org.castafiore.inventory.customers.Customer;
import org.castafiore.persistence.Dao;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.security.Address;
import org.castafiore.security.User;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXDatePicker;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.list.DataModel;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class BInvoiceForm extends EXXHTMLFragment implements Event{

	public BInvoiceForm(String name) {
		super(name, "templates/banharally/BInvoiceForm.xhtml");
		addChild(new EXInput("invoicenumber"));
		addChild(new EXDatePicker("date"));
		DataModel<Object> model = getCustomerModel();
		EXSelect cust= new EXSelect("customer", model);
		if(model.getSize() > 0)
			cust.setValue(model.getValue(0));
		addChild(cust.addEvent(this, CHANGE));
		
		DataModel<Object> mInvoiceTypes = getInvoiceTypes();
		EXSelect invoiceType= new EXSelect("invoicetype", mInvoiceTypes);
		invoiceType.setValue(mInvoiceTypes.getValue(0));
		addChild(invoiceType.addEvent(this, CHANGE));
		addChild(new EXContainer("customeraddressline1", "span"));
		addChild(new EXContainer("customeraddressline2", "span"));
		addChild(new EXContainer("customertelephone", "span"));
		addChild(new EXContainer("customermobile", "span"));
		addChild(new EXContainer("customeremail", "span"));
		addChild(new EXContainer("customerfax", "span"));
		addChild(new EXContainer("newLine", "button").setText("New Product").addEvent(this, CLICK));
		addChild(new EXContainer("details", "div"));
		updateCustomer();
	}

	private void updateCustomer(){
		Customer customer = (Customer)((EXSelect)getChild("customer")).getValue();
		if(customer != null){
			customer = (Customer)SpringUtil.getSecurityService().loadUserByUsername(customer.getUsername());
			Address add = customer.getDefaultAddress();
			if(add != null){
				getChild("customeraddressline1").setText(add.getLine1());
				getChild("customeraddressline2").setText(add.getLine2());
			}
			
			getChild("customertelephone").setText(customer.getPhone());
			getChild("customermobile").setText(customer.getMobile());
			getChild("customeremail").setText(customer.getEmail());
			getChild("customerfax").setText(customer.getFax());
		}
	}
	
	
	
	public void New(){
		//new invoice number
		//clean up details
		//
	}
	
	public void newLine(){
		BNewLineForm form = new BNewLineForm("");
		form.New();
		getAncestorOfType(EXPanel.class).addChild(form);
	}
	
	
	public void save(){
		
	}
	
	private DataModel<Object> getInvoiceTypes(){
		DefaultDataModel<Object> model = new DefaultDataModel<Object>();
		model.addItem(new SimpleKeyValuePair("creditvat", "Credit sales V.A.T"));
		model.addItem(new SimpleKeyValuePair("creditnonvat", "Credit sales Non vat"));
		model.addItem(new SimpleKeyValuePair("cashvat", "Cash sales V.A.T"));
		model.addItem(new SimpleKeyValuePair("cashnonvat", "Cash sales Non vat"));
		
		return model;
		
		
	}
	
	public List<User> getCustomers(){
		Session session = SpringUtil.getBeanOfType(Dao.class).getReadOnlySession();
		Criteria crit = session.createCriteria(Customer.class).add(Restrictions.eq("merchantUsername", MallUtil.getCurrentUser().getMerchant().getUser().getUsername()));
		List customers = crit.addOrder(Order.asc("firstName")).list();
		return customers;
	}
	
	protected  DataModel<Object> getCustomerModel(){
		List users = getCustomers();
		DefaultDataModel<Object> model = new DefaultDataModel<Object>(users);
		return model;
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		if(container instanceof EXSelect){
			updateCustomer();
		}else if(container.getName().equalsIgnoreCase("newLine")){
			newLine();
		}
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {

	}

}
