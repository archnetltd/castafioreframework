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
 package org.castafiore.resource;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.castafiore.ui.UIException;
import org.castafiore.ui.ex.form.EXUpload;
import org.castafiore.utils.IOUtil;
import org.castafiore.utils.ResourceUtil;

/**
 * Concrete implementation of the {@link FileData} interface<br>
 * This class handles resource on the file system<br>
 * This class is used in the {@link EXUpload} class to return the file or list of files uploaded
 * @author arossaye
 *
 */
public class BinaryFileData implements FileData{
	

	
	private String mimeType;
	
	private String name;
	
	private String location = ResourceUtil.getUploadDir() + (this.hashCode() + System.currentTimeMillis()) + ".casta";
	
	public InputStream getInputStream() throws Exception {
		return new FileInputStream(location);
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public OutputStream getOutputStream() {
		try{
			return new FileOutputStream(location);
		}catch(Exception e){
			throw new UIException(e);
		}
	}

	public void write(byte[] bytes) throws Exception {
		OutputStream out = getOutputStream();
		out.write(bytes);
		out.flush();
		out.close();
		
	}

	public void write(InputStream in) throws Exception {
		OutputStream out = getOutputStream();
		out.write(IOUtil.getStreamContentAsBytes(in));
		
	}

	@Override
	public void setUrl(String url) {
		location = url;
		
	}

}
