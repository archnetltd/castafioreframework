package org.castafiore.searchengine.back;

import java.util.Map;

import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.checkout.Order;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.castafiore.wfs.types.Message;
import org.hibernate.criterion.Restrictions;

public class EXOSNotifications extends EXXHTMLFragment implements Event, EventDispatcher{

	public EXOSNotifications(String name) {
		super(name, "templates/EXOSNotifications.xhtml");
		addChild(new EXContainer("messages", "ul"));
		setAttribute("neworders" , "0");
		setAttribute("delivery", "0");
		setAttribute("toprepare", "0");
		setAttribute("messages", "0");
		addEvent(this,READY);
		setStyle("display", "none");
		addEvent(DISPATCHER, Event.CLICK);
	}
	
	
	public void addMessage(String message){
		getChild("messages").addChild(new EXContainer("", "li").setText(message));
		
	}
	public boolean check(){
		
		boolean result = false;
		getChild("messages").getChildren().clear();
		getChild("messages").setRendered(false);
		Merchant merchant = MallUtil.getCurrentMerchant();
		QueryParameters params = new QueryParameters()
												.setEntity(Order.class)
												.addRestriction(Restrictions.eq("orderedFrom", merchant.getUsername()))
												.addRestriction(Restrictions.eq("status", 10));
		int newOrders = SpringUtil.getRepositoryService().countRows(params, Util.getRemoteUser());
		int currentOrders = Integer.parseInt(getAttribute("neworders"));
		
		if(newOrders > currentOrders){
			addMessage("You have " + newOrders + " new orders to prepare");
			result = true;
		}
		
		params = new QueryParameters()
		.setEntity(Order.class)
		.addRestriction(Restrictions.eq("orderedFrom", merchant.getUsername()))
		.addRestriction(Restrictions.eq("status", 12));
		int ondelivery = SpringUtil.getRepositoryService().countRows(params, Util.getRemoteUser());
		int currentdelivery = Integer.parseInt(getAttribute("delivery"));
		
		if(ondelivery > currentdelivery){
			addMessage("You have " + ondelivery + " orders being delivered");
			result = true;
		}
		
		params = new QueryParameters()
		.setEntity(Message.class)
		.addRestriction(Restrictions.eq("destination", merchant.getUsername()));
		
		int messages = SpringUtil.getRepositoryService().countRows(params, Util.getRemoteUser());
		int currentMessages = Integer.parseInt(getAttribute("messages"));
		
		if(messages > currentMessages){
			addMessage("You have " + messages + " new messages");
			result = true;
		}
		
		return result;
		
	}


	@Override
	public void ClientAction(ClientProxy container) {
		container.setTimeout(container.clone().makeServerRequest(this), 50000);
		
	}


	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		boolean to = check();
		if(to){
			request.put("fadein", "true");
		}
		return true;
	}


	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		if(request.containsKey("fadein")){
			container.fadeIn(6000);
		}
		
	}


	@Override
	public void executeAction(Container source) {
		setStyle("display", "none");
		setRendered(false);
		
	}
	

}
