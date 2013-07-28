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

public class ApplyAttributeDesignerPopupHolder extends EXInput implements DesignerInput, Event, SelectFileHandler{
	private String attrName;
	
	private String searchItem;
	
	public ApplyAttributeDesignerPopupHolder(String attrName, String searchItem, String def) {
		super("ApplyAttributeDesignerPopupHolder");
		this.attrName = attrName;
		addEvent(this, Event.DOUBLE_CLICK);
		setAttribute("title", "Double click to select url of binary file");
		this.searchItem = searchItem;
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
		setValue(c.getAttribute(attrName));		
	}
	
	

	public void ClientAction(ClientProxy container) {
		container.mask(container.getAncestorOfType(EXConfigPanel.class));
		container.makeServerRequest(this);
		
	}
	
	public boolean ServerAction(Container containers,Map<String, String> request) throws UIException {
		EXDesigner designer = getAncestorOfType(EXDesigner.class);
		String path = designer.getCurrentDir() + "/" + designer.getCurrentName() + "/pages";
		
		if(searchItem.equalsIgnoreCase("files")){
			path = "/root/users/" + Util.getRemoteUser();
		}
		
		EXFinder finder = new EXFinder("miniFileExplorer",EXDesigner.TABLE_FILTER,this, path);
		if(StringUtil.isNotEmpty(getValue())){
			String curDir = getValue().toString();
			if(curDir.startsWith(path)){
				finder.open(curDir);
			}
		}
		//finder.o
		
		getAncestorOfType(PopupContainer.class).addPopup(finder);
		return true;
	}

	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}
		
	
	
	
	
		
	public void onSelectFile(File file, EXFinder selector) {
		
		
		setValue(file.getAbsolutePath());
		selector.getAncestorOfType(EXFinder.class).remove();
		getDescendentOfType(EXDesigner.class).setSelectorDir(selector.getCurrentDir());
		
	}
	
}
