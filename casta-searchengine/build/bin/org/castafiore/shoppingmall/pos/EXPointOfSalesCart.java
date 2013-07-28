package org.castafiore.shoppingmall.pos;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.castafiore.catalogue.Product;
import org.castafiore.designable.CartItem;
import org.castafiore.designable.EXMiniCart;
import org.castafiore.designable.portal.PortalContainer;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.checkout.Order;
import org.castafiore.shoppingmall.checkout.SalesOrderEntry;
import org.castafiore.shoppingmall.merchant.FinanceUtil;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.ex.form.EXDatePicker;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.hibernate.criterion.Restrictions;

public class EXPointOfSalesCart extends EXMiniCart{

	public EXPointOfSalesCart(String name) {
		super(name);
		setTemplateLocation("templates/pos/EXPointOfSalesCart.xhtml");
		setStyleClass("").setStyle("margin-top", "0px");
		
		addChild(new EXItemsTable("tableList"));
	}
	
	public void update(){
		if(getItems().size() > 0){
			getChild("numItem").setText(getItems().size() + " item(s)");
			getChild("msubTotal").setText(StringUtil.toCurrency(FinanceUtil.getCurrentCurrency(),getSubTotal()));
			getChild("mtotal").setText(StringUtil.toCurrency(FinanceUtil.getCurrentCurrency(),getTotal()));
		}else{
			getChild("numItem").setText("Your cart is empty");
			getChild("msubTotal").setText(StringUtil.toCurrency(FinanceUtil.getCurrentCurrency(),0));
			getChild("mtotal").setText(StringUtil.toCurrency(FinanceUtil.getCurrentCurrency(),0));
		}
		
		getDescendentOfType(EXItemsTable.class).refresh();
	}
	
	public void setOrder(Order order){
		
		for(SalesOrderEntry entry :  order.getFiles(SalesOrderEntry.class).toList()){
			CartItem item = new CartItem();
			
			String productCode = entry.getProductCode();

			QueryParameters params = new QueryParameters();
			List l = SpringUtil.getRepositoryService().executeQuery(params.setEntity(Product.class).addRestriction(Restrictions.eq("providedBy", order.getOrderedFrom())).addRestriction(Restrictions.eq("code", item.getCode())), Util.getRemoteUser());
			
			if(l.size()> 0){
				Product p = (Product)l.get(0);
				entry.setProduct(item, p);
				item.setProduct(p);
				item.setQty(entry.getQuantity());
			}
			
		}
		update();
	}
	
	private Map<String,String> validate(){
		String player = getAncestorOfType(EXPointOfSales.class).getDescendentByName("player").getAncestorOfType(StatefullComponent.class).getValue().toString();
		
		String agent = getAncestorOfType(EXPointOfSales.class).getDescendentByName("agent").getAncestorOfType(StatefullComponent.class).getValue().toString();
		
		
		Date date = (Date)getAncestorOfType(EXPointOfSales.class).getDescendentOfType(EXDatePicker.class).getValue();
		
		Map<String, String> errors = new HashMap<String, String>();
		if(!StringUtil.isNotEmpty(player)){
			errors.put("player", "Please enter a valid supplier");
		}else{
			try{
				MallUtil.getMerchant(player);
			}catch(Exception e){
				errors.put("player", "The supplier " + player + " does not exist. Please choose another one. Click on the text box to open the search supplier panel");
			}
		}
		
		if(!StringUtil.isNotEmpty(agent)){
			errors.put("agent", "Please enter a valid agent");
		}
		
		if(getItems().size() == 0){
			errors.put("cart", "Please select at least a product");
		}
		
		return errors;
	}
	
	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
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
		}else{
			Map<String,String> error = validate();
			if(error.size() == 0)
				container.getAncestorOfType(PopupContainer.class).addPopup(new EXPointOfSalesPayment("payment"));
			else
				request.putAll(error);
		}
		
		return true;
	}
	
	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		if(request.containsKey("torem")){
			container.mergeCommand(new ClientProxy("#" + request.get("torem")).fadeOut(100));
		}else if(request.containsKey("player")){
			container.alert(request.get("player"));
		}else if(request.containsKey("agent")){
			container.alert(request.get("agent"));
		}else if(request.containsKey("cart")){
			container.alert(request.get("cart"));
		}
		
	}

}
