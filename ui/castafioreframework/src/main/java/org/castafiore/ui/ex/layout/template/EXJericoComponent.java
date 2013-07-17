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
 package org.castafiore.ui.ex.layout.template;

import java.util.List;
import java.util.Map;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

import org.castafiore.ui.UIException;
import org.castafiore.ui.ex.Compiler;
import org.castafiore.utils.JavascriptUtil;
import org.castafiore.utils.StringUtil;

/**
 * Implementation of {@link EXTemplateComponent} that extracts a portion of an html template <br>
 * It uses Jericho library to handle the parsing of the html template and extracting the required portion
 * @author arossaye
 *
 */
public class EXJericoComponent extends EXTemplateComponent implements Compiler {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;

	
	/**
	 * Constructs an instance of this class
	 * @param name The name of the component
	 * @param templateLocation The location of the template
	 * @param selector The selector which will be the root tag of the template
	 * @param index index of returned result of the selector if ever there are more than one
	 */
	public EXJericoComponent(String name, String templateLocation, String selector, int index) {
		super(name, templateLocation);
		setAttribute("selector", selector);
		setAttribute("elemindex", index + "");
	}
	
	
	/**
	 * The selector used
	 * @return The selector
	 */
	public String getSelector() {
		
		return getAttribute("selector");
	}

	/**
	 * Sets the selector
	 * @param selector The selector
	 */
	public void setSelector(String selector) {
		setAttribute("selector", selector);
		setRendered(false);
	}

	/**
	 * Returns the element index used
	 * @return The element index
	 */
	public int getIndex() {
		return Integer.parseInt(getAttribute("elemindex"));
		
	}

	/**
	 * 
	 * @param index
	 */
	public void setIndex(int index) {
		setAttribute("elemindex", index + "");
		setRendered(false);
	}

	public Compiler getCompiler() {
		return this;
	}
	
	public String getId(){
		if(StringUtil.isNotEmpty(id)){
			return id;
		}else{
			return super.getId();
		}
	}

	public String compile(String template, Map<String, Object> context)
			throws Exception {
		
		String selector = getSelector();
		int index =  getIndex();
		if(StringUtil.isNotEmpty(selector)){
			Source source = new Source(template);
			Element elem = null;
			if(selector.startsWith("#")){
				 elem = source.getElementById(selector.substring(1));
				 id = selector.substring(1);
				 
			}else if(selector.startsWith(".")){
				List<Element> elems = source.getAllElementsByClass(selector.substring(1));
				if(elems.size() > index){
					elem = elems.get(index);
					id = elem.getAttributeValue("id");
				}
			}else{
				//source.getAllStartTags()
			}
			if(elem != null){
				String  s =elem.toString();
				return JavascriptUtil.javaScriptEscape(  s );
			}else{
				throw new UIException("unable to find element for selector " + selector + " in template " + getTemplateLocation());
			}
			//if(elem.getName().equals(""))
			
			
		}else{
			return JavascriptUtil.javaScriptEscape(template);
		}
		
	}

}
