package org.castafiore.finance;

import org.castafiore.wfs.types.Article;

public class ExpenseType extends Article {
	
	public ExpenseType (){
		super();
	}
	
	private boolean vatType = false;

	public boolean isVatType() {
		return vatType;
	}

	public void setVatType(boolean vatType) {
		this.vatType = vatType;
	}
	

}
