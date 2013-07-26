package org.racingtips.ui.cards;

import org.castafiore.ui.Container;
import org.castafiore.ui.ex.ViewModel;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.racingtips.ui.LeftNavSensitive;

public class EXStable extends EXXHTMLFragment implements LeftNavSensitive{

	public EXStable()throws Exception {
			super("EXStable", "templates/racingtips/EXStable.xhtml");
		
			
		}

	

	@Override
	public ViewModel<Container> getLeftNavigation() {
		return new StablesMenuModel();
	}
}
