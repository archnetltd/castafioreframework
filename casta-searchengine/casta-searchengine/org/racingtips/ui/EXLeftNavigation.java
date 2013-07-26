package org.racingtips.ui;

import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.ViewModel;
import org.castafiore.ui.scripting.EXXHTMLFragment;


public class EXLeftNavigation  extends EXXHTMLFragment {

	public EXLeftNavigation() {
		super("EXLeftNavigation", "templates/racingtips/EXLeftNavigation.xhtml");
		addClass("block-top");
		Container menu = new EXContainer("menu", "ul").addClass("menu");
		addChild(menu);
	}
	
	
	public void setModel(ViewModel<Container> menuModel){
		Container menu = getChild("menu");
		menu.getChildren().clear();
		menu.setRendered(false);
		if(menuModel == null){
			setDisplay(false);
		}else{
			int size = menuModel.size();
			Container c = null;
			for(int i = 0; i < size; i++ ){
				c = menuModel.getComponentAt(i, menu);
				if(i == 0){
					c.addClass("first");
				}
				menu.addChild(c);
			}
			if(c != null){
				c.addClass("last");
			}
			setDisplay(true);
		}
	}
}
