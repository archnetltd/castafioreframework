package org.castafiore.community.ui.users;

import java.util.Map;



import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.button.EXButton;

public class EXPermissionTab extends EXContainer implements Event{

	public EXPermissionTab(String name, String username) {
		super(name, "div");
		//addChild(new EXButton("addPermission", "Assign permission").addEvent(this, CLICK));
		addChild(new EXPermissionsTable("pTable", username).setStyle("clear", "left").setStyle("padding", "10px 0px"));
		setAttribute("username", username);
		
		EXPermissionsForm form = new EXPermissionsForm("", getAttribute("username"));
		form.setDraggable(false);
		form.setShowCloseButton(false);
		addChild(form);
	}
	
	public void setUser(String username){
		getDescendentOfType(EXPermissionsTable.class).setUser(username);
		setAttribute("username", username);
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		container.getAncestorOfType(PopupContainer.class).addPopup(new EXPermissionsForm("", getAttribute("username")).setStyle("z-index", "5001"));
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
