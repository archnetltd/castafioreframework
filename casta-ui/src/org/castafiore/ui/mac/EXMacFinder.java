package org.castafiore.ui.mac;


import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.utils.StringUtil;

public class EXMacFinder extends EXContainer{

	public EXMacFinder(String name) {
		super(name, "div");
		addClass("ui-finder").addClass("ui-widget").addClass("ui-widget-header");
		
		Container title = new EXContainer("finderTitle", "div").addClass("ui-finder-title");
		addChild(title);
		Container span = new EXContainer("title", "span");
		title.addChild(span);
		
		Container header = new EXContainer("header", "div").addClass("ui-finder-header");
		addChild(header);
		
		Container wrapper = new EXContainer("wrapper", "div").addClass("ui-finder-wrapper").addClass("ui-widget-content");
		addChild(wrapper);
		
		Container finderContainer = new EXContainer("finderContainer", "div").addClass("ui-finder-container");
		finderContainer.setStyle("width", "auto");
		wrapper.addChild(finderContainer);
		
		addButton("Close", "Close", "ui-icon-closethick", true,new FinderCloseEvent());
		addChild(new EXContainer("popupContainer", "div").setStyle("position", "absolute"));
		
	}

	/**
	 * Sets the title of the finder
	 * @param title
	 */
	public void setTitle(String title){
		getChild("finderTitle").getChild("title").setText(title);
		getChild("finderTitle").setRendered(false);
	}

	public Container getHeader(){
		return getChild("header");
	}
		
	public void addButton(String name,String title, String iconCss, boolean left,Event event){
		Container div = new EXContainer(name, "div");
		if(left){
			div.setStyle("float", "left");
		}
		div.addClass("ui-finder-button ui-state-default").setAttribute("title", title);
		Container span = new EXContainer("", "span").addClass("ui-icon").addClass(iconCss);
		div.addChild(span);
		getHeader().addChild(div);
		if(event != null){
			div.addEvent(event, Event.CLICK);
		}
	}
	
	public void addColumn(EXMacFinderColumn column){
		Container fContainer = getDescendentByName("finderContainer");
		String parentWidth = fContainer.getStyle("width");
		String width = column.getStyle("width");
		fContainer.setStyle("width", "auto");
		if(!StringUtil.isNotEmpty(width)){
			width = "300px";
		}
		column.setAttribute("origwidth", width);
		boolean calculateParentWidth = false;
		if(fContainer.getChildren().size() == 0 || fContainer.getChildren().size() == 1){
			//if first or second child, take full space
			column.setStyle("width", (String)null);
		}else{
			column.setStyle("width", width);
			calculateParentWidth = true;
		}
		
		if(fContainer.getChildren().size() == 1 || fContainer.getChildren().size() == 2){
			//set first or second column to original width
			fContainer.getChildren().get(fContainer.getChildren().size()-1).setStyle("width", fContainer.getChildren().get(fContainer.getChildren().size()-1).getAttribute("origwidth"));
			
			
		}
		
		int left = 0;
		for(int i = 0; i < fContainer.getChildren().size(); i ++){
			left = left + fContainer.getChildByIndex(i).getWidth().getAmount();
		}
		column.setStyle("left", left + "px");
		
		
		getDescendentByName("finderContainer").addChild(column);
		if(calculateParentWidth){
			parentWidth = (left + column.getWidth().getAmount()) + "px";
			getDescendentByName("finderContainer").setStyle("width", parentWidth);
		}
		//adjustColumnWidth();
	}

	
	public static class FinderCloseEvent implements Event{

		@Override
		public void ClientAction(ClientProxy container) {
			container.getAncestorOfType(EXMacFinder.class).fadeOut(100,
					container.clone().makeServerRequest(this));
			
		}

		@Override
		public boolean ServerAction(Container container, Map<String, String> request)
				throws UIException {
			container.getAncestorOfType(EXMacFinder.class).remove();
			return true;
		}

		@Override
		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			// TODO Auto-generated method stub
			
		}

	}
	

}
