package org.castafiore.shoppingmall;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.castafiore.catalogue.Product;
import org.castafiore.persistence.Dao;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.engine.context.CastafioreApplicationContextHolder;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.FileNotFoundException;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.iterator.FileIterator;
import org.castafiore.wfs.service.QueryParameters;
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ShoppingMallImpl implements ShoppingMall{
	
	private RepositoryService repositoryService;
	
	private String root = ROOT;
	
	private final static String FEATURED = "$mall/featured";
	
	private final static String OFFERED =  "$mall/offered";
	
	private final static String CATEGORY =  "$mall/categories";
	
	private final static String MERCHANTS =  "/root/users/$user/Applications/e-Shop"; //"$mall/merchants";
	
	private boolean init = false;
	
	//private Map<String, Merchant> cache_ = new WeakHashMap<String, Merchant>();
	
	
	
	public ShoppingMallImpl(RepositoryService repositoryService, String root) {
		super();
		this.repositoryService = repositoryService;
		this.root = root;
	}

	
	private Map<String, List<String>> loadCategories()throws Exception{
		Map<String, List<String>> result = new HashMap<String, List<String>>();
		InputStream xml = Thread.currentThread().getContextClassLoader().getResourceAsStream("org/castafiore/shoppingmall/config/Categories.xml");
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(xml);
		doc.getDocumentElement().normalize();
		NodeList nodeList = doc.getChildNodes();
		nodeList = nodeList.item(0).getChildNodes();
		for(int i = 0; i < nodeList.getLength(); i ++){
			Node n = nodeList.item(i);
			
			String name = n.getNodeName();
			if(name.equals("category")){
				String category = n.getAttributes().getNamedItem("name").getTextContent().replace("|apm|", "&");
				//System.out.println(category);
				result.put(category, new ArrayList<String>());
				NodeList categories = n.getChildNodes();
				for(int j = 0; j < categories.getLength(); j ++){
					if(categories.item(j).getNodeName().equalsIgnoreCase("category")){
						String sCategory = categories.item(j).getAttributes().getNamedItem("name").getTextContent().replace("|apm|", "&");
						if(sCategory != null && sCategory.length() > 0){
							//System.out.println("\t" +sCategory);
							result.get(category).add(sCategory);
						}
					}
				}
			}
		}
		return result;
	}
	
	private void createCategories(Directory dirCat){
		try{
			Map<String, List<String>> cats = loadCategories();
			
			Iterator<String> iterCats = cats.keySet().iterator();
			while(iterCats.hasNext()){
				String category = iterCats.next();
				Directory dirCategory = dirCat.createFile(category, Directory.class);//new Directory();
				
				dirCategory.makeOwner(repositoryService.getSuperUser());
				
				List<String> lstSubCategories = cats.get(category);
				for(String subCategory : lstSubCategories){
					Directory dirSubCategory = dirCategory.createFile(subCategory, Directory.class);//new Directory();
					
					dirSubCategory.makeOwner(repositoryService.getSuperUser());
					
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public void reCreateCategories(){
		String superUser = repositoryService.getSuperUser();
		Directory dirCat = (Directory)repositoryService.getFile(CATEGORY.replace("$mall", root),repositoryService.getSuperUser() );
		repositoryService.delete(dirCat, repositoryService.getSuperUser());
		
		
		Directory mall = (Directory)repositoryService.getFile(root, superUser);
		dirCat = (Directory)mall.createFile("categories", Directory.class).makeOwner(superUser);
		
		createCategories(dirCat);
		dirCat.save();
	}
	
	private void createMall(){
		String superUser = repositoryService.getSuperUser();
		String parent = ResourceUtil.getParentPath(root);
		Directory p = repositoryService.getDirectory(parent, Util.getRemoteUser());
		Directory mall = p.createFile(ResourceUtil.getNameFromPath(root), Directory.class);
		
		
		mall.makeOwner(superUser);
		mall.createFile("featured",  Directory.class).makeOwner(superUser);
		mall.createFile("offered",  Directory.class).makeOwner(superUser);
		
		Directory categories = (Directory)mall.createFile("categories", Directory.class).makeOwner(superUser);
		
		mall.createFile("sysparams", Directory.class).makeOwner(superUser);
		mall.createFile("merchants", Directory.class).makeOwner(superUser);
		
		createCategories(categories);
		p.save();
	}
	
	public void checkAndInit(){
		
		if(init)return;
		try{
			File f = repositoryService.getFile(FEATURED.replace("$mall", root),repositoryService.getSuperUser());
		}catch(FileNotFoundException nfe){
			createMall();
		}
		
		init = true;
	}

	private List<Product> buildList(FileIterator<Product> iterator){
		return iterator.toList();
	}
	
	@Override
	public List<Product> getRecentProducts(int max){
		QueryParameters params = new QueryParameters().setEntity(Product.class).addRestriction(Restrictions.eq("status", Product.STATE_PUBLISHED)).addOrder(Order.desc("dateCreated")).setFirstResult(0).setMaxResults(max);
		List result = repositoryService.executeQuery(params, Util.getRemoteUser());
		return result;
	}
	
	@Override
	public List<Product> getFeatureProducts(int max) {
		
		
		QueryParameters params = new QueryParameters().setEntity(Product.class).addRestriction(Restrictions.eq("status", Product.STATE_PUBLISHED)).addRestriction(Restrictions.eq("featured", true)).addOrder(Order.desc("dateCreated")).setFirstResult(0).setMaxResults(max);
		List result = repositoryService.executeQuery(params, Util.getRemoteUser());
		return result;
	}

	
	public List<Product> getOfferedProducts() {
		return buildList(repositoryService.getDirectory(OFFERED.replace("$mall", root), Util.getRemoteUser()).getFiles(Product.class));
	}

	@Override
	public Product getProduct(String productPath) {
		return (Product)repositoryService.getFile(productPath, Util.getRemoteUser());
	}
	
	@Override
	public Merchant createMerchant(String name){
		Directory dir = repositoryService.getDirectory(MERCHANTS.replace("$user", name), repositoryService.getSuperUser());
		return dir.createFile(name,Merchant.class);
	}
	
	@Override
	public Merchant getMerchant(String username){
		if(StringUtil.isNotEmpty(username)){
			Map<String, Merchant> ms = (Map<String, Merchant>)CastafioreApplicationContextHolder.getContextValue("merchants");
			//Map<String, Merchant> ms = TM.get();
			if(ms == null){
				ms = new HashMap<String, Merchant>();
				CastafioreApplicationContextHolder.addInContext("merchants", ms);
			}
			
			if(ms.containsKey(username)){
				return ms.get(username);
			}else{
				Merchant m = (Merchant)repositoryService.getFile(MERCHANTS.replace("$user", username) + "/" + username, repositoryService.getSuperUser());
				ms.put(username, m);
				return m;
			}
		}return null;
	}
	
	public List<Merchant> getMerchants(){
		List result = repositoryService.executeQuery(new QueryParameters().setEntity(Merchant.class).addRestriction(Restrictions.eq("status", 12)), Util.getRemoteUser());
		return result;
		//return repositoryService.getDirectory(MERCHANTS.replace("$mall", root), Util.getRemoteUser()).getFiles(Merchant.class).toList();
	}

	@Override
	public Directory getRootCategory() {
		if(Util.getLoggedOrganization() != null){
			if(repositoryService.itemExists(MERCHANTS.replace("$user", Util.getLoggedOrganization()) + "/categories")){
				return repositoryService.getDirectory(MERCHANTS.replace("$user", Util.getLoggedOrganization())+ "/categories", Util.getRemoteUser());
			}
		}
		return repositoryService.getDirectory(CATEGORY.replace("$mall", root), Util.getRemoteUser());
	}

	

	
	public int countSearch(String search){
		QueryParameters params =parseSearch(search);
		params.addRestriction(Restrictions.eq("status", Product.STATE_PUBLISHED));
		return repositoryService.countRows(params, Util.getRemoteUser());
		
	}
	
	@Override
	public List<Product> searchProducts(String search, int page, int pageSize) {
		QueryParameters params =parseSearch(search).setFirstResult(pageSize*page).setMaxResults(pageSize).setEntity(Product.class).addRestriction(Restrictions.eq("status", Product.STATE_PUBLISHED));
		List result = repositoryService.executeQuery(params, Util.getRemoteUser());
		return result;
	}
	
	
	
	//cat:<>/<>,full:<>, tag:<>, provider:<>, related:<>
	public QueryParameters parseSearch(String search){
		if(StringUtil.isNotEmpty(search)){
			
			String[] parts = StringUtil.split(search, ",");
			if(parts != null && parts.length > 0){
				QueryParameters param = new QueryParameters().setEntity(Product.class);
				for(String part : parts){
					String []asKv = StringUtil.split(part, ":");
					if(asKv.length == 2){
						String key = asKv[0];
						String value = asKv[1];
						if(StringUtil.isNotEmpty(key) && StringUtil.isNotEmpty(value)){
							if("cat".equalsIgnoreCase(key)){
								String[] as = StringUtil.split(value, "/");
								if(as != null && as.length > 0){
									param.addRestriction(Restrictions.eq("category", as[0]));
									if(as.length == 2){
										param.addRestriction(Restrictions.eq("subCategory", as[1]));
									}
								}
							}else if("full".equalsIgnoreCase(key)){
								if(StringUtil.isNotEmpty(value))
									param.addRestriction(Restrictions.or(Restrictions.or(Restrictions.ilike("title", "%" + value + "%"),Restrictions.ilike("summary", "%" + value + "%") ), Restrictions.ilike("detail", "%" + value + "%")));
							}else if("tag".equalsIgnoreCase(key)){
								param.addRestriction(Restrictions.like("tags", "%" + value + "%"));
							}else if("provider".equalsIgnoreCase(key) || "merchant".equalsIgnoreCase(key)){
								param.addRestriction(Restrictions.eq("providedBy", value));
							}else if("related".equalsIgnoreCase(key)){
								
							}
						}
					}
				}
				return param;
			}
			
		}
		return null;
	}


	@Override
	public int countMerchants(String search) {
		
		if(search.equalsIgnoreCase("latest")){
			QueryParameters param = new QueryParameters().setEntity(Merchant.class);
			
			return repositoryService.countRows(param, Util.getRemoteUser());
		}
		String value = StringUtil.split(search,":")[1];
		QueryParameters param = new QueryParameters().setEntity(Merchant.class);
		param.addRestriction(Restrictions.or(Restrictions.or(Restrictions.ilike("title", "%" + value + "%"),Restrictions.ilike("summary", "%" + value + "%") ), Restrictions.ilike("detail", "%" + value + "%")));
		return repositoryService.countRows(param, Util.getRemoteUser());
	}
	
	


	@Override
	public List<Merchant> searchMerchants(String searchTerm, int page,
			int pageSize) {
		
		if(searchTerm.equalsIgnoreCase("latest")){
			QueryParameters param = new QueryParameters().setEntity(Merchant.class).setFirstResult(0).setMaxResults(10);
			param.addOrder(Order.desc("lastModified"));
			//param.addRestriction(Restrictions.or(Restrictions.or(Restrictions.ilike("title", "%" + value + "%"),Restrictions.ilike("summary", "%" + value + "%") ), Restrictions.ilike("detail", "%" + value + "%")));
			//param.setFirstResult(pageSize*page).setMaxResults(pageSize);
			List result = repositoryService.executeQuery(param, Util.getRemoteUser());
			return result;
		}
		String value = StringUtil.split(searchTerm,":")[1];
		QueryParameters param = new QueryParameters().setEntity(Merchant.class);
		
		param.addRestriction(Restrictions.or(Restrictions.or(Restrictions.ilike("title", "%" + value + "%"),Restrictions.ilike("summary", "%" + value + "%") ), Restrictions.ilike("detail", "%" + value + "%")));
		param.setFirstResult(pageSize*page).setMaxResults(pageSize);
		List result = repositoryService.executeQuery(param, Util.getRemoteUser());
		return result;
		
	}


	/* (non-Javadoc)
	 * @see org.castafiore.shoppingmall.ShoppingMall#getAlsoPurchased(java.lang.String)
	 */
	@Override
	public List<Product> getAlsoPurchased(String productCode) {
		String sql = "select  PRODUCTCODE from WFS_FILE where DTYPE='SalesOrderEntry' and PARENT_ID in (select distinct PARENT_ID  from WFS_FILE where DTYPE='SalesOrderEntry' and PRODUCTCODE = '"+productCode+"') and PRODUCTCODE != '"+productCode+"'";
		
		List codes = SpringUtil.getBeanOfType(Dao.class).getSession().createSQLQuery(sql).list();
		if(codes.size() == 0){
			codes.add("-1");
		}
		QueryParameters params = new QueryParameters().setEntity(Product.class).addRestriction(Restrictions.eq("status", Product.STATE_PUBLISHED));
		params.addRestriction(Restrictions.in("code", codes));
		
		List result = SpringUtil.getRepositoryService().executeQuery(params, Util.getRemoteUser());
		
		return result;
		
	}
	
	
	public List<Product> getProductsWithProperty(String name, String value, String merchant){
		String sql = "select absolutePath from WFS_FILE where dtype='Product' and providedby = '"+merchant+"' and ((category ='"+name+"' && subcategory='"+value+"') || (category_1 ='"+name+"' && subcategory_1='"+value+"') || (category_2 ='"+name+"' && subcategory_2='"+value+"') || (category_3 ='"+name+"' && subcategory_3='"+value+"') || (category_4 ='"+name+"' && subcategory_4='"+value+"');" ;
		
		System.out.println(sql);
		List l = SpringUtil.getBeanOfType(Dao.class).getReadOnlySession().createSQLQuery(sql).list();
		
		String hql = "from " + Product.class.getName() + " where absolutePath in :li";
		List products = SpringUtil.getBeanOfType(Dao.class).getReadOnlySession().createQuery(hql).setParameter("li", l).setMaxResults(10).list();
		return products;
		
		
		
		
		
		
	}

}
