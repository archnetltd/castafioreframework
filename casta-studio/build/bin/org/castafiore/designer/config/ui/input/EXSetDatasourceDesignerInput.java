package org.castafiore.designer.config.ui.input;

import java.util.HashMap;
import java.util.Map;

import org.castafiore.designable.DesignableFactory;
import org.castafiore.designable.portal.PortalContainer;
import org.castafiore.designer.Studio;
import org.castafiore.designer.config.DesignerInput;
import org.castafiore.designer.dataenvironment.Datasource;
import org.castafiore.designer.service.DesignableService;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;

public class EXSetDatasourceDesignerInput extends EXSelect implements DesignerInput{

	public EXSetDatasourceDesignerInput() {
		super("df", new DefaultDataModel<Object>());
		
		
		
		
	}
	
	
	

	@Override
	public void applyConfigOnContainer(Container c) {
		Map<String, String> att = new HashMap<String, String>(1);
		att.put("datasource", getValue().toString());
		Studio.applyAttributes(c, att);
		DesignableFactory factory = SpringUtil.getBeanOfType(DesignableService.class).getDesignable(c.getAttribute("des-id"));
		factory.applyAttribute(c, "datasource", getValue().toString());
		
	}

	@Override
	public void applyConfigOnContainer(String stringRepresentation, Container c) {
		Map<String, String> att = new HashMap<String, String>(1);
		att.put("datasource", stringRepresentation);
		Studio.applyAttributes(c, att);
		DesignableFactory factory = SpringUtil.getBeanOfType(DesignableService.class).getDesignable(c.getAttribute("des-id"));
		factory.applyAttribute(c, "datasource", stringRepresentation);
		
	}

	@Override
	public String getStringRepresentation() {
		return getValue().toString();
	}

	@Override
	public void initialise(Container c) {
		DefaultDataModel<Object> model = new DefaultDataModel<Object>();
		model.addItem("");
		for(Datasource d : c.getAncestorOfType(PortalContainer.class).getDatasources()){
			model.addItem(d.getName());
		}
		setModel(model);
		setValue(c.getAttribute("datasource"));
		
	}

}
