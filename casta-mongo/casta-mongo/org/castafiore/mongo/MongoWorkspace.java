package org.castafiore.mongo;

import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;

import javax.jcr.AccessDeniedException;
import javax.jcr.InvalidItemStateException;
import javax.jcr.InvalidSerializedDataException;
import javax.jcr.ItemExistsException;
import javax.jcr.NamespaceRegistry;
import javax.jcr.NoSuchWorkspaceException;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.UnsupportedRepositoryOperationException;
import javax.jcr.Workspace;
import javax.jcr.lock.LockException;
import javax.jcr.lock.LockManager;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NodeTypeManager;
import javax.jcr.observation.ObservationManager;
import javax.jcr.query.QueryManager;
import javax.jcr.version.Version;
import javax.jcr.version.VersionException;
import javax.jcr.version.VersionManager;

import org.castafiore.mongo.dao.MongoDao;
import org.xml.sax.ContentHandler;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class MongoWorkspace implements Workspace{
	
	private String repository;
	
	

	protected MongoWorkspace(String repository) {
		super();
		this.repository = repository;
	}

	@Override
	public void clone(String srcWorkspace, String srcAbsPath,
			String destAbsPath, boolean removeExisting)
			throws NoSuchWorkspaceException, ConstraintViolationException,
			VersionException, AccessDeniedException, PathNotFoundException,
			ItemExistsException, LockException, RepositoryException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void copy(String srcAbsPath, String destAbsPath)
			throws ConstraintViolationException, VersionException,
			AccessDeniedException, PathNotFoundException, ItemExistsException,
			LockException, RepositoryException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void copy(String srcWorkspace, String srcAbsPath, String destAbsPath)
			throws NoSuchWorkspaceException, ConstraintViolationException,
			VersionException, AccessDeniedException, PathNotFoundException,
			ItemExistsException, LockException, RepositoryException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createWorkspace(String name) throws AccessDeniedException,
			UnsupportedRepositoryOperationException, RepositoryException {
		try {
			MongoDao.getInstance(repository).getWorkspace(name);
		} catch (UnknownHostException e) {
			throw new RepositoryException(e);
		}
		
	}

	@Override
	public void createWorkspace(String name, String srcWorkspace)
			throws AccessDeniedException,
			UnsupportedRepositoryOperationException, NoSuchWorkspaceException,
			RepositoryException {
		try {
			if((MongoDao.getInstance(repository).hasWorkspace(srcWorkspace))){
				DBCollection neww = MongoDao.getInstance(repository).getWorkspace(name);
				DBCollection src = MongoDao.getInstance(repository).getWorkspace(srcWorkspace);
				DBCursor cursor= src.find();
				while(cursor.hasNext()){
					DBObject o = cursor.next();
					neww.save(o);
				}
			}else{
				throw new NoSuchWorkspaceException("The workspace " + srcWorkspace + " does not exist in the repository " + repository);
			}
			
		} catch (UnknownHostException e) {
			throw new RepositoryException(e);
		}
		
	}

	@Override
	public void deleteWorkspace(String name) throws AccessDeniedException,
			UnsupportedRepositoryOperationException, NoSuchWorkspaceException,
			RepositoryException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String[] getAccessibleWorkspaceNames() throws RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContentHandler getImportContentHandler(String parentAbsPath,
			int uuidBehavior) throws PathNotFoundException,
			ConstraintViolationException, VersionException, LockException,
			AccessDeniedException, RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LockManager getLockManager()
			throws UnsupportedRepositoryOperationException, RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NamespaceRegistry getNamespaceRegistry() throws RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NodeTypeManager getNodeTypeManager() throws RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObservationManager getObservationManager()
			throws UnsupportedRepositoryOperationException, RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QueryManager getQueryManager() throws RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Session getSession() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VersionManager getVersionManager()
			throws UnsupportedRepositoryOperationException, RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void importXML(String parentAbsPath, InputStream in, int uuidBehavior)
			throws IOException, VersionException, PathNotFoundException,
			ItemExistsException, ConstraintViolationException,
			InvalidSerializedDataException, LockException,
			AccessDeniedException, RepositoryException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move(String srcAbsPath, String destAbsPath)
			throws ConstraintViolationException, VersionException,
			AccessDeniedException, PathNotFoundException, ItemExistsException,
			LockException, RepositoryException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void restore(Version[] versions, boolean removeExisting)
			throws ItemExistsException,
			UnsupportedRepositoryOperationException, VersionException,
			LockException, InvalidItemStateException, RepositoryException {
		// TODO Auto-generated method stub
		
	}

}
