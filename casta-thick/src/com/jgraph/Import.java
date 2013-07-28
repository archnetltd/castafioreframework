package com.jgraph;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.castafiore.utils.StringUtil;

import sun.security.krb5.PrincipalName;

public class Import {

	
	
	//private static Sheet sheet;
	
	
	public static double getNumericCellValue(Cell c){
		if(c == null){
			return 0;
		}
		if(c.getCellType() == Cell.CELL_TYPE_BLANK){
			return 0;
		}else if(c.getCellType() == Cell.CELL_TYPE_BOOLEAN){
			return c.getBooleanCellValue()?1:0;
		}else if(c.getCellType() == Cell.CELL_TYPE_NUMERIC){
			return c.getNumericCellValue();
		}else{
			try{
				return Double.parseDouble(c.getStringCellValue());
			}catch(Exception e){
				return 0;
			}
		}
	}
	
	public static void createRowExport(Row row, int ids, Sheet sheet, File source, List<String> fsss){
		if(row == null){
			//System.out.println(row.getSheet().getWorkbook().)
			return;
		}
		
		String fsNumber = getStringCellValue(row.getCell(6));
		if(fsss.contains(fsNumber)){
			return;
		}
		
//		System.out.println(id);
//		if(id > 65535){
//			System.err.println(id);
//		}
			
		
		Row n = sheet.createRow(sheet.getLastRowNum() +1);
		//String dateTransaction = getStringCellValue(row.getCell(13));
		Date cal = row.getCell(0).getDateCellValue();
		//dateTransaction = dateTransaction.trim().replace("-", ".").replace(",", ".").replace("/", ".");
		
		
		String plan = getStringCellValue(row.getCell(7));
		String planDetail = getStringCellValue(row.getCell(7));
		String salesAgent = getStringCellValue(row.getCell(3));
		String contactName = getStringCellValue(row.getCell(9));
		String contactLastName =getStringCellValue(row.getCell(10));
		String contactAddress1 = getStringCellValue(row.getCell(13));
		String contactAddress2 = getStringCellValue(row.getCell(14));
		
		String contactTel = getStringCellValue(row.getCell(15));
		String contactMobile = getStringCellValue(row.getCell(17));
		String principAddress1 = contactAddress1;//getStringCellValue(row.getCell(8));
		String principAddress2 = contactAddress2;
		
		String principalTel = getStringCellValue(row.getCell(27));
		String principalMobile =getStringCellValue(row.getCell(28));
		String principalEmail = "";//getStringCellValue(row.getCell(11));
		
		double joiningFee = 0;//getNumericCellValue(row.getCell(18));
		double installment = getNumericCellValue(row.getCell(48));
		double advancePayment = 0;//getNumericCellValue(row.getCell(19));
		double total = getNumericCellValue(row.getCell(42));
		String paymentMethod = "Standing Order";
		
		String spouseName = getStringCellValue(row.getCell(30));
		String spouseId = getStringCellValue(row.getCell(35));
		
		String member1 = getStringCellValue(row.getCell(49));
		String member1id =getStringCellValue(row.getCell(50));
		
		String member2 = getStringCellValue(row.getCell(52));
		String member2id = getStringCellValue(row.getCell(53));
		
		String member3 = getStringCellValue(row.getCell(54));
		String member3id = getStringCellValue(row.getCell(56));
		
		String member4 = getStringCellValue(row.getCell(58));
		String member4id = getStringCellValue(row.getCell(59));
		
		String member5 = getStringCellValue(row.getCell(61));
		String member5id = getStringCellValue(row.getCell(62));
		
		String member6 = getStringCellValue(row.getCell(64));
		String member6id =getStringCellValue(row.getCell(65));
		
		n.createCell(0).setCellValue(n.getRowNum());
		
		//Calendar cal = Calendar.getInstance();
		//Date cal = new Date();
		try{
			//cal = new SimpleDateFormat("dd-MMM-yy").parse(dateTransaction);
//			String[] parts =	StringUtil.split(dateTransaction, ".");
//			cal.set(Calendar.YEAR, Integer.parseInt(parts[2]) + 2000);
//			cal.set(Calendar.MONTH, Integer.parseInt(parts[1])-1);
//			cal.set(Calendar.DATE, Integer.parseInt(parts[0]));
			n.createCell(1).setCellValue(cal);
			n.createCell(2).setCellValue(cal);
			n.createCell(34).setCellValue(cal);
			n.createCell(10).setCellValue(cal);
			n.createCell(36).setCellValue(getArrear(cal, total));
		}catch(Exception e){
			//System.out.println("Transaction Date : -" + dateTransaction + "-:Row:" +row.getRowNum() + ";Sheet:" + row.getSheet().getSheetName() + ";File:" + source.getName() );
		}
		n.createCell(3).setCellValue("Other");
		n.createCell(4).setCellValue(salesAgent);
		n.createCell(5).setCellValue(fsNumber);
		int pay =0;
		if(paymentMethod == null || paymentMethod.length() <=0){
			pay = 0;
		}else if(paymentMethod.equalsIgnoreCase("Installment")){
			pay = 0;
		}else if(paymentMethod.equalsIgnoreCase("standing order")){
			pay = 1;
		}else{
			pay = 2;
		}
		
		n.createCell(6).setCellValue(pay);
		n.createCell(7).setCellValue(" ");
		n.createCell(8).setCellValue(" ");
		n.createCell(9).setCellValue("Other");
		
		n.createCell(11).setCellValue(installment);
		n.createCell(12).setCellValue(joiningFee);
		n.createCell(13).setCellValue(total);
		n.createCell(14).setCellValue(plan);
		n.createCell(15).setCellValue(planDetail);
		n.createCell(16).setCellValue(contactName);
		n.createCell(17).setCellValue(contactLastName);
		n.createCell(18).setCellValue(contactAddress1);
		n.createCell(19).setCellValue(contactAddress2);
		n.createCell(20).setCellValue(contactTel);
		n.createCell(21).setCellValue(contactMobile);
		n.createCell(22).setCellValue(" ");
		n.createCell(23).setCellValue(" ");
		n.createCell(24).setCellValue(principalEmail);
		n.createCell(25).setCellValue(contactName);
		n.createCell(26).setCellValue(contactLastName);
		n.createCell(27).setCellValue(choose(principAddress1,contactAddress1));
		n.createCell(28).setCellValue(principAddress2);
		n.createCell(29).setCellValue(choose(principalMobile,contactMobile));
		n.createCell(30).setCellValue(choose(principalTel,contactTel));
		n.createCell(31).setCellValue(choose(principalEmail,""));
		n.createCell(32).setCellValue(" ");
		n.createCell(33).setCellValue(1);
		
		n.createCell(35).setCellValue(total);
		
		n.createCell(37).setCellValue(11);
		n.createCell(38).setCellValue(0);
		n.createCell(39).setCellValue(new Date(System.currentTimeMillis() + 5000000));
		n.createCell(40).setCellValue(new Date(System.currentTimeMillis() + 5000000));
		n.createCell(41).setCellValue(new Date(System.currentTimeMillis() + 5000000));
		n.createCell(42).setCellValue(" ");
		n.createCell(43).setCellValue(spouseName);
		n.createCell(44).setCellValue(new Date(System.currentTimeMillis() + 5000000));
		n.createCell(45).setCellValue(new Date());
		n.createCell(46).setCellValue(" ");
		n.createCell(47).setCellValue(spouseId);
		n.createCell(48).setCellValue(" ");
		n.createCell(49).setCellValue(" ");
		n.createCell(50).setCellValue(member1);
		n.createCell(51).setCellValue(member1id);
		n.createCell(52).setCellValue(1);
		
		n.createCell(53).setCellValue(member2);
		n.createCell(54).setCellValue(member2id);
		n.createCell(55).setCellValue(1);
		
		n.createCell(56).setCellValue(member3);
		n.createCell(57).setCellValue(member3id);
		n.createCell(58).setCellValue(1);
		
		n.createCell(59).setCellValue(member4);
		n.createCell(60).setCellValue(member4id);
		n.createCell(61).setCellValue(1);
		
		n.createCell(62).setCellValue(member5);
		n.createCell(63).setCellValue(member5id);
		n.createCell(64).setCellValue(1);
	}
	
	public static void createRowFox(Row row, int ids, Sheet sheet, File source){
		if(row == null){
			//System.out.println(row.getSheet().getWorkbook().)
			return;
		}
//		System.out.println(id);
//		if(id > 65535){
//			System.err.println(id);
//		}
			
		
		Row n = sheet.createRow(sheet.getLastRowNum() +1);
		String dateTransaction = getStringCellValue(row.getCell(13));
		//dateTransaction = dateTransaction.trim().replace("-", ".").replace(",", ".").replace("/", ".");
		
		String fsNumber = getStringCellValue(row.getCell(0));
		String plan = getStringCellValue(row.getCell(14));
		String planDetail = getStringCellValue(row.getCell(15));
		String salesAgent = "Other";//row.getCell(3)==null?"":getStringCellValue(row.getCell(3));
		String contactName = getStringCellValue(row.getCell(1));
		String contactLastName =getStringCellValue(row.getCell(2));
		String contactAddress1 = getStringCellValue(row.getCell(5));
		String contactAddress2 = getStringCellValue(row.getCell(6));
		
		String contactTel = getStringCellValue(row.getCell(8));
		String contactMobile = getStringCellValue(row.getCell(8));
		String principAddress1 = contactAddress1;//getStringCellValue(row.getCell(8));
		String principAddress2 = contactAddress2;
		
		String principalTel = contactTel;//getStringCellValue(row.getCell(9));
		String principalMobile =contactMobile;getStringCellValue(row.getCell(10));
		String principalEmail = "";//getStringCellValue(row.getCell(11));
		
		double joiningFee = getNumericCellValue(row.getCell(18));
		double installment = getNumericCellValue(row.getCell(16));
		double advancePayment = getNumericCellValue(row.getCell(19));
		double total = 0;//getNumericCellValue(row.getCell(15));
		String paymentMethod = row.getCell(20)==null?"Standing Order":getStringCellValue(row.getCell(16));
		
		String spouseName = getStringCellValue(row.getCell(28));
		String spouseId = " ";//getStringCellValue(row.getCell(18));
		
		String member1 = getStringCellValue(row.getCell(29));
		String member1id =" ";// getStringCellValue(row.getCell(20));
		
		String member2 = getStringCellValue(row.getCell(30));
		String member2id = " ";//getStringCellValue(row.getCell(22));
		
		String member3 = getStringCellValue(row.getCell(31));
		String member3id = " ";//getStringCellValue(row.getCell(24));
		
		String member4 = getStringCellValue(row.getCell(32));
		String member4id = " ";//getStringCellValue(row.getCell(26));
		
		String member5 = getStringCellValue(row.getCell(33));
		String member5id = " ";//getStringCellValue(row.getCell(28));
		
		String member6 = getStringCellValue(row.getCell(34));
		String member6id = " ";//getStringCellValue(row.getCell(30));
		
		n.createCell(0).setCellValue(n.getRowNum());
		
		//Calendar cal = Calendar.getInstance();
		Date cal = new Date();
		try{
			cal = new SimpleDateFormat("dd-MMM-yy").parse(dateTransaction);
//			String[] parts =	StringUtil.split(dateTransaction, ".");
//			cal.set(Calendar.YEAR, Integer.parseInt(parts[2]) + 2000);
//			cal.set(Calendar.MONTH, Integer.parseInt(parts[1])-1);
//			cal.set(Calendar.DATE, Integer.parseInt(parts[0]));
			n.createCell(1).setCellValue(cal);
			n.createCell(2).setCellValue(cal);
			n.createCell(34).setCellValue(cal);
			n.createCell(10).setCellValue(cal);
			n.createCell(36).setCellValue(getArrear(cal, total));
		}catch(Exception e){
			System.out.println("Transaction Date : -" + dateTransaction + "-:Row:" +row.getRowNum() + ";Sheet:" + row.getSheet().getSheetName() + ";File:" + source.getName() );
		}
		n.createCell(3).setCellValue("Other");
		n.createCell(4).setCellValue(salesAgent);
		n.createCell(5).setCellValue(fsNumber);
		int pay =0;
		if(paymentMethod == null || paymentMethod.length() <=0){
			pay = 0;
		}else if(paymentMethod.equalsIgnoreCase("Installment")){
			pay = 0;
		}else if(paymentMethod.equalsIgnoreCase("standing order")){
			pay = 1;
		}else{
			pay = 2;
		}
		
		n.createCell(6).setCellValue(pay);
		n.createCell(7).setCellValue(" ");
		n.createCell(8).setCellValue(" ");
		n.createCell(9).setCellValue("Other");
		
		n.createCell(11).setCellValue(installment);
		n.createCell(12).setCellValue(joiningFee);
		n.createCell(13).setCellValue(total);
		n.createCell(14).setCellValue(plan);
		n.createCell(15).setCellValue(planDetail);
		n.createCell(16).setCellValue(contactName);
		n.createCell(17).setCellValue(contactLastName);
		n.createCell(18).setCellValue(contactAddress1);
		n.createCell(19).setCellValue(contactAddress2);
		n.createCell(20).setCellValue(contactTel);
		n.createCell(21).setCellValue(contactMobile);
		n.createCell(22).setCellValue(" ");
		n.createCell(23).setCellValue(" ");
		n.createCell(24).setCellValue(principalEmail);
		n.createCell(25).setCellValue(contactName);
		n.createCell(26).setCellValue(contactLastName);
		n.createCell(27).setCellValue(choose(principAddress1,contactAddress1));
		n.createCell(28).setCellValue(principAddress2);
		n.createCell(29).setCellValue(choose(principalMobile,contactMobile));
		n.createCell(30).setCellValue(choose(principalTel,contactTel));
		n.createCell(31).setCellValue(choose(principalEmail,""));
		n.createCell(32).setCellValue(" ");
		n.createCell(33).setCellValue(1);
		
		n.createCell(35).setCellValue(total);
		
		n.createCell(37).setCellValue(11);
		n.createCell(38).setCellValue(0);
		n.createCell(39).setCellValue(new Date(System.currentTimeMillis() + 5000000));
		n.createCell(40).setCellValue(new Date(System.currentTimeMillis() + 5000000));
		n.createCell(41).setCellValue(new Date(System.currentTimeMillis() + 5000000));
		n.createCell(42).setCellValue(" ");
		n.createCell(43).setCellValue(spouseName);
		n.createCell(44).setCellValue(new Date(System.currentTimeMillis() + 5000000));
		n.createCell(45).setCellValue(new Date());
		n.createCell(46).setCellValue(" ");
		n.createCell(47).setCellValue(spouseId);
		n.createCell(48).setCellValue(" ");
		n.createCell(49).setCellValue(" ");
		n.createCell(50).setCellValue(member1);
		n.createCell(51).setCellValue(member1id);
		n.createCell(52).setCellValue(1);
		
		n.createCell(53).setCellValue(member2);
		n.createCell(54).setCellValue(member2id);
		n.createCell(55).setCellValue(1);
		
		n.createCell(56).setCellValue(member3);
		n.createCell(57).setCellValue(member3id);
		n.createCell(58).setCellValue(1);
		
		n.createCell(59).setCellValue(member4);
		n.createCell(60).setCellValue(member4id);
		n.createCell(61).setCellValue(1);
		
		n.createCell(62).setCellValue(member5);
		n.createCell(63).setCellValue(member5id);
		n.createCell(64).setCellValue(1);
	}
	
	
	public static void createRow(Row row, int ids, Sheet sheet, File source){
		if(row == null){
			//System.out.println(row.getSheet().getWorkbook().)
			return;
		}
//		System.out.println(id);
//		if(id > 65535){
//			System.err.println(id);
//		}
			
		
		Row n = sheet.createRow(sheet.getLastRowNum() +1);
		String dateTransaction = row.getCell(0)==null?"":getStringCellValue(row.getCell(0));
		dateTransaction = dateTransaction.trim().replace("-", ".").replace(",", ".").replace("/", ".");
		String fsNumber = row.getCell(1)==null?"":getStringCellValue(row.getCell(1));
		String plan = row.getCell(2)==null?"":getStringCellValue(row.getCell(2));
		String salesAgent = row.getCell(3)==null?"":getStringCellValue(row.getCell(3));
		String contactName = row.getCell(4)==null?"":getStringCellValue(row.getCell(4));
		String contactAddress = row.getCell(5)==null?"":getStringCellValue(row.getCell(5));
		String contactTel = getStringCellValue(row.getCell(6));
		String contactMobile = getStringCellValue(row.getCell(7));
		String principAddress = row.getCell(8)==null?"":getStringCellValue(row.getCell(8));
		String principalTel = getStringCellValue(row.getCell(9));
		String principalMobile =getStringCellValue(row.getCell(10));
		String principalEmail = getStringCellValue(row.getCell(11));
		
		double joiningFee = row.getCell(12)==null?0: getNumericCellValue(row.getCell(12));
		double installment = row.getCell(13)==null?0:getNumericCellValue(row.getCell(13));
		double advancePayment = row.getCell(14)==null?0:getNumericCellValue(row.getCell(14));
		double total = row.getCell(15)==null?0:getNumericCellValue(row.getCell(15));
		String paymentMethod = row.getCell(16)==null?"o":getStringCellValue(row.getCell(16));
		
		String spouseName = row.getCell(17)==null?"":getStringCellValue(row.getCell(17));
		String spouseId = row.getCell(18)==null?"":getStringCellValue(row.getCell(18));
		
		String member1 = row.getCell(19)==null?"":getStringCellValue(row.getCell(19));
		String member1id = row.getCell(20)==null?"":getStringCellValue(row.getCell(20));
		
		String member2 = row.getCell(21)==null?"":getStringCellValue(row.getCell(21));
		String member2id = row.getCell(22)==null?"":getStringCellValue(row.getCell(22));
		
		String member3 = row.getCell(23)==null?"":getStringCellValue(row.getCell(23));
		String member3id = row.getCell(24)==null?"":getStringCellValue(row.getCell(24));
		
		String member4 = row.getCell(25)==null?"":getStringCellValue(row.getCell(25));
		String member4id = row.getCell(26)==null?"":getStringCellValue(row.getCell(26));
		
		String member5 = row.getCell(27)==null?"":getStringCellValue(row.getCell(27));
		String member5id = row.getCell(28)==null?"":getStringCellValue(row.getCell(28));
		
		String member6 = row.getCell(29)==null?"":getStringCellValue(row.getCell(29));
		String member6id = row.getCell(30)==null?"":getStringCellValue(row.getCell(30));
		
		n.createCell(0).setCellValue(n.getRowNum());
		
		Calendar cal = Calendar.getInstance();
		try{
			String[] parts =	StringUtil.split(dateTransaction, ".");
			cal.set(Calendar.YEAR, Integer.parseInt(parts[2]));
			cal.set(Calendar.MONTH, Integer.parseInt(parts[1])-1);
			cal.set(Calendar.DATE, Integer.parseInt(parts[0]));
			n.createCell(1).setCellValue(cal);
			n.createCell(2).setCellValue(cal);
			n.createCell(34).setCellValue(cal);
			n.createCell(10).setCellValue(cal);
			n.createCell(36).setCellValue(getArrear(cal, total));
		}catch(Exception e){
			System.out.println("Transaction Date : -" + dateTransaction + "-:Row:" +row.getRowNum() + ";Sheet:" + row.getSheet().getSheetName() + ";File:" + source.getName() );
		}
		n.createCell(3).setCellValue("Other");
		n.createCell(4).setCellValue(salesAgent);
		n.createCell(5).setCellValue(fsNumber);
		int pay =0;
		if(paymentMethod == null || paymentMethod.length() <=0){
			pay = 0;
		}else if(paymentMethod.equalsIgnoreCase("O")){
			pay = 0;
		}else if(paymentMethod.equalsIgnoreCase("s")){
			pay = 1;
		}else{
			pay = 2;
		}
		
		n.createCell(6).setCellValue(pay);
		n.createCell(7).setCellValue(" ");
		n.createCell(8).setCellValue(" ");
		n.createCell(9).setCellValue("Other");
		
		n.createCell(11).setCellValue(installment);
		n.createCell(12).setCellValue(joiningFee);
		n.createCell(13).setCellValue(total);
		n.createCell(14).setCellValue(plan);
		n.createCell(15).setCellValue(plan);
		n.createCell(16).setCellValue(contactName);
		n.createCell(17).setCellValue(" ");
		n.createCell(18).setCellValue(contactAddress);
		n.createCell(19).setCellValue(" ");
		n.createCell(20).setCellValue(contactTel);
		n.createCell(21).setCellValue(contactMobile);
		n.createCell(22).setCellValue(" ");
		n.createCell(23).setCellValue(" ");
		n.createCell(24).setCellValue(principalEmail);
		n.createCell(25).setCellValue(contactName);
		n.createCell(26).setCellValue(" ");
		n.createCell(27).setCellValue(choose(principAddress,contactAddress));
		n.createCell(28).setCellValue(" ");
		n.createCell(29).setCellValue(choose(principalMobile,contactMobile));
		n.createCell(30).setCellValue(choose(principalTel,contactTel));
		n.createCell(31).setCellValue(choose(principalEmail,""));
		n.createCell(32).setCellValue(" ");
		n.createCell(33).setCellValue(1);
		
		n.createCell(35).setCellValue(total);
		
		n.createCell(37).setCellValue(11);
		n.createCell(38).setCellValue(0);
		n.createCell(39).setCellValue(new Date(System.currentTimeMillis() + 5000000));
		n.createCell(40).setCellValue(new Date(System.currentTimeMillis() + 5000000));
		n.createCell(41).setCellValue(new Date(System.currentTimeMillis() + 5000000));
		n.createCell(42).setCellValue(" ");
		n.createCell(43).setCellValue(spouseName);
		n.createCell(44).setCellValue(new Date(System.currentTimeMillis() + 5000000));
		n.createCell(45).setCellValue(new Date());
		n.createCell(46).setCellValue(" ");
		n.createCell(47).setCellValue(spouseId);
		n.createCell(48).setCellValue(" ");
		n.createCell(49).setCellValue(" ");
		n.createCell(50).setCellValue(member1);
		n.createCell(51).setCellValue(member1id);
		n.createCell(52).setCellValue(1);
		
		n.createCell(53).setCellValue(member2);
		n.createCell(54).setCellValue(member2id);
		n.createCell(55).setCellValue(1);
		
		n.createCell(56).setCellValue(member3);
		n.createCell(57).setCellValue(member3id);
		n.createCell(58).setCellValue(1);
		
		n.createCell(59).setCellValue(member4);
		n.createCell(60).setCellValue(member4id);
		n.createCell(61).setCellValue(1);
		
		n.createCell(62).setCellValue(member5);
		n.createCell(63).setCellValue(member5id);
		n.createCell(64).setCellValue(1);
		
		
		
	}
	
	public static String getStringCellValue(Cell cell){
		if(cell == null){
			return "";
		}
		if(cell.getCellType()  == Cell.CELL_TYPE_NUMERIC){
			return cell.getNumericCellValue() + "";
		}else if(cell.getCellType() == Cell.CELL_TYPE_BLANK){
			return "";
		}else if(cell.getCellType() == Cell.CELL_TYPE_BOOLEAN){
			return cell.getBooleanCellValue() + "";
		}else if(cell.getCellType() == Cell.CELL_TYPE_STRING){
			return cell.getStringCellValue() + "";
		}else{
			return "";
		}
		
	}
	
	public static  double getArrear(Calendar paymentDate, double total){
		return 0;
	}
	
	public static  double getArrear(Date paymentDate, double total){
		return 0;
	}
	
	private static String choose(String s1, String s2){
		if(s1 != null && s1.trim().length() > 0){
			return s1;
		}else{
			return s2;
		}
	}
	
	
	private static void analyse(String file, Sheet sheet, Sheet sheeterror)throws Exception{
		
		int id = 1;
		List<String> fsss = getFSCodes(); 
		//for(File f : new File(file ==null?"E:\\":file).listFiles()){
		if(true){
			File f = new File(file);
			
			try{
//				if(f.isDirectory()){
//					analyse(f.getAbsolutePath(),sheet,sheeterror);
//					continue;
//				}
				if(f.getName().contains("xls") && !f.getName().contains("~$")){
					System.out.println(f.getName());
					FileInputStream fin = new FileInputStream(f);
					Workbook wb = null;
					if(f.getName().endsWith("xls"))
						wb =new HSSFWorkbook(fin);
					else
						wb = new XSSFWorkbook(fin);
					for(int i =0; i < wb.getNumberOfSheets(); i++){
						Sheet s = wb.getSheetAt(i);
						
						
						for(int irow =1; irow < s.getLastRowNum();irow++){
							Row r = s.getRow(irow);
							try{
								if(r!= null){
									//String dateTransaction = r.getCell(0)==null?"":getStringCellValue(r.getCell(0));
									//if(StringUtil.isNotEmpty(dateTransaction)){
									if(true){
										createRowExport(r, id,sheet,f,fsss);
										id++;
									}
								}
							}catch(Exception e){
								//System.err.println(e.getMessage());
								e.printStackTrace();
//								Row errorRow = sheeterror.createRow(sheeterror.getLastRowNum());
//								for(int col =0; col < r.getLastCellNum();col++){
//									Cell newerror = errorRow.createCell(col);
//									if(errorR)
//									newerror.setCellValue(r.getCell(col).getStringCellValue());
//								}
							}
						}
						
						
						//r.createCell(0).setCellValue(f.getName());
						//r.createCell(1).setCellValue(s.getSheetName());
						
						//Row first = s.getRow(0);
//						for(int j =0; j<=first.getLastCellNum();j++){
//							try{
//							r.createCell(j+2).setCellValue(first.getCell(j).getStringCellValue());
//							}catch(Exception e){
//								r.createCell(j+2).setCellValue("ERROR");
//								System.err.println(e.getMessage() + "j=" + j + ";i=" +i);
//							}
//							
//						}
						
					}
					
					fin.close();
				}
			}catch(Exception e){
				e.printStackTrace();
				//System.err.println(f.getName());
			}
		
		}
	}
	
	
	
	public static void copyRow(Row source, Sheet dest){
		Iterator<Cell> iter = source.iterator();
		Row ne = dest.createRow(dest.getLastRowNum()+1);
		int i =0;
		while(iter.hasNext()){
			Cell r = iter.next();
			Cell c = ne.createCell(i);
			if(r.getCellType() == Cell.CELL_TYPE_BOOLEAN){
				c.setCellValue(r.getBooleanCellValue());
			}else if(r.getCellType() == Cell.CELL_TYPE_NUMERIC){
				c.setCellValue(r.getNumericCellValue());
			}else if(r.getCellType() == Cell.CELL_TYPE_STRING){
				c.setCellValue(r.getStringCellValue());
			}
			i=i+1;
		}
	}
	
	public static List<String> getFSCodes()throws Exception{
		File f = new File("c:\\java\\elie\\complete records");
		
		List<String> result = new ArrayList<String>();
		for(int i = 0; i <= 25;i++){
			FileInputStream fin = new FileInputStream(new File(f.getAbsoluteFile() + "\\section" + i + ".xls"));
			HSSFWorkbook w = new HSSFWorkbook(fin);
			
			//HSSFWorkbook destw = new HSSFWorkbook();
			//Sheet dest = destw.createSheet("Sheet 1");
			
			Sheet s = w.getSheetAt(0);
			Iterator<Row> iter = s.iterator();
			while(iter.hasNext()){
				Row r = iter.next();
				if(r != null){
					Cell c = r.getCell(5);
					String fs = getStringCellValue(c);
					if(!result.contains(fs)){
						result.add(fs);
						//copyRow(r, dest);
					}else{
						System.out.println(fs);
					}
					
				}
			}
			fin.close();
			//FileOutputStream fout = new FileOutputStream(new File(f.getAbsoluteFile() + "\\section" + i + ".xls"));
			//destw.write(fout);
			//fout.flush();
			//fout.close();
			
			iter = null;
			s = null;
			w = null;
			//destw = null;
			//dest = null;
			System.gc();
				
		}
		
		return result;
	}
	
	public static void analyseImports()throws Exception{
		HSSFWorkbook n = new HSSFWorkbook();
		HSSFSheet sheet= n.createSheet("sheet 1");
				
		HSSFWorkbook error = new HSSFWorkbook();
		HSSFSheet sheeterror=error.createSheet("sheet 1");
		analyse("E:\\section5",sheet,sheeterror);
		FileOutputStream fout = new FileOutputStream(new File("c:\\java\\elie\\section5.xls"));
		n.write(fout);
		fout.flush();
		fout.close();
		
		
		FileOutputStream fourerror = new FileOutputStream(new File("c:\\java\\elie\\errorssda.xls"));
		error.write(fourerror);
		fourerror.flush();
		fourerror.close();
	}
	
	public static void analyseImportsssdaa()throws Exception{
		HSSFWorkbook n = new HSSFWorkbook();
		HSSFSheet sheet= n.createSheet("sheet 1");
				
		HSSFWorkbook error = new HSSFWorkbook();
		HSSFSheet sheeterror=error.createSheet("sheet 1");
		analyse("c:\\java\\elie\\imppppppppppppp\\Book5.xlsx",sheet,sheeterror);
		FileOutputStream fout = new FileOutputStream(new File("c:\\java\\elie\\section15.xls"));
		n.write(fout);
		fout.flush();
		fout.close();
		
		
		FileOutputStream fourerror = new FileOutputStream(new File("c:\\java\\elie\\errorssda.xls"));
		error.write(fourerror);
		fourerror.flush();
		fourerror.close();
	}
	
	public static void main(String[] args)throws Exception {
		//analyseImportsssdaa();
		System.out.println(getFSCodes().size());
	}

}
