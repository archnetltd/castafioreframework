package org.castafiore.shoppingmall.merchant;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.searchengine.EXMall;
import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.ui.Container;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.scripting.EXXHTMLFragment;

public class EXMerchantRotator extends EXXHTMLFragment implements EventDispatcher{

	private List<Merchant> merchants = new ArrayList<Merchant>(5);
	public EXMerchantRotator(String name) {
		super(name, "templates/EXMerchantRotator.xhtml");
		addChild(new EXContainer("logo","img"));
		addChild(new EXContainer("title","label"));
		addEvent(DISPATCHER, Event.CLICK);
		setStyle("cursor", "pointer");
	}
	
	
	public void addMerchant(Merchant merchant){
		merchants.add(merchant);
		if(merchants.size() == 1){
			getChild("logo").setAttribute("src", merchant.getLogoUrl());
			getChild("title").setText(merchant.getTitle());
			setAttribute("username", merchant.getUsername());
		}
	}


	@Override
	public void executeAction(Container source) {
		String sMerchant = getAttribute("username");
		getAncestorOfType(EXMall.class).showMerchantCard(sMerchant);
		
	}

}
