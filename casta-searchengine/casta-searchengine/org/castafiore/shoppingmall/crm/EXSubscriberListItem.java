package org.castafiore.shoppingmall.crm;

import java.util.Map;

import org.castafiore.catalogue.Product;
import org.castafiore.searchengine.EXMall;
import org.castafiore.security.User;
import org.castafiore.shoppingmall.list.AbstractListItem;
import org.castafiore.shoppingmall.product.ui.EXProductListDetail;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ComponentVisitor;

public class EXSubscriberListItem extends AbstractListItem implements Event, ComponentVisitor{
	private boolean open = false;
	
	
	public EXSubscriberListItem(String name) {
		super(name);
		
		
		//addChild(new EXContainer("td_name", "td").setStyle("vertical-align","top" ).addChild(new EXContainer("name","div")));
		addChild(new EXContainer("td_name", "td").setStyle("vertical-align","top" ).addChild(new EXContainer("name","a").addEvent(this, CLICK).setAttribute("href", "#").setText("").setStyle("font-weight", "bold").setStyle("color", "#111")));
		addChild(new EXContainer("td_email", "td").setStyle("vertical-align","top" ).addChild(new EXContainer("email","div")));
		addChild(new EXContainer("td_phone", "td").setStyle("vertical-align","top" ).addChild(new EXContainer("phone","div")));
		addChild(new EXContainer("td_mobile", "td").setStyle("vertical-align","top" ).addChild(new EXContainer("mobile","div")));
		
		//addChild(new EXContainer("td_image", "td").setStyle("vertical-align","top" ));
		//addChild(new EXContainer("td_username", "td").setStyle("vertical-align","top" ).addChild(new EXUserInfoLink("author")));
		//addChild(new EXContainer("td_email", "td").setStyle("vertical-align","top" ).addChild(new EXContainer("email","a").setAttribute("href", "#").setText("Thank you for your purchase").setStyle("font-weight", "bold").setStyle("color", "#111")));
		
		
	}

	public User getItem(){
		return SpringUtil.getSecurityService().loadUserByUsername(getAttribute("username"));
		
	}

	public void setItem(User user) {
		setAttribute("username", user.getUsername());
		getDescendentByName("name").setText(user.toString());
		getDescendentByName("email").setText(user.getEmail()).setAttribute("href", "mailto:" + user.getEmail());
		getDescendentByName("phone").setText(user.getPhone());		
		getDescendentByName("mobile").setText(user.getMobile());
	}
	
	
	public void open(){
		if(!open){
			EXSubscribersList list = getAncestorOfType(EXSubscribersList.class);
			ComponentUtil.iterateOverDescendentsOfType(list, EXSubscriberListItem.class,this);
			Container name = getDescendentByName("name");
//			Container title = getDescendentByName("td_title");
			
			EXSubscriberListDetail detail = getDescendentOfType(EXSubscriberListDetail.class);
//			if(userImg == null){
//				userImg = new EXContainer("userimg", "img").setAttribute("style", "width: 60px; height: 60px; margin-top: 0.8em;display:block").setAttribute("src", "http://www.space.com/common/forums/images/avatars/gallery/All/Avatar_gear.jpg");
//				code.getParent().addChild(userImg);
//			}
//			
//			userImg.setDisplay(true);
//			Product p = getItem();
//			userImg.setAttribute("src", p.getImageUrl(""));
			
//			if(detail == null){
//				detail = new EXSubscriberListItem("listDetail");
//				detail.setProduct(getItem());
//				title.addChild(detail);
//			}
//			detail.setDisplay(true);
//			if(getAncestorOfType(EXMall.class) != null){
//				detail.getChild("buttons").getChildren().clear();
//				detail.getChild("buttons").setDisplay(false);
//			}
			open = true;
		}
	}
	
	public void toggle(){
		if(open){
			close();
		}else{
			open();
		}
	}
	
	public void close(){
		if(open){
			Container userImg = getDescendentByName("userimg");
			if(userImg != null){
				userImg.setDisplay(false);
			}
			EXProductListDetail detail = getDescendentOfType(EXProductListDetail.class);
			if(detail != null){
				detail.setDisplay(false);
			}
			open = false;
		}
		
	}

	@Override
	public void ClientAction(ClientProxy container) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doVisit(Container c) {
		// TODO Auto-generated method stub
		
	}
}
