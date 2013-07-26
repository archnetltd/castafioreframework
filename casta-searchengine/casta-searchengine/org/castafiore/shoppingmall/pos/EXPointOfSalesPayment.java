package org.castafiore.shoppingmall.pos;

import java.util.List;
import java.util.Map;

import org.castafiore.KeyValuePair;
import org.castafiore.shoppingmall.checkout.BillingInformation;
import org.castafiore.shoppingmall.checkout.Order;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.dynaform.EXFieldSet;
import org.castafiore.ui.ex.form.AutoCompleteSource;
import org.castafiore.ui.ex.form.EXAutoComplete;
import org.castafiore.ui.ex.form.EXDatePicker;
import org.castafiore.ui.ex.form.EXDateTimePicker;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.list.DataModel;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXList;
import org.castafiore.ui.ex.form.list.EXListItem;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.ui.ex.form.list.ListItem;
import org.castafiore.ui.ex.form.list.ListItemRenderer;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.ui.ex.panel.Panel;
import org.castafiore.ui.js.JArray;
import org.castafiore.ui.js.JMap;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.castafiore.wfs.types.File;
import org.hibernate.criterion.Restrictions;

public class EXPointOfSalesPayment extends EXDynaformPanel implements Event{

	public EXPointOfSalesPayment(String name) {
		super(name, "Please enter payment information");
		DefaultDataModel<Object> model = new DefaultDataModel<Object>().addItem("At Counter", "Credit Card", "mKesh", "Bank Transfer");
		addField("Payment Method :", new EXSelect("paymentMethod", model));
		getField("paymentMethod").addEvent(this, Event.CLICK);
		
		EXFieldSet fs = new EXFieldSet("info", "Payment at counter information", false);
		fs.addField("Date/Time of payment :", new EXDateTimePicker("dateTime"));
		fs.addField("Showroom for payment :", new EXInput("showroom"));
		addField("Payment info :", fs);
		setStyle("width", "600px");
		
		addButton(new EXButton("savePayment", "Save and Close"));
		addButton(new EXButton("closePayment", "Close"));
		getDescendentByName("savePayment").addEvent(this, CLICK);
		getDescendentByName("closePayment").addEvent(Panel.CLOSE_EVENT, Event.CLICK);
		setStyle("z-index", "6000");
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}
	
	public EXFieldSet getPaymentInfoFields(String paymentMethod){
		if(paymentMethod.equalsIgnoreCase("Credit Card")){
			EXFieldSet fs = new EXFieldSet("info", "Credit Card info", false);
			fs.addField("Credit card type :", new EXSelect("creditCardType", new DefaultDataModel<Object>().addItem("MASTERCARD", "VISA")));
			fs.addField("Credit card number :", new EXInput("creditCardNumber"));
			fs.addField("Name of card :", new EXInput("nameOfCard"));
			fs.addField("Expiry date :", new EXDatePicker("expiryDate"));
			return fs;
			
		}else if(paymentMethod.equalsIgnoreCase("mkesh")){
			EXFieldSet fs = new EXFieldSet("info", "Mkesh Account info", false);
			fs.addField("Mobile phone :", new EXInput("mkeshMobilePhone"));
			fs.addField("PIN :", new EXInput("mkeshPin"));
			return fs;
		}else if(paymentMethod.equalsIgnoreCase("At Counter")){
			EXFieldSet fs = new EXFieldSet("info", "Payment at counter information", false);
			fs.addField("Date/Time of payment :", new EXDateTimePicker("dateTime"));
			fs.addField("Showroom for payment :", new EXInput("showroom"));
			return fs;
		}else if(paymentMethod.equalsIgnoreCase("Bank Transfer")){
			EXFieldSet fs = new EXFieldSet("info", "Payment By bank transfer", false);
			List<String> banks = SpringUtil.getRelationshipManager().getRelatedOrganizations("erevolution", "Bank");
			
			
			List<File> merchants = SpringUtil.getRepositoryService().executeQuery(new QueryParameters().setEntity(Merchant.class).addRestriction(Restrictions.in("username", banks)), Util.getRemoteUser());
			DefaultDataModel<File> model = new DefaultDataModel<File>(merchants);
			EXList<File> list = new EXList<File>("bankName", model);
			list.setRenderer(new ListItemRenderer<File>() {
				
				@Override
				public ListItem<File> getCellAt(int index, File value,DataModel<File> model,org.castafiore.ui.ex.form.list.List<File> holder) {
					Merchant m = (Merchant)value;
					EXListItem<File> item = new EXListItem<File>(value.getName(), "div");
					item.setStyle("width", "260px").addClass("EXGrid");
					//item.addChild(new EXContainer("", "img").setStyle("width", "50px").setStyle("height", "50px").setStyle("float", "left").setAttribute("src", m.getLogoUrl()));
					
					Container div = new EXContainer("", "div");
					div.addChild(new EXContainer("", "p").setText(m.getSummary()));
					item.addChild(div.setStyle("float", "right"));
					return item;
				}
			});
			
			fs.addField("Select Bank :", list);
			
//			fs.addField("To which bank :", new EXAutoComplete("bankName", "").setSource(new AutoCompleteSource() {
//				
//				@Override
//				public JArray getSource(String param) {
//					
//					
//					JArray array = new JArray();
//					for(Object o : merchants){
//						Merchant m =(Merchant)o;
//						String txt = "<img src='" + m.getLogoUrl() + "' width='48' height='48' style='float:left'></img>" + m.getCompanyName() + "";
//						//array.add(m.getUsername());
//						JMap map = new JMap();
//						map.put("label", m.getCompanyName()).put("value", m.getUsername()).put("icon", m.getLogoUrl()).put("desc", m.getSummary());
//						array.add(map);
//						
//					}
//					
//					return array;
//					//SpringUtil.getRepositoryService().executeQuery(new QueryParameters().setEntity(Merchant.class).addRestriction(Restrictions.eq("nature", value)), username)
//				}
//			}));
			//fs.addField("IBAN :", new EXInput("iban"));
			fs.addField("Account Number :", new EXInput("bankAccountNumber"));
			return fs;
		}else{
			return null;
		}
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		if(container.getName().equalsIgnoreCase("paymentMethod")){
			EXFieldSet fs = getDescendentOfType(EXFieldSet.class);
			Container parent = fs.getParent();
			fs.remove();
			parent.addChild(getPaymentInfoFields(container.getDescendentOfType(EXSelect.class).getValue().toString()));
		}else if(container.getName().equalsIgnoreCase("savePayment")){
			//throw new UIException("Payment methods still under construction");
			placeOrder();
			
		}
		
		return true;
	}
	
	public void placeOrder(){
		Order or = getAncestorOfType(EXPointOfSales.class).createOrder();
		
		String paymentType = ((EXSelect)getDescendentByName("paymentMethod")).getValue().toString();
		or.setPaymentMethod(paymentType);
		BillingInformation bif = or.getBillingInformation();
		List<StatefullComponent> stfs = getDescendentOfType(EXFieldSet.class).getFields();
		for(StatefullComponent stf : stfs){
			bif.setOtherProperty(stf.getName(), stf.getValue().toString());
		}
		
		or.save();
		
		getAncestorOfType(EXPurchaseOrdersPanel.class).showOrderList();
		getAncestorOfType(EXPurchaseOrdersPanel.class).setRendered(false);
		getAncestorOfType(EXPurchaseOrdersPanel.class).getDescendentOfType(EXSearchPurchases.class).search();
		getAncestorOfType(EXPointOfSales.class).getAncestorOfType(EXPanel.class).remove();
		//this.remove();
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
