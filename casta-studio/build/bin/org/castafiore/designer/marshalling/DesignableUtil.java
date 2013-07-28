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
 package org.castafiore.designer.marshalling;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import net.htmlparser.jericho.Attribute;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

import org.castafiore.KeyValuePair;
import org.castafiore.SimpleKeyValuePair;
import org.castafiore.designable.DesignableFactory;
import org.castafiore.designable.layout.DesignableLayoutContainer;
import org.castafiore.designable.portal.PageContainer;
import org.castafiore.designable.portal.PortalContainer;
import org.castafiore.designer.dataenvironment.Datasource;
import org.castafiore.designer.dataenvironment.DatasourceFactory;
import org.castafiore.designer.dataenvironment.DatasourceFactoryService;
import org.castafiore.designer.events.GroovyEvent;
import org.castafiore.designer.service.DesignableService;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Application;
import org.castafiore.ui.Container;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.layout.DroppableSection;
import org.castafiore.ui.interceptors.SpringInterceptorRegistry;

import org.castafiore.utils.EventUtil;
import org.castafiore.utils.IOUtil;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.futil;
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class DesignableUtil {
	
	
	public static void unzipTemplates()throws Exception{
		byte[] buffer = new byte[2048];
		String base = "c://java//freecss//";
		java.io.File dbase = new java.io.File(base);
		for(java.io.File dir : dbase.listFiles()){
			for(java.io.File f : dir.listFiles()){
				if(f.getName().endsWith(".zip")){
					ZipInputStream zip = new ZipInputStream(new FileInputStream(f));
					ZipEntry entry;
					while ((entry = zip.getNextEntry()) != null) {
						
						try {
							String name = entry.getName();
							
							if(!entry.isDirectory()){
								
								java.io.File disk = new java.io.File(dir.getAbsolutePath() + "/" + name);
								
									disk.getParentFile().mkdirs();
								
								//disk.mkdirs();
								FileOutputStream fout = new FileOutputStream(disk);
								int len = 0;
								while ((len = zip.read(buffer)) > 0) {
									fout.write(buffer, 0, len);
								}
								fout.flush();
								fout.close();
							}else{
								java.io.File disk = new java.io.File(dir.getAbsolutePath() + "/" + name);
								disk.mkdirs();
							}
						}catch(Exception e){
							e.printStackTrace();
						}
					}
				}
			}
		}
			
	}
	
	public static void downloadFreecss()throws Exception{
		String base = "c://java//freecss//";
		
		for(int i = 1; i <=57;i++){
			String url = "http://www.freecsstemplates.org/css-templates/"+i+"/";
			Source s = new Source(new URL(url));
			List<Element> tms = s.getAllElementsByClass("thumbnail");
			for(Element e : tms){
				Element img = e.getFirstElement("img");
				String name = img.getAttributeValue("alt");
				String imgUrl = "http://www.freecsstemplates.org" + img.getAttributeValue("src");
				List<KeyValuePair> properties = new ArrayList<KeyValuePair>();
				List<Element> lines = e.getAllElements("tr");
				for(Element line : lines){
					SimpleKeyValuePair kv = new SimpleKeyValuePair(line.getFirstElement("th").getTextExtractor().toString(),line.getFirstElement("td").getTextExtractor().toString());
					properties.add(kv);
				}
				String zipUrl = "http://www.freecsstemplates.org/download/zip/" + name;
				java.io.File fbase = new java.io.File(base + "/" + name);
				fbase.mkdir();
				
				FileOutputStream fimg = new FileOutputStream(new java.io.File(base + "/"+name+"/thumbnail.jpg"));
				fimg.write(ResourceUtil.readUrlBinary(imgUrl));
				fimg.flush();
				fimg.close();
				
				FileOutputStream fzip = new FileOutputStream(new java.io.File(base + "/"+name+"/"+name+".zip"));
				fzip.write(ResourceUtil.readUrlBinary(zipUrl));
				fzip.flush();
				fzip.close();
				
			}
		}
	}
	
	
	private static Map<String, String> getTagMappings(){
		Map<String, String> m = new HashMap<String, String>();
		m.put("label", "core:label");
		m.put("p", "core:p");
		m.put("h1", "core:h1");
		m.put("h2", "core:h2");
		m.put("h3", "core:h3");
		m.put("h4", "core:h4");
		m.put("h5", "core:h5");
		m.put("h6", "core:h6");
		m.put("hr", "core:hr");
		m.put("img", "core:image");
		m.put("a", "core:a");
		m.put("ul", "core:ul");
		m.put("li", "core:li");
		m.put("div", "core:div");
		
		m.put("input", "core:textbox");
		m.put("body", "portal:portalcontainer");
		return m;
		
	}
	
	/**
	 * copy a whole portal from
	 * @param portal
	 * @param destination
	 * 
	 * .ptl
	 * pages
	 * modules
	 * css
	 * _files
	 */
	public static void copyProject(BinaryFile portal, String destination)throws Exception{
		String ptlName = portal.getName();
		String name = ptlName.replace(".ptl", "").trim();
		Directory files = portal.getParent().getFile(name + "_files", Directory.class);
		
		
		Directory dest = SpringUtil.getRepositoryService().getDirectory(destination, Util.getRemoteUser());
		BinaryFile newPortal = dest.createFile(ptlName, BinaryFile.class);
		Directory newFiles = dest.createFile(files.getName(), Directory.class);
		newPortal.write(portal.getInputStream());
		
		
		copyChildren(portal, newPortal);
		copyChildren(files, newFiles);
		dest.save();
	}
	
	private static void copyChildren(Directory portal, Directory newPortal)throws Exception{
		for(File f : portal.getFiles().toList()){
			if(f instanceof BinaryFile){
				BinaryFile bf = newPortal.createFile(f.getName(), BinaryFile.class);
				bf.write(((BinaryFile)f).getInputStream());
				
				copyChildren((Directory)f, bf);
			}else if(f instanceof Directory){
				Directory dir = newPortal.createFile(f.getName(), Directory.class);
				
				copyChildren((Directory)f, dir);
			}
			
			if(f instanceof Directory){
				
			}
		}
	}
	
	
	public static void generateGeneric(){
		
	}
	
	
	public static void generateFreecssDisk(String name)throws Exception{
		//generate portal
		//generate page
		//generate css
		//add images
		
		
		ApplicationContext ctx = new FileSystemXmlApplicationContext("C:\\castafioera\\casta-studio\\web\\WEB-INF\\configs\\designer\\dodo.xml");
		String html = "c://java//freecss//" + name + "//index.html";
		Map<String,String> mappings = getTagMappings();
		List<String> ids = new ArrayList<String>();
		List<Object> result = new ArrayList<Object>(1);
		
		Source source = new Source(new FileInputStream(new java.io.File(html)));
		Element body = source.getFirstElement("body");
		String url = "dav/splashy/" + name;
		Container c = addElement(body, null, mappings, ids,result,false,url,ctx);
		
		String ptl =DesignableUtil.generateXML(c, c.getAncestorOfType(PortalContainer.class));
		
		System.out.println(ptl);
		
		Container page = addElement(((Element)result.get(0)).getFirstElement(),null,mappings,ids,result,true,url,ctx);
		
		String pgs = DesignableUtil.generateXML(page, c.getAncestorOfType(DesignableLayoutContainer.class));
		System.out.println(pgs);
		String css = extractStyle(url);
		
		for(String id : ids){
			css = css.replace("#" + id, "." + id);
		}
		css = css.replace("url(", "url(" + url);
		System.out.println(css);
		
		
//		String portalName = url.replace("http://www.freecsstemplates.org/previews/", "").replace("/", "");
//		
//		
//		Directory dir = SpringUtil.getRepositoryService().getDirectory("/root/users/" + Util.getRemoteUser(), Util.getRemoteUser());
		
//		BinaryFile portal = dir.getFile(portalName + ".ptl") != null?dir.getFile(portalName + ".ptl", BinaryFile.class):dir.createFile(portalName + ".ptl", BinaryFile.class);
//		Directory pages = portal.getFile("pages") != null?portal.getFile("pages", Directory.class):portal.createFile("pages", Directory.class);
//		BinaryFile home = pages.getFile("home") != null?pages.getFile("home", BinaryFile.class):pages.createFile("home", BinaryFile.class);
//		portal.write(ptl.getBytes());
//		home.write(pgs.getBytes());
//		BinaryFile bcss = portal.getFile(portalName + ".css") != null?portal.getFile(portalName + ".css", BinaryFile.class):portal.createFile(portalName + ".css", BinaryFile.class);
//		bcss.write(css.getBytes());
//		//Directory files = portal.getFile(portalName + "_files") != null?portal.getFile(portalName + "_files", Directory.class):dir.createFile(portalName + "_files", Directory.class);
////		Directory images = portal.getFile(portalName + "_files") != null?portal.getFile(portalName + "_files", Directory.class):files.createFile("images", Directory.class);
////		for(String img : uimages){
////			String name = StringUtil.split(img, "/")[StringUtil.split("img", "/").length-1];
////			BinaryFile bimg = images.createFile(name, BinaryFile.class);
////			bimg.write(new URL(img).openStream());
////			
////		}
//		
//		dir.save();
		
	}
	
	public static void generateFreescc(String url)throws Exception{
		List<String> ids = new ArrayList<String>();
		if(url == null)
			url ="http://www.freecsstemplates.org/previews/connectivity/";
		
		List<Object> result = new ArrayList<Object>(1);
		List<String> uimages = new ArrayList<String>();
		
		Map<String,String> mappings = getTagMappings();
		ApplicationContext ctx = SpringUtil.getApplicationContext();
		Source source = new Source(new URL(url));
		Element body = source.getAllElements("body").get(0);
		Container c = addElement(body, null, mappings, ids,result,false,url,ctx);
		
		String ptl =DesignableUtil.generateXML(c, c.getAncestorOfType(PortalContainer.class));
		
		System.out.println(ptl);
		
		Container page = addElement(((Element)result.get(0)).getFirstElement(),null,mappings,ids,result,true,url, ctx);
		
		String pgs = DesignableUtil.generateXML(page, c.getAncestorOfType(DesignableLayoutContainer.class));
		System.out.println(pgs);
		String css = extractStyle(url);
		
		for(String id : ids){
			css = css.replace("#" + id, "." + id);
		}
		css = css.replace("url(", "url(" + url);
		System.out.println(css);
		
		
		String portalName = url.replace("http://www.freecsstemplates.org/previews/", "").replace("/", "");
		
		
		Directory dir = SpringUtil.getRepositoryService().getDirectory("/root/users/" + Util.getRemoteUser(), Util.getRemoteUser());
		
		BinaryFile portal = dir.getFile(portalName + ".ptl") != null?dir.getFile(portalName + ".ptl", BinaryFile.class):dir.createFile(portalName + ".ptl", BinaryFile.class);
		Directory pages = portal.getFile("pages") != null?portal.getFile("pages", Directory.class):portal.createFile("pages", Directory.class);
		BinaryFile home = pages.getFile("home") != null?pages.getFile("home", BinaryFile.class):pages.createFile("home", BinaryFile.class);
		portal.write(ptl.getBytes());
		home.write(pgs.getBytes());
		BinaryFile bcss = portal.getFile(portalName + ".css") != null?portal.getFile(portalName + ".css", BinaryFile.class):portal.createFile(portalName + ".css", BinaryFile.class);
		bcss.write(css.getBytes());
		//Directory files = portal.getFile(portalName + "_files") != null?portal.getFile(portalName + "_files", Directory.class):dir.createFile(portalName + "_files", Directory.class);
//		Directory images = portal.getFile(portalName + "_files") != null?portal.getFile(portalName + "_files", Directory.class):files.createFile("images", Directory.class);
//		for(String img : uimages){
//			String name = StringUtil.split(img, "/")[StringUtil.split("img", "/").length-1];
//			BinaryFile bimg = images.createFile(name, BinaryFile.class);
//			bimg.write(new URL(img).openStream());
//			
//		}
		
		dir.save();
		
	}
	
	
	
	
	private static String extractStyle(String root)throws Exception{
		Source source = new Source(new URL(root));
		String result = "";
		for(Element e : source.getAllElements("link")){
			if(e.getName().equalsIgnoreCase("link")){
				
				if(StringUtil.isNotEmpty(e.getAttributeValue("rel")) && e.getAttributeValue("rel").equalsIgnoreCase("stylesheet")){
					if(StringUtil.isNotEmpty(e.getAttributeValue("href"))){
						String href =e.getAttributeValue("href");
						if(StringUtil.isNotEmpty(href) && href.endsWith(".css")){
							String url = root + href;
							result = result + "\n" + ResourceUtil.readUrl(url);
						}else{
							result =  "import url(\""+href+"\")\n" + result ;
						}
					}
				}
					
				
			}
		}
		return result;
	}
	
	private static String getId(Map<String,String> mappings, Element tag){
		String tagName = tag.getName().toLowerCase();
		String id = "core:div";
		if(mappings.containsKey(tagName))
			id = mappings.get(tagName);
		
		return id;
	}
	
	
	private static void decorateContainer(Container div, Element tag,List<String> ids, String source){
		String name = null;
		String sclass = null;
		if(StringUtil.isNotEmpty(tag.getAttributeValue("name"))){
			name = tag.getAttributeValue("name");
			
		}else if(StringUtil.isNotEmpty(tag.getAttributeValue("id"))){
			name = tag.getAttributeValue("id");
			ids.add(name);
			div.addClass(name);
		}
		if(StringUtil.isNotEmpty(tag.getAttributeValue("class"))){
			if(name != null)
				name = tag.getAttributeValue("class");
			sclass = tag.getAttributeValue("class");
		}
		
		if(name == null)
			name = tag.getName();
		
		if(sclass == null){
			sclass = name;
		}
		try{
		
		for(Attribute a : tag.getAttributes()){
			if(!a.getName().equalsIgnoreCase("id") && !a.getName().equalsIgnoreCase("name") && !a.getName().equalsIgnoreCase("class")){
				if(a.getName().equalsIgnoreCase("src")){
					div.setAttribute("src",    source + a.getValue());
				}else
					div.setAttribute(a.getName(), a.getValue());
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		div.setName(name);
		if(sclass != null){
			div.addClass(sclass);
		}
	}
	
	private static String extractName(Element tag){
		String name = null;
		if(StringUtil.isNotEmpty(tag.getAttributeValue("name"))){
			name = tag.getAttributeValue("name");
			
		}else if(StringUtil.isNotEmpty(tag.getAttributeValue("id"))){
			name = tag.getAttributeValue("id");
			
		}
		if(StringUtil.isNotEmpty(tag.getAttributeValue("class"))){
			if(name != null)
				name = tag.getAttributeValue("class");
			
		}
		
		if(name == null)
			name = tag.getName();
		
		return name;
		
	}
	
	public static <T extends Object> T getBeanOfType(Class<T> clazz, ApplicationContext ctx){
		String[] names = ctx.getBeanNamesForType(clazz);
		
		if(names != null && names.length > 0){
			return (T)ctx.getBean(names[0]);
		}throw new RuntimeException("cannot find bean of type " + clazz.getName() + " configured in any application context");
	}
	private static Container addElement(Element elem, Container parent, Map<String,String> mappings, List<String> ids, List<Object> results, boolean isPg, String root, ApplicationContext ctx)throws Exception{
		
		
		//boolean pageContainer = (((StringUtil.isNotEmpty(elem.getAttributeValue("id")) && elem.getAttributeValue("id").contains("page")) || (StringUtil.isNotEmpty(elem.getAttributeValue("class")) && elem.getAttributeValue("class").contains("page"))) && !elem.getName().equalsIgnoreCase("body"));
		
		boolean pageContainer = StringUtil.isNotEmpty(elem.getAttributeValue("id")) && elem.getAttributeValue("id").equals("page")  && !elem.getName().equalsIgnoreCase("body");
		
		if(isPg)
			pageContainer = false;
		
		if(pageContainer)
			results.add(elem);
		
		
		List<Element> tags = elem.getChildElements();
		DesignableService service = getBeanOfType(DesignableService.class,ctx);
		
		DesignableFactory factory = null;
		
		if(!pageContainer)
			factory =service.getDesignable(getId(mappings, elem));
		else
			factory = service.getDesignable("portal:pagecontainer");
		
		Container  pc =  factory.getInstance();
		
	
		
		if(parent != null){
			parent.addChild(pc);
		}
		decorateContainer(pc, elem, ids,root);
		
		if(elem.getName().equalsIgnoreCase("p") || elem.getName().equalsIgnoreCase("h1") || elem.getName().equalsIgnoreCase("h2") || elem.getName().equalsIgnoreCase("h3") || elem.getName().equalsIgnoreCase("h4") || elem.getName().equalsIgnoreCase("h5") || elem.getName().equalsIgnoreCase("h6") ){
			//factory.applyAttribute(pc, "text", elem.getTextExtractor().toString());
			String text = elem.toString();
			text = text.substring(text.indexOf('>') +1, text.lastIndexOf('<')-1);
			text = text.replace("src=\"", "src=\"" + root);
			return pc.setText(text);
		}
		if(elem.getChildElements().size() == 0){
			//factory.applyAttribute(pc, "text", elem.getTextExtractor().toString());
			return pc.setText(elem.getTextExtractor().toString());
			//return pc;
		}
		
		pc.setText("");
		
		
			for(Element tag : tags){
				if(!tag.getName().startsWith("!"))
					addElement(tag, pc, mappings, ids, results,isPg,root,ctx);
			}
		
		
		return pc;
		
	}
	
	public static String generateXML(Container container, DesignableLayoutContainer parent){
		String desId = container.getAttribute("des-id");
		StringBuilder b = new StringBuilder();
		if(StringUtil.isNotEmpty(desId)){
			//the component is a designable.
			
			//1. we get all attributes
			//2. we get all styles
			//3. we get all events
			//4. we get all datasources
			
			//<casta:designable name="Main" uniqueId="core:verticallayout" layoutData="">
			
			
			String name = container.getName();
			String layoutData = "";
			if(parent != null){
				layoutData = container.getAttribute("layoutdata"); //parent.getPossibleLayoutData(container);
			}
			
			b.append("<casta:designable name=\""+name+"\" uniqueId=\""+desId+"\" layoutData=\""+layoutData+"\">");
			b.append(getAttributesXML(container));
			b.append(getStylesXML(container));
			
			
			
			b.append(getGroovyEventsXML(container));
			
			
			
			if(container instanceof DesignableLayoutContainer && !(container instanceof PageContainer)){
				
				//1. if current container is PageContainer, we do not do the children
				
				DesignableLayoutContainer desLayoutContainer = (DesignableLayoutContainer)container;
				List<DroppableSection> sections = desLayoutContainer.getSections();
				for(DroppableSection section : sections){
					String compId = section.getComponentId();
					Container c = desLayoutContainer.getDescendentById(compId);
					
					b.append(generateXML(c, desLayoutContainer));
					
				}	
			}
			
			if(container instanceof PortalContainer){
				b.append(getDatasourceXML((PortalContainer)container));
			}
			
			b.append("</casta:designable>");
			
		}
		
		return b.toString();
	}
	
	
	private static String getAttributesXML(Container container){
		String[] attributeNames = container.getAttributeNames();
		
		StringBuilder b = new StringBuilder();
		String datasource = null;
		for(String attributeName : attributeNames){
			if(!attributeName.equals("__path")&& !attributeName.equals("__oid") && !attributeName.equals("text") && !attributeName.equals("des-id") && !attributeName.equals("name") && !attributeName.equalsIgnoreCase("layoutData")){
				String attribute = container.getAttribute(attributeName);
				
				if(attributeName.equalsIgnoreCase("class")){
					if(attribute != null && attribute.trim().equalsIgnoreCase("des")){
						attribute = "";
					}
				}
				
				if(attribute != null && attribute.trim().length() > 0){
					b.append("<casta:attribute name=\"").append(attributeName).append("\"").append("><![CDATA[").append(attribute).append("]]></casta:attribute>");
					if(attributeName.equalsIgnoreCase("datasource")){
						datasource = attribute;
					}
				}
			}
			
			
		}
		
		String text = container.getText(false);
		if(datasource != null){
			text = "";
		}
		
		if(StringUtil.isNotEmpty(text.trim())){
			b.append("<casta:attribute name=\"").append("text").append("\"").append("><![CDATA[").append(text).append("]]></casta:attribute>");
		}
		//b.append("<casta:attribute name=\"").append("text").append("\"").append(" value=\"").append(text).append("\"/>");
		return b.toString();
	}
	
	
	private static String getStylesXML(Container container){
		String[] styleNames = container.getStyleNames();
		
		StringBuilder b = new StringBuilder();
		for(String styleName : styleNames){
			String style = container.getStyle(styleName);
			if(style != null && style.trim().length() > 0){
				b.append("<casta:style name=\"").append(styleName).append("\"").append(" value=\"").append(style).append("\"></casta:style>");
			}
		}
		return b.toString();
	}
	
	
	private static String getGroovyEventsXML(Container container){
		Map<Integer, List<Event>> events = container.getEvents();
		
		Iterator<Integer> eventTypes = events.keySet().iterator();
		StringBuilder b = new StringBuilder();
		while(eventTypes.hasNext()){
			Integer eventType = eventTypes.next();
			List<Event> lstEvent = events.get(eventType);
			String sEventType = EventUtil.getEventName(eventType);
			for(Event event : lstEvent){
				if(event instanceof GroovyEvent){
					GroovyEvent groovyEvent = (GroovyEvent)event;
					String template = groovyEvent.getTemplate();
					//<casta:event on="click" type="groovy">System.out.println("kureem is the best")</casta:event>
					b.append("<casta:event on=\""+sEventType+"\" type=\"groovy\">").append("<![CDATA[").append(template).append("]]></casta:event>");
				}
			}
		}
		
		return b.toString();
	}
	
	public static String getDatasourceXML(PortalContainer pc){
		
		List<Datasource> list = pc.getDatasources();
		StringBuilder b = new StringBuilder();
		DatasourceFactoryService service = SpringUtil.getBeanOfType(DatasourceFactoryService.class);
		for(Datasource d : list){
			
			DatasourceFactory factory = service.getDatasourceFactory(d.getFactoryId());
			String[] attributeNames =  factory.getRequiredAttributes();
			String name = d.getName();
			String desId = d.getFactoryId();
			b.append("<casta:datasource name=\""+name+"\" uniqueId=\""+desId+"\">");
			
			for(String attributeName : attributeNames){
				
				String attribute = d.getAttribute(attributeName);
				if(attribute != null && attribute.trim().length() > 0){
					b.append("<casta:attribute name=\"").append(attributeName).append("\"").append("><![CDATA[").append(attribute).append("]]></casta:attribute>");
				}
			}
			
			b.append("</casta:datasource>");
		}
		
		
		
		return b.toString();
	}
	
	
	
	public static Container getInstance(String uniqueId){
		DesignableService service = SpringUtil.getBeanOfType(DesignableService.class);
		DesignableFactory factory = service.getDesignable(uniqueId);
		return factory.getInstance();
	}
	
	
	
	
	
	public static Container buildContainer(InputStream xml, boolean editMode)throws Exception{
		return DesignableDTO.buildContainer(xml, editMode);
	}
	
	
	public static Container createSamplePage(String name, String  portalFile)throws Exception{
		RepositoryService service = SpringUtil.getBeanOfType(RepositoryService.class);
		Container page = getInstance("core:xylayout");
		page.addChild(getInstance("core:h1"));
		page.getChildByIndex(0).setText(name);
		page.setName(name);
		page.setAttribute("Text", ""); 
		page.setStyle("margin", "0");
		page.setStyle("padding", "0");
		page.setStyle("width", "100%");
		page.setStyle("min-height", "500px");
		page.setStyle("background-color", "beige");
		
		Directory file = futil.getDirectory(portalFile + "/pages");
		
		BinaryFile bf = file.createFile(name, BinaryFile.class);//new BinaryFile();

		bf.write(DesignableUtil.generateXML(page, null).getBytes());
		bf.save();
		
		page.setAttribute("pagepath", bf.getAbsolutePath());
		return page;
	}
	
	public static BinaryFile generateSite(Application app, String username, String logo, String bodyBack,String banner, String menu,String companyName )throws Exception{
		String portalName = "ecommerce.ptl";
		RepositoryService service = SpringUtil.getRepositoryService();
		String rootDir = "/root/users/" + username;
		Directory dir = service.getDirectory(rootDir ,username);
		
		Directory portalData = dir.createFile(portalName.replace(".ptl", "") + "_files", Directory.class);
		portalData.makeOwner(username);
		
		Directory templates = portalData.createFile("templates", Directory.class);
		templates.makeOwner(username);
		
		
		String bannerLocation = putBanner(banner, portalData);
		
		putMenu(app, menu, templates);
		//copy templates
		String[] files = new String[]{"Copyright.xhtml"};
		String footer = "";
		for(String  f : files){
			byte[] bytes = IOUtil.getStreamContentAsBytes(Thread.currentThread().getContextClassLoader().getResourceAsStream(f));
			footer = ResourceUtil.getDownloadURL("ecm",copyFile(templates, bytes, f).getAbsolutePath());
		}
		
		//copy portal
		String emptyPortal = IOUtil.getStreamContentAsString(Thread.currentThread().getContextClassLoader().getResourceAsStream("empty-portal.xml"));
		emptyPortal = emptyPortal.replace("${username}", username);
		emptyPortal = emptyPortal.replace("${footer}", footer);
		emptyPortal = emptyPortal.replace("${logo}", logo);
		emptyPortal = emptyPortal.replace("${companyName}", companyName);
		emptyPortal = emptyPortal.replace("${banner}", bannerLocation);
		emptyPortal = emptyPortal.replace("${dir}", portalData.getAbsolutePath());
		
		BinaryFile ptl =copyFile(dir, emptyPortal.getBytes(), portalName);
		Directory pages = ptl.createFile("pages", Directory.class);
		pages.makeOwner(username);
		
		
		//save home page
		String home = IOUtil.getStreamContentAsString(Thread.currentThread().getContextClassLoader().getResourceAsStream("home.xml"));
		
		copyFile(pages,home.getBytes(), "home");
		
		dir.save();
		return ptl;
	}
	
	private static String putBanner(String banner, Directory portalData)throws Exception{
		byte[] bannerBytes = ResourceUtil.readUrlBinary(banner);
		String bannerName = "banner.jpg";
		if(banner.endsWith("png")){
			bannerName = "banner.png";
		}
		BinaryFile fBanner = copyFile(portalData , bannerBytes, bannerName);
		
		String bannerLocation = ResourceUtil.getDownloadURL("ecm",fBanner.getAbsolutePath());
		return bannerLocation;
	}
	
	private static void putMenu(Application app, String menu, Directory templates)throws Exception{
		
		String contextPath = app.getContextPath();
		String serverPort = app.getServerPort();
		String servaerName = app.getServerName();
		String menuUrl = "http://" + servaerName + ":" + serverPort  + contextPath + "/designer/menu/vertical/contents/" + menu + "/preview.html";
		
		byte[] menuBytes = IOUtil.getStreamContentAsBytes(new URL(menuUrl).openStream());
		copyFile(templates, menuBytes, "Menu.xhtml");
	}
	
	public static BinaryFile generateSite(Application app, String banner, String menu, String portalType, String rootDir, String portalName)throws Exception{
		String username = Util.getRemoteUser();
		RepositoryService service = SpringUtil.getRepositoryService();
		Directory dir = service.getDirectory(rootDir ,username);
		
		Directory portalData = dir.createFile(portalName.replace(".ptl", "") + "_files", Directory.class);
		portalData.makeOwner(username);
		
		Directory templates = portalData.createFile("templates", Directory.class);
		templates.makeOwner(username);
		
		
		
		
		
		//copy templates
		String[] files = new String[]{"TitleWithList.xhtml", "TitleWithParagraph.xhtml", "TitleWithImage.xhtml", "Article.xhtml", "Menu9.xhtml", "Copyright.xhtml"};

		for(String  f : files){
			byte[] bytes = IOUtil.getStreamContentAsBytes(Thread.currentThread().getContextClassLoader().getResourceAsStream(f));
			copyFile(templates, bytes, f);
		}
		
		//copy banner
		String contextPath = app.getContextPath();
		String serverPort = app.getServerPort();
		String servaerName = app.getServerName();
		String location = banner;
		//URL url = new URL(location);
		
		byte[] bannerBytes = ResourceUtil.readUrlBinary(location);
		String bannerName = "banner.jpg";
		if(location.endsWith("png")){
			bannerName = "banner.png";
		}
		BinaryFile fBanner = copyFile(portalData , bannerBytes, bannerName);
		
		String bannerLocation = ResourceUtil.getDownloadURL("ecm",fBanner.getAbsolutePath());
		
		
		byte[] imgByte = IOUtil.getStreamContentAsBytes(Thread.currentThread().getContextClassLoader().getResourceAsStream("org/castafiore/designer/newportal/image2.jpg"));
		copyFile(portalData , imgByte, "sample-img.jpg");
		
		
		String menuUrl = "http://" + servaerName + ":" + serverPort  + contextPath + "/designer/menu/vertical/contents/" + menu + "/preview.html";
		
		byte[] menuBytes = IOUtil.getStreamContentAsBytes(new URL(menuUrl).openStream());
		copyFile(templates, menuBytes, "Menu.xhtml");
		
		//copy portal
		String emptyPortal = IOUtil.getStreamContentAsString(Thread.currentThread().getContextClassLoader().getResourceAsStream(portalType));
		
		//int[] pageIndx = new int[]{2,2,1,1,3,2,2,1,1,2,1,2,2,2,1,3,4,3,2,4,}
		
		//Container uiEmptyPortal = buildPortal(portaldType, bannerLocation, pageIndex, portalData.getAbsolutePath()); 
		
		
		emptyPortal = emptyPortal.replace("${user}", Util.getRemoteUser());
		//emptyPortal = emptyPortal.replace("${menu}", menu);
		emptyPortal = emptyPortal.replace("${dir}", portalData.getAbsolutePath());
		emptyPortal = emptyPortal.replace("${banner}", bannerLocation);
		BinaryFile ptl =copyFile(dir, emptyPortal.getBytes(), portalName);
		Directory pages = ptl.createFile("pages", Directory.class);
		pages.makeOwner(username);
		
		
		//save home page
		String home = IOUtil.getStreamContentAsString(Thread.currentThread().getContextClassLoader().getResourceAsStream("home.xml"));
		home = home.replace("${user}", Util.getRemoteUser());
		
		copyFile(pages,home.getBytes(), "home");
		
		dir.save();
		return ptl;
	}
	
	
	
	private static Container buildPortal(String type, String bannerUrl,  String path)throws Exception{
		Source source = new Source(new URL("http://www.primarycss.com/Layout.aspx?jib=" + type));
		DesignableService service = SpringUtil.getBeanOfType(DesignableService.class);
		
		DesignableFactory portalFactory = service.getDesignable("portal:portalcontainer");
		DesignableFactory divFactory = service.getDesignable("core:xylayout");
		//DesignableFactory pageFactory = service.getDesignable("portal:pagecontainer");
		
		for(Element e : source.getAllElements("div")){
			if(e.getAttributeValue("class").equalsIgnoreCase("templateholder")){
				
				Container portal = portalFactory.getInstance();
				portal.setAttribute("definitionpath", "castafiore/resource?spec=ecm:"+path+"/default.ptl");
				portal.setName("Site");
			
				for(Element ee : e.getAllElements("div")){
					String cls = ee.getAttributeValue("class");
					if(cls.contains("header")){
						Container div = divFactory.getInstance();
						div.setName("Header");
						div.setStyle("background-url", "url('"+bannerUrl+"')");
						portal.addChild(div);
					}else{
						Container div = divFactory.getInstance();
						div.setName(cls);
						portal.addChild(div);
					}
				}
				return portal;
			}
		}
		return null;
	}
	
	private static BinaryFile copyFile(Directory fDest, byte[] data, String name)throws Exception{
		BinaryFile bf = fDest.createFile(name,BinaryFile.class);
		bf.write(data);
		return bf;
	}
	
	public static void createNewPortal(Directory intoDir, String name)throws Exception{
		
		String emptyPortal = IOUtil.getStreamContentAsString(Thread.currentThread().getContextClassLoader().getResourceAsStream("empty-portal.xml"));
		emptyPortal = emptyPortal.replace("${user}", Util.getRemoteUser());
		copyFile(intoDir, emptyPortal.getBytes(), name);
		
		Directory fu = futil.getDirectory(intoDir.getAbsolutePath() + "/" + name);
		Directory pages = fu.createFile("pages", Directory.class);
		
		pages.makeOwner(Util.getRemoteUser());
		
		
		//save home page
		String home = IOUtil.getStreamContentAsString(Thread.currentThread().getContextClassLoader().getResourceAsStream("home.xml"));
		home = home.replace("${user}", Util.getRemoteUser());
		
		copyFile(pages, home.getBytes(), "home");
	}
	
public static void main(String[] args)throws Exception {
		
		//downloadFreecss();
	//unzipTemplates();
	generateFreecssDisk("blackcoffee");

	}

}
