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
 package org.castafiore.designer.layout;

import org.castafiore.designable.layout.DesignableLayoutContainer;
import org.castafiore.designer.EXDesigner;
import org.castafiore.designer.EXPortalExecutor;
import org.castafiore.designer.events.OnDeactivateEvent;
import org.castafiore.designer.events.OnDropEvent;
import org.castafiore.designer.events.OnOutEvent;
import org.castafiore.designer.events.OnOverEvent;
import org.castafiore.groovy.EXGroovyContainer;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.js.JMap;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ResourceUtil;

public class EXDroppableGroovyTemplateLayout extends EXGroovyContainer implements DesignableLayoutContainer {

	public EXDroppableGroovyTemplateLayout(String name) {
		super(name, ResourceUtil.getDownloadURL("classpath", "org/castafiore/designer/layout/DefaultTemplate.xhtml"));
		setAttribute("template", ResourceUtil.getDownloadURL("classpath", "org/castafiore/designer/layout/DefaultTemplate.xhtml"));
		addEvent(new OnOverEvent(), Event.DND_OVER);
		addEvent(new OnOutEvent(), Event.DND_OUT);
		addEvent(new OnDeactivateEvent(), Event.DND_DEACTIVATE);
		addEvent(new OnDropEvent(), Event.DND_DROP);
	}

	public String getPossibleLayoutData(Container container) {
		return container.getName();
		
	}

	public String[] getAcceptClasses() {
		return new String[]{"components"};
	}

	public JMap getDroppableOptions() {
		return new JMap().put("greedy", "true");
	}


	public void onAddComponent(Container component) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveUp(Container component) {
		ComponentUtil.moveUp(component, this);
	}

	@Override
	public void moveDown(Container component) {
		ComponentUtil.moveDown(component, this);
	}

	@Override
	public String getTemplate() {
		if(getRoot() instanceof EXPortalExecutor && !getRoot().getConfigContext().containsKey("devmode"))
			return super.getTemplate();
			
		else
			return ResourceUtil.getTemplate(getTemplateLocation(), getRoot());
			
//		try{
//			
//			if(getAncestorOfType(EXDesigner.class) == null){
//				return ResourceUtil.getTemplate(getTemplateLocation(), getRoot());
//			}else{
//				return super.getTemplate();
//			}
//			
//		}catch(Exception e){
//			e.printStackTrace();
//			throw new UIException(e);
//		}
	}
	
	
}
