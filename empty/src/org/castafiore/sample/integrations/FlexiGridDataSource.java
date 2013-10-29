package org.castafiore.sample.integrations;

import java.util.List;

public interface FlexiGridDataSource {
	
	public List<FlexiGridDataRow> getPage(int page,int pageSize, String sort, String order, String query);
	
	
	public int getSize();

}
