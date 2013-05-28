package com.maureva.autodownload.retreive;

import java.io.Reader;

public class FileData {
	
	private String name;
	
	private String id;
	
	private int size;
	
	private Reader fileReader;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Reader getFileReader() {
		return fileReader;
	}

	public void setFileReader(Reader fileReader) {
		this.fileReader = fileReader;
	}
	
	

}
