package org.castafiore.shoppingmall.checkout;

import java.util.List;

import javax.persistence.Entity;

@Entity
public class Payment extends BookEntry<OrderEntry>{

	
	public Payment(){
		super();
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public List<OrderEntry> getDetails() {
		return null;
	}

	
	 
	 
	 
	 

}
