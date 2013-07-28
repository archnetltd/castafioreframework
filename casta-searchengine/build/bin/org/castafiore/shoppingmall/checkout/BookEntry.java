package org.castafiore.shoppingmall.checkout;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;

import org.castafiore.wfs.types.Article;
import org.hibernate.annotations.Type;

@Entity()
public abstract class BookEntry<T extends Article> extends Article {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String code;

	//who got money (Username)
	@Type(type="text")
	private String debitor;
	
	//who paid money (Username)
	@Type(type="text")
	private String creditor;
	
	// percentage
	private BigDecimal taxRate;
	
	private BigDecimal subTotal;

	private BigDecimal total;
	
	@Type(type="text")
	private String paymentMethod;
	
	@Type(type="text")
	private String chequeNo;
	
	@Type(type="text")
	private String pointOfSale;
	
	
	
	
	public String getPointOfSale() {
		return pointOfSale;
	}

	public void setPointOfSale(String pointOfSale) {
		this.pointOfSale = pointOfSale;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getChequeNo() {
		return chequeNo;
	}

	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}

	public BookEntry(){
		super();
	}
	
	public String getCode(){
		return code;
	}

	public String getDebitor() {
		return debitor;
	}

	public void setDebitor(String debitor) {
		this.debitor = debitor;
	}

	public String getCreditor() {
		return creditor;
	}

	public void setCreditor(String creditor) {
		this.creditor = creditor;
	}

	public BigDecimal getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}

	

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

	public abstract List<T> getDetails();
	
}
