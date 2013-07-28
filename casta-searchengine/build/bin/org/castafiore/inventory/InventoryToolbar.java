package org.castafiore.inventory;

import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.button.EXButtonSet;
import org.castafiore.ui.ex.toolbar.EXToolBar;
import org.castafiore.utils.EventUtil;

public class InventoryToolbar extends EXToolBar{

	public InventoryToolbar(String name) {
		super(name);
		
		EXButtonSet set = new EXButtonSet("set");
		set.addItem(new EXButton("products", "Products"))
		.addItem(new EXButton("customers", "Customers"))
		.addItem(new EXButton("suppliers", "Suppliers"))
		.addItem(new EXButton("invoices", "Invoices"))
		.addItem(new EXButton("accounts", "Accounts"))
		.addItem(new EXButton("cashBook", "CashBook"));
		set.setTouching(true);
		addItem(set);
		getDescendentByName("products").setAttribute("method", "showProducts").setAttribute("ancestor", BInventory.class.getName()).addEvent(EventUtil.GENERIC_FORM_METHOD_EVENT, Event.CLICK);
		getDescendentByName("customers").setAttribute("method", "showCustomers").setAttribute("ancestor", BInventory.class.getName()).addEvent(EventUtil.GENERIC_FORM_METHOD_EVENT, Event.CLICK);
		getDescendentByName("suppliers").setAttribute("method", "showSuppliers").setAttribute("ancestor", BInventory.class.getName()).addEvent(EventUtil.GENERIC_FORM_METHOD_EVENT, Event.CLICK);
		getDescendentByName("invoices").setAttribute("method", "showInvoices").setAttribute("ancestor", BInventory.class.getName()).addEvent(EventUtil.GENERIC_FORM_METHOD_EVENT, Event.CLICK);
		getDescendentByName("accounts").setAttribute("method", "showAccounts").setAttribute("ancestor", BInventory.class.getName()).addEvent(EventUtil.GENERIC_FORM_METHOD_EVENT, Event.CLICK);
		getDescendentByName("cashBook").setAttribute("method", "showCashBookEntries").setAttribute("ancestor", BInventory.class.getName()).addEvent(EventUtil.GENERIC_FORM_METHOD_EVENT, Event.CLICK);
		
	}

}
