package org.castafiore.flickr;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.castafiore.reconcilliation.ReconcilationDTO;
import org.castafiore.utils.StringUtil;

public class EXFlickrColumn {
	
	
	public static void main(String[] args) throws Exception {
		manipulate();
	}
	
	public static void manipulate()throws Exception{
		HSSFWorkbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("sheet");
		
		FileInputStream fstream = new FileInputStream("c://java//elie//attachments-so/prepared/20120702.txt");
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		while ((strLine = br.readLine()) != null) {
			//System.out.println(strLine);
			
			if(strLine != null){
				if(strLine.endsWith("=")){
					strLine = strLine.replace("=", "") + br.readLine();
				}
				String[] parts = StringUtil.split(strLine, "|");
				
				Row r = sheet.createRow(sheet.getLastRowNum()+1);
				for(String s : parts){
					Cell c = r.createCell(r.getLastCellNum()+1);
					c.setCellValue(s);
				}
			}
		}
		
		
		FileOutputStream fout = new FileOutputStream(new File("c://java//elie//attachments-so/prepared/20120702.xls"));
		wb.write(fout);
		fout.flush();
		fout.close();
		
	}

}



