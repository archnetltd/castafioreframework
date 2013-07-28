package org.racingtips.ui.tips;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.castafiore.wfs.types.File;
import org.hibernate.criterion.Order;
import org.racingtips.mtc.Meeting;
import org.racingtips.mtc.ui.EXRace;
import org.racingtips.mtc.ui.EXRaceBody;
import org.racingtips.mtc.ui.EXRaceCardItem;
import org.racingtips.tips.AbstractTips;
import org.racingtips.tips.Tips;

public abstract class EXTipsCard extends EXRace{

	public EXTipsCard() throws Exception {
		super();
		setTemplateLocation("templates/racingtips/EXTipsCard.xhtml");
		Container showPrediction = getChild("showPrediction");
		if(showPrediction != null){
			showPrediction.setDisplay(false);
		}
		
	}
	
	public abstract Class<? extends AbstractTips> getClazz();
	
	public abstract String getTitle();
	
	public void setMeeting(Meeting meeting)throws Exception{
		//all firstchoice in directory meeting.getAbs() ordered by raceNumber
		QueryParameters params = new QueryParameters().setEntity(getClazz()).addSearchDir(meeting.getAbsolutePath()).addOrder(Order.asc("raceNumber"));
		
		List<File> firstChoices = SpringUtil.getRepositoryService().executeQuery(params, Util.getRemoteUser());
		List card = new ArrayList();
		if(firstChoices.size() > 0){
			for(File fc : firstChoices){
				card.add(fc.getParent());
			}
		}
		
		getDescendentOfType(EXRaceBody.class).setRaceCard(card);
		List<Container> result = new ArrayList<Container>();
		ComponentUtil.getDescendentsOfType(getDescendentOfType(EXRaceBody.class), result, EXRaceCardItem.class);
		for(Container c: result){
			EXRaceCardItem item = (EXRaceCardItem)c;
			item.setTemplateLocation("templates/racingtips/EXTipsCardItem.xhtml");
			item.addChild(new EXContainer("raceNumber", "td").setText(item.getAttribute("raceNumber")));
		}
		getChild("title").setText(getTitle());
		getChild("length").setDisplay(false);
		getChild("time").setDisplay(false);
		getChild("rating").setDisplay(false);
		
		 getChild("comments").setDisplay(false);
		
	}


}
