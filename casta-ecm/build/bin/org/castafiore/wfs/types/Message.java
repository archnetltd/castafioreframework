package org.castafiore.wfs.types;

import java.util.List;

import javax.persistence.Entity;

import org.hibernate.annotations.Type;

@Entity
public class Message extends Comment{
	
	public Message(){
		super();
	}
	
	@Type(type="text")
	private String destination;

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	public List<Comment> getReplies(){
		return getComments().toList();
	}
	
	

}
