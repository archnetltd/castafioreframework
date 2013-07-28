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
 package org.castafiore.wfs.session;

import java.io.Serializable;
import java.util.List;

import org.castafiore.wfs.service.QueryParameters;
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.Drive;
import org.castafiore.wfs.types.File;
import org.springframework.transaction.annotation.Transactional;

public interface Session extends Serializable {
	
	
	public boolean itemExists(String path);
	
	public String getRemoteUser(); 

	public RepositoryService getRepositoryService();

	

	/**
	 * returns all drives
	 * @return
	 * @throws Exception
	 */
	public List<Drive> getDrives() ;
	
	
	/**
	 * returns a file with the specified path
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public File getFile(String path); 
	
	
	
	
	
	/**
	 * saves a file in the specified directory
	 * @param path
	 * @param file
	 * @return
	 * @throws Exception
	 */
//	@Transactional(readOnly = false)
//	public File saveIn(String path, File file); 
	
	
	
	/**
	 * deletes a file
	 * @param file
	 * @throws Exception
	 */
	@Transactional(readOnly = false)
	public void delete(File file); 
	
	
	
	
	
	/**
	 * simply saves a file
	 * @param file
	 * @throws Exception
	 */
	@Transactional(readOnly = false)
	public void save(File file);
	
	
	public List<File> executeQuery(QueryParameters parameters);
	
	
	/**
	 * refreshes the instance of file
	 * @param file
	 */
	public void refresh(File file); 
	
	
	/**
	 * returns the directory from the specified path
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public Directory getDirectory(String path); 
	

}
