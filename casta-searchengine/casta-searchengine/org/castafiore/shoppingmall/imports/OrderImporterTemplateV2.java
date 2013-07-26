package org.castafiore.shoppingmall.imports;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.format.CellTextFormatter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.castafiore.persistence.Dao;
import org.castafiore.shoppingmall.checkout.Order;
import org.castafiore.spring.SpringUtil;
import org.castafiore.utils.StringUtil;
import org.hibernate.criterion.Restrictions;

public class OrderImporterTemplateV2 extends OrderImporterTemplate{


	@Override
	public Map<String, String> transform(Row r) {
		
		List<String> errors = new ArrayList<String>();
		Map<String,String> data = new HashMap<String, String>();
		
		String cell4 = gc(r,3, "");
		
		String plan = cell4.substring(0, 1).trim();
		
		String other = cell4.substring(1).replace("(", "").replace(")", "").trim();
		String splanType = other.substring(0,1);
		
		String planType = splanType + "Couverture Individuel (" + other.replace("S - ", "").trim() + ")";
		
		if(splanType.equalsIgnoreCase("F")){
			planType = "Couverture familiale";
		}
			
		
		
		if(plan.equalsIgnoreCase("H")){
			plan = "D";
		}
		data.put("plan",plan);
		data.put("plandetail", planType);
		
		data.put("code", gc(r,2,"").replace(" ", ""));
		
		
		String pp = data.get("code").replace("FS", "").trim();
		if(SpringUtil.getBeanOfType(Dao.class).getReadOnlySession().createCriteria(Order.class).add(Restrictions.like("code", "%"+pp+"%")).list().size() >0){
			data.clear();
			data.put("Info ->", "Line " + r.getRowNum() + ":Already created");
			return data;
		}
		
		
		data.put("pointOfSale", "Other");
		data.put("dateOfTransaction", gc(r,1, ""));
		data.put("salesAgent", gc(r,4, ""));
		data.put("dateFormat", "dd.MM.yyyy");
		data.put("productQty", "1");
		data.put("productCode", plan);
		
		data.put("pos", "Other");
		data.put("accountNumber","");
		data.put("bankName", "");
		data.put("email", gc(r,12, ""));
		
		try{
		Calendar date = Calendar.getInstance();
		SimpleDateFormat ff = new SimpleDateFormat(data.get("dateFormat"));
		Date d = ff.parse(data.get("dateOfTransaction"));
		
		date.setTime(d);
		date.add(Calendar.MONTH	,6);
		
		data.put("effDate",ff.format(date.getTime()));
		
		data.put("ptitle","");
		}catch(Exception e){
			errors.add("Error in date format");
		}
		
		String name = gc(r,18, "");
		
		String splitter =","; 
		if(!name.contains(",")){
			splitter= " ";
		}
		String[] nameparts = StringUtil.split(name, splitter);
		if(nameparts.length > 0){
		
			String oname = "";
			if(nameparts.length >1){
				for(int i =1;i < nameparts.length;i++){
					oname = oname + " " + nameparts[i];
				}
			}
			
			data.put("pfullName", oname.trim());
			data.put("pSurname", nameparts[0]);
		}else{
			
			
			errors.add("Error in name : should be in format <SURNAMR>, <OTHER NAMES> e.g ROSSAYE, Abdool Kureem");
		}
		
		data.put("pdob", "");
		data.put("pgender", "");
		data.put("pstatus", "");
		data.put("pidnumber", gc(r,19, ""));
		data.put("presi", gc(r,9,""));
		data.put("presi2", "");
		data.put("ptelhome", gc(r,10,""));
		data.put("pcell", gc(r,11,""));
		
		
		data.put("ctitle","");
		data.put("cfullName", data.get("pfullName"));
		data.put("cSurname", data.get("pSurname"));
		data.put("cdob", "");
		data.put("cgender", "");
		data.put("cstatus", "");
		data.put("cidnumber", data.get("pidnumber"));
		data.put("cresi", data.get("presi"));
		data.put("cresi2", data.get("presi2"));
		data.put("telHome", data.get("ptelhome"));
		data.put("telOffice", "");
		data.put("cell", data.get("pcell"));
		
		if(planType.equalsIgnoreCase("F")){
		
			data.put("stitle", "Ms");
			data.put("sfullName", gc(r,20,""));
			data.put("sSurname", data.get("pSurname"));
			data.put("sdob", "");
			data.put("sgender", "Female");
			data.put("sstatus", "Married");
			data.put("sidnumber", gc(r,21,""));
			
			
			
			data.put("c1surname", gc(r,22,""));
			data.put("c2surname", gc(r,24,""));
			data.put("c3surname", gc(r,26,""));
			data.put("c4surname", gc(r,28,""));
			data.put("c5surname", gc(r,30,""));
			data.put("c6surname", gc(r,32,""));
		}else{
			data.put("stitle", "");
			data.put("sfullName", "");
			data.put("sSurname", "");
			data.put("sdob", "");
			data.put("sgender", "");
			data.put("sstatus", "");
			data.put("sidnumber", "");
			data.put("c1surname", "");
			data.put("c2surname", "");
			data.put("c3surname", "");
			data.put("c4surname", "");
			data.put("c5surname", "");
			data.put("c6surname", "");
		}
		
		
		if(r.getCell(14) != null && (r.getCell(14).getCellType() == Cell.CELL_TYPE_NUMERIC)){
			data.put("default", r.getCell(14).getNumericCellValue() + "");
			data.put("installment", r.getCell(14).getNumericCellValue() + "");
		}else{
			data.put("default",  "0");
			data.put("installment", "0");
		}
		data.put("deposit", "0");
		
		if(r.getCell(13) != null && (r.getCell(13).getCellType() == Cell.CELL_TYPE_NUMERIC)){
			data.put("joining", r.getCell(13).getNumericCellValue() + "");
		}else{
			data.put("joining", "0");
		}
		
		
		if(r.getCell(15) != null && (r.getCell(15).getCellType() == Cell.CELL_TYPE_NUMERIC)){
			data.put("advance", r.getCell(15).getNumericCellValue() + "");
		}else{
			data.put("advance", "0");
		}
		
		//data.put("installment", gc(r,16))
		String pt = gc(r,17, "");
		if(pt.equalsIgnoreCase("B")){
			pt = "Standing Order";
		}else{
			pt = "Cash";
		}
		data.put("paymentMethod", pt);
		
		if(errors.size() > 0){
			data.clear();
			data.put("Error ->", "Line " + r.getRowNum() + ":" + errors.toString());
		}
		
		return data;
	}
	
	
	public static void main(String[] args)throws Exception {
		Workbook wb = new HSSFWorkbook(new FileInputStream(new File("C:\\java\\elie\\fwentriesofnewmember\\veema-april.xls")));
		
		Sheet s = wb.getSheetAt(0);
		
		for(int i=1;i<s.getLastRowNum();i++){
			Row r = s.getRow(i);
			System.out.println(new OrderImporterTemplateV2().transform(r));
		}
	}

}
