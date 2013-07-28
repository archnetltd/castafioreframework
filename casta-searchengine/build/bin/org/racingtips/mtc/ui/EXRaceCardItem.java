package org.racingtips.mtc.ui;

import java.math.BigDecimal;
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
import org.racingtips.mtc.RaceCardItem;

public class EXRaceCardItem extends EXXHTMLFragment implements Event{

	public EXRaceCardItem(String name, RaceCardItem item)throws Exception {
		super(name, "templates/racingtips/EXRaceCardItem.xhtml");
		addChild(new EXContainer("col1", "td").setAttribute("valign", "top"));
		addChild(new EXContainer("horseName", "td").setStyle("font-weight", "bold"));
		addChild(new EXContainer("jockey", "td"));
		addChild(new EXContainer("showForms", "a").addEvent(this, CLICK).setAttribute("href", "#d").setAttribute("title", "Show form").setText("<img src=\"icons-2/fugue/icons/application-detail.png\"></img>"));
		addChild(new EXContainer("showPublicVote", "a").addEvent(this, CLICK).setAttribute("href", "#d").setAttribute("title", "Public vote").setText("<img src=\"icons-2/fugue/icons/target--plus.png\"></img>"));
		addChild(new EXContainer("showPersonalRating", "a").setDraggable(true).addEvent(this, CLICK).setAttribute("href", "#d").setAttribute("title", "Free personal details").setText("<img src=\"icons-2/fugue/icons/book--pencil.png\"></img>"));
		addChild(new EXContainer("hideHorse", "a").addEvent(this, CLICK).setAttribute("href", "#d").setAttribute("title", "Hide this horse").setText("<img src=\"icons-2/fugue/icons/eraser.png\"></img>"));
		addChild(new EXContainer("configureHorse", "a").addEvent(this, CLICK).setAttribute("href", "#d").setAttribute("title", "Configure and comment this horse").setText("<img src=\"icons-2/fugue/icons/gear.png\"></img>"));
		addChild(new EXContainer("trackWork", "a").addEvent(this, CLICK).setAttribute("href", "#d").setAttribute("title", "Show track work").setText("<img src=\"racingtips/images/horse.png\"></img>"));
		addChild(new EXContainer("showHideComment", "a").addEvent(this, CLICK).setAttribute("href", "#d").setAttribute("title", "Show/Hide analysis").setText("<img src=\"icons-2/fugue/icons/sticky-note.png\"></img>"));
		addChild(new EXContainer("odds", "td"));
		addChild(new EXContainer("rating", "td"));
		addChild(new EXContainer("pVote", "td").addClass("pVote"));
		addChild(new EXContainer("silk", "img").setStyle("height", "45px").setAttribute("src", item.getSilk()));
		setRaceCardItem(item);
		
		if(!"3RacingtipsAdmin".equals(Util.getRemoteUser())){
			getChild("configureHorse").setDisplay(false).getEvents().clear();
			getChild("configureHorse").setRendered(false);
		}
		
	}
	
	public void setRaceCardItem(RaceCardItem item)throws Exception{
		
		if(item.getStatus() == item.HORSE_REMOVED){
			setStyle("text-decoration", "line-through");
		}
		//1 (2)<br> 3-5-4-6/4 <br>7 yrs
		setAttribute("path", item.getAbsolutePath());
		String col1 = item.getHorseNumber() + " (" + item.getDraw() + ") " + "<br>" + item.getPerf() + "<br>" + item.getAge() + "yrs";
		
		//FEEL THE RAIN (449kg) (60.5) N
		String horseName = item.getHorse()  + " (" +item.getHorseWeight() + ") <span style=color:red>" + item.getGear() + "</span>";
		 
		String jockey = item.getStable() + "/" + item.getJockey() + " " + item.getWeight() ;
		
		
		
		String opening = item.getOpeningOdd();
		
		String recentOdd = item.getRecentOdd();
		
		String img = "<img src=\"icons-2/fugue/icons/arrow-270.png\"></img>";
		
		
		try{
			int op = Integer.parseInt(opening);
			int re = Integer.parseInt(recentOdd);
			
			if(op > re){
				img = "<img src=\"icons-2/fugue/icons/arrow-090.png\"></img>";
			}else if(op == re){
				img = "<img src=\"icons-2/fugue/icons/arrow-180.png\"></img>";
			}
		}catch(Exception e){
			img = "<img src=\"icons-2/fugue/icons/exclamation-red.png\"></img>";
			
		}
		
		
		String odds = opening + " - " +recentOdd+ " " + img;
		
		
		// "<label>60 %</label><div style=\"width: 60px\"></div>";
		
		
		BigDecimal[] votes = new BigDecimal[]{new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0)};
		try{
			BigDecimal total = new BigDecimal(0);
			if(item.getV1() != null){
				total = total.add(item.getV1());
				votes[0] = (item.getV1().divide(total)).multiply(new BigDecimal(100));
			}
			
			if(item.getV2() != null){
				total = total.add(item.getV2());
				votes[1] = (item.getV2().divide(total)).multiply(new BigDecimal(100));
			}
			
			if(item.getV3() != null){
				total = total.add(item.getV3());
				votes[2] = (item.getV3().divide(total)).multiply(new BigDecimal(100));
			}
			
			if(item.getV4() != null){
				total = total.add(item.getV4());
				votes[3] = (item.getV4().divide(total)).multiply(new BigDecimal(100));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
		
		

		 String pVote = "<div style=\"width: "+votes[0]+"px;background: red;\"></div>" +
				"<div style=\"width: "+votes[1]+"px;background: blue;\"></div>" +
						"<div style=\"width: "+votes[2]+"px;background: yellow;\"></div>" +
								"<div style=\"width: "+votes[3]+"px;background: green;\"></div>";
		 
		 String rating = SpringUtil.getBeanOfType(MTCDTO.class).getHorseRating(item.getHorseId());
		
		getChild("col1").setText(col1);
		getChild("horseName").setText(horseName);
		getChild("jockey").setText(jockey);
		getChild("odds").setText(odds);
		getChild("pVote").setText(pVote);
		getChild("rating").setText(rating);
	}
	
	public void showHideComment(){
		Container comment = getDetailCont().getChild("comment");
		comment.setDisplay(!comment.isVisible());
	}
	
	
	public void showComment(){
		Container comment = getDetailCont().getChild("comment");
		comment.setDisplay(true);
	}
	
	public void hideComment(){
		Container comment = getDetailCont().getChild("comment");
		comment.setDisplay(false);
	}
	
	public void showTrackWork(){
		if(getDescendentOfType(EXTrackWork.class) == null){
			RaceCardItem item = (RaceCardItem)SpringUtil.getRepositoryService().getFile(getAttribute("path"), Util.getRemoteUser());
			showTrackWork(item.getHorseId());
		}
	}
	
	public void hideTrackWork(){
		EXTrackWork work = getDescendentOfType(EXTrackWork.class);
		if(work != null){
			Container parent = work.getParent();
			work.remove();
			parent.setRendered(false);
		}
	}
	
	public void showForms(){
		if(getDescendentOfType(EXForms.class) == null){
			RaceCardItem item = (RaceCardItem)SpringUtil.getRepositoryService().getFile(getAttribute("path"), Util.getRemoteUser());
			showForms(item);
		}
	}
	
	public void hideForms(){
		EXForms work = getDescendentOfType(EXForms.class);
		if(work != null){
			Container parent = work.getParent();
			work.remove();
			parent.setRendered(false);
		}
	}
	
	public void showTrackWork(String horseId){
		getDetailCont().ShowDetail(new EXTrackWork("", horseId));
	}
	
	public void showForms(RaceCardItem item){
		getDetailCont().ShowDetail(new EXForms("", item.getHorseId()));
	}
	
	public void hideHorse(){
		setDisplay(false);
		getDetailCont().setDisplay(false);
		
	}
	
	
	
	
	public void showPublicVote(RaceCardItem item){
		getDetailCont().ShowDetail(new EXPublicVote(""));
		EXPublicVote vote = getDetailCont().getDescendentOfType(EXPublicVote.class);
		if(vote != null){
			vote.setItem(item);
		}
	}
	
	
	public void showPersonalRating(RaceCardItem item){
		getDetailCont().ShowDetail(new EXPersonalRating(""));
		EXPersonalRating vote = getDetailCont().getDescendentOfType(EXPersonalRating.class);
		if(vote != null){
			vote.setItem(item);
		}
	}
	
	
	
	private EXRaceCardItemDetail getDetailCont(){
		return (EXRaceCardItemDetail)getParent().getDescendentById(getAttribute("detailid"));
	}
	
	
	private void showConfiguration(RaceCardItem item){
		getDetailCont().ShowDetail(new EXRaceConfDetail("", item));
	}
	
	
	
	


	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		RaceCardItem item = (RaceCardItem)SpringUtil.getRepositoryService().getFile(getAttribute("path"), Util.getRemoteUser());
		if(container.getName().equalsIgnoreCase("showForms")){
			showForms(item);
		}else if(container.getName().equalsIgnoreCase("showPublicVote")){
			showPublicVote(item);
		}else if(container.getName().equalsIgnoreCase("showPersonalRating")){
			showPersonalRating(item);
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
		// TODO Auto-generated method stub
		
	}

}
