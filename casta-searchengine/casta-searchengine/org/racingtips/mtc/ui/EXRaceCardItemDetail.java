package org.racingtips.mtc.ui;

import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.wfs.types.Comment;
import org.racingtips.mtc.RaceCardItem;

public class EXRaceCardItemDetail extends EXXHTMLFragment{

	public EXRaceCardItemDetail(String name) {
		super(name, "templates/racingtips/EXRaceCardItemDetail.xhtml");
		addChild(new EXContainer("comment", "h4").setText("Return from the dead").setDisplay(false));
		
		addChild(new EXContainer("detailContainer", "div"));
		
	}
	
	public void ShowDetail(Container detail){
		Container d = getChild("detailContainer");
		
		if(d.getDescendentOfType(detail.getClass()) != null ){
			d.getChildren().clear();
			d.setRendered(false);
		}else{
			d.getChildren().clear();
			d.setRendered(false);
			d.addChild(detail);
		}
	}
	
	//public void hideDetail()
	
	public void setRace(RaceCardItem race){
		Comment comment = race.getFirstChild(Comment.class);
		String sComment = "No comment available yet";
		if(comment != null ){
			sComment = comment.getSummary();
		}
		getChild("comment").setText(sComment);
		
	}
	
	

}
