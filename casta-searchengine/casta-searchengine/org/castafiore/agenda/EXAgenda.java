package org.castafiore.agenda;

import java.util.Calendar;
import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.button.EXButtonSet;
import org.castafiore.ui.ex.form.button.EXIconButton;
import org.castafiore.ui.ex.form.button.Icons;
import org.castafiore.ui.ex.panel.EXOverlayPopupPlaceHolder;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.ui.ex.toolbar.EXToolBar;
import org.castafiore.utils.ResourceUtil;

public class EXAgenda extends EXContainer implements Event, PopupContainer{

	public EXAgenda(String name) {
		super(name, "div");
		addClass("TabbedPanelsContent");
		
		
//		EXToolBar toolbar =new EXToolBar("tb");
//		toolbar.removeClass("ui-corner-all").setStyle("border", "none");
//		
//		EXButtonSet set = new EXButtonSet("set");
//		set.addItem(new EXIconButton("days", "Jour"));
//		set.addItem(new EXIconButton("weeks", "Semaine"));
//		set.addItem(new EXIconButton("months", "Mois"));
//		set.addItem(new EXIconButton("agenda", "Mon agenda"));
//		for(Container c : set.getChildren()){
//			c.setStyle("height", "18px");
//		}
//		set.setTouching(true);
//		toolbar.addItem(set);
//		
//		toolbar.addItem(new EXIconButton("add", Icons.ICON_PLUSTHICK));
//		
//		
//		addChild(toolbar);
		
		
		
		addStyleSheet(ResourceUtil.getDownloadURL("classpath", "org/castafiore/agenda/agenda.css"));
		addChild(new EXDailyView("dailview", Calendar.getInstance()));
//		getDescendentByName("days").addEvent(this, CLICK);
//		getDescendentByName("weeks").addEvent(this, CLICK);
//		getDescendentByName("months").addEvent(this, CLICK);
//		getDescendentByName("agenda").addEvent(this, CLICK);
//		getDescendentByName("add").addEvent(this, CLICK).setAttribute("title", "Ajouter un evenement");
		
		addChild(new EXOverlayPopupPlaceHolder("overlay"));
		
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		if(container.getName().equalsIgnoreCase("days")){
			for(Container c : getChildren()){
				if(!(c instanceof EXToolBar)){
					if(c instanceof EXDailyView){
						c.setDisplay(true);
					}else{
						c.setDisplay(false);
					}
				}
			}
		}else if(container.getName().equalsIgnoreCase("weeks")){
			boolean found = false;
			for(Container c : getChildren()){
				if(!(c instanceof EXToolBar)){
					if(c instanceof EXWeekView){
						c.setDisplay(true);
						found = true;
					}else{
						c.setDisplay(false);
					}
				}
			}
			
			if(!found){
				addChild(new EXWeekView("weeks", Calendar.getInstance()));
			}
		}else if(container.getName().equalsIgnoreCase("months")){
			boolean found = false;
			for(Container c : getChildren()){
				if(!(c instanceof EXToolBar)){
					if(c instanceof EXMonthView){
						c.setDisplay(true);
						found = true;
					}else{
						c.setDisplay(false);
					}
				}
			}
			
			if(!found){
				addChild(new EXMonthView("weeks", Calendar.getInstance()));
			}
		}else if(container.getName().equals("add")){
			addPopup(new EXAgendaEventForm("form"));
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
		getChild("overlay").addChild(popup);
		
	}

}
