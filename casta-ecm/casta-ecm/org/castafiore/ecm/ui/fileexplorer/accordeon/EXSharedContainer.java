package org.castafiore.ecm.ui.fileexplorer.accordeon;

import java.util.List;

import org.castafiore.ecm.ui.fileexplorer.button.EXButtonWithLabel;
import org.castafiore.ecm.ui.fileexplorer.events.OpenFileEvent;
import org.castafiore.security.Role;
import org.castafiore.security.User;
import org.castafiore.security.api.SecurityService;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.js.JMap;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.RepositoryService;

public class EXSharedContainer extends EXContainer{

	public EXSharedContainer(String name)throws Exception {
		super(name, "div");
		String user = Util.getRemoteUser();
		String organization = Util.getLoggedOrganization();
		
		SecurityService secu = SpringUtil.getSecurityService();
		RepositoryService repo = SpringUtil.getRepositoryService();
		List<Role> roles = secu.getRoles();
		for(Role r : roles){
			if(secu.isUserAllowed(r.getName() + ":" + organization, user)){
				String path = "/root/users/" + organization + "/Documents/Shared/" + r.getName();
				if(repo.itemExists(path)){
					EXButtonWithLabel driveButton = new EXButtonWithLabel("" , r.getName(), "Drive.gif", "medium");
					driveButton.setAttribute("path", path );
					driveButton.addEvent(new OpenFileEvent(){
						@Override
						public void ClientAction(ClientProxy application) {
							application.makeServerRequest(new JMap().put("path", application.getAttribute("path")), this);
						}
					}, Event.CLICK);
					addChild(driveButton);
				}
			}
		}
	}

}
