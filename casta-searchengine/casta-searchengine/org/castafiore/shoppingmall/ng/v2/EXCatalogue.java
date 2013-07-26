package org.castafiore.shoppingmall.ng.v2;

import java.util.List;

import org.castafiore.catalogue.Product;
import org.castafiore.designable.product.EXProductGrid;
import org.castafiore.designable.product.EXProductResultBar;
import org.castafiore.shoppingmall.ng.EXCatalogueNG;
import org.castafiore.shoppingmall.ng.EXProductItemNG;
import org.castafiore.shoppingmall.ng.EXProductResultBarNG;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class EXCatalogue extends EXCatalogueNG {

	public EXCatalogue() {
		super("catalogue");
		
	}

//	public void setProducts(List<Product> products, String skin){
//		Container grid = getChild("grid");
//		grid.getChildren().clear();
//		grid.setRendered(false);
//		for(Product p : products){
//			grid.addChild(new EXProductItem(skin, p.getTitle()).setProduct(p).setStyle("float", "left").setStyle("width", "175px"));
//		}
//		getDescendentOfType(EXProductResultBarNG.class).setSkin(skin);
//	}
	
	@Override
	public void changePage(int page) {
		this.curpage = page;
		int  start = page*getRowsPerPage();
		int end = start + getRowsPerPage();
		if(data.size() <= end){
			end = data.size();
		}
		Container grid = getChild("grid");
		grid.getChildren().clear();
		grid.setRendered(false);
		for(int i = start; i < end;i++){
			Product p = data.get(i);
			grid.addChild(new EXProductItem(skin, p.getTitle()).setProduct(p).setStyle("float", "left").setStyle("width", "175px"));
		}
		
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
		
		setProducts(l, "");
		return l;
	}
	

}
