package org.castafiore.designer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;




public class DesignableCategoryComparator implements Comparator<String> {
	
	private List<String> categories = new ArrayList<String>();

	@Override
	public int compare(String s1, String s2) {
		Integer pos1 = 100000;
		Integer pos2 = 100000;
		if(categories.contains(s1)){
			pos1 = categories.indexOf(s1);
		}
		
		if(categories.contains(s2)){
			pos2 = categories.indexOf(s2);
		}
		return pos1.compareTo(pos2);
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}
	
	
	

}
