package org.castafiore.designer.marshalling;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.castafiore.designable.InvalidDesignableException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DatasourceDTO implements Serializable{

	private String name;

	private String uniqueId;

	private Map<String, String> attributes = new HashMap<String, String>();

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
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public static DatasourceDTO getDtoFromNode(Node n){
		if(n.getAttributes().getNamedItem("uniqueId") == null){
			throw new InvalidDesignableException("Error: every casta:designable must have a mandatory attribute <<uniqueId>>, which is a valid designable uniqueId configured");
		}
		
		if(n.getAttributes().getNamedItem("name") == null){
			throw new InvalidDesignableException("Error: every casta:designable must have a mandatory attribute <<name>>, which will be the name of the container");
		}
		
		String uniqueId = n.getAttributes().getNamedItem("uniqueId").getTextContent();
		String name = n.getAttributes().getNamedItem("name").getTextContent();
		DatasourceDTO dto = new DatasourceDTO();
		 
		dto.setName(name);
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
			}
		}
		
		return dto;
	}
}
