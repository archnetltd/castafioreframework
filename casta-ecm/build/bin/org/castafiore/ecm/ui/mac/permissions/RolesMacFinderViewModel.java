package org.castafiore.ecm.ui.mac.permissions;

import java.util.List;

import org.castafiore.security.Role;
import org.castafiore.security.api.SecurityService;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.mac.EXMacFinderColumn;
import org.castafiore.ui.mac.events.OpenColumnEventHandler;
import org.castafiore.ui.mac.item.EXMacLabel;
import org.castafiore.ui.mac.item.MacColumnItem;
import org.castafiore.ui.mac.renderer.MacFinderColumnViewModel;

public class RolesMacFinderViewModel implements MacFinderColumnViewModel{

	private List<Role> roles;
	
	
	public RolesMacFinderViewModel() {
		super();
		try{
			SecurityService service = SpringUtil.getSecurityService();
			roles = service.getRoles();
		}catch(Exception e){
			throw new UIException(e);
		}
	}

	@Override
	public MacColumnItem getValueAt(int index) {
		
		Role role = roles.get(index);
		EXMacLabel label = new EXMacLabel("", role.getName());
		label.setRightItem(new EXContainer("", "span").addClass("ui-icon-triangle-1-e"));
		label.setLeftItem(new EXContainer("", "span").addClass("ui-icon-document"));
		return label;
	}

	@Override
	public int size() {
		return roles.size();
	}



}
