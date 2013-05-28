package com.maureva.autodownload.model;

import java.util.Date;

public class FileEntry {
	
	private Date dateRetreived;
	
	private String siteRetrived;
	
	private int status;
	
	private int fileId;
	
	private int id;
	
	private String originalName;
	
	private String newName;

	public Date getDateRetreived() {
		return dateRetreived;
	}

	public void setDateRetreived(Date dateRetreived) {
		this.dateRetreived = dateRetreived;
	}

	public String getSiteRetrived() {
		return siteRetrived;
	}

	public void setSiteRetrived(String siteRetrived) {
		this.siteRetrived = siteRetrived;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}
	
	

}
