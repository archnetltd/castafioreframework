package org.castafiore.wfs.types;

import javax.persistence.Entity;

import org.hibernate.annotations.Type;

@Entity
public class Link extends Directory{
	
	@Type(type="text")
	private String label;
	
	@Type(type="text")
	private String url;
	
	public Link(){
		super();
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	

}
