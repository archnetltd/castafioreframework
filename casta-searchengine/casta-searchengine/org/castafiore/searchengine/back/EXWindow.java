package org.castafiore.searchengine.back;

import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.ex.panel.EXOverlayPopupPlaceHolder;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.ui.ex.panel.Panel;

public class EXWindow extends EXPanel implements EventDispatcher, PopupContainer, Panel{

	public EXWindow(String name, String title) {
		super(name, "div");
//		addClass("ex-window");
//		addChild(
//				new EXContainer("header", "div").addClass("ex-header")
//					.addChild(new EXContainer("icon", "img").addClass("ex-icon").setAttribute("src", "icons-2/fugue/icons/sql-join-right.png"))
//					.addChild(new EXContainer("title", "h5").setText(title))
//					.addChild(new EXContainer("close", "a").setAttribute("href", "#").addClass("minimize").addEvent(DISPATCHER, Event.CLICK))
//		);
//		
//		addChild(new EXContainer("bodyContainer", "div").addClass("ex-body"));
//		setDraggable(true);
		addChild(new EXOverlayPopupPlaceHolder("overlay"));
		setTitle(title);
	}
	
//	public Panel setTitle(String title){
//		getDescendentByName("title").setText(title);
//		return this;
//	}
//	
//	public String getTitle(){
//		return getDescendentByName("title").getText();
//	}
//	
//	
//	public void setIcon(String icon){
//		getDescendentByName("icon").setAttribute("src", icon);
//	}
//	
//	public String getIcon(){
//		return getDescendentByName("icon").getAttribute("src");
//	}
//
//	
//	public Container getBody(){
//		return getChild("bodyContainer").getChildByIndex(0);
//	}
//	public EXWindow setBody(Container body){
//		getChild("bodyContainer").getChildren().clear();
//		getChild("bodyContainer").setRendered(false);
//		getChild("bodyContainer").addChild(body);
//		return this;
//	}

	@Override
	public void executeAction(Container source) {
		if(source.getName().equals("close"))
			getAncestorOfType(Desktop.class).removeWindow(getName());
		
	}
	
	
//	public Container setDraggable(boolean draggable)
//	{
//		if(draggable)
//		{
//			JMap options = new JMap().put("opacity", 0.35).put("handle", "#" +getDescendentByName("header").getId());
//			options.put("containment", "document");
//			setDraggable(true, options);
//			setStyle("position", "absolute");
//			setStyle("top", "10%");
//			setStyle("left", "10%");
//		}
//		else
//		{
//			super.setDraggable(false);
//			setStyle("position", "static");
//		}
//		return this;
//	}

	@Override
	public void addPopup(Container popup) {
		getChild("overlay").addChild(popup);
		
	}

//	@Override
//	public Panel setShowCloseButton(boolean b) {
//		return this;
//	}
//
//	@Override
//	public Panel setShowFooter(boolean display) {
//		return this;
//	}
//
//	@Override
//	public Panel setShowHeader(boolean showHeader) {
//		return this;
//	}
	

}
