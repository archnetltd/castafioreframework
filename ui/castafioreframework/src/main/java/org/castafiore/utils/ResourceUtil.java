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
 package org.castafiore.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.castafiore.resource.ResourceLocator;
import org.castafiore.resource.ResourceLocatorFactory;
import org.castafiore.ui.Application;
import org.castafiore.ui.CastafioreController;
import org.castafiore.ui.Container;




public class ResourceUtil {
	
	
	private static String ICONS_REPO = "org/castafiore/resource/icons";
	
	private static String JS_REPOSITORY = "org/castafiore/resource/js";
	
	
	private static java.util.Properties castaProp = new java.util.Properties();
	
	private static java.util.Properties MIMETYPES_EXTENSION = new java.util.Properties();
	
	private static java.util.Properties EXTENSION_MIMETYPES = new java.util.Properties();
	
	
	
	
	
	static{
		try{
			castaProp.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("castafiore.properties"));
			MIMETYPES_EXTENSION.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("mimetype.properties"));
			EXTENSION_MIMETYPES.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("extensions.properties"));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
		
	public static String getDownloadURL(String type, String path)
	{
		if(path.startsWith("/"))
			return "castafiore/resource/"+type+"" + path;
		else
			return "castafiore/resource/"+type+"/" + path;
	}
	
	
	public static boolean isTextType(String mimetype){
		if(mimetype != null)
			return mimetype.startsWith("text");
		return false;
	}
	public static String getExtensionFromFileName(String name){
		try{
			if(name != null){
				String[] as = StringUtil.split(name, ".");
				if(as != null && as.length > 1){
					return as[as.length-1];
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
		//System.out.println("did not find extension from :" + name);
		return "";
	}
	
	public static String getParentPath(String filePath){
		try{
			String[] as = StringUtil.split(filePath, "/");
			String res = filePath.substring(0, filePath.length() - as[as.length-1].length()-1);
			if(res.length() == 0){
				return "/root";
			}else{
				return res;
			}
		}catch(Exception e){
			return "/root";
		}
	}
	
	
	public static String getNameFromPath(String path){
		return StringUtil.getFileNameFromPath(path);
	}
	
	public static String getMimeFromExtension(String extension){
		
		return EXTENSION_MIMETYPES.getProperty(extension, "");
	}
	
	public static String getIconUrl(String imageName, String size)
	{
		return ResourceUtil.getDownloadURL("classpath", ICONS_REPO + "/" + size + "/" +imageName);
	}
	
	public static String getMethodUrl(Container c, String methodName, String paramName, String parameter){
		String componentid = c.getId();
		Application app = c.getRoot();
		
		String applicationid = app.getName();
		return "castafiore/methods/" + applicationid + "/" + componentid + "?method=" + methodName + "&"+paramName+"=" + parameter +"&paramName=" +  paramName;
	}
	
	public static String getMethodUrl(Container c, String methodName, String paramName){
		String componentid = c.getId();
		Application app = c.getRoot();
		
		String applicationid = app.getName();
		return "castafiore/methods/" + applicationid + "/" + componentid + "?method=" + methodName + "&paramName=" + paramName;
	}
	
	public static String getMethodUrl(CastafioreController c){
		String componentid = c.getId();
		Application app = c.getRoot();
		
		String applicationid = app.getName();
		return "castafiore/methods/" + applicationid + "/" + componentid;
	}
	
	public static String getJavascriptURL(String path)
	{
		return ResourceUtil.getDownloadURL("classpath", JS_REPOSITORY + "/" +path);
	}
	
	public static String getTemplate(String templateLocation, Application app){
		
		String contextPath = app.getContextPath();
		String serverPort = app.getServerPort();
		String servaerName = app.getServerName();
		if("www.3racingtips.com".equalsIgnoreCase(servaerName)){
			servaerName = "racingtips.s18.eatj.com";
		}
		
		if(!contextPath.startsWith("/")){
			contextPath = "/" + contextPath;
		}
		if(contextPath.endsWith("/") && templateLocation.startsWith("/")){
			templateLocation = templateLocation.substring(1);
		}
		if(!contextPath.endsWith("/") && ! templateLocation.startsWith("/")){
			templateLocation = "/" + templateLocation;
		}
		//String url = "";
		if(!templateLocation.startsWith("http")){
			templateLocation = "http://" + servaerName + ":" + serverPort  + contextPath + "" + templateLocation;
		}
		
		try{
			//URL oUrl = new URL(templateLocation);
			//InputStream in = oUrl.openStream();
			
			String template = readUrl(templateLocation);
			
			//throw new Exception("dfs");
			return template;
		}catch(Exception e){
			
			try{
				String[] asSpec = StringUtils.splitByWholeSeparator(templateLocation , "spec=");
				if(asSpec != null && asSpec.length == 2){
					String spec = asSpec[1];
					ResourceLocator locator = ResourceLocatorFactory.getResourceLocator(spec);
					InputStream data = locator.getResource(spec, null).getInputStream();
					
					String template = IOUtil.getStreamContentAsString(data);
					return template;
				}else{
					throw new RuntimeException("unable to load template:" + templateLocation, e);
				}
			}catch(Exception ex){
				throw new RuntimeException("unable to load template:" + templateLocation, e);
			}
			//e.printStackTrace();
		}
		
		
	}
	public static String extractUrlFromStyle(String style){
		return style.replaceAll(".*url\\(", "").replaceAll("'|\\).*", "");
	}
	
	public static String readUrl(String url)throws Exception{
		URL yahoo = new URL(url);
		BufferedReader in = new BufferedReader(new InputStreamReader(yahoo.openStream()));

		String inputLine;
		StringBuilder b = new StringBuilder();
		while ((inputLine = in.readLine()) != null)
			b.append(inputLine).append("\n");
		    //System.out.println(inputLine);

		in.close();
		
		return b.toString();

	}
	
	
	public static String getDirToWrite(){
		return ((Map)BaseSpringUtil.getBean("uploadprops")).get("upload.dir").toString();
		//return castaProp.getProperty("upload.dir");
	}
	public static byte[] readUrlBinary(String url)throws Exception{
		 

		    URL u = new URL(url);
		    URLConnection uc = u.openConnection();
		    String contentType = uc.getContentType();
		    int contentLength = uc.getContentLength();
		    if (contentType.startsWith("text/") || contentLength == -1) {
		      throw new IOException("This is not a binary file.");
		    }
		    InputStream raw = uc.getInputStream();
		    InputStream in = new BufferedInputStream(raw);
		    byte[] data = new byte[contentLength];
		    int bytesRead = 0;
		    int offset = 0;
		    while (offset < contentLength) {
		      bytesRead = in.read(data, offset, data.length - offset);
		      if (bytesRead == -1)
		        break;
		      offset += bytesRead;
		    }
		    in.close();

		    if (offset != contentLength) {
		      throw new IOException("Only read " + offset + " bytes; Expected " + contentLength + " bytes");
		    }

		    return data;
			  

	}
	
	public static byte[] read(String url)throws Exception{
		 

	    URL u = new URL(url);
	    URLConnection uc = u.openConnection();
	   // int contentLength = uc.getContentLength();
	    InputStream raw = uc.getInputStream();
	    //InputStream in = new BufferedInputStream(raw);
	    return IOUtil.getStreamContentAsBytes(raw);
	    
//	    byte[] data = new byte[contentLength];
//	    int bytesRead = 0;
//	    int offset = 0;
//	    while (offset < contentLength) {
//	      bytesRead = in.read(data, offset, data.length - offset);
//	      if (bytesRead == -1)
//	        break;
//	      offset += bytesRead;
//	    }
//	    in.close();
//
//	    if (offset != contentLength) {
//	      throw new IOException("Only read " + offset + " bytes; Expected " + contentLength + " bytes");
//	    }

	   // return data;
		  

}
	
	public static String getUploadDir(){
		return  "c:\\data";//((Map)BaseSpringUtil.getBean("uploadprops")).get("upload.dir").toString();
	}

}
