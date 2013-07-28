package org.castafiore.community.ui.groups;

import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.table.EXPagineableTable;
import org.castafiore.ui.ex.form.table.EXTable;

public class EXGroupsTab extends EXContainer implements Event{

	public EXGroupsTab(String name) {
		super(name, "div");
		addChild(new EXButton("newGroup", "New group").addEvent(this, CLICK));
		try{
		EXTable table = new EXTable("groupsList", new GroupsTableModel());
		table.setWidth(Dimension.parse("100%"));
		table.setCellRenderer(new GroupsCellRenderer());
		table.setColumnModel(new GroupsColumnModel());
		EXPagineableTable pTable = new EXPagineableTable("pGroupsList", table);
		pTable.setWidth(Dimension.parse("100%"));
		addChild(pTable.setStyle("clear", "left").setStyle("padding", "10px 0px"));
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		try{
			EXGroupsForm form = new EXGroupsForm();
			form.setGroup(null);
			container.getAncestorOfType(PopupContainer.class).addPopup(form.setStyle("z-index", "4000"));
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}
	
	

}
