/**
 * 
 */
package org.castafiore.shoppingmall.employee.ui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.castafiore.KeyValuePair;
import org.castafiore.SimpleKeyValuePair;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.Value;

/**
 * @author acer
 *
 */
public class OCUtil {
	
	//private final static String connectionUrl = "jdbc:odbc:DRIVER=Microsoft Access Driver (*.mdb, *.accdb);DBQ=C:\\java\\oceancall\\hitftpa_pesonallog\\log.accdb;";
	
	private final static String connectionUrls = "jdbc:sqlserver://10.0.2.2:1433;databaseName=HITFPTA;user=hitattendance;password=upstage;";
	
	//private final static String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
	
	private final static String drivers = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	
	private final static String username = "hitattendance";
	private final static String password = "upstage";
	
	
	
	
	public static void importEntries(Double userid, Calendar start, Calendar end)throws Exception{
		Connection con = getConnection();
		String query = "SELECT Min(DateTime) AS MinOfDateTime, Max(DateTime) AS MaxOfDateTime FROM PERSONALLOG where FingerPrintID=" + userid + " and DateLog = ?";
		PreparedStatement stmt = con.prepareStatement(query);
		while(true){
			long[] data = getEntryExit(userid, start, con, stmt);
			if(data != null){
				String rootPath = "/root/users/"+Util.getLoggedOrganization()+"/timesheet/" + userid;
				
				String p = new SimpleDateFormat("yyyyMMdd").format(start.getTime());
				if(!SpringUtil.getRepositoryService().itemExists(rootPath)){
					Directory dir = SpringUtil.getRepositoryService().getDirectory("/root/users/"+Util.getLoggedOrganization()+"/timesheet", Util.getRemoteUser()) ;
					Directory myt = dir.createFile(userid + "", Directory.class);
					Directory mm = myt.createFile(p, Directory.class);
					Value st = mm.createFile("start", Value.class);
					Calendar c1 = Calendar.getInstance();
					c1.setTimeInMillis(data[0]);
					st.setDate(c1);
					Value ed = mm.createFile("end", Value.class);
					c1.setTimeInMillis(data[1]);
					ed.setDate(c1);
					dir.save();
				}else {
					Directory myt = SpringUtil.getRepositoryService().getDirectory(rootPath, Util.getRemoteUser());
					if(myt.getFile(p) != null){
						Directory mm = myt.getFile(p, Directory.class);
						Value st = mm.getFile("start", Value.class);
						Calendar c1 = Calendar.getInstance();
						c1.setTimeInMillis(data[0]);
						st.setDate(c1);
						Value ed = mm.getFile("end", Value.class);
						c1.setTimeInMillis(data[1]);
						ed.setDate(c1);
						myt.save();
					}else{
						Directory mm = myt.createFile(p, Directory.class);
						Value st = mm.createFile("start", Value.class);
						Calendar c1 = Calendar.getInstance();
						c1.setTimeInMillis(data[0]);
						st.setDate(c1);
						Value ed = mm.createFile("end", Value.class);
						c1.setTimeInMillis(data[1]);
						ed.setDate(c1);
						myt.save();
					}
				}
				
				
			}
			start.add(Calendar.DATE, 1);
			if(start.getTimeInMillis() > end.getTimeInMillis()){
				break;
			}
		}
		
		
	}
	
	private static Connection getConnection()throws Exception{
		 Connection con =null;
			String strConnect =   connectionUrls;//"jdbc:odbc:DRIVER=Microsoft Access Driver (*.mdb, *.accdb);DBQ=C:\\java\\oceancall\\hitftpa_pesonallog\\log.accdb;";
           Class.forName(drivers);
           con = DriverManager.getConnection(strConnect,username,password);
           return con;
		
	}
	
	public static long[] getEntryExit(Double id, Calendar date, Connection con, PreparedStatement stmt)throws Exception{
       stmt.setDate(1, new java.sql.Date(date.getTimeInMillis()));
      // stmt.setDate(2, new java.sql.Date(date.getTimeInMillis()+1000000));
       stmt.execute(); 
       ResultSet rs = stmt.getResultSet(); 
       if (rs != null) {
    	  
    	   if (rs.next()) {
    		   Timestamp d1 = rs.getTimestamp(1);
    		   Timestamp d2 = rs.getTimestamp(2);
    		   if(d1!=null && d2!=null){
    			   System.out.println(id + ":" + new SimpleDateFormat("dd/MM/yyyy").format(date.getTime()) + ":" +d1.toString());
    			   long[] res = new long[]{d1.getTime(),d2.getTime()};
    			   return res;
    		   }
           }
    	   rs.close();
       }
       return null;
	}
	
	
	public static Sheet geFingerSheet(Double id, Calendar date)throws Exception{
		Sheet sheet = null;
		try {
			
			
           Connection con =getConnection(); //DriverManager.getConnection(strConnect, username, password); 
           if (null == con) {
        	  // System.out.println(DAM + "Unable to connect to data source " + strConnect);
        	   return null;
           }
          
           String query = "SELECT * from PERSONALLOG where FingerPrintID=" + id + " and DateLog=?";
           PreparedStatement stmt = con.prepareStatement(query);
           stmt.setDate(1, new java.sql.Date(date.getTimeInMillis()));
           stmt.execute(); 
           ResultSet rs = stmt.getResultSet(); 
           if (rs != null) {
        	   Workbook book = new HSSFWorkbook();
        	   sheet = book.createSheet();
        	   ResultSetMetaData rsmd = rs.getMetaData();
        	   Row r = sheet.createRow(0);
        	   for (int column = 1; column <= rsmd.getColumnCount(); column++) {
        		   Cell c = r.createCell(column -1);
        		   c.setCellValue(rsmd.getColumnName(column));
        	   }
        	   
        	   int rowCount = 0;
        	   while (rs.next()) {
        		   Row nr = sheet.createRow(rowCount + 1);
        		   for (int column = 1; column <= rsmd.getColumnCount(); column++) {
	        		 if(rsmd.getColumnType(column) == Types.DATE){
	        			 nr.createCell(column-1).setCellValue(rs.getDate(column));
	        		 }else{
	        			 nr.createCell(column-1).setCellValue(rs.getString(column));
	        		 }
	        		 
	        	   }
        		   rowCount++;
               }
        	   System.out.println(": Total Row Count: " + rowCount);
           }
           stmt.close(); 
           con.close(); 
           
           
       } catch (Exception err) {
    	  // System.out.println(DAM + ": Exception: " + err.getMessage());
    	   err.printStackTrace();
       } finally {
           //System.out.println(DAM + ": Cleanup. Done.");
       }
       return sheet;
	}
	
	
	public static Workbook export(Calendar start, Calendar end)throws Exception{
		
		Workbook wb = new HSSFWorkbook();
		Sheet s = wb.createSheet();
		List<KeyValuePair> kvs = geFingerSheet();
		
		Directory timesheet = SpringUtil.getRepositoryService().getDirectory("/root/users/"+Util.getLoggedOrganization()+"/timesheet", Util.getRemoteUser());
		List<Directory> users = timesheet.getFiles(Directory.class).toList();
		
		
		
		Date ed = end.getTime();
		int rows = 0;
		for(Directory user : users){
			String userid = user.getName();
			
			Date sta = start.getTime();
			Calendar csta = Calendar.getInstance();
			csta.setTime(sta);
			
			Row r = s.createRow(rows);
			r.createCell(0).setCellValue(userid);
			
			String longName = "";
			double duserid = Double.parseDouble(userid);
			for(KeyValuePair kv : kvs){
				if (Double.parseDouble(kv.getKey())==duserid){
					longName = kv.getValue();
				}
			}
			
			r.createCell(1).setCellValue(longName);
			int cols = 2;
			while(true){
				String p = new SimpleDateFormat("yyyyMMdd").format(sta);
				Directory dir = user.getFile(p, Directory.class);
				
				if(dir != null){
					
					
					long l = dir.getFile("start", Value.class).getLong();
					long e = dir.getFile("end", Value.class).getLong();

					Calendar cal1 = Calendar.getInstance();
					cal1.setTimeInMillis(l);
					
					r.createCell(cols).setCellValue(cal1);
					cols = cols +1;
					
					Calendar cal2 = Calendar.getInstance();
					cal2.setTimeInMillis(e);
					r.createCell(cols).setCellValue(cal2);
					cols=cols+1;
					
					
				}
				csta.add(Calendar.DATE, 1);
				if(csta.getTime().getTime() > ed.getTime())
					break;
				
			}
			rows++;
		}
		
		return wb;
	}
	
	
	
	
	public static Workbook saveAndExportToExcel(Calendar start, Calendar end)throws Exception{
		
		Workbook wb = new HSSFWorkbook();
		Sheet s = wb.createSheet();
		List<KeyValuePair> kvs = geFingerSheet();
		
		Connection con = getConnection();
		
		
		
		Date ed = end.getTime();
		int rows = 0;
		for(KeyValuePair kv : kvs){
			String userid = kv.getKey();
			String query = "SELECT Min(DateTime) AS MinOfDateTime, Max(DateTime) AS MaxOfDateTime FROM PERSONALLOG where FingerPrintID=" + userid + " and DateLog = ?";
			PreparedStatement stmt = con.prepareStatement(query);
			Date sta = start.getTime();
			Calendar csta = Calendar.getInstance();
			csta.setTime(sta);
			
			Directory timesheet = SpringUtil.getRepositoryService().getDirectory("/root/users/"+Util.getLoggedOrganization()+"/timesheet", Util.getRemoteUser());
			
			Directory myt = timesheet.getFile(userid, Directory.class);
			if(myt == null){
				myt = timesheet.createFile(userid, Directory.class);
				timesheet.save();
			}
			
			Row r = s.createRow(rows);
			r.createCell(0).setCellValue(kv.getKey());
			r.createCell(1).setCellValue(kv.getValue());
			int cols = 2;
			while(true){
				
				long[] entry = getEntryExit(Double.parseDouble(kv.getKey()), csta, con, stmt);
				if(entry == null){
					entry = new long[2];
					entry[0] = 0;
					entry[1] = 0;
				}
				
				//saveEntry(Double.parseDouble(userid), entry[0], entry[1], myt);
				
				Calendar cal1 = Calendar.getInstance();
				cal1.setTimeInMillis(entry[0]);
				
				r.createCell(cols).setCellValue(cal1);
				cols = cols +1;
				
				Calendar cal2 = Calendar.getInstance();
				cal2.setTimeInMillis(entry[1]);
				r.createCell(cols).setCellValue(cal2);
				cols=cols+1;
				
				csta.add(Calendar.DATE, 1);
				if(csta.getTime().getTime() > ed.getTime())
					break;
				
			}
			rows++;
		}
		
		return wb;
	}
	
	
	
	
	
	public static List<KeyValuePair> geFingerSheet()throws Exception{
		Sheet sheet = null;
		List<KeyValuePair> result = new ArrayList<KeyValuePair>();
		try {
			
           Connection con =getConnection(); //DriverManager.getConnection(strConnect, username,password); 
           if (null == con) {
        
        	   return null;
           }
           //String query = "SELECT FingerPrintID from PERSONALLOG";
           String query = "SELECT FingerPrintID, EmployeeFirstName, EmployeeMiddleName,EmployeeLastName from EMPLOYEE where EmployeeOutDate IS NULL";
           Statement stmt = con.createStatement();
           stmt.execute(query); 
           ResultSet rs = stmt.getResultSet(); 
           if (rs != null) {
        	   while (rs.next()) {
        		   
        		   SimpleKeyValuePair kv = new SimpleKeyValuePair(rs.getString(1), rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));
        		   result.add(kv);
               }
           }
           stmt.close(); 
           con.close(); 
           
           
       } catch (Exception err) {
    	  // System.out.println(DAM + ": Exception: " + err.getMessage());
    	   err.printStackTrace();
    	   result.add(new SimpleKeyValuePair(Util.getRemoteUser(), Util.getRemoteUser()));
       } finally {
           //System.out.println(DAM + ": Cleanup. Done.");
       }
       return result;
	}
	
	

	Container cc = null;

	
	
	
	public static void main(String[] args) throws Exception {
		try {
			//Connection c = getConnection();
			//System.out.println(geFingerSheet().size());
			//System.out.println(c.isClosed());
			//exportToExcel();
		} catch (Exception e) {
			System.out.println("Error: " + e);
			e.printStackTrace();
		} finally {
			
		}
	}

}
