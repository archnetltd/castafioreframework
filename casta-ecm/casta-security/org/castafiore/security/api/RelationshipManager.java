package org.castafiore.security.api;

import java.io.Serializable;
import java.util.List;

import org.castafiore.security.Relationship;

public interface RelationshipManager extends Serializable{
	
	
	public List<String> getRelatedOrganizations(String firstOrganization);
	
	public List<String> getRelatedOrganizations(String firstOrganization, String relationship);
	
	public void saveRelationship(String firstOrganization, String secondOrganization, String relationship);
	
	public void removeRelationships(String firstOrganization, String secondOrganization, String relationship);
	
	public void removeRelationships(String firstOrganization, String secondOrganization);
	
	public void removeRelationships(String firstOrganization);
	
	
	public List<Relationship> getRelationships(String organization);
	
	public List<String> getDistinctRelations(String organization);
	
	
	public boolean hasRelationship(String firstOrganization, String secondOrganization, String relationship);
	
	public boolean hasRelationship(String firstOrganization, String secondOrganization);
	
	
}
