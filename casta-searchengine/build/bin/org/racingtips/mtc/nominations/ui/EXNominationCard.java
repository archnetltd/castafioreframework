package org.racingtips.mtc.nominations.ui;

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
import org.racingtips.mtc.MTCDTO;
import org.racingtips.mtc.Meeting;
import org.racingtips.mtc.Nomination;
import org.racingtips.mtc.NominationRace;

public class EXNominationCard extends  EXXHTMLFragment implements Event {

	private String meetingName = null;
	

	
	public EXNominationCard() throws Exception {
		super("EXRace", "templates/racingtips/EXNominationCard.xhtml");
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
		addChild(new EXNominationBody("raceCardBody"));
		
		Meeting meeting = SpringUtil.getBeanOfType(MTCDTO.class).getCurrentMeeting();
		setMeeting(meeting);
		select.setValue(meeting);
		
	}
	
	public void setMeeting(Meeting meeting)throws Exception{
		
		
		NominationRace race = SpringUtil.getBeanOfType(MTCDTO.class).getNominationRace(meeting.getName(), "1");
		
		
		if(race != null){
			setNominationRace(race);
			getChild("race1").setStyle("background", "url(\"http://osc4.template-help.com/drupal_30257/themes/theme514/images/bg-li-act.gif\") repeat scroll 0 0 transparent");
		}
		this.meetingName = meeting.getName();
	}
	
	protected void setNominationRace(NominationRace race)throws Exception{
		
		List<Nomination> nominations = race.getNominations();
		
		getDescendentOfType(EXNominationBody.class).setNominationCard(nominations);
		getChild("title").setText(race.getTitle());
		getChild("length").setText(race.getLength());
		getChild("time").setText(race.getTime());
		getChild("rating").setText(race.getRating());
		//this.currentRacePath = race.getAbsolutePath();
	}
	
	public void executeAction(Container source) {
		try{
			if(source.getName().startsWith("race")){
				for(Container c : getChildren()){
					if(c.getName().startsWith("race")){
						c.setStyle("background", "url(\"http://osc4.template-help.com/drupal_30257/themes/theme514/images/bg-menu.gif\") repeat scroll right 0 transparent");
					}
				}
			
				source.setStyle("background", "url(\"http://osc4.template-help.com/drupal_30257/themes/theme514/images/bg-li-act.gif\") repeat scroll 0 0 transparent");
				String race = source.getAttribute("raceid");
				String meeting = meetingName;
				NominationRace r = SpringUtil.getBeanOfType(MTCDTO.class).getNominationRace(meeting, race);
				//Race r =  ((Meeting)SpringUtil.getRepositoryService().getFile(meetingPath, Util.getRemoteUser())).getRace(source.getAttribute("raceid")); 
				setNominationRace(r);
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
		
		//container.makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		executeAction(container);
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		container.mergeCommand(new ClientProxy("#iamloading").setStyle("display", "none"));
		
	}

}
