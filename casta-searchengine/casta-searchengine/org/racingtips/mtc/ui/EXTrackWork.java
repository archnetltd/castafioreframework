package org.racingtips.mtc.ui;

import java.util.List;

import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.racingtips.mtc.MTCDTO;
import org.racingtips.mtc.horse.TrackWork;

public class EXTrackWork extends EXXHTMLFragment implements EventDispatcher{

	public EXTrackWork(String name, String horseId) {
		super(name,"templates/racingtips/EXTrackWork.xhtml");
		Container save = new EXContainer("save","a").setText("<img style=\"padding: 4px 0\" src=\"icons-2/fugue/icons/tick.png\"></img>").addEvent(DISPATCHER, Event.CLICK);
		addChild(save);
		
		Container tbody = new EXContainer("formsItem", "tbody");
		List<TrackWork> forms = SpringUtil.getBeanOfType(MTCDTO.class).getTrackWork(horseId);
		
		for(TrackWork form : forms){
			
			EXContainer tr = new EXContainer("", "tr");
			StringBuilder b = new StringBuilder();
			b.append("<td>").append(form.getHorse()).append("</td>");
			b.append("<td>").append(form.getJockey()).append("</td>");
			b.append("<td>").append(form.getM1000()).append("</td>");
			b.append("<td>").append(form.getM800()).append("</td>");
			b.append("<td>").append(form.getM600()).append("</td>");
			b.append("<td>").append(form.getM400()).append("</td>");
			b.append("<td>").append(form.getM200()).append("</td>");
			tr.setText(b.toString());
			tbody.addChild(tr);
		}
		addChild(tbody);
	}
	
	@Override
	public void executeAction(Container source) {
		getParent().setRendered(false);
		this.remove();
		
	}

}
