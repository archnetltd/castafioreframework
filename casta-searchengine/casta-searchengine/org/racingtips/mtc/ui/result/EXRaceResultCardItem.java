package org.racingtips.mtc.ui.result;

import java.util.Map;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.wfs.Util;
import org.racingtips.mtc.RaceCardItem;
import org.racingtips.mtc.RaceResultCardItem;
import org.racingtips.mtc.ui.EXForms;
import org.racingtips.mtc.ui.EXPersonalRating;
import org.racingtips.mtc.ui.EXPublicVote;
import org.racingtips.mtc.ui.EXRaceCardItemDetail;
import org.racingtips.mtc.ui.EXRaceConfDetail;
import org.racingtips.mtc.ui.EXTrackWork;

public class EXRaceResultCardItem  extends EXXHTMLFragment implements Event{

	public EXRaceResultCardItem(String name, RaceResultCardItem item) {
		super(name, "templates/racingtips/EXRaceResultCardItem.xhtml");
		addChild(new EXContainer("col1", "td").setAttribute("valign", "top"));
		addChild(new EXContainer("horseName", "td").setStyle("font-weight", "bold"));
		addChild(new EXContainer("jockey", "td"));
		addChild(new EXContainer("rank", "td"));
		addChild(new EXContainer("pVote", "td").addClass("pVote"));
		addChild(new EXContainer("rating", "td"));
		addChild(new EXContainer("time", "td"));
		addChild(new EXContainer("trackWork", "a").addEvent(this, CLICK).setAttribute("href", "#d").setAttribute("title", "Show track work").setText("<img src=\"racingtips/images/horse.png\"></img>"));
		addChild(new EXContainer("showForms", "a").addEvent(this, CLICK).setAttribute("href", "#d").setAttribute("title", "Show form").setText("<img src=\"icons-2/fugue/icons/application-detail.png\"></img>"));
		addChild(new EXContainer("showHideComment", "a").addEvent(this, CLICK).setAttribute("href", "#d").setAttribute("title", "Show/Hide comment").setText("<img src=\"icons-2/fugue/icons/sticky-note.png\"></img>"));
		setRaceCardItem(item);
		
	}
	
	public void setRaceCardItem(RaceResultCardItem item){
		
		setAttribute("path", item.getAbsolutePath());
		//1 (2)<br> 3-5-4-6/4 <br>7 yrs
		String col1 = item.getHorseNumber() + " (" + item.getDraw() + ") " + "<br></br>" + item.getPerf() + "<br></br>" + item.getAge() + "yrs";
		
		//FEEL THE RAIN (449kg) (60.5) N
		String horseName = item.getHorse()  + " <span style=color:red>" + item.getGear() + "</span>";
		 
		String jockey = item.getStable() + "/" + item.getJockey() + " " + item.getWeight() ;
		
		String pVote = "<label>60 %</label><div style=\"width: 60px;background: red;\"></div><br></br>" +
		"<label>15 %</label><div style=\"width: 15px;background: blue;\"></div><br></br>" +
				"<label>20 %</label><div style=\"width: 20px;background: yellow;\"></div><br></br>" +
						"<label>5 %</label><div style=\"width: 5px;background: green;\"></div>";
		
		getChild("col1").setText(col1);
		getChild("horseName").setText(horseName);
		getChild("jockey").setText(jockey);
		getChild("pVote").setText(pVote);
		getChild("rank").setText(item.getRank());
		getChild("time").setText(item.getTime());
		
	}

	public void showHideComment(){
		Container comment = getDetailCont().getChild("comment");
		comment.setDisplay(!comment.isVisible());
	}
	
	public void hideHorse(){
		setDisplay(false);
		getDetailCont().setDisplay(false);
	}
	
	public void showForms(RaceCardItem item){
		getDetailCont().ShowDetail(new EXForms("", item.getHorseId()));
	}
	
	public void showPublicVote(){
		getDetailCont().ShowDetail(new EXPublicVote(""));
	}
	
	public void showPersonalRating(){
		getDetailCont().ShowDetail(new EXPersonalRating(""));
	}

	private EXRaceCardItemDetail getDetailCont(){
		return (EXRaceCardItemDetail)getParent().getDescendentById(getAttribute("detailid"));
	}
	
	private void showConfiguration(RaceCardItem item){
		getDetailCont().ShowDetail(new EXRaceConfDetail("", item));
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
		RaceResultCardItem item = (RaceResultCardItem)SpringUtil.getRepositoryService().getFile(getAttribute("path"), Util.getRemoteUser());
		if(container.getName().equalsIgnoreCase("showForms")){
			showForms(item);
		}else if(container.getName().equalsIgnoreCase("showPublicVote")){
			showPublicVote();
		}else if(container.getName().equalsIgnoreCase("showPersonalRating")){
			showPersonalRating();
		}else if(container.getName().equalsIgnoreCase("hideHorse")){
			hideHorse();
		}else if(container.getName().equalsIgnoreCase("configureHorse")){
			showConfiguration(item);
		}else if(container.getName().equalsIgnoreCase("trackwork")){
			showTrackWork(item.getHorseId());
		}else if(container.getName().equalsIgnoreCase("showHideComment")){
			 showHideComment();
		}
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
	}
}
