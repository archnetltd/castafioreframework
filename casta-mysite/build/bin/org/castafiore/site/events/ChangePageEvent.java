package org.castafiore.site.events;

import java.util.Map;

import org.castafiore.site.YoudoPortal;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;

public class ChangePageEvent implements Event {

	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		String name=  container.getName();
		YoudoPortal portal = container.getAncestorOfType(YoudoPortal.class);
		portal.gotoPage(name);
		
//		if(name.equalsIgnoreCase("home")){
//			if(portal.getDescendentByName("slider")==null){
//				portal.addChild(new EXXHTMLFragment("slider", "youdo-templates/slider.xhtml"));
//				
//				//services
//				portal.addChild(new EXXHTMLFragment("services", "youdo-templates/services.xhtml"));
//			}
//			
//			
//		}else{
//			if(portal.getDescendentByName("slider")!=null){
//				portal.getDescendentByName("slider").remove();
//			}
//			
//				
//			if(portal.getDescendentByName("services")!=null){
//				portal.getDescendentByName("services").remove();
//			}
//		}
//		
//		Container pageContainer=	 portal.getDescendentByName("pageContainer");
//		pageContainer.getChildren().clear();
//		pageContainer.setRendered(false);
//		
//		
//		if(name.equalsIgnoreCase("get started")){
//			pageContainer.addChild(new GetStartedPage(""));
//		}else{
//			pageContainer.addChild(new EXXHTMLFragment("page", "youdo-templates/pages/" + name + ".xhtml"));
//		}
		
		
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
