package org.castafiore.wfs.types;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.Entity;

import org.castafiore.Types;
import org.hibernate.annotations.Type;

@Entity
public class Value extends Directory {
	
	public Value(){
		super();
	}
	
	private int type = Types.STRING.ordinal();
	
	@Type(type="text")
	private String value;
	
	
	
	public void setString(String value){
		type = Types.STRING.ordinal();
		this.value = value;
	}
	
	public void setDate(Calendar date){
		type = Types.DATE.ordinal();
		value = date.getTime().getTime() + "";
	}
	
	public void setLong(Long val){
		type = Types.LONG.ordinal();
		value = val.toString();
	}
	
	
	public void setDouble(Double val){
		type = Types.DOUBLE.ordinal();
		value = val.toString();
	}
	
	
	public void setBoolean(Boolean val){
		type = Types.BOOLEAN.ordinal();
		value = val.toString();
	}
	
	public String getString(){
		return value;
	}
	
	
	public Calendar getDate(){
		Calendar cal = new GregorianCalendar();
		cal.setTime(new Date(Long.parseLong(value)));
		return cal;
	}
	
	
	public Long getLong(){
		return Long.parseLong(value);
	}
	
	public Double getDouble(){
		return Double.parseDouble(value);
	}
	
	public boolean geBoolean(){
		return Boolean.parseBoolean(value);
	}
	
	
	public Types getType(){
		for(Types val : Types.values()){
			if(val.ordinal() == type){
				return val;
			}
		}
		
		return null;
	}
	

}
