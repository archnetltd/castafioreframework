package org.castafiore.shoppingmall.reports;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.castafiore.SimpleKeyValuePair;
import org.castafiore.inventory.orders.OrderService;
import org.castafiore.persistence.Dao;
import org.castafiore.shoppingmall.crm.OrdersIndexer;
import org.castafiore.shoppingmall.orders.OrdersWorkflow;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.EXGrid;
import org.castafiore.ui.ex.dynaform.EXFieldSet;
import org.castafiore.ui.ex.form.EXCheckBox;
import org.castafiore.ui.ex.form.EXDatePicker;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.ui.ex.toolbar.EXToolBar;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.utils.StringUtil;
import org.hibernate.engine.SessionFactoryImplementor;

public class EXReportsPanel extends EXContainer implements Event{

	public EXReportsPanel(String name) {
		super(name, "div");
		
		
		
		EXFieldSet subMenu = new EXFieldSet("filter", "Filter", false);
		subMenu.addField("From : ",new EXDatePicker("from") );
		subMenu.addField("To : ",new EXDatePicker("to") );
		subMenu.addField("Filter by : ",new EXSelect("filterBy", new DefaultDataModel<Object>().addItem("Date of transaction", "Date of data entry")) );
		subMenu.getField("from").setRawValue("");
		subMenu.getField("to").setRawValue("");
		
		
		
		OrdersWorkflow wf = SpringUtil.getBeanOfType(OrdersWorkflow.class);
		int[] istates = wf.getAvailableStates();
		
		
		
		EXGrid grid = new EXGrid("ii", 2, istates.length +1);
		
		
		grid.getCell(0, 0).addChild(new EXCheckBox("c_*")).setStyle("padding", "0px").setStyle("margin", "0px").setStyle("width", "15px");
		grid.getCell(1, 0).setText("All");
		int count = 1;
		
		for(int i : istates){
			grid.getCell(0, count).addChild(new EXCheckBox("c_" + i)).setStyle("padding", "0px").setStyle("margin", "0px").setStyle("width", "15px");
			grid.getCell(1, count).setText(wf.getStatus(i));
			count++;
		}
		
		
		subMenu.addItem(new EXContainer("", "label").setText("Status :"), grid, false);
		
		
		
		addChild(subMenu);

		
		addChild(new EXToolBar("btnCtn"));
		
		
		Container salesBySalesman = new EXButton("salesBySalesman", "button").setText("Sales by salesman").addEvent(this, Event.CLICK);
		getChild("btnCtn").addChild(salesBySalesman);
		
		Container salesByPOS = new EXButton("salesByPOS", "button").setText("Sales by POS").addEvent(this, Event.CLICK);
		getChild("btnCtn").addChild(salesByPOS);
		
		
		Container salesByMonth = new EXButton("salesByMonth", "button").setText("Sales by Month").addEvent(this, Event.CLICK);
		getChild("btnCtn").addChild(salesByMonth);
		
		
		Container dailySales = new EXButton("dailySales", "button").setText("Daily Sales").addEvent(this, Event.CLICK);
		getChild("btnCtn").addChild(dailySales);
		
		Container sourceSales = new EXButton("sourceSales", "button").setText("Sales by source").addEvent(this, Event.CLICK);
		getChild("btnCtn").addChild(sourceSales);
		
		
		Container latePayments = new EXButton("latePayments", "button").setText("Late Payments").addEvent(this, Event.CLICK);
		getChild("btnCtn").addChild(latePayments);
		
		Container deathReport = new EXButton("deathReport", "button").setText("Death Report").addEvent(this, Event.CLICK);
		getChild("btnCtn").addChild(deathReport);
		
		
		Container index = new EXButton("index", "button").setText("Index Orders").addEvent(this, Event.CLICK);
		getChild("btnCtn").addChild(index);
		
		
		addChild(new EXContainer("fullExport", "a").setText("Full Customer export").addEvent(this, CLICK).setAttribute("href", ResourceUtil.getMethodUrl(this, "fullExport", "state","1")));
		
		Container body = new EXContainer("body", "div");
		addChild(body);
		
		
		
		
		
		init();
	}
	static String[] fields = new String[]{
		"timeIn", "timeOut","salesAgent","pos","date","fsNumber","plan",
		"ctitle","cfullName","cSurname","cidnumber","cdob","cresi","cresi2","telHome","telOffice","cell","cemail",
		"ptitle","pfullName","pSurname","pidnumber","pdob","presi", "presi2","pstatus","ptelhome", "pcell",
		"stitle","sfullName","sSurname","sdob","sgender","sstatus","sidnumber",
		 "accountNumber", "bankName", "email","effDate",
		"firstPaymentDate","nextPaymentDate","totalPayments","arrears","status", "costOfFuneral", "dateTerminated", "dateCancelled","monthlyInstallment", 
		 
		
		"c1name","c1Age","c1gender","c2name","c2Age","c2gender","c3name","c3Age","c3gender","c4name","c4Age","c4gender","c5name","c5Age","c5gender","c6name","c6Age","c6gender"
		,"cmobile","pgender"
	};
	
	static String[] labels = new String[]{
		"Time In", "Time Out","Sales Agent","POS","Trans date","FS Number","Plan",
		"Contact title","Contact Name","Contact Surname","Contact Id","Contact DOB","Contact address 1","Contact address 2","Contact Tel","Contact tel office","Contact cell","Contact email",
		"Primary title","Primary Name","Primary Surname","Primary ID","Primary DOB","Primary address 1","Primary address 2","Primary Marital Status","Primary Tel", "Primary Cell",
		"Spouse title","Spouse name","Spouse Surname","Spouse DOB","Spouse gender","Spouse status","Spouse Id Number",
		"Account Number", "Bank Name", "Email","effective Date",
		"First Payment date","Next Payment date","Total Payments","Arrears","Status", "Cost of Funeral", "date Terminated", "Date cancelled","Installment", 
		  
		
		"Child 1","Age/Id","Gender",
		"Child 2","Age/Id","Gender",
		"Child 3","Age/Id","Gender",
		"Child 4","Age/Id","Gender",
		"Child 5","Age/Id","Gender",
		"Child 6","Age/Id","Gender",
		"cmobile","pgender"
		
	};
	
	
	public void indexOrders(){
		try{
			OrderService.reIndex();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public InputStream fullExport(String state)throws Exception{
		//String sqlup = "select absolutepath, code, dateOfTransaction, "
		String sql = "select dateCreated, value from WFS_FILE where dtype='Value' and name='applicationForm'";
		HSSFWorkbook w = new HSSFWorkbook();
		Sheet s = w.createSheet("Export Sheet");
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy");
		Date startDate = (Date)getDescendentOfType(EXFieldSet.class).getField("from").getValue();
		Date endDate= (Date)getDescendentOfType(EXFieldSet.class).getField("to").getValue();
		
		
		
		SessionFactoryImplementor impl = (SessionFactoryImplementor)SpringUtil.getBeanOfType(Dao.class).getReadOnlySession().getSessionFactory();
		//List l = SpringUtil.getBeanOfType(Dao.class).getReadOnlySession().createSQLQuery(sql).list();
		Connection connection = impl.getConnectionProvider().getConnection();
		
		ResultSet rs = connection.createStatement().executeQuery(sql);
		
		int rc = 1;
		Row fr = s.createRow(0);
		fr.createCell(0).setCellValue("Date created");
		for(String f : labels){
			fr.createCell(fr.getLastCellNum()).setCellValue(f);
		}
		while(rs.next()){
			Date dateCreated = rs.getDate(1);
			String val = rs.getString(2);
			Map<String,String> data = new HashMap<String, String>();
			String[] as = StringUtils.splitByWholeSeparator(val, "-:;:-");
			if(as == null){
				continue;
			}
			for(String kv : as){
				String[] akv = StringUtil.split(kv, ":");
				if(akv != null && akv.length >1 && akv[0] != null && akv[0].length() > 0){
					data.put(akv[0], akv[1]);
				}
			}
			Date effective = null;
			try{
			effective =format.parse(data.get("effDate"));
			}catch(Exception e){
				continue;
			}
			Calendar c_ = Calendar.getInstance();
			c_.setTime(effective);
			c_.add(Calendar.MONTH, -6);
			Date transDate = c_.getTime();
			
			if(! (transDate.after(startDate) && transDate.before(endDate)))
				continue;
			
			Row r = s.createRow(rc);rc++;
			int cc=1;
			Cell dc = r.createCell(0);
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(dateCreated.getTime());
			dc.setCellValue(cal);
			for(String f : fields){
				Cell c = r.createCell(cc);cc++;
				if(data.containsKey(f)){
					String v = data.get(f);
					if(v != null && v.trim().length() > 0&& !v.equalsIgnoreCase("null"))
					{
						if(f.equalsIgnoreCase("status"))
						{
							try{
							c.setCellValue(SpringUtil.getBeanOfType(OrdersWorkflow.class).getStatus(Integer.parseInt(v)));
							}catch(Exception e){
								c.setCellValue(v);
							}
						}else{
							c.setCellValue(v);
						}
					}
					else
						c.setCellValue("");
				}else{
					c.setCellValue("");
				}
				
			}
		}
		
		rs.close();
		connection.clearWarnings();
		FileOutputStream out=new FileOutputStream(new File("/usr/local/software/tb.xls"));
		w.write(out);
		out.flush();
		out.close();
		
		return new FileInputStream(new File("/usr/local/software/tb.xls"));

	}
	
	public void init(){
//		EXDatePicker startDate = (EXDatePicker)getDescendentByName("from");
//		EXDatePicker endDate = (EXDatePicker)getDescendentByName("to");
//		String filterType = getDescendentOfType(EXSelect.class).getValue().toString();
//		Date sd = new Date();
//		sd.setDate(1);
//		startDate.setValue(sd);
//		
//		Date ed = new Date();
//		ed.setDate(1);
//		int month = new Date().getMonth();
//		if(month == 11){
//			month = 0;
//			ed.setYear(new Date().getYear() + 1);
//			
//		}else{
//			month = month + 1;
//		}
//		
//		ed.setMonth(month);
//		
//		endDate.setValue(ed);
		
		//Container table =  ReportsUtil.getSalesBySalesmanReport(sd,ed,filterType);
		//table.addClass("ex-content");
		getChild("body").getChildren().clear();
		//getChild("body").setRendered(false).addChild(table.setAttribute("width", "100%"));
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask(container.getAncestorOfType(EXReportsPanel.class)).makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		if(container.getName().equalsIgnoreCase("index")){
			indexOrders();
			return true;
		}
		if(container.getName().equalsIgnoreCase("fullExport")){
			return true;
		}
		
		EXDatePicker startDate = (EXDatePicker)getDescendentByName("from");
		EXDatePicker endDate = (EXDatePicker)getDescendentByName("to");
		String filterType = getDescendentOfType(EXSelect.class).getValue().toString();
		Date sd =  (Date)startDate.getValue();
		Date ed =  (Date)endDate.getValue();
		try{
		if(sd == null){
			sd = new SimpleDateFormat("dd MM yyyy").parse("12 12 1900");
		}
		
		if(ed == null){
			ed = new SimpleDateFormat("dd MM yyyy").parse("12 12 3000");
		}
		}catch(Exception e){
			
		}
		Container table ;
		if(container.getName().equalsIgnoreCase("salesBySalesman")){
			table = ReportsUtil.getSalesBySalesmanReport(sd,ed,filterType);
		}else if(container.getName().equalsIgnoreCase("salesByPOS")){
			table = ReportsUtil.getSalesByPOSReport(sd, ed,filterType);
		}else if(container.getName().equalsIgnoreCase("dailySales")){
			table = ReportsUtil.getDailySalesReport(sd, ed,filterType);
		}else if(container.getName().equalsIgnoreCase("latePayments")){
			table = ReportsUtil.getLatePayments();
		}else if(container.getName().equalsIgnoreCase("sourceSales")){
			table = ReportsUtil.getSalesBySourceReport(sd, ed,filterType);
		}else if(container.getName().equalsIgnoreCase("deathReport")){
			table = ReportsUtil.getDeathReport(sd, ed, filterType);
		}else{
			table = ReportsUtil.getSalesByMonth(sd, ed,filterType);
		}
		
		
		
		getChild("body").getChildren().clear();
		getChild("body").setRendered(false).addChild(table.setAttribute("width", "100%"));
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
