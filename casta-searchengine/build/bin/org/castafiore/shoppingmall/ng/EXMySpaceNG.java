package org.castafiore.shoppingmall.ng;

import org.castafiore.searchengine.left.EXTreeBar;
import org.castafiore.shoppingmall.user.ui.EXMyAccountPanel;
import org.castafiore.shoppingmall.user.ui.UserActionsTreeNode;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.layout.EXBorderLayoutContainer;
import org.castafiore.ui.ex.panel.EXPanel;

public class EXMySpaceNG extends EXPanel{

	public EXMySpaceNG(String name) {
		super(name, "My personal space");
		addClass("nextgeneration");
		EXBorderLayoutContainer layout = new EXBorderLayoutContainer();
		setBody(layout);
		layout.addChild(new EXMyAccountPanel("", "About Me"), EXBorderLayoutContainer.CENTER);
		setStyle("z-index", "3000");
		setStyle("width", "700px");
		layout.getDescendentOfType(EXMyAccountPanel.class).showAboutMe();
		EXTreeBar bar = new EXTreeBar("", "My account", new UserActionsTreeNode());
		layout.addChild(bar, EXBorderLayoutContainer.LEFT);
		bar.getDescendentByName("title").addClass("ui-widget-header").addClass("ui-corner-top").setStyle("width", "142px");
		bar.getDescendentByName("ul").addClass("ui-widget-content");
		for(Container li : bar.getDescendentByName("ul").getChildren()){
			li.addClass("ui-state-default").addClass("ui-corner-all");
		}
		layout.getContainer(EXBorderLayoutContainer.LEFT).setStyle("vertical-align", "top");
		layout.getContainer(EXBorderLayoutContainer.CENTER).setStyle("vertical-align", "top");
	}

}
