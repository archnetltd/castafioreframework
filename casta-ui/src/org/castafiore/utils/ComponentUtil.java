/*
 * Copyright (C) 2007-2008 Castafiore
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

package org.castafiore.utils;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.castafiore.ui.Application;
import org.castafiore.ui.ApplicationRegistryUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.WebServletAwareApplication;
import org.castafiore.ui.engine.context.CastafioreApplicationContextHolder;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.EXMutableIdContainer;
import org.castafiore.ui.ex.panel.EXPanel;
/**
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * June 27 2008
 */
public class ComponentUtil {

	public static int compcount = 0;
	
	public static void fastExtractComponents(Container container, List<StatefullComponent> result, List<Container> ccs, String componentId)
	{
		compcount++;
		if(container == null)
		{
			return;
		}
		if(container instanceof StatefullComponent)
		{
			result.add((StatefullComponent)container);
		}
		if(container.getId().equals(componentId))
		{
			ccs.add(container);
		}
		
		Iterator<Container> iterChildre = container.getChildren().iterator();
		
		while(iterChildre.hasNext())
		{
			Container child = iterChildre.next();
			fastExtractComponents(child, result, ccs, componentId);
		}
	}
	
	public static void getAllStatefullDescendents(Container container, List<StatefullComponent> result)
	{
		if(container == null)
		{
			return;
		}
		if(container instanceof StatefullComponent)
		{
			result.add((StatefullComponent)container);
		}
		
		Iterator<Container> iterChildre = container.getChildren().iterator();
		
		while(iterChildre.hasNext())
		{
			Container child = iterChildre.next();
			getAllStatefullDescendents(child, result);
		}
	}
	
	
	public static void iterateOverDescendentsOfType(Container container,  Class type,ComponentVisitor visitor){
		
		if(type.isAssignableFrom(container.getClass()))
		{
			//result.add(container);
			visitor.doVisit(container);
		}
		
		Iterator<Container> iterChildre = container.getChildren().iterator();
		
		while(iterChildre.hasNext())
		{
			Container child = iterChildre.next();
			iterateOverDescendentsOfType(child, type, visitor);
		}
		
	}
	
	public static void getDescendentsOfType(Container container, List<Container> result, Class type)
	{
		if(type.isAssignableFrom(container.getClass()))
		{
			result.add(container);
		}
		
		Iterator<Container> iterChildre = container.getChildren().iterator();
		
		while(iterChildre.hasNext())
		{
			Container child = iterChildre.next();
			getDescendentsOfType(child, result, type);
		}
	}
	
	public static Container getContainerById(Container root, String componentId)
	{
		if(componentId == null)
		{
			return root;
		}
		if(root.getId().equalsIgnoreCase(componentId))
		{
			return root;
		}
		Iterator<Container> iterChildren = root.getChildren().iterator();
		
		
		while(iterChildren.hasNext())
		{
			Container child = iterChildren.next();
			
			if(child.getId().equalsIgnoreCase(componentId))
			{
				return child;
			}
			else
			{
				Container tmp = getContainerById(child, componentId);
				 if(tmp!= null && tmp.getId().equalsIgnoreCase(componentId))
				 {
					 return tmp;
				 }
			}
		}
		
		
		return null;
		
	}
	
	
	public static void applyStyles(Container c, Map<String,String> styles){
		Iterator<String> keys = styles.keySet().iterator();
		while(keys.hasNext()){
			String key = keys.next();
			c.setStyle(key, styles.get(key));
			
		}
	}
	
	
	
	public static Container getContainer(String name, String tagName, String text, String sclass){
		EXContainer container = new EXContainer(name, tagName);
		if(text != null){
			container.setText(text);
		}
		
		if(sclass != null){
			container.setStyleClass(sclass);
		}
		
		return container;
	}
	
	public static Container getContainer(String name, String tagName){
		return getContainer(name,tagName,null,null);
	}
	
	public static Container getBR(){
		return new EXContainer("", "br");
	}
	
	public static Container getIdContainer(String name, String tagName, String id){
		EXMutableIdContainer container = new EXMutableIdContainer(name, tagName, id);
		
		
		
		return container;
	}
	
	public static void applyStyleOnAll(Container ancestor, Class<? extends Container> type, String styleName, String styleValue){
		List<Container> lst = new ArrayList<Container>();
		ComponentUtil.getDescendentsOfType(ancestor, lst, type);
		for(Container c : lst){
			c.setStyle(styleName, styleValue);
		}
	}
	
	
	public static void loadApplication(HttpServletRequest request, HttpServletResponse response)throws ServletException{
		String applicationId = request.getParameter("casta_applicationid");
		String userIdentity = request.getParameter("casta_userid");
		String portalPath = request.getParameter("casta_portalpath");
		//Enumeration params = request.getParameterNames();
		//request.getp
		if(applicationId != null){
			
			
			Application application =  (Application)request.getSession().getAttribute(applicationId);
			boolean isNew = false;
			if(application == null && applicationId != null)
			{
				isNew = true;
				
				//logger.debug("application :" + applicationId + " not found in session. Loading fresh application from registry");
				application = ApplicationRegistryUtil.getApplicationRegistry().getApplication(request, response);
				if(application == null)
				{
					//logger.error("there is no bean with name " + applicationId  + " configured in application registry");
					throw new ServletException("there is no bean with name " + applicationId  + " configured in application registry");
				}
				if(!application.getName().equals(applicationId))
				{
					//logger.error("The application Id configured in application-registry should be the same as the application name");
					throw new ServletException("The application Id configured in application-registry should be the same as the application name.");
				}
				
				
				
				
				
				//isnew = true;
				
			}
			Map para = request.getParameterMap();
			Iterator keys = para.keySet().iterator();
			while(keys.hasNext()){
				String key = keys.next().toString();
				String val = request.getParameter(key);
				application.getConfigContext().put(key, val);
			}
			application.getConfigContext().put("contextPath", ((HttpServletRequest)request).getContextPath());
			application.getConfigContext().put("serverName", request.getServerName());
			application.getConfigContext().put("serverPort", request.getServerPort());
			application.getConfigContext().put("session", request.getSession());
			application.getConfigContext().put("remoteAddress", request.getRemoteAddr());
			application.getConfigContext().put("sessionid", request.getSession().getId());
			application.getConfigContext().put("context", request.getSession());
			
			if(userIdentity != null){
				application.getConfigContext().put("remoteUser", userIdentity);
			}
			if(portalPath != null){
				application.getConfigContext().put("portalPath", portalPath);
			}
			CastafioreApplicationContextHolder.setApplicationContext(application);
			if(isNew){
				application.refresh();
			}
			if(application instanceof WebServletAwareApplication){
				((WebServletAwareApplication)application).setRequest(request);
				((WebServletAwareApplication)application).setResponse(response);
				
			}
		}
	}
	
	
	public static void moveUp(Container component, Container layout){
		int index = layout.getChildren().indexOf(component);
		if(index == 0){
			throw new UIException("Cannot move upward. Already at start of list");
		}
		layout.getChildren().remove(index);
		layout.getChildren().add(index-1, component);
		layout.setRendered(false);
	}
	
	public static void moveDown(Container component, Container layout) {
		
		int index = layout.getChildren().indexOf(component);
		if(index == layout.getChildren().size() -1){
			throw new UIException("Cannot move downward. Already at end of list");
		}
		layout.getChildren().remove(index);
		layout.getChildren().add(index+1, component);
		layout.setRendered(false);
	}
	
	public static void metamorphosePanel(EXPanel panel){
		panel.setAttribute("skin", "WARP");
		panel.removeClass("ui-widget-content").addClass("ui-widget-header").setStyle("padding", "0").setStyle("background-position", "0 0");
		String title = panel.getDescendentByName("title").getText();
		panel.getDescendentByName("titleBar").getChildByIndex(0).remove();
		panel.getDescendentByName("titleBar").setText(title).setStyleClass("").setStyle("text-align", "center").setStyle("padding", "10px");
		panel.getDescendentByName("content").setStyleClass("ui-widget-content").setStyle("margin", "10px").setStyle("padding", "0").setStyle("overflow", "auto").setStyle("font-weight", "normal");
		panel.getDescendentByName("panelFooter").setStyle("margin", "10px");
		panel.getDescendentByName("closeButton").setStyleClass("ui-state-default").setStyle("position", "absolute").setStyle("top", "3px").setStyle("right", "3px")
		.getDescendentByName("closeIcon").removeClass("ui-icon-closethick").addClass("ui-icon-close");
		
	}
	
	public static void metamorphoseExplorer(Container explorer){
		explorer.getDescendentByName("addressToolBar").setStyle("border", "none").removeClass("ui-corner-all").removeClass("ui-corner-top");
		if(explorer.getDescendentByName("ThumbnailView") != null)
		explorer.getDescendentByName("ThumbnailView").removeClass("ui-corner-all").setStyle("margin", "10px");
	}
	
	public static void metamorphoseDynaform(Container panel){
		panel.getDescendentByName("content").setStyle("padding", "10px");
	}
	
	public static void metamorphoseDesigner(Container des){
		des.getDescendentByName("digner").removeClass("ui-corner-all").setStyle("border-bottom", "none");
		des.getDescendentByName("desToolbar").removeClass("ui-corner-all").setStyle("border-bottom", "none");
		//for(Container c : children)
		
		
	}

}
