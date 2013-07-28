package org.castafiore.designer.newportal;

import java.util.Map;

import org.castafiore.designer.newportal.body.AbstractWizardBody;
import org.castafiore.designer.newportal.body.EXNewPortalBody;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;

import org.castafiore.ui.scripting.EXXHTMLFragment;

public class EXNewPortal extends EXXHTMLFragment implements Event{
	
	private NewPortal newPortal = new NewPortal();

	public EXNewPortal(String name) {
		super(name, "designer/newportal/EXNewPortal.xhtml");
		//addChild(new EXInput("filter"));
		addChild(new EXContainer("body", "div").addClass("ui-widget-content").addChild(new EXNewPortalBody("EXNewPortalBody")));
		setStyle("z-index", "6000");
		addChild(new EXContainer("next", "button").addClass("green").setText("<span class=label>Next</span>").addEvent(this, Event.CLICK).setStyle("float", "right").setStyle("margin-top", "4px").setStyle("padding", "2px 20px").setStyle("height", "20px"));
		addChild(new EXContainer("back", "button").addClass("red").setText("<span class=label>Back</span>").addEvent(this, Event.CLICK).setStyle("float", "left").setStyle("margin-top", "4px").setStyle("padding", "2px 20px").setStyle("height", "20px"));
	}
	
	
	public NewPortal getNewPortal(){
		return newPortal;
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask(container.getAncestorOfType(EXNewPortal.class)).makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		AbstractWizardBody body = getDescendentOfType(AbstractWizardBody.class);
		AbstractWizardBody next = body.clickButton(container);
		if(next != null){
			Container c = body.getParent();
			c.getChildren().clear();
			c.setRendered(false);
			c.addChild(next);
		}
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
