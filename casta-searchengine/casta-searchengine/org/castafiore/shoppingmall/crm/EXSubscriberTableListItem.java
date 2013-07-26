package org.castafiore.shoppingmall.crm;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.castafiore.KeyValuePair;
import org.castafiore.accounting.Account;
import org.castafiore.accounting.CashBook;
import org.castafiore.inventory.orders.OrderService;
import org.castafiore.persistence.Dao;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.searchengine.back.OS;
import org.castafiore.security.User;
import org.castafiore.shoppingmall.checkout.BillingInformation;
import org.castafiore.shoppingmall.checkout.Order;
import org.castafiore.shoppingmall.crm.subscriptions.PaymentsCellRenderer;
import org.castafiore.shoppingmall.crm.subscriptions.PaymentsModel;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.shoppingmall.merchant.MerchantSubscription;
import org.castafiore.shoppingmall.message.ui.EXNewMessagePopup;
import org.castafiore.shoppingmall.orders.OrdersUtil;
import org.castafiore.shoppingmall.orders.OrdersWorkflow;
import org.castafiore.shoppingmall.reports.ReportsUtil;
import org.castafiore.shoppingmall.reports.ReportsUtil.Dat;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.button.EXIconButton;
import org.castafiore.ui.ex.form.button.Icons;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.ui.ex.form.table.EXPagineableTable;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.ui.ex.toolbar.EXToolBar;
import org.castafiore.ui.js.JMap;
import org.castafiore.utils.JavascriptUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.File;
import org.hibernate.criterion.Restrictions;

public class EXSubscriberTableListItem extends EXContainer implements Event{
	


	private OrderInfo orderInfo;
	

	public EXSubscriberTableListItem(String name) {
		super(name, "div");
		setStyle("width", "150px");
		
		setStyle("float", "left");
		setStyle("margin", "5px");
		setStyle("padding", "8px");
		addClass("EXSubscriberTableListItem").addClass("ui-state-default").addClass("ui-corner-all").setStyle("text-align", "center");
		addChild(new EXContainer("fs", "div").addClass("name").setStyle("font-weight", "bold"));
		addChild(new EXContainer("name", "div").addClass("name"));
		addChild(new EXContainer("email", "div").addClass("email"));
		addChild(new EXContainer("phone", "div").addClass("phone"));
		Container mail = new EXIconButton("mail", Icons.ICON_ALERT).setAttribute("title", "Send user a mail").addClass("mail");
		mail.getChildByIndex(0).setStyle("background-image", "url(\"icons-2/fugue/icons/mail.png\")").setStyle("background-position", "0");
		mail.addEvent(this, Event.CLICK);
		addChild(mail);
		
		Container message = new EXIconButton("appForm", Icons.ICON_ALERT).setAttribute("title", "Show customer detail").addClass("message");
		message.getChildByIndex(0).setStyle("background-image", "url(\"icons-2/fugue/icons/balloon--arrow.png\")").setStyle("background-position", "0");
		message.addEvent(this, Event.CLICK);
		addChild(message);
		
		Container payments = new EXIconButton("payments", Icons.ICON_ALERT).setAttribute("title", "Show Payments").addClass("message");
		payments.getChildByIndex(0).setStyle("background-image", "url(\"icons-2/fugue/icons/money.png\")").setStyle("background-position", "0");
		payments.addEvent(this, Event.CLICK);
		addChild(payments);
	}
	
	public EXPanel showPayments(){
		//CashBook book = MallUtil.getCurrentMerchant().getCashBook("DefaultCashBook");
		
		String name = getChild("name").getText(false);
		EXPanel panel = new EXPanel("Payments made", "Payment made by " + name);
		panel.setStyle("width","722px");
		EXTable table = new EXTable("paymentTable", new PaymentsModel("/root/users/$user/Applications/e-Shop/$user/DefaultCashBook".replace("$user", Util.getLoggedOrganization()), orderInfo.getFsCode()));
		table.setCellRenderer(new PaymentsCellRenderer(name));
		
		EXPagineableTable pt = new EXPagineableTable("", table);
		table.setStyle("width", "700px");
		
		
		Container body = new EXContainer("", "div");
		
		
		EXToolBar tb = new EXToolBar("");
		tb.addItem(new EXButton("newPayment", "Make new payment"));
		body.addChild(tb);
		
		//Order order = ReportsUtil.getOrder(username);
		
		BigDecimal total =orderInfo.getTotalPayment();// ReportsUtil.getTotalPaymentsMade(username);

		Dat dat = ReportsUtil.calculate(total.doubleValue(), orderInfo.getStartInstallmentDate(), orderInfo.getInstallment().doubleValue());

		String msg = "Current Installments :" + dat.currentInstallment ;
		String msg1 = "Installment price :" + StringUtil.toCurrency("MUR", orderInfo.getInstallment());
		String msg2 = "Total payment made :" +StringUtil.toCurrency("MUR", total);
		String msg3 = "Arrears :" +StringUtil.toCurrency("MUR", dat.arrear);
		
		String msg4 = "Next Payment : " + PaymentsCellRenderer.DATE_FORMAT.format(dat.nextPaymentDate.getTime());
		
		body.addChild(new EXContainer("", "h5").setText("Reg. Date :" +PaymentsCellRenderer.DATE_FORMAT.format(orderInfo.getDateOfTransaction())).setStyle("margin", "3px"));
		body.addChild(new EXContainer("", "h5").setText(msg).setStyle("margin", "3px"));
		body.addChild(new EXContainer("", "h5").setText(msg1).setStyle("margin", "3px"));
		body.addChild(new EXContainer("", "h5").setText(msg2).setStyle("margin", "3px"));
		if(dat.arrear> 0){
			body.addChild(new EXContainer("", "h5").setText(msg3).setStyle("margin", "3px").setStyle("color", "red"));
		}else{
			body.addChild(new EXContainer("", "h5").setText(msg3).setStyle("margin", "3px"));
		}
		body.addChild(new EXContainer("", "h5").setText(msg4).setStyle("margin", "3px"));
		
		body.addChild(pt);
		
		tb.getDescendentByName("newPayment").addEvent(this, CLICK);
		 
		panel.setBody(body);
		getAncestorOfType(PopupContainer.class).addPopup(panel.setStyle("z-index", "3000"));
		return panel;
	}
	
	public void setOrderInfo(OrderInfo u){
		this.orderInfo = u;
		setAttribute("username", u.getFsCode());
		getChild("name").setText(u.getTitle() + " " + u.getFirstName() + " " + u.getLastName());
		getChild("email").setText(u.getEmail());
		String p = u.getPhone();
		if(StringUtil.isNotEmpty(u.getMobile())){
			if(StringUtil.isNotEmpty(p)){
				p =  p + "/";
			}
			p = p + u.getMobile();
		}
		if(u != null){
			String color = SpringUtil.getBeanOfType(OrdersWorkflow.class).getColor(u.getStatus());
			String style = "-moz-linear-gradient(center top , "+color+" 0%, #C4C4C4 100%) repeat scroll 0 0 transparent";
			setStyle("background", style);
			
		}
		getChild("phone").setText(p);
		getChild("fs").setText(u.getFsCode());
		
		getChild("mail").setAttribute("subscriberemail", u.getEmail());
		
		
		
		
	}


	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}
	
	
	
	public void showSMS(String username, Container source){
		EXNewMessagePopup popup = new EXNewMessagePopup(username);
		popup.init();
		popup.setStyle("z-index", "3001").setStyle("border", "double 3px silver").setStyle("background", "steelblue");
		source.getAncestorOfType(PopupContainer.class).addPopup(popup);
	}
	
	
	
	public void showMail(String subscriberemail, Container source){
		EXNewMessagePopup popup = new EXNewMessagePopup(subscriberemail);
		String fromemail = MallUtil.getCurrentMerchant().getEmail();
		
		popup.initForSendMail(fromemail, subscriberemail, "");
		popup.setStyle("z-index", "3001").setStyle("border", "double 3px silver").setStyle("background", "steelblue");
		source.getAncestorOfType(PopupContainer.class).addPopup(popup);
		popup.getDescendentByName("sendMessage").setText("Send Mail");
	}
	
	
	public void showMessage(String username, Container source){
		EXNewMessagePopup popup = new EXNewMessagePopup(username);
		popup.init();
		popup.setStyle("z-index", "3001").setStyle("border", "double 3px silver").setStyle("background", "steelblue");
		source.getAncestorOfType(PopupContainer.class).addPopup(popup);
	}
	
	
	public EXPanel appForm( Container source){
		try{
			String path = this.orderInfo.getAbsolutePath();
 
			Class c = Thread.currentThread().getContextClassLoader().loadClass("com.eliensons.ui.plans.EXElieNSonsApplicationForm");
			Container appform = source.getAncestorOfType(OS.class).getChild("inits").getDescendentOfType(c);
			c.getMethod("setInfoF", String.class).invoke(appform,path);
			EXPanel panel =appform.getAncestorOfType(EXPanel.class);
			if(panel == null){
				panel = getAncestorOfType(EXPanel.class);
				appform.setStyle("position", "absolute").setStyle("z-index", "5000");
			}
			panel.setDisplay(true);
			return panel;
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return null;
	}
	

	
	
	private EXPanel newPayment(Container source){
		EXDynaformPanel panel = new EXDynaformPanel("", "New Intallment");

		
		EXUnPaidInstallments installments = new EXUnPaidInstallments("installment", orderInfo);

		panel.addField("Instalmts. :",installments);
		panel.addField("Amount", new EXInput("amount"));
		
		panel.addField("Paid by:",new EXSelect("type", new DefaultDataModel<Object>().addItem("Cash", "Cheque", "Bank transfer", "Standing Order")));
		panel.addField("Check No. :",new EXInput("check"));
		
		
		try{
		
		
		
		EXSelect posSelect = new EXSelect("pos", new OrderService().getPointOfSales());
		
		panel.addField("POS :",posSelect);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		panel.addButton(new EXButton("savePayment", "Save"));
		panel.setStyle("z-index", "4000");
		source.getAncestorOfType(EXPanel.class).addPopup(panel);
		
		panel.getDescendentByName("savePayment").addEvent(this, CLICK);
		return panel;
		
	}
	
	private void changeValue(Container source){
		Account acc = (Account)SpringUtil.getRepositoryService().getFile(((KeyValuePair)((EXSelect)source).getValue()).getKey()	, Util.getRemoteUser());
		
		source.getAncestorOfType(EXDynaformPanel.class).getField("amount").setValue(acc.getDefaultValue().toPlainString());
	}
	
	
	public String getUrl(Container source, String code){
		try{
		EXDynaformPanel panel = source.getAncestorOfType(EXDynaformPanel.class);
		String accCode = getAttribute("username");
		
		
		Date date = panel.getDescendentOfType(EXUnPaidInstallments.class).getLastDate();
		Double value = Double.parseDouble(panel.getField("amount").getValue().toString());
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH,1);
		String next = new SimpleDateFormat("MMM/yyyy").format(cal.getTime());
		String description = "Payment of installment for " + getAttribute("username")  +" up to " + new SimpleDateFormat("dd/MMM/yyyy").format(date) + " next installment for " + next;
		BigDecimal ttl = new BigDecimal(value);
		
		User user = SpringUtil.getSecurityService().loadUserByUsername(getAttribute("username"));
		
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("receipt", code);
		params.put("name", user.toString());
		params.put("date", new SimpleDateFormat("dd/MMM/yyyy").format(new Date()));
		params.put("price", StringUtil.toCurrency("MUR",ttl));
		params.put("description", description);
		params.put("fsNumber", accCode);
		
		Iterator<String> iter = params.keySet().iterator();
		String url = "receipt.jsp?0=0";
		while(iter.hasNext()){
			String k = iter.next();
			String v = params.get(k);
			url = url + "&" + k + "=" + v;
		}
		
		return url;
		}catch(Exception e){
			throw new UIException(e);
		}
	}
	
	private void savePayment(Container source, String code)throws UIException{
		
		try{
			EXDynaformPanel panel = source.getAncestorOfType(EXDynaformPanel.class);
			
			Date date = panel.getDescendentOfType(EXUnPaidInstallments.class).getLastDate();
			if(date == null){
				panel.getDescendentOfType(EXUnPaidInstallments.class).addClass("ui-state-error");
				return;
			}
			
			//SimpleKeyValuePair kv = (SimpleKeyValuePair)panel.getField("installment").getValue();
			//Account acc = (Account)SpringUtil.getRepositoryService().getFile(kv.getKey()	, Util.getRemoteUser());
			Merchant m = MallUtil.getCurrentMerchant();
			Calendar cal = Calendar.getInstance();
			
			
			cal.setTime(date);
			//cal.add(Calendar.MONTH,1);
			String next = new SimpleDateFormat("MMM/yyyy").format(cal.getTime());
			Double value = Double.parseDouble(panel.getField("amount").getValue().toString());
			
			String name = new Date().getTime() + "";
			String description = "Payment of installment for " + getAttribute("username")  +" up to " + new SimpleDateFormat("dd/MMM/yyyy").format(date) + " next installment for " + next;
			String accCode = getAttribute("username");
			BigDecimal ttl = new BigDecimal(value);
			//String code = SpringUtil.getRepositoryService().getNextSequence("Payments", Util.getRemoteUser()) + "";// m.nextSequenceValue("Installments").toString();
			
			String pos = "Unknown";
			if(panel.getField("pos") != null){
				pos = panel.getField("pos").getValue().toString();
			}
			
			m.save();
			String query = "insert into WFS_FILE (clazz, dateCreated, lastModified, name, parent_id, summary, title, code, paymentMethod,total, accountCode, dateOfTransaction, DTYPE, absolutePath, size, status, commentable, dislikeit, likeit,ratable, pointOfSale) values " +
					"" +
					"('org.castafiore.accounting.CashBookEntry',now(),now(),?, '/root/users/elieandsons/Applications/e-Shop/elieandsons/DefaultCashBook', ?, ?, ?, 'Cash', ?, ?,? ,'CashBookEntry',?,1,1,true,1,1, true,?);";
			
	
			//0=name
			//1=summary
			//2=title
			//3=code
			//4=paymenttype
			//5=total
			//6=accountCode
			//7=date transaction
			//8=absolutepath
			//
			
			
			SpringUtil.getBeanOfType(Dao.class).getSession().createSQLQuery(query).setParameter(0, name)
			.setParameter(1, description).setParameter(2, description).setParameter(3, code)
			.setParameter(4, ttl).setParameter(5, accCode).setParameter(6, new Timestamp(date.getTime()))
			.setParameter(7, "/root/users/"+Util.getLoggedOrganization()+"/Applications/e-Shop/elieandsons/DefaultCashBook/" + name).setParameter(8, pos).executeUpdate();
			
			
			
			
			
			
			Map<String, String> params = new HashMap<String, String>();
			
			params.put("receipt", code);
			User user = SpringUtil.getSecurityService().loadUserByUsername(getAttribute("username"));
			params.put("name", user.toString());
			params.put("date", new SimpleDateFormat("dd/MMM/yyyy").format(new Date()));
			params.put("price", StringUtil.toCurrency("MUR",ttl));
			params.put("description", description);
			params.put("fsNumber", accCode);
			
//			Order order = OrdersUtil.getOrderByCode(accCode);
//			BillingInformation bif = order.getBillingInformation();
//			
//			File f = bif.getFile("applicationForm");
//			f.setStatus(8);
//			f.save();
			
			String url = ReportsUtil.getReceiptUrl(params, "http://68.68.109.26/elie/insreceipt.xls");
			source.setText(url);
			
			Container parent = source.getParent();
			source.remove();
			parent.setRendered(false);
			parent.addChild(new EXContainer("", "a").setText("Download receipt").setAttribute("href", url).setAttribute("target", "_blank"));
			
			EXTable table = (EXTable)panel.getParent().getAncestorOfType(EXPanel.class).getDescendentByName("paymentTable");
			PaymentsModel model = (PaymentsModel)table.getModel();
			model.reLoad();
			table.refresh();
			//table.setModel(new PaymentsModel("/root/users/elieandsons/Applications/e-Shop/elieandsons/DefaultCashBook", user.getUsername()));
			
			
			//update order
			
		}catch(Exception e){
			throw new UIException(e);
		}
		
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		String subscriberemail = container.getAttribute("subscriberemail");
		
		if(container.getText().equalsIgnoreCase("Print receipt")){
			//print recept;
		}
		
		if(container.getName().equals("sms")){
			showSMS(subscriberemail, container);
		}else if(container.getName().equals("mail")){
			showMail(subscriberemail, container);
		}else if(container.getName().equals("message")){
			showMessage(getAttribute("username"), container);
		}else if(container.getName().equals("payments")){
			request.put("pisdd", showPayments().setStyle("visibility", "visible").setDisplay(true).getId());
		}else if(container.getName().equals("newPayment")){
			request.put("pid", newPayment(container).getId());
		}else if(container.getName().equals("installment")){
			changeValue(container);
		}else if(container.getName().equals("savePayment")){
			if(request.containsKey("secondphase")){
				savePayment(container, request.get("secondphase"));
			}else{
				String code = SpringUtil.getRepositoryService().getNextSequence("Payments", Util.getRemoteUser()) + "";
				String url = getUrl(container,code);
				request.put("url", url);
				request.put("code",code);
			}
			
			
			
		}else if(container.getName().equals("appForm")){
			EXPanel panel = appForm(container);
			request.put("pid", panel.getId());
		}
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		if(request.containsKey("pid")){
			container.mergeCommand(new ClientProxy("#" + request.get("pid")).appendTo(new ClientProxy(container.getRoot().getIdRef())));
		}else if(request.containsKey("url")){
			String js = "window.open('"+JavascriptUtil.javaScriptEscape(request.get("url"))+"', '_blank');";
			container.appendJSFragment(js);
			container.makeServerRequest(new JMap().put("secondphase", request.get("code")), this);
		}
		
	}


//	@Override
//	public JMap getDraggableOptions() {
//		return new JMap().put("revert", "true");
//	}
//	

}
