package org.castafiore.shoppingmall.user.ui;

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
public class EXUserInfoLink extends EXContainer implements EventDispatcher{

	/**
	 * Instantiates a new eX user info link.
	 *
	 * @param name the name
	 */
	public EXUserInfoLink(String name) {
		super(name, "a");
		setAttribute("href", "#");
		setUsername("");
		addEvent(DISPATCHER, Event.CLICK);
	}
	
	/**
	 * Instantiates a new eX user info link.
	 *
	 * @param name the name
	 * @param tag the tag
	 * @param username the username
	 */
	public EXUserInfoLink(String name, String tag, String username) {
		super(name, tag);
		setUsername(username);
	}
	
	/**
	 * Sets the username.
	 *
	 * @param username the new username
	 */
	public void setUsername(String username){
		if(StringUtil.isNotEmpty(username)){
			try{
				Merchant merchant =MallUtil.getMerchant(username);
				setAttribute("username", merchant.getUsername());
				setText(merchant.getTitle());
			}catch(Exception e){
				e.printStackTrace();
				try{
				setUser(SpringUtil.getSecurityService().loadUserByUsername(username));
				}catch(Exception ee){
					
				}
			}
		
			
		}
	}
	
	
	public void setUser(User user){
		setAttribute("username", user.getUsername());	
		setText(user.toString());
	}

	@Override
	public void executeAction(Container source) {
		//throw new UIException("must implement user card");
		try{
		String username = getAttribute("username");
		Merchant merchant =MallUtil.getMerchant(username);
		
		EXMerchantInfo info = new EXMerchantInfo("");
		EXPanel panel = new EXPanel("mm");
		panel.setBody(info);
		info.setMerchant(merchant);
		source.getAncestorOfType(PopupContainer.class).addPopup(panel.setStyle("z-index", "4000").setStyle("width", "700px"));
		}catch(Exception e){
			e.printStackTrace();
		}
		//getAncestorOfType(EXMall.class).showMerchantCard(getAttribute("username"));
		
	}

}
