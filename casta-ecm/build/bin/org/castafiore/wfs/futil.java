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
 package org.castafiore.wfs;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.SimpleKeyValuePair;
import org.castafiore.spring.SpringUtil;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.utils.IOUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;
import org.hibernate.SessionFactory;

public class futil {
	

	public static List<SimpleKeyValuePair> getEntityTypes(){
		List<SimpleKeyValuePair> result = new ArrayList<SimpleKeyValuePair>();
		try{
			SessionFactory factory = BaseSpringUtil.getBeanOfType(SessionFactory.class);
			Object[] clss = factory.getAllClassMetadata().keySet().toArray();
			for(int index = 0; index < clss.length; index++){
				try{
					Object o =  Thread.currentThread().getContextClassLoader().loadClass(clss[index].toString()).newInstance();
					if(o instanceof File){
						SimpleKeyValuePair kv = new SimpleKeyValuePair(clss[index].toString(), StringUtil.split(clss[index].toString(), ".")[StringUtil.split(clss[index].toString(), ".").length-1]);
						result.add(kv);
					}
				}catch(Exception e){
					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static Directory getDirectory(String path){
		return SpringUtil.getRepositoryService().getDirectory(path, Util.getRemoteUser());
	}
	
	
	public static File get(String path){
		return SpringUtil.getRepositoryService().getFile(path, Util.getRemoteUser());
	}
	
	public static void update(File f ){
		f.save();
		//SpringUtil.getRepositoryService().update(f, Util.getRemoteUser());
	}
	
	public static String getTextFromBinaryUrl(String url){
		if(url.startsWith("castafiore/resource?")){
			try{
				String path = StringUtil.split(url, ":")[1];
				BinaryFile file = (BinaryFile)SpringUtil.getRepositoryService().getFile(path, Util.getRemoteUser());
				String txt = IOUtil.getStreamContentAsString(file.getInputStream());
				return txt;
				
			}catch(Exception e){
				e.printStackTrace();
			}
			return "";
		}
		return null;
	}
	
	
	
	

}
