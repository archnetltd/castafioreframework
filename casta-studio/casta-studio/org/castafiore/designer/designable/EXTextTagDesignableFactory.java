package org.castafiore.designer.designable;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.collections.map.ListOrderedMap;
import org.castafiore.beans.info.IBeanInfo;
import org.castafiore.designable.AbstractDesignableFactory;
import org.castafiore.designer.EXTextFileEditor;
import org.castafiore.designer.config.ConfigForm;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.utils.IOUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.futil;
import org.castafiore.wfs.types.BinaryFile;

public class EXTextTagDesignableFactory extends AbstractDesignableFactory implements IBeanInfo {

	private String tagName;
	
	private Map<String, String> defaultStyles;
	
	private Map<String, String> defaultAttributes;
	
	private String defaultText;
	
	public EXTextTagDesignableFactory() {
		super("EXTextTagDesignableFactory");
	}
	
	@Override
	public String getCategory() {
		return "Text components";
	}

	@Override
	public Container getInstance() {
		//supported tags : span, label, p, h1, h2, h3, h4, a
		Container container = new EXContainer(tagName, tagName);
		applyStyles(container);
		applyAttributes(container);
		if(defaultText != null){
			container.setText(defaultText);
		}else{
			container.setText(tagName);
		}
		//container.setAttribute("datasource", "none");
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
			return new String[]{"href", "target"};
		}else{
			return new String[]{ };
		}
		
	}
	
	public void applyAttribute(Container c, String attributeName, String attributeValue){
//		if(attributeName.equalsIgnoreCase("text")){
//			c.setText(attributeValue);
//		}
//		if(attributeName.equalsIgnoreCase("datasource")){
//			if(attributeValue != null && attributeValue.trim().length() > 0 && !attributeValue.equalsIgnoreCase("none")){
//				String txt = futil.getTextFromBinaryUrl(attributeValue);
//				c.setText(txt);
//			}
//		}
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
	
	public Map<String, ConfigForm> getAdvancedConfigs(){
		Map<String, ConfigForm> forms = super.getAdvancedConfigs();
		forms.put("text", new EXTextFileEditor("texteditor"));
		return forms;
	}
}
