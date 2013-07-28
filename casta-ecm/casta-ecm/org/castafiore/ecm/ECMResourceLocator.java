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
 package org.castafiore.ecm;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import org.castafiore.resource.AbstractResourceLocator;
import org.castafiore.utils.IOUtil;
import org.castafiore.utils.ImageUtil;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.wfs.FileNotFoundException;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.File;

public class ECMResourceLocator extends AbstractResourceLocator  {
	
	private RepositoryService repositoryService;
	
	//private Map<String, BinaryFile> cache_ = new HashMap<String, BinaryFile>();
	

	public BinaryFile getResource(String spec, String width)throws Exception {
		
	
//		if(width == null && cache_.containsKey(spec)){
//			return cache_.get(spec);
//		}else if (width != null && cache_.containsKey(spec + ":" + width)){
//			return cache_.get(spec + ":" + width);
//		}
		
		String path = getIdentifier(spec);
		if(width != null){
			path = path + "/__" + width + "__" + ResourceUtil.getNameFromPath(path);
			File f = null;
			try{
				f = repositoryService.getFile(path, Util.getRemoteUser());
				//cache_.put(spec + ":" + width, (BinaryFile)f);
				if(((BinaryFile)f).getInputStream() == null){
					throw new IOException("no image created");
				}
				return (BinaryFile)f;
			}catch(FileNotFoundException fne){
				path = getIdentifier(spec);
				BinaryFile bf = (BinaryFile)repositoryService.getFile(path, Util.getRemoteUser());
				BinaryFile sm = bf.createFile("__" + width + "__" + bf.getName(), BinaryFile.class);
				String extension = ResourceUtil.getExtensionFromFileName(bf.getName());
				 ImageUtil.resizeImage(bf.getInputStream(), Integer.parseInt(width), extension,sm.getOutputStream());
				 sm.save();
				// cache_.put(spec + ":" + width, sm);
				 System.gc();
				 return sm;
			}catch(IOException ie){
				if(f != null){
					BinaryFile bf = (BinaryFile)f.getParent();
					BinaryFile sm = (BinaryFile)f;
					String extension = ResourceUtil.getExtensionFromFileName(bf.getName());
					ImageUtil.resizeImage(bf.getInputStream(), Integer.parseInt(width), extension,sm.getOutputStream());
					sm.save();
					System.gc();
					return sm;
				}
			}
		}else{
			File f = repositoryService.getFile(getIdentifier(spec),Util.getRemoteUser());
			if(f != null){
				//cache_.put(spec, (BinaryFile)f);
				return (BinaryFile)f;
			}
		}
		
//		File f = repositoryService.getFile(getIdentifier(spec),Util.getRemoteUser());
//		if(f != null)
//		{
//			if(width == null){
//				cache_.put(spec, (BinaryFile)f);
//				return (BinaryFile)f;
//			}else{
//				BinaryFile bf = (BinaryFile)f;
//				BinaryFile sm = (BinaryFile)bf.getFile("__" + width + "__" + bf.getName());
//				if(sm != null){
//					cache_.put(spec + ":" + width, sm);
//					return sm;
//				}else{
//					sm = bf.createFile("__" + width + "__" + bf.getName(), BinaryFile.class);
//					String extension = ResourceUtil.getExtensionFromFileName(bf.getName());
//					 sm.write(IOUtil.resizeImage(bf.getInputStream(), Integer.parseInt(width), extension));
//					 sm.save();
//					 cache_.put(spec + ":" + width, sm);
//					 System.gc();
//					 return sm;
//				}
//			}
//		}
		throw new Exception("cannot find resource for spec " + spec);
	}

	public RepositoryService getRepositoryService() {
		return repositoryService;
	}

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}
	
	

}
