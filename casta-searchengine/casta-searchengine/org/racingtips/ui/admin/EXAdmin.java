package org.racingtips.ui.admin;

import java.util.List;
import java.util.Map;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.wfs.Util;
import org.racingtips.mtc.MTCDTO;
import org.racingtips.mtc.Meeting;
import org.racingtips.mtc.Race;

public class EXAdmin extends EXContainer implements Event, PopupContainer{

	public EXAdmin(String name)throws Exception {
		super(name, "div");
	
		

		addChild(new EXInput("stableImportId"));
		addChild(new EXContainer("importStable", "button").setText("Import stable").addEvent(this, CLICK));
		
		
		MTCDTO dto = SpringUtil.getBeanOfType(MTCDTO.class);
		List<Meeting> meetings = dto.getMeetings();
		DefaultDataModel mmmm = new DefaultDataModel(meetings);
		EXSelect select = new EXSelect("meetings", mmmm);
		addChild(select);
		
		addChild(new EXContainer("currentMeeting", "button").setText("Set current meeting").addEvent(this, CLICK));
		addChild(new EXContainer("closeMeeting", "button").setText("Close meeting").addEvent(this, CLICK));
		addChild(new EXContainer("futureMeeting", "button").setText("Future meeting").addEvent(this, CLICK));
		addChild(new EXContainer("importNominations", "button").setText("Import Nomination").addEvent(this, CLICK));
		
		addChild(new EXContainer("resynch", "button").setText("Re synch Race card").addEvent(this, CLICK));
		
		addChild(new EXContainer("resynchnom", "button").setText("Re synch Nomination").addEvent(this, CLICK));
		
		addChild(new EXContainer("import2010", "button").setText("IMport 2010").addEvent(this, CLICK));
		
		
	}
	
	
	public void resynchNom(){
		try{
			Meeting meeting = (Meeting)((EXSelect)getChild("meetings")).getValue();
			meeting = (Meeting)SpringUtil.getRepositoryService().getFile(meeting.getAbsolutePath(), Util.getRemoteUser());
			SpringUtil.getBeanOfType(MTCDTO.class).resynchNomination(meeting.getMeetingId());
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void resynch(){
		try{
			Meeting meeting = (Meeting)((EXSelect)getChild("meetings")).getValue();
			meeting = (Meeting)SpringUtil.getRepositoryService().getFile(meeting.getAbsolutePath(), Util.getRemoteUser());
			List<Race> races = meeting.getRaces();
			for(Race r : races){
				r.resynch();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	public void currentMeeting(){
		try{
			Meeting meeting = (Meeting)((EXSelect)getChild("meetings")).getValue();
			meeting = (Meeting)SpringUtil.getRepositoryService().getFile(meeting.getAbsolutePath(), Util.getRemoteUser());
			meeting.setStatus(Meeting.MEETING_CURRENT);
			meeting.save();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void futureMeeting(){
		try{
			Meeting meeting = (Meeting)((EXSelect)getChild("meetings")).getValue();
			meeting = (Meeting)SpringUtil.getRepositoryService().getFile(meeting.getAbsolutePath(), Util.getRemoteUser());
			meeting.setStatus(Meeting.MEETING_WAITING);
			meeting.save();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void closeMeeting(){
		try{
			Meeting meeting = (Meeting)((EXSelect)getChild("meetings")).getValue();
			meeting = (Meeting)SpringUtil.getRepositoryService().getFile(meeting.getAbsolutePath(), Util.getRemoteUser());
			meeting.setStatus(Meeting.MEETING_COMPLETED);
			meeting.save();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public void importNomination(){
		try{
			Meeting meeting = (Meeting)((EXSelect)getChild("meetings")).getValue();
			SpringUtil.getBeanOfType(MTCDTO.class).importNominations(meeting.getLabel(), meeting.getMeetingId());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void importStable(){
		try{
			String id = ((EXInput)getChild("stableImportId")).getValue().toString();
			SpringUtil.getBeanOfType(MTCDTO.class).importStable(id);
		}catch(Exception e){
			throw new UIException(e);
		}
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		if(container.getName().equals("importStable")){
			importStable();
		}else if(container.getName().equals("importNominations")){
			importNomination();
		}else if(container.getName().equals("futureMeeting")){
			futureMeeting();
		}else if(container.getName().equals("closeMeeting")){
			closeMeeting();
		}else if(container.getName().equals("currentMeeting")){
			currentMeeting();
		}else if(container.getName().equals("resynch")){
			resynch();
		}else if(container.getName().equals("resynchnom")){
			resynchNom();
		}else if(container.getName().equals("import2010")){
			MTCDTO dto = new MTCDTO();
			//dto.setCurrentYear("2010");
			try{
				new MTCDTO().importSeason("2010");
				new MTCDTO().importSeason("2009");
				new MTCDTO().importSeason("2008");
				new MTCDTO().importSeason("2007");
				new MTCDTO().importSeason("2006");
				new MTCDTO().importSeason("2005");
				new MTCDTO().importSeason("2004");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}





	@Override
	public void addPopup(Container popup) {
		addChild(popup);
		
	}

}
