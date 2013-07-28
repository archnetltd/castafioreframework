package org.castafiore.shoppingmall.crm;

import java.util.Map;

import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.shoppingmall.merchant.MerchantSubscription;
import org.castafiore.ui.Container;
import org.castafiore.ui.Droppable;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.js.JMap;
import org.castafiore.wfs.iterator.FileIterator;
import org.castafiore.wfs.types.Directory;

public class EXSubscriptionCategory extends EXContainer implements Droppable, Event{

	public EXSubscriptionCategory(String name) {
		super(name, "div");
		addEvent(this, Event.DND_DROP);
		addEvent(OVER, Event.DND_OVER);
		addEvent(OUT, Event.DND_OUT);
	}

	@Override
	public String[] getAcceptClasses() {
		return new String[]{"EXSubscriberTableListItem"};
	}

	@Override
	public JMap getDroppableOptions() {
		return new JMap();
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(new JMap().put("sourceid", container.getDragSourceId()),this, "Do you really want to move this user?");
		
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		String category = getAttribute("category");
		String source = request.get("sourceid");
		Container c = container.getAncestorOfType(EXSubscribersList.class).getDescendentById(source);
		String username = c.getAttribute("username");
		Merchant merchant = MallUtil.getCurrentMerchant();
		
		MerchantSubscription subscription = merchant.getSubscription(username);
		try{
			subscription.moveTo(merchant.getSubscriptionCategory(category).getAbsolutePath());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return true;
		
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}
	
	
	public final static Event OVER = new Event(){

		@Override
		public void ClientAction(ClientProxy container) {
			container.getParent().setStyle("background-color", "Tomato");
			
			
		}

		@Override
		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	
	public final static Event OUT = new Event(){

		@Override
		public void ClientAction(ClientProxy container) {
			container.IF(container.clone().getAttribute("sel").equal("true"), container.clone().getParent().setStyle("background-color", "steelblue"), container.clone().getParent().setStyle("background-color", "transparent"));
			
			
		}

		@Override
		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			// TODO Auto-generated method stub
			
		}
		
	};

}
