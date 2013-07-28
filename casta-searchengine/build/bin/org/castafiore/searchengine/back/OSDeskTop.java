package org.castafiore.searchengine.back;

import java.util.Map;

import org.castafiore.ecm.ui.fileexplorer.icon.EXIcon;
import org.castafiore.searchengine.EXAnimator;
import org.castafiore.security.User;
import org.castafiore.shoppingmall.crm.EXFastPaymentCreator;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.Droppable;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.contextmenu.ContextMenuAble;
import org.castafiore.ui.ex.contextmenu.ContextMenuModel;
import org.castafiore.ui.ex.panel.EXOverlayPopupPlaceHolder;
import org.castafiore.ui.ex.panel.Panel;
import org.castafiore.ui.js.JMap;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;
import org.castafiore.wfs.types.Shortcut;

public class OSDeskTop extends EXContainer implements PopupContainer,  Event, Desktop{

	public OSDeskTop(String name, User user) {
		super(name, "div");
		addClass("container").setStyle("width", "970px");
		addChild(new EXAnimator());
		addChild(new EXOSManager(name, user));
		//addChild(new EXOSNotifications(name));
		addChild(new EXOverlayPopupPlaceHolder(""));
		
		addChild(new EXContainer("logout", "a").setAttribute("href", "logout.jsp").addChild(new EXContainer("", "img").setAttribute("src", "http://www.destination360.com/images/dsgn/logout.png?1292352535")));
		addChild(new EXContainer("fastPayment", "a").addEvent(new Event() {
			
			@Override
			public void Success(ClientProxy container, Map<String, String> request)
					throws UIException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean ServerAction(Container container, Map<String, String> request)
					throws UIException {
				container.getAncestorOfType(OSDeskTop.class).addWindow(new EXFastPaymentCreator("fs"), false);
				return true;
			}
			
			@Override
			public void ClientAction(ClientProxy container) {
				container.makeServerRequest(this);
				
			}
		}, CLICK).setAttribute("title", "Make new payment").setStyle("position", "absolute").setStyle("top", "25px").setStyle("right", "140px").addChild(new EXContainer("", "img").setAttribute("src", "http://icons.iconarchive.com/icons/aha-soft/large-business/64/Cash-register-icon.png")));
		//addEvent(this,Event.DND_DROP );
	}
	
	
	public void addWindow(Panel window, boolean forceNew){
		String[] positions = getDefaultPositions();
		
		if(forceNew){
			if(StringUtil.isNotEmpty(window.getStyle("top"))){
				window.setStyle("top", positions[0]);
			}
			if(StringUtil.isNotEmpty(window.getStyle("left"))){
				window.setStyle("left", positions[1]);
			}
			addChild(window.setStyle("position", "absolute"));
		}else{
			if(getDescendentByName(window.getName()) == null){
				addChild(window.setStyle("position", "absolute"));
			}
		}
	}
	
	public String[] getDefaultPositions(){
		
		int[] pxs = new int[]{60,60};
		for(Container c : getChildren()){
			pxs[0] = pxs[0] + 40;
			pxs[1] = pxs[1] + 60;
		}
		
		return new String[]{pxs[0] + "px", pxs[1] + "px"};
	}
	
	
	public void removeWindow(String name){
		for(Container c : getChildren()){
			if(c.getName().equals(name)){
				getDescendentOfType(EXAnimator.class).addCommand(new ClientProxy("#" + c.getId()).fadeOut(100));
				getChildren().remove(c);
				break;
				
			}
		}
		
	}

	
	

	@Override
	public void addPopup(Container popup) {
		getDescendentOfType(EXOverlayPopupPlaceHolder.class).addChild(popup);
		
	}

	
	private final static String[] MENU = new String[]{"Change wallpaper"};


	public Event getEventAt(int index) {
		// TODO Auto-generated method stub
		return null;
	}



	public String getIconSource(int index) {
		// TODO Auto-generated method stub
		return null;
	}


	
	public String getTitle(int index) {
		return MENU[index];
	}



	public int size() {
		return MENU.length;
	}



	public ContextMenuModel getContextMenuModel() {
		return (ContextMenuModel)this;
		
	}



	public String[] getAcceptClasses() {
		return new String[]{"feicon"};
	}


	
	public JMap getDroppableOptions() {
		return new JMap();
	}


	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(new JMap().put("sourceid", ClientProxy.getDragSourceId()),this);
		
	}


	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		
		Container source = getDescendentById(request.get("sourceid"));
		File f = ((EXIcon)source).getFile();
		Directory desktop = (Directory)SpringUtil.getRepositoryService().getFile("/root/users/" + Util.getRemoteUser() + "/Desktop",Util.getRemoteUser());
		Shortcut s = desktop.createFile("shortcut-" + f.getName(), Shortcut.class);
		s.setReference(f.getAbsolutePath());
		desktop.save();
		return true;
		
	}


	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
