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

import java.util.ArrayList;
import java.util.List;

import org.castafiore.contact.Contact;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXGrid;

public class EXContactDepartment extends EXGrid {

	private List<Contact> contacts = new ArrayList<Contact>();

	public EXContactDepartment(String name,  	List<Contact> contacts) {
		super(name, 2, getRows(contacts.size()));
		setAttribute("width", "100%");
		int count = 0;
		for(Contact c : contacts){
			
			EXContact uiContact = new EXContact("",c);
			
			Container cell = getCell(count%2, (count - (count%2))/2);
			cell.setAttribute("width", "50%");
			cell.addChild(uiContact);
			count++;
		}
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	private static int getRows(int size) {
		return ((size % 2) + size) / 2;
	}

}
