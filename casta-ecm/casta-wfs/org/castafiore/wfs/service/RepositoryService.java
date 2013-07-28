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

package org.castafiore.wfs.service;

import java.util.List;

import org.castafiore.wfs.FileAlreadyExistException;
import org.castafiore.wfs.FileNotFoundException;
import org.castafiore.wfs.InsufficientPriviledgeException;
import org.castafiore.wfs.InvalidFileTypeException;
import org.castafiore.wfs.LockedFileException;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.Drive;
import org.castafiore.wfs.types.File;
import org.springframework.dao.DataAccessException;


/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 29, 2008
 */

public interface RepositoryService {
	
	public org.castafiore.wfs.session.Session getCastafioreSession();
	
	/**
	 * returns the user that has full access to all reasource
	 * @return
	 */
	public String getSuperUser();
	
	
	/**
	 * returns all drives
	 * @return
	 * @throws Exception
	 */
	public List<Drive> getDrives(String username);
	
	
	/**
	 * returns a file with the specified path
	 * @param path
	 * @return
	 * @throws FileNotFoundException 
	 * @throws InsufficientPriviledgeException
	 */
	public File getFile(String path, String username)throws  InsufficientPriviledgeException, FileNotFoundException;
	
	
	/**
	 * deletes a file
	 * @param file
	 * @throws Exception
	 */
	public void delete(File file, String username)throws  InsufficientPriviledgeException, LockedFileException;
	
	
	
	
	
	/**
	 * simply saves a file
	 * @param file
	 * @throws Exception
	 */
	public File save(File file, String username)throws  InsufficientPriviledgeException, FileAlreadyExistException, LockedFileException;
	
	

	
	public List<File> executeQuery(QueryParameters parameters , String username);
	
	public Integer countRows(QueryParameters parameters , String username);
	
	/**
	 * refreshes the instance of file
	 * @param file
	 */
	public void refresh(File file);
	
	
	/**
	 * returns the directory from the specified path
	 * @param path
	 * @return
	 * @throws FileNotFoundException 
	 * @throws InsufficientPriviledgeException
	 * @throws InvalidFileTypeException
	 */
	public Directory getDirectory(String path, String username)throws  InsufficientPriviledgeException, InvalidFileTypeException, FileNotFoundException;
	
	/**
	 * checks if the specified file exists
	 * @param path
	 * @param username
	 * @return
	 * @throws DataAccessException
	 */
	public boolean itemExists(String path);
	
	public FileManipulator getFileManipulator();
	
	public int getNextSequence(String sequenceName, String username, String organization);
	
	public int getNextSequence(String username);
	
	public int getNextSequence(String sequenceName, String username);

}
