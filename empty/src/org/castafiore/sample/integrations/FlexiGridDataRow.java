package org.castafiore.sample.integrations;

import org.castafiore.utils.ListOrderedMap;

public class FlexiGridDataRow extends ListOrderedMap<String, String>{
	
	
	private String id;
	
	
	public FlexiGridDataRow(String id) {
		super();
		this.id = id;
	}


	public String getId(){
		return id;
	}

}
