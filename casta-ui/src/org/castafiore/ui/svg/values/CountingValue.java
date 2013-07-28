package org.castafiore.ui.svg.values;

import java.math.BigDecimal;

import org.castafiore.ui.UIException;

public class CountingValue {
	
	private String val = "1";
	
	public void setIndefinit(){
		val = "indefinite";
	}
	
	public boolean isIndefinit(){
		return "indefinite".equals(val);
	}
	
	public void setNumber(BigDecimal number){
		val = number.toPlainString();
	}
	
	
	public BigDecimal getNumber(){
		if(isIndefinit()){
			throw new UIException("The couting attribute is indefinite");
		}else{
			return new BigDecimal(val);
		}
	}
	
	
	
	@Override
	public String toString() {
		return val;
	}

	public static CountingValue create(int number){
		CountingValue c = new CountingValue();
		c.setNumber(new BigDecimal(number));
		return c;
	}
	public static CountingValue create(String s){
		CountingValue c = new CountingValue();
		try{
		c.setNumber(new BigDecimal(s));
		}catch(Exception e){
			c.setIndefinit();
		}
		return c;
	}
	
	public static CountingValue createIndefinite(){
		CountingValue c = new CountingValue();
		c.setIndefinit();
		return c;
	}

}
