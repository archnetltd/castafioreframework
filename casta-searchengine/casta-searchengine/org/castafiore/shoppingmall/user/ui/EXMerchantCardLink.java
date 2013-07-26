package org.castafiore.shoppingmall.user.ui;

import org.castafiore.searchengine.EXMall;
import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.security.User;
import org.castafiore.shoppingmall.cart.EXMerchantInfo;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.utils.StringUtil;

/**
 * The Class EXUserInfoLink.
 *
 * @author kureem
 */
public class EXMerchantCardLink extends EXContainer implements EventDispatcher{

	/**
	 * Instantiates a new eX user info link.
	 *
	 * @param name the name
	 */
	public EXMerchantCardLink(String name) {
		super(name, "a");
		setAttribute("href", "#");
		setMerchantUsername("");
		addEvent(DISPATCHER, Event.CLICK);
		setStyle("color", "green");
	}
	
	/**
	 * Instantiates a new eX user info link.
	 *
	 * @param name the name
	 * @param tag the tag
	 * @param username the username
	 */
	public EXMerchantCardLink(String name, String tag, String username) {
		super(name, tag);
		setMerchantUsername(username);
		addEvent(DISPATCHER, Event.CLICK);
	}
	
	/**
	 * Sets the username.
	 *
	 * @param username the new username
	 */
	public void setMerchantUsername(String username){
		if(StringUtil.isNotEmpty(username)){
			setMerchant(MallUtil.getMerchant(username));
		}
	}
	
	
	public void setMerchant(Merchant user){
		setAttribute("username", user.getUsername());	
		setText(SpringUtil.getSecurityService().loadUserByUsername(user.getName()).toString());
	}

	@Override
	public void executeAction(Container source) {
		EXMerchantInfo info = new EXMerchantInfo("");
		EXPanel panel = new EXPanel("mm");
		panel.setBody(info);
		info.setMerchant(MallUtil.getMerchant(getAttribute("username")));
		source.getAncestorOfType(PopupContainer.class).addPopup(panel.setStyle("z-index", "3000").setStyle("width", "700px"));
		
		//getAncestorOfType(.class).showMerchantCard(getAttribute("username"));
		
	}

}
