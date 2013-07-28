package org.castafiore.designer.portalmenu;

import org.castafiore.designer.layout.EXDroppableGroovyTemplateLayout;
import org.castafiore.designer.model.NavigationDTO;
import org.castafiore.ui.UIException;

public class EXTemplatePortalMenu extends EXDroppableGroovyTemplateLayout implements PortalMenu{

	private NavigationDTO navigation = null;
	
	public EXTemplatePortalMenu() {
		super("menu");		
	}

	public NavigationDTO getNavitation() {
		return navigation;
	}

	public void setNavitation(NavigationDTO navigation) {
		try{
			setAttribute("navigation", NavigationDTO.getJSON(navigation).toString());
			this.navigation = navigation;
			setRendered(false);
		}catch(Exception e){
			throw new UIException(e);
		}
	}
}
