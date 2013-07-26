package org.castafiore.searchengine.back;

import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.panel.Panel;

public class OSBarItem extends EXContainer implements Event{
	
	private OSApplicationRegistry osApplicationRegistry;
	
	private String permission;
	
	public OSBarItem(){
		this("", "", "");
	}

	public OSBarItem(String title, String icon, String appName) {
		super("OSBarItem", "a");
		addChild(new EXContainer("img", "img").setAttribute("src", icon).setAttribute("height", "75"));
		setAttribute("href", "#");
		addEvent(this, Event.CLICK);
		setAttribute("title", title);
		setAttribute("appName", appName);
		
	}
	
	
	public String getPermission() {
		if(permission == null)
			return "";
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getTitle(){
		return getAttribute("title");
	}
	
	public void setTitle(String title){
		setAttribute("title", title);
		
	}
	
	
	public String getIcon(){
		return getDescendentByName("img").getAttribute("src");
	}
	
	public void setIcon(String icon){
		getDescendentByName("img").setAttribute("src", icon);
		
	}
	
	public void setAppName(String appName){
		setAttribute("appName", appName);
	}
	
	
	public String getAppName(){
		return getAttribute("appName");
	}
	
	
	

	public OSApplicationRegistry getOsApplicationRegistry() {
		return osApplicationRegistry;
	}

	public void setOsApplicationRegistry(OSApplicationRegistry osApplicationRegistry) {
		this.osApplicationRegistry = osApplicationRegistry;
	}

	
	public void executeAction(Container source) {
		String appName = getAttribute("appName");
		Container app = getAncestorOfType(OSInterface.class).getDeskTop().getDescendentByName(appName);
		if(app != null && app instanceof Panel){
			app.setDisplay(true);
			return;
		}
		
		app = getAncestorOfType(OSInterface.class).getChild("inits").getDescendentByName(appName);
		if(app != null && app instanceof Panel){
			app.setDisplay(true);
			return;
		}
		
		Panel window  = osApplicationRegistry.getWindow(getAttribute("appName"));
		window.setName(getAppName());
		getAncestorOfType(OSInterface.class).getDeskTop().addWindow(window, false);
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		executeAction(container);
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

	

}
