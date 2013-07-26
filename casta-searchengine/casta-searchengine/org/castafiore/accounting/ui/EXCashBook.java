package org.castafiore.accounting.ui;

import java.util.Map;

import org.castafiore.accounting.CashBook;
import org.castafiore.searchengine.MallUtil;

import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.table.EXScrollableTable;
import org.castafiore.ui.ex.form.table.EXTable;

import org.castafiore.ui.ex.panel.Panel;
import org.castafiore.ui.ex.toolbar.EXToolBar;

public class EXCashBook extends EXContainer implements Event{

	public EXCashBook(String name) {
		super(name, "div");
		init();
		
	}

	
	public void init(){

		EXToolBar toolbar = new EXToolBar("accountsToolBar");
		toolbar.addClass("btnCtn");
		
		EXButton accountList = new EXButton("accountList", "Account List");
		toolbar.addItem(accountList);
		accountList.setStyleClass("");
		accountList.addEvent(this, Event.CLICK);
		
		EXButton newAccount = new EXButton("newAccount", "New Account");
		toolbar.addItem(newAccount);
		newAccount.setStyleClass("");
		newAccount.addEvent(this, Event.CLICK);
		
		EXButton cashBook = new EXButton("cashBook", "Cash book");
		toolbar.addItem(cashBook);
		cashBook.setStyleClass("");
		cashBook.addEvent(this, Event.CLICK);
		
		EXButton newEntry = new EXButton("newEntry", "New Cash Book entry");
		toolbar.addItem(newEntry);
		newEntry.setStyleClass("");
		newEntry.addEvent(this, Event.CLICK);
		
		
		//window.setBody(new EXContainer("", "div").setStyle("width", "100%").setStyle("height", "450px"));
		addChild(toolbar);
		CashBook book = MallUtil.getCurrentMerchant().createCashBook("DefaultCashBook");
		EXTable table =new EXTable("accountList", new AccountModel(book.getAbsolutePath()));
		table.setStyle("width", "100%");
		table.setCellRenderer(new AccountCellRenderer());
		EXScrollableTable pTable = new EXScrollableTable("tableList", table);
		pTable.setStyle("width", "100%").setStyle("height", "410px");
		addChild(pTable);
		
	}
	
	public void accountList(Container source){
		Panel panel = source.getAncestorOfType(Panel.class);
		Container body = panel.getBody();
		if(body.getChildren().size() > 1 && body.getChild("accountList")==null){
			body.getChildren().get(1).remove();
		}
		if(body.getChildren().size() == 1){
			CashBook book = MallUtil.getCurrentMerchant().createCashBook("DefaultCashBook");
			EXTable table =new EXTable("taleList", new AccountModel(book.getAbsolutePath()));
			table.setStyle("width", "100%");
			table.setCellRenderer(new AccountCellRenderer());
			
			EXScrollableTable pTable = new EXScrollableTable("tableList", table);
			
			//EXPagineableTable pTable = new EXPagineableTable("tableList", table);
			pTable.setStyle("width", "100%").setStyle("height", "410px");
			panel.getBody().addChild(pTable);
		}
	}
	
	public void newAccount(Container source){
		CashBook book = MallUtil.getCurrentMerchant().createCashBook("DefaultCashBook");
		BAccountForm form = new BAccountForm("BAccountForm",book.getAbsolutePath());
		form.New();
		source.getAncestorOfType(PopupContainer.class).addPopup(form.setStyle("z-index", "3000"));
	}
	
	public void cashBook(Container source){
		//EXWindow panel = new EXWindow("cashbook", "CashBook");
		Panel panel = source.getAncestorOfType(Panel.class);
		Container body = panel.getBody();
		if(body.getChildren().size() > 1 && body.getChild("cashBook")==null){
			body.getChildren().get(1).remove();
		}
		
		if(body.getChildren().size() == 1){
			CashBook book = MallUtil.getCurrentMerchant().createCashBook("DefaultCashBook");
			EXTable table =new EXTable("cashBook", new CashBookEntryModel(book.getAbsolutePath()));
			table.setStyle("width", "100%");
			table.setCellRenderer(new CashBookEntryCellRenderer());
			EXScrollableTable pTable = new EXScrollableTable("tableList", table);
			pTable.setStyle("width", "100%").setStyle("height", "410px");
			panel.getBody().addChild(pTable);
		}
	}
	
	
	public void newEntry(Container source){
		CashBook book = MallUtil.getCurrentMerchant().createCashBook("DefaultCashBook");
		BCashBookEntryForm form = new BCashBookEntryForm("BCashBookEntryForm", book.getAbsolutePath());
		form.New();
		source.getAncestorOfType(PopupContainer.class).addPopup(form);
	}
		

	public void ClientAction(ClientProxy container) {
		container.mask(container.getAncestorOfType(Panel.class));
		container.makeServerRequest(this);
		
	}

	public boolean ServerAction(Container container,
			Map<String, String> request) throws UIException {
		
		
		try{
		getClass().getMethod(container.getName(), Container.class).invoke(this, container);
		}catch(Exception e){
			throw new UIException(e);
		}
		return true;
	}


	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}



}
