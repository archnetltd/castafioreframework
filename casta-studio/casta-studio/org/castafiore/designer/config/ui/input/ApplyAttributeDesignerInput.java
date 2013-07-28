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
 package org.castafiore.designer.config.ui.input;

import java.util.Map;

import org.castafiore.designable.DesignableFactory;
import org.castafiore.designer.EXDesigner;
import org.castafiore.designer.config.DesignerInput;
import org.castafiore.designer.config.ui.EXConfigPanel;
import org.castafiore.designer.service.DesignableService;
import org.castafiore.ecm.ui.finder.EXFinder;
import org.castafiore.ecm.ui.finder.EXFinder.SelectFileHandler;
import org.castafiore.ui.Application;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.File;

public class ApplyAttributeDesignerInput extends EXInput implements DesignerInput {
	
	
	private String attrName;
	public ApplyAttributeDesignerInput(String attrName, String def) {
		super("ApplyAttributeDesignerInput");
		this.attrName = attrName;
		addEvent(SHOW_EXPLORER_EVENT, Event.DOUBLE_CLICK);
		setAttribute("title", "Double click to select url of binary file");
		if(StringUtil.isNotEmpty(def)){
			setValue(def);
		}
	}

	public void applyConfigOnContainer(Container c) {
		applyConfigOnContainer(getStringRepresentation(), c);
		
	}

	public void applyConfigOnContainer(String stringRepresentation, Container c) {
		DesignableFactory factory = BaseSpringUtil.getBeanOfType(DesignableService.class).getDesignable(c.getAttribute("des-id"));
		c.setAttribute(attrName, stringRepresentation);
		factory.applyAttribute(c, attrName, stringRepresentation);		
	}

	public String getStringRepresentation() {
		return getValue().toString();
	}

	public void initialise(Container c) {
		if(StringUtil.isNotEmpty(c.getAttribute(attrName)))
		setValue(c.getAttribute(attrName));		
	}
	
	private final static Event SHOW_EXPLORER_EVENT = new Event(){

		public void ClientAction(ClientProxy container) {
			container.mask(container.getAncestorOfType(EXConfigPanel.class));
			container.makeServerRequest(this);
			
		}
		
		public boolean ServerAction(Container container,Map<String, String> request) throws UIException {

			String sDir = container.getAncestorOfType(EXDesigner.class).getSelectorDir();
			EXFinder finder = new EXFinder("miniFileExplorer",EXDesigner.TABLE_FILTER,ON_SELECT_FILE_HANDLER, "/root/users/" + Util.getRemoteUser());
			finder.setAttribute("componentid", container.getId());
			container.getAncestorOfType(PopupContainer.class).addPopup(finder);
			return true;
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	
	public final static SelectFileHandler ON_SELECT_FILE_HANDLER = new SelectFileHandler() {
		
		public void onSelectFile(File file, EXFinder selector) {
			Application root = selector.getRoot();
			EXInput input = (EXInput)root.getDescendentById(selector.getAttribute("componentid"));
			input.setValue(ResourceUtil.getDownloadURL("ecm", file.getAbsolutePath()));
			selector.getAncestorOfType(EXFinder.class).remove();
			root.getDescendentOfType(EXDesigner.class).setSelectorDir(selector.getCurrentDir());
			
		}
	};
	
	
	//public final static OnSelectFileHander 

}
