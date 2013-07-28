package org.castafiore.shoppingmall.employee.ui.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class DataModel {
	
	private Calendar startDate;
	
	private Calendar endDate;
	
	private List<PointerUser> pointerUsers = new ArrayList<PointerUser>();


	public DataModel(Calendar startDate, Calendar endDate) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
	}


	public DataModel(Calendar startDate, Calendar endDate,
			List<PointerUser> pointerUsers) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.pointerUsers = pointerUsers;
	}


	public Calendar getStartDate() {
		return startDate;
	}


	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}


	public Calendar getEndDate() {
		return endDate;
	}


	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}


	public List<PointerUser> getPointerUsers() {
		return pointerUsers;
	}


	public void setPointerUsers(List<PointerUser> pointerUsers) {
		this.pointerUsers = pointerUsers;
	}

	

	public Workbook getWorkbook(){
		Workbook w = new HSSFWorkbook();
		Sheet s = w.createSheet();
		


		Row r0 = s.createRow(0);
		Cell c = r0.createCell(0);
		c.setCellValue("Print id");
		r0.createCell(1).setCellValue("Name");
		Calendar cstart = (Calendar)startDate.clone();
		Calendar cend = (Calendar)endDate.clone();
		int ce = 2;
		while(true){ 
			r0.createCell(ce).setCellValue(new SimpleDateFormat("dd/MM").format(cstart.getTime()) + " In" );
			ce++;
			r0.createCell(ce).setCellValue(new SimpleDateFormat("dd/MM").format(cstart.getTime()) + " Out");
			ce++;
			r0.createCell(ce).setCellValue("Total");
			ce++;
			
			r0.createCell(ce).setCellValue("Transport");
			ce++;
			
			cstart.add(Calendar.DATE, 1);
			if(cstart.getTime().getTime() > cend.getTimeInMillis())
				break;
			
			
		}
		r0.createCell(r0.getLastCellNum()).setCellValue("Total heure semaine");
		r0.createCell(r0.getLastCellNum()).setCellValue("Total heure weekend");
		r0.createCell(r0.getLastCellNum()).setCellValue("Total Jour transport");
		
		int row = 1;
		for(PointerUser u : pointerUsers){
			Row r = s.createRow(row);
			r.createCell(0).setCellValue(u.getId());
			r.createCell(1).setCellValue(u.getName());
			u.fillRow(r);
			row++;
		}
		
		
		Calendar clones = (Calendar)startDate.clone();
		
		
		for(int r=1; r< s.getLastRowNum();r++){
			Row rr = s.getRow(r);
			//float weekly=0;
			//float weekend = 0;
			StringBuilder bnormalDay = new StringBuilder();
			StringBuilder bweekend = new StringBuilder();
			StringBuilder btransport = new StringBuilder();
			for(int col =4; col<rr.getLastCellNum();col=col+4){
				
				int dayofweek = clones.get(Calendar.DAY_OF_WEEK) ;
				if(dayofweek == Calendar.SATURDAY || dayofweek == Calendar.SUNDAY ){
					//weekend = weekend +  Float.parseFloat(rr.getCell(col).getNumericCellValue() + "");
					
					if(bweekend.length() >0){
						bweekend.append(",");
					}
					bweekend.append(getCellIndex(col, r));
					
					
					
					
				}else{
					//weekly = weekly + Float.parseFloat(rr.getCell(col).getNumericCellValue() + "");
					if(bnormalDay.length() >0){
						bnormalDay.append(",");
					}
					bnormalDay.append(getCellIndex(col, r));
				}
				
				if(btransport.length() >0){
					btransport.append(",");
				}
				Cell t = rr.getCell(col+1);
				t.setCellValue(1);
				
				btransport.append(getCellIndex(col+1, r));
				clones.add(Calendar.DATE, 1);
			}
			
			rr.createCell(rr.getLastCellNum()).setCellFormula("SUM(" + bnormalDay.toString() + ")");
			rr.createCell(rr.getLastCellNum()).setCellFormula("SUM(" + bweekend.toString() + ")");
			rr.createCell(rr.getLastCellNum()).setCellFormula("SUM(" + btransport.toString() + ")");
		}
		return w;
		
	}
	
	
	private String getCellIndex(int col, int row){
		String[] as = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
				
				"AA","AB","AC","AD","AE","AF","AG","AH","AI","AJ","AK","AL","AM","AN","AO","AP","AQ","AR","AS","AT","AU","AV","AW","AX","AY","AZ","BA","BB","BC","BD","BE","BF","BG","BH","BI","BJ","BK","BL","BM","BN","BO","BP","BQ","BR","BS","BT","BU","BV","BW","BX","BY","BZ","CA","CB","CC","CD","CE","CF","CG","CH","CI","CJ","CK","CL","CM","CN","CO","CP","CQ","CR","CS","CT","CU","CV","CW","CX","CY","CZ","DA","DB","DC","DD","DE","DF","DG","DH","DI","DJ","DK","DL","DM","DN","DO","DP","DQ","DR","DS","DT","DU","DV","DW","DX","DY","DZ","EA","EB","EC","ED","EE","EF","EG","EH","EI","EJ","EK","EL","EM","EN","EO","EP","EQ","ER","ES","ET","EU","EV","EW","EX","EY","EZ","FA","FB","FC","FD","FE","FF","FG","FH","FI","FJ","FK","FL","FM","FN","FO","FP","FQ","FR","FS","FT","FU","FV","FW","FX","FY","FZ","GA","GB","GC","GD","GE","GF","GG","GH","GI","GJ","GK","GL","GM","GN","GO","GP","GQ","GR","GS","GT","GU","GV","GW","GX","GY","GZ","HA","HB","HC","HD","HE","HF","HG","HH","HI","HJ","HK","HL","HM","HN","HO","HP","HQ","HR","HS","HT","HU","HV","HW","HX","HY","HZ","IA","IB","IC","ID","IE","IF","IG","IH","II","IJ","IK","IL","IM","IN","IO","IP","IQ","IR","IS","IT","IU","IV","IW","IX","IY","IZ","JA","JB","JC","JD","JE","JF","JG","JH","JI","JJ","JK","JL","JM","JN","JO","JP","JQ","JR","JS","JT","JU","JV","JW","JX","JY","JZ"};
	
		
		int rr = row+1;

		return as[col] + rr;
		
	}
	
	
	
}
