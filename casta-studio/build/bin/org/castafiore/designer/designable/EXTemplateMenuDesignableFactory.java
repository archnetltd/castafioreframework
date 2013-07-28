package org.castafiore.designer.designable;

import org.castafiore.designer.portalmenu.EXTemplatePortalMenu;
import org.castafiore.ui.Container;
import org.castafiore.ui.scripting.EXTemplateComponent;

public class EXTemplateMenuDesignableFactory extends EXPortalMenuDesignableFactory{
	

	public EXTemplateMenuDesignableFactory() {
		super();
		
		setText("Template menu");
	}
	public String getUniqueId() {
		
		return "portal:template-menu";
	}	

	@Override
	public String getCategory() {
		return "Portal";
	}

	@Override
	public Container getInstance() {
		EXTemplateComponent tc = null;
		
		tc = new EXTemplatePortalMenu();
		
		return tc;
	}

	
	
	@Override
	public void applyAttribute(Container c, String attributeName,
			String attributeValue) {
		if(attributeName.equals("template")){
			((EXTemplateComponent)c).setTemplateLocation(attributeValue);
		}else{
			super.applyAttribute(c, attributeName, attributeValue);
		}
	}

	public String[] getRequiredAttributes(){
		return new String[]{"template"};
	}

	public String getInfoAttribute(String key) {
		return "Creates a template component for a menu";
	}

	public String[] getKeys() {
		return new String[]{"description"};
	}

	public String getSupportedUniqueId() {
		return getUniqueId();
	}

}
