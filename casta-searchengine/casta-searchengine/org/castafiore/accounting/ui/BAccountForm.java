package org.castafiore.accounting.ui;

import java.util.Date;
import java.util.HashMap;

import org.castafiore.accounting.Account;
import org.castafiore.accounting.CashBook;
import org.castafiore.inventory.AbstractModel;
import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
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

public class BAccountForm extends EXDynaformPanel implements EventDispatcher{

	private String cashbookdir;
	
	public BAccountForm(String name, String cashbookdir) {
		super(name, "New Account");
		this.cashbookdir = cashbookdir;
		addField("Code :",new EXInput("code"));
		addField("Title :",new EXInput("title"));
		addField("Default Amount :",new EXInput("default"));
		addField("Description :", new EXTextArea("description"));
		addField("Type :", new EXSelect("type", new DefaultDataModel<Object>().addItem("Income", "Expense", "Liability", "Equity", "Asset")));
		addButton((Button)new EXButton("save", "Save and Close").addEvent(DISPATCHER, Event.CLICK));
		addButton((Button)new EXButton("saveaddnew", "Save and Add new").addEvent(DISPATCHER, Event.CLICK));
		addButton((Button)new EXButton("cancel", "Cancel").addEvent(CLOSE_EVENT, Event.CLICK));
		setStyle("width", "500px");
		
		//ComponentUtil.applyStyleOnAll(this, StatefullComponent.class, "padding", "3px");
		//ComponentUtil.applyStyleOnAll(this, StatefullComponent.class, "margin", "10px 3px");
		ComponentUtil.applyStyleOnAll(this, StatefullComponent.class, "width", "350px");
		getDescendentOfType(EXTextArea.class).setStyle("height", "100px");
		setStyle("z-index", "3000");
	}
	
	public void setAccount(Account p){
		if(p == null){
			setAttribute("path", "new");
			clear();
		}else{
			setAttribute("path", p.getAbsolutePath());
			setValue("code", p.getCode());
			setValue("title", p.getTitle());
			setValue("description", p.getSummary());
			getField("type").setValue(p.getAccType());
			getField("default").setValue(p.getDefaultValue().toPlainString());
		}
	}
	
	public void clear(){
		setValue("code", "");
		setValue("title", "");
		setValue("description", "");
		setValue("default", "");
	}
	
	public void setValue(String field, String value){
		getField(field).setValue(value);
	}
	
	public String getValue(String field){
		return getField(field).getValue().toString();
	}
	public String save(){
		String code = getValue("code");
		String title = getValue("title");
		
		
		if(!StringUtil.isNotEmpty(code)){
			getField("code").addClass("ui-state-errror");
			return "Please enter a code";
		}
		
		if(!StringUtil.isNotEmpty(title)){
			getField("title").addClass("ui-state-errror");
			return "Please enter a title";
		}
		
		String path = getAttribute("path");
		Account p = null;
		if(path.equals("new")){
			CashBook book = (CashBook)SpringUtil.getRepositoryService().getFile(cashbookdir, Util.getLoggedOrganization());
			p = book.createAccount(new Date().getTime() + "");
			book.save();
		}else{
			p = (Account)SpringUtil.getRepositoryService().getFile(path, Util.getRemoteUser());
		}
		p.setCode(getValue("code"));
		p.setTitle(getValue("title"));
		p.setSummary(getValue("description"));
		p.setAccType(getValue("type"));
		p.save();
		return null;
		
	}
	
	
	public void New(){
		setAccount(null);
	}
	
	public void saveNClose(Container source){
		if(save()==null)
			back(source);
	}
	
	public void back(Container source){
		CLOSE_EVENT.ServerAction(source, new HashMap<String, String>());
		
		EXPagineableTable ptable = getAncestorOfType(PopupContainer.class).getDescendentOfType(EXPagineableTable.class);
		
		
		((AbstractModel)ptable.getDescendentOfType(EXTable.class).getModel()).refresh();
		ptable.refresh();
	}
	
	public void SaveNNew(){
		if(save()==null)
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
