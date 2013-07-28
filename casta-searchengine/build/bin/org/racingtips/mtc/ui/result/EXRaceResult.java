package org.racingtips.mtc.ui.result;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.Comment;
import org.racingtips.mtc.MTCDTO;
import org.racingtips.mtc.Meeting;
import org.racingtips.mtc.Race;
import org.racingtips.mtc.RaceResultCardItem;

import org.racingtips.mtc.ui.EXCommentForm;

import org.racingtips.mtc.ui.EXRaceComment;

public class EXRaceResult extends EXXHTMLFragment implements Event{

	
	private String meetingPath = null;
	
	private String currentRacePath = null;
	public EXRaceResult()throws Exception {
		super("EXRaceResult", "templates/racingtips/EXRaceResult.xhtml");
		addClass("bg-cont");
		
		
		MTCDTO dto = SpringUtil.getBeanOfType(MTCDTO.class);
		List<Meeting> meetings = dto.getSelectedMeetings();
		
		DefaultDataModel mmmm = new DefaultDataModel(meetings);

		
		EXSelect select = new EXSelect("meetings", mmmm);
		select.setStyle("margin", "5px 0 5px 130px");
		addChild(select);
		select.addEvent(this, Event.CHANGE);
		
		for(int i = 1; i <= 8; i++){
			Container td = new EXContainer("race" + i, "td").setText("<a href=#e>"+i+"</a>").addEvent(this, Event.CLICK);
			td.setAttribute("raceid", i + "");
			addChild(td);
			if(i ==1){
				td.setStyle("background", "url(\"http://osc4.template-help.com/drupal_30257/themes/theme514/images/bg-li-act.gif\") repeat scroll 0 0 transparent");
			}
		}
		addChild(new EXContainer("title", "span"));
		addChild(new EXContainer("length", "span"));
		addChild(new EXContainer("time", "span"));
		addChild(new EXContainer("rating", "span"));
		addChild(new EXRaceResultBody("raceCardBody"));
		
		addChild(new EXContainer("comments", "div"));
		Meeting meeting = SpringUtil.getBeanOfType(MTCDTO.class).getCurrentMeeting();
		setMeeting(meeting);
		select.setValue(meeting);
		
	}
	
	public void addComment(String title, String summary){
		Race race = (Race)SpringUtil.getRepositoryService().getFile(currentRacePath, Util.getRemoteUser());
		String name = Util.getRemoteUser() + new Date().getTime();
		Comment comment = race.createComment(name);
		comment.setTitle(title);
		comment.setSummary(summary);
		race.save();
		Container comments = getChild("comments");
		comments.addChild(new EXRaceComment("", race));
		
	}
	
	
	
	public void setMeeting(Meeting meeting)throws Exception{
		List<Race> races = meeting.getRaces();
		if(races.size() > 0){
			setRace(races.get(0));
			getChild("race1").setStyle("background", "url(\"http://osc4.template-help.com/drupal_30257/themes/theme514/images/bg-menu.gif\") repeat scroll right 0 transparent");
		}
		this.meetingPath = meeting.getAbsolutePath();
	}
	
	private void setRace(Race race)throws Exception{
		List<RaceResultCardItem> card = race.getResult().getResultCard();
		getDescendentOfType(EXRaceResultBody.class).setRaceCard(card);
		getChild("title").setText(race.getTitle());
		getChild("length").setText(race.getLength());
		getChild("time").setText(race.getTime());
		getChild("rating").setText(race.getRating());
		this.currentRacePath = race.getAbsolutePath();
		Container comments = getChild("comments");
		comments.getChildren().clear();
		comments.setRendered(false);
	 	List<Comment> fcomments = race.getComments().toList();
	 	for(Comment comment : fcomments){
	 		comments.addChild(new EXRaceComment("", race));
	 	}
	 	
		
	}



	public void executeAction(Container source) {
		try{
			if(source.getName().startsWith("race")){
				for(Container c : getChildren()){
					if(c.getName().startsWith("race")){
						c.setStyle("background", "url(\"http://osc4.template-help.com/drupal_30257/themes/theme514/images/bg-menu.gif\") repeat scroll right 0 transparent");
					}
				}
				Integer index = Integer.parseInt(source.getAttribute("raceid"))-1;
				source.setStyle("background", "url(\"http://osc4.template-help.com/drupal_30257/themes/theme514/images/bg-li-act.gif\") repeat scroll 0 0 transparent");
				Race r =  ((Meeting)SpringUtil.getRepositoryService().getFile(meetingPath, Util.getRemoteUser())).getRaces().get(index); // meeting.getRaces().get(index);
				setRace(r);
			}else{
				EXSelect select = (EXSelect)source;
				Meeting meeting = (Meeting)select.getValue();
				meeting = ((Meeting)SpringUtil.getRepositoryService().getFile(meeting.getAbsolutePath(), Util.getRemoteUser()));
				setMeeting(meeting);
			}
		}catch(Exception e){
			throw new UIException(e);
		}
		
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mergeCommand(new ClientProxy("#iamloading").setStyle("display", "block")).makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		if(container.getName().equals("postComment")){
			getDescendentOfType(EXCommentForm.class).setDisplay(true);
			return true;
		}
		executeAction(container);
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		container.mergeCommand(new ClientProxy("#iamloading").setStyle("display", "none"));
		
	}
}