package org.castafiore.designer.helpers;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.stream.FileImageInputStream;

import net.htmlparser.jericho.Attribute;
import net.htmlparser.jericho.Attributes;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

import org.castafiore.designable.DesignableFactory;
import org.castafiore.designer.service.DesignableService;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.scripting.TemplateComponent;
import org.castafiore.utils.StringUtil;

public class TemplateHelper {
	
	
	private static List<String> primitive = new ArrayList<String>();
	
	static{
		primitive.add("label");
		primitive.add("a");
		primitive.add("p");
		primitive.add("h1");
		primitive.add("h2");
		primitive.add("h3");
		primitive.add("h4");
		primitive.add("h5");
		primitive.add("h6");
		primitive.add("hr");
		primitive.add("image");
		primitive.add("ul");
		primitive.add("li");

	}
	public static void main(String[] args) throws Exception {
		//populateTemplate(null);
		
		Source source = new Source(new FileInputStream("C:\\java\\emall\\website\\Noname2.html"));
		System.out.println( source.getChildElements().get(0).getContent().toString());
	}
	
	public static void populateTemplate(TemplateComponent component){
		String template = component.getTemplate();
		
		component.getChildren().clear();
		component.setRendered(false);
		Source source = new Source(template);
		
		List<Element> children = source.getChildElements();
		
		Element root = children.get(0);
		int count = 0;
		for(Element e : root.getAllElements()){
			String name = e.getAttributeValue("name");
			if(count == 0){
				count++;
				Attributes attrs = e.getAttributes();
				 for(int i = 0; i < attrs.size(); i ++){
					 Attribute  attr = attrs.get(i);
					 String attrName = attr.getName();
					 String value = attr.getValue();
					 if(attrName.equalsIgnoreCase("name")){
						 component.setName(value);
					 }else if(!attrName.equalsIgnoreCase("id") && !attrName.equalsIgnoreCase("style")){
						 component.setAttribute(attrName, value);
					 }else if(attrName.equalsIgnoreCase("style")){
						 String[] styles = StringUtil.split(value, ";");
						 for(String style : styles){
							 String[] kv = StringUtil.split(style, ":");
							 if(kv != null && kv.length == 2){
								 component.setStyle(kv[0], kv[1]);
							 }
						 }
					 }
				 }
				continue;
			}
			if(name != null && name.length() > 0){
				//String tag = e.getStartTag().getName();
				
				//System.out.println("added tag :" + tag + " of name:" + name);
				
				Container instance = getContainer(e);
				if(instance != null)
					component.addChild(instance);
				
			}
			
		}
	}
	
	
	public static Container getContainer(Element element){
		String tag = element.getStartTag().getName().toLowerCase();
		String uid = element.getAttributeValue("uid");
		if(primitive.contains(tag)){
			uid = "core:" + tag;
		}else if(tag.equals("input")){
			String type = element.getAttributeValue("type");
			if(type.equalsIgnoreCase("text") || !StringUtil.isNotEmpty(type)){
				uid = "core:textbox";
			}else if(type.equalsIgnoreCase("password")){
				uid= "core:password";
			}else if(type.equalsIgnoreCase("checkbox")){
				uid="core:checkbox";
			}else if(type.equalsIgnoreCase("radio")){
				uid="core:radio";
			}else if(type.equalsIgnoreCase("date")){
				uid="core:datepicker";
			}else if(type.equalsIgnoreCase("color")){
				uid="core:colorpicker";
			}else if(type.equalsIgnoreCase("button")){
				uid="core:button";
			}else if(type.equalsIgnoreCase("submit")){
				uid="core:button";
			}
		}else if(tag.equalsIgnoreCase("select")){
			uid = "core:select";
		}else if(tag.equalsIgnoreCase("textarea")){
			String type = element.getAttributeValue("type");
			if(StringUtil.isNotEmpty(type)){
				uid = "core:textarea";
			}else if(type.equalsIgnoreCase("richtextarea")){
				uid="core:richtextarea";
			}
		}else if(tag.equalsIgnoreCase("button")){
			uid="core:button";
		}else if(tag.equalsIgnoreCase("div")){
			uid = element.getAttributeValue("uid");
			if(!StringUtil.isNotEmpty(uid)){
				uid="core:div";
			}
		}else if(tag.equalsIgnoreCase("img")){
			uid="core:image";
		}
		
		if(StringUtil.isNotEmpty(uid)){
			 DesignableFactory factory = SpringUtil.getBeanOfType(DesignableService.class).getDesignable( uid);
			 Container instance = factory.getInstance();
			 
			 Attributes attrs = element.getAttributes();
			 for(int i = 0; i < attrs.size(); i ++){
				 Attribute  attr = attrs.get(i);
				 String attrName = attr.getName();
				 String value = attr.getValue();
				 if(attrName.equalsIgnoreCase("name")){
					 instance.setName(value);
				 }else if(!attrName.equalsIgnoreCase("id") && !attrName.equalsIgnoreCase("style")){
					 factory.applyAttribute(instance, attrName, value);
				 }else if(attrName.equalsIgnoreCase("style")){
					 String[] styles = StringUtil.split(value, ";");
					 for(String style : styles){
						 String[] kv = StringUtil.split(style, ":");
						 if(kv != null && kv.length == 2){
							 instance.setStyle(kv[0], kv[1]);
						 }
					 }
				 }
			 }
			 
			 String text = element.getContent().toString();
			 if(StringUtil.isNotEmpty(text))
				 instance.setText(text);
			 
			 return instance;
			 
		}else{
			//throw new UIException("Unable to identify what instance to put for tag :" + element.getStartTag().getName());
		}
		return null;
	}

}
