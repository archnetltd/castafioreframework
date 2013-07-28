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
 package org.castafiore.contact;

import org.castafiore.utils.ResourceUtil;
import org.castafiore.wfs.types.File;
import org.castafiore.wfs.types.config.AbstractFileConfig;

public class ContactConfig extends AbstractFileConfig {
	
	private String username;
	
	private String email;
	
	private String department;
	
	private String subDepartment;
	
	private String imgUrl=ResourceUtil.getDownloadURL("classpath", "org/castafiore/contact/resources/Contact.jpg");
	
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getSubDepartment() {
		return subDepartment;
	}

	public void setSubDepartment(String subDepartment) {
		this.subDepartment = subDepartment;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	@Override
	public void fillInstance(File empty) {
		
//		Contact c = (Contact)empty;
//		c.setName(getName());
//		c.setDepartment(department);
//		c.setSubDepartment(subDepartment);
//		c.setUsername(username);
//		c.setEmail(email);
//		c.setImgUrl(imgUrl);
		
	}

	@Override
	public File getNewInstance() {
		//return new Contact();
		
		return null;
	}

}
