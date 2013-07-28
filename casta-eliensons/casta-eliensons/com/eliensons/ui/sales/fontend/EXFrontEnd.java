package com.eliensons.ui.sales.fontend;

import java.util.Map;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXApplication;
import org.castafiore.ui.ex.panel.EXOverlayPopupPlaceHolder;

public class EXFrontEnd extends EXApplication implements PopupContainer , Event{

	public EXFrontEnd() {
		super("fontend");
		try{
		SpringUtil.getSecurityService().login("elieandsons", "elieandsons");
		addEvent(this, READY);
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		addChild(new EXOverlayPopupPlaceHolder("pc"));
	}

	@Override
	public void addPopup(Container popup) {
		getChild("pc").addChild(popup);
		
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		try{
		addChild(new EXElieNSonsFrontEnd("f"));
		this.getEvents().clear();
		setRendered(false);
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
