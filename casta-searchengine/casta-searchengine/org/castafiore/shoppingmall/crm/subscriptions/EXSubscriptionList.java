package org.castafiore.shoppingmall.crm.subscriptions;

import java.util.List;

import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.merchant.MerchantSubscription;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXCheckBox;

public class EXSubscriptionList extends EXContainer{
	public EXSubscriptionList(String name) {
		super(name, "table");
		EXContainer head = new EXContainer("", "thead");
		addChild(head);
		EXContainer tr = new EXContainer("", "tr");
		head.addChild(tr);
		
		tr.addChild(new EXContainer("", "th").setStyle("width", "15px").addChild(new EXCheckBox("selectAll")));
		tr.addChild(new EXContainer("", "th").setStyle("width", "300px").setText("Name"));
		tr.addChild(new EXContainer("", "th").setText("E-Mail"));
		EXContainer body = new EXContainer("body", "tbody");
		addChild(body);
	}
	
	
	
	public void showSubscriptions(){
		
		getChild("body").getChildren().clear();
		getChild("body").setRendered(false);
		
		
		
		List<MerchantSubscription> subs = MallUtil.getCurrentUser().getSubscriptions();
		for(MerchantSubscription sub : subs){
			EXSubscriptionListItem item = new EXSubscriptionListItem("");
			item.setItem(sub);
			int size = getChild("body").getChildren().size();
			if((size % 2) == 0){
				item.addClass("even");
			}else{
				item.addClass("odd");
			}
			getChild("body").addChild(item);
		}
	}
}
