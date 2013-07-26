package org.racingtips.mtc.ui;

import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.wfs.Util;
import org.racingtips.mtc.RaceCardItem;

public class EXPublicVote_ extends EXXHTMLFragment implements EventDispatcher {
	
	

	public EXPublicVote_(String name) {
		super(name, "templates/racingtips/EXPublicVote.xhtml");
		setStyle("width", "200px");
		setStyle("margin-left", "8px");
		addClass("exceltable");
		setAttribute("cellpadding", "0").setAttribute("cellspacing", "0");
		init();
	}
	
	
	public void init(){
		String[] as = new String[]{"first", "second", "third", "fourth"};
		for(String name : as){
			Container a = new EXContainer(name,"a").setText(EXPublicVote.UNCHECKED).addEvent(DISPATCHER, Event.CLICK);
			addChild(a);
		}
		
		Container save = new EXContainer("save","a").setText("<img style=\"padding: 4px 0\" src=\"icons-2/fugue/icons/tick.png\"></img>").addEvent(DISPATCHER, Event.CLICK);
		Container cancel = new EXContainer("cancel","a").setText("<img style=\"padding: 4px 0\" src=\"icons-2/fugue/icons/cross-circle.png\"></img>").addEvent(DISPATCHER, Event.CLICK);
		addChild(save);
		addChild(cancel);
		
	}
	
	
	
	public RaceCardItem getItem(){
		return (RaceCardItem)SpringUtil.getRepositoryService().getFile(getAttribute("path"), Util.getRemoteUser());
	}
	
	public void setItem(RaceCardItem item){
		setAttribute("path", item.getAbsolutePath());
		int rank = item.getRank(Util.getRemoteUser());
		String[] as = new String[]{"first", "second", "third", "fourth"};
		getChild(as[(rank-1)]).setText(EXPublicVote.CHECKED).setAttribute("checked", "true");
	}
	
	public void save(){
		String[] as = new String[]{"first", "second", "third", "fourth"};
		int index =1;
		boolean voted = false;
		for(String s : as){
			Container c = getChild(s);
			if("true".equals(c.getAttribute("checked") )){
				voted = true;
				break;
			}	
			index++;	
		}
		
		if(voted){
			try{
				getItem().vote(index);
			}catch(Exception e){
				//if(e.getMessage().eq)
				e.printStackTrace();
			}
		}
	}


	@Override
	public void executeAction(Container source) {
		
		if(source.getName().equalsIgnoreCase("save")){
			save();
			getParent().setRendered(false);
			this.remove();
		}else if(source.getName().equalsIgnoreCase("cancel")){
			getParent().setRendered(false);
			this.remove();
		}else{
			String[] as = new String[]{"first", "second", "third", "fourth"};
			for(String s : as){
				Container c = getChild(s);
				if(!c.getName().equals(source.getName())){
					c.setText(EXPublicVote.UNCHECKED);
					c.setAttribute("checked", "false");
				}
			}
			source.setText(EXPublicVote.CHECKED);
			source.setAttribute("checked", "true");
		}
		
		
	}

}
