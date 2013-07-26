package org.racingtips.ui;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.ViewModel;

public class EXPageContainer extends EXContainer{

	public EXPageContainer() {
		super("EXPageContainer", "div");
		EXContainer pageAdd = new EXContainer("pageAdd", "img");
		pageAdd.setAttribute("src", "racingtips/images/bigbanneradd.png");
		pageAdd.addClass("PageAdd");
		addChild(pageAdd);
		Container p = new EXContainer("p", "div");
		addChild(p);
		
		EXContainer pageAdd2 = new EXContainer("pageAdd2", "img");
		pageAdd2.setAttribute("src", "racingtips/images/bigbanneradd.png");
		pageAdd2.addClass("PageAdd");
		addChild(pageAdd2);
		
	}
	
	public void showPage(Class<? extends Container> pageType, boolean changeNav){
		Container p = getChild("p");
		String clazz = null;
		if(pageType.equals(EXLogin.class)){
			clazz = p.getChildren().get(0).getClass().getName();
		}
		p.getChildren().clear();
		p.setRendered(false);
		try{
			Container c = pageType.newInstance();
			if(clazz != null){
				c.setAttribute("from", clazz);
			}
			p.addChild(c);
			if(changeNav){
				EXPortal portal = getAncestorOfType(EXPortal.class);
				EXLeftNavigation adds = portal.getDescendentOfType(EXLeftNavigation.class);
				if(c instanceof LeftNavSensitive){
					ViewModel<Container> model = ((LeftNavSensitive)c).getLeftNavigation();
					adds.setModel(model);
					
				}else{
					adds.setModel(null);
					
				}
			}
			
		}catch(Exception e){
			throw new UIException(e);
		}
	}
	
	public void showPage(Container c, boolean changeNav){
		Container p = getChild("p");
		
		p.getChildren().clear();
		p.setRendered(false);
		try{
			
			p.addChild(c);
			if(changeNav){
				EXPortal portal = getAncestorOfType(EXPortal.class);
				EXLeftNavigation adds = portal.getDescendentOfType(EXLeftNavigation.class);
				if(c instanceof LeftNavSensitive){
					ViewModel<Container> model = ((LeftNavSensitive)c).getLeftNavigation();
					adds.setModel(model);
					
				}else{
					adds.setModel(null);
					
				}
			}
			
		}catch(Exception e){
			throw new UIException(e);
		}
	}
	
	
	

}
