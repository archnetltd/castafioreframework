package org.castafiore.designer.newpage;

import java.util.Map;

import org.castafiore.designer.newportal.body.AbstractWizardBody;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;

import org.castafiore.ui.scripting.EXXHTMLFragment;

public class EXNewPage extends EXXHTMLFragment implements Event{
	private NewPage newPage = new NewPage();

	public EXNewPage(String name) {
		super(name, "designer/newportal/EXNewPage.xhtml");
		addChild(new EXInput("pageWidth"));
		addChild(new EXContainer("body", "div").addClass("ui-widget-content").addChild(new EXNewPageBody("EXNewPageBody")));
		addChild(new EXContainer("next", "button").setText("<span class=label>Finish</span>").setStyleClass("red" ).addEvent(this, Event.CLICK).setStyle("float", "right"));
		addChild(new EXContainer("back", "button").setText("<span class=label>Cancel</span>").setStyleClass("green" ).addEvent(this, Event.CLICK).setStyle("float", "left"));
		setStyle("z-index", "5000");
	}
	
	
	public NewPage getNewPage(){
		return newPage;
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
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
