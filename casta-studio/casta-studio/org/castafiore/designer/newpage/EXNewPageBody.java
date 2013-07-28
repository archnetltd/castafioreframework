package org.castafiore.designer.newpage;

import java.util.Map;

import org.castafiore.designer.newportal.body.AbstractWizardBody;
import org.castafiore.designer.newportal.icons.EXPortalIcon;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.scripting.EXXHTMLFragment;

public class EXNewPageBody extends AbstractWizardBody implements Event {
	
	private final static String[] Layouts = new String[]{
		"Empty",
		"FourCells",
		"Mixed",
		"MixedInverse",
		"ThreeRows",
		"TwoCols",
		"TwoRows"};
	
	public EXNewPageBody(String name) {
		super(name);
		for(String s : Layouts){
			EXXHTMLFragment fragment = new EXXHTMLFragment(s, "designer/newpage/" + s + ".xhtml");
			addChild(fragment);
			fragment.addClass("template").addEvent(this, CLICK);
		}
	}

	

	@Override
	public AbstractWizardBody clickButton(Container button) {
		if(button.getName().equals("next")){
			for(Container c : getChildren()){
				if("true".equals(c.getAttribute("selected"))){
					NewPage newPage = getAncestorOfType(EXNewPage.class).getNewPage();
					newPage.setLayout(c.getName());
					return new EXSelectPageFolderBody("EXSelectPageFolderBody", getAncestorOfType(EXNewPage.class).getAttribute("portalPath") );
				}
			}
			//String selected = get
			
		}else if (button.getName().equals("back")){
			EXNewPage newPage = getAncestorOfType(EXNewPage.class);
			Container parent = newPage.getParent();
			newPage.remove();
			parent.setRendered(false);
		}
		return null;
	}
	
	@Override
	public void ClientAction(ClientProxy container) {
		container.mask(container.getAncestorOfType(EXPortalIcon.class));
		container.makeServerRequest(this);
		
	}
	
	

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		for(Container c : getChildren()){
			c.setStyle("background", "url('designer/newportal/empty.png')");
			c.setAttribute("selected", "false");
			c.setStyle("border", "none");
		}
		container.setStyle("background", "url('designer/newportal/emptypressed.png')");
		container.setAttribute("selected", "true");
		container.setStyle("border", "solid red");
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
	}
	
	
	
}
