package com.eliensons.ui.sales.cache;

import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.ui.ex.panel.Panel;

import com.eliensons.ui.plans.EXElieNSonsApplicationForm;

public class AppFormPanel extends EXPanel {

	public AppFormPanel() {
		super("AppFormPanel", "Application Form");
		setDisplay(false);
		setCloseButtonEvent(Panel.HIDE_EVENT);
		EXElieNSonsApplicationForm c = new EXElieNSonsApplicationForm();
		setBody(c);
		setStyle("width", "737px");
		setStyle("z-index", "4000");

		setStyle("top", "0px");
		setDisplay(false);

	}

}
