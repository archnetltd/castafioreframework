package org.castafiore.google.docs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.castafiore.shoppingmall.checkout.BillingInformation;

import com.google.gdata.util.ServiceException;
import com.googlecode.htmlcompressor.compressor.HtmlCompressor;
import com.yahoo.platform.yui.compressor.CssCompressor;

public class Main {

	/**
	 * @param args
	 * @throws ServiceException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws Exception{

		//workoutallimports();
		//return b.toString();
		
		//extractduplicates();
	//	generatedeletescript();
		generateagain();
		
	}
	
	public static void generateagain()throws Exception{
		Sheet export = new HSSFWorkbook(new FileInputStream(new File("c:\\java\\elie\\analysis\\imported\\clients.xls"))).getSheetAt(0);
		Sheet system = new HSSFWorkbook(new FileInputStream(new File("c:\\java\\elie\\analysis\\code_path.xls"))).getSheetAt(0);
		StringBuilder b = new StringBuilder();
		for(int i =0; i < system.getLastRowNum();i++){
			try{
			String code = system.getRow(i).getCell(0).getStringCellValue();
			
			String path = system.getRow(i).getCell(1).getStringCellValue();
			
			String plan = getPlan(code, export);
			if(plan != null){
				//data.put("plan", plan);
				//String nv = BillingInformation.buildString(data);
				String sql = "update WFS_FILE set status=" + 80 + " where absolutepath like'" + path + "%' and name='applicationForm'";
				b.append(sql).append(";\n");
			}
			}catch(Exception e){
				System.out.println("Line : " + i + " " + e.getMessage());
			}
		}
		
		FileOutputStream fout = new FileOutputStream(new File("c:\\java\\elie\\analysis\\todelete.sql"));
		fout.write(b.toString().getBytes());
		fout.flush();
		fout.close();
	}
	
	public static void updateChildren()throws Exception{
		Sheet system = new HSSFWorkbook(new FileInputStream(new File("c:\\java\\elie\\analysis\\code_path.xls"))).getSheetAt(0);
		Sheet export = new HSSFWorkbook(new FileInputStream(new File("c:\\java\\elie\\analysis\\imported\\clients.xls"))).getSheetAt(0);
		StringBuilder b = new StringBuilder();
		for(int i =0; i < system.getLastRowNum();i++){
			try{
			String code = system.getRow(i).getCell(0).getStringCellValue();
			
			String path = system.getRow(i).getCell(1).getStringCellValue();
			//Map<String, String> data = BillingInformation.buildMap(value);
			//String code = data.get("fsNumber");
			String plan = getPlan(code, export);
			if(plan != null){
				//data.put("plan", plan);
				//String nv = BillingInformation.buildString(data);
				String sql = "update WFS_FILE set title='" + plan + "' where absolutepath like'" + path + "%' and dtype='SalesOrderEntry'";
				b.append(sql).append(";\n");
			}
			}catch(Exception e){
				System.out.println("Line : " + i + " " + e.getMessage());
			}
		}
		
		FileOutputStream fout = new FileOutputStream(new File("c:\\java\\elie\\analysis\\todelete.sql"));
		fout.write(b.toString().getBytes());
		fout.flush();
		fout.close();
	}
	
	public static void updatePlans()throws Exception{
		Sheet system = new HSSFWorkbook(new FileInputStream(new File("c:\\java\\elie\\analysis\\code_path.xls"))).getSheetAt(0);
		Sheet export = new HSSFWorkbook(new FileInputStream(new File("c:\\java\\elie\\analysis\\imported\\clients.xls"))).getSheetAt(0);
		StringBuilder b = new StringBuilder();
		for(int i =0; i < system.getLastRowNum();i++){
			try{
			String code = system.getRow(i).getCell(0).getStringCellValue();
			
			String path = system.getRow(i).getCell(1).getStringCellValue();
			//Map<String, String> data = BillingInformation.buildMap(value);
			//String code = data.get("fsNumber");
			String plan = getPlan(code, export);
			if(plan != null){
				//data.put("plan", plan);
				//String nv = BillingInformation.buildString(data);
				String sql = "update WFS_FILE set title='" + plan + "' where absolutepath like'" + path + "%' and dtype='SalesOrderEntry'";
				b.append(sql).append(";\n");
			}
			}catch(Exception e){
				System.out.println("Line : " + i + " " + e.getMessage());
			}
		}
		
		FileOutputStream fout = new FileOutputStream(new File("c:\\java\\elie\\analysis\\todelete.sql"));
		fout.write(b.toString().getBytes());
		fout.flush();
		fout.close();
	}
	
	
	
	
//	public static void generateSQLSforOrder(String fsNumber,
//			Calendar transDate,
//			String title, 
//			String firstName, 
//			String lastName,
//			String address1,
//			String address2,
//			String address3,
//			String plan,
//			String planDetail,
//			
//	
//			
//	)
	
	
	
	
	private static String getPlan(String code, Sheet s){
		for(int i =0; i < s.getLastRowNum();i++){
			try{
				String c = s.getRow(i).getCell(0).getStringCellValue();
				if(code.equalsIgnoreCase(c)){
					String plan = s.getRow(i).getCell(12).getStringCellValue();
					String detail = s.getRow(i).getCell(13).getStringCellValue();
					
					return "Plan " + plan + " " + detail;
				}
			}catch(Exception e){
				
			}
		}
		
		return null;
	}
	
	
	public static void generatedeletescript()throws Exception{
		HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(new File("c:\\java\\elie\\analysis\\codes_letest.xls")));
		Sheet s = wb.getSheetAt(1);
		StringBuilder b = new StringBuilder();
		for(int i =0; i < s.getLastRowNum(); i++){
			String path = s.getRow(i).getCell(0).getStringCellValue();
			b.append("delete from WFS_FILE where absolutepath like '"+path+"%';\n");
		}
		
		FileOutputStream fout = new FileOutputStream(new File("c:\\java\\elie\\analysis\\todelete.sql"));
		fout.write(b.toString().getBytes());
		fout.flush();
		fout.close();
		
	
	}
	
	public static void extractduplicates()throws Exception{
		HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(new File("c:\\java\\elie\\analysis\\duplicates.xls")));
		Sheet s = wb.getSheetAt(0);
		
		List<String> todelete = new ArrayList<String>();
		List<String> tmp = new ArrayList<String>();
		for(int i =0; i < s.getLastRowNum(); i++){
			String code = s.getRow(i).getCell(0).getStringCellValue();
			String path = s.getRow(i).getCell(1).getStringCellValue();
			if(tmp.contains(code)){
				todelete.add(path);
			}else{
				tmp.add(code);
			}
		}
		
		
		HSSFWorkbook output = new HSSFWorkbook();
		HSSFSheet sheet = output.createSheet();
		int i = 0;
		for(String path : todelete){
			Row r = sheet.createRow(i);
			i++;
			r.createCell(0).setCellValue(path);
		}
		
		FileOutputStream fout = new FileOutputStream(new File("c:\\java\\elie\\analysis\\imported\\todelete.xls"));
		output.write(fout );
		fout.flush();
		fout.close();
	}
	
	
	public static void workoutallimports()throws Exception{
		List<String[]> codes = new ArrayList<String[]>();
		
		
		for(File f : new File("c:\\java\\elie\\analysis\\imported").listFiles()){
			HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(f));
			for(int i =0; i < wb.getNumberOfSheets();i++){
				Sheet s = wb.getSheetAt(i);
				int col = getfsCol(s);
				
				System.out.println(f.getName() + "|" + s.getSheetName() + "|" + col);
				
				
				for(int j =1; j < s.getLastRowNum();j++){
					try{
						String code = s.getRow(j).getCell(col).getStringCellValue();
						boolean has = false;
						for(String[] ass : codes){
							if(ass[2] != null && ass[2].equals(code)){
								has = true;
								break;
							}
								
						}
						if(!has){
							codes.add(new String[]{f.getName(), s.getSheetName(), code});
						}
					}catch(Exception e){
						
					}
					
				}
			}
		
			
		}
		
		
		HSSFWorkbook out = new HSSFWorkbook();
		Sheet s = out.createSheet();
		int index = 0;
		for(String[] ss : codes){
			Row r = s.createRow(index);
			index++;
			r.createCell(0).setCellValue(ss[0]);
			r.createCell(1).setCellValue(ss[1]);
			r.createCell(2).setCellValue(ss[2]);
		}
		FileOutputStream fout = new FileOutputStream(new File("c:\\java\\elie\\analysis\\imported\\output.xls"));
		out.write(fout );
		fout.flush();
		fout.close();
	}
	
	
	public static int getfsCol(Sheet sheet){
		try{
			
			
			for(int row = 1; row < sheet.getLastRowNum();row++){
				Row r = sheet.getRow(row);
				
				for(int i =0; i < r.getLastCellNum();i++){
					try{
						String s = r.getCell(i).getStringCellValue();
						if(s.toLowerCase().startsWith("fs")){
							return i;
						}
					}catch(Exception e){
						
					}
				}
			}
		}catch(Exception e){
			return -1;
		}
		
		return -1;
	}

}
