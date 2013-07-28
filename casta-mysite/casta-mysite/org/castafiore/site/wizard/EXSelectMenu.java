package org.castafiore.site.wizard;

import java.io.File;
import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.scripting.EXXHTMLFragment;

public class EXSelectMenu extends EXXHTMLFragment implements Event{

	public EXSelectMenu(String name) {
		super(name, "webos/gs/Menus.xhtml");
		
		addChild(new EXContainer("menus", "div").setStyle("margin", "auto").setStyle("width", "567px"));
		//File dir = new File("/usr/local/software/tomcat6/emallofmauritius/mall/designer/menu/vertical");
		File dir = new File("C:\\apache-tomcat-6.0.18\\webapps\\casta-ui\\designer\\menu\\vertical\\");
		File[] contents = dir.listFiles();
		//for(String s : LAYOUTS){
		for(File f : contents){
			if(f.isDirectory() ){
				continue;
			}
			getChild("menus").addChild(new EXContainer(f.getName().replace("'", "").replace(" ", "").replace(".bmp", ""), "img").setAttribute("src", "designer/menu/vertical/" + f.getName()).addEvent(this, CLICK));
			
		}
	}

	
	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}
	
	public String getSelected(){
		for(Container c : getChild("menus").getChildren()){
			if("true".equals(c.getAttribute("selected"))){
				return c.getName();
			}
		}
		return null;
	}
	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		for(Container c : container.getParent().getChildren()){
			c.setStyle("border", "3px double");
			c.setAttribute("selected", "false");
		}
		container.setStyle("border", "double 3px red");
		container.setAttribute("selected", "true");
		
		return true;
	}
	

	

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
