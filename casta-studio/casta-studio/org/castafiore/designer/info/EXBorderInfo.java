package org.castafiore.designer.info;

import org.castafiore.ui.Container;
import org.castafiore.ui.ex.form.EXColorPicker;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.ui.scripting.EXXHTMLFragment;

public class EXBorderInfo extends EXXHTMLFragment {

	public EXBorderInfo(String name, Container item) {
		super(name, "designer/EXBorderInfo.xhtml");
		addChild(new EXScrollableInput("border-top-width", item));
		addChild(new EXScrollableInput("border-right-width", item));
		addChild(new EXScrollableInput("border-bottom-width", item));
		addChild(new EXScrollableInput("border-left-width", item));
		
		addChild(new EXScrollableInput("-moz-border-radius-topleft", item));
		addChild(new EXScrollableInput("-moz-border-radius-topright", item));
		addChild(new EXScrollableInput("-moz-border-radius-bottomleft", item));
		addChild(new EXScrollableInput("-moz-border-radius-bottomright", item));
		
		
		addChild(new EXColorPicker("border-top-color"));
		addChild(new EXColorPicker("border-right-color"));
		addChild(new EXColorPicker("border-bottom-color"));
		addChild(new EXColorPicker("border-left-color"));
		
		//none,hidden,dashed,solid,double,grooved,ridge,inset,outset,inherit
		addChild(new EXSelect("border-top-style", new DefaultDataModel<Object>().addItem("none").addItem("hidden").addItem("dashed").addItem("solid").addItem("double").addItem("grooved").addItem("inset").addItem("outset").addItem("inherit")));
		addChild(new EXSelect("border-right-style", new DefaultDataModel<Object>().addItem("none").addItem("hidden").addItem("dashed").addItem("solid").addItem("double").addItem("grooved").addItem("inset").addItem("outset").addItem("inherit")));
		addChild(new EXSelect("border-bottom-style", new DefaultDataModel<Object>().addItem("none").addItem("hidden").addItem("dashed").addItem("solid").addItem("double").addItem("grooved").addItem("inset").addItem("outset").addItem("inherit")));
		addChild(new EXSelect("border-left-style", new DefaultDataModel<Object>().addItem("none").addItem("hidden").addItem("dashed").addItem("solid").addItem("double").addItem("grooved").addItem("inset").addItem("outset").addItem("inherit")));
		
	}

}
