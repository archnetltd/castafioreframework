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
 package org.castafiore.webwizard;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.castafiore.designable.portal.PortalContainer;
import org.castafiore.designer.marshalling.DesignableUtil;
import org.castafiore.designer.model.NavigationDTO;
import org.castafiore.designer.portalmenu.PortalMenu;
import org.castafiore.resource.ResourceLocatorFactory;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.layout.EXVerticalLayoutContainer;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.utils.IOUtil;
import org.castafiore.utils.ResourceUtil;

public class Template {

	private String name;
	
	private String rootPortalDataPath = "/root/portal-data/master-templates";
	
	public String getThumbnailUrl(){
		return ResourceUtil.getDownloadURL("ecm", rootPortalDataPath + "/" + name + "/" + name + ".gif" ); 
	}
	
	public PortalContainer getInstance()throws Exception{
		String spec = "ecm:" + rootPortalDataPath + "/" + name + "/portal-template.ptl" ;
		InputStream in = ResourceLocatorFactory.getResourceLocator(spec).getResource(spec, null).getInputStream();
		return (PortalContainer)DesignableUtil.buildContainer(in, false);
	} 
	
	public byte[] getPortalDefinition(String username)throws Exception{
		//SpringUtil.getRepositoryService().getFile(path, username)
		String spec = "ecm:" + rootPortalDataPath + "/portal-template.ptl" ;
		
		InputStream in = ResourceLocatorFactory.getResourceLocator(spec).getResource(spec, null).getInputStream();
		String s = IOUtil.getStreamContentAsString(in);
		s = s.replace("${name}", name);
		s = s.replace("${username}", username);
		return s.getBytes();
		//return IOUtil.getStreamContentAsBytes(in);
	}
	
	public Properties getProperties()throws Exception{
		String spec = ResourceUtil.getDownloadURL("ecm", rootPortalDataPath + "/" + name + "/properties.properties" );
		InputStream in = ResourceLocatorFactory.getResourceLocator(spec).getResource(spec, null).getInputStream();
		Properties prop = new Properties();
		prop.load(in);
		return prop;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public static void GEN(){
		String[] templates = new String[]{"blooming", "petpaws", "yosemite", "mountainbreeze", "proofing"};
		for(String s : templates){
			Template t =new Template();
			t.setName(s);
			//System.out.println()
			t.buildFreeCSSPortal();
		}
	}
	public PortalContainer buildFreeCSSPortal(){
		
		String templatePath = rootPortalDataPath + "/" + name;
		
		String portalTemplate = ResourceUtil.getDownloadURL("ecm",  templatePath + "/portal.html");
		String logoTemplate = ResourceUtil.getDownloadURL("ecm",  rootPortalDataPath + "/logo.html");
		String searchTemplate = ResourceUtil.getDownloadURL("ecm",  rootPortalDataPath + "/searchForm.html");
		String footerTemplate = ResourceUtil.getDownloadURL("ecm",  rootPortalDataPath + "/footer.html");
		String columnArticleTemplate = ResourceUtil.getDownloadURL("ecm",  rootPortalDataPath + "/columnArticle.html");
		String columnListTemplate =  ResourceUtil.getDownloadURL("ecm",  rootPortalDataPath + "/columnList.html");
		
		PortalContainer portalContainer = (PortalContainer)DesignableUtil.getInstance("portal:portalcontainer");
		portalContainer.setName(name);
		
		Container rootXHTML = DesignableUtil.getInstance("core:xhtml");
		rootXHTML.setName("root");
		rootXHTML.setAttribute("template", portalTemplate);
		
		Container logo = DesignableUtil.getInstance("data:datacontainer");
		logo.setName("logo");
		logo.setAttribute("templatelocation", logoTemplate);
		
		Container search = DesignableUtil.getInstance("core:xhtml");
		search.setName("search");
		search.setAttribute("template", searchTemplate);
		
		PortalMenu menu = (PortalMenu)DesignableUtil.getInstance("portal:simple-menu");
		menu.setStyleClass("");
		String[] modules = new String[]{"home", "blog", "forum", "about us"};
		NavigationDTO root = new NavigationDTO();
		root.setLabel("root");
		root.setName("root");
		root.setPageReference("root");
		root.setUri("root");
		
		for(String module : modules){
			NavigationDTO nav = new NavigationDTO();
			nav.setLabel(module);
			nav.setName(module);
			nav.setPageReference(module);
			nav.setUri(module);
			root.getChildren().add(nav);
		}
		menu.setName("menu");
		menu.setNavitation(root);
		
		Container pageContainer = DesignableUtil.getInstance("portal:pagecontainer");
		pageContainer.setName("pageContainer");
		
		EXVerticalLayoutContainer sideBar = (EXVerticalLayoutContainer)DesignableUtil.getInstance("core:verticallayout");
		sideBar.setName("sideBar");
		
		Container columnArticle = DesignableUtil.getInstance("data:datacontainer");
		columnArticle.setAttribute("layoutdata", "0");
		columnArticle.setAttribute("templatelocation", columnArticleTemplate);
		columnArticle.setName("columnArticle");
		
		Container columnList = DesignableUtil.getInstance("data:datacontainer");
		columnList.setAttribute("templatelocation", columnListTemplate);
		columnList.setAttribute("layoutdata", "1");
		columnList.setName("columnList");
		
		Container footer = DesignableUtil.getInstance("data:datacontainer");
		footer.setName("footer");
		footer.setAttribute("templatelocation", footerTemplate);
		
		
		portalContainer.addChild(rootXHTML);
			rootXHTML.addChild(logo);
			rootXHTML.addChild(search);
			rootXHTML.addChild(menu);
			rootXHTML.addChild(pageContainer);
			rootXHTML.addChild(sideBar);
				sideBar.addChild(columnArticle, "0");
				sideBar.addChild(columnList, "1");
			rootXHTML.addChild(footer);
		
			
		String xml = DesignableUtil.generateXML(portalContainer, null);
		System.out.println("*******" + name + "*********");
		System.out.println(xml);
		try{
		Container c = DesignableUtil.buildContainer(new ByteArrayInputStream(xml.getBytes()), false);
			
		return (PortalContainer)c;
		}catch (Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		
		//create portal container
			//add xhtml that points to portal
				//add dataContainer
	}

}
