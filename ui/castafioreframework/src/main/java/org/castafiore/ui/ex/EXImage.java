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
 package org.castafiore.ui.ex;

import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.js.JArray;
import org.castafiore.ui.js.JMap;
import org.castafiore.utils.StringUtil;

public class EXImage extends EXContainer {
	
	private JArray defaultTags = null;
	
	private JArray autoCompleteValues = null;

	public EXImage(String name) {
		super(name, "img");
	}
	
	
	
	
	public void setTaggable(Boolean b){
		setAttribute("taggable", b.toString());
	}
	
	
	private Boolean getNullSafeBooleanAttribute(String attr, Boolean default_){
		
		try{
			return StringUtil.isNotEmpty(getAttribute(attr))? Boolean.parseBoolean(getAttribute(attr)) : default_;
		}catch(Exception e){
			return default_;
		}
		
	}
	
private Integer getNullSafeIntegerAttribute(String attr, Integer default_){
		
		try{
			return StringUtil.isNotEmpty(getAttribute(attr))? Integer.parseInt(getAttribute(attr)) : default_;
		}catch(Exception e){
			return default_;
		}
		
	}
	
	private String getNullSafeStringAttribute(String attr, String default_){
		
		try{
			return StringUtil.isNotEmpty(getAttribute(attr))? getAttribute(attr) : default_;
		}catch(Exception e){
			return default_;
		}
		
	}
	public Boolean isTaggable(){
		return getNullSafeBooleanAttribute("taggable", false);
	}
	
	
	
	public void setDeleteTag(Boolean b){
		setAttribute("canDeleteTag", b.toString());
	}
	
	
	public Boolean canDeleteTag(){
		return getNullSafeBooleanAttribute("canDeleteTag", null);
	}
	
	public void setDefaultTags(JArray tags){
		this.defaultTags = tags;
	}
	
	
	public JArray getDefaultTags(){
		return defaultTags;
	}
	
	public Integer getMinWidth(){
		return getNullSafeIntegerAttribute("minWidth", null);
	}
	
	
	public void setMinWidth(int width){
		setAttribute("minWidth", width + "");
	}
	
	
	public void setDefaultWidth(int width){
		setAttribute("defaultWidth", width + "");
	}
	
	
	public Integer getDefaultWidth(){
		return getNullSafeIntegerAttribute("defaultWidth", null);
	}
	
	public void setDefaultHeight(int height){
		setAttribute("defaultHeight", height + "");
	}
	
	
	
	
	public Integer getDefaultHeight(){
		return getNullSafeIntegerAttribute("defaultHeight", null);
	}
	
	public void setMinHeight(int width){
		setAttribute("minHeight", width + "");
	}
	
	
	public Integer getMinHeight(){
		return getNullSafeIntegerAttribute("minHeight", null);
	}
	
	
	public JArray getAutoCompleteValues(){
		return autoCompleteValues;
	}
	
	public void setAutoCompleteValues(JArray values){
		this.autoCompleteValues = values;
	}
	
	
	public Boolean isAutoComplete(){
		return getNullSafeBooleanAttribute("autoComplete", false);
	}
	
	
	public void setAutoComplete(Boolean a){
		setAttribute("autoComplete", a + "");
	}
	
	
	public Integer getMaxHeight(){
		return getNullSafeIntegerAttribute("maxHeight", null);
	}
	
	public void setMaxHeight(int h){
		setAttribute("maxHeight", h + "");
	}
	
	public Integer getMaxWidth(){
		return getNullSafeIntegerAttribute("maxWidth", null);
	}
	
	public void setMaxWidth(Integer w){
		setAttribute("maxWidth", w + "");
	}
	
	public Boolean canTag(){
		return getNullSafeBooleanAttribute("canTag", null);
	}
	
	public void setCanTag(Boolean b){
		setAttribute("canTag", b + "");
	}
	
	public Boolean isAutoShowDrag(){
		return getNullSafeBooleanAttribute("autoShowTag", null);
	}
	
	public void setAutoShowDrag(Boolean b){
		setAttribute("autoShowTag", b + "");
	}
	
	public Boolean isClickToTag(){
		return getNullSafeBooleanAttribute("clickToTag", null);
	}
	
	public void setClickToTag(Boolean b){
		setAttribute("clickToTag", b + "");
	}
	
	
	public Boolean isTagDraggable(){
		return getNullSafeBooleanAttribute("tagDraggable", null);
	}
	
	public Boolean isTagResizable(){
		return getNullSafeBooleanAttribute("tagResizable", null);
	}
	
	public String showTagOn(){
		return getNullSafeStringAttribute("showTagOn", null);
	}
	
	
	public Boolean isShowLabels(){
		return getNullSafeBooleanAttribute("showLabels",null);
	}
	
	
	public void setTagDraggable(Boolean b){
		setAttribute("tagDraggable", b + "");
	}
	
	public void setTagResizable(Boolean b){
		setAttribute("tagResizable", b + "");
	}
	
	public void setShowTagOn(String on){
		setAttribute("showTagOn", on );
	}
	
	
	public void setShowLabels(Boolean b){
		setAttribute("showLabels", b + "");
	}
	
	public void onReady(ClientProxy proxy){
		
		if(isTaggable()){
			JMap option = new JMap();
			if(getMaxWidth() != null)
				option.put("minWidth", getMinWidth());
			
			if(getMinHeight() != null)
				option.put("minHeight", getMinHeight());
			
			if(getDefaultWidth() != null)
				option.put("defaultWidth", getDefaultWidth());
			
			if(getDefaultHeight() != null)
				option.put("defaultHeight",getDefaultHeight());
			
			if(getMaxHeight() != null)
				option.put("maxHeight", getMaxHeight());
			
			if(getMaxWidth() != null)
				option.put("maxWidth", getMaxWidth());
			
			if(canTag() != null)
				option.put("canTag", canTag());
			
			if(canDeleteTag() != null)
				option.put("canDelete", canDeleteTag());
			
			if(isAutoShowDrag() != null)
				option.put("autoShowDrag", isAutoShowDrag());
			
			if(isAutoComplete() && getAutoCompleteValues() != null)
				option.put("autoComplete", getAutoCompleteValues());
			
			if(getDefaultTags() != null)
				option.put("defaultTags", getDefaultTags());
			
			if(isClickToTag() != null)
				option.put("clickToTag", isClickToTag());
			
			if(isTagDraggable() != null)
				option.put("draggable", isTagDraggable());
			
			if(isTagResizable() != null)
				option.put("resizable", isTagResizable());
			
			if(showTagOn() != null)
				option.put("showTag", showTagOn());
			
			if(isShowLabels() != null)
				option.put("showLabels", isShowLabels());
			
			
			ClientProxy cb = proxy.clone();
			cb.getCSS("http://djpate.com/portfolio/jTag/css/jquery.tag.css");
			cb.addMethod("tag", option);
			
			proxy.getScript("http://djpate.com/portfolio/jTag/source/jquery.tag.js",cb );
			
			

		}
		
	}

}
