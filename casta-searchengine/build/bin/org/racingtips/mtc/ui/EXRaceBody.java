package org.racingtips.mtc.ui;

import java.util.List;
import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.racingtips.mtc.RaceCardItem;

public class EXRaceBody extends EXContainer implements Event{

	public EXRaceBody(String name) {
		super(name, "tbody");
		setStyle("font-weight", "bold").setStyle("font-size", "10px").setStyle("padding", "0");
	}
	public void setRaceCard(List<RaceCardItem> raceCard)throws Exception{
		setText("");
		this.getChildren().clear();
		setRendered(false);
		for(RaceCardItem item : raceCard){
			addRaceCardItem(item);
		}
	}
	
	public void addRaceCardItem(RaceCardItem item)throws Exception{
		
		EXRaceCardItem uiItem = new EXRaceCardItem("", item);
		uiItem.setAttribute("raceNumber", item.getParent().getName());
		EXRaceCardItemDetail detail = new EXRaceCardItemDetail("");
		detail.setRace(item);
		addChild(uiItem.setAttribute("detailid", detail.getId()));
		addChild(detail);
	}
	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container,
			Map<String, String> request) throws UIException {
		Container detailtr = getDescendentById(container.getAttribute("handleid"));
		if(detailtr.getStyle("display").equals("none")){
			detailtr.setStyle("display", "table-row");
		}else{
			detailtr.setStyle("display", "none");
		}
		return true;
		
	}

	@Override
	public void Success(ClientProxy container,
			Map<String, String> request) throws UIException {
		// TODO Auto-generated method stub
		
	}
	

}
