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

import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.castafiore.persistence.Dao;
import org.castafiore.security.User;
import org.castafiore.security.api.SecurityService;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.utils.StringUtil;

public class UserCreator extends AbstractCreator {

	private String username;

	private String password;

	private String firstName;

	private String lastName;

	private String email;

	private boolean enabled = true;

	private boolean accountNonLocked = true;

	private boolean accountNonExpired = true;

	private boolean credentialsNonExpired = true;
	
	private String permissions = null;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}
	
	

	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	@Override
	public Object createInstance() {
		User u = new User();
		u.setUsername(username);
		u.setPassword(password);
		u.setFirstName(firstName);
		u.setLastName(lastName);
		u.setEmail(email);
		u.setEnabled(enabled);
		u.setAccountNonLocked(accountNonLocked);
		u.setAccountNonExpired(accountNonExpired);
		u.setCredentialsNonExpired(credentialsNonExpired);
		return u;
		// u.setName(getName());
	}
	
	public void save(){
		
		try{
			
			SecurityService service = ((SecurityService)getApplicationContext().getBean("securityService"));
			try{
				service.loadUserByUsername(username);
			}catch(UsernameNotFoundException nfe){
				((SecurityService)getApplicationContext().getBean("securityService")).registerUser((User)createInstance());
				getApplicationContext().getBeansOfType(Dao.class).values().iterator().next().getSession().flush();
				if(permissions != null && permissions.length() > 0){
					String[] parts = StringUtil.split(permissions, ";");
					if(parts != null){
						for(String p : parts){
							String[] pp = StringUtil.split(p, ":");
							if(pp != null){
								if(pp.length == 2){
									try
									{
										//((SecurityService)getApplicationContext().getBean("securityService")).assignSecurity(username, pp[0], pp[1], true);
									}
									catch(Exception e){
										e.printStackTrace();
									}
								}
							}
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		//super.save();
		
		
		
	}


}
