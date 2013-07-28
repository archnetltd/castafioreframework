package org.castafiore.community.ui;

import org.castafiore.community.ui.users.EXUserForm;
import org.castafiore.designable.AbstractDesignableFactory;
import org.castafiore.designable.DesignableFactory;
import org.castafiore.security.User;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.wfs.Util;

public class EXUserDesignableFactory extends AbstractDesignableFactory {

	public EXUserDesignableFactory() {
		super("EXUserDesignableFactory");
		setText("User");
	}

	@Override
	public String getCategory() {
		return "Community";
	}

	@Override
	public Container getInstance() {
		User user = SpringUtil.getSecurityService().loadUserByUsername(Util.getRemoteUser());
		EXUserForm form = new EXUserForm(user);
		return form;
	}

	public String getUniqueId() {
		return "community:user";
	}

	@Override
	public void applyAttribute(Container c, String attributeName,
			String attributeValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String[] getRequiredAttributes() {
		// TODO Auto-generated method stub
		return null;
	}
}
