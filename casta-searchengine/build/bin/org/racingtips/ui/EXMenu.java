package org.racingtips.ui;

import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.ResourceUtil;
import org.racingtips.mtc.ui.EXRace;
import org.racingtips.mtc.ui.result.EXRaceResult;
import org.racingtips.ui.cards.EXStable;
import org.racingtips.ui.tips.EXLastMinuteTips;
import org.racingtips.ui.tips.EXTips;

public class EXMenu extends EXXHTMLFragment implements Event {

	public EXMenu() {
		super("EXMenu", ResourceUtil.getDownloadURL("ecm", "/root/users/racingtips/EXMenu.xhtml"));
		addClass("first-of-type").setStyle("text-decoration", "none");
		addChild(new EXContainer("home", "a").addEvent(this, CLICK).setText("Home").setAttribute("pageId", EXHome.class.getName()).setAttribute("href", "#TG").addClass("yuimenubaritemlabel"));
		addChild(new EXContainer("tips", "a").addEvent(this, CLICK).setText("Tips").setAttribute("pageId", EXTips.class.getName()).setAttribute("href", "#GH").addClass("yuimenubaritemlabel"));
		addChild(new EXContainer("horseRacing", "a").addEvent(this, CLICK).setText("Mauritius Horse Racing").setAttribute("pageId", EXCardHome.class.getName()).setAttribute("href", "#GH").addClass("yuimenubaritemlabel"));
		addChild(new EXContainer("stables", "a").addEvent(this, CLICK).setText("Stables").setAttribute("pageId", EXStable.class.getName()).setAttribute("href", "#GH").addClass("yuimenubaritemlabel"));
		addChild(new EXContainer("aboutMauritius", "a").addEvent(this, CLICK).setText("About Mauritius").setAttribute("pageId", EXAboutMauritius.class.getName()).setAttribute("href", "#GH").addClass("yuimenubaritemlabel"));
		addChild(new EXContainer("aboutUs", "a").setText("About Us").setAttribute("pageId", EXAboutUs.class.getName()).setAttribute("href", "#RT").addClass("yuimenubaritemlabel"));
		
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mergeCommand(new ClientProxy("#iamloading").setStyle("display", "block")).makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		for(Container c : getChildren()){
			c.setStyleClass("yuimenubaritemlabel");
		}
		container.setStyleClass("yuimenubaritemlabel active");
		String pageId = container.getAttribute("pageId");
		try{
			Class clazz = Thread.currentThread().getContextClassLoader().loadClass(pageId);
			getAncestorOfType(EXPortal.class).showPage(clazz, true);
		}catch(Exception e){
			throw new UIException(e);
		}
		
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		container.appendJSFragment("_gaq.push(['_trackEvent', 'Navigation', 'TopMenu', '"+container.getName()+"']);");
		container.mergeCommand(new ClientProxy("#iamloading").setStyle("display", "none"));
		
	}

}