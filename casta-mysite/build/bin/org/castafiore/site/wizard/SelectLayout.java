package org.castafiore.site.wizard;

import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;

public class SelectLayout extends EXContainer implements Event{
	
	private final static String[] LAYOUTS= {"blank_layout", "simple_layout", "leftbar", "doubebar", "3rows"};
	
	private String selectedLayout = null;
	
	
	public SelectLayout() {
		super("selectLayout", "div");
		addClass("images_container");
		for(String s : LAYOUTS){
			addChild(new EXContainer(s, "img").setAttribute("src", "youdo/images/layouts/" + s+ ".png").addEvent(this, Event.CLICK));
		}
	}
	 
	

	public String getSelectedLayout() {
		return selectedLayout;
	}



	public void setSelectedLayout(String selectedLayout) {
		this.selectedLayout = selectedLayout;
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
		
		this.selectedLayout = container.getName();
		
		getAncestorOfType(GetStartedPage.class).getUserdate().setLayout(selectedLayout);
		
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
