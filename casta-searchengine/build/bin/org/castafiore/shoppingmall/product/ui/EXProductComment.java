package org.castafiore.shoppingmall.product.ui;

import org.castafiore.security.User;
import org.castafiore.shoppingmall.user.ui.EXUserInfoLink;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.wfs.types.Comment;

public class EXProductComment extends EXContainer{

	public EXProductComment(String name) {
		super(name, "div");
		addClass("span-13 desc-wrapper");
		
		Container span = new EXContainer("", "div").addClass("span-3");
		addChild(span);
		span.addChild(new EXContainer("img", "img").setAttribute("src", "http://www.teline-tv.net/images/unknown-user.jpg").addClass("commentAuthor"));
		span.addChild(new EXUserInfoLink("author","span", "kureem").setStyle("clear", "both").setStyle("float", "left"));
		addChild(new EXContainer("description", "div").setText("A logo is a graphic mark or emblem commonly used by commercial enterprises, organizations and even individuals to aid and promote instant public recognition ..."));
		
	}
	
	
	public void setComment(Comment comment){
		User user = SpringUtil.getSecurityService().loadUserByUsername(comment.getAuthor());
		getDescendentOfType(EXUserInfoLink.class).setUser(user);
		//getChild(name)
		getDescendentByName("img").setAttribute("src", user.getAvatar());
		getDescendentByName("description").setText(comment.getSummary());
		
		
		
	}

}
