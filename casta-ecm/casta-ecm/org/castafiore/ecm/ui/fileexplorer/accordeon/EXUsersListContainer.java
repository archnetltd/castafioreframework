package org.castafiore.ecm.ui.fileexplorer.accordeon;

import java.util.List;

import org.castafiore.ecm.ui.fileexplorer.button.EXButtonWithLabel;
import org.castafiore.ecm.ui.fileexplorer.events.OpenFileEvent;
import org.castafiore.security.User;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.js.JMap;

public class EXUsersListContainer extends EXContainer {

	public EXUsersListContainer(String name, List<User> employees, List<User> clients) {
		super(name, "div");

		setWidth(Dimension.parse("100%"));
		setStyle("text-align", "left");
		addList("Employees", employees);
		addList("Clients", clients);
		
		
	}
	
	private void addList(String label, List<User> users){
		if(users.size() > 0){
			addChild(new EXContainer("", "label").setText(label));
			for(User s : users){
				EXButtonWithLabel driveButton = new EXButtonWithLabel("" , s.getUsername(), "Drive.gif", "medium");
				driveButton.setAttribute("path", "/root/users/" + s.getUsername() );
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
