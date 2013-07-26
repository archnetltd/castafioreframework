package org.racingtips.mtc.ui.result;

import java.util.List;

import org.castafiore.ui.ex.EXContainer;
import org.racingtips.mtc.RaceResultCardItem;
import org.racingtips.mtc.ui.EXRaceCardItemDetail;

public class EXRaceResultBody extends EXContainer  {
	public EXRaceResultBody(String name) {
		super(name, "tbody");
		setStyle("font-weight", "bold").setStyle("font-size", "10px").setStyle("padding", "0");
	}
	public void setRaceCard(List<RaceResultCardItem> raceCard){
		this.getChildren().clear();
		setRendered(false);
		for(RaceResultCardItem item : raceCard){
			addRaceCardItem(item);
		}
	}
	
	public void addRaceCardItem(RaceResultCardItem item){
		
		EXRaceResultCardItem uiItem = new EXRaceResultCardItem("", item);
		addChild(uiItem);
		uiItem.setAttribute("raceNumber", item.getParent().getName());
		EXRaceCardItemDetail detail = new EXRaceCardItemDetail("");
		detail.setRace(item);
		uiItem.setAttribute("detailid", detail.getId());
		
		addChild(detail);
		
	}
	
}
