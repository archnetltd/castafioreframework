package org.castafiore.community.ui;

import java.util.Map;

import org.castafiore.community.ui.users.RegisterUserForm;
import org.castafiore.community.ui.users.UsersCellRenderer;
import org.castafiore.community.ui.users.UsersColumnModel;
import org.castafiore.community.ui.users.UsersTableModel;
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

public class EXUsersTab extends EXContainer implements Event{

	public EXUsersTab(String name) {
		super(name, "div");
		addChild(new EXButton("addUser", "New user").addEvent(this, Event.CLICK));
		addChild(new EXButton("refresh", "Refresh").addEvent(this, Event.CLICK));
		try{
			EXTable table = new EXTable("usersList", new UsersTableModel());
			table.setWidth(Dimension.parse("100%"));
			table.setCellRenderer(new UsersCellRenderer());
			table.setColumnModel(new UsersColumnModel());
			EXPagineableTable pTable = new EXPagineableTable("pUsersList", table);
			pTable.setWidth(Dimension.parse("100%"));
			addChild(pTable.setStyle("padding", "10px 0").setStyle("clear", "left"));
		}catch(Exception e){
			throw new UIException(e);
		}
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		if(container.getName().equalsIgnoreCase("refresh")){
			EXTable table = getDescendentOfType(EXTable.class);
			try{
			table.setModel(new UsersTableModel());
			table.getAncestorOfType(EXPagineableTable.class).refresh();
			}catch(Exception e){
				e.printStackTrace();
				throw new UIException(e);
			}
		}else{
		
			container.getAncestorOfType(PopupContainer.class).addPopup(new RegisterUserForm().setStyle("z-index", "3000"));
		}
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
