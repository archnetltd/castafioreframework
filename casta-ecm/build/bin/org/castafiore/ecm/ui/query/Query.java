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
 package org.castafiore.ecm.ui.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Query {

	private String entity;

	private List<String> directories = new ArrayList<String>(3);

	private Map<String, Criterion> criteria = new HashMap<String, Criterion>();

	public String getEntity() {
		return entity;
	}

	public Query setEntity(String entity) {
		this.entity = entity;
		return this;
	}

	public List<String> getDirectories() {
		return directories;
	}

	public Query addDirectory(String s){
		this.directories.add(s);
		return this;
	}

	public Map<String, Criterion> getCriteria() {
		return criteria;
	}

	public void setCriteria(Map<String, Criterion> criteria) {
		this.criteria = criteria;
	}

	public class Criterion {

		private String propertyName;

		private String sort;

		private String operator;

		private List<String> values = new ArrayList<String>();
		
		private String propertyType;
		
		

	}

}
