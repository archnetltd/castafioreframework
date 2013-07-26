package org.racingtips.ui.cards;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.ViewModel;
import org.castafiore.wfs.Util;
import org.racingtips.mtc.MTCDTO;
import org.racingtips.mtc.horse.Stable;
import org.racingtips.ui.EXPortal;

public class StablesMenuModel implements ViewModel<Container>, Event{

	
	
	private List<Stable> stables = new ArrayList<Stable>();
	
	public StablesMenuModel(){
		stables = SpringUtil.getBeanOfType(MTCDTO.class).getStables();
	}
	
	@Override
	public int bufferSize() {
		return size();
	}

	@Override
	public Container getComponentAt(int index, org.castafiore.ui.Container parent) {
			Stable stable = stables.get(index);
			Container li = new EXContainer("", "li");//.setAttribute("pageId", clazz[index].getName());
			li.setText("<a href=\"#3\">"+stable.getStableName()+"</a>");
			li.addEvent(this, Event.CLICK);
			li.setAttribute("stable", stable.getAbsolutePath());
			return li;
	}

	@Override
	public int size() {
		return stables.size();
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mergeCommand(new ClientProxy("#iamloading").setStyle("display", "block")).makeServerRequest(this);
		//container.makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		String pageId = container.getAttribute("pageId");
		try{
			//Class clazz = Thread.currentThread().getContextClassLoader().loadClass(pageId);
			Stable stable =(Stable)SpringUtil.getRepositoryService().getFile(container.getAttribute("stable"), Util.getRemoteUser());
			EXStableCard card = new EXStableCard(stable);
			container.getAncestorOfType(EXPortal.class).showPage(card, false);
			
			for(Container c : container.getParent().getChildren()){
				c.setStyleClass("");
			}
			
			container.setStyleClass("selected");
		}catch(Exception e){
			throw new UIException(e);
		}
		
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		container.appendJSFragment("_gaq.push(['_trackEvent', 'Navigation', 'LeftMenu', '"+container.getName()+"']);");
		container.mergeCommand(new ClientProxy("#iamloading").setStyle("display", "none"));
	}

}
