package org.castafiore.shoppingmall.product.ui;

import org.castafiore.shoppingmall.user.ui.EXUserInfoLink;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.wfs.types.Comment;

public class EXComment extends EXContainer{

	public EXComment(String name) {
		super(name, "div");
		addClass("span-13 desc-wrapper");
		
		Container span = new EXContainer("", "div").addClass("span-3");
		addChild(span);
		span.addChild(new EXContainer("img", "img").setAttribute("src", "http://www.teline-tv.net/images/unknown-user.jpg").addClass("commentAuthor"));
		span.addChild(new EXUserInfoLink("author","span", "").setStyle("clear", "both").setStyle("float", "left"));
		addChild(new EXContainer("description", "div").setText(""));
		
	}
	
	
	public void setComment(Comment comment){
		getDescendentOfType(EXUserInfoLink.class).setUsername(comment.getAuthor());
		getDescendentByName("description").setText(comment.getSummary());
		//getDescendentOfType(EXUserInfoLink.class)
		
		
	}

}
