package org.castafiore.community.ui.users;

import java.util.List;
import java.util.Map;

import org.castafiore.security.Group;
import org.castafiore.security.Role;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.utils.StringUtil;

public class EXPermissionsForm extends EXDynaformPanel implements Event{

	public EXPermissionsForm(String name, String username) {
		super(name, "Add Permission");
		try{
			setAttribute("username", username);
			List roles = SpringUtil.getSecurityService().getRoles();
			addField("Role :", new EXSelect("role", new DefaultDataModel<Object>(roles)));
			
			List groups = SpringUtil.getSecurityService().getGroups();
			
			addField("Groups :", new EXSelect("group", new DefaultDataModel<Object>(groups)));
			
			
			addButton(new EXButton("save", "Save"));
			addButton(new EXButton("cancel", "Cancel"));
			getDescendentByName("cancel").addEvent(CLOSE_EVENT, Event.CLICK);
			getDescendentByName("save").addEvent(this, CLICK);
		}catch(Exception e){
			throw new UIException(e);
			//.e.printStackTrace();
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
			Group grp = (Group)((EXSelect)getDescendentByName("group")).getValue();
			Role role = (Role)((EXSelect)getDescendentByName("role")).getValue();
			if(StringUtil.isNotEmpty(getAttribute("username"))){
				String username = getAttribute("username");
				SpringUtil.getSecurityService().assignSecurity(username, role.getName(), grp.getName());
				container.getAncestorOfType(EXPermissionTab.class).getDescendentOfType(EXPermissionsTable.class).setUser(username);
			}else{
				throw new UIException("Please save the user befor assigning permission");
			}
		}catch(Exception  e){
			throw new UIException(e);
		}
			
		
		return true;
		
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
