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

import org.castafiore.persistence.Dao;
import org.castafiore.wfs.FileAlreadyExistException;
import org.castafiore.wfs.FileNotFoundException;
import org.castafiore.wfs.InsufficientPriviledgeException;
import org.castafiore.wfs.InvalidFileTypeException;
import org.castafiore.wfs.LockedFileException;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.iterator.ScrollableFileIterator;
import org.castafiore.wfs.iterator.ScrollableList;
import org.castafiore.wfs.session.SessionImpl;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.Drive;
import org.castafiore.wfs.types.File;
import org.castafiore.wfs.types.FileImpl;
import org.castafiore.wfs.types.Sequence;
import org.hibernate.HibernateException;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 29, 2008
 */
public class RepositoryServiceImpl  implements RepositoryService {
	
	private ThreadLocal<String> tusername = new ThreadLocal<String>();
	

	private Dao dao = null;
	
	private String superUser = "system";
	
	private QueryExecutor queryExecutor;
	
	private FileManipulator fileManipulator;
	
	

	public FileManipulator getFileManipulator() {
		return fileManipulator;
	}
	
	public org.castafiore.wfs.session.Session getCastafioreSession(){
		return new SessionImpl(tusername.get(), this);
	}

	public void setFileManipulator(FileManipulator fileManipulator) {
		fileManipulator.setDao(dao);
		fileManipulator.setRepositoryService(this);
		this.fileManipulator = fileManipulator;
	}

	private Session getSession(){
		return dao.getSession();
	}
	
	public String getSuperUser() {
		return superUser;
	}

	public void setSuperUser(String superUser) {
		this.superUser = superUser;
	}
	
	public Dao getDao() {
		return dao;
	}

	public void setDao(Dao dao) {
		this.dao = dao;
	}

	public QueryExecutor getQueryExecutor() {
		return queryExecutor;
	}

	public void setQueryExecutor(QueryExecutor queryExecutor) {
		this.queryExecutor = queryExecutor;
	}

	public boolean itemExists(String path)throws InsufficientPriviledgeException{
		
		try{
			

			Object result = dao.getReadOnlySession().createSQLQuery("select count(*) from WFS_FILE where absolutepath =:absp").setParameter("absp", path).uniqueResult();
			
			return Integer.parseInt(result.toString()) > 0;
		}
		catch(Exception e)	{
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Drive> getDrives(String username){
		QueryParameters param = new QueryParameters().setEntity(Drive.class);
		List tmp = queryExecutor.executeQuery(param, dao.getReadOnlySession());
		
		return tmp;
	}

	@SuppressWarnings("unchecked")
	public File getFile(String path, String username)throws InsufficientPriviledgeException, FileNotFoundException {
		tusername.set(username);
		File result = (File)dao.getReadOnlySession().get(FileImpl.class, path);
		if(result == null){
			throw new FileNotFoundException("the file " + path + " could not be found");
		}else{
			return result;
		}
	}
	
	public Directory getDirectory(String path, String username)throws InsufficientPriviledgeException, InvalidFileTypeException, FileNotFoundException{
		try{
			tusername.set(username);
			return (Directory)getFile(path, username);
		}
		catch(ClassCastException cc){
			throw new InvalidFileTypeException("The file " + path + " is not a directory", cc);
		}
		 
	}
	
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void delete(File file, String username) throws  InsufficientPriviledgeException, LockedFileException {
		File parent = file.getParent();
		//file.evict();
		getSession().update(parent);
		getSession().delete(file);
		tusername.set(username);
	}
 
//	@Transactional(propagation=Propagation.SUPPORTS)
//	public File getFileById(int id, String username)throws InsufficientPriviledgeException{
//		tusername.set(username);
//		return (File)getSession().get(File.class, id);
//	}
	

	@Transactional(propagation=Propagation.REQUIRED)
	public File save(File file, String username) throws FileAlreadyExistException, InsufficientPriviledgeException, LockedFileException {
		
		if(file.getName() == null || file.getName().length() == 0){
			throw new IllegalArgumentException("you cannot add a file without setting its name first");
		}
		
		if(file.getAbsolutePath() == null || file.getAbsolutePath().length() == 0){
			throw new IllegalArgumentException("the file " + file.getName() + " cannot be updated since it was not save previously. Please use the saveIn method to save it in a directory first");
		}
		
		if(file.getOwner() != null && file.getOwner().length() >0){}
		
		else{
			file.setOwner(username);
		}
		
		Session session = getSession();
		try{
			session.saveOrUpdate(file);
			session.flush();
			
		}catch(NonUniqueObjectException nue){
		}catch(HibernateException hbe){
			
		}
		tusername.set(username);
		return file;
	}
	
	


	
	@Transactional(propagation=Propagation.REQUIRED)
	public void refresh(File file){
		getSession().merge(file);
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<File> getFilesOfType(Class<? extends File> type, String username) throws DataAccessException {
		tusername.set(username);
		
		return queryExecutor.executeQuery(new QueryParameters().setEntity(type), dao.getReadOnlySession());
		
	}
	
	
	


	@Transactional(propagation=Propagation.REQUIRED)
	public void lockFile(File file, String username) {
		tusername.set(username);
		//this method has got its implementation in the LockingInterptor
		
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void unlockFile(File file, String username) {
		tusername.set(username);
		// this method has got its implementation in the LockingInterptor
		
	}
	
	public List<File> executeQuery(QueryParameters parameters , String username){
		tusername.set(username);
		return new ScrollableList(new ScrollableFileIterator(10,parameters,username));
		//return this.queryExecutor.executeQuery(parameters, dao.getReadOnlySession());
	}

	@Override
	public Integer countRows(QueryParameters parameters, String username) {
		tusername.set(username);
		return queryExecutor.count(parameters, dao.getReadOnlySession());
	}

	@Override
	 public int getNextSequence(String sequenceName, String username, String organization) {
		QueryParameters params = new QueryParameters().setEntity(Sequence.class).addRestriction(Restrictions.eq("name", sequenceName)).addSearchDir("/root/users/" + organization);
		 List<File> result = executeQuery(params, username);
		 if(result.size() == 0){
			 Directory dir = getDirectory("/root/users/"+ organization, username);
			 Sequence sequence = dir.createFile(sequenceName, Sequence.class);
			 sequence.setDouble(1.0);
			 dir.save();
			 return sequence.getNextSequence();
		 }else{
			 Sequence sequence = (Sequence)result.get(0);
			 sequence.setDouble(sequence.getDouble() + 1);
			 sequence.save();
			 return sequence.getNextSequence();
		 }
	}
	
	@Override
	 public int getNextSequence(String sequenceName, String username) {
		 return getNextSequence(sequenceName,username, Util.getLoggedOrganization());
	 }

	@Override
	public int getNextSequence(String username) {
		
		String sequenceName = "sequence_" + System.currentTimeMillis();
		QueryParameters params = new QueryParameters().setEntity(Sequence.class).addSearchDir("/root/users/" + Util.getLoggedOrganization());
		 List<File> result = executeQuery(params, username);
		 if(result.size() == 0){
			 Directory dir = getDirectory("/root/users/"+ Util.getLoggedOrganization(), username);
			 Sequence sequence = dir.createFile(sequenceName, Sequence.class);
			 sequence.setDouble(1.0);
			 dir.save();
			 return sequence.getNextSequence();
		 }else{
			 Sequence sequence = (Sequence)result.get(0);
			 sequence.setDouble(sequence.getDouble() + 1);
			 sequence.save();
			 return sequence.getNextSequence();
		 }
	}
	
	
	

}
