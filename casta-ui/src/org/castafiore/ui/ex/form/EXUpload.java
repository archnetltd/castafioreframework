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

package org.castafiore.ui.ex.form;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.groovy.EXGroovyContainer;
import org.castafiore.resource.FileData;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.input.Decoder;
import org.castafiore.ui.input.Encoder;
import org.castafiore.utils.ResourceUtil;
/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 22, 2008
 */
public class EXUpload extends EXGroovyContainer implements StatefullComponent
{

	
	private List<FileData> items = new ArrayList<FileData>();
	public EXUpload(String name)
	{
		super(name, "sd.html");
		
		
	}
	public FileData getFile() {
		if(items.size() == 0)
			return null;
		return items.get(0);
	}
	//value is set in castafioreServlet
	public void setFile(FileData item) {
		items.clear();
		this.items.add(item);
	}
	
	public void addFile(FileData item) {
		this.items.add(item);
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
		// TODO Auto-generated method stub
		return null;
	}
	public Object getValue() {
		if(items.size() ==1)
		return getFile();
		else
			return items;
	}
	public void setDecoder(Decoder decoder) {
		// TODO Auto-generated method stub
		
	}
	public void setEncoder(Encoder encoder) {
		// TODO Auto-generated method stub
		
	}
	public void setRawValue(String rawValue) {
		// TODO Auto-generated method stub
		
	}
	public void setValue(Object value) {
		setFile((FileData)value);
		
	}
	
	
	
	
	

}
