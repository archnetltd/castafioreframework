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

import org.castafiore.inventory.orders.OrderService;
import org.castafiore.persistence.Dao;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.security.User;
import org.castafiore.shoppingmall.checkout.BillingInformation;
import org.castafiore.shoppingmall.checkout.Order;
import org.castafiore.shoppingmall.crm.subscriptions.PaymentsModel;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.shoppingmall.orders.OrdersUtil;
import org.castafiore.shoppingmall.reports.ReportsUtil;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;

import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.ui.js.JMap;
import org.castafiore.utils.JavascriptUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.File;

public class EXFastPaymentCreator extends EXDynaformPanel implements Event{

	public EXFastPaymentCreator(String name) {
		super(name, "Fast Payment  creator");
		
		
		//List codes = SpringUtil.getBeanOfType(Dao.class).getReadOnlySession().createSQLQuery("select code from WFS_FILE where dtype='Order'").list();
		
		
		addField("FS Code", new EXInput("fsCodes",""));
		getDescendentByName("fsCodes").addEvent(this, BLUR);
		
		
		EXUnPaidInstallments installments = new EXUnPaidInstallments("installment");

		addField("Instalmts. :",installments);
		addField("Amount", new EXInput("amount"));
		
		addField("Paid by:",new EXSelect("type", new DefaultDataModel<Object>().addItem("Cash", "Cheque", "Bank transfer", "Standing Order")));
		addField("Check No. :",new EXInput("check"));
		
		
		try{
		
		
		
		
		EXSelect posSelect = new EXSelect("pos", new OrderService().getPointOfSales());
		
		addField("POS :",posSelect);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		addButton(new EXButton("savePayment", "Save"));
		setStyle("z-index", "4000");
		
		
		getDescendentByName("savePayment").addEvent(this, CLICK);
	}
	
	
	public void refreshItems(String username){
		setAttribute("username", username);
		getDescendentOfType(EXUnPaidInstallments.class).setUsername(new OrderService().getOrder(username));
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
			cal.add(Calendar.MONTH,1);
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
			
			Order order = OrdersUtil.getOrderByCode(accCode);
			BillingInformation bif = order.getBillingInformation();
			
			File f = bif.getFile("applicationForm");
			f.setStatus(8);
			f.save();
			
			String url = ReportsUtil.getReceiptUrl(params, "http://68.68.109.26/elie/insreceipt.xls");
			source.setText(url);
			
			Container parent = source.getParent();
			source.remove();
			parent.setRendered(false);
			parent.addChild(new EXContainer("", "a").setText("Download receipt").setAttribute("href", url).setAttribute("target", "_blank"));
			
			EXTable table = (EXTable)panel.getParent().getAncestorOfType(EXPanel.class).getDescendentByName("paymentTable");
			table.setModel(new PaymentsModel("/root/users/$user/Applications/e-Shop/$user/DefaultCashBook".replace("$user", Util.getLoggedOrganization()), user.getUsername()));
			
			
			//update order
			
		}catch(Exception e){
			throw new UIException(e);
		}
		
		
	}


	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}


	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		if(container instanceof EXInput){
			String code = container.getAncestorOfType(EXInput.class).getValue().toString();
			
			refreshItems(code);
			return true;
		}
		
		if(request.containsKey("secondphase")){
			savePayment(container, request.get("secondphase"));
		}else{
			String code = SpringUtil.getRepositoryService().getNextSequence("Payments", Util.getRemoteUser()) + "";
			String url = getUrl(container,code);
			request.put("url", url);
			request.put("code",code);
		}
		//savePayment(container);
		return true;
	}


	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		if(request.containsKey("url")){
			String js = "window.open('"+JavascriptUtil.javaScriptEscape(request.get("url"))+"', '_blank');";
			container.appendJSFragment(js);
			container.makeServerRequest(new JMap().put("secondphase", request.get("code")), this);
		}
		
	}

	
	
	
}
