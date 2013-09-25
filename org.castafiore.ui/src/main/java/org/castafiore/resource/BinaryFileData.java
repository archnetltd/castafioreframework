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
 * This class is used in the {@link EXUpload} class to return the file or list
 * of files uploaded
 * 
 * @author arossaye
 * 
 */
public class BinaryFileData implements FileData {

	private String mimeType;

	private String name;

	private String location = ResourceUtil.getUploadDir()
			+ (this.hashCode() + System.currentTimeMillis()) + ".casta";

	/**
	 * @return An {@link InputStream} for the binary data
	 */
	public InputStream getInputStream() throws Exception {
		return new FileInputStream(location);
	}

	/**
	 * @return The mimetype of this Resource
	 */
	public String getMimeType() {
		return mimeType;
	}

	/**
	 * Sets the mimetype of this Resource
	 * 
	 * @param mimeType
	 *            The mimetype
	 */
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	/**
	 * @return The name of this resource
	 */
	public String getName() {
		return name;
	}

	/**
	 * sets the name of this resource
	 * 
	 * @param name
	 *            The name of this resource
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns an {@link OutputStream} ready to write into this resource
	 * 
	 * @return The {@link OutputStream} to write into
	 */
	public OutputStream getOutputStream() {
		try {
			return new FileOutputStream(location);
		} catch (Exception e) {
			throw new UIException(e);
		}
	}

	/**
	 * Writes the array of byte into this resource
	 * 
	 * @param bytes
	 *            The array of byte to write
	 * @throws Exception
	 *             If an IOException occures
	 */
	public void write(byte[] bytes) throws Exception {
		OutputStream out = getOutputStream();
		out.write(bytes);
		out.flush();
		out.close();

	}

	/**
	 * Writes this {@link InputStream} into the the resource
	 * 
	 * @param The
	 *            {@link InputStream} to read from in order to write into the
	 *            resource
	 */
	public void write(InputStream in) throws Exception {
		OutputStream out = getOutputStream();
		out.write(IOUtil.getStreamContentAsBytes(in));

	}

	/**
	 * sets a url downloadable directly via Castafiore framework
	 */
	@Override
	public void setUrl(String url) {
		location = url;

	}

}
