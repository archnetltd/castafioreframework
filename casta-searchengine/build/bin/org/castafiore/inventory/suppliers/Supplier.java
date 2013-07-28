package org.castafiore.inventory.suppliers;

import javax.persistence.Entity;

import org.castafiore.security.User;
@Entity
public class Supplier extends User{
	
	private String code;
	
	private String vatReg;
	
	private String merchantUsername;


	public String getVatReg() {
		return vatReg;
	}

	public void setVatReg(String vatReg) {
		this.vatReg = vatReg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMerchantUsername() {
		return merchantUsername;
	}

	public void setMerchantUsername(String merchantUsername) {
		this.merchantUsername = merchantUsername;
	}

	
	
	

}
