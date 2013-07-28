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

import javax.persistence.Entity;

import org.castafiore.security.User;
import org.castafiore.spring.SpringUtil;
import org.castafiore.wfs.types.File;
import org.castafiore.wfs.types.FileImpl;
import org.hibernate.annotations.Type;
@Entity
public class Contact extends FileImpl{
	
	private String username;
	
	private String email;
	
	@Type(type="text")
	private String department;
	
	@Type(type="text")
	private String subDepartment;
	
	@Type(type="text")
	private String imgUrl;
	
	public Contact(){
		super();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
	
	public User getUser(){
		return SpringUtil.getSecurityService().loadUserByUsername(username);
	}

}
