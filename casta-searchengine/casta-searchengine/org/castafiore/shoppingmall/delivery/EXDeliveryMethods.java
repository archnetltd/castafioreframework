package org.castafiore.shoppingmall.delivery;

import java.math.BigDecimal;
import java.util.List;

import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.security.Address;
import org.castafiore.security.User;
import org.castafiore.shoppingmall.checkout.Order;
import org.castafiore.shoppingmall.checkout.OrderEntry;
import org.castafiore.shoppingmall.merchant.FinanceUtil;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;

public class EXDeliveryMethods extends EXXHTMLFragment implements EventDispatcher{
	
	private Container source;
	
	public EXDeliveryMethods(String name) {
		super(name, "templates/EXDeliveryMethods.xhtml");
		addClass("exDelivery");
		addChild(new EXContainer("delPrice", "div").addClass("delprice"));
		addChild(new EXContainer("delTax", "div").addClass("deltax"));
		addChild(new EXContainer("totalDelPrice", "div").addClass("deltax"));
		addChild(new EXContainer("totalWeight", "div").addClass("delweight"));
		addChild(new EXContainer("warningMess", "div").addClass("warningM"));
		addChild(new EXInput("addressLine1"));
		addChild(new EXInput("addressLine2"));
		addChild(new EXInput("city"));
		addChild(new EXInput("phone"));
		addChild(new EXInput("fax"));
		addChild(new EXContainer("confirmDelivery", "button").setText("Confirm").addEvent(DISPATCHER, Event.CLICK));
		addChild(new EXContainer("cancel", "button").setText("Cancel").addEvent(DISPATCHER, Event.CLICK));
		setStyle("z-index", "3001");
		setStyle("position", "fixed");
	}
	
	public void setSource(Container source) {
		this.source = source;
	}

	public void setOrder(Order order){
		setAttribute("path", order.getAbsolutePath());
		//List<OrderEntry> order.getEntries()
		BigDecimal totalWeight = new BigDecimal(0);
		List<OrderEntry> entries = order.getEntries();
		for(OrderEntry p : entries){
			String message = canDeliver(p);
			if(message == null){
				totalWeight = totalWeight.add(p.getWeight());
			}else{
				addWarningMessage(message);
			}
		}
		
		if(totalWeight.doubleValue() > 70){
			addWarningMessage("This order is too heavy. Total shipment should not exceed 70 Kg");
		}
		setTotalWeight(totalWeight);
		BigDecimal price = getPrice(totalWeight);
		setPrice(price);
		//User u = SpringUtil.getSecurityService().loadUserByUsername(order.getOrderedBy());
		//setAddress(u);
	}
	
	
	private void setAddress(User u){
		Address addr = u.getDefaultAddress();
		if(addr != null){
		((EXInput)getChild("addressLine1")).setValue(addr.getLine1());
		((EXInput)getChild("addressLine2")).setValue(addr.getLine2());
		((EXInput)getChild("fax")).setValue(u.getFax());
		((EXInput)getChild("phone")).setValue(u.getPhone());
		((EXInput)getChild("city")).setValue(addr.getCity());
		}
		//((EXInput)getChild("fax")).setValue(addr.getLine1());
	}
	
	private BigDecimal getPrice(BigDecimal weight){
		double dWeight = weight.doubleValue();
		if(dWeight == 0){
			return new BigDecimal(0);
		}if(dWeight <= 30){
			return new BigDecimal(130);
		}else{
			double excess =( dWeight- 30) * 9;
			return new BigDecimal(130).add(new BigDecimal(excess* 9));
		}
		
	}
	
	private void setPrice(BigDecimal price){
		getChild("delPrice").setText(StringUtil.toCurrency(FinanceUtil.getCurrentCurrency(),price));
		double d = price.multiply(new BigDecimal(0.15)).doubleValue();
		d = Math.ceil(d);
		getChild("delTax").setText(StringUtil.toCurrency(FinanceUtil.getCurrentCurrency(),d ));
		getChild("totalDelPrice").setText(StringUtil.toCurrency(FinanceUtil.getCurrentCurrency(), (price.doubleValue() + d)) );
	}
	
	private void addWarningMessage(String message){
		getChild("warningMess").setDisplay(true).addChild(new EXContainer("", "div").addClass("warningMess").setText(message));
	}
	
	private void setTotalWeight(BigDecimal weight){
		getChild("totalWeight").setText(weight.toPlainString() + " Kg");
	}
	
	private String canDeliver(OrderEntry p){
		if(p.getLength() == null || p.getHeight() == null || p.getWidth() == null){
			return p.getTitle() + "cannot be delivered since we cannot find its size";
		}
		
		BigDecimal volume = p.getLength().multiply(p.getWidth().multiply(p.getHeight()));
		if(volume.doubleValue() > 30000){
			return p.getTitle() + " is too big to deliver";
		}
		return null;
	}
	
	
	public Delivery createDelivery(){
		Order order = (Order)SpringUtil.getRepositoryService().getFile(getAttribute("path"), Util.getRemoteUser());
		Delivery d = order.createFile("delivery", Delivery.class);
		d.setPrice(new BigDecimal(getChild("delPrice").getText().replace("MUR", "").trim()));
		return d;
	}

	@Override
	public void executeAction(Container source) {
		if(source.getName().equals("confirmDelivery")){
			createDelivery();
			if(this.source != null){
				this.source.setDisplay(false);
			}
			this.remove();
		}else if(source.getName().equalsIgnoreCase("cancel")){
			Container parent = getParent();
			parent.getChildren().clear();
			parent.setRendered(false);
		}
		
	}

}
