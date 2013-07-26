package org.castafiore.shoppingmall.orders;

import groovy.lang.Binding;
import groovy.lang.GroovyCodeSource;
import groovy.lang.GroovyShell;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.internet.MimeMessage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang.ArrayUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.castafiore.catalogue.Product;
import org.castafiore.persistence.Dao;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.searchengine.back.EXWindow;
import org.castafiore.security.User;
import org.castafiore.shoppingmall.checkout.BillingInformation;
import org.castafiore.shoppingmall.checkout.DeliveryOptions;
import org.castafiore.shoppingmall.checkout.EXCheckoutPanel;
import org.castafiore.shoppingmall.checkout.Order;
import org.castafiore.shoppingmall.checkout.OrderEntry;
import org.castafiore.shoppingmall.checkout.SalesOrderEntry;
import org.castafiore.shoppingmall.checkout.ShippingInformation;
import org.castafiore.shoppingmall.delivery.Delivery;
import org.castafiore.shoppingmall.delivery.EXDeliveryMethods;
import org.castafiore.shoppingmall.merchant.FinanceUtil;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.shoppingmall.payment.EXPayment;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Application;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.dynaform.DynaForm;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXColorPicker;
import org.castafiore.ui.ex.form.EXDatePicker;
import org.castafiore.ui.ex.form.EXDateTimePicker;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXPassword;
import org.castafiore.ui.ex.form.EXTextArea;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.utils.IOUtil;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.omg.DynamicAny.DynAnyOperations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public  class OrdersUtil  implements Event{
	
	
	private  Properties prop = new Properties();
	
	
	public OrdersUtil(){
		
		try 
		{
			prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(getResourceClassPath() + "/workflow.properties"));
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	public String getResourceClassPath(){
		return "org/castafiore/shoppingmall/orders";
	}
	
	
	
	public  String getStatus(int status){
		return prop.getProperty(status + ".label");
	}
	
	

		
		@Override
		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			
			ClientProxy p = new ClientProxy("#mainLoading").setStyle("display", "none");
			container.mergeCommand(p);
		}
		
		@Override
		public boolean ServerAction(Container container, Map<String, String> request)
				throws UIException {
			
			if(container.getName().equalsIgnoreCase("buyCredits")){
				container.getAncestorOfType(PopupContainer.class).addPopup(new EXPayment("payments"));
			}
			
			try{
				
				EXCheckoutPanel panel = container.getAncestorOfType(EXCheckoutPanel.class);
				if(panel != null){
					panel.getDescendentByName("title").getDescendentByName("back").setDisplay(false);
				}
				
				SpringUtil.getBeanOfType(OrdersWorkflow.class).executeXML(container);
			}catch(Exception e){
				throw new UIException(e);
			}
			return true;
		}
		
		@Override
		public void ClientAction(ClientProxy container) {
			ClientProxy p = new ClientProxy("#mainLoading").setStyle("display", "block");
			
			container.mergeCommand(p).makeServerRequest(this);
			if(container.getAncestorOfType(EXWindow.class) != null){
				container.mask(container.getAncestorOfType(EXWindow.class), "Processing");
			}
			
			
		}

	
	
	/**
	 * actors
	 * customer
	 * merchant
	 * archnet
	 * @param status
	 * @param actor
	 * @return
	 */
	public  void addButtons(Container parent,int status, String actor, String order){
		String key = "if." + status + "." + actor;
		
		
		//Merchant merchant = MallUtil.getMerchant(order.getOrderedFrom());
		//String plan = merchant.getPlan();
		if(actor.equalsIgnoreCase("customer")){
			key = "if." + status + "." + actor + ".free";
		}
		if(prop.containsKey(key)){
			String property = prop.getProperty(key);
			String[] parts = StringUtil.split(property, ",");
			for(int i =0; i < parts.length; i ++){
				String s = parts[i];
				String text = prop.getProperty(s);
				Container button = new EXContainer(s, "button");
				button.setText(text);
				button.setAttribute("path", order);
				button.addEvent(new OrdersUtil(), Event.CLICK);
				button.setAttribute("actor", actor);
				parent.addChild(button);
			}
		}
	}
	
	public static Order getOrderByCode(String code){
		QueryParameters params = new QueryParameters().setEntity(Order.class).addRestriction(Restrictions.eq("code", code));
		
		List result = SpringUtil.getRepositoryService().executeQuery(params, Util.getRemoteUser());
		if(result.size() > 0){
			return (Order)result.get(0);
		}else{
			return null;
		}
	}
	
	
	public static Order getOrderByCheque(String cheque){
		QueryParameters params = new QueryParameters().setEntity(Order.class).addRestriction(Restrictions.eq("chequeNo", cheque));
		
		List result = SpringUtil.getRepositoryService().executeQuery(params, Util.getRemoteUser());
		if(result.size() > 0){
			return (Order)result.get(0);
		}else{
			return null;
		}
	}
	
	public  String getColor(int status){
		return prop.getProperty(status + ".color");
	}
	
	
	public static void backToList (Container source)throws Exception{
		EXOrdersPanel panel =source.getAncestorOfType(EXOrdersPanel.class);
		if(panel != null){
			panel.showOrderList();
		}
	}
	
	public static void delete (Container source)throws Exception{
		
		String path = source.getAttribute("path");
		Order order = (Order)SpringUtil.getRepositoryService().getFile(source.getAttribute("path"), Util.getRemoteUser());
		String code = order.getCode();
		String s1= "delete from WFS_FILE where absolutePath like '"+path+"%'";
		String s2= "delete from WFS_FILE where code = '"+code+"' and DTYPE ='Account'";
		String s3 ="delete from WFS_FILE where absolutePath like '/root/users/elieandsons/Applications/e-Shop/elieandsons/DefaultCashBook/FS00032/12%'";
		String s4 = "delete from WFS_FILE where subscriber='"+code+"' and DTYPE = 'MerchantSubscription'";
		
		
		Session s = SpringUtil.getBeanOfType(Dao.class).getSession();
		
		s.createSQLQuery(s1).executeUpdate();
		s.createSQLQuery(s3).executeUpdate();
		s.createSQLQuery(s2).executeUpdate();
		s.createSQLQuery(s4).executeUpdate();
		//delete everything in the order
		//delete subscriber
		//delete entries in cash book
		
		
		
	}
	
	
	public static void delivery(Container source)throws Exception{
		Order order = (Order)SpringUtil.getRepositoryService().getFile(source.getAttribute("path"), Util.getRemoteUser());
		EXDeliveryMethods method = new EXDeliveryMethods("delivery");
		method.setOrder(order);
		method.setSource(source);
		source.getAncestorOfType(EXInvoice.class).addPopup(method);
	}
	
	
	public static void sendDelivery(Container source)throws Exception{
		Order order = (Order)SpringUtil.getRepositoryService().getFile(source.getAttribute("path"), Util.getRemoteUser());
		Merchant merchant = MallUtil.getMerchant(order.getOrderedFrom());
		if(merchant.isUps()){
			generateUPSDeliveryExcelSheet(source.getRoot(), order, merchant);
		}
	}
	
	public static void generateUPSDeliveryExcelSheet( Application app, Order order, Merchant merchant)throws Exception{
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("org/castafiore/shoppingmall/delivery/ups.properties");
		Workbook wb = new HSSFWorkbook(in); 
		Sheet sheet = wb.getSheetAt(0);
		
		Delivery delivery = order.getDelivery();
		ShippingInformation shipping = order.getShippingInformation();
		DeliveryOptions options = merchant.getDeliveryOptions();
		User contact = SpringUtil.getSecurityService().loadUserByUsername(merchant.getUsername());
		
		
		String companyName = merchant.getTitle();
		String companyAddress = merchant.getAddressLine1() + " " + merchant.getAddressLine2();
		String companyCity = merchant.getCity();
		String companyPhone = merchant.getPhone();
		String companyFax = merchant.getFax();
		String companyContact = contact.toString();
		String readyTime = "";
		String agreedTime = "";
		
		String recieverCompany = shipping.getCompany();
		String recieverContact = shipping.getFirstName() + " " + shipping.getLastName();
		String recieverAddress =shipping.getAddressLine1() + " " + shipping.getAddressLine2();
		String recieverCity = shipping.getCity();
		String recieverPhone = shipping.getPhone();
		String recieverFax = shipping.getFax();
		
		String serviceOption = "Next Day";
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, 1);
		Date deliveryDate = c.getTime();
		String packageType ="Non-Documents";
		double weight= delivery.getWeight().doubleValue();
		
		
		String transportPayer = options.getTransportPayer();
		if(transportPayer.equals("Reciever")){
			transportPayer = "Reciever";
		}
		String paymentMode = order.getPaymentMethod();
		String confirmationPrint = "UPS ASC";
		
		if(paymentMode.equals("Cheque")){
			String chequeNo = order.getChequeNo();
			sheet.getRow(50).getCell(6).setCellValue(chequeNo);
			
		}
		
		
		
		sheet.getRow(7).getCell(6).setCellValue(companyName);
		sheet.getRow(9).getCell(6).setCellValue(companyAddress);
		sheet.getRow(11).getCell(6).setCellValue(companyCity);
		sheet.getRow(13).getCell(6).setCellValue(companyContact);
		sheet.getRow(15).getCell(6).setCellValue(readyTime);
		
		sheet.getRow(11).getCell(28).setCellValue(companyPhone);
		sheet.getRow(13).getCell(28).setCellValue(companyFax);
		sheet.getRow(15).getCell(28).setCellValue(agreedTime);
		
		
		sheet.getRow(20).getCell(6).setCellValue(recieverCompany);
		sheet.getRow(22).getCell(6).setCellValue(recieverAddress);
		sheet.getRow(24).getCell(6).setCellValue(recieverCity);
		sheet.getRow(26).getCell(6).setCellValue(recieverContact);
		sheet.getRow(24).getCell(28).setCellValue(recieverPhone);
		sheet.getRow(26).getCell(28).setCellValue(recieverFax);
		
		sheet.getRow(31).getCell(2).setCellValue(serviceOption);
		sheet.getRow(33).getCell(6).setCellValue(deliveryDate);
		
		sheet.getRow(39).getCell(2).setCellValue(packageType);
		
		sheet.getRow(41).getCell(6).setCellValue(weight);
		
		sheet.getRow(46).getCell(6).setCellValue(transportPayer);
		
		sheet.getRow(48).getCell(6).setCellValue(paymentMode);
		
		
		
		
		sheet.getRow(55).getCell(2).setCellValue(confirmationPrint);
		
		
		if(merchant.isUps()){
			//collect on delivery = true
//			sheet.getRow(31).getCell(34).setCellValue("Yes");
//			//37,31 collect on delivery
//		
//				sheet.getRow(31).getCell(33).setCellValue(order.getTotal().doubleValue());
//				sheet.getRow(31).getCell(37).setCellValue("Cash");
//				sheet.getRow(31).getCell(33).setCellValue(order.getTotal().add(order.getDelivery().getPrice()).doubleValue());
//				sheet.getRow(31).getCell(41).setCellValue("Shipper");
			
			//31,33 amount to collect
			
			//31,37 payment mode
			
			
			//31,39 cheque no
			//31,41 cod fee payer = "Receiver" or "Shipper"
			
		}
		
		
		BinaryFile bf = order.createFile("delivery.xls", BinaryFile.class);
		//bf.write(in)
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		wb.write(out);
		
		bf.write(out.toByteArray());
		
		bf.save();
		
		try{
			JavaMailSender sender = SpringUtil.getBeanOfType(JavaMailSender.class);
			MimeMessage message = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setSubject("New delivery by " + merchant.getTitle());
			helper.setFrom(merchant.getEmail());
			
			helper.setTo(new String[]{"kureem@gmail.com", merchant.getEmail()});
			
			String contextPath = app.getContextPath();
			String serverPort = app.getServerPort();
			String servaerName = app.getServerName();
			
			String templateLocation = ResourceUtil.getDownloadURL("ecm", bf.getAbsolutePath());
			if(!contextPath.startsWith("/")){
				contextPath = "/" + contextPath;
			}
			if(contextPath.endsWith("/") && templateLocation.startsWith("/")){
				templateLocation = templateLocation.substring(1);
			}
			if(!contextPath.endsWith("/") && ! templateLocation.startsWith("/")){
				templateLocation = "/" + templateLocation;
			}
			//String url = "";
			if(!templateLocation.startsWith("http")){
				templateLocation = "http://" + servaerName + ":" + serverPort  + contextPath + "" + templateLocation;
			}
			
			String link ="<a href=" + templateLocation + ">" + templateLocation + "</a>";
			helper.setText("Please download the reservation for the new delivery from the link below <br><br>" + link , true);
			
			sender.send(message);
		}catch(Exception e){
			e.printStackTrace();
		}
		//send mail to ups
	}
	
	
	
	public static List<String> createProducts(InputStream xls)throws Exception{
		List<String> result = new ArrayList<String>();
		Workbook wb = new HSSFWorkbook(xls); 
		Sheet sheet = wb.getSheetAt(0);
		int index = 1;
		///root/users/200030767/Applications/e-Shop/Configs
		String scriptPath = "/root/users/" + Util.getLoggedOrganization() + "/Applications/e-Shop/Configs/ProductScript.groovy";
		
		GroovyShell shell = null;
		GroovyCodeSource source = null;
		if(SpringUtil.getRepositoryService().itemExists(scriptPath)){
			BinaryFile bf = (BinaryFile)SpringUtil.getRepositoryService().getFile(scriptPath, Util.getRemoteUser());
			shell = new GroovyShell();
			source = new GroovyCodeSource(new java.io.File(bf.getUrl()));
		}
		
		
		
		while(true){
			try{
				Row row = sheet.getRow(index);
				if(row.getCell(0) != null && row.getCell(0).getStringCellValue() != null){
					result.add(createRow(row, source, shell));
				}else{
					break;
				}
				index++;
			}catch(Exception e){
				break;
			}
		}
		
		return result;
		
	}
	
	
	public static String createRow(Row row, GroovyCodeSource source, GroovyShell shell){
		try{
			Product p = MallUtil.getCurrentUser().getMerchant().createProduct();
			p.setStatus(Product.STATE_DRAFT);
			if(source != null && shell != null){
				
				shell.getContext().getVariables().clear();
				shell.setVariable("row", row);
				shell.setVariable("product", p);
				
				shell.evaluate(source);
				
			}else{
			
				String code = row.getCell(0).getStringCellValue();
				String title = row.getCell(1).getStringCellValue();
				String detail = row.getCell(2).getStringCellValue();
				double price = row.getCell(3).getNumericCellValue();
				String category = row.getCell(4).getStringCellValue();
				String sCategory = row.getCell(5).getStringCellValue();
				double weight = row.getCell(6).getNumericCellValue();
				double length = row.getCell(7).getNumericCellValue();
				double width  = row.getCell(8).getNumericCellValue();
				double height  = row.getCell(9).getNumericCellValue();
				
				
				
				p.setTitle(title);
				p.setCode(code);
				p.setSummary(detail);
				p.setTotalPrice(new BigDecimal(price));
				p.setTaxRate(new BigDecimal(15));
				p.setCategory(category);
				p.setSubCategory(sCategory);
				p.setWeight(new BigDecimal(weight));
				p.setLength(new BigDecimal(length));
				p.setWidth(new BigDecimal(width));
				p.setHeight(new BigDecimal(height));
				p.createImage("Image 1", ResourceUtil.getDownloadURL("ecm", "/root/users/" + Util.getRemoteUser() + "/Media/Images/" + code + ".jpg"));
			}
			
			p.save();
		}catch(Exception e){
			return e.getMessage();
		}
		return "ok";
	}
	

	
	public  void executeXML(Container source, String actor, NodeList nl)throws Exception{
		String sname = source.getName();
		NodeList nodeList = null;
		
		if(nl == null){
			InputStream xml = Thread.currentThread().getContextClassLoader().getResourceAsStream(getResourceClassPath() + "/" + sname + ".xml");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(xml);
			doc.getDocumentElement().normalize();
			nodeList = doc.getChildNodes().item(0).getChildNodes();
		}else{
			nodeList = nl;
		}
		BillingInformation customer = null;
		Order order = null;
		Merchant merchant = null;
		if (source.getName().equals("RejectByMerchant")){
			order = (Order)SpringUtil.getRepositoryService().getFile(source.getAttribute("path"), Util.getRemoteUser());
			List<OrderEntry> entries = order.getEntries();
			for(OrderEntry entry : entries){
				String code = entry.getProductCode();
				BigDecimal qty = entry.getQuantity();
				Product p = (Product)SpringUtil.getRepositoryService().executeQuery(new QueryParameters().setEntity(Product.class).addRestriction(Restrictions.eq("code", code)), Util.getRemoteUser()).get(0);
				BigDecimal currentQty = p.getCurrentQty();
				if(currentQty != null){
					p.setCurrentQty(currentQty.add(qty));
					p.save();
				}
			}
		}
		for(int i = 0; i < nodeList.getLength(); i ++){
			Node n = nodeList.item(i);
			
			String name = n.getNodeName();
			if(name.equals("status")){
				String status = n.getTextContent();
				if("Delete".equalsIgnoreCase(status)){
					if(order == null)
						order = (Order)SpringUtil.getRepositoryService().getFile(source.getAttribute("path"), Util.getRemoteUser());
					Directory parent = order.getParent();
					order.remove();
					parent.save();
				}else{
					if(order == null)
						order = (Order)SpringUtil.getRepositoryService().getFile(source.getAttribute("path"), Util.getRemoteUser());
					order.setStatus(Integer.parseInt(status));
					order.save();
				}
			}else if(name.equals("mails")){
				if(order == null)
					order = (Order)SpringUtil.getRepositoryService().getFile(source.getAttribute("path"), Util.getRemoteUser());
				
				if(merchant == null)
					merchant = MallUtil.getCurrentMall().getMerchant(order.getOrderedFrom());
				if(customer == null)
					customer = order.getBillingInformation();//SpringUtil.getSecurityService().loadUserByUsername(order.getOrderedBy());
				sendMails(n.getChildNodes(), order, merchant, customer);
			}else if(name.equals("display")){
				if(merchant == null)
					merchant = MallUtil.getCurrentMall().getMerchant(order.getOrderedFrom());
				if(customer == null)
					customer = order.getBillingInformation();
				String textContent = n.getTextContent();
				Map<String, String> rep = new HashMap<String, String>();
				rep.put("${customer.mail}", customer.getEmail());
				rep.put("$customer", customer.getFirstName() + " " + customer.getLastName());
				rep.put("$merchant", merchant.getCompanyName());
				//rep.put("$invoicenumber", order.getCode());
				
				rep.put("${merchant.mail}", merchant.getEmail());
				rep.put("$message", "UPS has been notified about the delivery. An agent will confirm the delivery to you as soon as possible");
				if(!merchant.isUps()){
					rep.put("$message", "Please make the necessary arrangement for the delivery");
				}
				//rep.put("$order", buildOrder(order, merchant, customer));
				//rep.put("$administrator.mail", "kureem@gmail.com");
				//rep.put("$delivery","Rs " + order.getDescendentOfType(Delivery.class).getPrice().toPlainString());
				//rep.put("$total", order.getTotal().toPlainString());
				
				textContent = compile(textContent, rep);
				source.getAncestorOfType(EXInvoiceHeader.class).setMessage(textContent);
			}else if(name.equals("method")){
				String amethod = n.getTextContent();
				String[] as = StringUtil.split(amethod, ".");
				String method = as[as.length-1];
				String cls = amethod.replace("." + method, "");
				
				try{
					Class clazz =Thread.currentThread().getContextClassLoader().loadClass(cls);
					clazz.getMethod(method, Container.class).invoke(clazz.newInstance(), source);
				}catch(Exception e){
					throw new UIException(e);
				}
			}else if(name.equalsIgnoreCase("script")){
				String script = n.getTextContent();
				GroovyShell sh = new GroovyShell(new Binding());

				
				if(source.getAncestorOfType(DynaForm.class) != null){
					DynaForm form = source.getAncestorOfType(DynaForm.class);
					List<StatefullComponent> fields = form.getFields();
					for(StatefullComponent field : fields){
						sh.setVariable(field.getName(), field.getValue());
					}
				}
				
				sh.evaluate(script);
			}else if(name.equalsIgnoreCase("form")){
				if(order == null)
					order = (Order)SpringUtil.getRepositoryService().getFile(source.getAttribute("path"), Util.getRemoteUser());
				EXDynaformPanel panel = createForm(n.getChildNodes(), n.getAttributes().getNamedItem("title").getTextContent(),order,source.getAttribute("actor"));
				panel.setStyle("width", "550px").setStyle("z-index", "3000");
				source.getAncestorOfType(PopupContainer.class).addPopup(panel);
			}
		}
		
		if(order != null){
			source.getParent().getChildren().clear();
			source.getParent().setRendered(false);
			addButtons(source.getParent(), order.getStatus(), actor, order.getAbsolutePath());
		}
		
		
	}
	
private EXDynaformPanel createForm(NodeList fields, String title, File file, final String actor){
		
		EXDynaformPanel panel = new EXDynaformPanel("panel", title);
		
		for(int i = 0;i < fields.getLength(); i++){
			Node n = fields.item(i);
			String name = n.getNodeName();
			if(name.equalsIgnoreCase("input")){
				String type =n.getAttributes().getNamedItem("type").getTextContent();
				StatefullComponent stf = null;
				if(type.equalsIgnoreCase("text")){
					stf = new EXInput(n.getAttributes().getNamedItem("name").getTextContent());
				}else if(type.equalsIgnoreCase("password")){
					stf = new EXPassword(n.getAttributes().getNamedItem("name").getTextContent());
				}else if(type.equalsIgnoreCase("date")){
					stf = new EXDatePicker(n.getAttributes().getNamedItem("name").getTextContent());
				}else if(type.equalsIgnoreCase("datetime")){
					stf = new EXDateTimePicker(n.getAttributes().getNamedItem("name").getTextContent());
				}else if(type.equalsIgnoreCase("color")){
					stf = new EXColorPicker(n.getAttributes().getNamedItem("name").getTextContent());
				}else if(type.equalsIgnoreCase("textarea")){
					stf = new EXTextArea(n.getAttributes().getNamedItem("name").getTextContent());
				}else if(type.equalsIgnoreCase("select")){
					String[] vals = StringUtil.split(n.getAttributes().getNamedItem("options").getTextContent(), ",");
					DefaultDataModel<Object> model = new DefaultDataModel<Object>();
					for(String s : vals)
						model.addItem(s);
					
					stf = new EXSelect(n.getAttributes().getNamedItem("name").getTextContent(),model);
					
				}
				
				if(n.getAttributes().getNamedItem("width") != null){
					String width = n.getAttributes().getNamedItem("width").getTextContent();
					stf.setStyle("width", width);
				}
				
				if(n.getAttributes().getNamedItem("height") != null){
					String width = n.getAttributes().getNamedItem("height").getTextContent();
					stf.setStyle("height", width);
				}
				
				if(stf != null){
					panel.addField(n.getAttributes().getNamedItem("label").getTextContent(), stf);
				}
			}else if(name.equalsIgnoreCase("btn")){
				String btnName = n.getAttributes().getNamedItem("name").getTextContent();
				String label =n.getAttributes().getNamedItem("label").getTextContent();
				EXButton btn = new EXButton(btnName, label);
				Node action  = null;
				panel.addButton(btn);
				NodeList actions = n.getChildNodes();
				btn.setAttribute("path", file.getAbsolutePath());
				btn.setAttribute("actor", actor);
				for(int j = 0; j <actions.getLength(); j++){
					Node nn = actions.item(j);
					if(nn.getNodeName().equalsIgnoreCase("action")){
						action = nn;
						break;
					}
				}
				final NodeList nl = action.getChildNodes();
				if(action != null){
					
					btn.addEvent(new Event() {
						
						@Override
						public void Success(ClientProxy container, Map<String, String> request)
								throws UIException {
							
							
						}
						
						@Override
						public boolean ServerAction(Container container, Map<String, String> request)
								throws UIException {
							try{
							String path = container.getAttribute("path");
							File input = SpringUtil.getRepositoryService().getFile(path, Util.getRemoteUser());
							Map variables = new HashMap();
							variables.put("input", input);
							variables.put("merchant", MallUtil.getCurrentMerchant());
							
							if(container.getAncestorOfType(EXDynaformPanel.class) != null){
								Map<String,String> fields = container.getAncestorOfType(EXDynaformPanel.class).getFieldValues();
								variables.putAll(fields);
							}
							
							executeXML(container, actor,nl);
							}catch(Exception e){
								throw new RuntimeException(e);
							}
							return true;
						}
						
						@Override
						public void ClientAction(ClientProxy container) {
							container.mask().makeServerRequest(this);
							
						}
					},  CLICK);
				}
			}
		}
		return panel;
	}
	
	private  void sendMails(NodeList list,Order order, Merchant merchant, BillingInformation customer){
		for(int i = 0; i < list.getLength(); i ++){
			Node n = list.item(i);
			String name = n.getNodeName();
			if(name.equals("mail")){
				sendMail(n.getChildNodes(),order, merchant, customer);
			}
		}
	}
	
	public  String buildOrder(Order order, Merchant merchant){
		String s = IOUtil.getStreamContentAsString(Thread.currentThread().getContextClassLoader().getResourceAsStream(getResourceClassPath() + "/Order.xhtml"));
		Map<String , String> rep = new HashMap<String, String>();
		
		List<OrderEntry> entries  = order.getEntries();
		StringBuilder or = new StringBuilder();
		ShippingInformation shipping = order.getShippingInformation();
		BillingInformation billing = order.getBillingInformation();
		int ii = 0;
		for(OrderEntry entry : entries){
			or.append(getOrderLine((SalesOrderEntry)entry, ii));
			ii++;
		}
		Delivery del = order.getDelivery();
		String currentCurrency = FinanceUtil.getCurrentCurrency();
		rep.put("$orderlines", or.toString());
		rep.put("$invoicenumber", order.getCode());
		rep.put("$date", new SimpleDateFormat("dd/MM/yyyy").format(order.getDateOfTransaction()));
		
		rep.put("$subtotal", StringUtil.toCurrency(currentCurrency,order.getSubTotal()));
		rep.put("$tax", StringUtil.toCurrency(currentCurrency, order.getTax()));
		rep.put("$delivery", StringUtil.toCurrency( currentCurrency,del.getPrice()));
		
		rep.put("$status", getStatus(order.getStatus()));
		rep.put("$total",StringUtil.toCurrency(currentCurrency,order.getTotal().doubleValue() + del.getPrice().doubleValue()));
		
		rep.put("$bfirstName", billing.getFirstName());
		rep.put("$blastName", billing.getLastName());
		rep.put("$bcompanyName", billing.getCompany());
		rep.put("$baddline1", billing.getAddressLine1());
		rep.put("$baddline2", billing.getAddressLine2());
		rep.put("$bcity", billing.getCity());
		rep.put("$bphone", billing.getPhone());
		rep.put("$bmobile", billing.getMobile());
		rep.put("$bemail", billing.getEmail());
		rep.put("$bcountry", billing.getCountry());
		rep.put("$bzipcode", billing.getZipPostalCode());
		rep.put("$bfax", billing.getFax());
		
		rep.put("$sfirstName", shipping.getFirstName());
		rep.put("$slastName", shipping.getLastName());
		rep.put("$scompanyName", shipping.getCompany());
		rep.put("$saddline1", shipping.getAddressLine1());
		rep.put("$saddline2", shipping.getAddressLine2());
		rep.put("$scity", shipping.getCity());
		rep.put("$sphone", shipping.getPhone());
		rep.put("$smobile", shipping.getMobile());
		rep.put("$semail", shipping.getEmail());
		rep.put("$scountry", shipping.getCountry());
		rep.put("$szipcode", shipping.getZipPostalCode());
		rep.put("$sfax", shipping.getFax());
		

		rep.put("$mcompanyName", merchant.getTitle());
		rep.put("$maddline1", merchant.getAddressLine1());
		rep.put("$maddline2", merchant.getAddressLine2());
		rep.put("$mcity", merchant.getCity());
		rep.put("$mphone", merchant.getPhone());
		rep.put("$mmobile", merchant.getMobilePhone());
		rep.put("$memail", merchant.getEmail());
		rep.put("$mfax", merchant.getFax());
		rep.put("$mbrn", merchant.getBusinessRegistrationNumber());
		rep.put("$mvatreg", merchant.getVatRegistrationNumber());

		s = compile(s, rep);
		
		return s;
		
		
	}
	
	protected  String getOrderLine(SalesOrderEntry entry, int index){
		String style = "style=background:steelBlue";
		if((index %2 ) == 0){
			style = "";
		}
		
		String currency = FinanceUtil.getCurrentCurrency();
		String s = "<tr "+style+"><td>"+entry.getProductCode()+"</td><td>"+entry.getTitle()+"</td><td style='text-align:right'>"+entry.getQuantity()+"</td><td style='text-align:right'>"+ StringUtil.toCurrency(currency,entry.getPrice())+"</td><td style='text-align:right'>"+StringUtil.toCurrency(currency,entry.getTotal())+"</td></tr>";
		return s;
	}
	
	private  void sendMail(NodeList list, Order order, Merchant merchant, BillingInformation customer){
		
		String subject = "";
		String from = "";
		String to = "";
		String content = "";
		
		Map<String, String> rep = new HashMap<String, String>();
		rep.put("${customer.mail}", customer.getEmail());
		rep.put("$customer", customer.getFirstName() + " " + customer.getLastName());
		rep.put("$merchant", merchant.getCompanyName());
		rep.put("$invoicenumber", order.getCode());
		rep.put("$message", "UPS has been notified about the delivery. An agent will confirm the delivery to you as soon as possible");
		if(!merchant.isUps()){
			rep.put("$message", "Please make the necessary arrangement for the delivery");
		}
		
		rep.put("${merchant.mail}", merchant.getEmail());
		rep.put("$order", buildOrder(order, merchant));
		rep.put("$administrator.mail", "kureem@gmail.com");
		
		for(int i = 0; i < list.getLength(); i ++){
			Node n = list.item(i);
			String name = n.getNodeName();
			if(name.equals("subject")){
				subject =  compile( n.getTextContent(), rep);
			}else if(name.equals("from")){
				from = compile( n.getTextContent(), rep);
			}
			else if(name.equals("to")){
				to = compile( n.getTextContent(), rep);
			}else if(name.equals("content")){
				content = compile( n.getTextContent(), rep);
			}
		}
		try{
			JavaMailSender sender = SpringUtil.getBeanOfType(JavaMailSender.class);
			MimeMessage message = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setSubject(subject);
			helper.setFrom(from);
			helper.setTo(to);
			helper.setText(content, true);
			sender.send(message);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public static  String compile(String s , Map<String, String> rep){
		Iterator<String> iter = rep.keySet().iterator();
		while(iter.hasNext()){
			String key = iter.next();
			String value = rep.get(key);
			if(key != null){
				if(value == null){
					value = "";
				}
				s = s.replace(key, value);
			}
		}
		
		return s;
	}
	

	public int[] getAvailableStates() {
		
		List<Integer> ad = new ArrayList<Integer>();
		try{
			//Properties prop = getWorkflow(MallUtil.getCurrentMerchant().getUsername());
			Iterator<Object> iter = prop.keySet().iterator();
			while(iter.hasNext()){
				String key = iter.next().toString();
				if(key.endsWith(".label")){
					ad.add( Integer.parseInt(key.replace(".label", "")));
					
				}
			}
			int[] res = new int[ad.size()];
			for(int i=0;i<ad.size(); i++){
				res[i] = ad.get(i);
			}
			return res;
			//return ad.toArray(new int[ad.size()]);
		}catch(Exception e){
			throw new UIException(e);
		}
		
		
//		int[] res = new int[]{};
//		try{
//			//Properties prop = getWorkflow(MallUtil.getCurrentMerchant().getUsername());
//			Iterator<Object> iter = prop.keySet().iterator();
//			while(iter.hasNext()){
//				String key = iter.next().toString();
//				if(key.endsWith(".label")){
//					ArrayUtils.add(res, Integer.parseInt(key.replace(".label", "")));
//					
//				}
//			}
//			return res;
//		}catch(Exception e){
//			throw new UIException(e);
//		}
		
	}
	
	public static void main(String[] args)throws Exception {
	
	}

}
