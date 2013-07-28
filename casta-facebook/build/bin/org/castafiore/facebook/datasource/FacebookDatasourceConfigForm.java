package org.castafiore.facebook.datasource;

import java.util.Map;

import org.castafiore.designer.dataenvironment.Datasource;
import org.castafiore.designer.dataenvironment.DatasourceConfigForm;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXTextArea;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.utils.StringUtil;

public class FacebookDatasourceConfigForm extends EXDynaformPanel implements DatasourceConfigForm , Event{

	public FacebookDatasourceConfigForm(String name) {
		super(name, "Facebook datasource configuration");
		addField("Access Token :", new EXInput("AccessToken"));
		addField("Return type :", new EXSelect("Type", getFacebookTypes()));
		addField("Facebook Query :", new EXTextArea("Query"));
		setStyle("width", "500px");
		getDescendentByName("AccessToken").setStyle("width", "313px");
		getDescendentByName("Type").setStyle("width","315px");
		getDescendentByName("Query").setStyle("width", "313px");
		addButton(new EXButton("save", "Save"));
		getDescendentByName("save").addEvent(this, Event.CLICK);
		
		
	}
	
	private DefaultDataModel<Object> getFacebookTypes(){
		String s = "Account Album Application AppRequest CategorizedFacebookType Checkin Comment Conversation Event FacebookType FriendList Group Insight Link Location Message Note Page PageConnection Photo Place Post Question QuestionOption StatusMessage Url User Venue Video";
		
		String[] as = StringUtil.split(s, " ");
		DefaultDataModel<Object> model = new DefaultDataModel<Object>();
		for(String ss : as){
			model.addItem(ss);
		}
		return model;
	}

	private Datasource datasource;
	
	@Override
	public Datasource cancelEdit() {
		this.remove();
		return datasource;
	}

	@Override
	public boolean isNew() {
		return datasource == null;
	}

	@Override
	public Datasource save() {
		if(datasource == null)
			datasource = new FacebookDataSource("cloud:facebook", "Facebook");
		for (StatefullComponent stf : getFields()){
			String val = stf.getValue().toString();
			if(stf instanceof EXSelect){
				val = "com.restfb.types." + val;
			}
			datasource.setAttribute(stf.getName(), val);
		}
		datasource.reset();
		
		return datasource;
	}

	@Override
	public void setDatasource(Datasource datasource) {
		this.datasource= datasource;
		if(datasource != null){
			getField("AccessToken").setValue(datasource.getAttribute("AccessToken"));
			getField("Type").setValue(datasource.getAttribute("Type"));
			getField("Query").setValue(datasource.getAttribute("Query"));
		}
		
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
