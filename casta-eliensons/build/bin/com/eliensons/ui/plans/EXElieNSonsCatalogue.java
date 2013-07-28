package com.eliensons.ui.plans;

import java.util.List;

import org.castafiore.catalogue.Product;
import org.castafiore.designable.EXCatalogue;
import org.castafiore.designable.product.EXProductGrid;
import org.castafiore.spring.SpringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;

public class EXElieNSonsCatalogue extends EXCatalogue{

	public EXElieNSonsCatalogue(String name) {
		super(name, ElieNSonsProductItem.class);
		removeChild("p");
		search();
	}
	
	public List<Product> search(){
		QueryParameters params = new QueryParameters().setEntity(Product.class);
		List l = SpringUtil.getRepositoryService().executeQuery(params, Util.getRemoteUser());
		getDescendentOfType(EXProductGrid.class).setProducts(l);
		return l;
	}

}
