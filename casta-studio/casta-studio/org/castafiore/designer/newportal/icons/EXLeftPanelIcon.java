package org.castafiore.designer.newportal.icons;

import org.castafiore.ui.ex.EXContainer;

public class EXLeftPanelIcon extends EXPortalIcon {

	public EXLeftPanelIcon() {
		super("LeftPanel");
		getChild("img").addChild(
				new EXContainer("top", "div").setText("Header")
					.setStyle("width", "100px")
					.setStyle("border", "solid 1px")
					.setStyle("margin", "2px auto")
					.setStyle("text-align", "center")
					.setStyle("font-size", "7px")
					.setStyle("height", "9px")
				);
		
		getChild("img").addChild(
				new EXContainer("", "div")
					.setStyle("width", "100px")
					
					.setStyle("margin", "2px auto")
					.setStyle("height", "29px")
				.addChild(new EXContainer("left", "div")
					.setStyle("width", "20px")
					.setStyle("border", "solid 1px")
					.setStyle("margin-right", "2px")
					.setStyle("text-align", "center")
					.setStyle("font-size", "7px")
					.setStyle("height", "27px")
					.setStyle("float", "left").setText("Left"))
				.addChild(new EXContainer("left", "div")
						.setStyle("width", "74px")
						.setStyle("border", "solid 1px")
						.setStyle("background", "tan")
						.setStyle("text-align", "center")
						.setStyle("font-wight", "bold")
						.setStyle("height", "27px")
						.setStyle("float", "left").setText("Page"))
				
				);
		
		getChild("img").addChild(
				new EXContainer("bottom", "div").setText("Footer")
					.setStyle("width", "100px")
					.setStyle("border", "solid 1px")
					.setStyle("margin", "2px auto")
					.setStyle("text-align", "center")
					.setStyle("font-size", "7px")
					.setStyle("height", "9px")
				);
	}

}
