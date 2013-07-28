package org.castafiore.shoppingmall.orders;

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
import java.util.Map;

import org.castafiore.KeyValuePair;
import org.castafiore.accounting.Account;
import org.castafiore.accounting.CashBook;
import org.castafiore.inventory.orders.OrderService;
import org.castafiore.persistence.Dao;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.security.User;
import org.castafiore.shoppingmall.checkout.BillingInformation;
import org.castafiore.shoppingmall.checkout.Order;
import org.castafiore.shoppingmall.checkout.ShippingInformation;
import org.castafiore.shoppingmall.crm.EXUnPaidInstallments;
import org.castafiore.shoppingmall.crm.OrderInfo;
import org.castafiore.shoppingmall.crm.subscriptions.PaymentsCellRenderer;
import org.castafiore.shoppingmall.crm.subscriptions.PaymentsModel;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.shoppingmall.message.ui.EXNewMessagePopup;
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
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.ui.ex.form.table.EXPagineableTable;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.ui.ex.toolbar.EXToolBar;
import org.castafiore.ui.js.JMap;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.JavascriptUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.File;

public class EXCustomerInfo extends EXXHTMLFragment implements Event {
	private OrderInfo order;

	public EXCustomerInfo(String name) {
		super(name, "templates/EXCustomerInfo.xhtml");
		// TODO Auto-generated constructor stub

	}

	public void setOrder(OrderInfo order) {
		this.order = order;
		setAttribute("path", order.getAbsolutePath());
		this.getChildren().clear();
		this.setRendered(false);
		// BillingInformation bi= order.getBillingInformation();
		ShippingInformation si = null;// order.getShippingInformation();
		addChild(new EXContainer("bi.firstName", "label").setText(order
				.getFirstName()));
		addChild(new EXContainer("bi.lastName", "label").setText(order
				.getLastName()));
		addChild(new EXContainer("bi.company", "label").setText(order
				.getCompany()));
		addChild(new EXContainer("bi.phone", "label").setText(order.getPhone()));
		addChild(new EXContainer("bi.mobile", "label").setText(order
				.getMobile()));
		addChild(new EXContainer("bi.fax", "label").setText(order.getFax()));
		addChild(new EXContainer("bi.email", "label").setText(order.getEmail()));
		addChild(new EXContainer("bi.addressLine1", "label").setText(order
				.getAddressLine1()));
		addChild(new EXContainer("bi.addressLine2", "label").setText(order
				.getAddressLine2()));
		addChild(new EXContainer("bi.city", "label").setText(order.getCity()));
		addChild(new EXContainer("bi.country", "label").setText(order
				.getCountry()));
		addChild(new EXContainer("bi.zipPostalCode", "label").setText(order
				.getZipPostalCode()));

		if (si != null) {
			addChild(new EXContainer("si.firstName", "label").setText(si
					.getFirstName()));
			addChild(new EXContainer("si.lastName", "label").setText(si
					.getLastName()));
			addChild(new EXContainer("si.company", "label").setText(si
					.getCompany()));
			addChild(new EXContainer("si.phone", "label")
					.setText(si.getPhone()));
			addChild(new EXContainer("si.mobile", "label").setText(si
					.getMobile()));
			addChild(new EXContainer("si.fax", "label").setText(si.getFax()));
			addChild(new EXContainer("si.email", "label")
					.setText(si.getEmail()));
			addChild(new EXContainer("si.addressLine1", "label").setText(si
					.getAddressLine1()));
			addChild(new EXContainer("si.addressLine2", "label").setText(si
					.getAddressLine2()));
			addChild(new EXContainer("si.city", "label").setText(si.getCity()));
			addChild(new EXContainer("si.country", "label").setText(si
					.getCountry()));
			addChild(new EXContainer("si.zipPostalCode", "label").setText(si
					.getZipPostalCode()));
			addChild(new EXContainer("sendMessage", "button").setText(
					"Send Message").addEvent(this, Event.CLICK));
		}

		addChild(new EXContainer("sendMessage", "button").setText("Send Mail")
				.addEvent(this, CLICK));
		addChild(new EXContainer("appForm", "button").setText(
				"Show application form").addEvent(this, CLICK));

		addChild(new EXContainer("payments", "button").setText("Payments")
				.addEvent(this, CLICK));
	}

	public EXPanel showPayments() {
		// Order order =
		// (Order)SpringUtil.getRepositoryService().getFile(getAttribute("path"),
		// Util.getRemoteUser());

		// CashBook book =
		// MallUtil.getCurrentMerchant().getCashBook("DefaultCashBook");

		// User u =
		// SpringUtil.getSecurityService().loadUserByUsername(order.getFsCode());
		// BillingInformation u = order.getBillingInformation();
		EXPanel panel = new EXPanel("Payments made",
				"Payment made by " + order.getFirstName() + " "
						+ order.getLastName() != null ? order.getLastName()
						: "");
		panel.setStyle("width", "722px");
		EXTable table = new EXTable("paymentTable",	new PaymentsModel("/root/users/$user/Applications/e-Shop/$user/DefaultCashBook"	.replace("$user", Util.getLoggedOrganization()),this.order.getFsCode()));
		table.setCellRenderer(new PaymentsCellRenderer(order.getFirstName()
				+ " " + order.getLastName()));

		EXPagineableTable pt = new EXPagineableTable("", table);
		table.setStyle("width", "700px");

		Container body = new EXContainer("", "div");

		EXToolBar tb = new EXToolBar("");
		tb.addItem(new EXButton("newPayment", "Make new payment"));
		body.addChild(tb);

		BigDecimal total = order.getTotalPayment();// ReportsUtil.getTotalPaymentsMade(order.getFsCode());

		Dat dat = ReportsUtil.calculate(total.doubleValue(), order
				.getStartInstallmentDate(), order.getInstallment()
				.doubleValue());

		String msg = "Current Installments :" + dat.currentInstallment;
		String msg1 = "Installment price :"
				+ StringUtil.toCurrency("MUR", order.getInstallment());
		String msg2 = "Total payment made :"
				+ StringUtil.toCurrency("MUR", total);
		String msg3 = "Arrears :" + StringUtil.toCurrency("MUR", dat.arrear);

		String msg4 = "Next Payment : "
				+ new SimpleDateFormat("dd/MMM/yyyy")
						.format(dat.nextPaymentDate.getTime());

		body.addChild(new EXContainer("", "h5").setText(
				"Reg. Date :"
						+ new SimpleDateFormat("dd/MMM/yyyy").format(order
								.getDateOfTransaction())).setStyle("margin",
				"3px"));
		body.addChild(new EXContainer("", "h5").setText(msg).setStyle("margin",
				"3px"));
		body.addChild(new EXContainer("", "h5").setText(msg1).setStyle(
				"margin", "3px"));
		body.addChild(new EXContainer("", "h5").setText(msg2).setStyle(
				"margin", "3px"));
		if (dat.arrear > 0) {
			body.addChild(new EXContainer("", "h5").setText(msg3)
					.setStyle("margin", "3px").setStyle("color", "red"));
		} else {
			body.addChild(new EXContainer("", "h5").setText(msg3).setStyle(
					"margin", "3px"));
		}
		body.addChild(new EXContainer("", "h5").setText(msg4).setStyle(
				"margin", "3px"));

		body.addChild(pt);

		tb.getDescendentByName("newPayment").addEvent(this, CLICK);

		panel.setBody(body);
		getAncestorOfType(PopupContainer.class).addPopup(
				panel.setStyle("z-index", "3000"));
		return panel;
	}

	private EXPanel newPayment(Container source) {
		EXDynaformPanel panel = new EXDynaformPanel("", "New Intallment");
		EXUnPaidInstallments installments = new EXUnPaidInstallments(
				"installment", order);

		panel.addField("Instalmts. :", installments);
		panel.addField("Amount", new EXInput("amount"));

		panel.addField(
				"Paid by:",
				new EXSelect("type", new DefaultDataModel<Object>().addItem(
						"Cash", "Cheque", "Bank transfer", "Standing Order")));
		panel.addField("Check No. :", new EXInput("check"));

		try {

			EXSelect posSelect = new EXSelect("pos",
					new OrderService().getPointOfSales());

			panel.addField("POS :", posSelect);
		} catch (Exception e) {
			e.printStackTrace();
		}

		panel.addButton(new EXButton("savePayment", "Save"));
		panel.setStyle("z-index", "4000");
		source.getAncestorOfType(EXPanel.class).addPopup(panel);

		panel.getDescendentByName("savePayment").addEvent(this, CLICK);
		return panel;

	}

	private void changeValue(Container source) {
		Account acc = (Account) SpringUtil.getRepositoryService().getFile(
				((KeyValuePair) ((EXSelect) source).getValue()).getKey(),
				Util.getRemoteUser());

		source.getAncestorOfType(EXDynaformPanel.class).getField("amount")
				.setValue(acc.getDefaultValue().toPlainString());
	}

	public String getUrl(Container source, String code) {
		try {
			// Order order =
			// (Order)SpringUtil.getRepositoryService().getFile(getAttribute("path"),
			// Util.getRemoteUser());
			EXDynaformPanel panel = source
					.getAncestorOfType(EXDynaformPanel.class);
			String accCode = order.getFsCode();

			Date date = panel.getDescendentOfType(EXUnPaidInstallments.class)
					.getLastDate();
			Double value = Double.parseDouble(panel.getField("amount")
					.getValue().toString());
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.MONTH, 1);
			String next = new SimpleDateFormat("MMM/yyyy")
					.format(cal.getTime());
			String description = "Payment of installment for "
					+ getAttribute("username") + " up to "
					+ new SimpleDateFormat("dd/MMM/yyyy").format(date)
					+ " next installment for " + next;
			BigDecimal ttl = new BigDecimal(value);

			// User user =
			// SpringUtil.getSecurityService().loadUserByUsername(getAttribute("username"));

			Map<String, String> params = new HashMap<String, String>();
			params.put("receipt", code);
			params.put("name", order.getFirstName() + " " + order.getLastName());
			params.put("date",
					new SimpleDateFormat("dd/MMM/yyyy").format(new Date()));
			params.put("price", StringUtil.toCurrency("MUR", ttl));
			params.put("description", description);
			params.put("fsNumber", accCode);

			Iterator<String> iter = params.keySet().iterator();
			String url = "receipt.jsp?0=0";
			while (iter.hasNext()) {
				String k = iter.next();
				String v = params.get(k);
				url = url + "&" + k + "=" + v;
			}

			return url;
		} catch (Exception e) {
			throw new UIException(e);
		}
	}

	private void savePayment(Container source, String code) throws UIException {

		try {
			// Order order =
			// (Order)SpringUtil.getRepositoryService().getFile(getAttribute("path"),
			// Util.getRemoteUser());
			EXDynaformPanel panel = source
					.getAncestorOfType(EXDynaformPanel.class);

			Date date = panel.getDescendentOfType(EXUnPaidInstallments.class)
					.getLastDate();
			if (date == null) {
				panel.getDescendentOfType(EXUnPaidInstallments.class).addClass(
						"ui-state-error");
				return;
			}

			// SimpleKeyValuePair kv =
			// (SimpleKeyValuePair)panel.getField("installment").getValue();
			// Account acc =
			// (Account)SpringUtil.getRepositoryService().getFile(kv.getKey() ,
			// Util.getRemoteUser());
			Merchant m = MallUtil.getCurrentMerchant();
			Calendar cal = Calendar.getInstance();

			cal.setTime(date);
			cal.add(Calendar.MONTH, 1);
			String next = new SimpleDateFormat("MMM/yyyy")
					.format(cal.getTime());
			Double value = Double.parseDouble(panel.getField("amount")
					.getValue().toString());

			String name = new Date().getTime() + "";
			String description = "Payment of installment for "
					+ getAttribute("username") + " up to "
					+ new SimpleDateFormat("dd/MMM/yyyy").format(date)
					+ " next installment for " + next;
			String accCode = getAttribute("username");
			BigDecimal ttl = new BigDecimal(value);
			// String code =
			// SpringUtil.getRepositoryService().getNextSequence("Payments",
			// Util.getRemoteUser()) + "";//
			// m.nextSequenceValue("Installments").toString();

			String pos = "Unknown";
			if (panel.getField("pos") != null) {
				pos = panel.getField("pos").getValue().toString();
			}

			m.save();
			String query = "insert into WFS_FILE (clazz, dateCreated, lastModified, name, parent_id, summary, title, code, paymentMethod,total, accountCode, dateOfTransaction, DTYPE, absolutePath, size, status, commentable, dislikeit, likeit,ratable, pointOfSale) values "
					+ ""
					+ "('org.castafiore.accounting.CashBookEntry',now(),now(),?, '/root/users/elieandsons/Applications/e-Shop/elieandsons/DefaultCashBook', ?, ?, ?, 'Cash', ?, ?,? ,'CashBookEntry',?,1,1,true,1,1, true,?);";

			// 0=name
			// 1=summary
			// 2=title
			// 3=code
			// 4=paymenttype
			// 5=total
			// 6=accountCode
			// 7=date transaction
			// 8=absolutepath
			//

			SpringUtil
					.getBeanOfType(Dao.class)
					.getSession()
					.createSQLQuery(query)
					.setParameter(0, name)
					.setParameter(1, description)
					.setParameter(2, description)
					.setParameter(3, code)
					.setParameter(4, ttl)
					.setParameter(5, accCode)
					.setParameter(6, new Timestamp(date.getTime()))
					.setParameter(
							7,
							"/root/users/"
									+ Util.getLoggedOrganization()
									+ "/Applications/e-Shop/elieandsons/DefaultCashBook/"
									+ name).setParameter(8, pos)
					.executeUpdate();

			Map<String, String> params = new HashMap<String, String>();

			params.put("receipt", code);
			User user = SpringUtil.getSecurityService().loadUserByUsername(
					order.getFsCode());
			params.put("name", user.toString());
			params.put("date",
					new SimpleDateFormat("dd/MMM/yyyy").format(new Date()));
			params.put("price", StringUtil.toCurrency("MUR", ttl));
			params.put("description", description);
			params.put("fsNumber", accCode);

			// Order order = OrdersUtil.getOrderByCode(accCode);
			// BillingInformation bif = order.getBillingInformation();

			// File f = bif.getFile("applicationForm");
			// f.setStatus(8);
			// f.save();

			String url = ReportsUtil.getReceiptUrl(params,
					"http://68.68.109.26/elie/insreceipt.xls");
			source.setText(url);

			Container parent = source.getParent();
			source.remove();
			parent.setRendered(false);
			parent.addChild(new EXContainer("", "a")
					.setText("Download receipt").setAttribute("href", url)
					.setAttribute("target", "_blank"));

			EXTable table = (EXTable) panel.getParent()
					.getAncestorOfType(EXPanel.class)
					.getDescendentByName("paymentTable");
			table.setModel(new PaymentsModel(
					"/root/users/elieandsons/Applications/e-Shop/elieandsons/DefaultCashBook",
					user.getUsername()));

			// update order

		} catch (Exception e) {
			throw new UIException(e);
		}

	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);

	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {

		if (container.getName().equals("sendMessage")) {

			Merchant m = MallUtil.getCurrentMerchant();
			EXNewMessagePopup popup = new EXNewMessagePopup("");
			String to = getDescendentByName("bi.email").getText();
			String from = m.getEmail();

			popup.initForSendMail(from, to, "Message concerning invoice "
					+ order.getFsCode() + " ");
			getAncestorOfType(PopupContainer.class).addPopup(popup);
		} else if (container.getName().equalsIgnoreCase("payments")) {
			request.put("pisdd",
					showPayments().setStyle("visibility", "visible")
							.setDisplay(true).getId());
		} else if (container.getName().equals("newPayment")) {
			request.put("pid", newPayment(container).getId());
		} else if (container.getName().equals("installment")) {
			changeValue(container);
		} else if (container.getName().equals("savePayment")) {

			if (request.containsKey("secondphase")) {
				savePayment(container, request.get("secondphase"));
			} else {
				String code = SpringUtil.getRepositoryService()
						.getNextSequence("Payments", Util.getRemoteUser()) + "";
				String url = getUrl(container, code);
				request.put("url", url);
				request.put("code", code);
			}
			// savePayment(container);
		} else {
			Order myorder = (Order) SpringUtil.getRepositoryService().getFile(
					getAttribute("path"), Util.getRemoteUser());
			try {

				EXPanel panel = new EXPanel("", "Application form");
				Container c = (Container) Thread
						.currentThread()
						.getContextClassLoader()
						.loadClass(
								"com.eliensons.ui.plans.EXElieNSonsApplicationForm")
						.newInstance();
				c.getClass().getMethod("setInfo", BillingInformation.class)
						.invoke(c, myorder.getBillingInformation());
				panel.setBody(c);
				panel.setStyle("width", "725px");
				panel.setStyle("z-index", "4000");
				getAncestorOfType(PopupContainer.class).addPopup(panel);
			} catch (Exception e) {
				// e.printStackTrace();

				EXOtherProperties properties = new EXOtherProperties(
						"Other properties", myorder.getBillingInformation());
				properties.setStyle("width", "600px");
				getAncestorOfType(PopupContainer.class).addPopup(
						properties.setStyle("z-index", "6000"));

			}
		}

		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		if (request.containsKey("pid")) {
			container.mergeCommand(new ClientProxy("#" + request.get("pid"))
					.appendTo(new ClientProxy(container.getRoot().getIdRef())));
		} else if (request.containsKey("url")) {
			String js = "window.open('"
					+ JavascriptUtil.javaScriptEscape(request.get("url"))
					+ "', '_blank');";
			container.appendJSFragment(js);
			container.makeServerRequest(
					new JMap().put("secondphase", request.get("code")), this);
		}

	}

}
