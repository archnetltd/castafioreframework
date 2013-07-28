package org.castafiore.wfs.service;

import org.castafiore.persistence.Dao;
import org.castafiore.wfs.types.File;

public interface FileManipulator {
	
	//public void moveFile(File file, String destination, String username);
	
	//public void deleteFile(File file, String username);
	
	//public void copyFileTo(File file, String destination, String username);
	
	//public void restoreFile(File file, String username);
	
	public void setDao(Dao dao);
	
	public void setRepositoryService(RepositoryService repositoryService);

}
