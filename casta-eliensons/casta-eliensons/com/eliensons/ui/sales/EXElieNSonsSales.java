package com.eliensons.ui.sales;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.castafiore.SimpleKeyValuePair;
import org.castafiore.designable.EXMiniCart;
import org.castafiore.inventory.orders.OrderService;
import org.castafiore.persistence.Dao;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.security.User;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.shoppingmall.reports.ReportsUtil;
import org.castafiore.shoppingmall.reports.ReportsUtil.Dat;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.dynaform.EXFieldSet;
import org.castafiore.ui.ex.form.AutoCompleteSource;
import org.castafiore.ui.ex.form.EXAutoComplete;
import org.castafiore.ui.ex.form.EXDatePicker;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.ui.ex.toolbar.EXToolBar;
import org.castafiore.ui.js.JArray;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.Value;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import com.eliensons.ui.plans.EXElieNSonsCatalogue;

public class EXElieNSonsSales extends EXContainer implements Event{

	public EXElieNSonsSales(String name)throws Exception {
		super(name, "div");
		try{
			
			//SpringUtil.getBeanOfType(Importer.class).doImport();
			update();
		}catch(Exception e){
			throw new UIException(e);
		}
		EXToolBar tb = new EXToolBar("tb");
		tb.addItem(new EXButton("generateInvoices", "Generate Forms"));
		
		tb.getDescendentByName("generateInvoices").addEvent(this, Event.CLICK);
		
		addChild(tb);
		
		
		EXFieldSet fs = new EXFieldSet("header", "Contract information", true);
		
		addChild(fs);
		List<User> users = SpringUtil.getSecurityService().getUsersForOrganization(Util.getLoggedOrganization());
		DefaultDataModel<Object> model = new DefaultDataModel<Object>();
		for(User u : users){
			model.addItem(u);
			
		}
		EXSelect select = new EXSelect("agent", model);
		select.setValue(MallUtil.getCurrentUser().getUser());
		fs.addField("Sales Agent", select);

		
		EXSelect posSelect = new EXSelect("pos", new OrderService().getPointOfSales());
		fs.addField("Point of sales : ", posSelect);
		EXAutoComplete complete = new EXAutoComplete("invoiceNumber", "");
		complete.setSource(new AutoCompleteSource() {
			
			@Override
			public JArray getSource(String param) {
				
				String sql = "select name from WFS_FILE where absolutePath like '/root/users/elieandsons/Applications/e-Shop/elieandsons/invoiceCodes/%' and status = " + Value.STATE_PUBLISHED + " and name like '%"+param+"%'";
				
JArray codes = null; 
				
				//if(codes == null){
					//Merchant merchant = MallUtil.getCurrentMerchant();
					//Directory dcodes = (Directory)merchant.getFile("invoiceCodes");
					//if(dcodes != null){
						//QueryParameters params = new QueryParameters();
						
						//params.setEntity(Value.class).addSearchDir("/root/users/elieandsons/Applications/e-Shop/elieandsons/invoiceCodes/").addOrder(Order.desc("value"));
						
						//List result = SpringUtil.getRepositoryService().executeQuery(params, Util.getRemoteUser());
List result = SpringUtil.getBeanOfType(Dao.class).getReadOnlySession().createSQLQuery(sql).list();
						codes = new JArray();
						for(Object o : result){
							codes.add( o.toString());
						}
						return codes;
					//}else{
						//codes =new  JArray();
					//}
				//}
				//return codes;
				
			}
		});
		//complete.addEvent(this, BLUR);
		fs.addField("Invoice Code : ", complete);
		complete.setStyle("width", "187px");
		//complete.getParent().addChild(new EXContainer("refresh", "a").setAttribute("href", "#").setAttribute("title", "Refresh generated forms").setText("<img src=\"icons-2/fugue/icons/arrow.png\">").addEvent(this, CLICK));
		
		EXDatePicker invoiceDate = new EXDatePicker("invoiceDate");
		invoiceDate.setStyle("width", "200px");
		fs.addField("Invoice Date : ", invoiceDate);
		
		
		DefaultDataModel<Object> promoModel = new DefaultDataModel<Object>();
		
		promoModel.addItem(new SimpleKeyValuePair("0", "No promotion"));
		promoModel.addItem(new SimpleKeyValuePair("3", "3 Month promotion"));
		EXSelect installmentDate = new EXSelect("installmentDate", promoModel); 
		installmentDate.setStyle("width", "200px");
		fs.addField("Promotion : ", installmentDate);
		
		DefaultDataModel<Object> fmodel = new DefaultDataModel<Object>();
		fmodel.addItem("Newspaper", "Radio", "Mouth to Ear" , "From Expo", "Web");
		EXSelect fromSelect = new EXSelect("fromSource", fmodel);
		fromSelect.setStyle("width", "200px");
		fs.addField("Source", fromSelect);
		
		
		
		addChild(new EXElieNSonsMiniCart("cart"));
		getDescendentOfType(EXMiniCart.class).setTemplateLocation("templates/EXElieNSonsMiniCart.xhtml");
		addChild(new EXElieNSonsCatalogue(""));
	}
	

	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}
	
	
	public static void update()throws Exception{
		
		
//		Session s = SpringUtil.getBeanOfType(Dao.class).getSession();
//		 
//		String query = "select value, absolutePath from WFS_FILE where dtype='Value' and name='applicationForm' and status != 81";
//		List l = s.createSQLQuery(query).setMaxResults(300).list();
//		Sheet foxpro= new HSSFWorkbook(new FileInputStream(new File("/usr/local/software/clients.xls"))).getSheetAt(0);
//		for(Object o : l){
//			Object[] arr = (Object[])o;
//			
//			String value = arr[0].toString();
//			String path = arr[1].toString();
//			
//			String orderPath = ResourceUtil.getParentPath(ResourceUtil.getParentPath(path));
//			try{
//			org.castafiore.shoppingmall.checkout.Order order = (org.castafiore.shoppingmall.checkout.Order)SpringUtil.getRepositoryService().getFile(orderPath, Util.getRemoteUser());
//			Map<String, String> map = BillingInformation.buildMap(value);
//			
//			
//			
//			
//			searchProperty(map, order, s, foxpro);
//			String up = "update WFS_FILE set value =?, status=81 where absolutePath =?";
//			s.createSQLQuery(up).setParameter(0, BillingInformation.buildString(map)).setParameter(1, path).executeUpdate();
//			System.out.println(orderPath + "/" + order.getCode());
//			}catch(FileNotFoundException e){
//				System.out.println("Error:" + path);
//				s.createSQLQuery("delete from WFS_FILE where absolutePath like '"+orderPath+"%'").executeUpdate();
//			}
//			
//		}
	}
	
	
	
	
	public static void searchProperty(Map<String,String> map, org.castafiore.shoppingmall.checkout.Order order, Session session, Sheet foxpro){
		map.put("fsNumber", order.getCode());
		map.put("firstPaymentDate", new SimpleDateFormat("dd/MMM/yyyy").format(order.getStartInstallmentDate()));
		map.put("monthlyInstallment", order.getInstallment().toPlainString());
		map.put("date", new SimpleDateFormat("dd/MMM/yyyy").format(order.getDateOfTransaction()));
		Calendar cal = Calendar.getInstance();
		cal.setTime(order.getDateOfTransaction());
		cal.add(Calendar.MONTH, 6);
		
		map.put("effDate", new SimpleDateFormat("dd/MMM/yyyy").format(cal.getTime()));
		
		
		for(int i = 1; i <=6;i++){
			String name = map.get("c" + i + "name");
			
			if(name != null && name.length() > 0 && !name.equalsIgnoreCase("null")){
				StringBuilder b = new StringBuilder();
				for(int j =1; j <=14;j++){
					String s = map.get(("c" + i )+ (j + ""));
					if(s != null && s.trim().length() > 0 && !s.equalsIgnoreCase("null")){
						b.append(s);
					}
				}
				map.put("c" + 1 + "Age", b.toString());
			}
		}
		
		Object totalPayments = session.createSQLQuery("select SUM(total) from WFS_FILE where dtype='CashBookEntry' and accountCode ='"+order.getCode()+"'").uniqueResult();
		
		if(totalPayments == null){
			totalPayments = "0";
		}
		
		map.put("totalPayments", totalPayments.toString());
		
		Dat d = ReportsUtil.calculate(Double.parseDouble(totalPayments.toString()), order.getStartInstallmentDate(), order.getInstallment().doubleValue());
		
		map.put("arrears", d.arrear + "");
		map.put("nextPaymentDate", new SimpleDateFormat("dd/MMM/yyyy").format(d.nextPaymentDate.getTime()));
		map.put("status", order.getStatus() + "");
		try{
			System.out.println("Plan : " + order.getEntries().get(0).getTitle());
			map.put("plan", order.getEntries().get(0).getTitle());
		}catch(Exception e){
			
		}
		
		try{
			String[] dep = getDependents(order.getCode(), foxpro);
			if(dep != null){
				for(int i =0; i < dep.length; i++){
					map.put("c" + (i+1) + "name", dep[i]);
				}
			}
		}catch(Exception e){
			
		}
	}
	
	private static String[] getDependents(String code, Sheet s){
		for(int i =0; i < s.getLastRowNum();i++){
			try{
				String c = s.getRow(i).getCell(0).getStringCellValue();
				if(code.equalsIgnoreCase(c)){
					String plan = s.getRow(i).getCell(12).getStringCellValue();
					String detail = s.getRow(i).getCell(13).getStringCellValue();
					
					String[] dep = new String[6];
					
					for(int d = 0; d < dep.length;d++ ){
						try{
						Cell cell = s.getRow(i).getCell(d+26);
						if(cell != null && cell.getStringCellValue() != null){
							dep[d] = cell.getStringCellValue();
						}else{
							dep[d] = "";
						}
						}catch(Exception e){
							e.printStackTrace();
							//System.out.println("error :" + e.getMessage());
							dep[d] = "";
						}
					}
					
					//return "Plan " + plan + " " + detail;
					return dep;
				}
			}catch(Exception e){
				
			}
		}
		
		return null;
	}
	

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		if(container.getName().equalsIgnoreCase("invoiceNumber")){
			setAttribute("timeIn", System.currentTimeMillis() + "");
			return true;
		}
		
		EXPanel panel = new EXPanel("", "Generate Invoices");
		
		
		panel.setBody(new EXElieNSonsPreprintInvoice(""));
		container.getAncestorOfType(PopupContainer.class).addPopup(panel.setStyle("z-index", "4000").setStyle("width", "500px"));
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
