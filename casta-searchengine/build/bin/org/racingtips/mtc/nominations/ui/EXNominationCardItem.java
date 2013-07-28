package org.racingtips.mtc.nominations.ui;

import java.util.Map;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.wfs.Util;
import org.racingtips.mtc.MTCDTO;
import org.racingtips.mtc.Nomination;
import org.racingtips.mtc.RaceCardItem;

import org.racingtips.mtc.ui.EXForms;
import org.racingtips.mtc.ui.EXRaceCardItemDetail;
import org.racingtips.mtc.ui.EXTrackWork;

public class EXNominationCardItem extends EXXHTMLFragment implements Event{

	public EXNominationCardItem(String name, Nomination item)throws Exception {
		super(name, "templates/racingtips/EXNominationCardItem.xhtml");
		addChild(new EXContainer("col1", "td").setAttribute("align", "left").setAttribute("valign", "top"));
		addChild(new EXContainer("horseName", "td").setAttribute("align", "left").setStyle("font-weight", "bold"));
		addChild(new EXContainer("stable", "td").setAttribute("align", "left"));
		addChild(new EXContainer("weight", "td"));
		addChild(new EXContainer("draw", "td"));
		addChild(new EXContainer("rating", "td").setAttribute("valign", "top"));
		addChild(new EXContainer("showForms", "a").addEvent(this, CLICK).setAttribute("href", "#d").setAttribute("title", "Show form").setText("<img src=\"icons-2/fugue/icons/application-detail.png\"></img>"));
		addChild(new EXContainer("hideHorse", "a").addEvent(this, CLICK).setAttribute("href", "#d").setAttribute("title", "Hide this horse").setText("<img src=\"icons-2/fugue/icons/eraser.png\"></img>"));
		addChild(new EXContainer("trackWork", "a").addEvent(this, CLICK).setAttribute("href", "#d").setAttribute("title", "Show track work").setText("<img src=\"racingtips/images/horse.png\"></img>"));
		setNomination(item);
		
	}
	
	public void setNomination(Nomination item)throws Exception{
		setAttribute("path", item.getAbsolutePath());
		String col1 = item.getHorseNumber();
		String horseName = item.getHorse()  ;
		String stable = item.getStable();
		String rating = SpringUtil.getBeanOfType(MTCDTO.class).getHorseRating(item.getHorseId());
		getChild("col1").setText(col1);
		getChild("horseName").setText(horseName);
		getChild("stable").setText(stable);
		getChild("draw").setText(item.getDraw());
		getChild("rating").setText(rating);
		getChild("weight").setText(item.getWeight());
	}
	
	public void hideHorse(){
		setDisplay(false);
		getDetailCont().setDisplay(false);
	}
	
	public void showForms(String horseId){
		getDetailCont().ShowDetail(new EXForms("", horseId));
	}

	private EXRaceCardItemDetail getDetailCont(){
		return (EXRaceCardItemDetail)getParent().getDescendentById(getAttribute("detailid"));
	}
	
	private void showTrackWork(String horseId){
		getDetailCont().ShowDetail(new EXTrackWork("", horseId));
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		Nomination item = (Nomination)SpringUtil.getRepositoryService().getFile(getAttribute("path"), Util.getRemoteUser());
		if(container.getName().equalsIgnoreCase("showForms")){
			showForms(item.getHorseId());
		}else if(container.getName().equalsIgnoreCase("hideHorse")){
			hideHorse();
		}else if(container.getName().equalsIgnoreCase("trackwork")){
			showTrackWork(item.getHorseId());
		}
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
	}
}