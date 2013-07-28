package org.castafiore.searchengine.top;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.security.User;
import org.castafiore.shoppingmall.ui.MallLoginSensitive;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.utils.StringUtil;

public class EXTopBar extends EXContainer implements MallLoginSensitive, EventDispatcher{
	
	private List<String> categories = new ArrayList<String>();
	
	private static List<String> mine = new ArrayList<String>();
	static{
		mine.add("Walk Out");
		mine.add("Transactions");
		mine.add("My Account");
		mine.add("Wish List");
		mine.add("My subscriptions");
		mine.add("Inbox");
	}
	

	public EXTopBar() {
		super("TopBar", "div"); 
		//setDisplay(false);
		setStyle("visibility", "hidden");
		addClass("top").setStyle("height", "24px");
		addChild(new EXContainer("welcome", "label").setDisplay(false).setStyle("float", "left"));
	}
		
	
	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}




	public void init(){
		for(int i = 0; i < mine.size(); i ++){
			if(mine.get(i).equalsIgnoreCase("walk out")){
				addChild(new EXContainer("", "a").addClass("mine").setAttribute("action", mine.get(i)).setAttribute("href", "logout.jsp").setText(mine.get(i)));
			}else{
				addChild(new EXContainer("", "a").addClass("mine").setAttribute("action", mine.get(i)).setAttribute("href", "#").setText(mine.get(i)).addEvent(DISPATCHER, Event.CLICK) );
			}
		}
	}
	
	
	
	
	public void executeAction(Container source){
		getParent().getAncestorOfType(EventDispatcher.class).executeAction(source);
	}
	
	public void onLogin(String username){

		setStyle("visibility", "visible");
		User u = SpringUtil.getSecurityService().loadUserByUsername(username);
		getChild("welcome").setText("Welcome " + username).setDisplay(true);
		
	}

}
