package org.castafiore.mongo;



import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jcr.AccessDeniedException;
import javax.jcr.Credentials;
import javax.jcr.InvalidItemStateException;
import javax.jcr.InvalidSerializedDataException;
import javax.jcr.Item;
import javax.jcr.ItemExistsException;
import javax.jcr.ItemNotFoundException;
import javax.jcr.LoginException;
import javax.jcr.NamespaceException;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.ReferentialIntegrityException;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.UnsupportedRepositoryOperationException;
import javax.jcr.ValueFactory;
import javax.jcr.Workspace;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.retention.RetentionManager;
import javax.jcr.security.AccessControlManager;
import javax.jcr.version.VersionException;
import javax.persistence.Transient;

import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import com.mongodb.BasicDBObject;

public class MongoSession implements Session{
	
	private String organization;
	
	private boolean haspending_ = false;
	private MongoNode root;

	private transient  Workspace workspace;
	
	
	public MongoSession(Workspace workspace) {
		super();
		//this.organization = organization;
		this.workspace = workspace;
	}



//	public MongoNode getNode(String absPath){
//		BasicDBObject o = new BasicDBObject();
//		o.put("_id", absPath);
//		return (MongoNode)Mongo.c.findOne(o);
//	}
//
//	
//	
//	public MongoNode getRootNode() {
//		root = getNode("/" + organization);
//		if(root == null){
//			root = new MongoNode();
//			List<String> nts = new ArrayList<String>();
//			nts.add("nt:folder");
//			nts.add("nt:drive");
//			nts.add("nt:root");
//			root.put("nodeTypes", nts);
//			root.put("dateCreated", new Date());
//			root.put("dateModified", new Date());
//			root.put("owner", organization);
//			root.put("_id", "/" + organization);
//			root.put("name", organization);
//			root.save();
//		}
//		
//		return root;
//	}
//
//	
//	public String getUserID() {
//		return organization;
//	}
//
//	
//
//
//	public boolean hasPendingChanges() {
//		return haspending_;
//	}
//
//	
//
//	
//	public boolean itemExists(String absPath)  {
//		BasicDBObject o = new BasicDBObject();
//		o.put("_id", absPath);
//		return Mongo.c.count(o) > 0;
//	}
//
//	
//	public void logout() {
//		root = null;
//		
//	}
//
//	
//	
//
//	public boolean nodeExists(String absPath) {
//		return itemExists(absPath);
//	}
//	
//	public void save() {
//		root.save();
//		
//	}



	@Override
	public Repository getRepository() {
		Repository r = null;
		r.
	}



	@Override
	public String[] getAttributeNames() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Object getAttribute(String name) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Workspace getWorkspace() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Session impersonate(Credentials credentials) throws LoginException,
			RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Node getNodeByUUID(String uuid) throws ItemNotFoundException,
			RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Node getNodeByIdentifier(String id) throws ItemNotFoundException,
			RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Item getItem(String absPath) throws PathNotFoundException,
			RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Property getProperty(String absPath) throws PathNotFoundException,
			RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public boolean propertyExists(String absPath) throws RepositoryException {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public void move(String srcAbsPath, String destAbsPath)
			throws ItemExistsException, PathNotFoundException,
			VersionException, ConstraintViolationException, LockException,
			RepositoryException {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void removeItem(String absPath) throws VersionException,
			LockException, ConstraintViolationException, AccessDeniedException,
			RepositoryException {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void refresh(boolean keepChanges) throws RepositoryException {
		// TODO Auto-generated method stub
		
	}



	@Override
	public ValueFactory getValueFactory()
			throws UnsupportedRepositoryOperationException, RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public boolean hasPermission(String absPath, String actions)
			throws RepositoryException {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public void checkPermission(String absPath, String actions)
			throws AccessControlException, RepositoryException {
		// TODO Auto-generated method stub
		
	}



	@Override
	public boolean hasCapability(String methodName, Object target,
			Object[] arguments) throws RepositoryException {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public ContentHandler getImportContentHandler(String parentAbsPath,
			int uuidBehavior) throws PathNotFoundException,
			ConstraintViolationException, VersionException, LockException,
			RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void importXML(String parentAbsPath, InputStream in, int uuidBehavior)
			throws IOException, PathNotFoundException, ItemExistsException,
			ConstraintViolationException, VersionException,
			InvalidSerializedDataException, LockException, RepositoryException {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void exportSystemView(String absPath, ContentHandler contentHandler,
			boolean skipBinary, boolean noRecurse)
			throws PathNotFoundException, SAXException, RepositoryException {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void exportSystemView(String absPath, OutputStream out,
			boolean skipBinary, boolean noRecurse) throws IOException,
			PathNotFoundException, RepositoryException {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void exportDocumentView(String absPath,
			ContentHandler contentHandler, boolean skipBinary, boolean noRecurse)
			throws PathNotFoundException, SAXException, RepositoryException {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void exportDocumentView(String absPath, OutputStream out,
			boolean skipBinary, boolean noRecurse) throws IOException,
			PathNotFoundException, RepositoryException {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void setNamespacePrefix(String prefix, String uri)
			throws NamespaceException, RepositoryException {
		// TODO Auto-generated method stub
		
	}



	@Override
	public String[] getNamespacePrefixes() throws RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public String getNamespaceURI(String prefix) throws NamespaceException,
			RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public String getNamespacePrefix(String uri) throws NamespaceException,
			RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public boolean isLive() {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public void addLockToken(String lt) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public String[] getLockTokens() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void removeLockToken(String lt) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public AccessControlManager getAccessControlManager()
			throws UnsupportedRepositoryOperationException, RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public RetentionManager getRetentionManager()
			throws UnsupportedRepositoryOperationException, RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public String getUserID() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Node getRootNode() throws RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Node getNode(String absPath) throws PathNotFoundException,
			RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public boolean itemExists(String absPath) throws RepositoryException {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean nodeExists(String absPath) throws RepositoryException {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public void save() throws AccessDeniedException, ItemExistsException,
			ReferentialIntegrityException, ConstraintViolationException,
			InvalidItemStateException, VersionException, LockException,
			NoSuchNodeTypeException, RepositoryException {
		// TODO Auto-generated method stub
		
	}



	@Override
	public boolean hasPendingChanges() throws RepositoryException {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public void logout() {
		// TODO Auto-generated method stub
		
	}



}
