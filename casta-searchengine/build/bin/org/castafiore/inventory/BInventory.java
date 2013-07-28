package org.castafiore.inventory;

import org.castafiore.accounting.CashBook;
import org.castafiore.accounting.ui.AccountCellRenderer;
import org.castafiore.accounting.ui.AccountModel;
import org.castafiore.accounting.ui.BAccountForm;
import org.castafiore.accounting.ui.BCashBookEntryForm;
import org.castafiore.accounting.ui.CashBookEntryCellRenderer;
import org.castafiore.accounting.ui.CashBookEntryModel;
import org.castafiore.inventory.customers.BCustomerForm;
import org.castafiore.inventory.customers.CustomerCellRenderer;
import org.castafiore.inventory.customers.CustomerModel;
import org.castafiore.inventory.orders.BInvoiceForm;
import org.castafiore.inventory.orders.SalesOrderCellRenderer;
import org.castafiore.inventory.orders.SalesOrderModel;
import org.castafiore.inventory.product.BProductForm;
import org.castafiore.inventory.product.ProductCellRenderer;
import org.castafiore.inventory.product.ProductModel;
import org.castafiore.inventory.suppliers.BSupplierForm;
import org.castafiore.inventory.suppliers.SupplierCellRenderer;
import org.castafiore.inventory.suppliers.SupplierModel;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.table.EXPagineableTable;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.layout.EXBorderLayoutContainer;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.ui.ex.toolbar.EXToolBar;
import org.castafiore.utils.EventUtil;

public class BInventory extends EXPanel{
	
	public BInventory(){
		super("BInventory");
		setTitle("Inventory System");
		EXBorderLayoutContainer c = new EXBorderLayoutContainer("bo");
		c.addChild(new InventoryToolbar("main"), EXBorderLayoutContainer.TOP);
		setBody(c);
		setStyle("width", "800px");
	}
	
	
	public void showProducts(){
		EXPanel panel = new EXPanel("products", "Products");
		panel.setShowCloseButton(false);
		panel.setShowHeader(false);
		panel.setShowFooter(false);
		panel.setDraggable(false);
		panel.setStyle("margin", "auto");
		
		panel.setStyle("width", "99%");
		EXToolBar toolbar = new EXToolBar("productToolbar");;
		toolbar.addItem(new EXButton("newProduct", "New Product"));
		toolbar.getDescendentByName("newProduct").addEvent(EventUtil.GENERIC_FORM_METHOD_EVENT, Event.CLICK).setAttribute("method", "newProduct").setAttribute("ancestor", getClass().getName());
		
		panel.setBody(new EXContainer("", "div").setStyle("width", "100%").setStyle("height", "450px"));
		panel.getBody().addChild(toolbar);
		EXTable table =new EXTable("taleList", new ProductModel());
		table.setStyle("width", "100%");
		table.setCellRenderer(new ProductCellRenderer());
		EXPagineableTable pTable = new EXPagineableTable("tableList", table);
		pTable.setStyle("width", "100%");
		panel.getBody().addChild(pTable);
		
		
		Container center = getDescendentOfType(EXBorderLayoutContainer.class).getContainer(EXBorderLayoutContainer.CENTER);
		center.getChildren().clear();
		center.setRendered(false);
		center.addChild(panel);
	}
	
	public void newProduct(Container source){
		BProductForm form = new BProductForm("");
		form.New();
		source.getAncestorOfType(EXPanel.class).addChild(form);
	}
	
	
	public void showCustomers(){
		EXPanel panel = new EXPanel("customer", "Customers");
		panel.setShowCloseButton(false);
		panel.setShowHeader(false);
		panel.setShowFooter(false);
		panel.setDraggable(false);
		panel.setStyle("margin", "auto");
		
		panel.setStyle("width", "99%");
		EXToolBar toolbar = new EXToolBar("customerToolbar");;
		toolbar.addItem(new EXButton("newCustomer", "New Customer"));
		toolbar.getDescendentByName("newCustomer").addEvent(EventUtil.GENERIC_FORM_METHOD_EVENT, Event.CLICK).setAttribute("method", "newCustomer").setAttribute("ancestor", getClass().getName());
		
		panel.setBody(new EXContainer("", "div").setStyle("width", "100%").setStyle("height", "450px"));
		panel.getBody().addChild(toolbar);
		EXTable table =new EXTable("taleList", new CustomerModel());
		table.setStyle("width", "100%");
		table.setCellRenderer(new CustomerCellRenderer());
		EXPagineableTable pTable = new EXPagineableTable("tableList", table);
		pTable.setStyle("width", "100%");
		panel.getBody().addChild(pTable);
		
		
		Container center = getDescendentOfType(EXBorderLayoutContainer.class).getContainer(EXBorderLayoutContainer.CENTER);
		center.getChildren().clear();
		center.setRendered(false);
		center.addChild(panel);
	}
	
	
	public void newCustomer(Container source)throws Exception{
		BCustomerForm form = new BCustomerForm("");
		form.New();
		source.getAncestorOfType(EXPanel.class).addChild(form);
	}
	
	
	
	public void showSuppliers(){
		EXPanel panel = new EXPanel("customer", "Customers");
		panel.setShowCloseButton(false);
		panel.setShowHeader(false);
		panel.setShowFooter(false);
		panel.setDraggable(false);
		panel.setStyle("margin", "auto");
		
		panel.setStyle("width", "99%");
		EXToolBar toolbar = new EXToolBar("supplierToolbar");;
		toolbar.addItem(new EXButton("newSupplier", "New Supplier"));
		toolbar.getDescendentByName("newSupplier").addEvent(EventUtil.GENERIC_FORM_METHOD_EVENT, Event.CLICK).setAttribute("method", "newSupplier").setAttribute("ancestor", getClass().getName());
		
		panel.setBody(new EXContainer("", "div").setStyle("width", "100%").setStyle("height", "450px"));
		panel.getBody().addChild(toolbar);
		EXTable table =new EXTable("taleList", new SupplierModel());
		table.setStyle("width", "100%");
		table.setCellRenderer(new SupplierCellRenderer());
		EXPagineableTable pTable = new EXPagineableTable("tableList", table);
		pTable.setStyle("width", "100%");
		panel.getBody().addChild(pTable);
		
		
		Container center = getDescendentOfType(EXBorderLayoutContainer.class).getContainer(EXBorderLayoutContainer.CENTER);
		center.getChildren().clear();
		center.setRendered(false);
		center.addChild(panel);
	}
	
	public void newSupplier(Container source)throws Exception{
		BSupplierForm form = new BSupplierForm("");
		form.New();
		source.getAncestorOfType(EXPanel.class).addChild(form);
	}
	
	
	public void showInvoices(){
		EXPanel panel = new EXPanel("invoices", "Orders");
		panel.setShowCloseButton(false);
		panel.setShowHeader(false);
		panel.setShowFooter(false);
		panel.setDraggable(false);
		panel.setStyle("margin", "auto");
		
		panel.setStyle("width", "99%");
		EXToolBar toolbar = new EXToolBar("invoicesToolbar");;
		toolbar.addItem(new EXButton("newInvoice", "New Invoice"));
		toolbar.getDescendentByName("newInvoice").addEvent(EventUtil.GENERIC_FORM_METHOD_EVENT, Event.CLICK).setAttribute("method", "newInvoice").setAttribute("ancestor", getClass().getName());
		
		panel.setBody(new EXContainer("", "div").setStyle("width", "100%").setStyle("height", "450px"));
		panel.getBody().addChild(toolbar);
		EXTable table =new EXTable("taleList", new SalesOrderModel());
		table.setStyle("width", "100%");
		table.setCellRenderer(new SalesOrderCellRenderer());
		EXPagineableTable pTable = new EXPagineableTable("tableList", table);
		pTable.setStyle("width", "100%");
		panel.getBody().addChild(pTable);
		
		
		Container center = getDescendentOfType(EXBorderLayoutContainer.class).getContainer(EXBorderLayoutContainer.CENTER);
		center.getChildren().clear();
		center.setRendered(false);
		center.addChild(panel);
	}
	
	public void newInvoice(Container source){
		
		BInvoiceForm form = new BInvoiceForm("BInvoiceForm");
		
		source.getAncestorOfType(EXPanel.class).addChild(form);
	}
	
	
	public void showAccounts(){
		EXPanel panel = new EXPanel("accounts", "Accounts");
		panel.setShowCloseButton(false);
		panel.setShowHeader(false);
		panel.setShowFooter(false);
		panel.setDraggable(false);
		panel.setStyle("margin", "auto");
		
		panel.setStyle("width", "99%");
		EXToolBar toolbar = new EXToolBar("accountsToolBar");;
		toolbar.addItem(new EXButton("newAccount", "New Account"));
		toolbar.getDescendentByName("newAccount").addEvent(EventUtil.GENERIC_FORM_METHOD_EVENT, Event.CLICK).setAttribute("method", "newAccount").setAttribute("ancestor", getClass().getName());
		
		panel.setBody(new EXContainer("", "div").setStyle("width", "100%").setStyle("height", "450px"));
		panel.getBody().addChild(toolbar);
		CashBook book = MallUtil.getCurrentMerchant().createCashBook("DefaultCashBook");
		EXTable table =new EXTable("taleList", new AccountModel(book.getAbsolutePath()));
		table.setStyle("width", "100%");
		table.setCellRenderer(new AccountCellRenderer());
		EXPagineableTable pTable = new EXPagineableTable("tableList", table);
		pTable.setStyle("width", "100%");
		panel.getBody().addChild(pTable);
		
		
		Container center = getDescendentOfType(EXBorderLayoutContainer.class).getContainer(EXBorderLayoutContainer.CENTER);
		center.getChildren().clear();
		center.setRendered(false);
		center.addChild(panel);
	}
	
	
	public void newAccount(Container source){
		CashBook book = MallUtil.getCurrentMerchant().createCashBook("DefaultCashBook");
		BAccountForm form = new BAccountForm("BAccountForm",book.getAbsolutePath());
		form.New();
		source.getAncestorOfType(EXPanel.class).addChild(form);
	}
	
	
	public void showCashBookEntries(){
		EXPanel panel = new EXPanel("cashbook", "CashBook");
		panel.setShowCloseButton(false);
		panel.setShowHeader(false);
		panel.setShowFooter(false);
		panel.setDraggable(false);
		panel.setStyle("margin", "auto");
		
		panel.setStyle("width", "99%");
		EXToolBar toolbar = new EXToolBar("cashBookToolBar");;
		toolbar.addItem(new EXButton("newCashBookEntry", "New Cash Book Entry"));
		toolbar.getDescendentByName("newCashBookEntry").addEvent(EventUtil.GENERIC_FORM_METHOD_EVENT, Event.CLICK).setAttribute("method", "newCashBookEntry").setAttribute("ancestor", getClass().getName());
		
		panel.setBody(new EXContainer("", "div").setStyle("width", "100%").setStyle("height", "450px"));
		panel.getBody().addChild(toolbar);
		CashBook book = MallUtil.getCurrentMerchant().createCashBook("DefaultCashBook");
		EXTable table =new EXTable("taleList", new CashBookEntryModel(book.getAbsolutePath()));
		table.setStyle("width", "100%");
		table.setCellRenderer(new CashBookEntryCellRenderer());
		EXPagineableTable pTable = new EXPagineableTable("tableList", table);
		pTable.setStyle("width", "100%");
		panel.getBody().addChild(pTable);
		
		
		Container center = getDescendentOfType(EXBorderLayoutContainer.class).getContainer(EXBorderLayoutContainer.CENTER);
		center.getChildren().clear();
		center.setRendered(false);
		center.addChild(panel);
	}
	
	
	public void newCashBookEntry(Container source){
		CashBook book = MallUtil.getCurrentMerchant().createCashBook("DefaultCashBook");
		BCashBookEntryForm form = new BCashBookEntryForm("BCashBookEntryForm", book.getAbsolutePath());
		form.New();
		source.getAncestorOfType(EXPanel.class).addChild(form);
	}

}
