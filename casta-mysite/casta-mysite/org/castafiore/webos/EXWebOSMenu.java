/*
 * Copyright (C) 2007-2010 Castafiore
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */
 package org.castafiore.webos;
 
import java.io.IOException;
import java.net.URL;
import java.util.Map;

import org.castafiore.community.ui.users.EXUserForm;
import org.castafiore.designer.EXDesigner;
import org.castafiore.security.User;
import org.castafiore.sms.ui.EXSMSModule;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Application;
import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.EventUtil;
import org.castafiore.wfs.Util;

import com.google.gdata.client.youtube.YouTubeQuery;
import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.data.youtube.VideoFeed;
import com.google.gdata.util.ServiceException;

public class EXWebOSMenu extends EXContainer implements Event{
	
	private final static String modules[] = new String[]{
		"My Computer",
		"Portal Studio",
		"Collaboration",
		"Manage my business",
		"Events",
		"Youtube Player",
		"Messaging",
		"Multimedia",
		"Log out"
	};

	public EXWebOSMenu() {
		super("EXWebOSMenu", "div");
		
		addStyleSheet("webos/style/style.css");
		addScript("webos/js/interface.js");
		addClass("dock");
		addClass("myDoc");
		Container docContainer = new EXContainer("docContainer", "div").addClass("dock-container2").
		setStyle("width", "100%").setStyle("left", "60px").setStyle("top", "-50px").setStyle("height", "129px").
		setStyle("background-image", "url(webos/img/orig/doc.png)")
		.setStyle("background-position", "143px 50%")
		.setStyle("background-repeat", "no-repeat");
		
		
		addChild(docContainer);
		 
		String width = "128px";
		for(int i = 0; i < modules.length; i ++){
			String title = modules[i];
			
			String left = (i*75)+250 + "px";
			
			
			Container a = new EXContainer("", "a").setStyle("width", width).setStyle("left", left).addClass("dock-item2").setAttribute("href", "#").setStyle("top", "0px");
			a.setAttribute("ancestor", getClass().getName()).setAttribute("method", "showApplication").addEvent(EventUtil.GENERIC_FORM_METHOD_EVENT, Event.CLICK).setAttribute("appIndex", i + "");
			
			
			
			Container span = new EXContainer("", "span").setDisplay(false).setText(title);
			a.addChild(span);
			
			Container img = new EXContainer("", "img").setAttribute("src", "webos/img/arnaud/" + (i+1) + ".png");
			img.setStyle("width", "65%");
			a.addChild(img);
			
			if(i == 8){
				a.setStyle("right", "75px").setStyle("left", "none");
			}
			
			docContainer.addChild(a);
		}
		
		addEvent(this, READY);
	}

	public void ClientAction(ClientProxy container) {
//		JMap options = new JMap()
//		.put("maxWidth", 60)
//		.put("items", "a")
//		.put("itemsText", "span")
//		.put("container", container.getChildren().get(0).getIdRef())
//		.put("itemWidth", 40)
//		.put("proximity", 80)
//		.put("alignment", "left")
//		.put("valign", "bottom")
//		.put("halign", "center");
//		container.addMethod("Fisheye", options);
	}

	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		return false;
	}

	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
	}
	
	
	public void showApplication(Container caller){
		String index = caller.getAttribute("appIndex");
		if(index.equals("0")){
			loadFileExplorer();
		}else if(index.equals("2")){
			loadCommunityManagement();
		}else if(index.equals("1")){
			loadDesigner();
		}else if(index.equals("3")){
			youtube();
		}
		else if(index.equals("8")){
			getAncestorOfType(WebOS.class).logOut();
		}
		else{
			loadUnderConstruction();
		}
	}
	
	protected void loadUnderConstruction(){
		EXPanel panel = new EXPanel("undercontruction", "Under Contruction");
		//panel.setWidth(Dimension.parse("960px"));
		//Container container = (Container)SpringUtil.getBean("fileExplorer");
		
		panel.setBody(new EXContainer("", "div").setStyle("text-align", "center"));
		panel.getBody().addChild(new EXContainer("", "h2").setText("This module is still under contruction and will be available soon"));
		
		Application root = getRoot();
		root.addChild(panel);
	}
	
	public void youtube(){
//		try{
//			EXYoutubePlayer player = new EXYoutubePlayer("player");
//			VideoFeed feed = searchFeed("Castafiore");
//			player.setVideoFeed(feed);
//			Application root = getRoot();
//			
//			root.addChild(player);
//		}catch(Exception e){
//			throw new UIException(e);
//		}
	}
	private  VideoFeed searchFeed(String searchTerms) throws IOException,
	ServiceException {
//	YouTubeService service = new YouTubeService("gdataSample-YouTube-1");
//	YouTubeQuery query = new YouTubeQuery(new URL(YouTubeReadonlyClient.VIDEOS_FEED));
//	query.setOrderBy(YouTubeQuery.OrderBy.VIEW_COUNT);
//	query.setSafeSearch(YouTubeQuery.SafeSearch.NONE);
//	query.setFullTextQuery(searchTerms);
//	VideoFeed videoFeed = service.query(query, VideoFeed.class);
//	return videoFeed;
		return null;

}
	
	protected void loadDesigner(){
		
		Application root = getRoot();
		EXDesigner des = new EXDesigner();
		
		
		
		root.addChild(des);
		des.setStyle("position", "absolute");
		des.setStyle("top", "0");
		des.setStyle("left", "0");
		root.getDescendentOfType(EXWebOSMenu.class).setDisplay(false);
		
	}
	
	protected void loadFileExplorer(){
		try{
			EXPanel panel = new EXPanel("myExplorer", "File explorer");
			panel.setWidth(Dimension.parse("960px"));
			Container container = (Container)SpringUtil.getBean("fileExplorer");
			panel.setBody(container);
			Application root = getRoot();
			root.addChild(panel);
			ComponentUtil.metamorphoseExplorer(container);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	protected void loadCommunityManagement(){
//		EXPanel panel = new EXPanel("", "Configure your account");
//		panel.setWidth(Dimension.parse("960px"));
//		//EXCommunity comm = new EXCommunity();
		//panel.setBody(comm);
		User user = SpringUtil.getSecurityService().loadUserByUsername(Util.getRemoteUser());
		EXUserForm form = new EXUserForm(user);
		Application root = getRoot();
		root.addChild(form);
	}
	
	protected void loadSMSModule(){
		EXPanel panel = new EXPanel("", "Configure SMS Module");
		panel.setWidth(Dimension.parse("960px"));
		EXSMSModule module = new EXSMSModule();
		panel.setBody(module);
		Application root = getRoot();
		root.addChild(panel);
	}

}
