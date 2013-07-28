package org.castafiore.site.wizard;

import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;

public class SelectMenu extends EXContainer  implements Event{
	 
	
	private String selectedMenu = null;

	private final static String[] LAYOUTS= {"menu2", "menu3", "menu4", "menu5", "menu6", "menu7"};
	public SelectMenu() {
		super("SelectMenu", "div");
		addClass("banner_container");
		for(String s : LAYOUTS){
			addChild(new EXContainer(s, "img").setAttribute("src", "youdo/images/menu/Menu-CSStab designer/" + s+ "/" + s+".png").addEvent(this, Event.CLICK));
		}
	}
	
	
	
	
	
	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}


	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		for(Container c : getChildren()){
			c.setStyle("border-color", "silver");
		}
		container.setStyle("border-color", "steelBlue");
		
		this.selectedMenu = container.getName();
		getAncestorOfType(GetStartedPage.class).getUserdate().setMenu(selectedMenu);
		return true;
	}


	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}
}
