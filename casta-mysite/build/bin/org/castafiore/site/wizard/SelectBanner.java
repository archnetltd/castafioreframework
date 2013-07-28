package org.castafiore.site.wizard;

import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;

public class SelectBanner extends EXContainer implements Event{

	private String selectedBanner = null;
	 
	private final static String[] LAYOUTS= {"banner1", "banner9", "banner2", "banner3", "banner4", "banner6"};
	public SelectBanner() {
		super("SelectBanner", "div");
		addClass("banner_container");
		for(String s : LAYOUTS){
			addChild(new EXContainer(s, "img").setAttribute("src", "youdo/images/banners/" + s+ ".jpg").addEvent(this, Event.CLICK));
		}
	}
	

	public String getSelectedBanner() {
		return selectedBanner;
	}

	public void setSelectedBanner(String selectedBanner) {
		this.selectedBanner = selectedBanner;
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
		
		this.selectedBanner = container.getName();
		
		getAncestorOfType(GetStartedPage.class).getUserdate().setBanner(selectedBanner + ".jpg");
		return true;
	}


	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}
}
