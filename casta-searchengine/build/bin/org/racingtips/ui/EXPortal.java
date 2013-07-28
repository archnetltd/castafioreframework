package org.racingtips.ui;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.ResourceUtil;

public class EXPortal extends EXXHTMLFragment {

	public EXPortal(String name) {
		super(name, ResourceUtil.getDownloadURL("ecm", "/root/users/racingtips/EXPortal.xhtml"));
		addClass("min-width");
		addChild(new EXTop());
		addChild(new EXMenu());
		addChild(new EXContainer("headerRow4", "div"));
		addChild(new EXLeftNavigation());
		addChild(new EXLeftColumnAdds());
		addChild(new EXRightColumnAdds());
		addChild(new EXPageContainer());
		//addChild(new EXFooter());
		showPage(EXHome.class, true);
	}
	
	
	public void showPage(Class<? extends Container> pageType, boolean changeNav){
		try{
			EXPageContainer pc = (EXPageContainer)getChild("EXPageContainer");
			pc.showPage(pageType, changeNav);
		
		}catch(Exception e){
			throw new UIException(e);
		}
	}
	
	public void showPage(Container c, boolean changeNav){
		try{
			EXPageContainer pc = (EXPageContainer)getChild("EXPageContainer");
			pc.showPage(c, changeNav);
		
		}catch(Exception e){
			throw new UIException(e);
		}
	}

}
