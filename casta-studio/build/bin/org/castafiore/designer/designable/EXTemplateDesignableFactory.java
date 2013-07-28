/*
 * Copyright (C) 2007-2010 Castafiore
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */
 package org.castafiore.designer.designable;

import org.castafiore.beans.info.IBeanInfo;
import org.castafiore.designable.AbstractDesignableFactory;
import org.castafiore.designer.layout.EXDroppableGroovyTemplateLayout;
import org.castafiore.designer.layout.EXDroppableXHTMLLayoutContainer;
import org.castafiore.ui.Container;
import org.castafiore.ui.scripting.EXTemplateComponent;

public class EXTemplateDesignableFactory extends AbstractDesignableFactory implements IBeanInfo{
	
	
	public EXTemplateDesignableFactory() {
		super("EXTemplateDesignableFactory");
		//setText("")
	}
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
		setText(type + " template");
	}

	

	@Override
	public String getCategory() {
		return "Layout";
	}

	@Override
	public Container getInstance() {
		EXTemplateComponent tc = null;
		if(type.equalsIgnoreCase("xhtml")){
			tc =new  EXDroppableXHTMLLayoutContainer("XHTML fragment");
		}else{
			tc = new EXDroppableGroovyTemplateLayout("Groovy container");
		}
		//tc.setForceStyle(true);
		return tc;
	}

	public String getUniqueId() {
		return "core:"+type;
	}
	
	
	@Override
	public void applyAttribute(Container c, String attributeName,
			String attributeValue) {
		if(attributeName.equals("template")){
			((EXTemplateComponent)c).setTemplateLocation(attributeValue);
		}
	}

	public String[] getRequiredAttributes(){
		return new String[]{"template"};
	}

	public String getInfoAttribute(String key) {
		return "Creates a template component of type " + getType();
	}

	public String[] getKeys() {
		return new String[]{"description"};
	}

	public String getSupportedUniqueId() {
		return getUniqueId();
	}
}
