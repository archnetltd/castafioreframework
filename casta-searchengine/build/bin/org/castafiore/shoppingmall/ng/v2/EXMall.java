package org.castafiore.shoppingmall.ng.v2;

import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.panel.EXOverlayPopupPlaceHolder;

public class EXMall extends EXContainer implements PopupContainer{

	public EXMall(String name) {
		super(name, "div");
		addClass("mall");
		addChild(new EXHeader("head"));
		addChild(new EXBody("body"));
		addChild(new EXOverlayPopupPlaceHolder("overlay"));
	}
	
	public Container  goToPage(Class<? extends Container> uiPage){
		Container c =getChild("body").getChild("center");
		boolean found = false;
		Container p = null;
		for(Container cc : c.getChildren()){
			if(uiPage.isInstance(cc)){
				cc.setDisplay(true);
				p = cc;
			}else{
				if(cc.isVisible()){
					cc.setDisplay(false);
				}
			}
		}
		try{
			if(p==null){
				p = uiPage.newInstance();
				c.addChild(p);
			}
			return p;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	@Override
	public void addPopup(Container popup) {
		getChild("overlay").addChild(popup);
		
	}

}
