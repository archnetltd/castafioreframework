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
 package org.castafiore.designer.wizard;

import java.util.List;
import java.util.Map;

import org.castafiore.designable.layout.DesignableLayoutContainer;
import org.castafiore.designable.portal.PortalContainer;
import org.castafiore.designer.EXDesigner;
import org.castafiore.designer.model.NavigationDTO;

import org.castafiore.designer.portalmenu.EXSimplePortalMenu;
import org.castafiore.designer.portalmenu.PortalMenu;
import org.castafiore.designer.wizard.layout.EXLayoutGenerator;
import org.castafiore.designer.wizard.menu.EXPortalMenuSelector;
import org.castafiore.designer.wizard.navigation.EXNavigationWizard;
import org.castafiore.ecm.ui.selector.EXFileSelector;
import org.castafiore.ui.Application;
import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.button.EXIconButton;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.utils.ComponentUtil;

public class EXWizard extends EXContainer{
	
	private PortalContainer geneLayout = null;
	

	public EXWizard() {
		super("EXWizard", "div");
		
		Container windowContainer = ComponentUtil.getContainer("windowContainer", "div",null,null);
		addChild(windowContainer);
		
		
		EXIconButton next = new EXIconButton("next", "Next");
		next.addClass("wizard-button");
		addChild(next);
		next.addEvent(NextWindowEvent, Event.CLICK);
		
		EXIconButton previous = new EXIconButton("previous", "Previous");
		previous.addClass("wizard-button");
		addChild(previous);
		previous.addEvent(PreviousWinwod, Event.CLICK);
		
		
		
		
	}
	
	
	public void setWindow(Container window){
		getChild("windowContainer").getChildren().clear();
		getChild("windowContainer").setRendered(false);
		getChild("windowContainer").addChild(window);
	}
	
	public Container getWindow(){
		List<Container> children = getChild("windowContainer").getChildren();
		if(children.size() > 0){
			return children.get(0);
		}return null;
	}
	
	public void nextWindow(){
		
		
		Container currentWindow = getWindow();
		if(currentWindow instanceof EXLayoutGenerator){
			this.geneLayout = ((EXLayoutGenerator)(currentWindow)).getGeneratedLayout();
			
			getAncestorOfType(EXPanel.class).setTitle("Select the desired navigation menu");
			setWindow(new EXPortalMenuSelector("portalMenuSelector"));
			getDescendentByName("next").setDisplay(false);
		}
		if(currentWindow instanceof EXPortalMenuSelector){
			getAncestorOfType(EXPanel.class).setTitle("Select modules to include");
			setWindow(new EXNavigationWizard("navigationwizard"));
			getDescendentByName("next").setDisplay(true);
			((EXIconButton)getDescendentByName("next")).setLabel("Finish");
		}
		if(currentWindow instanceof EXNavigationWizard){
			Application app = getRoot();
			
			List<String> modules = ((EXNavigationWizard)currentWindow).getSelectedModules();
			NavigationDTO root = new NavigationDTO();
			root.setLabel("root");
			root.setName("root");
			root.setPageReference("root");
			root.setUri("root");
			
			for(String module : modules){
				NavigationDTO nav = new NavigationDTO();
				nav.setLabel(module);
				nav.setName(module);
				nav.setPageReference(module);
				nav.setUri(module);
				root.getChildren().add(nav);
			}
			PortalMenu pMenu = geneLayout.getDescendentOfType(PortalMenu.class);
			if(pMenu != null)
				pMenu.setNavitation(root);
			app.getDescendentOfType(EXDesigner.class).setRootLayout(geneLayout, null, null);
			getAncestorOfType(EXPanel.class).remove();
			
			EXPanel panel = new EXPanel("miniFileExplorer", "Select a folder to save");
			panel.setWidth(Dimension.parse("670px"));
			panel.setBody(new EXFileSelector("myFileSelector", EXDesigner.TREE_FILTER, EXDesigner.TREE_FILTER, null));
			//Application app = getRoot();
			getAncestorOfType(EXDesigner.class).addPopup(panel);
			panel.getDescendentOfType(EXFileSelector.class).addOnSelectFileHandler(EXDesigner.SELECT_FOLDER_TO_SAVE_HANDLER);
			panel.getDescendentOfType(EXFileSelector.class).getOkButton().addEvent(EXDesigner.SELECT_FOLDER_EVENT, Event.CLICK);
		}
		
		
	}
	
	public void previousWindow(){
		getAncestorOfType(EXPanel.class).setTitle("Select the page layout");
		setWindow(new EXLayoutGenerator());
	}
	
	public DesignableLayoutContainer getGenLayout(){
		return geneLayout;
	}
	
	public void setLayoutMenu(EXSimplePortalMenu menu){
		Container nav = geneLayout.getDescendentByName("navigation");
		if(nav != null){
			nav.getChildren().clear();
			nav.setRendered(false);
			nav.addChild(menu);
		}
	}
	
	private final static Event NextWindowEvent = new Event(){

		public void ClientAction(ClientProxy container) {
			container.mask();
			container.makeServerRequest(this);
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			
			container.getAncestorOfType(EXWizard.class).nextWindow();
			return true;
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	
	private final static Event PreviousWinwod = new Event(){

		public void ClientAction(ClientProxy container) {
			container.mask();
			container.makeServerRequest(this);
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			container.getAncestorOfType(EXWizard.class).previousWindow();
			return true;
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	

}
