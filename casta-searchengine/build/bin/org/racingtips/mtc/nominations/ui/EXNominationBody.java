package org.racingtips.mtc.nominations.ui;

import java.util.List;
import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.racingtips.mtc.Nomination;
import org.racingtips.mtc.ui.EXRaceCardItemDetail;

public class EXNominationBody extends EXContainer implements Event{
	public EXNominationBody(String name) {
		super(name, "tbody");
		setStyle("font-weight", "bold").setStyle("font-size", "10px").setStyle("padding", "0");
	}
	public void setNominationCard(List<Nomination> raceCard)throws Exception{
		this.getChildren().clear();
		setRendered(false);
		for(Nomination item : raceCard){
			addNominationCardItem(item);
		}
	}
	
	public void addNominationCardItem(Nomination item)throws Exception{
		
		EXNominationCardItem uiItem = new EXNominationCardItem("", item);
		uiItem.setAttribute("raceNumber", item.getParent().getName());
		EXRaceCardItemDetail detail = new EXRaceCardItemDetail("");
		addChild(uiItem.setAttribute("detailid", detail.getId()));
		detail.getChild("comment").setDisplay(false);
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
