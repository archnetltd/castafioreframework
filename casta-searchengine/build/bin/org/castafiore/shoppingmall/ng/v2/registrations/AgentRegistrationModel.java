package org.castafiore.shoppingmall.ng.v2.registrations;

import org.castafiore.shoppingmall.ng.v2.AccordeoPanelModel;
import org.castafiore.shoppingmall.ng.v2.EXAccordeonPanel;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.form.EXTextArea;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.ui.tabbedpane.TabModel;
import org.castafiore.ui.tabbedpane.TabPanel;

public class AgentRegistrationModel implements AccordeoPanelModel{
	
	private String[] labels = new String[]{"User information", "Other relevant Information"};

	@Override
	public int getSelectedTab() {
		
		return 0;
	}

	@Override
	public Container getTabContentAt(TabPanel pane, int index) {
		if(index == 0){
			return new EXXHTMLFragment("", "templates/v2/registrations/EXUserInformation.xhtml");
		}else if(index == 1){
			return new EXXHTMLFragment("", "templates/v2/registrations/EXRelevantInformation.xhtml").addChild(new EXTextArea("relevant.information"));
		}else{
			return null;
		}
	}

	@Override
	public String getTabLabelAt(TabPanel pane, int index, boolean selected) {
		return labels[index];
	}

	@Override
	public int size() {
		
		return labels.length;
	}

	@Override
	public boolean onNext(Container fromBody,  int fromIndex,
			 EXAccordeonPanel panel) {
		return true;
		
	}

}
