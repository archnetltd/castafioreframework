package org.castafiore.reconcilliation;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.castafiore.accounting.CashBook;
import org.castafiore.persistence.Dao;
import org.castafiore.shoppingmall.checkout.BillingInformation;
import org.castafiore.shoppingmall.checkout.Order;
import org.castafiore.spring.SpringUtil;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;





public class ReconciliationUtil {
	
	
	public static List<Map<String, String>> loadOrdersValues(){
		String sql = "select value from WFS_FILE where dtype='Value' and name='applicationForm'";
		List l = SpringUtil.getBeanOfType(Dao.class).getReadOnlySession().createSQLQuery(sql).list();
		
		List<Map<String,String>> result = new ArrayList<Map<String,String>>();
		for(Object o : l){
			if(o!=null)
			result.add(BillingInformation.buildMap(o.toString()));
		}
		
		return result;
	}
	public static Sheet getMatchSheet()throws Exception{
		
		String path = "/root/users/"+ Util.getLoggedOrganization() + "/reconcilationrules.xls";
		BinaryFile bf = null;
		boolean recreate = false;
		if(SpringUtil.getRepositoryService().itemExists(path)){
			bf = (BinaryFile)SpringUtil.getRepositoryService().getFile(path, Util.getRemoteUser());
			if(bf.getInputStream() == null){
				recreate = true;
			}
		}else{
			
			Directory dir = SpringUtil.getRepositoryService().getDirectory(ResourceUtil.getParentPath(path), Util.getRemoteUser());
			bf = dir.createFile("reconcilationrules.xls", BinaryFile.class);
			dir.save();
			recreate = true;
		}
		
		if(recreate){
			
			Workbook wb = new HSSFWorkbook();
			OutputStream out = bf.getOutputStream();
			Sheet s = wb.createSheet();
			wb.write(out);
			out.flush();
			out.close();
			return s;
		}else{
			Workbook wb = new HSSFWorkbook(bf.getInputStream());
			return wb.getSheetAt(0);
		}
	}
	
	public static void saveMatchSheet(Workbook wb)throws Exception{
		String path = "/root/users/"+ Util.getLoggedOrganization() + "/reconcilationrules.xls";
		BinaryFile bf = null;
		if(SpringUtil.getRepositoryService().itemExists(path)){
			bf = (BinaryFile)SpringUtil.getRepositoryService().getFile(path, Util.getRemoteUser());
		}else{
			Directory dir = SpringUtil.getRepositoryService().getDirectory(ResourceUtil.getParentPath(path), Util.getRemoteUser());
			bf = dir.createFile("reconcilationrules.xls", BinaryFile.class);
			dir.save();
		}
		
		OutputStream out = bf.getOutputStream();
		wb.write(out);
		out.flush();
		out.close();
		
	}
	public static List<ReconcilationDTO> getMatchDTO(Sheet matchSheet, ReconcilationDTO dto){
		List<ReconcilationDTO> result = new ArrayList<ReconcilationDTO>();
		for(int i =0; i <= matchSheet.getLastRowNum();i++){
			Row r = matchSheet.getRow(i);
			if(r == null)
				continue;
			if(r.getLastCellNum() > 3){
				String ref = r.getCell(1).getStringCellValue();
				String amount = r.getCell(2).getStringCellValue();
				String bank = r.getCell(3).getStringCellValue();
				String acc = r.getCell(4).getStringCellValue();
				boolean match = false;
				if(StringUtil.isNotEmpty(ref)){
					match = dto.getRefNumber().equalsIgnoreCase(ref);
				}
				
				if(match){
					if(StringUtil.isNotEmpty(amount)){
						match = dto.getAmount().equalsIgnoreCase(amount);
					}
				}else{
					continue;
				}
				
				if(match){
					if(StringUtil.isNotEmpty(bank)){
						match = dto.getBank().equalsIgnoreCase(bank);
					}
				}else{
					continue;
				}
				
				if(match){
					if(StringUtil.isNotEmpty(acc)){
						match = dto.getAccountNumber().equalsIgnoreCase(acc);
					}
				}else{
					continue;
				}
				
				
				if(match){
					int in = 1;
					
					while(true){
						
						if(r.getLastCellNum() > in*5){
							int start = (in*5) + 1;
							if(r.getCell(start) != null){
								ReconcilationDTO d = new ReconcilationDTO("");
								d.setRefNumber(r.getCell(start).getStringCellValue());
								d.setAmount(r.getCell(start+1).getStringCellValue());
								d.setBank(r.getCell(start+2).getStringCellValue());
								d.setAccountNumber(r.getCell(start+3).getStringCellValue());
								d.setName(r.getCell(start+4).getStringCellValue());
								result.add(d);
								in++;
							}else
								break;
							
						}else{
							break;
						}
					}
					return result;
					
				}
			}
			
		}
		return null;
	}
	public static String getMatch(Sheet matchSheet, ReconcilationDTO dto){
		
		for(int i =0; i <= matchSheet.getLastRowNum();i++){
			Row r = matchSheet.getRow(i);
			if(r == null)
				continue;
			if(r.getLastCellNum() > 3){
				String ref = r.getCell(1).getStringCellValue();
				String amount = r.getCell(2).getStringCellValue();
				String bank = r.getCell(3).getStringCellValue();
				String acc = r.getCell(4).getStringCellValue();
				boolean match = false;
				if(StringUtil.isNotEmpty(ref)){
					match = dto.getRefNumber().equalsIgnoreCase(ref);
				}
				
				if(match){
					if(StringUtil.isNotEmpty(amount)){
						match = dto.getAmount().equalsIgnoreCase(amount);
					}
				}else{
					continue;
				}
				
				if(match){
					if(StringUtil.isNotEmpty(bank)){
						match = dto.getBank().equalsIgnoreCase(bank);
					}
				}else{
					continue;
				}
				
				if(match){
					if(StringUtil.isNotEmpty(acc)){
						match = dto.getAccountNumber().equalsIgnoreCase(acc);
					}
				}else{
					continue;
				}
				
				
				if(match){
					int in = 1;
					String s = "";
					while(true){
						
						if(r.getLastCellNum() > in*5){
							int start = (in*5) + 1;
							if(r.getCell(start) != null){
								if(s.length() > 0){
									s = s + "<br>";
								}
								s = s+ r.getCell(start).getStringCellValue() + "|" + r.getCell(start+1).getStringCellValue()+ "|" + r.getCell(start+2).getStringCellValue()+ "|" + r.getCell(start + 3).getStringCellValue()+ "|" + r.getCell(start +4).getStringCellValue();
								in++;
							}else
								break;
							
						}else{
							break;
						}
					}
					return s;
				}
			}
		}
		
		return null;
		
	}
	
	public static Map<String, String> searchFromCode(List<Map<String, String>> orders, String code){
		if(code != null){
		for(Map<String,String> o : orders){
			System.out.println("matching:" + code + ":=>" + o.toString());
			if(code.equalsIgnoreCase(o.get("fsNumber"))){
				return o;
			}
		}
		}
		return null;
	}
	
	public static List<Map<String, String>> searchFromAccNumber(List<Map<String, String>> orders, String acc){
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		if(acc != null){
			for(Map<String, String> o : orders){
				if(acc.equalsIgnoreCase(o.get("accountNumber"))){
					result.add(o);
				}
			}
		}
		return result;
	}
	
//	public static List<Order> loadOrders(){
//		List result = SpringUtil.getRepositoryService().executeQuery(new QueryParameters().setEntity(Order.class), Util.getRemoteUser());
//		return result;
//	}
	
	
//	public static List<BillingInformation> loadBillings(){
//		List result = SpringUtil.getRepositoryService().executeQuery(new QueryParameters().setEntity(BillingInformation.class), Util.getRemoteUser());
//		return result;
//	}
	
	
	public static List<ReconcilationDTO> analyse(InputStream fstream, List<Map<String, String>> orders){
		List<ReconcilationDTO> rs = new ArrayList<ReconcilationDTO>();
		try {
			
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			String sdate = "";
			while ((strLine = br.readLine()) != null) {
				//System.out.println(strLine);
				
				if(strLine != null){
					
					if(strLine.endsWith("=")){
						strLine = strLine.replace("=", "") + br.readLine();
					}
					
					if(strLine.startsWith("0")){
						sdate = StringUtil.split(strLine, "|")[2];
						SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
						sdate = new SimpleDateFormat("dd MMM yyyy").format(format.parse(sdate));
					}else if(strLine.startsWith("1")){
						List<ReconcilationDTO> dtos = getDTO(strLine, orders);
						for(ReconcilationDTO dto : dtos){
							dto.setDate(sdate);
							rs.add(dto);
						}
					}
				}
			}
			in.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
		return rs;
	}
	
	public static boolean createItem(ReconcilationDTO tr, CashBook cb){
		
		String ref = tr.getRefNumber();
		String amount = tr.getAmount();
		if(amount != null && amount.contains("(<label")){
			amount = StringUtils.splitByWholeSeparator(amount, "(<label")[0];
		}
		try{
		if(StringUtil.isNotEmpty(ref) && StringUtil.isNotEmpty(amount)){
			String nm = new Date().getTime() + StringUtil.nextString(10);
			String code = StringUtil.nextString(10);
			
			String sql ="insert into WFS_FILE (clazz, dateCreated, lastModified, name, parent_id, summary, title, code, paymentMethod,total, accountCode, dateOfTransaction, DTYPE, absolutePath, size, status, commentable, dislikeit, likeit,ratable) values " +
					"('org.castafiore.accounting.CashBookEntry',now(),now(),'"+nm+"', " +
					"'/root/users/elieandsons/Applications/e-Shop/elieandsons/DefaultCashBook', " +
					"'Installment "+tr.getName()+"  "+tr.getDate()+"', 'Installment "+StringEscapeUtils.escapeSql(tr.getName())+"  "+tr.getDate()+"', '"+code+"', 'Standing Order', '"+amount.trim()+"', '"+tr.getName()+"',now() ,'CashBookEntry','/root/users/elieandsons/Applications/e-Shop/elieandsons/DefaultCashBook/"+nm+"',1,1,true,1,1, true);";
			
			SpringUtil.getBeanOfType(Dao.class).getSession().createSQLQuery(sql).executeUpdate();
			return true;
		} 
		}catch(Exception e){
			e.printStackTrace();
		
		}
		return false;
		
	}
	
	
	public static List<Map<String, String>> searchText(String txt, List<Map<String, String>> all){
		txt = txt.replace("MRS", "").replace("MR", "");
		Map<String, Integer> score = new HashMap<String,Integer>();
		String[] parts = StringUtil.split(txt, " ");
		if(parts != null){
			for(Map<String, String> o : all){
				for(String part : parts){
					//BillingInformation b = getInfo(o, billings);
					
						String firstName = o.get("cfullName");
						String lastName = o.get("cSurname");
						
						
						String ss = firstName!=null?firstName:" ";
						
						ss = lastName!=null?lastName: " ";
						
						if(ss.toLowerCase().contains(part)){
							if(score.containsKey(o.get("fsNumber"))){
								score.put(o.get("fsNumber"), score.get(o.get("fsNumber")) + 1);
							}else{
								score.put(o.get("fsNumber"), 1);
							}
						}
					
					
				}
			}
		}
		
		for(int i = parts.length; i >0; i--){
			List<Map<String, String>> orders =  getKeys(score, i, all);
			if(orders.size() > 0){
				return orders;
			}
		}
		return  new ArrayList<Map<String, String>>();
		
		
	}
	
//	public static BillingInformation getInfo(Order o,List<BillingInformation> billings){
//		for(BillingInformation b : billings){
//			if(b.getAbsolutePath().startsWith(o.getAbsolutePath())){
//				return b;
//			}
//		}return null;
//	}
	
	private static List<Map<String,String>> getKeys(Map<String, Integer> score, Integer value,List<Map<String,String>> all){
		Iterator<String> iter = score.keySet().iterator();
		List<Map<String,String>> result = new ArrayList<Map<String,String>>();
		while(iter.hasNext()){
			String next = iter.next();
			if(score.get(next).equals(value)){
				Map<String,String> o = getFromFsNumber(next, all);
				if(o != null)
					result.add(o);
			}
		}
		
		return result;
	}
	
	public static Map<String,String> getFromFsNumber(String fsNumber, List<Map<String,String>> all){
		for(Map<String,String> o : all){
			if(o != null && o.get("fsNumber") != null && fsNumber != null){
				if(o.get("fsNumber").equalsIgnoreCase(fsNumber)){
					return o;
				}
			}
		}
		return null;
	}
	
	public static void analyseAll(List<ReconcilationDTO> table_){
		List<Map<String,String>> orders = loadOrdersValues();
		for(ReconcilationDTO dto : table_){
			if(getFromFsNumber(dto.getRefNumber(), orders) != null){
				System.out.println("OK");
				dto.setOk(true);
			}
		}
	}
	
	
	private static List<ReconcilationDTO> getDTO(String line, List<Map<String, String>> orders){
		List<ReconcilationDTO> result = new ArrayList<ReconcilationDTO>();
		line = line.toUpperCase();
		String[] parts = StringUtil.split(line, "|");
		if(parts != null){
			if(parts[0].equals("1")){
				try{
				int count = 0;
				for(String part : parts){
					if(part.contains("FS") || part.contains("/11") || part.contains("/12")){
						part =part.replace("FS ", "FS").replace("AND ", " ").replace("-", " ").replace("REF:", "").replace(",", " ");
						for(int i = 10; i < 30;i++){
							part = part.replace("/" + i, "/" + i + " ");
						}
						String as[] = StringUtil.split(part, " "); 
						
						for(String documentNumber : as){
							if(documentNumber != null && documentNumber.trim().length() > 0){
								
								String[] slash = StringUtil.split(documentNumber.trim(), "/");
								if(slash != null && slash.length ==2){
									if(slash[0].startsWith("FS")){
										slash[0] = slash[0].replace("FS", "").trim();
										
									}
									if(slash[0].length() == 3){
										slash[0] = "0" + slash[0];
									}else if(slash[0].length() == 2){
										slash[0] = "00" + slash[0];
									}else if(slash[0].length() == 1){
										slash[0] = "000" + slash[0];
									}
									
									documentNumber = "FS" + slash[0] + "/" + slash[1];
								}
								
								ReconcilationDTO dto = new ReconcilationDTO(line);
								//dto.setOriginalLine(line);
								dto.setRefNumber(documentNumber.trim());
								result.add(dto);
							}
						}
					}
				}
				
				if(result.size() == 0){
					ReconcilationDTO dto = new ReconcilationDTO(line);
					dto.setRefNumber("");
					dto.setComment("No reference number");
					dto.setName(line);
					result.add(dto);
				}
				
				for(String part : parts){
					if(part.toLowerCase().contains("bank")){
						for(ReconcilationDTO dto : result)
							dto.setBank(part);
					}else if(part.equalsIgnoreCase("MUR")){
						for(ReconcilationDTO dto : result)
							dto.setAmount(parts[count+1]);
					}else if(count == 3){
						for(ReconcilationDTO dto : result)
							dto.setName(part);
					}else if(count == 2){
						for(ReconcilationDTO dto : result)
							dto.setAccountNumber(part);
					}
					
					count = count+1;
				}
				Map<String, String> order = null;
				
				for(ReconcilationDTO dto: result){
					order = searchFromCode(orders,dto.getRefNumber());
					if(order != null){
						//first priority : code
						dto.setComment(dto.getComment() +  " Order found from FS");
						//dto.setOk(true);
						dto.setStatus("Automatic");
						dto.setOk(true);
						continue;
						
					}else{
						dto.setComment("FS not found.Found from :");
						dto.setStatus("Unreconciled");
					}
						
					//2nd priority : accnumber
					List<Map<String, String>> narrow = searchFromAccNumber(orders, dto.getAccountNumber());
					if(narrow.size() > 0){
						dto.setComment(dto.getComment() +  " acc. number");
						//3rd : name
						List<Map<String, String>> tmp = searchText(dto.getName(), narrow);
						if(tmp.size() > 0){
							//found found in name
							dto.setComment(dto.getComment() +  ",name");
							for(Map<String,String> tp : tmp){
								try{
									if( new BigDecimal( tp.get("total")).equals(new BigDecimal(dto.getAmount()))){
										order = tp;
										dto.setComment(dto.getComment() +  " ,installment");
									}
								}
								catch(Exception e){
									
								}
							}
						}else{
							//not found in name. Take bigger
							for(Map<String,String> tp : narrow){
								try{
									if(new BigDecimal(tp.get("total")).equals(new BigDecimal(dto.getAmount()))){
										order = tp;
										dto.setComment(dto.getComment() +  " ,installment");
									}
								}
								catch(Exception e){
									
								}
							}
						}
						if(order == null){
							order = narrow.get(0);
							
						}
					}else{
						narrow = orders;
					}
						
					if(order == null){
						//finally search from text
						List<Map<String,String>> narrowfromName = searchText(dto.getName(), orders);
						if(narrowfromName.size() >0){
							dto.setComment(dto.getComment() +  ",name");
							for(Map<String,String> tp : narrowfromName){
								try{
									if(new BigDecimal(tp.get("total")).equals(new BigDecimal(dto.getAmount()))){
										order = tp;
										dto.setComment(dto.getComment() +  " ,installment");
									}
								}
								catch(Exception e){
									
								}
							}
							if(order == null){
								order = narrowfromName.get(0);
							}
						}
					}
					
					if(order == null){
						dto.setComment("Not found at all");
					}else{
						try{
							//
							if(new BigDecimal(order.get("total")).equals(new BigDecimal(dto.getAmount()))){
									
								if(!dto.getComment().contains("installment")){
									dto.setComment(dto.getComment() +  " ,installment");
								}
							} else{
								dto.setAmount(dto.getAmount() + "(<label style='color:red'>" + order.get("total") + "</label>)");
								dto.setStatus("Invalid Amount");
							}
							
							dto.setRefNumber(dto.getRefNumber() + "(<label style='color:red'>" + order.get("fsNumber") + "</label>)");
							
							//BillingInformation billing = getInfo(order, billings);
							
							
								String firstName = order.get("cfullName");//billing.getFirstName();
								String lastName = order.get("cSurname");//billing.getLastName();
								
								String ss = firstName!=null?firstName:" ";
								
								ss = lastName!=null?lastName: " ";
								
								dto.setName(dto.getName()+ "(<label style='color:red'>" + ss + "</label>)");
							
							
							
							
							//dto.setAmount(amount)
						}
						catch(Exception e){
							
						}
					}
					
					
					
					
					
				}
				
				}catch(Exception e){
					for(ReconcilationDTO dto : result){
						dto.setComment("Cannot parse line");
						dto.setName(line);
					}
				}
			}
		}
		return result;
	}
	
	
	public static void main(String[] args)throws Exception {
		StringBuilder bb = new StringBuilder();
		HSSFWorkbook b = new HSSFWorkbook(new FileInputStream("C:\\java\\elie\\payments.xls"));
		Sheet sheet = b.getSheetAt(0);
		int lines = 0;
		for(int row = 2; row < sheet.getLastRowNum();row++){
			Row r = sheet.getRow(row);
			String fs = r.getCell(2).getStringCellValue();
			if(!StringUtil.isNotEmpty(fs))
				break;
			String name = r.getCell(3).getStringCellValue();
			for(int col = 6; col < r.getLastCellNum(); col++){
				try{
					BigDecimal amount = new BigDecimal(r.getCell(col).getNumericCellValue());
					if(amount.doubleValue() > 0){
					String nm = new Date().getTime() + StringUtil.nextString(10);
					String sql ="insert into WFS_FILE (clazz, dateCreated, lastModified, name, parent_id, summary, title, code, paymentMethod,total, accountCode, dateOfTransaction, DTYPE, absolutePath, size, status, commentable, dislikeit, likeit,ratable) values " +
					"('org.castafiore.accounting.CashBookEntry',now(),now(),'"+nm+"', " +
					"'/root/users/elieandsons/Applications/e-Shop/elieandsons/DefaultCashBook', " +
					"'Installment "+fs+"', 'Installment "+fs+" ', '"+fs+"', 'Bank transfer', '"+amount+"', '"+fs+"',now() ,'CashBookEntry','/root/users/elieandsons/Applications/e-Shop/elieandsons/DefaultCashBook/"+nm+"',1,1,true,1,1, true);";
					bb.append(sql).append("\n");
					lines = lines +1;
					
					if(lines == 500){
						FileOutputStream fout = new FileOutputStream("c:\\java\\out.sql",true);
						fout.write(bb.toString().getBytes());
						fout.flush();
						fout.close();
						bb = new StringBuilder();
						lines = 0;
					}
					}
					
					
				}catch(Exception e){
					
				}
			}
			
		}
		
		
		lines =0;
		sheet = b.getSheetAt(1);
		
		for(int row = 2; row < sheet.getLastRowNum();row++){
			Row r = sheet.getRow(row);
			String fs = r.getCell(1).getStringCellValue();
			if(!StringUtil.isNotEmpty(fs))
				break;
			String name = r.getCell(2).getStringCellValue();
			for(int col = 5; col < r.getLastCellNum(); col++){
				try{
					BigDecimal amount = new BigDecimal(r.getCell(col).getNumericCellValue());
					if(amount.doubleValue() > 0){
					String nm = new Date().getTime() + StringUtil.nextString(10);
					String sql ="insert into WFS_FILE (clazz, dateCreated, lastModified, name, parent_id, summary, title, code, paymentMethod,total, accountCode, dateOfTransaction, DTYPE, absolutePath, size, status, commentable, dislikeit, likeit,ratable) values " +
					"('org.castafiore.accounting.CashBookEntry',now(),now(),'"+nm+"', " +
					"'/root/users/elieandsons/Applications/e-Shop/elieandsons/DefaultCashBook', " +
					"'Installment "+fs+"', 'Installment "+fs+" ', '"+fs+"', 'Cash', '"+amount+"', '"+fs+"',now() ,'CashBookEntry','/root/users/elieandsons/Applications/e-Shop/elieandsons/DefaultCashBook/"+nm+"',1,1,true,1,1, true);";
					bb.append(sql).append("\n");
					lines = lines +1;
					
					if(lines == 500){
						FileOutputStream fout = new FileOutputStream("c:\\java\\out.sql",true);
						fout.write(bb.toString().getBytes());
						fout.flush();
						fout.close();
						bb = new StringBuilder();
						lines = 0;
					}
					}
					
					
				}catch(Exception e){
					
				}
			}
			
		}
		
		
	}

}
