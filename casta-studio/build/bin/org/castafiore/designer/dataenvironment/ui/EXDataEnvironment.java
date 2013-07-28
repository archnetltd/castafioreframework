package org.castafiore.designer.dataenvironment.ui;

import java.util.Iterator;
import java.util.Map;

import org.castafiore.KeyValuePair;
import org.castafiore.SimpleKeyValuePair;
import org.castafiore.designable.portal.PortalContainer;
import org.castafiore.designer.dataenvironment.DatasourceFactory;
import org.castafiore.ecm.ui.fileexplorer.button.EXButtonWithLabel;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.ui.ex.form.table.EXPagineableTable;
import org.castafiore.ui.ex.panel.EXPanel;

public class EXDataEnvironment extends EXPanel implements Event{

	Map<String,DatasourceFactory> factories = SpringUtil.getApplicationContext().getBeansOfType(DatasourceFactory.class);
	
	private PortalContainer pc_;
	
	public EXDataEnvironment(String name, PortalContainer pc) {
		super(name, "Data environment");
		
		this.pc_ = pc;
		DefaultDataModel<Object> model = new DefaultDataModel<Object>();
		
		Iterator<String> iter = factories.keySet().iterator();
		while(iter.hasNext()){
			String key = iter.next();
			String value = factories.get(key).getUniqueId();
			model.addItem(new SimpleKeyValuePair(key, value));
		}
		
		EXSelect select = new EXSelect("factories", model);
		
		Container body = new EXContainer("body", "div");
		body.addChild(select);
		EXButtonWithLabel btn = new EXButtonWithLabel("add", "Add new datasource", "AddPage.gif", "small");
		body.addChild(btn.setStyle("margin", "7px").addEvent(this, CLICK));
		EXPagineableTable ptable = new EXPagineableTable("ptable", new EXDateEnvironmentTable("data", pc));
		
		body.addChild(ptable);
		setBody(body);
		
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		String datasourceName = ((KeyValuePair)getDescendentOfType(EXSelect.class).getValue()).getKey();
		
		DatasourceFactory factory = factories.get(datasourceName);
		
		pc_.addDatasource(factory.getInstance());
		
		getDescendentOfType(EXPagineableTable.class).refresh();
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
