package org.castafiore.ui.mac.item;

import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;

public class EXMacLabel extends EXContainer implements MacColumnItem{

	public EXMacLabel(String name, String label) {
		super(name, "li");
		Container a= new EXContainer("a", "a").setAttribute("title", label).setAttribute("href", "#");
		addChild(a);
		Container span = new EXContainer("label", "span").setText(label);
		a.addChild(span);
	}
	
	
	
	public void setLabel(String label){
		getChild("a").setAttribute("title", label).getChild("label").setText(label);	
	}
	
	public void setRightItem(Container rightItem){
		if(getChildren().size() > 1){
			getChildByIndex(1).remove();
			setRightItem(rightItem);
		}else{
			addChild(rightItem.addClass("ui-icon").addClass("ui-icon-right").addClass("ui-finder-icon"));
		}
	}
	
	public void setLeftItem(Container leftItem){
		if(getChild("a").getChildren().size() > 1){
			getChild("a").getChildByIndex(1).remove();
			setLeftItem(leftItem);
		}else{
			getChild("a").addChild(leftItem.addClass("ui-icon").addClass("ui-icon-left").addClass("ui-finder-icon"));
		}
	}
	
	public Container getLeftItem(){
		if(getChild("a").getChildren().size() > 1){
			return getChild("a").getChildByIndex(1);
			
		}
		return null;
	}
	
	public Container getRightItem(){
		if(getChildren().size() > 1){
			return getChildByIndex(1);
		}
		return null;
	}

}
