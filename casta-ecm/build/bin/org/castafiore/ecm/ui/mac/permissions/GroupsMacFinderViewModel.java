package org.castafiore.ecm.ui.mac.permissions;

import java.util.List;

import org.castafiore.security.Group;
import org.castafiore.security.Role;
import org.castafiore.security.api.SecurityService;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.UIException;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXCheckBox;
import org.castafiore.ui.mac.item.EXMacLabel;
import org.castafiore.ui.mac.item.MacColumnItem;
import org.castafiore.ui.mac.renderer.MacFinderColumnViewModel;

public class GroupsMacFinderViewModel implements MacFinderColumnViewModel{

	
	private List<Group> groups;
	
	
	public GroupsMacFinderViewModel() {
		super();
		try{
			SecurityService service = SpringUtil.getSecurityService();
			groups = service.getGroups();
		}catch(Exception e){
			throw new UIException(e);
		}
	}

	@Override
	public MacColumnItem getValueAt(int index) {
		
		Group group = groups.get(index);
		EXMacLabel label = new EXMacLabel("", group.getName());
		label.setRightItem(new EXCheckBox(""));
		label.setLeftItem(new EXContainer("", "span").addClass("ui-icon-document"));
		return label;
	}

	@Override
	public int size() {
		return groups.size();
	}
}
