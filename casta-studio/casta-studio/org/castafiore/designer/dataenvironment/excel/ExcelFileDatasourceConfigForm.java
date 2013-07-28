package org.castafiore.designer.dataenvironment.excel;

import java.util.Map;

import org.castafiore.designer.dataenvironment.Datasource;
import org.castafiore.designer.dataenvironment.DatasourceConfigForm;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXCheckBox;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.button.EXButton;

public class ExcelFileDatasourceConfigForm extends EXDynaformPanel implements DatasourceConfigForm,Event{

	public ExcelFileDatasourceConfigForm(String name) {
		super(name, "Configure excel file datasource");
		
		addField("Url : ", new EXInput("Path"));
		addField("Sheet Name : ", new EXInput("Sheet"));
		addField("Is First row header : ", new EXCheckBox("isheader"));
		
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
			d_ = SpringUtil.getBeanOfType(ExcelDatasourceFactory.class).getInstance();
		}
		d_.setAttribute("Path", getField("Path").getValue().toString());
		d_.setAttribute("Sheet", getField("Sheet").getValue().toString());
		d_.setAttribute("FirstRowField", getDescendentOfType(EXCheckBox.class).isChecked() + "");
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
