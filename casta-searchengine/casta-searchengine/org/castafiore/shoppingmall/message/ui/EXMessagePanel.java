package org.castafiore.shoppingmall.message.ui;

import java.util.List;

import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.user.ShoppingMallUser;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.panel.EXOverlayPopupPlaceHolder;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ComponentVisitor;
import org.castafiore.wfs.types.Comment;
import org.castafiore.wfs.types.Message;

public class EXMessagePanel extends EXContainer implements EventDispatcher, PopupContainer{

	public EXMessagePanel(String name, String title) {
		super(name, "div");
		//setStyle("margin-left", "-10px");
		addChild(new EXContainer("title", "h3").addClass("MessageListTitle").setText(title));
		addChild(new EXContainer("deleteAll", "button").setText("Delete").addClass("ui-widget-header").addClass("ui-corner-all").addEvent(DISPATCHER, Event.CLICK));
		addChild(new EXContainer("compose", "button").setText("Compose").addClass("ui-widget-header").addClass("ui-corner-all").addEvent(DISPATCHER, Event.CLICK));
		addChild(new EXMessageList("list").setStyle("clear", "both"));
		addChild(new EXOverlayPopupPlaceHolder("popupPlaceHolder"));
		
	}
	
	public void setTitle(String title){
		getChild("title").setText(title);
	}
	
	public void composeMessage()throws Exception{
		EXNewMessagePopup popup = new EXNewMessagePopup("");
		popup.init();
		getAncestorOfType(PopupContainer.class).addPopup(popup);
	}
	
	public void replyMessage(Message preparedMessage )throws Exception{
		getChild("deleteAll").setDisplay(false);
		getChild("compose").setDisplay(false);
		getChild("list").setDisplay(false);
		setTitle("Reply message");
		EXNewMessage newMessage = getDescendentOfType(EXNewMessage.class);
		if(newMessage == null){
			newMessage = new EXNewMessage();
			addChild(newMessage);
		}
		newMessage.setTemplate(preparedMessage);
		newMessage.setDisplay(true);
	}
	
	public void showList(){
		getChild("deleteAll").setDisplay(true);
		getChild("compose").setDisplay(true);
		getChild("list").setDisplay(true);
		
		Container newMessage = getDescendentOfType(EXNewMessage.class);
		if(newMessage != null){
			newMessage.setDisplay(false);
		}
	}

	public void showSentMessage(){
		
		showList();
		ShoppingMallUser user = MallUtil.getCurrentUser();
		List<Message> messages = user.getSentMessages(0, 20);
		getDescendentOfType(EXMessageList.class).setSentMessages(messages);
		setTitle("Messages I have sent");
	}
	
	public void showInbox(){
		showList();
		ShoppingMallUser user = MallUtil.getCurrentUser();
		List<Message> messages = user.getPersonalMessages(0, 20);
		getDescendentOfType(EXMessageList.class).setInbox(messages);
		setTitle("Messages sent to me");
	}
	
	public void showSharedMessages(){
		showList();
		ShoppingMallUser user = MallUtil.getCurrentUser();
		List<Message> messages = user.getSharedMessages(0, 20);
		getDescendentOfType(EXMessageList.class).setInbox(messages);
		setTitle("Messages shared with me");
	}
	
	
	public void showDraftMessages(){
		showList();
		ShoppingMallUser user = MallUtil.getCurrentUser();
		List<Message> messages = user.getDraftMessages(0, 20);
		getDescendentOfType(EXMessageList.class).setInbox(messages);
		setTitle("Messages shared with me");
	}
	
	public void showAddedComments(){
		showList();
		ShoppingMallUser user = MallUtil.getCurrentUser();
		List<Comment> messages = user.getAddedComments(0, 20);
		getDescendentOfType(EXMessageList.class).setComments(messages);
		setTitle("Comments I have added");
	}


	@Override
	public void executeAction(Container source) {
		if(source.getName().equals("compose")){
			try{
				composeMessage();
			}catch(Exception e){
				throw new UIException(e);
			}
		}else if(source.getName().equals("deleteAll")){
			ComponentUtil.iterateOverDescendentsOfType(getDescendentOfType(EXMessageList.class), EXMessageListItem.class, new ComponentVisitor() {
				
				@Override
				public void doVisit(Container c) {
					EXMessageListItem item = (EXMessageListItem)c;
					if(item.isChecked()){
						item.delete();
						//item.remove();
						item.setDisplay(false);
					}
					
				}
			});
		}
		
	}

	@Override
	public void addPopup(Container popup) {
		getChild("popupPlaceHolder").addChild(popup);
		
	}


}
