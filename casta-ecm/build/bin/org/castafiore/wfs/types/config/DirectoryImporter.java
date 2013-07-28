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
 package org.castafiore.wfs.types.config;

import java.io.File;

import org.castafiore.persistence.Dao;
import org.castafiore.spring.Startable;
import org.castafiore.utils.IOUtil;
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;


public class DirectoryImporter implements Startable {
	
	private String startDir;
	
	private String wfsStartDir = "/root";
	
	private RepositoryService repositoryService;
	
	private Dao dao;
	
	public void doImport(){
		try{
			dao.getSession();
			File file = new File(startDir);
			_doImport(file, wfsStartDir );
			//HibernateUtil.closeSession();
			dao.closeSession();
		}catch(Exception e){
			dao.rollBack();
			System.out.println(e.getMessage());
		}
	}
	
	protected void _doImport(File file, String destDir) throws Exception{
		addFile(file, destDir);
		if(file.isDirectory()){
			File[] files = file.listFiles();
			if(files != null && files.length > 0){
				for(File f : files){
					if(!f.getName().startsWith("."))
						_doImport(f, destDir + "/" + file.getName());
				}
			}
		}
	}
	
	public void addFile(File file, String destDir) throws Exception{
		String name = file.getName();
		boolean isDir = file.isDirectory();
		Directory fdestDir = getRepositoryService().getDirectory(destDir, getRepositoryService().getSuperUser());
		if(isDir){
			Directory dir = fdestDir.createFile(name, Directory.class);//new Directory();

			dir.setOwner(repositoryService.getSuperUser());
			
		}else{
			BinaryFile bf = fdestDir.createFile(name, BinaryFile.class);
			
			bf.setOwner(repositoryService.getSuperUser());
			byte[] bytes = IOUtil.getFileContentAsBytes(file.getAbsolutePath());
			bf.write(bytes);
			
		}
		fdestDir.save();
		
	}

	public String getStartDir() {
		return startDir;
	}

	public void setStartDir(String startDir) {
		this.startDir = startDir;
	}

	public String getWfsStartDir() {
		return wfsStartDir;
	}

	public void setWfsStartDir(String wfsStartDir) {
		this.wfsStartDir = wfsStartDir;
	}

	public RepositoryService getRepositoryService() {
		return repositoryService;
	}

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	public Dao getDao() {
		return dao;
	}

	public void setDao(Dao dao) {
		this.dao = dao;
	}

	
	public Integer getPriority() {
		return 1;
	}

	
	public void start() {
		doImport();
		
	}

	
}
