package org.castafiore.accounting.ui;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.castafiore.SimpleKeyValuePair;
import org.castafiore.accounting.Account;
import org.castafiore.accounting.CashBook;
import org.castafiore.accounting.CashBookEntry;
import org.castafiore.inventory.AbstractModel;
import org.castafiore.inventory.orders.OrderService;
import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXDatePicker;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXTextArea;
import org.castafiore.ui.ex.form.button.Button;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.ui.ex.form.table.EXPagineableTable;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.BinaryFile;

public class BCashBookEntryForm extends EXDynaformPanel implements EventDispatcher{

	private String cashbookdir;
	
	public BCashBookEntryForm(String name, String cashbookdir) {
		super(name, "New CashBook Entry");
		this.cashbookdir = cashbookdir;
		
		addField("Date :",new EXDatePicker("date"));
		addField("Title :",new EXInput("title"));
		
		
		CashBook cb = (CashBook)SpringUtil.getRepositoryService().getFile(cashbookdir, Util.getLoggedOrganization());
		List<Account> accounts = cb.getAccountsConfigured();
		
		
		DefaultDataModel<Object> model = new DefaultDataModel<Object>();
		for(Account acc : accounts){
			if(!StringUtil.isNotEmpty(acc.getCategory())){
				SimpleKeyValuePair kv = new SimpleKeyValuePair(acc.getAbsolutePath(), acc.getTitle());
				model.addItem(kv);
			}
		}
		addField("Account :", new EXSelect("account", model));
		
		addField("Amount :",new EXInput("amount"));
		addField("Paid by:",new EXSelect("type", new DefaultDataModel<Object>().addItem("Cash", "Cheque", "Bank transfer")));
		addField("Check No. :",new EXInput("check"));
		addField("Description :", new EXTextArea("description"));
		
		try{
			
			
			
			
			EXSelect posSelect = new EXSelect("pos", new OrderService().getPointOfSales());
			
			addField("POS :",posSelect);
			}catch(Exception e){
				e.printStackTrace();
			}
		
		addButton((Button)new EXButton("save", "Save and Close").addEvent(DISPATCHER, Event.CLICK));
		addButton((Button)new EXButton("saveaddnew", "Save and Add new").addEvent(DISPATCHER, Event.CLICK));
		addButton((Button)new EXButton("cancel", "Cancel").addEvent(CLOSE_EVENT, Event.CLICK));
		setStyle("width", "500px");
		

		ComponentUtil.applyStyleOnAll(this, StatefullComponent.class, "width", "200px");
		ComponentUtil.applyStyleOnAll(this, StatefullComponent.class, "width", "350px");
		getDescendentOfType(EXTextArea.class).setStyle("height", "100px");
		setStyle("z-index", "3000");
	}
	
	public void setBookEntry(CashBookEntry p){
		if(p == null){
			setAttribute("path", "new");
			clear();
		}else{
			setAttribute("path", p.getAbsolutePath());
			getField("date").setValue(p.getDateOfTransaction());
			setValue("title", p.getTitle());
			setValue("description", p.getSummary());
			List<Account> details = p.getDetails();
			if(details.size() > 0){
				getField("account").setValue(new SimpleKeyValuePair(details.get(0).getAbsolutePath(),details.get(0).getTitle()));
			}
			setValue("amount", p.getTotal()!=null?  p.getTotal().toString():"0.00");
			setValue("type",p.getPaymentMethod()!= null ?p.getPaymentMethod() : "Cash");
			setValue("check",p.getChequeNo()!= null ?p.getChequeNo() : "");
			
		}
	}
	
	public void clear(){
		getField("date").setValue(new Date());
		setValue("title", "");
		setValue("description", "");
		getField("account").setValue(new SimpleKeyValuePair("", ""));
		setValue("amount", "0.00");
		//setValue("type", "Cash");
		setValue("check", "");
		
	}
	
	public void setValue(String field, String value){
		getField(field).setValue(value);
	}
	
	public String getValue(String field){
		return getField(field).getValue().toString();
	}
	public void save(){
		String path = getAttribute("path");
		CashBookEntry p = null;
		if(path.equals("new")){
			CashBook book = (CashBook)SpringUtil.getRepositoryService().getFile(cashbookdir, Util.getLoggedOrganization());
			p = book.createEntry(new Date().getTime() + "");
		}else{
			p = (CashBookEntry)SpringUtil.getRepositoryService().getFile(path, Util.getRemoteUser());
		}
		p.setTitle(getValue("title"));
		p.setSummary(getValue("description"));
		p.setPointOfSale(getValue("pos"));
		
		p.setDateOfTransaction((Date)getField("date").getValue());
		
		p.setTotal(new BigDecimal(getValue("amount")));
		SimpleKeyValuePair kv = (SimpleKeyValuePair)getField("account").getValue();
		Account acc = (Account)SpringUtil.getRepositoryService().getFile(kv.getKey(), Util.getLoggedOrganization());
		p.setAccount(acc);
		p.setChequeNo(getValue("check"));
		p.setPaymentMethod(getValue("type"));
		p.setCode(acc.getCode());
		p.save();
		
	}
	
	
	public void New(){
		setBookEntry(null);
	}
	
	public void saveNClose(Container source){
		save();
		back(source);
	}
	
	public void back(Container source){
		CLOSE_EVENT.ServerAction(source, new HashMap<String, String>());
EXPagineableTable ptable = getAncestorOfType(PopupContainer.class).getDescendentOfType(EXPagineableTable.class);
		
		
		((AbstractModel)ptable.getDescendentOfType(EXTable.class).getModel()).refresh();
		ptable.refresh();
	}
	
	public void SaveNNew(){
		save();
		New();
	}

	@Override
	public void executeAction(Container source) {
		
		if(source.getName().equals("saveaddnew")){
			SaveNNew();
		}else if(source.getName().equals("save")){
			saveNClose(source);
		}
	}
}