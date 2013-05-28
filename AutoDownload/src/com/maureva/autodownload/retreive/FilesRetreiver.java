package com.maureva.autodownload.retreive;

import java.util.List;
import java.util.Map;

public interface FilesRetreiver {
	
	public String getProtocol();
	
	public Map<String, String> getCredentials();
	
	public void authenticate();
	
	public List<FileData> getFiles();
	
	public void close();
	
	public String getCustomer();

}
