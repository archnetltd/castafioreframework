package org.castafiore.shoppingmall.crm.subscriptions;

import org.castafiore.security.User;
import org.castafiore.shoppingmall.list.AbstractListItem;
import org.castafiore.shoppingmall.merchant.MerchantSubscription;
import org.castafiore.shoppingmall.user.ui.EXMerchantCardLink;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;

public class EXSubscriptionListItem extends AbstractListItem{
	public EXSubscriptionListItem(String name) {
		super(name);
		
		addChild(new EXContainer("td_username", "td").setStyle("vertical-align","top" ).addChild(new EXMerchantCardLink("author")));
		addChild(new EXContainer("td_email", "td").setStyle("vertical-align","top" ).addChild(new EXContainer("email","a").setAttribute("href", "#").setText("Thank you for your purchase").setStyle("font-weight", "bold").setStyle("color", "#111")));
		
		
	}

	public MerchantSubscription getItem(){
		return (MerchantSubscription)SpringUtil.getRepositoryService().getFile(getAttribute("path"), Util.getRemoteUser());
		
	}

	public void setItem(MerchantSubscription subscription) {
		setAttribute("path", subscription.getAbsolutePath());
		User user = SpringUtil.getSecurityService().loadUserByUsername(subscription.getMerchantUsername());
		getDescendentOfType(EXMerchantCardLink.class).setMerchantUsername(subscription.getMerchantUsername());
		getDescendentByName("author").setText(user.toString());
		getDescendentByName("email").setText(user.getEmail()).setAttribute("href", "mailto:" + user.getEmail());
		
	}
	
	
	public void deleteSubscription(){
		if(isChecked()){
			File f = SpringUtil.getRepositoryService().getFile(getAttribute("path"), Util.getRemoteUser());
			Directory parent = f.getParent();
			f.remove();
			parent.save();
			setDisplay(false);
		}
	}
}
