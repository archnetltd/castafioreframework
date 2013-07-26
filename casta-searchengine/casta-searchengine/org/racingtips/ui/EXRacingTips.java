package org.racingtips.ui;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.RefreshSentive;
import org.castafiore.ui.ex.EXApplication;
import org.racingtips.mtc.ui.EXRace;
import org.racingtips.ui.cards.EXStable;
import org.racingtips.ui.tips.EXTips;

public class EXRacingTips extends EXApplication implements RefreshSentive{

	public EXRacingTips() {
		super("racingtips");
		try{
			SpringUtil.getSecurityService().login("youdo", "youdo");
		}catch(Exception e){
			e.printStackTrace();
		}
		addChild(new EXPortal("Portal"));
		highLight("home");
		
	}
	
	private void highLight(String name){
		EXMenu menu = getDescendentOfType(EXMenu.class);
		for(Container c : menu.getChildren()){
			c.setStyleClass("yuimenubaritemlabel");
		}
		menu.getChild(name).setStyleClass("yuimenubaritemlabel active");
	}

	@Override
	public void onRefresh() {
		Object p = getConfigContext().get("page");
		if(p != null){
			
			String page =p.toString();
			if(page.equalsIgnoreCase("card")){
				highLight("horseRacing");
				getDescendentOfType(EXPortal.class).showPage(EXCardHome.class, true);
			}else if(page.equalsIgnoreCase("home")){
				highLight("home");
				getDescendentOfType(EXPortal.class).showPage(EXHome.class, true);
			}else if(page.equalsIgnoreCase("aboutmauritius")){
				highLight("aboutMauritius");
				getDescendentOfType(EXPortal.class).showPage(EXAboutMauritius.class, true);
			}else if(page.equalsIgnoreCase("aboutus")){
				highLight("aboutUs");
				getDescendentOfType(EXPortal.class).showPage(EXAboutUs.class, true);
			}else if(page.equalsIgnoreCase("tips")){
				getDescendentOfType(EXPortal.class).showPage(EXTips.class, true);
				highLight("tips");
			}else if(page.equalsIgnoreCase("stables")){
				getDescendentOfType(EXPortal.class).showPage(EXStable.class, true);
				highLight("stables");
			}
		}else{
			getDescendentOfType(EXPortal.class).showPage(EXHome.class, true);
		}
		
	}

}
