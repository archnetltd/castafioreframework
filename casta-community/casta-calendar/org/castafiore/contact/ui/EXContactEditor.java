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
import org.castafiore.ecm.ui.fileexplorer.FileEditor;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.futil;
import org.castafiore.wfs.types.File;

public class EXContactEditor extends EXContainer implements FileEditor {
	
	private Contact contact;
	
	private boolean isNew = true;
	
	private String currentDir;
	

	public EXContactEditor() {
		super("EXContactEditor", "div");
		addField("Username :", new EXInput("username"));
		addField("Email :", new EXInput("email"));
		addField("Department :", new EXInput("department"));
		addField("Sub department", new EXInput("subDepartment"));
		addField("Image :", new EXInput("imgUrl"));
		
		
	}
	public void addField(String label, StatefullComponent input){
		addChild(new EXContainer("", "label").setText(label).setStyle("display", "block"));
		addChild(input);
	}
	public StatefullComponent getField(String name){
		return (StatefullComponent)getDescendentByName(name);
	}
	public void initialiseEditor(File file, String directory, boolean isNew) {
		
		this.contact = (Contact)file;
		this.currentDir = directory;
		this.isNew = isNew;
		
		if(!isNew){
			getField("username").setValue(contact.getUsername());
			getField("email").setValue(contact.getEmail());
			getField("department").setValue(contact.getDepartment());
			getField("subDepartment").setValue(contact.getSubDepartment());
			getField("imgUrl").setValue(contact.getImgUrl());
		}
		// TODO Auto-generated method stub
		
	}

	public File save(String name) {
		
		if(isNew){
			contact = futil.getDirectory(currentDir).createFile(name,Contact.class);
		}
		contact.setUsername(getField("username").getValue().toString());
		contact.setEmail(getField("email").getValue().toString());
		contact.setDepartment(getField("department").getValue().toString());
		contact.setSubDepartment(getField("subDepartment").getValue().toString());
		contact.setImgUrl(getField("imgUrl").getValue().toString());
		contact.save();
		
		return contact;
	}

}
