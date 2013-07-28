package org.castafiore.ecm.ui.mac.ecm;

import java.util.LinkedList;
import java.util.List;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.mac.EXMacFinderColumn;
import org.castafiore.ui.mac.events.OpenColumnEvent;
import org.castafiore.ui.mac.events.OpenColumnEventHandler;
import org.castafiore.ui.mac.item.EXMacLabel;
import org.castafiore.ui.mac.item.MacColumnItem;
import org.castafiore.ui.mac.renderer.MacFinderColumnViewModel;
import org.castafiore.utils.StringUtil;

public class AbstractFileManipOptionsMacFinderViewModel implements MacFinderColumnViewModel, OpenColumnEventHandler {
	
	protected List<MacColumnItem> items = new LinkedList<MacColumnItem>();
	
	public AbstractFileManipOptionsMacFinderViewModel addLabel(String slabel, String leftIcon, String methodName){
		EXMacLabel label = new EXMacLabel("", slabel);
		label.setRightItem(new EXContainer("", "span").addClass("ui-icon-triangle-1-e"));
		label.setLeftItem(new EXContainer("", "span").addClass(leftIcon));
		label.setAttribute("method", methodName);
		label.addEvent(new OpenColumnEvent(), Event.CLICK);
		items.add(label);
		return this;
	}
	
	public void clear(){
		items.clear();
	}

	@Override
	public MacColumnItem getValueAt(int index) {
		return (MacColumnItem)items.get(index).setRendered(false);
	}

	@Override
	public int size() {
		return items.size();
	}
	
	@Override
	public EXMacFinderColumn getColumn(Container caller) {
		String methodName = caller.getAttribute("method");
		
		if(StringUtil.isNotEmpty(methodName)){
			MacFinderColumnViewModel model = caller.getAncestorOfType(EXMacFinderColumn.class).getViewModel();
			try{
				Object o = model.getClass().getMethod(methodName, Container.class).invoke(model, caller);
				return (EXMacFinderColumn)o;
			}catch(Exception e){
				throw new UIException("There should have a method called " + methodName + " in a MacFinderViewModel");
			}
		}
		throw new UIException("the attribute method is missing in the Caller");
	}

}
