package org.castafiore.swing.orders;

import org.openswing.swing.message.receive.java.ValueObjectImpl;

public class FSCodeVO extends ValueObjectImpl{
	
	private String fsCode;
	
	private String customer;

	public String getFsCode() {
		return fsCode;
	}

	public void setFsCode(String fsCode) {
		this.fsCode = fsCode;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}
	
	

}
