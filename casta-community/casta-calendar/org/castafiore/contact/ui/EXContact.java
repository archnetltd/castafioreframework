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
 package org.castafiore.contact.ui;

import org.castafiore.contact.Contact;
import org.castafiore.contact.ContactService;
import org.castafiore.security.User;
import org.castafiore.ui.Container;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ResourceUtil;

public class EXContact extends EXXHTMLFragment {

	private Contact contact;
	
	public EXContact(String name, Contact contact) {
		super(name, ResourceUtil.getDownloadURL("classpath", "org/castafiore/contact/resources/EXContact.xhtml"));
		Container img = ComponentUtil.getContainer("image", "img");
		img.setAttribute("width", "100");
		img.setAttribute("height", "100");
		addChild(img);
		
		addChild(ComponentUtil.getContainer("name", "span"));
		
		addChild(ComponentUtil.getContainer("department", "span"));
		
		addChild(ComponentUtil.getContainer("telephone", "span"));
		
		addChild(ComponentUtil.getContainer("email", "a"));
		setContact(contact);
		
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		try{
			this.contact = contact;
			getChild("image").setAttribute("src", contact.getImgUrl());
			ContactService service = BaseSpringUtil.getBeanOfType(ContactService.class);
			User u = service.getAccociatedUser(contact);
			getChild("name").setText(u.getFirstName() + " " + u.getLastName());
			getChild("department").setText(contact.getDepartment());
			getChild("email").setText(contact.getEmail());
			getChild("email").setAttribute("href", "mailto:" + contact.getEmail());
			getChild("telephone").setText("+ 230 7159028");
		}catch(Exception e){
			e.printStackTrace();
		}
			
	}
	
	

}
