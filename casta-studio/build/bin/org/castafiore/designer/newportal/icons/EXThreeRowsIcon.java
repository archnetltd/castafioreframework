package org.castafiore.designer.newportal.icons;

import org.castafiore.ui.ex.EXContainer;

public class EXThreeRowsIcon extends EXPortalIcon {

	public EXThreeRowsIcon() {
		super("ThreeRows");
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
				new EXContainer("top", "div").setText("Top")
					.setStyle("width", "100px")
					.setStyle("border", "solid 1px")
					.setStyle("margin", "2px auto")
					.setStyle("text-align", "center")
					.setStyle("font-weight", "bold")
					.setStyle("height", "29px")
					.setStyle("background", "tan")
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
