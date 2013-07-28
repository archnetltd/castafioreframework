package org.castafiore.searchengine;

import org.castafiore.designer.EXDesigner;
import org.castafiore.searchengine.back.EXWindow;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXApplication;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.BinaryFile;

public class DesignerOnly extends EXApplication{

	public DesignerOnly() {
		super("visualgroovy");
		
		try{
			SpringUtil.getSecurityService().login("elieandsons", "elieandsons");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		EXDesigner des = new EXDesigner();
		des.setAttribute("appid", "visualgroovy");
		EXWindow window = new EXWindow("", "Designer");
		window.getChildren().clear();
		window.setDraggable(false);
		window.setRendered(false);
		window.setStyleClass("");
		des.getChild("close").getEvents().clear();
		des.getChild("close").addEvent(EventDispatcher.DISPATCHER, Event.CLICK);
		window.setStyle("top", "0").setStyle("left", "0");
		window.addChild(des);
		window.setStyle("width", "1000px");
		try{
		des.open((BinaryFile)SpringUtil.getRepositoryService().getFile("/root/users/" + Util.getRemoteUser() + "/site.ptl", Util.getRemoteUser()));
		}catch(Exception e){
			
		}
		addChild(window);
	}

}
