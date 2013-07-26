package org.castafiore.shoppingmall.employee.ui.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class PointerUser {
	
	private String name;
	
	private String id;
	
	private List<PointerEntry> entries = new ArrayList<PointerEntry>();

	public PointerUser(String id, String name) {
		super();
		this.name = name;
		this.id = id;
	}
	
	public void addPointerEntry(PointerEntry entry){
		entries.add(entry);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	public void fillRow(Row r){
		int start = 2;
		for(PointerEntry ent : entries){
			float hrs = getHours(ent.getStart(),ent.getEnd());
			
			r.createCell(start).setCellValue(new SimpleDateFormat("HH:mm").format(ent.getStart().getTime()));
			start++;
			r.createCell(start).setCellValue(new SimpleDateFormat("HH:mm").format(ent.getEnd().getTime()));
			start++;
			//r.createCell(start).setCellValue(hrs);
			
			Cell c =r.createCell(start);
			c.setCellValue(hrs);
				
			if(hrs <= 0){
				HSSFCellStyle st =(HSSFCellStyle)r.getSheet().getWorkbook().createCellStyle();
				st.setFillBackgroundColor(HSSFColor.RED.index);
				c.setCellStyle(st);
				
			}
			start++;
			
			if(hrs == 0f)
				r.createCell(start).setCellValue(0);
			else
				r.createCell(start).setCellValue(1);
			
			start++;
			
			//transport
		}
	}
	
	private float getHours(Calendar start, Calendar end){
		float entryTime = ParamUtil.getEntryTime();
		
		float sh = Float.parseFloat(new SimpleDateFormat("HH").format(start.getTime()));
		float eh = Float.parseFloat(new SimpleDateFormat("HH").format(end.getTime()));
		
		float sm = Float.parseFloat(new SimpleDateFormat("mm").format(start.getTime()))/60;
		float em = Float.parseFloat(new SimpleDateFormat("mm").format(end.getTime()))/60;
		
		sh = sh + sm;
		eh = eh + em;
		
		
		if(sh < entryTime  ){
			sh = entryTime;
		}
		
		
		float pause = 1;
		
		if(sh >= 13.8333f){
			pause = 2/3;
		}
		if(sh >= 15.333333){
			pause = 1/3;
		}
		if(sh > 19.333333){
			pause = 0;
		}
		
		
		if(eh < 19){
			pause = pause-(1/3);
		}
		if(eh < 15){
			pause = pause-(1/3);
		}
		
		if(eh < 13 || (eh==13&&em<30)){
			pause = pause-(1/3);
		}
		
		
		
		float res = eh - sh -pause;
		
		if( res <0.5){
			res = 0;
		}
		return res ;
	}
	
	
}
