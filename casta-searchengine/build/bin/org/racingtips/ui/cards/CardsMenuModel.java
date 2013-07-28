package org.racingtips.ui.cards;

import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.ViewModel;
import org.racingtips.mtc.nominations.ui.EXNominationCard;
import org.racingtips.mtc.ui.EXRace;
import org.racingtips.mtc.ui.result.EXRaceResult;
import org.racingtips.ui.EXHorseRatingList;
import org.racingtips.ui.EXPortal;

public class CardsMenuModel implements ViewModel<Container>, Event{

	private final static String[] items = new String[]{"Nomination and Draws", "Race cards", "Forms and Track Work", "Results"};
	
	private final static Class[] clazz = new Class[]{EXNominationCard.class, EXRace.class, EXHorseRatingList.class, EXRaceResult.class};
	
	@Override
	public int bufferSize() {
		return size();
	}

	@Override
	public Container getComponentAt(int index,
			org.castafiore.ui.Container parent) {
		
			Container li = new EXContainer("", "li").setAttribute("pageId", clazz[index].getName());
			li.setText("<a href=\"#3\">"+items[index]+"</a>");
			li.addEvent(this, Event.CLICK);
			return li;
	}

	@Override
	public int size() {
		return items.length;
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mergeCommand(new ClientProxy("#iamloading").setStyle("display", "block")).makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		String pageId = container.getAttribute("pageId");
		try{
			Class clazz = Thread.currentThread().getContextClassLoader().loadClass(pageId);
			container.getAncestorOfType(EXPortal.class).showPage(clazz, false);
			
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
