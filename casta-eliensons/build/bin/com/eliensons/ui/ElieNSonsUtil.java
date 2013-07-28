package com.eliensons.ui;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.castafiore.accounting.CashBook;
import org.castafiore.persistence.Dao;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.security.User;
import org.castafiore.shoppingmall.checkout.BillingInformation;
import org.castafiore.shoppingmall.checkout.Order;
import org.castafiore.shoppingmall.checkout.SalesOrderEntry;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.shoppingmall.reports.ReportsUtil;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXAutoComplete;
import org.castafiore.ui.ex.form.EXCheckBox;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.FileNotFoundException;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.castafiore.wfs.types.File;
import org.castafiore.wfs.types.Value;
import org.hibernate.Session;

import com.eliensons.ui.reconciliation.ReconcilationDTO;

public class ElieNSonsUtil implements Event {
	
	
	
//	public static HSSFSheet getSheet()throws Exception{
//		Workbook wb = new HSSFWorkbook(new FileInputStream("/usr/local/software/tomcat6/webapps/elie/applicationform.xls"));
//		return (HSSFSheet)wb.getSheetAt(0);
//	}
	public static void main(String[] args) throws Exception {
		genUpdate();
	}
	
	
	public static void genSQL(){
		String sql ="select absolutepath,code,total,paymentMethod,chequeNo,pointOfSale,bankName,dateOfTransaction,startInstallmentDate,source,installment,joiningFee,productCode,price,taxRate,subTotal,total,username,firstName,lastName,company,phone,mobile,gender,title,maritalStatus,fax,email,addressLine1,addressLine2,city,nic,dateOfBirth,value,status,dtype from WFS_FILE where dtype in ('Order','BillingInformation','Value', 'SalesOrderEntry')";
	}
	
	public static void genUpdate()throws Exception{
		String dir = "C:\\java\\elie\\new contracts\\importtemplate";
		
		FileOutputStream fout = new FileOutputStream(new java.io.File("c:\\java\\elie\\update.sql"));
		
		for(java.io.File f : new java.io.File(dir).listFiles()){
			if(!f.isDirectory()){
				HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(f));
				
				for(int s = 0; s < wb.getNumberOfSheets();s++){
					Sheet sheet = wb.getSheetAt(s);
					
					for(int r = 1; r < sheet.getLastRowNum();r++){
						try{
						Row row = sheet.getRow(r);
						
						String fs = row.getCell(2).getStringCellValue().replace(" ", "");
						double installment = row.getCell(14).getNumericCellValue();
						String line = "update WFS_FILE set installment = " + installment + " where code = '" + fs + "' and dtype='Order';\n";
						fout.write(line.getBytes());
						
						}catch(Exception e){
							//e.printStackTrace();
						}
					}
					
				}
			}
		}
		fout.flush();
		fout.close();
	}
	
	public static void analyseImport()throws Exception{
		Workbook wb = new HSSFWorkbook(new FileInputStream("/usr/local/software/clients.xls"));
		Dao dao = SpringUtil.getBeanOfType(Dao.class);
		Session session = dao.getReadOnlySession();
		Sheet s = wb.getSheetAt(0);
		for(int i =  0; i < s.getLastRowNum();i++){
			String code = s.getRow(i).getCell(0).getStringCellValue();
			List res = session.createSQLQuery("select code from WFS_FILE where dtype='Order' and code='"+code+"'").list();
			if(res.size() == 0){
				writeLog(code + " Not created");
			}else if(res.size() > 1){
				writeLog(code + " Duplicate");
			}
		}
		
	}
	
	private static void writeLog(String file)throws Exception{
		FileOutputStream in = new FileOutputStream("/usr/local/software/importlog.txt",true);
		in.write((file + "\n").getBytes());
		
	}
	
	
	public static Map<String, String> getParams(Order order){
		User customer = SpringUtil.getSecurityService().loadUserByUsername(order.getOrderedBy());
		BillingInformation bi = order.getBillingInformation();
		SalesOrderEntry entry = order.getFiles(SalesOrderEntry.class).get(0);
		Map<String,String> res = new HashMap<String, String>();
		res.put("cname", customer.toString());
		res.put("caddress", bi.getAddressLine1() + " "  + bi.getAddressLine2());
		res.put("cphone", bi.getPhone() + "/" + bi.getMobile());
		res.put("email", bi.getEmail());
		res.put("fsNumber", order.getCode());
		res.put("date", new SimpleDateFormat("dd/MMM/yyyy").format(order.getDateOfTransaction()));
		Calendar cal = Calendar.getInstance();
		cal.setTime(order.getDateOfTransaction());
		cal.add(Calendar.MONTH, 6);
		Date effDate = cal.getTime();
		res.put("effDate",  new SimpleDateFormat("dd/MMM/yyyy").format(effDate));
		
		res.put("salesman", order.getOwner());
		res.put("plan", entry.getTitle());
		res.put("jFee", StringUtil.toCurrency("MUR", order.getJoiningFee()));
		res.put("inst", StringUtil.toCurrency("MUR", order.getInstallment()));
		res.put("total", StringUtil.toCurrency("MUR", order.getTotal()));
		res.put("paymentMode", order.getPaymentMethod());
		
		cal.add(Calendar.MONTH, -5);
		res.put("firstInstallment", new SimpleDateFormat("dd/MMM/yyyy").format(cal.getTime()));
		
		
		Value val = bi.getFile("applicationForm", Value.class);
		String[] as =  StringUtils.splitByWholeSeparator(val.getString(), "-:;:-");//StringUtil.split(val.getString(), "-:;:-");
		Map<String,String> f = new HashMap<String, String>();
		for(String s : as){
			
			String [] kv = StringUtil.split(s, ":");
			if(kv.length > 1 && !kv[1].equalsIgnoreCase("null")){
				f.put(kv[0], kv[1]);
			}else{
				if(kv.length >0){
					f.put(kv[0], "");
				}
			}
		}
		
		for(int i = 1; i <=6;i++ ){
			if(f.containsKey("c"+i+"surname")){
				res.put("member" + (i+1), f.get("c"+i+"surname") + " " + f.get("c"+i+"name"));
				StringBuilder dob = new StringBuilder();
				for(int c =1; c<=11;c++){
					String key = "c" + i + c;
					dob.append(f.get(key));
				}
				res.put("dob" + (i +1), dob.toString());
				res.put("sn" + (i +1), i+ "");
				
			}else{
				res.put("member" + (i+1), "");
				res.put("dob" + (i +1), "");
				res.put("sn" + (i +1), "");
			}
			
			
			
		}
		res.put("dob", f.get("pdob"));
		
		if(res.containsKey("pidnumber")){
			res.put("dob", res.get("pidnumber") + " " + f.get("pdob"));
		}
		res.put("cname", f.get("cfullName") + " " + f.get("cSurname"));
		res.put("address", f.get("presi") + " " + f.get("presi2"));
		res.put("phone", f.get("ptelhome")+ "/" + f.get("pcell"));
		res.put("email", f.get("email"));
		res.put("name", f.get("pfullName") + " " + f.get("pSurname"));
		if(f.containsKey("sSurname") || f.containsKey("sfullName")){
			res.put("sname", f.get("sfullName") + " " + f.get("sSurname"));
		}else{
			res.put("sname", f.get(""));
		}
		
		if(f.containsKey("sdob")){
			res.put("sdob", f.get("sdob"));
		}else{
			res.put("sdob", "");
		}
		if(f.containsKey("sidnumber")){
			String dob = res.get("sdob");
			res.put("sdob", f.get("sidnumber").toString() + " " + dob );
		}
		
		
		
		return res;
		
	}
	
	

	
	public static String printReceipt(Order order)throws Exception{
		String code = ((SalesOrderEntry)order.getEntries().get(0)).getProductCode();
		String url =  "http://68.68.109.26/elie/agreement-"+code+".xls";
		return ReportsUtil.getReceiptUrl(getParams(order), url);
	}
	
	
//	private static String getDescription(SalesOrderEntry entry)throws Exception{
//		final StringBuilder b = new StringBuilder();
//		if(entry.getOptions() != null){
//			Container c = DesignableUtil.buildContainer(new ByteArrayInputStream(entry.getOptions().getBytes()), false);
//		
//			final Map<String, String> mptions = new HashMap<String, String>();
//		
//		
//			ComponentUtil.iterateOverDescendentsOfType(c, StatefullComponent.class, new ComponentVisitor() {
//				
//				@Override
//				public void doVisit(Container c) {
//					StatefullComponent stf = (StatefullComponent)c;
//					mptions.put(c.getName(), stf.getValue().toString());
//					b.append(c.getName() + ":" + stf.getValue().toString() ).append("\n");
//				}
//			});
//		}
//		return b.toString();
//	}
	
	
	
	
	public static String checkInvNumber(String invNumber){
		Merchant merchant = MallUtil.getCurrentMerchant();
		String dir = merchant.getAbsolutePath() + "/invoiceCodes" + "/" + invNumber;
		try{
			Value val = (Value)SpringUtil.getRepositoryService().getFile(dir, Util.getRemoteUser());
			if(val.getStatus() == 40){
				return "used";
			}else if (val.getStatus() == File.STATE_PUBLISHED){
				return "ok";
			}else{
				return "notprinted";
			}
		}catch(FileNotFoundException fne){
			Order order = getOrder(invNumber);
			if(order != null){
				return "used";
			}else{
				return "ok";
			}
		}
		
	}
	
	
	public static void useInvNumber(String invNumber){
		Merchant merchant = MallUtil.getCurrentMerchant();
		String dir = merchant.getAbsolutePath() + "/invoiceCodes" + "/" + invNumber;
		try{
			Value val = (Value)SpringUtil.getRepositoryService().getFile(dir, Util.getRemoteUser());
			val.setStatus(40);
			val.save();
		}catch(FileNotFoundException fne){
			
		}
	}
	
	public static Order getOrder(String code){
		return ReportsUtil.getOrder(code);
	}
	

	
	public static List getAllAccounts(){
		

		return new ArrayList();
		
	}
 	
	//mcb reconsiliation
	public static void analyseReconcilliationMCB(Container table, InputStream fstream) 
	{
		try {
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			List<Order> orders = loadOrders();
			while ((strLine = br.readLine()) != null) {
				//System.out.println(strLine);
				analyseMCBLine(strLine, table, orders);
			}
			in.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	
	public static void analyseReconcilliationStateBank(Container table, InputStream fstream) 
	{
		try {
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			List<Order> orders = loadOrders();
			while ((strLine = br.readLine()) != null) {
				//System.out.println(strLine);
				analyseStateBankLine(strLine, table, orders);
			}
			in.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	private static void analyseStateBankLine(String line, Container table, List<Order> orders){
		String[] parts = StringUtil.split(line, "|");
		//List<Order> orders = loadOrders();
		if(parts != null){
			//only if starts with 1
			if(parts[0].equals("1")){
				if(parts.length == 7){
					//case when it is complete
					String accountNumber = parts[2];
					String amount = parts[5];
					String documentNumber = parts[1];
					String client = parts[3];
					documentNumber = documentNumber.replace("FS ", "FS").replace("AND ", "").replace("-", " ");
					//List<Order> foAcc = searchFromAccNo(orders, accountNumber);
					String[] docParts = StringUtil.split(documentNumber, " ");
					if(docParts != null && docParts.length > 0){
						for(int i = 0; i < docParts.length; i++){
							String p = docParts[i];
							if(!p.startsWith("FS")){
								p = "FS" + p;
							}
							p.replace("/", "");
							String l2 = p.substring(p.length()-2);
							if(!p.endsWith("/" + l2)){
								p = p.replace(l2, "") + "/" + l2;
							}
							docParts[i] = p;
						}
						documentNumber = "";
						for(String s : docParts){
							Container tr = new EXContainer("", "tr");
							tr.addChild(new EXContainer("acc", "td").setText(accountNumber).addEvent(new ElieNSonsUtil(), DOUBLE_CLICK));
							tr.addChild(new EXContainer("client", "td").setText(client).addEvent(new ElieNSonsUtil(), DOUBLE_CLICK));
							tr.addChild(new EXContainer("amount", "td").setText(amount).addEvent(new ElieNSonsUtil(), DOUBLE_CLICK));
							tr.addChild(new EXContainer("code", "td").setText(s).addEvent(new ElieNSonsUtil(), DOUBLE_CLICK));
							String comment = "";
							boolean check = false;
							Order o = searchFromCode(orders, s);
							if(o != null){
								comment = "Plan found";
								check = true;
							}else{
								comment = "Plan not found";
							}
							tr.addChild(new EXContainer("", "td").setText(comment));
							tr.addChild(new EXContainer("", "td").addChild(new EXCheckBox(s,check)));
							table.addChild(tr);
							documentNumber = documentNumber + " " + s;
						}
						documentNumber = documentNumber.trim();
					}
					System.out.println(accountNumber + "|" + amount + "|" + documentNumber);
 
				}else if(parts.length == 6){
					String accountNumber = parts[2];
					String amount = parts[5];
					String client = parts[3];
					List<Order> fo = searchFromAccNo(orders, accountNumber);
					Container tr = new EXContainer("", "tr");
					tr.addChild(new EXContainer("acc", "td").setText(accountNumber).addEvent(new ElieNSonsUtil(), DOUBLE_CLICK));
					tr.addChild(new EXContainer("client", "td").setText(client).addEvent(new ElieNSonsUtil(), DOUBLE_CLICK));
					tr.addChild(new EXContainer("amount", "td").setText(amount).addEvent(new ElieNSonsUtil(), DOUBLE_CLICK));
					tr.addChild(new EXContainer("code", "td").setText("").addEvent(new ElieNSonsUtil(), DOUBLE_CLICK));
					String comment = "";
					if(fo.size() == 1){
						comment = "Plan found for acc no.";
					}else if(fo.size() > 1){
						comment = "more than 1 plan found for acc no.";
					}else{
						comment = "No plan found for acc no.";
					}
					for(Order o : fo){
						if(o.getTotal().doubleValue() == Double.parseDouble(amount)){
							comment = comment + " Amount matching for " + o.getCode();
							break;
						}
					}
					tr.addChild(new EXContainer("comment", "td").setText(comment));
					tr.addChild(new EXContainer("", "td").addChild(new EXCheckBox("")));
					table.addChild(tr);
					System.out.println(accountNumber + "|" + amount);
				}else{
					System.out.println(line);
				}
			}else{
				try{
					String sdate = parts[2];
					SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
					
					table.setAttribute("date", new SimpleDateFormat("dd MMM yyyy").format(format.parse(sdate)));
				}catch(Exception e){
					
				}
			}
		}
	}
	
	
	public static boolean createItem(ReconcilationDTO tr, CashBook cb){
		
		String ref = tr.getRefNumber();
		String amount = tr.getAmount();
		try{
		if(StringUtil.isNotEmpty(ref) && StringUtil.isNotEmpty(amount)){
//			BigDecimal bAmount = new BigDecimal(amount);
//			CashBookEntry entry = cb.createEntry(System.currentTimeMillis() + "" + StringUtil.nextString(10));
//			entry.setAccountCode( ref);
//			entry.setSummary("Installment " + ref + " "+ tr.getDate());
//			entry.setTitle(entry.getSummary());
//			entry.setTotal(bAmount);
//			entry.setDateOfTransaction(new SimpleDateFormat("dd MMM yyyy").parse(tr.getDate()));
//			entry.save();
			String nm = new Date().getTime() + StringUtil.nextString(10);
			String code = StringUtil.nextString(10);
			String sql ="insert into WFS_FILE (clazz, dateCreated, lastModified, name, parent_id, summary, title, code, paymentMethod,total, accountCode, dateOfTransaction, DTYPE, absolutePath, size, status, commentable, dislikeit, likeit,ratable) values " +
					"('org.castafiore.accounting.CashBookEntry',now(),now(),'"+nm+"', " +
					"'/root/users/elieandsons/Applications/e-Shop/elieandsons/DefaultCashBook', " +
					"'Installment "+tr.getName()+"  "+tr.getDate()+"', 'Installment "+tr.getName()+"  "+tr.getDate()+"', '"+code+"', 'Standing Order', '"+tr.getAmount()+"', '"+tr.getName()+"',now() ,'CashBookEntry','/root/users/elieandsons/Applications/e-Shop/elieandsons/DefaultCashBook/"+nm+"',1,1,true,1,1, true);";
			
			SpringUtil.getBeanOfType(Dao.class).getSession().createSQLQuery(sql).executeUpdate();
			return true;
		}
		}catch(Exception e){
			e.printStackTrace();
		
		}
		return false;
		
	}
	
	
	public static List<Order> loadOrders(){
		List result = SpringUtil.getRepositoryService().executeQuery(new QueryParameters().setEntity(Order.class), Util.getRemoteUser());
		return result;
	}
	
	private static List<Order> searchFromAccNo(List<Order> orders, String accN){
		List<Order> result = new ArrayList<Order>();
		for(Order o : orders){
			if(accN.equalsIgnoreCase(o.getChequeNo())){
				result.add(o);
			}
		}
		return result;
	}
	
	public static Order searchFromCode(List<Order> orders, String code){
		if(code != null){
		for(Order o : orders){
			if(code.equalsIgnoreCase(o.getCode())){
				return o;
			}
		}
		}
		return null;
	}
	
	public static List<ReconcilationDTO> analyse(InputStream fstream, List<Order> orders){
		List<ReconcilationDTO> rs = new ArrayList<ReconcilationDTO>();
		try {
			
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			String sdate = "";
			while ((strLine = br.readLine()) != null) {
				//System.out.println(strLine);
				
				if(strLine != null){
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
	
	private static List<ReconcilationDTO> getDTO(String line, List<Order> orders){
		List<ReconcilationDTO> result = new ArrayList<ReconcilationDTO>();
		
		String[] parts = StringUtil.split(line, "|");
		if(parts != null){
			if(parts[0].equals("1")){
				try{
				int count = 0;
				for(String part : parts){
					if(part.contains("FS")){
						part =part.replace("FS ", "FS").replace("AND ", "").replace("-", " ").replace("REF:", "").replace(",", " ");
						String as[] = StringUtil.split(part, " ");
						for(String documentNumber : as){
							if(documentNumber != null && documentNumber.trim().length() > 0){
								ReconcilationDTO dto = new ReconcilationDTO();
								dto.setRefNumber(documentNumber.trim());
								result.add(dto);
							}
						}
					}
				}
				
				if(result.size() == 0){
					ReconcilationDTO dto = new ReconcilationDTO();
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
				Order order = null;
				if(result.size()> 0)
					order = searchFromCode(orders, result.get(0).getRefNumber());
				
				if(order != null){
					for(ReconcilationDTO dto : result){
						dto.setComment(dto.getComment() +  " Order found");
						dto.setOk(true);
					}
				}else{
					for(ReconcilationDTO dto : result){
						dto.setComment(dto.getComment() +  " Order not found");
						dto.setOk(false);
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
	
	private static void analyseMCBLine(String line, Container table, List<Order> orders){
		String[] parts = StringUtil.split(line, "|");
		//List<Order> orders = loadOrders();
		if(parts != null){
			//only if starts with 1
			if(parts[0].equals("1")){
				if(parts.length == 7){
					//case when it is complete
					String accountNumber = parts[2];
					String amount = parts[5];
					String documentNumber = parts[6];
					String client = parts[3];
					documentNumber = documentNumber.replace("FS ", "FS").replace("AND ", "").replace("-", " ");
					//List<Order> foAcc = searchFromAccNo(orders, accountNumber);
					String[] docParts = StringUtil.split(documentNumber, " ");
					if(docParts != null && docParts.length > 0){
						for(int i = 0; i < docParts.length; i++){
							String p = docParts[i];
							if(!p.startsWith("FS")){
								p = "FS" + p;
							}
							p.replace("/", "");
							String l2 = p.substring(p.length()-2);
							if(!p.endsWith("/" + l2)){
								p = p.replace(l2, "") + "/" + l2;
							}
							docParts[i] = p;
						}
						documentNumber = "";
						for(String s : docParts){
							Container tr = new EXContainer("", "tr");
							tr.addChild(new EXContainer("acc", "td").setText(accountNumber).addEvent(new ElieNSonsUtil(), DOUBLE_CLICK));
							tr.addChild(new EXContainer("client", "td").setText(client).addEvent(new ElieNSonsUtil(), DOUBLE_CLICK));
							tr.addChild(new EXContainer("amount", "td").setText(amount).addEvent(new ElieNSonsUtil(), DOUBLE_CLICK));
							tr.addChild(new EXContainer("code", "td").setText(s).addEvent(new ElieNSonsUtil(), DOUBLE_CLICK));
							String comment = "";
							boolean check = false;
							Order o = searchFromCode(orders, s);
							if(o != null){
								comment = "Plan found";
								check = true;
//								if(o.getChequeNo().equalsIgnoreCase(accountNumber)){
//									comment = comment + " account number matching";
//									
//									if(o.getTotal().doubleValue() == Double.parseDouble(amount)){
//										comment = comment + " amount matching";
//										
//									}
//								}
							}else{
								comment = "Plan not found";
							}
							tr.addChild(new EXContainer("", "td").setText(comment));
							tr.addChild(new EXContainer("", "td").addChild(new EXCheckBox(s,check)));
							table.addChild(tr);
							documentNumber = documentNumber + " " + s;
						}
						documentNumber = documentNumber.trim();
					}
					System.out.println(accountNumber + "|" + amount + "|" + documentNumber);
 
				}else if(parts.length == 6){
					String accountNumber = parts[2];
					String amount = parts[5];
					String client = parts[3];
					List<Order> fo = searchFromAccNo(orders, accountNumber);
					Container tr = new EXContainer("", "tr");
					tr.addChild(new EXContainer("acc", "td").setText(accountNumber).addEvent(new ElieNSonsUtil(), DOUBLE_CLICK));
					tr.addChild(new EXContainer("client", "td").setText(client).addEvent(new ElieNSonsUtil(), DOUBLE_CLICK));
					tr.addChild(new EXContainer("amount", "td").setText(amount).addEvent(new ElieNSonsUtil(), DOUBLE_CLICK));
					tr.addChild(new EXContainer("code", "td").setText("").addEvent(new ElieNSonsUtil(), DOUBLE_CLICK));
					String comment = "";
					if(fo.size() == 1){
						comment = "Plan found for acc no.";
					}else if(fo.size() > 1){
						comment = "more than 1 plan found for acc no.";
					}else{
						comment = "No plan found for acc no.";
					}
					for(Order o : fo){
						if(o.getTotal().doubleValue() == Double.parseDouble(amount)){
							comment = comment + " Amount matching for " + o.getCode();
							break;
						}
					}
					tr.addChild(new EXContainer("comment", "td").setText(comment));
					tr.addChild(new EXContainer("", "td").addChild(new EXCheckBox("")));
					table.addChild(tr);
					System.out.println(accountNumber + "|" + amount);
				}else{
					System.out.println(line);
				}
			}else{
				try{
					String sdate = parts[2];
					SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
					
					table.setAttribute("date", new SimpleDateFormat("dd MMM yyyy").format(format.parse(sdate)));
				}catch(Exception e){
					
				}
			}
		}
	}


	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}


	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		if(!(container instanceof StatefullComponent)){
			String text = container.getText();
			if(container.getName().equals("code")){
				container.setText("");
				EXAutoComplete c = new EXAutoComplete("iCode", text);
				List<Order> order =loadOrders();
				List<String> dict = new ArrayList<String>();
				for(Order o : order){
					dict.add(o.getCode());
				}
				c.setDictionary(dict);
				container.addChild(c.addEvent(this, BLUR));
			}else if(container.getName().equals("client")){
				container.setText("");
				EXAutoComplete c = new EXAutoComplete("iClient", text);
				List<Order> order =loadOrders();
				List<String> dict = new ArrayList<String>();
				for(Order o : order){
					if(o.getBillingInformation() != null)
					dict.add(o.getBillingInformation().getFirstName() +" " +  o.getBillingInformation().getLastName());
				}
				c.setDictionary(dict);
				container.addChild(c.addEvent(this, BLUR));
			}else if(container.getName().equals("acc")){
				container.setText("");
				EXAutoComplete c = new EXAutoComplete("iAcc", text);
				List<Order> order =loadOrders();
				List<String> dict = new ArrayList<String>();
				for(Order o : order){
					dict.add(o.getChequeNo());
				}
				c.setDictionary(dict);
				container.addChild(c.addEvent(this, BLUR));
				
			}else if(container.getName().equals("amount")){
				container.setText("");
				container.addChild(new EXInput("iAmount", text).addEvent(this, BLUR));
			}
		}else{
			StatefullComponent stf = (StatefullComponent)container;
			String value = stf.getValue().toString();
			Container parent = container.getParent();
			parent.getChildren().clear();
			if(container.getName().equals("iCode")){
				parent.setText(value);
			}else if(container.getName().equals("iClient")){
				parent.setText(value);
			}else if(container.getName().equals("iAcc")){
				parent.setText(value);
			}else if(container.getName().equals("iAmount")){
				parent.setText(value);
			}
		}
		
		return true;
	}


	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
