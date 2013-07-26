package org.castafiore.designable;

import java.util.List;

import org.castafiore.catalogue.Product;
import org.castafiore.designable.product.EXProductGrid;
import org.castafiore.designable.product.EXProductItem;
import org.castafiore.designable.product.EXProductResultBar;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class EXCatalogue extends EXContainer {
	
	

	public EXCatalogue(String name) {
		super(name, "div");
		addChild(new EXProductResultBar("p"));
		addChild(new EXProductGrid(""));
		
	}
	
	public EXCatalogue(String name, Class<? extends EXProductItem> pitem) {
		super(name, "div");
		addChild(new EXProductResultBar("p"));
		addChild(new EXProductGrid("", pitem));
		
	}
	public List<Product> orderBy(String name){
		
		return search(getAttribute("stype"), getAttribute("svalue"), getAttribute("merchant"),0, name);
	}
	public List<Product> search(String type,String value,String merchant){
		return search(type,value,merchant,0,"title");
	}
	
	public List<Product> search(String type,String value,String merchant, int page, String order){
		setAttribute("stype", type).setAttribute("svalue", value);
		setAttribute("page", page + "");
		setAttribute("merchant", merchant);
		setAttribute("order", order);
		QueryParameters params = null;
		if(type.equals("fulltext")){
			
			params =	new QueryParameters().setEntity(Product.class);
			if(StringUtil.isNotEmpty(value)){
				params.addRestriction(Restrictions.or(Restrictions.or(Restrictions.ilike("title", "%" + value + "%"),Restrictions.ilike("summary", "%" + value + "%") ), Restrictions.ilike("detail", "%" + value + "%")));
			}
			
		}else if(type.equals("demo")){
			params =	new QueryParameters().setEntity(Product.class).addRestriction(Restrictions.like("code", "TST%"));
		}else if(type.equals("recent")){
			params =	new QueryParameters().setEntity(Product.class);
		}
		else{
			params = new QueryParameters().setEntity(Product.class).addRestriction(Restrictions.eq("category", value));
		}
		params.addRestriction(Restrictions.eq("providedBy",merchant)).addRestriction(Restrictions.eq("status",Product.STATE_PUBLISHED));
		
		int count = SpringUtil.getRepositoryService().countRows(params, Util.getRemoteUser());
		
		params.addOrder(Order.asc(order));
		int pSize = Integer.parseInt(getDescendentOfType(EXSelect.class).getValue().toString());
		params.setFirstResult(page*pSize).setMaxResults(pSize);
		
		List l = SpringUtil.getRepositoryService().executeQuery(params, Util.getRemoteUser());
		//List<Product> products =MallUtil.getCurrentMall().searchProducts(search, 0, -1);
		
		if(getDescendentOfType(EXProductResultBar.class) != null)
			getDescendentOfType(EXProductResultBar.class).reset(pSize, count, page);
		getDescendentOfType(EXProductGrid.class).setProducts(l);
		return l;
	}
	
	
	public void goToPage(int page){
		
		String type = getAttribute("stype");
		String value = getAttribute("svalue");
		
		String merchant = getAttribute("merchant");
		String order = getAttribute("order");
		
		
		List l = search(type,value,merchant,page, order);
		//List<Product> products =MallUtil.getCurrentMall().searchProducts(search, 0, -1);
		
	
		getDescendentOfType(EXProductGrid.class).setProducts(l);
	}
	
	
	

	
	

}
