package org.castafiore.shoppingmall.merchant;

import org.castafiore.searchengine.EXMall;
import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.message.ui.EXNewMessagePopup;
import org.castafiore.shoppingmall.ui.MallLoginSensitive;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.panel.EXOverlayPopupPlaceHolder;
import org.castafiore.wfs.Util;

public class EXMerchantCardPanel extends EXContainer implements EventDispatcher, PopupContainer, MallLoginSensitive{

	public EXMerchantCardPanel(String name) {
		super(name, "div");
		addChild(new EXOverlayPopupPlaceHolder("overlay"));
		addChild(new EXContainer("actions", "div").addClass("merchant-card-bar"));
		
		Container actions = getChild("actions");
		actions.addChild(new EXContainer("sendMessage", "button").setText("Send Message").addEvent(DISPATCHER, Event.CLICK));
		actions.addChild(new EXContainer("subscribe", "button").setText("Subscribe").addEvent(DISPATCHER, Event.CLICK));
		
		if(Util.getRemoteUser() == null){
			actions.setStyle("display", "none");
		}
		addChild(new EXMerchantCard("EXMerchantCard"));
		
	}
	
	
	public void setMerchant(Merchant merchant){
		setAttribute("path", merchant.getAbsolutePath());
		getDescendentOfType(EXMerchantCard.class).setMerchant(merchant);
		setAttribute("username", merchant.getUsername());
	}
	
	public void subscribe(){
		Merchant merchant = (Merchant)SpringUtil.getRepositoryService().getFile(getAttribute("path"), Util.getRemoteUser());
		merchant.subscribe(MallUtil.getCurrentUser().getUser().getUsername());
		merchant.save();
		getAncestorOfType(EXMall.class).showMessage("Successfully subscribed to merchant " + merchant.getCompanyName());
		
	}


	@Override
	public void executeAction(Container source) {
		if(source.getName().equalsIgnoreCase("subscribe")){
			subscribe();
		}else if(source.getName().equalsIgnoreCase("sendMessage")){
			EXNewMessagePopup message = new EXNewMessagePopup(getAttribute("username"));
			message.init();
			message.setStyle("z-index", "3001").setStyle("border", "double 3px silver").setStyle("background", "steelblue");
			addPopup(message);
			
		}
		
	}


	@Override
	public void addPopup(Container popup) {
		getChild("overlay").addChild(popup);
		
	}


	@Override
	public void onLogin(String username) {
		getChild("actions").setDisplay(true);
		
	}

}
