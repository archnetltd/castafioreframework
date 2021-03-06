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
 package org.castafiore.security.configs;

import org.castafiore.security.Group;
import org.castafiore.security.api.SecurityService;

public class GroupCreator extends AbstractCreator {

	private String description;

	private String name;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Object createInstance() {
		Group g = new Group();
		g.setName(name);
		g.setDescription(description);
		return g;
	}
	
	public void save(){
		try{
			//((SecurityService)getApplicationContext().getBean("securityService")).saveOrUpdateGroup(name, description);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
