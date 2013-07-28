package org.castafiore.shoppingmall.contacts.ui;

import org.castafiore.contact.Contact;
import org.castafiore.security.User;
import org.castafiore.shoppingmall.list.AbstractListItem;
import org.castafiore.shoppingmall.user.ui.EXUserInfoLink;
import org.castafiore.shoppingmall.util.list.ListItem;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.wfs.Util;


public class EXContactListItem extends AbstractListItem implements ListItem<Contact> {
	
	

	public EXContactListItem(String name) {
		
		super(name);
		addChild(new EXContainer("td_image", "td").setStyle("vertical-align","top" ));
		addChild(new EXContainer("td_username", "td").setStyle("vertical-align","top" ).addChild(new EXUserInfoLink("author")));
		addChild(new EXContainer("td_email", "td").setStyle("vertical-align","top" ).addChild(new EXContainer("email","a").setAttribute("href", "#").setText("Thank you for your purchase").setStyle("font-weight", "bold").setStyle("color", "#111")));
		
		
	}
	
	@Override
	public Contact getItem(){
		Contact comment = (Contact)SpringUtil.getRepositoryService().getFile(getAttribute("path"), Util.getRemoteUser());
		return comment;
	}


	

	@Override
	public void setItem(Contact contact) {
		setAttribute("path", contact.getAbsolutePath());
		getDescendentOfType(EXUserInfoLink.class).setUsername(contact.getUsername());
		User user = contact.getUser();
		getDescendentByName("email").setText(user.getEmail()).setAttribute("href", "mailto:" + user.getEmail());
		getDescendentByName("td_image").addChild(new EXContainer("userimg", "img").setAttribute("style", "width: 60px; height: 60px; margin-top: 0.8em;display:block").setAttribute("src", "http://www.space.com/common/forums/images/avatars/gallery/All/Avatar_gear.jpg"));
		
	}


	


	
}
