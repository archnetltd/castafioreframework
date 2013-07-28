package com.eliensons.ui.sales;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.castafiore.SimpleKeyValuePair;
import org.castafiore.catalogue.Product;
import org.castafiore.designable.CartItem;
import org.castafiore.designable.EXCartDetail;
import org.castafiore.designable.EXEcommerce;
import org.castafiore.designable.EXMiniCart;
import org.castafiore.designable.checkout.EXCheckout;
import org.castafiore.designable.portal.PortalContainer;
import org.castafiore.designer.marshalling.DesignableUtil;
import org.castafiore.shoppingmall.merchant.FinanceUtil;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.form.EXDatePicker;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ComponentVisitor;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.json.JSONArray;
import org.json.JSONObject;

import com.eliensons.ui.ElieNSonsUtil;
import com.eliensons.ui.plans.ElieNSonsCartItem;

public class EXElieNSonsMiniCart extends EXMiniCart {

	public EXElieNSonsMiniCart(String name) {
		super(name);
		setStyle("margin-top", "0px");
		addChild(new EXButton("clear", "Clear").setStyleClass("ui-state-default ui-corner-all").setStyle("width", "90px").setStyle("margin", "auto").setStyle("float", "none").addEvent(this, Event.CLICK));
		getChild("clear").setStyle("margin", "10px").setStyle("display", "inline").getParent().setStyle("text-align", "center");
		getChild("checkout").setStyle("margin", "10px").setStyle("display", "inline");
	}
	
	public CartItem addToCart(Product product, int qty, Container source){
		Container c = getOptions(product, qty, source);
		if(c == null){

		
			CartItem item = getItem(product.getAbsolutePath());
			if(item == null){
				item = new ElieNSonsCartItem();
				item.setProduct(product);
				item.setQty(new BigDecimal(qty));
				getItems().add(item);
			}else{
				
				item.setQty(item.getQty().add(new BigDecimal(qty)));
				
			}
			update();
			return item;
		}else{
			CartItem item = new ElieNSonsCartItem();
			item.request.put("container", c.getId());
			item.request.put("method", "appendTo");
			item.request.put("param", source.getAncestorOfType(PopupContainer.class).getId());
			return item;
			
		}
	}
	
	public CartItem saveOptions(Container c){
		Container root = c.getAncestorOfType(PortalContainer.class);
		String xml = DesignableUtil.generateXML(root, null);

		Product product = (Product)SpringUtil.getRepositoryService().getFile(c.getAttribute("path"), Util.getRemoteUser());
		CartItem item = new ElieNSonsCartItem();
		item.setOptions(xml);
		item.setProduct(product);
		item.setQty(new BigDecimal(Integer.parseInt(c.getAttribute("qty"))));
		
		getItems().add(item);
		update();
		
		Container totalll = getParent().getDescendentByName("totalll");
		if(totalll != null){
			BigDecimal total = new BigDecimal(0);
			for(Container cc : getParent().getChildren()){
				if(cc instanceof EXMiniCart){
					total = ((EXMiniCart)cc).getTotal().add(total);
				}	
			}
			totalll.setText("Cart total :" + StringUtil.toCurrency(FinanceUtil.getCurrentCurrency(), total));
		}
		Container parent = root.getParent();
		root.remove();
		parent.setRendered(false);
		return item;
	}
	
	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		if(container.getName().equals("clear")){
			setItems(new ArrayList<CartItem>());
			return true;
		}
		
		if(container.getName().equals("saveoptions")){
			request.put("torem", container.getAncestorOfType(EXPanel.class).getId());
			saveOptions(container);
			return true;
		}else if(container.getName().equals("canceloptions")){
			request.put("torem", container.getAncestorOfType(EXPanel.class).getId());
			Container root = container.getAncestorOfType(PortalContainer.class);
			Container parent = root.getParent();
			root.remove();
			parent.setRendered(false);
			return true;
		}
			
			
		EXElieNSonsSales sales = getAncestorOfType(EXElieNSonsSales.class);
		EXInput inv = (EXInput)sales.getDescendentByName("invoiceNumber");
		
		
		EXDatePicker picker = (EXDatePicker)sales.getDescendentByName("invoiceDate");
		
		if(getItems().size() == 0){
			request.put("noitem", "true");
			return true;
		}
		
		if(StringUtil.isNotEmpty(inv.getValue().toString())){
			
			String invCode = inv.getValue().toString();
			String res = ElieNSonsUtil.checkInvNumber(invCode);
			if("ok".equals(res)){

				if(StringUtil.isNotEmpty(picker.getRawValue())){
					EXCheckout cat = new EXElieNSonsCheckout("", this);
					cat.setStyle("z-index", "4000");
					cat.setAttribute("merchant", getAttribute("merchant"));
					cat.setAttribute("cartid", getAttribute("cartid"));
					container.getAncestorOfType(PopupContainer.class).addPopup(cat);
					ElieNSonsUtil.useInvNumber(invCode);
					return true;
				}else{
					request.put("noinvdate", "true");
					return true;
				}
			}else{
				request.put(res, "true");
				return true;
			}
		}else{
			request.put("noinv", "true");
			return true;
		}
			
	}
	
	public void update(){
		super.update();
		
		if(getItems().size() > 0){
			getChild("numItem").setText(getItems().size() + " item(s)");
			getChild("msubTotal").setText(getItems().get(0).getTitle());
			getChild("mtotal").setText(StringUtil.toCurrency(FinanceUtil.getCurrentCurrency(),getTotal()));
		}else{
			getChild("numItem").setText("Your cart is empty");
			getChild("msubTotal").setText("");
			getChild("mtotal").setText("");
		}
	}
	
	public String getInvoiceNumber(){
		EXElieNSonsSales sales = getAncestorOfType(EXElieNSonsSales.class);
		EXInput inv = (EXInput)sales.getDescendentByName("invoiceNumber");
		return inv.getValue().toString();
	}
	
	public Date getNextPaymentDate(){
		Date d = getStatePaymentDate();
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		if(cal.get(Calendar.DATE) <=14){
			cal.add(Calendar.MONTH, 1);
		
		}else{
			cal.add(Calendar.MONTH, 2);
		}
		
		cal.set(Calendar.DATE, 1);
		return cal.getTime();
		
	}
	
	public Date getInvoiceDate(){
		 EXDatePicker picker = (EXDatePicker)getAncestorOfType(EXElieNSonsSales.class).getDescendentByName("invoiceDate");
		  return (Date)picker.getValue();
	}
	
	public Date getStatePaymentDate(){
		 EXSelect s = (EXSelect)getAncestorOfType(EXElieNSonsSales.class).getDescendentByName("installmentDate");
		 int months = Integer.parseInt(((SimpleKeyValuePair)s.getValue()).getKey());
		 
		 Calendar cal = Calendar.getInstance();
		 cal.setTime(getInvoiceDate());
		 cal.add(Calendar.MONTH, months);
		  return cal.getTime();
	}
	public String getPos(){
		EXSelect picker = (EXSelect)getAncestorOfType(EXElieNSonsSales.class).getDescendentByName("pos");
		  return picker.getValue().toString();
	}
	
	
	public String getAgent(){
		EXSelect picker = (EXSelect)getAncestorOfType(EXElieNSonsSales.class).getDescendentByName("agent");
		 return picker.getValue().toString();
	}
	
	public String getSource(){
		EXSelect picker = (EXSelect)getAncestorOfType(EXElieNSonsSales.class).getDescendentByName("fromSource");
		 return picker.getValue().toString();
	}
	
	public BigDecimal getInstallment(){
		ElieNSonsCartItem item = (ElieNSonsCartItem)getItems().get(0);
		return item.getInstallment();	
	}
	
	public BigDecimal getJoiningFee(){
		ElieNSonsCartItem item = (ElieNSonsCartItem)getItems().get(0);
		return item.getJoiningFee();
	}
	
	public boolean isPayingAdvance(){
		ElieNSonsCartItem item = (ElieNSonsCartItem)getItems().get(0);
		return "oui".equalsIgnoreCase(item.getAdvance());
	}
	
	public long getTimeIn(){
		return Long.parseLong(getAncestorOfType(EXElieNSonsSales.class).getAttribute("timeIn"));
	}
	
	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		super.Success(container, request);
		if(request.containsKey("noinv")){
			container.alert("Please enter an invoice number");
		}else if(request.containsKey("noitem")){
			container.alert("Please select a plan");
		}else if(request.containsKey("noinvdate")){
			container.alert("Please select an invoice date");
		}else if(request.containsKey("used")){
			container.alert("This FS code has already been used. Please choose another");
		}else if(request.containsKey("notprinted")){
			container.alert("This FS code has not been printed yet. Please choose another");
		}
	}

}
