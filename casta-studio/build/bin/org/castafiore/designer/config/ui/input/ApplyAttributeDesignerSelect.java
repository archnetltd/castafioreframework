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
import org.castafiore.ui.ex.form.list.DataModel;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.File;

public class ApplyAttributeDesignerSelect extends EXSelect  implements DesignerInput {

	private String attrName;
	public ApplyAttributeDesignerSelect(String attrName, String[] sss, String def) {
		super("ApplyAttributeDesignerInput", new DefaultDataModel<Object>());
		this.attrName = attrName;
		addEvent(SHOW_EXPLORER_EVENT, Event.DOUBLE_CLICK);
		DefaultDataModel<Object> m = new DefaultDataModel<Object>();
		
		for(String s : sss){
			m.addItem(s);
		}
		setModel(m);
		
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

}
