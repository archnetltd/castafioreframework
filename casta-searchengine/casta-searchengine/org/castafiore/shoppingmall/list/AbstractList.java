package org.castafiore.shoppingmall.list;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ComponentVisitor;

public abstract class AbstractList extends EXContainer {
	
	
	
	public AbstractList(String name, String tagName) {
		super(name, tagName);
	}

	public List<String> getSelectedItems(){
		
		final List<String> result = new ArrayList<String>();
		ComponentUtil.iterateOverDescendentsOfType(getChild("body"), AbstractListItem.class, new ComponentVisitor() {
			
			@Override
			public void doVisit(Container c) {
				if(((AbstractListItem)c).isChecked()){
					result.add(c.getAttribute("path"));
				}
				
				
			}
		});
		
		return result;
	}

}
