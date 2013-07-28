package org.racingtips.ui.tips;

import java.util.ArrayList;
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
import org.castafiore.utils.ComponentUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.castafiore.wfs.types.File;
import org.hibernate.criterion.Order;
import org.racingtips.mtc.MTCDTO;
import org.racingtips.mtc.Meeting;
import org.racingtips.mtc.Race;
import org.racingtips.mtc.ui.EXCommentForm;
import org.racingtips.mtc.ui.EXRaceBody;
import org.racingtips.mtc.ui.EXRaceCardItem;
import org.racingtips.tips.Trifecta;

public class EXTrifecta extends EXXHTMLFragment implements Event{

	public EXTrifecta()throws Exception {
		
		super("EXRace", "templates/racingtips/EXTrifecta.xhtml");
		addClass("bg-cont");
		addClass("EXTrifecta");
		
		
		MTCDTO dto = SpringUtil.getBeanOfType(MTCDTO.class);
		List<Meeting> meetings = dto.getSelectedMeetings();
		
		DefaultDataModel mmmm = new DefaultDataModel(meetings);

		
		EXSelect select = new EXSelect("meetings", mmmm);
		select.setStyle("margin", "5px 0 5px 130px");
		addChild(select);
		select.addEvent(this, Event.CHANGE);
		
		addChild(new EXTrifectaBody("body"));
		
		Meeting meeting = SpringUtil.getBeanOfType(MTCDTO.class).getCurrentMeeting();
		setMeeting(meeting);
		select.setValue(meeting);
		if(getDescendentOfType(EXTrifectaBody.class).getChildren().size() == 0){
			getDescendentOfType(EXTrifectaBody.class).addChild(new EXContainer("tr", "tr").setText("<td text-align='center' colspan='7' class='info-message'>Please note that Tips will be published by Friday 4PM. So stay tuned</td>"));
			//getDescendentOfType(EXTrifectaBody.class).setText("<tr><td text-align='center' colspan='7' class='info-message'>Please note that Tips will be published by Saturday 10 AM. So stay tuned</td></tr>");
		}
	}
	
	public void setMeeting(Meeting meeting){
		QueryParameters params = new QueryParameters().setEntity(Trifecta.class).addSearchDir(meeting.getAbsolutePath()).addOrder(Order.asc("raceNumber"));
		List<File> firstChoices = SpringUtil.getRepositoryService().executeQuery(params, Util.getRemoteUser());
		EXTrifectaBody body = getDescendentOfType(EXTrifectaBody.class);
		body.getChildren().clear();
		body.setRendered(false);
		if(firstChoices.size() > 0){
			for(File fc : firstChoices){
				body.addItem((Trifecta)fc);
			}
		}

	}
	
	
	public void executeAction(Container source) {
		try{
			
				EXSelect select = (EXSelect)source;
				Meeting meeting = (Meeting)select.getValue();
				meeting = ((Meeting)SpringUtil.getRepositoryService().getFile(meeting.getAbsolutePath(), Util.getRemoteUser()));
				setMeeting(meeting);
			
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
