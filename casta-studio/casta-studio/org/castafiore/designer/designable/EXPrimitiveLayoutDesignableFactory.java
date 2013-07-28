package org.castafiore.designer.designable;

import java.util.Iterator;
import java.util.Map;

import org.castafiore.beans.info.IBeanInfo;
import org.castafiore.designable.AbstractDesignableFactory;
import org.castafiore.designer.layout.EXDroppablePrimitiveTagLayoutContainer;
import org.castafiore.ui.Container;

public class EXPrimitiveLayoutDesignableFactory extends AbstractDesignableFactory implements IBeanInfo{

	private String tagName;
	
	private Map<String, String> defaultStyles;
	
	private Map<String, String> defaultAttributes;
	
	private String defaultText;
	
	public EXPrimitiveLayoutDesignableFactory() {
		super("EXPrimitiveLayoutDesignableFactory");
	}
	
	@Override
	public String getCategory() {
		return "Primitive Layout";
	}

	@Override
	public Container getInstance() {
		//supported tags : div, ul, ol, li, table, tr, td,
		Container container = new EXDroppablePrimitiveTagLayoutContainer(tagName, tagName);			
		applyStyles(container);
		applyAttributes(container);
		if(defaultText != null){
			container.setText(defaultText);
		}else{
			container.setText(tagName);
		}
		container.setText("");
		return container;
	}

	protected void applyStyles(Container container){
		if(defaultStyles != null){
			Iterator<String> is = defaultStyles.keySet().iterator();
			while(is.hasNext()){
				String style = is.next();
				container.setStyle(style, defaultStyles.get(style));
			}
		}
	}
	protected void applyAttributes(Container container){
		if(defaultAttributes != null){
			Iterator<String> is = defaultAttributes.keySet().iterator();
			while(is.hasNext()){
				String attr = is.next();
				container.setAttribute(attr, defaultAttributes.get(attr));
			}
		}
	}

	public String getUniqueId() {
		return "core:" + tagName;
	}
	
	public String[] getRequiredAttributes(){
		if(tagName.equalsIgnoreCase("a")){
			return new String[]{"text", "href"};
		}else if(tagName.equalsIgnoreCase("li") || tagName.equalsIgnoreCase("td")){
			
			return new String[]{"text"};
		}else{
			return null;
		}
		
	}
	
	public void applyAttribute(Container c, String attributeName, String attributeValue){
		if(attributeName.equalsIgnoreCase("text")){
			c.setText(attributeValue);
		}
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public Map<String, String> getDefaultStyles() {
		return defaultStyles;
	}

	public void setDefaultStyles(Map<String, String> defaultStyles) {
		this.defaultStyles = defaultStyles;
	}

	public Map<String, String> getDefaultAttributes() {
		return defaultAttributes;
	}

	public void setDefaultAttributes(Map<String, String> defaultAttributes) {
		this.defaultAttributes = defaultAttributes;
	}

	public String getDefaultText() {
		return defaultText;
	}

	public void setDefaultText(String defaultText) {
		this.defaultText = defaultText;
	}

	public String[] getKeys() {
		return new String[] {"description"};
	}

	public String getSupportedUniqueId() {
		return getUniqueId();
	}

	public String getInfoAttribute(String key) {
		return "Creates a simple " + getTagName();
	}

}
