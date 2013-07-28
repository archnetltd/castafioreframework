package org.castafiore.shoppingmall.merchant;

import org.castafiore.searchengine.EXMall;
import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.message.ui.EXNewMessagePopup;
import org.castafiore.shoppingmall.ui.MallLoginSensitive;
import org.castafiore.shoppingmall.user.ui.EXMerchantCardLink;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.panel.EXOverlayPopupPlaceHolder;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.wfs.Util;

public class EXMerchantSearchResultItem extends EXXHTMLFragment implements EventDispatcher, PopupContainer, MallLoginSensitive{

	public EXMerchantSearchResultItem(String name) {
		super(name, "templates/EXMerchantSearchResult.xhtml");
		addChild(new EXMerchantCardLink("visitShop", "button", "").setText("Visit shop"));
		addChild(new EXContainer("addLine1","label"));
		addChild(new EXContainer("addLine2","label"));
		addChild(new EXContainer("addLine3","label"));
		addChild(new EXContainer("companyName","h3"));
		addChild(new EXContainer("email","label").addClass("email"));
		addChild(new EXContainer("fax","label"));
		addChild(new EXContainer("phone","label"));
		addChild(new EXContainer("mobilePhone","label"));
		
		addChild(new EXContainer("logo","img"));
		addChild(new EXContainer("website","a"));
		addChild(new EXContainer("summary","p"));	
		addChild(new EXContainer("category","span"));
		addChild(new EXContainer("category_1","span"));	
		addChild(new EXContainer("category_2","span"));	
		addChild(new EXContainer("category_3","span"));	
		addChild(new EXContainer("category_4","span"));
		
		addChild(new EXContainer("subscribe", "button").setText("Subscribe").addEvent(DISPATCHER, Event.CLICK));
		addChild(new EXContainer("sendMessage", "button").setText("Send Message").addEvent(DISPATCHER, Event.CLICK));
		if(Util.getRemoteUser() == null){
			getChild("subscribe").setDisplay(false);
			getChild("sendMessage").setDisplay(false);
		}
		
		addChild(new EXOverlayPopupPlaceHolder("overlay"));
	}
	
	

	
	public void setMerchant(Merchant merchant){
		setAttribute("path", merchant.getAbsolutePath());
		
		
		getDescendentOfType(EXMerchantCardLink.class).setAttribute("username", merchant.getUsername());	
		addLabel("addLine1", merchant.getAddressLine1());
		addLabel("addLine2", merchant.getAddressLine2());
		addLabel("addLine3", merchant.getCity());
		addLabel("companyName", merchant.getCompanyName());
		addLabel("email", merchant.getEmail());
		addLabel("mobilePhone", merchant.getMobilePhone());
		addLabel("fax", merchant.getFax());
		addLabel("phone", merchant.getPhone());
		getChild("logo").setAttribute("src", merchant.getLogoUrl());
		getChild("website").setAttribute("href", merchant.getWebsite()).setAttribute("target", "_blank").setText(merchant.getWebsite());
		addLabel("summary", merchant.getSummary());
		addLabel("category", merchant.getCategory());
		addLabel("category_1", merchant.getCategory_1());
		addLabel("category_2", merchant.getCategory_2());
		addLabel("category_3", merchant.getCategory_3());
		addLabel("category_4", merchant.getCategory_4());
	}
	
	private void addLabel(String name, String value){
		getChild(name).setText(value);
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
		
		getChild("subscribe").setDisplay(true);
		getChild("sendMessage").setDisplay(true);
	}

}
