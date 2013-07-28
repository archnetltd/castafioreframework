/*
 * Copyright (C) 2007-2008 Castafiore
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

package org.castafiore.security;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.castafiore.security.ui.listproviders.GroupListProvider;
import org.castafiore.security.ui.listproviders.RoleListProvider;
import org.castafiore.security.ui.listproviders.UsersListProvider;
import org.castafiore.ui.ex.dynaform.annotations.Field;
import org.castafiore.ui.ex.form.list.EXSelect;
/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 22, 2008
 */
@Entity

@Table(name="SECU_USERSECURITY")
public class UserSecurity   implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@ManyToOne()
	private User user;
	
	@ManyToOne
	private Role role;
	
	@ManyToOne
	private Group grp;
	
	private String organization;
	
	

	public String getOrganization() {
		return organization;
	}


	public void setOrganization(String organization) {
		this.organization = organization;
	}


	public User getUser() {
		return user;
	}

	
	@Field(position=0,fieldType=EXSelect.class,listProvider=UsersListProvider.class, name="user", label="Select User :")
	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	@Field(position=1,fieldType=EXSelect.class,listProvider=RoleListProvider.class, name="role", label="Select Role :")
	public void setRole(Role role) {
		this.role = role;
	}

	public Group getGrp() {
		return grp;
	}

	@Field(position=2,fieldType=EXSelect.class,listProvider=GroupListProvider.class, name="group", label="Select Group :")
	public void setGrp(Group group) {
		this.grp = group;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	
	
	

}
