package org.castafiore.mongo;

import java.net.UnknownHostException;
import java.util.List;

import javax.jcr.AccessDeniedException;
import javax.jcr.NamespaceException;
import javax.jcr.NamespaceRegistry;
import javax.jcr.RepositoryException;
import javax.jcr.UnsupportedRepositoryOperationException;

import org.castafiore.mongo.dao.MongoDao;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class MongoNamespaceRegistry implements NamespaceRegistry{
	
	private List<DBObject> data;
	
	private String repository;
	
	public MongoNamespaceRegistry(String repository) throws UnknownHostException{
		this.repository = repository;
		data = MongoDao.getInstance(repository).getNamespaceRegistryCollection().find().toArray();
	}

	@Override
	public String getPrefix(String uri) throws NamespaceException,
			RepositoryException {
		
		for(DBObject o : data){
			BasicDBObject ob = (BasicDBObject)o;
			if(ob.getString("uri").equals(uri)){
				return ob.getString("prefix");
			}
		}
		throw new NamespaceException("There is no mapping for the uri " + uri);
		
	}

	@Override
	public String[] getPrefixes() throws RepositoryException {
		String[] result = new String[data.size()];
		int count = 0;
		for(DBObject o : data){
			BasicDBObject ob = (BasicDBObject)o;
			result[count] = ob.getString("prefix");
		}
		return result;
	}

	@Override
	public String getURI(String prefix) throws NamespaceException,
			RepositoryException {
		for(DBObject o : data){
			BasicDBObject ob = (BasicDBObject)o;
			if(ob.getString("prefix").equals(prefix)){
				return ob.getString("uri");
			}
		}
		throw new NamespaceException("There is no mapping for the prefix " + prefix);
	}

	@Override
	public String[] getURIs() throws RepositoryException {
		String[] result = new String[data.size()];
		int count = 0;
		for(DBObject o : data){
			BasicDBObject ob = (BasicDBObject)o;
			result[count] = ob.getString("uri");
		}
		return result;
	}

	@Override
	public void registerNamespace(String prefix, String uri)
			throws NamespaceException, UnsupportedRepositoryOperationException,
			AccessDeniedException, RepositoryException {
		BasicDBObject o = new BasicDBObject();
		o.put("prefix", prefix);
		o.put("uri", uri);
		
		try {
			MongoDao.getInstance(repository).getNamespaceRegistryCollection().save(o);
		} catch (UnknownHostException e) {
			throw new NamespaceException(e);
		}
	}

	@Override
	public void unregisterNamespace(String prefix) throws NamespaceException,
			UnsupportedRepositoryOperationException, AccessDeniedException,
			RepositoryException {
		BasicDBObject o = new BasicDBObject();
		o.put("prefix", prefix);
		
		
		try {
			MongoDao.getInstance(repository).getNamespaceRegistryCollection().findAndRemove(o);
		} catch (UnknownHostException e) {
			throw new NamespaceException(e);
		}
		
	}

}
