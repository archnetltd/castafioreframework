package org.castafiore.webos.intro;

import java.util.Map;

import org.castafiore.designer.EXDesigner;
import org.castafiore.designer.config.EXConfigVerticalBar;
import org.castafiore.designer.toolbar.EXDesignerToolbar;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Application;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.form.EXCheckBox;
import org.castafiore.ui.ex.form.button.EXIconButton;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.webos.EXWebOSMenu;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.File;
import org.castafiore.wfs.types.FileImpl;

public class EXDesignerIntro extends EXXHTMLFragment{

	public EXDesignerIntro() {
		super("EXDesignerIntro", "ddesignerintro.html");
		EXCheckBox cb = new EXCheckBox("showAgain");
		addChild(cb);
		
		EXIconButton button =new EXIconButton("skip", "Close Intro X");
		addChild(button);
		button.setStyle("float", "right");
		button.addEvent(CLOSE_INTRO, Event.CLICK);
	}
	
	
	public void close(){
		Application root = getRoot();
		
		if(getDescendentOfType(EXCheckBox.class).isChecked()){
			File file = SpringUtil.getRepositoryService().getDirectory("/root/users/" + Util.getRemoteUser() + "/applications/designer", Util.getRemoteUser());
			File f = file.createFile("nonintro",FileImpl.class);
			
			f.makeOwner(Util.getRemoteUser());
			f.save();
			
		}
		
		
		root.getChildren().remove(this);
		root.setRendered(false);
		
		
		
		EXDesigner des = new EXDesigner();
		String currentDir = "/root/users/" + Util.getRemoteUser() + "/portal-data";
		String currentName = "portal-template.ptl";
		des.open(currentDir, currentName);
		des.getDescendentOfType(EXDesignerToolbar.class).setDisplay(false);
		
		root.addChild(des);
		des.setStyle("position", "absolute");
		des.setStyle("top", "0");
		des.setStyle("left", "0");
		
		des.getDescendentOfType(EXConfigVerticalBar.class).getNode(des.getRootLayout()).refresh();
		root.getDescendentOfType(EXWebOSMenu.class).setDisplay(false);
		
		
	}
	
	
	
	
	public final static Event CLOSE_INTRO = new Event(){

		@Override
		public void ClientAction(ClientProxy container) {
			container.makeServerRequest(this);
			
		}

		@Override
		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			container.getAncestorOfType(EXDesignerIntro.class).close();
			return true;
		}

		@Override
		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			container.mergeCommand(new ClientProxy(".myHelpMask").remove());
			//container.mergeCommand(new ClientProxy("#" + request.get("idToRemove")).remove());
			
		}
		
	};

}
