package org.castafiore.shoppingmall.merchant;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.castafiore.catalogue.Product;
import org.castafiore.inventory.customers.Customer;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.checkout.Order;
import org.castafiore.shoppingmall.checkout.SalesOrderEntry;
import org.castafiore.shoppingmall.orders.OrdersWorkflow;
import org.castafiore.shoppingmall.user.ShoppingMallUserImpl;
import org.castafiore.spring.SpringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;
import org.hibernate.criterion.Restrictions;

public class ShoppingMallMerchantImpl extends ShoppingMallUserImpl implements ShoppingMallMerchant{

	public ShoppingMallMerchantImpl(RepositoryService repositoryService,ShoppingMallUserImpl userImpl) {
		super(repositoryService, userImpl.getUser());
		
	}
	
	@Override
	public List<Product> getMyProducts(int state) {
		List result = new ArrayList();
		String dir =PRODUCT_DRAFT_DIR;
		result = repositoryService.executeQuery(new QueryParameters().setEntity(Product.class).addRestriction(Restrictions.eq("status", state)).addSearchDir(dir.replace("$user", user.getUsername())), Util.getRemoteUser());
		return result;
	}

	@Override
	public List<Product> getMyProducts(int state, int start, int max) {
		List result = new ArrayList();
		String dir =PRODUCT_DRAFT_DIR;
		result = repositoryService.executeQuery(new QueryParameters().setEntity(Product.class).addRestriction(Restrictions.eq("status", state)).addSearchDir(dir.replace("$user", user.getUsername())).setFirstResult(start).setMaxResults(max), Util.getRemoteUser());
		return result;
	}
	
	
	public int countMyProduct(int state){
		String dir =PRODUCT_DRAFT_DIR;
		return repositoryService.countRows(new QueryParameters().setEntity(Product.class).addRestriction(Restrictions.eq("status", state)).addSearchDir(dir.replace("$user", user.getUsername())), Util.getRemoteUser());
	}

	@Override
	public void deleteProduct(Product product) {
		
		product.setStatus(Product.STATE_DELETED);
		product.save();
		
	}
	
	
	public Order createOrder(String supplier){
		Directory parent = repositoryService.getDirectory(ORDERS_DIR.replace("$user", supplier), Util.getRemoteUser());
		Order order = parent.createFile(new Date().getTime() + "", Order.class);
		order.setCode(new Date().getTime() + "");
		//order.setOrderedBy(getUser().getUsername());
		order.setOrderedFrom(supplier);
		order.setStatus(9);
		return order;
	}
	
	public Customer createCustomer(){
		
		Customer customer = new Customer();
		customer.setMerchantUsername(user.getUsername());
		return customer;
	}



	@Override
	public void publishProduct(Product product) {
		int count =  countPublished();
		Merchant merchant = MallUtil.getMerchant(getUser().getUsername());
		String plan = merchant.getPlan();
		if(plan.equalsIgnoreCase("free")){
			if(count >= 500){
				throw new PlanViolationException("Cannot publish more than 500 products in free plan");
			}
		}else if(plan.equals("professional")){
			if(count >= 3000){
				throw new PlanViolationException("Cannot publish more than 3000 products in professional plan");
			}
		}
		product.setStatus(Product.STATE_PUBLISHED);
		product.save();
	}
	
	
	
	public int countPublished(){
		
		
		QueryParameters parame = new QueryParameters().setEntity(Product.class).addRestriction(Restrictions.eq("status", Product.STATE_PUBLISHED));
		int count = repositoryService.countRows(parame, Util.getRemoteUser());
		return count;
	}


	
	public Product createProduct(){
		Directory parent = repositoryService.getDirectory(PRODUCT_DRAFT_DIR.replace("$user", user.getUsername()),Util.getRemoteUser());
		Product product = parent.createFile(new Date().getTime() + "", Product.class);
		product.makeOwner(getUser().getUsername());
		product.setProvidedBy(getUser().getUsername());
		product.setStatus(Product.STATE_DRAFT);
		parent.save();
		return product;
	}

	@Override
	public void saveProduct(Product product) {
		product.setStatus(Product.STATE_DRAFT);
		product.save();
	}



	@Override
	public void updateProduct(Product product) {
		product.setStatus(Product.STATE_DRAFT);
		product.save();
	}

	@Override
	public List<Order> getOrders() {
		
		List<Integer> allowedStates = new ArrayList<Integer>();
		
		int states[] =SpringUtil.getBeanOfType(OrdersWorkflow.class).getAvailableStates();
		for(int s : states)
			allowedStates.add(s);
		
		
		QueryParameters params = new QueryParameters().setEntity(Order.class).addRestriction(Restrictions.eq("orderedFrom", getUser().getUsername()));
		if(allowedStates.size() > 0)
			params.addRestriction(Restrictions.in("status", allowedStates));
		params.addOrder(org.hibernate.criterion.Order.asc("status")).addOrder(org.hibernate.criterion.Order.desc("dateCreated"));
		List result = repositoryService.executeQuery(params, Util.getRemoteUser());
		
		return result;
	}
	
	
	public  List<Order> getSalesReport(Date startDate, Date endDate){
		
		if(endDate == null){
			endDate = new Date();
		}
		
		if(startDate == null){
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.DATE, 1);
			startDate = cal.getTime();
		}
		
		Calendar sd = Calendar.getInstance();
		 sd.setTime(startDate);
		 
		 Calendar ed = Calendar.getInstance();
		 ed.setTime(endDate);
		QueryParameters params = new QueryParameters().setEntity(Order.class).addRestriction(Restrictions.between("dateCreated", sd,ed));
		params.addRestriction(Restrictions.eq("orderedFrom", getUser().getUsername()));
		List result = SpringUtil.getRepositoryService().executeQuery(params, Util.getRemoteUser());
		return result;
		
	}
	
	
	public List<Order> getProductReport(Date startDate, Date endDate, String productCode){
		
		if(endDate == null){
			endDate = new Date();
		}
		
		if(startDate == null){
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.DATE, 1);
			startDate = cal.getTime();
		}
		
		 Calendar sd = Calendar.getInstance();
		 sd.setTime(startDate);
		 
		 Calendar ed = Calendar.getInstance();
		 ed.setTime(endDate);
		
		QueryParameters params = new QueryParameters().setEntity(SalesOrderEntry.class).addRestriction(Restrictions.between("dateCreated", sd,ed));
		params.addSearchDir("/root/users/" + getUser().getUsername() );
		params.addRestriction(Restrictions.eq("productCode", productCode));
		List<File> result = SpringUtil.getRepositoryService().executeQuery(params, Util.getRemoteUser());
		List<Order> res = new ArrayList<Order>(result.size());
		for(File o : result){
			Order order =(Order) o.getParent();
			res.add(order);
		}
		return res;
	}

	

}
