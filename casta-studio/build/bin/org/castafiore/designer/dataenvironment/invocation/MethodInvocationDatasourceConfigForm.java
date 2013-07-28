package org.castafiore.designer.dataenvironment.invocation;

import java.util.Map;

import org.castafiore.designer.dataenvironment.Datasource;
import org.castafiore.designer.dataenvironment.DatasourceConfigForm;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.button.EXButton;

public class MethodInvocationDatasourceConfigForm extends EXDynaformPanel implements DatasourceConfigForm ,Event{
	
	public MethodInvocationDatasourceConfigForm(String name) {
		super(name, "Method Invocation datasource config form");
		addField("Service: ",new EXInput("Service"));
		addField("Method", new EXInput("Method"));
		addField("Return Type :", new EXInput("ReturnType"));
		addButton(new EXButton("save", "Save"));
		getDescendentByName("save").addEvent(this, Event.CLICK);
	}

	private Datasource d_ = null;

	@Override
	public Datasource cancelEdit() {
		return d_;
	}

	@Override
	public boolean isNew() {
		return d_==null;
	}

	@Override
	public Datasource save() {
		if(d_ == null){
			d_ = SpringUtil.getBeanOfType(MethodInvocationDatasourceFactory.class).getInstance();
		}
		d_.setAttribute("Service", getField("Service").getValue().toString());
		d_.setAttribute("Method", getField("Method").getValue().toString());
		d_.setAttribute("ReturnType", getField("ReturnType").getValue().toString());
		return d_.reset();
	}

	@Override
	public void setDatasource(Datasource datasource) {
		this.d_ = datasource;
		
	}

	
	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		save();
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}
}
