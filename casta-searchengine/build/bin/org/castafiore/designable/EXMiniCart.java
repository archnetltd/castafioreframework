package org.castafiore.designable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.castafiore.catalogue.Product;
import org.castafiore.designable.checkout.EXCheckout;
import org.castafiore.designable.portal.PortalContainer;
import org.castafiore.designer.marshalling.DesignableUtil;
import org.castafiore.searchengine.back.OSInterface;
import org.castafiore.shoppingmall.checkout.Order;
import org.castafiore.shoppingmall.checkout.SalesOrderEntry;
import org.castafiore.shoppingmall.merchant.FinanceUtil;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.RefreshSentive;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.BinaryFile;

public class EXMiniCart extends AbstractXHTML implements Event, RefreshSentive, CurrencySensitive{
	
	protected List<CartItem> items = new ArrayList<CartItem>();

	public EXMiniCart(String name) {
		super(name);
		
		addClass("cart-widget").setStyle("margin-top", "30px");
		
		
		
		addChild(new EXContainer("numItem", "span"));
		
		addChild(new EXContainer("msubTotal", "span"));
		addChild(new EXContainer("mtotal", "span"));
		
		addChild(new EXButton("checkout", "Checkout").setStyle("margin", "auto").setStyle("float", "none").addEvent(this, Event.CLICK));
		try{
		update();
		}catch(Exception e){
			
		}
	}
	
	public List<CartItem> getItems(){
//		HttpSession session = (HttpSession)getRoot().getConfigContext().get("session");
//		Map<String, List<CartItem>> carts = (Map<String, List<CartItem>>)session.getAttribute("shoppingCarts");
//		if(carts == null){
//			carts = new HashMap<String, List<CartItem>>();
//			session.setAttribute("shoppingCarts", carts);
//		}
//		if(carts.containsKey(getName())){
//			return carts.get(getName());
//		}else{
//			carts.put(getName(), new ArrayList<CartItem>());
//			 return carts.get(getName());
//		}
		return items;
	}
	public void createItems(Order order){
		for(CartItem item : getItems()){
			Product p = (Product)SpringUtil.getRepositoryService().getFile(item.getAbsolutePath(), Util.getRemoteUser());
			SalesOrderEntry entry = order.createFile(item.getCode(), SalesOrderEntry.class);
			entry.setProduct(item,p);
		}
	}
	
	
	public void update(){
		if(getItems().size() > 0){
			getChild("numItem").setText(getItems().size() + " item(s)");
			getChild("msubTotal").setText("Cart subtotal :" + StringUtil.toCurrency(FinanceUtil.getCurrentCurrency(),getSubTotal()));
			getChild("mtotal").setText(StringUtil.toCurrency(FinanceUtil.getCurrentCurrency(),getTotal()) + " Incl: VAT");
		}else{
			getChild("numItem").setText("Your cart is empty");
			getChild("msubTotal").setText("");
			getChild("mtotal").setText("");
		}
		
		Container images = getChild("images");
		if(images != null){
			images.getChildren().clear();
			images.setRendered(false);
			for(CartItem item : getItems()){
					images.addChild(new EXContainer("img", "img").setStyle("cursor", "pointer").setAttribute("path", item.getAbsolutePath()).addEvent(this, CLICK).setStyle("margin", "4px 4px 0px 4px").setStyle("width", "30px").setStyle("height", "30px").setAttribute("src", item.getImg()));
			}
			
			if(getItems().size() == 0){
				setDisplay(false);
			}else{
				setDisplay(true);
			}
		}
		
		BigDecimal total = new BigDecimal(0);
		if(getParent() != null){
			for(Container c : getParent().getChildren()){
				if(c instanceof EXMiniCart){
					total = ((EXMiniCart)c).getTotal().add(total);
				}	
			}
			Container totalll = getParent().getDescendentByName("totalll");
			if(totalll != null){
				totalll.setText("Cart total :" + StringUtil.toCurrency(FinanceUtil.getCurrentCurrency(), total));
			}
		}
	}
	
	public CartItem saveOptions(Container c){
		Container root = c.getAncestorOfType(PortalContainer.class);
		String xml = DesignableUtil.generateXML(root, null);
		Product product = (Product)SpringUtil.getRepositoryService().getFile(c.getAttribute("path"), Util.getRemoteUser());
		CartItem item = new CartItem();
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
	
	
	public BigDecimal getTotal(){
		BigDecimal t = new BigDecimal("0");
		for(CartItem item : getItems()){
			t = t.add(item.getQty().multiply(item.getTotalPrice()));
		}
		return t;
	}
	
	public BigDecimal getSubTotal(){
		BigDecimal t = new BigDecimal("0");
		for(CartItem item : getItems()){
			t = t.add(item.getQty().multiply(item.getPriceExcludingTax()));
		}
		return t;
	}
	
	

	protected Container getOptions(Product product, int qty, Container source){
		try{
			Container c = getRoot().getDescendentOfType(OSInterface.class).getChild("inits").getChild("ProductOptionsCache").getChild(product.getName());
			if(c!= null){
				if(c.getDescendentOfType(EXDynaformPanel.class) != null && c.getDescendentOfType(EXDynaformPanel.class).getFields().size() > 0){
					Container save = c.getDescendentByName("saveoptions");
					save.setAttribute("qty", qty + "").setAttribute("path", product.getAbsolutePath());
					if(save.getEvents().containsKey(Event.CLICK) && save.getEvents().get(Event.CLICK).size() > 0){
						
					}else{
						save.addEvent(this, CLICK);
						c.getDescendentByName("canceloptions").addEvent(EXDynaformPanel.HIDE_EVENT, CLICK);
					}
					c.setStyle("visibility", "visible !important");
					
					return c;
				}
				return null;
				
			}
		}catch(Exception e ){
			e.printStackTrace();
		}
		return null;
	}
	
	public BigDecimal getQty(String productPath){
		CartItem item = getItem(productPath);
		if(item != null){
			return item.getQty();
		}else{
			return new BigDecimal(0);
		}
		//return getItem(productPath).getQty()
	}
	
	public CartItem addToCart(Product product, int qty, Container source){
		Container c = getOptions(product, qty, source);
		if(c == null){

		
			CartItem item = getItem(product.getAbsolutePath());
			if(item == null){
				item = new CartItem();
				item.setProduct(product);
				item.setQty(new BigDecimal(qty));
				getItems().add(item);
			}else{
				
				item.setQty(item.getQty().add(new BigDecimal(qty)));
				
			}
			update();
			return item;
		}else{
			CartItem item = new CartItem();
			item.request.put("container", c.getId());
			item.request.put("method", "appendTo");
			item.request.put("param", source.getAncestorOfType(PopupContainer.class).getId());
			return item;
			
		}
	}
	
	public CartItem addToCart(Product product, int qty, Container source, Container options){
		//Container c = getOptions(product, qty, source);
		return saveOptions(options);
		
	}
	
	public void removeItem(String path){
		CartItem item = getItem(path);
		if(item != null){
			getItems().remove(item);
		}
		
		
		update();
	}
	
	public void updateQty(String path, BigDecimal qty){
		CartItem item = getItem(path);
		item.setQty(qty);
		update();
	}
	
	
	public CartItem getItem(String productPath){
		for(CartItem item : getItems()){
			if(item.getAbsolutePath().equals(productPath)){
				return item;
			}
		}
		return null;
	}


	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	public void setItems(List<CartItem> items){
		getItems().clear();
		getItems().addAll(items);
		
		update();
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
		}
		EXEcommerce ecommerce = container.getAncestorOfType(EXEcommerce.class);
		
		if(ecommerce != null){
			EXCartDetail cart =(EXCartDetail)ecommerce.getPageOfType(EXCartDetail.class);
			if(cart == null){
				cart = new EXCartDetail("");
				ecommerce.getBody().addChild(cart);
			}
			cart.init(this);
			ecommerce.showPage(EXCartDetail.class);
		}else{
			
			EXCheckout cat = new EXCheckout("");
			cat.setStyle("z-index", "4000");
			cat.setAttribute("merchant", getAttribute("merchant"));
			cat.setAttribute("cartid", getAttribute("cartid"));
			container.getAncestorOfType(PopupContainer.class).addPopup(cat);
			
		}
		
		return true;
	}


	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		if(request.containsKey("torem")){
			container.mergeCommand(new ClientProxy("#" + request.get("torem")).fadeOut(100));
		}
		
	}

	@Override
	public void onRefresh() {
		update();
		
	}

	@Override
	public void changeCurrency() throws Exception {
		String newCurrency = FinanceUtil.getCurrentCurrency();
		for(CartItem item : getItems()){
			item.changeCurrency(newCurrency);
		}
		update();
		
	}
	

}
