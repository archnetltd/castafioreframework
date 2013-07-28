package org.castafiore.security;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity

@Table(name="SECU_RELATIONSHIP")
public class Relationship implements Serializable{
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private String relationship;
	
	private String firstOrganization;
	
	private String secondOrganization;

	

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public String getFirstOrganization() {
		return firstOrganization;
	}

	public void setFirstOrganization(String firstOrganization) {
		this.firstOrganization = firstOrganization;
	}

	public String getSecondOrganization() {
		return secondOrganization;
	}

	public void setSecondOrganization(String secondOrganization) {
		this.secondOrganization = secondOrganization;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	
}
