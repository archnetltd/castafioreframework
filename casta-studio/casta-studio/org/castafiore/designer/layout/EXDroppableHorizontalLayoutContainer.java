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
import org.castafiore.designer.events.OnDeactivateEvent;
import org.castafiore.designer.events.OnDropEvent;
import org.castafiore.designer.events.OnOutEvent;
import org.castafiore.designer.events.OnOverEvent;
import org.castafiore.ui.Container;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.layout.EXHorizontalLayoutContainer;
import org.castafiore.ui.js.JMap;
import org.castafiore.utils.ComponentUtil;

public class EXDroppableHorizontalLayoutContainer extends EXHorizontalLayoutContainer
		implements DesignableLayoutContainer {

	public EXDroppableHorizontalLayoutContainer(){

		addEvent(new OnOverEvent(), Event.DND_OVER);
		addEvent(new OnOutEvent(), Event.DND_OUT);
		addEvent(new OnDeactivateEvent(), Event.DND_DEACTIVATE);
		addEvent(new OnDropEvent(), Event.DND_DROP);
	}
	
	
	public String[] getAcceptClasses() {
		return new String[] { "components" };
	}

	public JMap getDroppableOptions() {
		return new JMap().put("greedy", "true");
	}

	public String getPossibleLayoutData(Container container) {
		return getChildren().size() + "";
	}


	
	public void onAddComponent(Container component) {
		component.setResizable(true);
		
	}
	
	@Override
	public void moveUp(Container component) {
		ComponentUtil.moveUp(component.getParent(), this);
	}

	@Override
	public void moveDown(Container component) {
		ComponentUtil.moveDown(component.getParent(), this);
	}
}
