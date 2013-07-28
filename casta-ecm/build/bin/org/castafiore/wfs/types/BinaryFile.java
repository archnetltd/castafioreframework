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
 
package org.castafiore.wfs.types;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.persistence.Entity;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.castafiore.resource.FileData;
import org.castafiore.utils.ChannelUtil;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.utils.StringUtil;
import org.hibernate.annotations.AccessType;

@Entity
public class BinaryFile extends Directory implements FileData  {
	protected final  transient Log logger = LogFactory.getLog(getClass());
	
	@AccessType(value="field")
	private String mimeType;

//	@AccessType(value="field")
//	@LazyToOne(LazyToOneOption.PROXY)
//	private byte[] data;
	
	private String url;
	
	

	public String getMimeType() {
		return mimeType;
	}
	
	public BinaryFile(){
		super();
	}
	
	public void putInto(String parentDirectory){
		if(!StringUtil.isNotEmpty(getAbsolutePath())){
			Directory dir = getSession().getDirectory(parentDirectory);
			dir.addChild(this);
		}else{
			throw new RuntimeException("this file " + getAbsolutePath() + " has already been saved");
		}
	}
	
	public OutputStream getOutputStream()throws Exception{
		OutputStream out = new BufferedOutputStream(new FileOutputStream(new java.io.File(ResourceUtil.getDirToWrite() + "/" + getAbsolutePath().replace('/', '_')), false),1024);
		setUrl(ResourceUtil.getDirToWrite() + "/" + getAbsolutePath().replace('/', '_'));
		return out;
		
	}

	
	public void append(byte[] bytes)throws Exception{
		OutputStream out = new BufferedOutputStream(new FileOutputStream(new java.io.File(ResourceUtil.getDirToWrite() + "/" + getAbsolutePath().replace('/', '_')), true),1024);
		out.write(bytes);
		out.flush();
		out.close();
		
	}
	public InputStream getInputStream() throws Exception{
		if(url != null){
			return new BufferedInputStream(new FileInputStream(new java.io.File(url)));
		}
//		if(data != null){
//			String name = getAbsolutePath().replace('/', '_');
//			String dir = ResourceUtil.getDirToWrite();
//			FileOutputStream out = new FileOutputStream(new java.io.File(dir + "/" + name));
//			out.write(data);
//			out.flush();
//			out.close();
//			url = dir + "/" + name;
//			data = null;
//			save();
//			System.gc();
//			return new BufferedInputStream(new FileInputStream(new java.io.File(url)));
//		}
	
		
		return null;
		
	}
	
	
	

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

//	public void write(byte[] binaryData)throws Exception{
//		data = binaryData;
//		if(data != null){
//			setSize(data.length);
//		}else{
//			setSize(0);
//		}
//		
//	}

	
	public void write(byte[] binaryData)throws Exception{
		write(new ByteArrayInputStream(binaryData));
	}
	
	public void write(InputStream in)throws Exception {
		OutputStream baop = getOutputStream(); //new BufferedOutputStream(new FileOutputStream(new java.io.File(ResourceUtil.getDirToWrite() + "/" + getAbsolutePath().replace('/', '_')), false),1024);
		
		ChannelUtil.TransferData(in, baop);
		baop.flush();
		setUrl(ResourceUtil.getDirToWrite() + "/" + getAbsolutePath().replace('/', '_'));
		//write( baop.toByteArray());
		baop.close();
		setSize(1024);
		
	}


	public void setName(String name) {
		
		super.setName(name);
		String extension = ResourceUtil.getExtensionFromFileName(name);
		if(extension.length() > 0)
			this.mimeType = ResourceUtil.getMimeFromExtension(extension);
	}
	
	
}
