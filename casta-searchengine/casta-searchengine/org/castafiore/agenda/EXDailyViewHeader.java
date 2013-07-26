package org.castafiore.agenda;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.scripting.EXXHTMLFragment;

public class EXDailyViewHeader extends EXXHTMLFragment implements Event{
	
	private Calendar date;

	public EXDailyViewHeader(String name, Calendar date, String format) {
		super(name, "templates/agenda/EXDailyViewHeader.xhtml");
		addChild(new EXContainer("previous", "a").addClass("previ").setAttribute("href", "#").setText("Precedent").addEvent(this, CLICK));
		addChild(new EXContainer("month", "div").addClass("month"));
		addChild(new EXContainer("next", "a").addClass("next").setAttribute("href", "#").setText("Prochain").addEvent(this, CLICK));
		setAttribute("format", format);
		setDate(date);
	}
	
	public void setDate(Calendar date){
		this.date = date;
		getChild("month").setText(new SimpleDateFormat(getAttribute("format"), Locale.FRANCE).format(date.getTime()));
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		if(container.getName().equalsIgnoreCase("previous")){
			date.add(Calendar.DATE, -1);
			//setDate(date);
		}else{
			date.add(Calendar.DATE, 1);
			//setDate(date);
		}
		
		//getAncestorOfType(EXDailyView.class).setDate(date, SpringUtil.getBeanOfType(AgendaService.class).getEvents(date, Calendar.DATE));
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		
	}

}
