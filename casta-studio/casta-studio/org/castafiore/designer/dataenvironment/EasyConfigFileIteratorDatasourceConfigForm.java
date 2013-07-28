package org.castafiore.designer.dataenvironment;

import java.util.Map;

import org.castafiore.KeyValuePair;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.engine.SimpleKeyValuePair;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXTextArea;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.wfs.futil;

public class EasyConfigFileIteratorDatasourceConfigForm extends EXDynaformPanel implements DatasourceConfigForm, Event{

	private Datasource ds;
	
	public EasyConfigFileIteratorDatasourceConfigForm(String name) {
		super(name, "Easy config datasource");
		addField("Entity type :", new EXSelect("entity", new DefaultDataModel(futil.getEntityTypes())));
		addField("Page size :", new EXInput("PageSize"));
	
		addField("User : ", new EXInput("user"));
		
		EXTextArea sql = new EXTextArea("sql");
		addField("Sql :", sql);
		
		addButton(new EXButton("save", "Save"));
		getDescendentByName("save").addEvent(this, Event.CLICK);
		setStyle("width", "430px");
		getField("sql").setStyle("width", "300px").setStyle("height", "90px");
		
	}

	@Override
	public Datasource cancelEdit() {
		return ds;
	}

	@Override
	public boolean isNew() {
		return false;
	}

	@Override
	public Datasource save() {
		String entity = ((KeyValuePair)getField("entity").getValue()).getKey();
		String sql =  getField("sql").getValue().toString();
		String pageSize = getField("PageSize").getValue().toString();
		String dir = "/root/users/" + getField("user").getValue().toString();
		ds.setAttribute("Type", entity);
		ds.setAttribute("SQL", sql);
		ds.setAttribute("PageSize", pageSize);
		ds.setAttribute("Directories", dir);
		return ds;
	}

	@Override
	public void setDatasource(Datasource datasource) {
		this.ds = datasource;
		String entity = ds.getAttribute("Type");
		String sql = ds.getAttribute("SQL");
		String dir = ds.getAttribute("Directories");
		
		
		getField("entity").setValue(new SimpleKeyValuePair(entity,entity));
		getField("sql").setValue(sql);
		getField("user").setValue(dir!= null? dir.replace("/root/users/", ""):"");
		getField("PageSize").setValue(datasource.getAttribute("PageSize"));
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
