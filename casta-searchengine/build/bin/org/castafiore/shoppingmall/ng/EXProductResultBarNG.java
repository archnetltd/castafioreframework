package org.castafiore.shoppingmall.ng;

import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.ui.scripting.EXXHTMLFragment;

public class EXProductResultBarNG extends EXXHTMLFragment implements Event {

	public EXProductResultBarNG(String name) {
		super(name, "templates/ng/EXProductResultBarNG.xhtml");
		//addClass("toolbar");
		addChild(new EXContainer("amount", "p").addClass("amount"));
		addChild(new EXContainer("sortName", "a").setAttribute("href", "#sn").setAttribute("title", "Name").setText("Title").addEvent(this, CLICK));
		addChild(new EXContainer("sortPrice", "a").setAttribute("href", "#sp").setAttribute("title", "Price").setText("Price").addEvent(this, CLICK));
		addChild(new EXContainer("paginator", "ol"));
		addChild(new EXSelect("limiters", new DefaultDataModel<Object>().addItem("8", "12", "16", "32")).addEvent(this, Event.CHANGE));
		getDescendentOfType(EXSelect.class).setValue("12");
		addChild(new EXContainer("skinning", "style"));
		//setStyle("margin-top", "10px");
	}
	
	
	public int getPageSize(){
		return Integer.parseInt(getDescendentOfType(EXSelect.class).getValue().toString());
	}
	
	public void setSkin(String skin){
		//<style>.PaleGreen .ui-widget-content{background-image: url(emalltheme/headerPaleGreen);}</style>
		setStyleClass(skin);
		//addClass("toolbar");
		getChild("skinning").setText("." + skin + " .ui-widget-header{background-image: url(emalltheme/header"+ skin+".png);}");
		setStyleClass(skin);
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mergeCommand(new ClientProxy("#loader").setStyle("display", "block")).makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		if(container.getName().equals("sortName")){
			getAncestorOfType(EXCatalogueNG.class).sort("title");
		}else if(container.getName().equals("sortPrice")){
			getAncestorOfType(EXCatalogueNG.class).sort("price");
		}else if(container.getName().equals("limiters")){
			EXCatalogueNG ng = getAncestorOfType(EXCatalogueNG.class);
			ng.setModel(ng);
			//getAncestorOfType(EXCatalogueNG.class).changePage(0);
		}
		
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		container.mergeCommand(new ClientProxy("#loader").setStyle("display", "none"));
		
	}

}
