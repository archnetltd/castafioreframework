package org.castafiore.shoppingmall.ng.v2;

import java.util.Map;

import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.cart.EXMerchantInfo;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.ui.scripting.EXXHTMLFragment;

public class EXMerchantItem extends EXXHTMLFragment implements Event{

	public EXMerchantItem(String name, Merchant m) {
		super(name, "templates/v2/EXMerchantItem.xhtml");
		addChild(new EXContainer("widgetTitle", "span").addClass("title").setText(m.getCompanyName()));
		addChild(new EXContainer("image", "img").setStyle("width", "128px").setAttribute("src", m.getLogoUrl()).setStyle("height", "128px").addEvent(this, CLICK));
		setAttribute("merchant", m.getUsername());
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		EXMerchantInfo info = new EXMerchantInfo("");
		EXPanel panel = new EXPanel("mm");
		panel.setBody(info);
		info.setMerchant(MallUtil.getMerchant(getAttribute("merchant")));
		container.getAncestorOfType(PopupContainer.class).addPopup(panel.setStyle("z-index", "3000").setStyle("width", "700px"));
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
