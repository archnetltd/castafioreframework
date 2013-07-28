package org.castafiore.shoppingmall.ng;

import org.castafiore.searchengine.left.EXTreeBar;
import org.castafiore.shoppingmall.message.ui.EXMessagePanel;
import org.castafiore.shoppingmall.message.ui.MessageActionsTreeNode;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.layout.EXBorderLayoutContainer;
import org.castafiore.ui.ex.panel.EXPanel;

public class EXMyMessagesNG extends EXPanel{

	public EXMyMessagesNG(String name) {
		
		super(name, "My Messaging space");
		addClass("nextgeneration");
		EXBorderLayoutContainer layout = new EXBorderLayoutContainer();
		setBody(layout);
		EXMessagePanel panel = new EXMessagePanel("", "Messages");
		panel.showInbox();
		layout.addChild(panel, EXBorderLayoutContainer.CENTER);
		setStyle("z-index", "3000");
		setStyle("width", "883px");
		layout.getContainer(EXBorderLayoutContainer.LEFT).setStyle("width", "120px");
		
		EXTreeBar bar = new EXTreeBar("", "My Inbox", new MessageActionsTreeNode());
		layout.addChild(bar, EXBorderLayoutContainer.LEFT);
		bar.getDescendentByName("title").addClass("ui-widget-header").addClass("ui-corner-top").setStyle("width", "120px");
		bar.getDescendentByName("ul").addClass("ui-widget-content").setStyle("width", "118px");
		for(Container li : bar.getDescendentByName("ul").getChildren()){
			li.addClass("ui-state-default").addClass("ui-corner-all");
		}
		layout.getContainer(EXBorderLayoutContainer.LEFT).setStyle("vertical-align", "top");
		layout.getContainer(EXBorderLayoutContainer.CENTER).setStyle("vertical-align", "top");
		//setStyle("wudth", value)
	}

}
