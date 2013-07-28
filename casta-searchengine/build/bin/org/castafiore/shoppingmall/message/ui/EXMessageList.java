package org.castafiore.shoppingmall.message.ui;

import java.util.List;

import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXCheckBox;
import org.castafiore.wfs.types.Comment;
import org.castafiore.wfs.types.Message;

public class EXMessageList extends EXContainer{

	public EXMessageList(String name) {
		super(name, "table");
		EXContainer head = new EXContainer("", "thead");
		addChild(head);
		EXContainer tr = new EXContainer("", "tr");
		head.addChild(tr);
		
		tr.addChild(new EXContainer("", "th").setStyle("width", "15px").addChild(new EXCheckBox("selectAll")));
		tr.addChild(new EXContainer("to_header", "th").setStyle("width", "120px").setText("From"));
		tr.addChild(new EXContainer("", "th").setStyle("width", "280px").setText("Subject"));
		tr.addChild(new EXContainer("", "th").setText("Date"));
		EXContainer body = new EXContainer("body", "tbody");
		addChild(body);
	}
	
	
	public void setComments(List<Comment> comments){
		getChild("body").getChildren().clear();
		getChild("body").setRendered(false);
		getDescendentByName("to_header").setText("From");
		for(Comment message : comments){
			EXMessageListItem item = new EXMessageListItem("");
			item.setComment(message);
			int size = getChild("body").getChildren().size();
			if((size % 2) == 0){
				item.addClass("even");
			}else{
				item.addClass("odd");
			}
			getChild("body").addChild(item);
		}
	}
	
	public void setInbox(List<Message> messages){
		getChild("body").getChildren().clear();
		getChild("body").setRendered(false);
		getDescendentByName("to_header").setText("From");
		for(Message message : messages){
			EXMessageListItem item = new EXMessageListItem("");
			item.setRecievedMessage(message);
			int size = getChild("body").getChildren().size();
			if((size % 2) == 0){
				item.addClass("even");
			}else{
				item.addClass("odd");
			}
			getChild("body").addChild(item);
		}
	}

	
	public void setSentMessages(List<Message> messages){
		getChild("body").getChildren().clear();
		getChild("body").setRendered(false);
		getDescendentByName("to_header").setText("To");
		for(Message message : messages){
			EXMessageListItem item = new EXMessageListItem("");
			item.setSentMessage(message);
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
