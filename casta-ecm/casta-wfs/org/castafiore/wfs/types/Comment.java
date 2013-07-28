package org.castafiore.wfs.types;

import javax.persistence.Entity;

import org.hibernate.annotations.Type;

@Entity
public class Comment extends Article {
	
	//public final static int STATE_READ = 
	
	@Type(type="text")
	private String author;
	
	public Comment(){
		super();
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	

}
