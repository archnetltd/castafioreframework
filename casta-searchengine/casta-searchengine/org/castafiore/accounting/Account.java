package org.castafiore.accounting;

import java.math.BigDecimal;

import javax.persistence.Entity;

import org.castafiore.wfs.types.Article;
import org.hibernate.annotations.Type;

@Entity
public class Account extends Article{
	
	@Type(type="text")
	private String accType;
	
	private String code;
	
	@Type(type="text")
	private String category;
	
	@Type(type="text")
	private String category_1;

	@Type(type="text")
	private String category_2;
	
	@Type(type="text")
	private String category_3;
	
	@Type(type="text")
	private String category_4;
	
	private BigDecimal defaultValue = BigDecimal.ZERO;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAccType() {
		return accType;
	}

	public void setAccType(String accType) {
		this.accType = accType;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCategory_1() {
		return category_1;
	}

	public void setCategory_1(String category_1) {
		this.category_1 = category_1;
	}

	public String getCategory_2() {
		return category_2;
	}

	public void setCategory_2(String category_2) {
		this.category_2 = category_2;
	}

	public String getCategory_3() {
		return category_3;
	}

	public void setCategory_3(String category_3) {
		this.category_3 = category_3;
	}

	public String getCategory_4() {
		return category_4;
	}

	public void setCategory_4(String category_4) {
		this.category_4 = category_4;
	}

	public BigDecimal getDefaultValue() {
		if(defaultValue == null){
			defaultValue = BigDecimal.ZERO;
		}
		return defaultValue;
	}

	public void setDefaultValue(BigDecimal defaultValue) {
		this.defaultValue = defaultValue;
	}

	
	

}
