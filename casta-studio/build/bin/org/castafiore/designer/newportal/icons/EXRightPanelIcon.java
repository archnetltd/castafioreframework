package org.castafiore.designer.newportal.icons;

import org.castafiore.ui.ex.EXContainer;

public class EXRightPanelIcon extends EXPortalIcon{

	public EXRightPanelIcon() {
		super("RightPanel");
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
				.addChild(new EXContainer("page", "div")
					.setStyle("width", "74px")
					.setStyle("border", "solid 1px")
					.setStyle("background", "tan")
					.setStyle("text-align", "center")
					.setStyle("font-wight", "bold")
					.setStyle("height", "27px")
					.setStyle("float", "left").setText("Page"))
				.addChild(new EXContainer("right", "div")
					.setStyle("width", "20px")
					.setStyle("border", "solid 1px")
					.setStyle("margin-left", "2px")
					.setStyle("text-align", "center")
					.setStyle("font-size", "7px")
					.setStyle("height", "27px")
					.setStyle("float", "left").setText("Right"))
				
				
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
