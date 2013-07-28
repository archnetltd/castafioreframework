package org.castafiore.appstore.ui;

import java.util.List;
import java.util.Map;

import org.castafiore.appstore.AppPackage;
import org.castafiore.appstore.AppStoreUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.panel.EXPanel;

public class EXAppStore extends EXPanel implements Event{

	public EXAppStore(String name) {
		super(name, "Choose a Category");
		Container categories = new EXContainer("categories", "div").addClass("appCategoriesBody");
		setBody(categories);
		setStyle("width", "822px");
		getDescendentByName("titleBar").addChild(new EXButton("backToCategory", "Choose a Category").addEvent(this, Event.CLICK));
		showCategories();
		setShowCloseButton(false);

	}
	
	public void showCategories(){
		Container categories = getBodyContainer().getDescendentByName("categories");
		categories.getChildren().clear();
		categories.setRendered(false);
		List<String> cats = AppStoreUtil.getCategories();
		for(String cat : cats){
			EXAppCategory c = new EXAppCategory("");
			c.setAppCategory(cat);
			categories.addChild(c.setStyle("float", "left"));
		}
		getDescendentByName("titleBar").getDescendentByName("backToCategory").setDisplay(false);
	}
	
	public void showCategory(String category){
		Container body = getBodyContainer().getDescendentByName("categories");
		body.getChildren().clear();
		body.setRendered(false);
		List<AppPackage> apps = AppStoreUtil.getApplicationsByCategory(category);
		for(AppPackage app : apps){
			EXApplicationPackageThumbnail apt = new EXApplicationPackageThumbnail("");
			apt.setApplication(app);
			body.addChild(apt.setStyle("float", "left"));
		}
		setTitle(category);
		getDescendentByName("titleBar").getDescendentByName("backToCategory").setDisplay(true);
	}
	
	public void showApplication(String path){
		
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask(container.getAncestorOfType(EXAppStore.class)).makeServerRequest(this);
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		showCategories();
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
