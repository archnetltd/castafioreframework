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

package org.castafiore.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class IOUtil.
 *
 * @author Kureem Rossaye<br>
 * kureem@gmail.com
 * Oct 22, 2008
 */
public class IOUtil {
	
	/**
	 * Gets the file content as string.
	 *
	 * @param file the file
	 * @param encoding the encoding
	 * @return the file content as string
	 * @throws Exception the exception
	 */
	static public String getFileContenntAsString(File file, String encoding)
			throws Exception {
		FileInputStream is = new FileInputStream(file);
		return new String(getStreamContentAsBytes(is), encoding);
	}

	/**
	 * Gets the file content as string.
	 *
	 * @param file the file
	 * @return the file contennt as string
	 * @throws Exception the exception
	 */
	static public String getFileContenntAsString(File file) throws Exception {
		FileInputStream is = new FileInputStream(file);
		return new String(getStreamContentAsBytes(is));
	}

	/**
	 * Gets the file content as string.
	 *
	 * @param fileName the file name
	 * @param encoding the encoding
	 * @return the file content as string
	 * @throws Exception the exception
	 */
	static public String getFileContenntAsString(String fileName,
			String encoding) throws Exception {
		FileInputStream is = new FileInputStream(fileName);
		return new String(getStreamContentAsBytes(is), encoding);
	}

	/**
	 * Gets the file content as string.
	 *
	 * @param fileName the file name
	 * @return the file content as string
	 * @throws Exception the exception
	 */
	static public String getFileContenntAsString(String fileName)
			throws Exception {
		FileInputStream is = new FileInputStream(fileName);
		return new String(getStreamContentAsBytes(is));
	}

	/**
	 * Gets the file content as bytes.
	 *
	 * @param fileName the file name
	 * @return the file content as bytes
	 * @throws Exception the exception
	 */
	static public byte[] getFileContentAsBytes(String fileName)
			throws Exception {
		FileInputStream is = new FileInputStream(fileName);
		return getStreamContentAsBytes(is);
	}

	/**
	 * Gets the stream content as string.
	 *
	 * @param is the is
	 * @return the stream content as string
	 */
	static public String getStreamContentAsString(InputStream is)
			 {
		try{
			byte buf[] = new byte[is.available()];
			is.read(buf);
			return new String(buf, "UTF-8");
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	/**
	 * Gets the stream content as bytes.
	 *
	 * @param is the is
	 * @return the stream content as bytes
	 * @throws Exception the exception
	 */
	static public byte[] getStreamContentAsBytes(InputStream is)
			throws Exception {
		
		
		BufferedInputStream buffer = new BufferedInputStream(is);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] data = new byte[buffer.available()];
		if(buffer.available() == 0){
			return data;
		}
		
		int available = -1;
		while ((available = buffer.read(data)) > -1) {
			output.write(data, 0, available);
		}
		return output.toByteArray();
	}

	/**
	 * Gets the resource as string.
	 *
	 * @param resource the resource
	 * @return the resource as string
	 * @throws Exception the exception
	 */
	static public String getResourceAsString(String resource) throws Exception {
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		URL url = cl.getResource(resource);
		InputStream is = url.openStream();
		return getStreamContentAsString(is);
	}

	/**
	 * Gets the resource as bytes.
	 *
	 * @param resource the resource
	 * @return the resource as bytes
	 * @throws Exception the exception
	 */
	static public byte[] getResourceAsBytes(String resource) throws Exception {
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		URL url = cl.getResource(resource);
		InputStream is = url.openStream();
		return getStreamContentAsBytes(is);
	}

	/**
	 * Serialize.
	 *
	 * @param obj the obj
	 * @return the byte[]
	 * @throws Exception the exception
	 */
	static public byte[] serialize(Object obj) throws Exception {
		// long start = System.currentTimeMillis() ;
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(bytes);
		out.writeObject(obj);
		out.close();
		byte[] ret = bytes.toByteArray();
		// long end = System.currentTimeMillis() ;
		return ret;
	}

	/**
	 * Deserialize.
	 *
	 * @param bytes the bytes
	 * @return the object
	 * @throws Exception the exception
	 */
	static public Object deserialize(byte[] bytes) throws Exception {
		if (bytes == null)
			return null;
		// long start = System.currentTimeMillis() ;
		ByteArrayInputStream is = new ByteArrayInputStream(bytes);
		ObjectInputStream in = new ObjectInputStream(is);
		Object obj = in.readObject();
		in.close();
		return obj;
	}
	
	
	public static List<String> readStreamByLine(InputStream inf)throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(inf));
		List<String> result = new ArrayList<String>(); 
		String inputLine;
		while ((inputLine = in.readLine()) != null)result.add(inputLine);
		in.close();
		return result;
	}
	
	

}
