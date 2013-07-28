package org.castafiore.shoppingmall.message.ui;

import org.castafiore.catalogue.Product;
import org.castafiore.searchengine.EXMall;
import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.user.ShoppingMallUser;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.wfs.types.Comment;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;
import org.castafiore.wfs.types.Message;

public class EXMessageListDetail extends EXContainer implements EventDispatcher{

	public EXMessageListDetail(String name) {
		super(name, "div");
		addClass("body").setStyle("margin-top", "0.8em");
		addChild(new EXContainer("summary", "p"));
//		addChild(
//				new EXContainer("buttons", "div")
//				.addChild(new EXContainer("delete", "button").setText("Delete").addEvent(DISPATCHER, Event.CLICK))
//				.addChild(new EXContainer("reply", "button").setText("Reply").addEvent(DISPATCHER, Event.CLICK))
//				//.addChild(new EXContainer("spam", "button").setText("Mark as spam"))
//		);
		
	}
	
	
	public void setComment(Comment comment){
		getChild("summary").setText(comment.getSummary());
		if(comment.getClazz().equals(Comment.class.getName())){
			setAttribute("iscomment", "true");
		}else{
			setAttribute("iscomment", "false");
		}
	}

	
	public void reply(Message message){
		try{
			ShoppingMallUser user = MallUtil.getCurrentUser();
			
			Message reply = user.createMessage("Reply to " + message.getName());//new Message();
			//reply.setName( "Reply to " + message.getName());
			reply.setTitle("Re - " + message.getTitle());
			reply.setSummary("\n\r-------------------------------------------------\n\r" + message.getSummary());
			reply.setAuthor(user.getUser().getUsername());
			reply.setDestination(message.getAuthor());
			getAncestorOfType(EXMessagePanel.class).replyMessage(reply);
		}catch(Exception e){
			throw new UIException(e);
		}
	}

	@Override
	public void executeAction(Container source) {
		if(source.getName().equals("delete")){
			Comment comment = getAncestorOfType(EXMessageListItem.class).getItem();
			comment.setStatus(File.STATE_DELETED);
		}else if(source.getName().equals("reply")){
			Comment comment = getAncestorOfType(EXMessageListItem.class).getItem();
			if(comment.getClazz().equals(Comment.class.getName())){
				Directory parent = comment.getParent();
				if(parent instanceof Product){
					getAncestorOfType(EXMall.class).showProductCard((Product)parent);
				}
			}else{
				reply((Message)comment);
			}
		}
		
	}

}
