package org.castafiore.ui.ex.form;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;

public class EXTimePicker extends EXAutoComplete implements StatefullComponent{

	public EXTimePicker(String name, Date value) {
		super(name, "");
		
		List<String> dict = new ArrayList<String>();
		dict.add(12 + ":00 AM");
		dict.add(12 + ":30 AM");
		for(int i =1; i <= 11;i++){
			if(i < 10){
				dict.add("0" + i + ":00 AM");
				dict.add("0" + i + ":30 AM");
			}else{
				dict.add(i + ":00 AM");
				dict.add(i + ":30 AM");
			}
			
		}
		dict.add(12 + ":00 PM");
		dict.add(12 + ":30 PM");
		
		for(int i =1; i <= 11;i++){
			if(i < 10){
				dict.add("0" + i + ":00 PM");
				dict.add("0" + i + ":30 PM");
			}else{
				dict.add(i + ":00 PM");
				dict.add(i + ":30 PM");
			}
			
		}
		setDictionary(dict);
		if(value != null)
			setValue(value);
	}
	
	public EXTimePicker(String name) {
		this(name,null);
	}

	

	

	@Override
	public void setValue(Object value) {
		if(value instanceof String){
			value = new Date();
		}
		super.setValue(new SimpleDateFormat("hh:mm aa").format((Date)value));	
		setAttribute("time", ((Date)value).getTime() + "");
	}	
	
	
	public Date getValue(){
		try{
		Date date_ = new Date(Long.parseLong(getAttribute("time")));
		String stime = new SimpleDateFormat("dd/MM/yyyy").format(date_.getTime()) + " " + getRawValue();
		Date val = new SimpleDateFormat("dd/MM/yyyy hh:mm aa").parse(stime);
		return val;
		}catch(Exception e){
			throw new UIException(e);
		}
	}

}
