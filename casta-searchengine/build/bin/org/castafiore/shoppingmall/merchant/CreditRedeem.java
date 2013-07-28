package org.castafiore.shoppingmall.merchant;

import java.math.BigDecimal;

import javax.persistence.Entity;

import org.castafiore.wfs.types.Article;
import org.hibernate.annotations.Type;

@Entity
public class CreditRedeem extends Article{
	
	public final static int STATUS_REDEEMED = 40;
	
	@Type(type="text")
	private String merchant;
	
	private BigDecimal amount;
	
	@Type(type="text")
	private String bankAccountName;
	
	@Type(type="text")
	private String redeemedBy;
	

	public String getMerchant() {
		return merchant;
	}

	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	public String getRedeemedBy() {
		return redeemedBy;
	}

	public void setRedeemedBy(String redeemedBy) {
		this.redeemedBy = redeemedBy;
	}
	
	

}
