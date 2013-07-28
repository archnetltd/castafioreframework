package org.castafiore.appstore;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.spring.SpringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class AppStoreUtil {
	private static List<String> categories = new ArrayList<String>();
	static {
		categories.add("Books");
		categories.add("Business");
		categories.add("Education");
		categories.add("Entertainment");
		categories.add("Finance");
		categories.add("Games");
		categories.add("Health and fitness");
		categories.add("Lifestyle");
		categories.add("Medical");
		categories.add("Music");
		categories.add("Navigation");
		categories.add("News");
		categories.add("Photography");
		categories.add("Productivity");
		categories.add("Reference");
		categories.add("Social networking");
		categories.add("Sports");
		categories.add("Travel");
		categories.add("Utilities");
		categories.add("Weather");
		
	}
	public static List<String> getCategories(){
		return categories;
	}
	
	
	public static List<AppPackage> getApplicationsByCategory(String category){
		QueryParameters params = new QueryParameters();
		params.setEntity(AppPackage.class).addRestriction(Restrictions.eq("category", category)).addOrder(Order.asc("dateCreated"));
		
		List result = SpringUtil.getRepositoryService().executeQuery(params, Util.getRemoteUser());
		return result;
	}
	
	
	public static List<AppPackage> getMyApplications(String username){
		QueryParameters params = new QueryParameters();
		params.setEntity(AppPackage.class).addRestriction(Restrictions.eq("providedBy", username)).addOrder(Order.asc("dateCreated"));
		
		List result = SpringUtil.getRepositoryService().executeQuery(params, Util.getRemoteUser());
		return result;
	}
	

}
