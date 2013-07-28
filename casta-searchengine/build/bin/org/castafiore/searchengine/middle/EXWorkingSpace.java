package org.castafiore.searchengine.middle;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.castafiore.searchengine.EXMall;
import org.castafiore.searchengine.right.EXAdvertisementPanel;
import org.castafiore.searchengine.top.EXSearchBar;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;

public class EXWorkingSpace extends EXContainer implements Event{
	
	private List<Class<?>> pageHistory = new LinkedList<Class<?>>();

	public EXWorkingSpace(String name) {
		super(name, "div");
		addClass("mallresultpanel").addClass("last").addClass("span-14");
		
	}

	public void viewOnly(Class<?> clzaa){ 
		
		if(pageHistory.size() > 0){
			Class<?> current = pageHistory.get(pageHistory.size()-1);
			if(current.equals(clzaa)){
				return;
			}
		}
		pageHistory.add(clzaa);
		for(Container c : getChildren()){
			if(clzaa.isAssignableFrom(c.getClass())){
				c.setDisplay(true);
			}else{
				c.setDisplay(false);
			}
			
		}
		if(pageHistory.size() <=1){
			getAncestorOfType(EXMall.class).getDescendentOfType(EXSearchBar.class).getDescendentByName("mainback").setDisplay(false);
		}else{
			getAncestorOfType(EXMall.class).getDescendentOfType(EXSearchBar.class).getDescendentByName("mainback").setDisplay(true);
		}
	}
	
	
	public void back(){
		if(pageHistory.size() >1){
			//remove current 
			pageHistory.remove(pageHistory.size()-1);
			//remove previous page
			Class<?> previous = pageHistory.remove(pageHistory.size()-1);
			viewOnly(previous);
		}
	}
	
	public void hideRight(){
		removeClass("span-14").addClass("span-20");
		getAncestorOfType(EXMall.class).getDescendentOfType(EXAdvertisementPanel.class).setDisplay(false);
	}
	
	public void showRight(){
		removeClass("span-20").addClass("span-14");
		getAncestorOfType(EXMall.class).getDescendentOfType(EXAdvertisementPanel.class).setDisplay(true);
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		back();
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
