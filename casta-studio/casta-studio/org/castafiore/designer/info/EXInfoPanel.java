package org.castafiore.designer.info;

import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;

public class EXInfoPanel extends EXContainer{

	public EXInfoPanel(String name, Container container) {
		super(name, "div");
		addClass("genPopup");
		setStyle("z-index", "3");
		setDraggable(true);
		
		addChild(new EXContainer("header", "div").addClass("popup-header"));
		getChild("header").addChild(new EXContainer("title", "h3").addClass("title").setText("Information panel"));
		//getChild("header").addChild(new EXContainer("btnContainer", "div").addClass("button-container"));
		
		addChild(new EXContainer("content", "div").addClass("popup-content"));
		
		getContentContainer().addChild(new EXDimensionInfo("dim",container));
		getContentContainer().addChild(new EXContainer("", "hr"));
		getContentContainer().addChild(new EXPaddingMarginInfo("pad", container, "Padding"));
		getContentContainer().addChild(new EXContainer("", "hr"));
		getContentContainer().addChild(new EXPaddingMarginInfo("marg", container, "Margin"));
		
	}
	
	public void addButton(Container div){
		getDescendentByName("btnContainer").addChild(div);
	}
	
	public void setItem(Container item){
		getDescendentByName("title").setText( item.getName());
		((EXDimensionInfo)getDescendentByName("dim")).setContainer(item);
		((EXPaddingMarginInfo)getDescendentByName("pad")).setContainer(item);
		((EXPaddingMarginInfo)getDescendentByName("marg")).setContainer(item);
		
	}
	public Container getContentContainer(){
		return getChild("content");
	}

}
