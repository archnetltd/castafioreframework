package org.castafiore.shoppingmall.product.ui.tab;

import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.tabbedpane.TabModel;
import org.castafiore.ui.tabbedpane.TabPanel;
import org.castafiore.ui.tabbedpane.TabRenderer;

public class ProductTabRenderer implements TabRenderer{

	@Override
	public Container getComponentAt(TabPanel pane, TabModel model, int index) {
		Container a = new EXContainer("", "a").addClass("ex-tab-a").setAttribute("href", "#").addChild(new EXContainer("", "span").setText(model.getTabLabelAt(pane, index, false)));
		return new EXContainer("", "li").addClass("ex-tab-li").addChild( a);
	}

	@Override
	public void onDeselect(TabPanel pane, TabModel model, int index,
			Container tab) {
		tab.removeClass("current");
		tab.getParent().setAttribute("class", "ex-tab-ul");
		
	}

	@Override
	public void onSelect(TabPanel pane, TabModel model, int index, Container tab) {
		tab.addClass("current");
		tab.getParent().setAttribute("class", "ex-tab-ul");
	}

	

}
