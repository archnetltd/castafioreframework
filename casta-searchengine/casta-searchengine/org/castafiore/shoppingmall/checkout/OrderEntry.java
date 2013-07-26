package org.castafiore.shoppingmall.checkout;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;

import org.castafiore.catalogue.Product;
import org.castafiore.designable.CartItem;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.merchant.FinanceUtil;
import org.castafiore.wfs.Util;
import org.hibernate.annotations.Type;
@Entity
public class OrderEntry extends BookEntry<Product> {
	
	private BigDecimal quantity = BigDecimal.ZERO;
	
	
	private String productCode;
	
	private BigDecimal price;
	
	private BigDecimal weight;
	
	private BigDecimal length;
	
	private BigDecimal width;
	
	private BigDecimal height;
	
	private String currency = "MUR";
	
	@Type(type="text")
	private String options;
	
	
	
	
	
	public String getOptions() {
		return options;
	}



	public void setOptions(String options) {
		this.options = options;
	}



	public String getCurrency() {
		if(currency == null){
			currency = "MUR";
		}
		return currency;
	}



	public void setCurrency(String currency) {
		this.currency = currency;
	}



	public OrderEntry(){
		super();
	}
	
	

	public BigDecimal getWeight() {
		return weight;
	}



	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}



	public BigDecimal getLength() {
		return length;
	}



	public void setLength(BigDecimal length) {
		this.length = length;
	}



	public BigDecimal getWidth() {
		return width;
	}



	public void setWidth(BigDecimal width) {
		this.width = width;
	}



	public BigDecimal getHeight() {
		return height;
	}



	public void setHeight(BigDecimal height) {
		this.height = height;
	}



	@Override
	public List<Product> getDetails() {
		throw new UnsupportedOperationException("this is not supported for performance issues");
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public  void setProduct(CartItem product, Product p){
		setProductCode(product.getCode());
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
		
	}
	
	public  void setProduct( Product product, BigDecimal qty, String options){
		setProductCode(product.getCode());
		setCreditor(product.getProvidedBy());
		setCurrency(currency);
		try{
		setDebitor(Util.getRemoteUser());
		}catch(Exception e){
			
		}
		setTitle(product.getTitle());
		setSummary(product.getSummary());
		setTaxRate(product.getTaxRate());
		BigDecimal taxFraction =	product.getTaxRate().divide(new BigDecimal(100));
		BigDecimal subTotalFraction = new BigDecimal(1).subtract(taxFraction);
		setTotal(product.getTotalPrice().multiply(qty ));
		BigDecimal subTotal = subTotalFraction.multiply(product.getTotalPrice());
		setSubTotal(subTotal.multiply(qty) );
		setPrice(product.getTotalPrice());
		setQuantity(qty);
		setWeight(product.getWeight());
		setHeight(product.getHeight());
		setLength(product.getLength());
		setWidth(product.getWidth());
		setOptions(options);
		
	}
	
}
