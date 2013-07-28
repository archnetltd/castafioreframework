package com.eliensons.ui;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.castafiore.persistence.Dao;
import org.castafiore.spring.SpringUtil;
import org.castafiore.utils.IOUtil;
import org.castafiore.utils.StringUtil;
import org.hibernate.Session;

public class DD {
	
	public static void analyse()throws Exception{
		Workbook wb = new HSSFWorkbook(new FileInputStream("/usr/local/software/clients.xls"));
		Dao dao = SpringUtil.getBeanOfType(Dao.class);
		Session session = dao.getReadOnlySession();
		Sheet s = wb.getSheetAt(0);
		FileOutputStream in = new FileOutputStream("/usr/local/software/importlog.txt",true);
		
		for(int i =  0; i < s.getLastRowNum();i++){
			String code = s.getRow(i).getCell(0).getStringCellValue();
			List res = session.createSQLQuery("select code from WFS_FILE where dtype='Order' and code='"+code+"'").list();
			if(res.size() == 0){
				String log =(code + " Not created");
				in.write(log.getBytes());
			}else if(res.size() > 1){
				String log =(code + " Duplicate");
				in.write(log.getBytes());
			}
		}
		
		in.flush();
		in.close();
	}
	
	public static void work()throws Exception{
		
		Workbook wb = new HSSFWorkbook(new FileInputStream("C:\\java\\elie\\clients.xls"));
		Sheet sheet = wb.getSheetAt(0);
		Workbook result = new HSSFWorkbook();
		Sheet sheetResult = result.createSheet();
		
		String s = new String( IOUtil.getFileContentAsBytes("C:\\java\\elie\\importlog.txt"));
		
		s = s.replace("Not created", ">NC:").replace("Duplicate", ">D:");
		
		String[] as = StringUtil.split(s, ">");
		int rowcreated = 1;
		for(String a : as){
			if(a.startsWith("NC")){
				String fs =a.replace("NC:", "").trim();
				Row row = getRow(fs, sheet);
				
			
				if(row != null){
					Row rresult = sheetResult.createRow(rowcreated);
					rowcreated++;
					copyRow(row, rresult);
				}
				System.out.println(fs);
			}
		}
		FileOutputStream fout = new FileOutputStream("C:\\java\\elie\\notcreated.xls");
		result.write(new FileOutputStream("C:\\java\\elie\\notcreated.xls"));
		fout.flush();
		fout.close();
		//System.out.println(s);
	}
	
	private static void copyRow(Row r, Row result){
		for(int i = 0; i < r.getLastCellNum();i++){
			Cell c = result.createCell(i);
			Cell rcell = r.getCell(i);
			if(rcell.getCellType() == Cell.CELL_TYPE_NUMERIC)
				c.setCellValue(rcell.getNumericCellValue());
			else if(rcell.getCellType() == Cell.CELL_TYPE_STRING)
				c.setCellValue(rcell.getStringCellValue());
		}
	}
	
	private  static Row getRow(String fs, Sheet s){
		for(int i = 0; i < s.getLastRowNum();i++){
			HSSFRow r = (HSSFRow)s.getRow(i);
			if(fs.equalsIgnoreCase(r.getCell(0).getStringCellValue())){
				return r;
			}
		}
		return null;
	}
	
	public static void main(String[] args)throws Exception {
		work();
	}
	

}
