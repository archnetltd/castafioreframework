package org.castafiore.inventory.customers;

import java.util.HashMap;

import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.security.Address;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.button.Button;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.wfs.Util;

public class BCustomerForm extends EXDynaformPanel implements EventDispatcher{

	
	private final static String[] fields = new String[]{"Code", "FirstName", "LastName", "AddressLine1", "AddressLine2", "AddressLine3", "Phone", "Mobile", "Fax", "Email", "VatReg"};
	public BCustomerForm(String name) {
		super(name, "New Customer");
		for(String s : fields){
			addInput(s);
		}
		addButton((Button)new EXButton("save", "Save and Close").addEvent(DISPATCHER, Event.CLICK));
		addButton((Button)new EXButton("saveaddnew", "Save and Add new").addEvent(DISPATCHER, Event.CLICK));
		addButton((Button)new EXButton("cancel", "Cancel").addEvent(CLOSE_EVENT, Event.CLICK));
		
		setStyle("width", "500px");
		
		ComponentUtil.applyStyleOnAll(this, StatefullComponent.class, "padding", "3px");
		ComponentUtil.applyStyleOnAll(this, StatefullComponent.class, "margin", "10px 3px");
		ComponentUtil.applyStyleOnAll(this, StatefullComponent.class, "width", "200px");
	}
	
	public void addInput(String name){
		addField(name, new EXInput(name));
	}
	
	private void setValue(String field, String value){
		getField(field).setValue(value);
	}
	
	private String getValue(String field){
		return getField(field).getValue().toString();
	}
	
	private void clear(){
		for(String field : fields){
			setValue(field, "");
		}
	}
	
	private void save()throws Exception{
		Customer c;
		Address add;
		String username = getAttribute("username");
		if(username.equals("new")){
			c = MallUtil.getCurrentUser().getMerchant().createCustomer();
			c.setUsername(System.currentTimeMillis()+ "");
			c.setPassword(c.getUsername());
			add = new Address();
			add.setDefaultAddress(true);
			c.addAddress(add);
		}else{
			c = (Customer)SpringUtil.getSecurityService().loadUserByUsername(username);
			add = c.getDefaultAddress();
			if(add == null){
				add = new Address();
				add.setDefaultAddress(true);
			}
			c.addAddress(add);
		}
		 
		for(String f : fields){
			if(f.startsWith("Address")){
				if(f.equals("AddressLine1")){
					add.setLine1(getValue(f));
				}else if(f.equals("AddressLine2")){
					add.setLine2(getValue(f));
				}else if(f.equals("AddressLine3")){
					add.setCity(getValue(f));
				} 
			}else
				Customer.class.getMethod("set" + f, String.class).invoke(c, getValue(f));
		}
		SpringUtil.getSecurityService().saveOrUpdateUser(c);
	}
	
	
	public void setCustomer(Customer customer)throws Exception{
		if(customer == null){
			setAttribute("username", "new");
			clear();
			setTitle("New customer");
		}else{
			setAttribute("username", customer.getUsername());
			setTitle("Edit customer - " + customer.toString());
			for(String f : fields){
				if(f.equals("AddressLine1")){
					setValue(f,customer.getDefaultAddress().getLine1());
				}else if(f.equals("AddressLine2")){
					setValue(f,customer.getDefaultAddress().getLine2());
				}else if(f.equals("AddressLine3")){
					setValue(f,customer.getDefaultAddress().getCity());
				}else{
					setValue(f, Customer.class.getMethod("get" + f, new Class[]{}).invoke(customer, new Object[]{}).toString() ) ;
				}
			}
		}
	}
	
	public void New()throws Exception{
		setCustomer(null);
	}
	
	public void back(Container source){
		CLOSE_EVENT.ServerAction(source, new HashMap<String, String>());
	}
	
	

	@Override
	public void executeAction(Container source) {
		try{
			
			
			if(source.getName().equals("save")){
				save();
				back(source);
			}else if(source.getName().equals("saveaddnew")){
				save();
				New();
			}else if(source.getName().equals("cancel")){
				back(source);
			}
		}catch(Exception e){
			throw new UIException(e);
		}
		
	}
	
	
	

}
