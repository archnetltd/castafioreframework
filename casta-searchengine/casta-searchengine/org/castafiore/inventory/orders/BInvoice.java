package org.castafiore.inventory.orders;

import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.scripting.EXXHTMLFragment;

public class BInvoice extends EXXHTMLFragment {

	public BInvoice(String name) {
		super(name, "templates/ban/EXInvoice");
		addSpan("code").addSpan("date").addSpan("customername")
		.addSpan("customerAddLine1")
		.addSpan("customerAddLine2")
		.addSpan("customerAddLine3")
		.addSpan("phone")
		.addSpan("mobile")
		.addSpan("fax")
		.addSpan("email")
		.addSpan("invoicetype")
		.addSpan("vatReg");
		
		
		
	}
	
	public BInvoice addSpan(String name){
		addChild(new EXContainer(name, "span"));
		return this;
	}
	
	public void setSpan(String name, String value){
		getChild(name).setText(value);
	}
	
	public void setSalesOrder(SalesOrder salesOrder){
		setSpan("code", salesOrder.getCode());
		setSpan("invoicetype", salesOrder.getClass().getSimpleName());
		
		
		
	}

}
