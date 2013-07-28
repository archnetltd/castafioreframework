package org.castafiore.mongo;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class MongoNode extends BasicDBObject implements DBObject{
	
	private List<MongoNode> tmp = new LinkedList<MongoNode>();
	
	private MongoNode parent_=null;
	
	private boolean toremove = false;
	
	public String getOwner(){
		return super.getString("owner");
	}
	
	public Date getDateModified(){
		return super.getDate("dateModified");
	}

	public void setOwner(String owner){
		super.put("owner", owner);
	}
	
	public String[] getNodeTypes(){
		
		return ((List<String>)get("nodeTypes")).toArray(new String[]{});
	}
	
	public void addNodeType(String nt){
		List<String> nts = ((List<String>)get("nodeTypes"));
		if(!nts.contains(nt)){
			nts.add(nt);
			put("nodeTypes",nts);
		}
	}

	public long getSize() {
		return getLong("size");
	}

	public void setSize(long size) {
		put("size", size);
	}
	
	public String getName(){
		return getString("name");
	}


	public MongoNode getParent() {
		if(parent_ != null)
			return parent_;
		String id = getString("parent");
		BasicDBObject q = new BasicDBObject();
		q.put("_id", id);
		parent_ =(MongoNode)Mongo.c.findOne(q);
		return parent_;
	}


	

	public Date getDateCreated(){
		return getDate("dateCreated");
	}
	
	public void save(){
		if(toremove){
			String id = getString("_id");
			Pattern children = Pattern.compile("\\A"+id+"/[.]*");
			BasicDBObject query = new BasicDBObject("_id", children);

			Mongo.c.remove(query);
			
			Mongo.c.remove(this);
			
			MongoNode parent = getParent();
			if(parent != null){
				List<String> cids = (List<String>)parent.get("children");
				
				cids.remove(id);
				parent.put("children", cids);
				Mongo.c.save(parent_);
				
			}
			return;
		}
		
		
		if(parent_!= null){
			Mongo.c.save(parent_);
			
		}
		Mongo.c.save(this);
		
		for(MongoNode f : tmp){
			Mongo.c.save(f);
		}
	}
	
	public void remove(){
		this.toremove = true;
	}
	
	
	public boolean isLocked(){
		return getBoolean("locked");
	}
	
	public void lock(){
		put("locked", true);
	}
	
	public void releaseLock(){
		put("locked", false);
	}

	public int getStatus(){
		return getInt("status");
	}

	public void setStatus(int status){
		put("status", status);
	}
	
	public void setMetadata(String name, String value){
		((DBObject)super.get("metadata")).put(name, value);
	}
	
	public boolean hasMetadata(String name){
		return ((DBObject)super.get("metadata")).containsField(name);
	}
	
	public String getMetadata(String name){
		return ((DBObject)super.get("metadata")).get(name).toString();
	}
	
	public Map getMetadata(){
		BasicDBObject mets = ((BasicDBObject)super.get("metadata"));
		return mets;
	}
	
	public MongoNode createNode(String name, String nodeType){
		MongoNode f = new MongoNode();
		f.put("dateCreated", new Date());
		f.put("dateModified", new Date());
		List<String> nts  = (List<String>) f.get("nodeTypes");
		if(nts == null)
			nts = new ArrayList<String>(1);
		if(!nts.contains(nodeType)){
			nts.add(nodeType);
			f.put("nodeTypes", nts);
		}
		f.put("_id", get("_id") + "/" + name);
		f.put("name", name);
		f.put("parent", super.get("_id"));
		List<String> children = ((List<String>) get("children"));
		if(children == null)
			children = new ArrayList<String>(1);
		children.add(name);
		f.parent_ = this;
		tmp.add(f);
		return f;
	}
	
	
	public String getPath(){
		return getString("_id");
	}
	
	public DBCursor getNodes(){
		String id = getString("_id");
		Pattern children = Pattern.compile("\\A"+id+"/[^/]*");
		BasicDBObject query = new BasicDBObject("_id", children);
		return Mongo.c.find(query);
	}

}
