package org.castafiore.shoppingmall.imports;

import java.util.Map;

public interface ImporterTemplate<T> {
	
	public Map<String, String> transform(T instance);
	
	public <T extends Object> T getNextSource();
	
	public boolean hasNextSource();
	
	public void flush();
	
	public int getBatchSize();
	
	public void doImportInstance(Map<String,String> data);
}
