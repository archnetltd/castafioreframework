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

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.castafiore.designable.DesignableFactory;
import org.castafiore.designable.InvalidDesignableException;
import org.castafiore.designable.layout.DesignableLayoutContainer;
import org.castafiore.designable.portal.PortalContainer;
import org.castafiore.designer.Studio;
import org.castafiore.designer.dataenvironment.Datasource;
import org.castafiore.designer.dataenvironment.DatasourceFactory;
import org.castafiore.designer.dataenvironment.DatasourceFactoryService;
import org.castafiore.designer.service.DesignableService;
import org.castafiore.ui.Container;
import org.castafiore.ui.LayoutContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.events.Event;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.EventUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DesignableDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private String uniqueId;

	// this is the layoutData it uses when adding to parent.
	private String layoutData;

	private Map<String, String> attributes = new HashMap<String, String>();

	private List<EventDTO> events = new ArrayList<EventDTO>();
	
	private List<DatasourceDTO> datasources = new ArrayList<DatasourceDTO>();

	private List<DesignableDTO> children = new ArrayList<DesignableDTO>();
	
	

	private Map<String, String> styles = new HashMap<String, String>();
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getLayoutData() {
		return layoutData;
	}

	public void setLayoutData(String layoutData) {
		this.layoutData = layoutData;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	public List<EventDTO> getEvents() {
		return events;
	}

	public void setEvents(List<EventDTO> events) {
		this.events = events;
	}

	public List<DesignableDTO> getChildren() {
		return children;
	}

	public void setChildren(List<DesignableDTO> children) {
		this.children = children;
	} 

	public Map<String, String> getStyles() {
		return styles;
	}

	public void setStyles(Map<String, String> styles) {
		this.styles = styles;
	}
	
	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
	
	public static Container buildContainer(InputStream xml, boolean editMode)throws Exception {
		Container c = buildContainer(buildContainer_(xml), editMode);
		return c;
		
		//return buildContainer(buildContainer_(xml), editMode);
	}

	
	public static Datasource buildDatasource(DatasourceDTO dto, boolean editMode){
		DatasourceFactoryService service = BaseSpringUtil.getBeanOfType(DatasourceFactoryService.class);

		DatasourceFactory factory = service.getDatasourceFactory(dto.getUniqueId());
		
		Datasource d = factory.getInstance();
		d.setName(dto.getName());
		Iterator<String> iter = dto.getAttributes().keySet().iterator();
		while(iter.hasNext()){
			String next = iter.next();
			String val = dto.getAttributes().get(next);
			d.setAttribute(next, val);
		}
		
		
		for(String attr : factory.getRequiredAttributes()){
			String value = dto.getAttributes().get(attr);
			factory.applyAttribute(d, attr, value);
		}
		
		factory.refresh(d);
		
		return d;

	}
	
	public static Container buildContainer(DesignableDTO dto, boolean editMode) {

		DesignableService service = BaseSpringUtil.getBeanOfType(DesignableService.class);

		DesignableFactory designable = service.getDesignable(dto.getUniqueId());

		Container instance = designable.getInstance();
		//instance.setAttribute("des-id", dto.getUniqueId());

		ComponentUtil.applyStyles(instance, dto.getStyles());

		Studio.applyAttributes(instance, dto.getAttributes());
		
		instance.setName(dto.getName());

		for(EventDTO event : dto.getEvents()){
			int on = event.getOn();
			Event evt = event.getEvent();
			
			instance.addEvent(evt, on);
		}
		
		

		//designable.initialise(instance, dto.getAttributes());
		
		String[] requiredAttrs = instance.getAttributeNames();
		if(requiredAttrs != null){
			for(String attr : requiredAttrs){
				String attrValue = instance.getAttribute(attr);
				//instance.setAttribute(attr, attrValue);
				designable.applyAttribute(instance, attr, attrValue);
			}
		}
	
		if(instance instanceof PortalContainer){
			for(DatasourceDTO d : dto.datasources){
				Datasource dd = buildDatasource(d, editMode);
				((PortalContainer)instance).addDatasource(dd);
			}
		}
		
		
		if (instance instanceof LayoutContainer) {
			List<DesignableDTO> children = dto.getChildren();
			for (DesignableDTO cDto : children) {
				Container cInstance = DesignableDTO.buildContainer(cDto, editMode);
				((LayoutContainer) instance).addChild(cInstance, cDto.getLayoutData());
				if(editMode){
					if(instance instanceof DesignableLayoutContainer){
						((DesignableLayoutContainer)instance).onAddComponent(cInstance);
					}
				}
			}
		}
		
//		if(!editMode){
//			try{
//			ExpressionEvaluatorUtil.evaluate(instance);
//			}catch(Exception e){
//				throw new UIException ("error evaluating expression in component " + instance.getName(),e);
//			}
//		}
		return instance;
		// Designable design
	}

	private static DesignableDTO getDtoFromNode(Node n){
		
		if(n.getAttributes().getNamedItem("uniqueId") == null){
			throw new InvalidDesignableException("Error: every casta:designable must have a mandatory attribute <<uniqueId>>, which is a valid designable uniqueId configured");
		}
		if(n.getAttributes().getNamedItem("layoutData") == null){
			throw new InvalidDesignableException("Error: every casta:designable must have a mandatory attribute <<layoutData>>, which is a valid layoutData to be used when adding in the parentContainer. Can leave empty if is root");
		}
		
		if(n.getAttributes().getNamedItem("name") == null){
			throw new InvalidDesignableException("Error: every casta:designable must have a mandatory attribute <<name>>, which will be the name of the container");
		}
		String uniqueId = n.getAttributes().getNamedItem("uniqueId").getTextContent();
		String layoutData = n.getAttributes().getNamedItem("layoutData").getTextContent();
		String name = n.getAttributes().getNamedItem("name").getTextContent();
		DesignableDTO dto = new DesignableDTO();
		
		dto.setName(name);
		
		//layout data
		dto.setLayoutData(layoutData);
		
		//unique id
		dto.setUniqueId(uniqueId);
		
		NodeList nodeList = n.getChildNodes();
		
		for(int i= 0; i < nodeList.getLength(); i ++){
			Node cNode = nodeList.item(i);
			
			String nodeName = cNode.getNodeName();
			//attributes
			if(nodeName.equalsIgnoreCase("casta:attribute")){
				//text has precedence over attribute
				String[] kv = getKeyValue(cNode);
				dto.getAttributes().put(kv[0], kv[1]);
				
			}else if(nodeName.equalsIgnoreCase("casta:style")){
				String[] kv = getKeyValue(cNode);
				dto.getStyles().put(kv[0], kv[1]);
			}else if(nodeName.equalsIgnoreCase("casta:event")){
				//handle event to do later
				EventDTO eDto = new EventDTO();
				
				String eventOn = cNode.getAttributes().getNamedItem("on").getTextContent();
				String eventType = cNode.getAttributes().getNamedItem("type").getTextContent();
				
				int on = EventUtil.getEventType(eventOn);
				
				eDto.setType(eventType);
				eDto.setOn(on);
				eDto.setScript(cNode.getTextContent());
				
				if(eventType.equalsIgnoreCase(EventDTO.TYPE_MACRO)){
					if(cNode.getAttributes().getNamedItem("macroUniqueId") == null){
						throw new InvalidDesignableException("The type of an event is macro, but no macroUniqueId was configured:" + cNode.getTextContent());
					}
					String macroId = cNode.getAttributes().getNamedItem("macroUniqueId").getTextContent();
					eDto.setMacroUniqueId(macroId);
				}
				dto.getEvents().add(eDto);
				
			}else if(nodeName.equalsIgnoreCase("casta:datasource")){
				DatasourceDTO datasource = DatasourceDTO.getDtoFromNode(cNode);
				dto.datasources.add(datasource);
			}else if(nodeName.equalsIgnoreCase("casta:designable")){
				
				dto.getChildren().add(getDtoFromNode(cNode));
			}
		}
		return dto;
	}
	
	
	private static String[] getKeyValue(Node cNode){
		String attrName = cNode.getAttributes().getNamedItem("name").getTextContent();
		String value = "";
		Node valueAttr = cNode.getAttributes().getNamedItem("value");
		if(valueAttr != null){
			value = valueAttr.getTextContent();
		}else{
			value = cNode.getTextContent();
		}
		
		return new String[]{attrName, value};
	}
	
	private static DesignableDTO buildContainer_(InputStream xml)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(xml);
		doc.getDocumentElement().normalize();
		NodeList nodeList = doc.getChildNodes();
		
		for(int i = 0; i < nodeList.getLength(); i ++){
			Node n = nodeList.item(i);
			
			String name = n.getNodeName();
			if(name.equals("casta:designable")){
				DesignableDTO dto = getDtoFromNode(n);
				//System.out.println(dto);
				return dto;
			}
		}
		
		return null;
	}
	
	public static void main(String[] args)throws Exception {
		Container c = buildContainer(Thread.currentThread().getContextClassLoader().getResourceAsStream("org/castafiore/designer/marshalling/NewFile.xml"), true);
		
		//Container c = buildContainer(dto);
		
		//System.out.println(c);
	}

}
