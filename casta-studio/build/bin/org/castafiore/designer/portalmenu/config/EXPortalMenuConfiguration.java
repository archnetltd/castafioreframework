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
 package org.castafiore.designer.portalmenu.config;

import java.util.List;
import java.util.Map;

import org.castafiore.SimpleKeyValuePair;
import org.castafiore.designable.portal.PortalContainer;
import org.castafiore.designer.config.ConfigForm;
import org.castafiore.designer.model.NavigationDTO;
import org.castafiore.designer.portalmenu.PortalMenu;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.ui.ex.msgbox.EXPrompt;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.EventUtil;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.iterator.FileIterator;
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;
import org.json.JSONObject;

public class EXPortalMenuConfiguration extends EXXHTMLFragment implements ConfigForm, Event, PopupContainer {
	
	private PortalMenu container;
	
	private NavigationDTO root;

	public EXPortalMenuConfiguration() {
		super("EXPortalMenuConfiguration", ResourceUtil.getDownloadURL("classpath", "org/castafiore/designer/portalmenu/config/EXPortalMenuConfiguration.xhtml"));
		Container c = ComponentUtil.getContainer("popupContainer", "div", null, null);
		c.setStyle("position", "absolute");
		c.setStyle("top", "10%");
		c.setStyle("left", "10%");
		addChild(c);
		EXInput name = new EXInput("name");
		addChild(name);
		name.setEnabled(false);
		
		EXInput label = new EXInput("label");
		addChild(label);
		
		DefaultDataModel mMenuItems = new DefaultDataModel();
		EXSelect menuItems = new EXSelect("menuItems", mMenuItems);
		addChild(menuItems);
		menuItems.setAttribute("ancestor", getClass().getName());
		menuItems.setAttribute("method", "selectMenuItem");
		menuItems.addEvent(EventUtil.GENERIC_FORM_METHOD_EVENT, Event.CHANGE);
		
		DefaultDataModel mPages = new DefaultDataModel();
		EXSelect pages = new EXSelect("pages", mPages);
		addChild(pages);
		
		EXButton save = new EXButton("save", "Save");
		addChild(save);
		save.setAttribute("ancestor", getClass().getName());
		save.setAttribute("method", "save");
		save.addEvent(EventUtil.GENERIC_FORM_METHOD_EVENT, Event.CLICK);
		
		EXButton add = new EXButton("add", "Add");
		addChild(add);
		add.addEvent(this, Event.CLICK);
		
		EXButton delete = new EXButton("delete", "Delete");
		addChild(delete);
		delete.setAttribute("ancestor", getClass().getName());
		delete.setAttribute("method", "delete");
		delete.addEvent(EventUtil.GENERIC_FORM_METHOD_EVENT, Event.CLICK);
	}
	
	public void selectMenuItem(){
		EXSelect select = (EXSelect)getChild("menuItems");
		SimpleKeyValuePair kv = (SimpleKeyValuePair)select.getValue();
		if(kv != null){
			String item = kv.getKey();
			NavigationDTO dto = getItem(item);
			((EXInput)getChild("name")).setValue(dto.getName());
			((EXInput)getChild("label")).setValue(dto.getLabel());
			((EXSelect)getChild("pages")).setValue(dto.getPageReference());
		}
	}
	
	
	public void save(){
		EXSelect select = (EXSelect)getChild("menuItems");
		SimpleKeyValuePair kv = (SimpleKeyValuePair)select.getValue();
		String item = kv.getKey();
		NavigationDTO dto = getItem(item);
		String label = ((EXInput)getChild("label")).getValue().toString();
		String pageReference = ((EXSelect)getChild("pages")).getValue().toString();
		dto.setLabel(label);
		dto.setPageReference(pageReference);
		applyConfigs();
	}
	
	public void delete(){
		EXSelect select = (EXSelect)getChild("menuItems");
		SimpleKeyValuePair kv = (SimpleKeyValuePair)select.getValue();
		String item = kv.getKey();
		NavigationDTO dto = getItem(item);
		root.getChildren().remove(dto);
		DefaultDataModel m = (DefaultDataModel)select.getModel();
		m.getData().remove(kv);
		select.setModel(m);
		applyConfigs();
	}
	
	private NavigationDTO getItem(String name){
		List<NavigationDTO> navs = root.getChildren();
		
		for(NavigationDTO nav : navs){
			if(nav.getName().equals(name)){
				return nav;
			}
		}
		return null;
	}

	public void applyConfigs() {
		
		container.setNavitation(root);
	}

	public Container getContainer() {
		return container;
	}

	public void setContainer(Container container) {
		this.container = (PortalMenu)container;
		
		
		String navigation = container.getAttribute("navigation");
		root = null;
		try{
			
			JSONObject obj = new JSONObject(navigation);
			root = NavigationDTO.getObject(obj);
		}catch(Exception e){
			
			root = new NavigationDTO();
			root.setLabel("root");
			root.setName("root");
			root.setPageReference("root");
			root.setUri("root");
			
			//throw new UIException(e);
		}
		
		List<NavigationDTO> navs = root.getChildren();
		EXSelect select = (EXSelect)getChild("menuItems");
		DefaultDataModel model = (DefaultDataModel)select.getModel();
		model.getData().clear();
		Object val = null;
		int count = 0;
		for(NavigationDTO nav : navs){
			SimpleKeyValuePair kv = new SimpleKeyValuePair(nav.getName(), nav.getLabel());
			model.addItem(kv);
			if(count == 0){
				val = kv;
			}
			count++;
		}
		select.setModel(model);
		select.setValue(val);
		
		RepositoryService service = BaseSpringUtil.getBeanOfType(RepositoryService.class);
		String defnPath = container.getAncestorOfType(PortalContainer.class).getDefinitionPath();
		BinaryFile portal = (BinaryFile)service.getFile(defnPath, Util.getRemoteUser());
		FileIterator<File> pages = ((Directory)portal.getFile("pages")).getFiles();
		EXSelect selPages = (EXSelect)getChild("pages");
		DefaultDataModel modPages = (DefaultDataModel)selPages.getModel();
		modPages.getData().clear();
		
		while(pages.hasNext()){
			modPages.addItem(pages.next().getName());
		}
		selPages.setModel(modPages);
		selectMenuItem();
	}


	public void ClientAction(ClientProxy container) {
		container.mask();
		container.makeServerRequest(this);
		
	}


	public boolean ServerAction(final Container container, Map<String, String> request)
			throws UIException {
		EXPrompt prompt = new EXPrompt("newFolder", "Add Menu Item","Name of Menu Item:"){

			@Override
			public boolean onOk(Map<String, String> request) {
				String name = getInputValue();
				
				if(StringUtil.isNotEmpty(name)){
				
					EXSelect menuitems = (EXSelect)container.getAncestorOfType(EXPortalMenuConfiguration.class).getChild("menuItems");
					DefaultDataModel model = (DefaultDataModel)menuitems.getModel();
					SimpleKeyValuePair kv =new SimpleKeyValuePair(name,name);
					model.addItem(kv);
					menuitems.setModel(model);
					menuitems.setValue(kv);
					
					NavigationDTO dto = new NavigationDTO();
					dto.setName(name);
					dto.setLabel(name);
					container.getAncestorOfType(EXPortalMenuConfiguration.class).root.getChildren().add(dto);
					selectMenuItem();
					return true;
				}
				else
				{
					//addMessage("A folder name is mandatory");
					getInput().addClass("ui-state-error");
					return false;
					
				}
				
			}
			
		};
		
		addPopup(prompt);
		prompt.setStyle("width", "335px").setStyle("top", "-26px").setStyle("left", "-83px");
		return true;
	}

	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}


	public void addPopup(Container popup) {
		getChild("popupContainer").addChild(popup);
		
	}

}
