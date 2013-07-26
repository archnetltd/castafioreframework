package org.castafiore.shoppingmall.crm;

import java.util.Date;

import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.crm.newsletter.EXNewsletter;
import org.castafiore.shoppingmall.crm.newsletter.EXNewsletterList;
import org.castafiore.shoppingmall.crm.newsletter.Newsletter;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.button.EXButton;



public class EXCrmPanel extends EXContainer implements EventDispatcher{
	
	public EXCrmPanel(String name) {
		super(name, "div");
		
		
		addChild(
				
				new EXContainer("", "h3").addClass("ui-widget-header").addChild(
						new EXContainer("showSubscribers", "img").setAttribute("title", "Show subscribers list").setStyle("background", "steelblue").setAttribute("src", "osicons/newsletter/subscribers.png").addEvent(DISPATCHER, Event.CLICK)
						
					).addChild(
						
						new EXContainer("showNewsletters", "img").setAttribute("title", "Show newsletters list").setAttribute("src", "osicons/newsletter/newsletter.png").addEvent(DISPATCHER, Event.CLICK)
					));
		addChild(new EXContainer("btnCtn", "div").addClass("fg-toolbar ui-widget-header ui-helper-clearfix").setStyle("width", "748px").setStyle("padding", "12px").setStyle("float", "left"));
		showSubscribers();
	}
	public void setTitle(String title){
		getDescendentByName("title").setText(title);
	}
	
	
	
	public void showSubscribers(){
		getDescendentByName("showNewsletters").setStyle("background", "transparent");
		getDescendentByName("showSubscribers").setStyle("background", "steelblue");
		boolean found = false;
		for(Container c : getChildren()){
			if(c.getTag().equalsIgnoreCase("h3")){
				continue;
			}
			
			
			if( c instanceof EXSubscribersList){
				c.setDisplay(true);
				found = true;
				
			}else{
				c.setDisplay(false);
			}
		}
		
		if(!found){
			EXSubscribersList list = new EXSubscribersList("");
			
			addChild(list.addClass("ex-content"));
		}
	}
	
	
	
	
	public void showNewsletters(){
		getDescendentByName("showNewsletters").setStyle("background", "steelblue");
		getDescendentByName("showSubscribers").setStyle("background", "transparent");
		boolean found = false;
		for(Container c : getChildren()){
			if(c.getTag().equalsIgnoreCase("h3")){
				continue;
			}
			
			if(c.getName().equalsIgnoreCase("btnCtn")){
				c.setDisplay(true);
				if(c.getChildren().size() > 0){
					c.getChildren().clear();
					c.setRendered(false);
				}
				
				//c.addChild(new EXContainer("deleteNewsletters","button").setText("Delete").addEvent(DISPATCHER, Event.CLICK));
				c.addChild(new EXButton("newNewsletter","Schedule newsletter").addEvent(DISPATCHER, Event.CLICK));
				continue;
			}
			
			if( c instanceof EXNewsletterList){
				c.setDisplay(true);
				found = true;
				
			}else{
				c.setDisplay(false);
			}
		}
		
		if(!found){
			EXNewsletterList list = new EXNewsletterList("");
			
			addChild(list);
		}
	}
	
	
	public void saveNewsletter(){
		Newsletter nl = MallUtil.getCurrentMerchant().createNewsletter("NL " + new Date().getTime());
		EXNewsletter ui = getDescendentOfType(EXNewsletter.class);
		
		ui.fillNewsletter(nl); 
		nl.save();
		ui.getParent().setRendered(false);
		ui.remove();
		showNewsletters();
		
	}
	public void newNewsletter(){
		boolean found = false;
		for(Container c : getChildren()){
			if(c.getTag().equalsIgnoreCase("h3")){
				continue;
			}
			
			if(c.getName().equalsIgnoreCase("btnCtn")){
				if(c.getChildren().size() > 0){
					c.getChildren().clear();
					c.setRendered(false);
				}
				
				c.setDisplay(false);
				continue;
			}
			
			if( c instanceof EXNewsletter){
				c.setDisplay(true);
				found = true;
				((EXNewsletter)c).clear();
			}else{
				c.setDisplay(false);
			}
		}
		
		if(!found){
			EXNewsletter list = new EXNewsletter("");
			
			addChild(list);
		}
	}
	
	public void cancelNewsletter(){
		EXNewsletter ui = getDescendentOfType(EXNewsletter.class);
		ui.getParent().setRendered(false);
		ui.remove();
		showNewsletters();
	}
	
	public void deleteNewsletters(){
		getDescendentOfType(EXNewsletterList.class).deleteSelected();
	}
	
	@Override
	public void executeAction(Container source) {
		if(source.getName().equalsIgnoreCase("showSubscribers")){
			showSubscribers();
		}else if(source.getName().equalsIgnoreCase("showNewsletters")){
			showNewsletters();
		}else if(source.getName().equalsIgnoreCase("newNewsletter")){
			newNewsletter();
		}else if(source.getName().equalsIgnoreCase("saveNewsletter")){
			saveNewsletter();
			getDescendentOfType(EXNewsletterList.class).showNewsletters(0);
		}else if(source.getName().equalsIgnoreCase("cancelNewsletter")){
			cancelNewsletter();
		}else{
			try{
				getClass().getMethod(source.getName(), new Class[]{}).invoke(this, new Object[]{});
			}catch(Exception e){
				throw new UIException(e);
			}
		}
	
		
		
	}

}
