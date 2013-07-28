package org.castafiore.wfs.service;

import org.castafiore.persistence.Dao;
import org.castafiore.wfs.iterator.FileIterator;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;
import org.hibernate.EntityMode;
import org.hibernate.metadata.ClassMetadata;

public class SimpleFileManipulator implements FileManipulator{
	
	
	private RepositoryService repositoryService;
	
	private Dao dao;
	
	

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

//	@Override
//	public void copyFileTo(File file, String destination,String username) {
//		try{
//			ClassMetadata met = dao.getHibernateTemplate().getSessionFactory().getClassMetadata(file.getClazz());
//			String[] names = met.getPropertyNames();
//			
//			File newInstance = (File)Thread.currentThread().getContextClassLoader().loadClass(file.getClazz()).newInstance();
//			for(String name : names){
//				if(! name.equals("id") && ! met.getPropertyType(name).isEntityType() && ! met.getPropertyType(name).isCollectionType()){
//					met.setPropertyValue(newInstance, name, met.getPropertyValue(file, name, EntityMode.POJO), EntityMode.POJO);
//				}
//			}
//			repositoryService.saveIn(destination, file, username);
//			if(file instanceof Directory){
//				FileIterator iterator = ((Directory)file).getChildren();
//				while(iterator.hasNext()){
//					copyFileTo(iterator.next(), file.getAbsolutePath(), username);
//				}
//			}
//		}catch(Exception e){
//			throw new RuntimeException(e);
//		}
//	}

//	@Override
//	public void deleteFile(File file, String username) {
//		file.setMetadata("casta:deletedFrom", file.getAbsolutePath());
//		moveFile(file, "/root/users/" + username + "/Bin", username);
//	}

//	@Override
//	public void moveFile(File file, String destination, String username) {
//		repositoryService.saveIn(destination, file, username);
//		if(file instanceof Directory){
//			FileIterator iterator = ((Directory)file).getChildren();
//			while(iterator.hasNext()){
//				moveFile(iterator.next(), file.getAbsolutePath(), username);
//			}
//		}
//	}

//	@Override
//	public void restoreFile(File file, String username) {
//		String deletedFrom =  file.getMetadata("casta:deletedFrom");
//		if(deletedFrom != null  && file.getAbsolutePath().startsWith("/root/users/" + username + "/Bin")){
//			moveFile(file, deletedFrom, username);
//		}
//	}

}
