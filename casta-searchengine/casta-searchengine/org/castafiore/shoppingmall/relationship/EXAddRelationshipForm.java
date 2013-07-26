package org.castafiore.shoppingmall.relationship;

import java.util.Map;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.wfs.Util;

public class EXAddRelationshipForm extends EXDynaformPanel implements Event{

	public EXAddRelationshipForm(String name) {
		super(name, "Create relationship");
		addField("Relationship type :", new EXInput("relationship"));
		addField("Organization", new EXInput("organization"));
		
		addButton(new EXButton("save", "Save"));
		addButton(new EXButton("cancel", "Cancel"));
		
		getDescendentByName("save").addEvent(this, CLICK);
		getDescendentByName("cancel").addEvent(CLOSE_EVENT, CLICK);
		
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		String org = getField("organization").getValue().toString();
		String relationship = getField("relationship").getValue().toString();
		
		SpringUtil.getRelationshipManager().saveRelationship(Util.getLoggedOrganization(), org, relationship);
		
		DefaultDataModel model = new DefaultDataModel<String>(SpringUtil.getRelationshipManager().getDistinctRelations(Util.getLoggedOrganization()));
		((EXSelect)container.getAncestorOfType(EXRelationshipManager.class).getDescendentByName("relationsSelect")).setModel(model);
		
		this.remove();
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
