package org.racingtips.mtc.ui;

import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXTextArea;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.wfs.Util;
import org.racingtips.mtc.RaceCardItem;

public class EXPersonalRating_ extends EXXHTMLFragment implements EventDispatcher  {

	public EXPersonalRating_(String name) {
		super(name, "templates/racingtips/EXPersonalRating.xhtml");
		
		addClass("personalRating");
		addChild(new EXTextArea("myNote").addEvent(DISPATCHER, Event.BLUR));
		
		addChild(new EXContainer("win", "button").setText("Win").addEvent(DISPATCHER, Event.CLICK));
		addChild(new EXContainer("place", "button").setText("Place").addEvent(DISPATCHER, Event.CLICK));
		addChild(new EXContainer("outsider", "button").setText("Outsider").addEvent(DISPATCHER, Event.CLICK));
	}
	
	
	public RaceCardItem getItem(){
		return (RaceCardItem)SpringUtil.getRepositoryService().getFile(getAttribute("path"), Util.getRemoteUser());
	}
	
	public void myNote(Container c ){
		getItem().addPersonalComment(  ((EXTextArea)c).getValue().toString()  );
	}
	
	
	public void win(Container c){
		c.setStyle("background", "url(\"racingtips/images/bg-li-act.gif\") repeat-x scroll center top transparent");
		c.setStyle("color", "white");
		
		getChild("outsider").setStyle("background", "url(\"racingtips/images/bg-but.gif\") repeat-x scroll center top transparent");
		getChild("outsider").setStyle("color", "black");
		getChild("place").setStyle("background", "url(\"racingtips/images/bg-but.gif\") repeat-x scroll center top transparent");
		getChild("place").setStyle("color", "black");
		getItem().addSelection(c.getName());
	}
	
	
	public void place(Container c){
		c.setStyle("background", "url(\"racingtips/images/bg-li-act.gif\") repeat-x scroll center top transparent");
		c.setStyle("color", "white");
		
		getChild("win").setStyle("background", "url(\"racingtips/images/bg-but.gif\") repeat-x scroll center top transparent");
		getChild("win").setStyle("color", "black");
		getChild("outsider").setStyle("background", "url(\"racingtips/images/bg-but.gif\") repeat-x scroll center top transparent");
		getChild("outsider").setStyle("color", "black");
		getItem().addSelection(c.getName());
	}
	
	
	public void outsider(Container c){
		
		c.setStyle("background", "url(\"racingtips/images/bg-li-act.gif\") repeat-x scroll center top transparent");
		c.setStyle("color", "white");
		
		getChild("win").setStyle("background", "url(\"racingtips/images/bg-but.gif\") repeat-x scroll center top transparent");
		getChild("win").setStyle("color", "black");
		getChild("place").setStyle("background", "url(\"racingtips/images/bg-but.gif\") repeat-x scroll center top transparent");
		getChild("place").setStyle("color", "black");
		getItem().addSelection(c.getName());
		
	}
	
	public void setItem(RaceCardItem item){
		setAttribute("path", item.getAbsolutePath());
		String note = item.getPersonalComment();
		String selection = item.getSelection();
		getDescendentOfType(EXTextArea.class).setValue(note);
		if(selection.length() > 0){
			Container container = getChild(selection);
			container.setStyle("background", "url(\"racingtips/images/bg-li-act.gif\") repeat-x scroll center top transparent");
			container.setStyle("color", "white");
		}
		
	}

	@Override
	public void executeAction(Container source) {
		try{
			getClass().getMethod(source.getName(), Container.class).invoke(this,source);
		}catch(Exception e){
			throw new UIException(e);
		}
		
	}
	
	

}
