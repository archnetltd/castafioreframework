package org.racingtips.ui.tips;

import java.util.List;
import java.util.Map;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.ViewModel;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.castafiore.wfs.types.Article;
import org.castafiore.wfs.types.File;
import org.racingtips.mtc.MTCDTO;
import org.racingtips.mtc.Meeting;
import org.racingtips.ui.LeftNavSensitive;

public class EXTips extends EXXHTMLFragment implements Event, LeftNavSensitive{

	public EXTips()throws Exception {
		super("EXTips", "templates/racingtips/EXTips.xhtml");
		
		MTCDTO dto = SpringUtil.getBeanOfType(MTCDTO.class);
		List<Meeting> meetings = dto.getSelectedMeetings();
		
		DefaultDataModel mmmm = new DefaultDataModel(meetings);

		
		Container select = new EXSelect("meetings", mmmm).addEvent(this, CHANGE);
		select.setStyle("margin", "10px");
		addChild(select);
		Container c = new EXContainer("body", "div");
		addChild(c);
		
		showTips(meetings.get(0));
		
		
	}
	
	public ViewModel<Container> getLeftNavigation(){
		return new TipsMenuModel();
	}
	
	
	public void showTips(Meeting meeting){
		Container c = getChild("body");
		c.getChildren().clear();
		c.setRendered(false);
		String dir = "/root/users/racingtips/tips/" + meeting.getName();
		QueryParameters params = new QueryParameters();
		params.setEntity(Article.class).addSearchDir(dir);
		
		List<File> files = SpringUtil.getRepositoryService().executeQuery(params, Util.getRemoteUser());
		for(File f : files){
			Article art = (Article)f;
			Container date = new EXContainer("", "div").addClass("date").setText(art.getTitle()).setStyle("width", "100%").setStyle("color", "red");
			c.addChild(date);
			
			Container event = new EXContainer("", "div").addClass("tab-event").setText(art.getSummary()).setStyle("width", "521px");
			c.addChild(event);
			
		}
	}




	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}




	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		EXSelect select = (EXSelect)container;
		Meeting meeting = (Meeting)select.getValue();
		showTips(meeting);
		return true;
	}




	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
