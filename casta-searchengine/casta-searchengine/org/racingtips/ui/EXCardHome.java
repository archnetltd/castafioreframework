package org.racingtips.ui;

import org.castafiore.ui.Container;
import org.castafiore.ui.ex.ViewModel;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.ResourceUtil;
import org.racingtips.ui.cards.CardsMenuModel;

public class EXCardHome extends EXXHTMLFragment implements LeftNavSensitive {

	public EXCardHome() {
		super("EXCardHome", ResourceUtil.getDownloadURL("ecm", "/root/users/racingtips/articles/EXCardHome.xhtml"));
	}
	
	@Override
	public ViewModel<Container> getLeftNavigation() {
		return new CardsMenuModel();
	}

}
