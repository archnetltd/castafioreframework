package org.castafiore.shoppingmall.crm;

import java.math.BigDecimal;

public class OrderLine {
	private String code;
	
	private String title;
	
	private BigDecimal price;
	
	private BigDecimal taxRate;
	
	private BigDecimal qty;
	
	private String options;
	
	private BigDecimal subTotal;
	
	private BigDecimal total;
	
	

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}

	public BigDecimal getQty() {
		return qty;
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}
	
	
	
	
	
	/*
	 * setProductCode(product.getCode());
		setCreditor(p.getProvidedBy());
		setCurrency(currency);
		try{
		setDebitor(Util.getRemoteUser());
		}catch(Exception e){
			
		}
		setTitle(product.getTitle());
		setSummary(p.getSummary());
		setTaxRate(product.getTaxRate());
		BigDecimal taxFraction =	product.getTaxRate().divide(new BigDecimal(100));
		BigDecimal subTotalFraction = new BigDecimal(1).subtract(taxFraction);
		setTotal(product.getTotalPrice().multiply(product.getQty() ));
		BigDecimal subTotal = subTotalFraction.multiply(product.getTotalPrice());
		setSubTotal(subTotal.multiply(product.getQty()) );
		setPrice(product.getTotalPrice());
		setQuantity(product.getQty());
		setWeight(p.getWeight());
		setHeight(p.getHeight());
		setLength(p.getLength());
		setWidth(p.getWidth());
		setOptions(product.getOptions());
	 */

}
