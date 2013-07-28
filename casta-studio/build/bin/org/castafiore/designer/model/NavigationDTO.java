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
 package org.castafiore.designer.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NavigationDTO implements Serializable {
	
	private String name;
	
	private String label;
	
	private String uri;
	
	private String pageReference;
	
	private List<NavigationDTO> children = new ArrayList<NavigationDTO>();

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getPageReference() {
		return pageReference;
	}

	public void setPageReference(String pageReference) {
		this.pageReference = pageReference;
	}

	public List<NavigationDTO> getChildren() {
		return children;
	}

	public void setChildren(List<NavigationDTO> children) {
		this.children = children;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public static JSONObject getJSON(NavigationDTO dto)throws JSONException{
		JSONObject obj = new JSONObject();
		obj.put("name", dto.name);
		obj.put("label", dto.label);
		obj.put("uri", dto.uri);
		obj.put("pageReference", dto.pageReference);
		JSONArray children = new JSONArray();
		for(NavigationDTO child : dto.children){
			JSONObject jsChild = getJSON(child);
			children.put( jsChild);
		}
		obj.put("children", children);
		
		return obj;
	}
	
	public static NavigationDTO getObject(JSONObject obj)throws JSONException{
		NavigationDTO dto = new NavigationDTO();
		dto.setLabel(obj.getString("label"));
		dto.setName(obj.getString("name"));
		dto.setPageReference(obj.getString("pageReference"));
		try{
			dto.setUri(obj.getString("uri"));
		}catch(Exception e){
			dto.setUri(dto.getPageReference());
		}
		JSONArray array = obj.getJSONArray("children");
		for(int i = 0; i < array.length(); i++){
			JSONObject jsChild = array.getJSONObject(i);
			NavigationDTO child = getObject(jsChild);
			dto.children.add(child);
		}
		
		return dto;
	}
	
	
	
	

}
