package org.castafiore.shoppingmall.relationship;

import java.util.Map;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.ui.ex.form.table.EXPagineableTable;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.layout.EXBorderLayoutContainer;
import org.castafiore.ui.ex.toolbar.EXToolBar;
import org.castafiore.wfs.Util;

public class EXRelationshipManager extends EXBorderLayoutContainer implements Event{

	public EXRelationshipManager(String name) {
		super(name);
		for(int i =0; i <5;i++){
			getContainer(i).setStyle("padding", "0").setStyle("margin", "0").setStyle("vertical-align", "top");
		}
		EXToolBar tb = new EXToolBar("tb");
		
		EXButton btn = new EXButton("create", "Create Relationship");
		tb.addItem(btn);
		btn.addEvent(this, CLICK);
		addChild(tb,TOP);
		EXTable table = new EXRelationshipTable(name, Util.getLoggedOrganization());
		table.setStyle("width", "825px");
		EXPagineableTable ptable = new EXPagineableTable("rela", table);
		addChild(ptable,CENTER);
		
		DefaultDataModel model = new DefaultDataModel<String>(SpringUtil.getRelationshipManager().getDistinctRelations(Util.getLoggedOrganization()));
		EXSelect select = new EXSelect("relationsSelect", model);
		select.setAttribute("size", "25").setStyle("width", "150px").setStyle("margin", "0");
		
		addChild(select.addEvent(this, CHANGE),LEFT);
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		if(container.getName().equalsIgnoreCase("create")){
			container.getAncestorOfType(PopupContainer.class).addPopup(new EXAddRelationshipForm("add").setStyle("width", "400px"));
		}else{
			String relation = getDescendentOfType(EXSelect.class).getValue().toString();
			getDescendentOfType(EXRelationshipTable.class).filter(relation);
		}
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
