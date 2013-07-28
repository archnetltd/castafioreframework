/**
 * 
 */
package org.castafiore.shoppingmall.employee.ui.model;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.castafiore.ui.Container;

/**
 * @author acer
 *
 */
public class LoaderUtil {
	
	//private final static String connectionUrls = "jdbc:odbc:DRIVER=Microsoft Access Driver (*.mdb, *.accdb);DBQ=C:\\java\\oceancall\\table.accdb;";
	//private final static String username = "";
	//private final static String password = "";
	//private final static String drivers = "sun.jdbc.odbc.JdbcOdbcDriver";
	
	private final static String drivers = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private final static String username = "hitattendance";
	private final static String password = "upstage";
	private final static String connectionUrls = "jdbc:sqlserver://10.0.2.2:1433;databaseName=HITFPTA;user=hitattendance;password=upstage;";
	
	
	
	
	private static Connection getConnection()throws Exception{
		 Connection con =null;
			String strConnect =   connectionUrls;//"jdbc:odbc:DRIVER=Microsoft Access Driver (*.mdb, *.accdb);DBQ=C:\\java\\oceancall\\hitftpa_pesonallog\\log.accdb;";
           Class.forName(drivers);
           con = DriverManager.getConnection(strConnect,username,password);
           return con;
		
	}
	
	public static long[] getEntryExit(Double id, Calendar date, Connection con, PreparedStatement stmt)throws Exception{
      
		
		stmt.setDate(1, new java.sql.Date(date.getTimeInMillis()));
      
       stmt.execute(); 
       ResultSet rs = stmt.getResultSet(); 
       if (rs != null) {
    	  
    	   if (rs.next()) {
    		   Timestamp d1 = rs.getTimestamp(1);
    		   Timestamp d2 = rs.getTimestamp(2);
    		   if(d1!=null && d2!=null){
    			   //System.out.println(id + ":" + new SimpleDateFormat("dd/MM/yyyy").format(date.getTime()) + ":" +d1.toString());
    			   long[] res = new long[]{d1.getTime(),d2.getTime()};
    			   return res;
    		   }
           }
    	   rs.close();
       }
       return null;
	}
	
	/**
	 * split in weeks
	 * 
	 * 
	 */
	public static DataModel exp(Calendar start, Calendar end)throws Exception{
		
		
		List<PointerUser> kvs = geFingerSheet();
		Connection con = getConnection();
		
		DataModel model = new DataModel(start, end,kvs);
		
		
		
		
		for(PointerUser kv : kvs){
			String query = "SELECT Min(TimeLog) AS MinOfDateTime, Max(TimeLog) AS MaxOfDateTime FROM PersonalLog where DateLog =? and FingerPrintID=?";
			//String query = "SELECT Min(TimeLog) AS MinOfTimeLog, Max(TimeLog) AS MaxOfTimeLog, PersonalLogID FROM PersonalLog GROUP BY PersonalLogID where DateLog=? and FingerPrintID=?";
			PreparedStatement stmt = con.prepareStatement(query);
			String userid = kv.getId();
			stmt.setString(2, userid);
			
			
			Calendar csta = (Calendar)start.clone();

			while(true){
				long[] entry = getEntryExit(Double.parseDouble(kv.getId()), csta, con, stmt);
				if(entry == null){
					entry = new long[2];
					entry[0] = 0;
					entry[1] = 0;
				}
				Calendar cal1 = Calendar.getInstance();
				cal1.setTimeInMillis(entry[0]);
				Calendar cal2 = Calendar.getInstance();
				cal2.setTimeInMillis(entry[1]);
				csta.add(Calendar.DATE, 1);
				
				kv.addPointerEntry(new PointerEntry(cal1,cal2));
				
				if(csta.getTime().getTime() > end.getTimeInMillis())
					break;
				
				
				
			}
			stmt.close();
		}
		
		return model;
		
	}
	
	
	
	
	
	
	
	public static List<PointerUser> geFingerSheet()throws Exception{
		List<PointerUser> result = new ArrayList<PointerUser>();
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
        		   String name = rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4);
        		   if(!name.toLowerCase().contains("confirm") && !name.toLowerCase().contains("formation") && !name.contains("tracking") && !name.toLowerCase().contains("bluephone") && !name.contains("G.VIN")){
        			   PointerUser kv = new PointerUser(rs.getString(1), name);
        			   result.add(kv);
        		   }
               }
           }
           stmt.close(); 
           con.close(); 
           
           
       } catch (Exception err) {
    	  // System.out.println(DAM + ": Exception: " + err.getMessage());
    	   err.printStackTrace();
       } finally {
           //System.out.println(DAM + ": Cleanup. Done.");
       }
       return result;
	}

	Container cc = null;

	
	
	
	public static void main(String[] args) throws Exception {
		try {
			Connection c = getConnection();
			System.out.println(geFingerSheet().size());
			System.out.println(c.isClosed());
			Calendar start = Calendar.getInstance();
			Calendar end = Calendar.getInstance();
			
			start.setTime(new SimpleDateFormat("dd/MM/yyyy").parse("15/01/2012"));
			end.setTime(new SimpleDateFormat("dd/MM/yyyy").parse("31/01/2012"));
			
			Workbook wb =  exp(start, end).getWorkbook();
			
			wb.write(new FileOutputStream(new File("c:\\java\\oceancall\\exx.xls")));
			
		} catch (Exception e) {
			System.out.println("Error: " + e);
			e.printStackTrace();
		} finally {
			
		}
	}
	
	//1 sheet per week
	//

}
