package org.castafiore.mongo;

import javax.jcr.Credentials;
import javax.jcr.LoginException;
import javax.jcr.NoSuchWorkspaceException;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class MongoRepository  implements Repository, DBObjec {
	private BasicDBObject d;
	
	
	
	
	
	public Organization getOrganization(String name){
		BasicDBObject o = new BasicDBObject();
		o.put("_id", name);
		return (Organization)Mongo.o.findOne(o);
	}

	@Override
	public String[] getDescriptorKeys() {
		return super.get
	}

	@Override
	public boolean isStandardDescriptor(String key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSingleValueDescriptor(String key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Value getDescriptorValue(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value[] getDescriptorValues(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescriptor(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Session login(Credentials credentials, String workspaceName)
			throws LoginException, NoSuchWorkspaceException,
			RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Session login(Credentials credentials) throws LoginException,
			RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Session login(String workspaceName) throws LoginException,
			NoSuchWorkspaceException, RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Session login() throws LoginException, RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

}
