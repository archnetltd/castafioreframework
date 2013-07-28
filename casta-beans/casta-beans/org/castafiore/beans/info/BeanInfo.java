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
 package org.castafiore.beans.info;

import java.util.HashMap;
import java.util.Map;

public class BeanInfo implements IBeanInfo {
	
	private Map<String, String> attributes = new HashMap<String, String>();
	
	private String supportedUniqueId;
	
	

	public String getInfoAttribute(String key) {
		return attributes.get(key);
	}

	public String[] getKeys() {
		return attributes.keySet().toArray(new String[attributes.keySet().size()]);
	}

	public String getSupportedUniqueId() {
		return supportedUniqueId;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	public void setSupportedUniqueId(String supportedUniqueId) {
		this.supportedUniqueId = supportedUniqueId;
	}

	public String getDescription() {
		return attributes.get("description");
	}

	public void setDescription(String description) {
		attributes.put("description", description);
	}

	
	
	
}
