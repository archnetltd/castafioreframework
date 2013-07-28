package org.racingtips.mtc.ui;

import java.io.InputStream;
import java.util.Map;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXUpload;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.racingtips.mtc.MTCDTO;

public class EXHorseConf extends EXXHTMLFragment implements Event{

	public EXHorseConf(String name, String horseId, String horse) {
		super(name, "templates/racingtips/EXHorseConf.xhtml");
		setAttribute("horseId", horseId);
		setAttribute("horse", horse);
		addChild(new EXUpload("browseForms"));
		addChild(new EXUpload("browseTrackWork"));
		addChild( new EXContainer("importForms", "button").setText("Import Forms").addClass("form-submit").addEvent(this, Event.CLICK));
		addChild( new EXContainer("importTrackWork", "button").setText("Import TrackWork").addClass("form-submit").addEvent(this, Event.CLICK));
		
	}
	
	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
	}
	
	

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		SpringUtil.getBeanOfType(MTCDTO.class).kill();
		String horse = container.getAncestorOfType(EXHorseConf.class).getAttribute("horse");
		String horseId = container.getAncestorOfType(EXHorseConf.class).getAttribute("horseId");
		
		if(container.getName().equals("importForms")){
			try{
				EXUpload upl = (EXUpload)getChild("browseForms");
				InputStream stream = upl.getFile().getInputStream();
				
				SpringUtil.getBeanOfType(MTCDTO.class).importForms(stream, horseId, horse);
				return true;
			}catch(Exception e){
				throw new UIException(e);
			}
		}  else if(container.getName().equals("importTrackWork")){
			try{
				
				EXUpload upl = (EXUpload)getChild("browseTrackWork");
				InputStream stream = upl.getFile().getInputStream();
				
				SpringUtil.getBeanOfType(MTCDTO.class).importTrackWork(stream, horseId, horse);
				return true;
			}catch(Exception e){
				throw new UIException(e);
			}
		} 	
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
