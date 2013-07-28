package org.castafiore.site.wizard;

import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.scripting.EXXHTMLFragment;

public class EXTextures extends EXXHTMLFragment implements Event{
	
	public EXTextures(String name){
		super(name, "webos/gs/Textures.xhtml");
		
		addChild(new EXContainer("logos", "div").setStyle("width", "50000px"));
		
		
		for(int i = 40; i <=60; i++){
			Container container = new EXContainer("logo", "img").setAttribute("src", "webos/gs/logos/logo0" + i + ".png").addEvent(this, Event.CLICK);
			if(i==40){
				getChild("logos").setAttribute("selVal", container.getAttribute("src"));
				container.setStyle("border", "double 3px red");
			}
			getChild("logos").addChild(container);
		}
		addChild(new EXContainer("headerTextures", "div").setStyle("width", "50000px"));
		
		
		for(int i = 1; i <=28; i++){
			Container container = new EXContainer("texture", "img").setAttribute("src", "webos/gs/textures/" + i + ".jpg").addEvent(this, Event.CLICK).setStyle("width", "100px").setStyle("height", "100px");
			if(i == 1){
				getChild("headerTextures").setAttribute("selVal", container.getAttribute("src"));
				container.setStyle("border", "double 3px red");
			}
			getChild("headerTextures").addChild(container);
		}
	}
	
	public String getLogo(){
		return getChild("logos").getAttribute("selVal");
	}
	
	public String getBodyTexture(){
		return getChild("headerTextures").getAttribute("selVal");
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		if(container.getName().equals("logo")){
			getChild("logos").setAttribute("selVal", container.getAttribute("src"));
			for(Container c : getChild("logos").getChildren()){
				c.setStyle("border", "solid 1px silver");
			}
			
			container.setStyle("border", "double 3px red");
		}else if(container.getName().equals("texture")){
			getChild("headerTextures").setAttribute("selVal", container.getAttribute("src"));
			for(Container c : getChild("headerTextures").getChildren()){
				c.setStyle("border", "solid 1px silver");
			}container.setStyle("border", "double 3px red");
		}
		
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}
	
	
}
