package org.castafiore.splashy.templates;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.castafiore.designer.EXDesigner;
import org.castafiore.searchengine.EXAnimator;
import org.castafiore.searchengine.back.Desktop;
import org.castafiore.searchengine.back.OSApplicationRegistry;
import org.castafiore.searchengine.back.OSBarItem;
import org.castafiore.searchengine.back.OSInterface;
import org.castafiore.searchengine.back.OSManager;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.RefreshSentive;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.EXWebServletAwareApplication;
import org.castafiore.ui.ex.panel.Panel;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;

public class EXSplashy extends EXWebServletAwareApplication implements Desktop, OSInterface, RefreshSentive, Event {

	public EXSplashy() {
		super("backsplashy");
		if(Util.getRemoteUser() == null){
			setAttribute("redirect", "templates.html?fromLogin=true");
		}else{
			addChild(new EXAnimator());
			addChild(new EXContainer("header", "div").addClass("splashyHeader").addChild(new EXContainer("nav", "div").setDraggable(true).addClass("nav").addChild(getMenu())));
			
			addChild(new EXContainer("desktop", "div").addClass("section").setStyle("z-index", "1000"));
			
			Panel panel = SpringUtil.getBeanOfType(OSApplicationRegistry.class).getWindow("Designer");
			
			
			
			addWindow(panel, true);
			
		}
		
	}
	
	
	
	
	public BinaryFile getMyPortal(){
		Directory dir = SpringUtil.getRepositoryService().getDirectory("/root/users/" + Util.getRemoteUser(), Util.getRemoteUser());
		for(BinaryFile bf : dir.getFiles(BinaryFile.class).toList()){
			if(bf.getName().endsWith(".ptl")){
				return bf;
			}
		}
		
		return null;
	}
	
	
	protected Container getMenu(){
		Container menu = new EXContainer("menu", "ul").addClass("menu");
		//menu.setDraggable(true);
		List<OSBarItem> items= SpringUtil.getBeanOfType(OSManager.class).getOSBarIcons();
		List<String> freeApps = new ArrayList<String>();
		freeApps.add("ShopSetting");
		freeApps.add("Inventory");
		freeApps.add("Designer");
		freeApps.add("FileExplorer");
		freeApps.add("Organization");
		freeApps.add("Registry");
		for(OSBarItem item : items){
			if(freeApps.contains(item.getAppName()))
			menu.addChild(new EXContainer("","li").addChild(item));
			item.getDescendentByName("img").setAttribute("height", "64");
		}//http://www.destination360.com/images/dsgn/logout.png?1292352535
		
		Container logout = new EXContainer("logourt", "a").addChild(new EXContainer("img", "img").setAttribute("src", "http://www.destination360.com/images/dsgn/logout.png?1292352535"));
		logout.setAttribute("title", "Logout from the application").addEvent(this, CLICK);
		menu.addChild(new EXContainer("", "li").addChild(logout));
		return menu;
	}
	
	
	public void addWindow(Panel window, boolean forceNew){

		getChild("desktop").getChildren().clear();
		getChild("desktop").setRendered(false);
		getChild("desktop").addChild(window.setStyle("position", "absolute"));
		window.setStyle("width", "100%");
		window.setStyle("top", "0px").setStyle("left", "0px");
		
		if(window.getDescendentOfType(EXDesigner.class) != null){
			EXDesigner designer = window.getDescendentOfType(EXDesigner.class);
			designer.setTemplateLocation("designer/sliced/designer-splashy.xhtml");
			designer.setAttribute("appid", "backsplashy");
			try{
			designer.open(getMyPortal());
			}catch(Exception e){
				throw new UIException (e);
			}
		}
	}
	
	public String[] getDefaultPositions(){
		return new String[]{  "0px", "0px"};
	}
	
	
	public void removeWindow(String name){
		for(Container c : getChild("desktop").getChildren()){
			if(c.getName().equals(name)){
				getDescendentOfType(EXAnimator.class).addCommand(new ClientProxy("#" + c.getId()).fadeOut(100));
				getChildren().remove(c);
				break;
				
			}
		}
		
	}


	@Override
	public Desktop getDeskTop() {
		return this;
	}


	@Override
	public void onRefresh() {
		if(Util.getRemoteUser() == null){
			this.getChildren().clear();
			setAttribute("redirect", "templates.html?fromLogin=true");
		}else{
			setAttribute("redirect", "");
			if(getChildren().size() == 0){
				addChild(new EXAnimator());
				addChild(new EXContainer("header", "div").addClass("splashyHeader").addChild(new EXContainer("nav", "div").addClass("nav").addChild(getMenu())));
				
				addChild(new EXContainer("desktop", "div").addClass("section"));
			}
		}
		
	}
	
	
	public void onReady(ClientProxy proxy){
		if(StringUtil.isNotEmpty(getAttribute("redirect"))){
			proxy.redirectTo(getAttribute("redirect"));
		}
	}


	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this,"Are you sure you want to walk out from the system?");
		
	}


	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		getRequest().getSession().invalidate();
		return true;
	}


	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		container.redirectTo("templates.html?fromLogin=true");
		
	}
	
	
	

}
