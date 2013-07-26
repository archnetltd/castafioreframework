package org.castafiore.shoppingmall.contacts.ui;

import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;



public class EXContactPanel extends EXContainer implements EventDispatcher{

	public EXContactPanel(String name, String title) {
		super(name, "div");
		
		addChild(new EXContainer("title", "h3").addClass("MessageListTitle").setText(title));
		addChild(new EXContainer("deleteAll", "button").setText("Delete"));
		addChild(new EXContainer("invite", "button").setText("Send invitation").addEvent(DISPATCHER, Event.CLICK));
		addChild(new EXContactList("list").setStyle("clear", "both"));
		
	}
	
	public void setTitle(String title){
		getChild("title").setText(title);
	}
	
	public void invite()throws Exception{
		getChild("deleteAll").setDisplay(false);
		getChild("invite").setDisplay(false);
		getChild("list").setDisplay(false);
		setTitle("Invite someone");
		EXInviteFriend newMessage = getDescendentOfType(EXInviteFriend.class);
		if(newMessage == null){
			newMessage = new EXInviteFriend("EXInviteFriend");
			addChild(newMessage);
		}
		newMessage.init();
		newMessage.setDisplay(true);
	}
	
	
	
	public void showList(){
		getChild("deleteAll").setDisplay(true);
		getChild("invite").setDisplay(true);
		getChild("list").setDisplay(true);
		
		Container newMessage = getChild("EXInviteFriend");
		if(newMessage != null){
			newMessage.setDisplay(false);
		}
	}

	public void showContacts(String category){
		
		showList();
		setTitle("Contacts - " + category);
		getDescendentOfType(EXContactList.class).showContacts(category);
		setTitle(category + " Contacts");
	}

	@Override
	public void executeAction(Container source) {
		if(source.getName().equals("invite")){
			try{
				invite();
			}catch(Exception e){
				throw new UIException(e);
			}
		}
		
	}


}
