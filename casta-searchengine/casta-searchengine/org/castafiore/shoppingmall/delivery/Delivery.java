package org.castafiore.shoppingmall.delivery;

import java.math.BigDecimal;

import javax.persistence.Entity;

import org.castafiore.wfs.types.Article;

@Entity
public class Delivery extends Article {

	private BigDecimal price;
	
	private BigDecimal weight;

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	
	

}
