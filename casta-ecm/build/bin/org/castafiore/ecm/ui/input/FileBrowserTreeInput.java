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
 package org.castafiore.ecm.ui.input;

import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.ex.tree.EXTree;
import org.castafiore.ui.input.Decoder;
import org.castafiore.ui.input.Encoder;
import org.castafiore.wfs.iterator.FileFilter;
import org.castafiore.wfs.types.Directory;

public class FileBrowserTreeInput extends EXTree implements StatefullComponent {

	public FileBrowserTreeInput(String name, Directory startDirectory, FileFilter filter) {
		super(name, new FileTreeNode(startDirectory,null,filter));
		// TODO Auto-generated constructor stub
	}
	public FileBrowserTreeInput(String name) {
		super(name, new FileTreeNode(null,null,null));
		// TODO Auto-generated constructor stub
	}
	

	public Decoder getDecoder() {
		// TODO Auto-generated method stub
		return null;
	}

	public Encoder getEncoder() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getRawValue() {
		return getAttribute("value");
	}

	public Object getValue() {
		return getRawValue();
	}

	public void setDecoder(Decoder decoder) {
		// TODO Auto-generated method stub
		
	}

	public void setEncoder(Encoder encoder) {
		// TODO Auto-generated method stub
		
	}

	public void setRawValue(String rawValue) {
		setAttribute("value", rawValue);
		
	}

	public void setValue(Object value) {
		setRawValue(value.toString());
		
	}

}
