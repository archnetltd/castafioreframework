package org.castafiore.inventory.customers;

import java.util.List;

import org.castafiore.inventory.AbstractModel;
import org.castafiore.persistence.Dao;
import org.castafiore.persistence.DaoImpl;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.merchant.ShoppingMallMerchant;
import org.castafiore.spring.SpringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public  class CustomerModel extends AbstractModel{

	private String[] columns = new String[]{ "Code", "Name","Credit"};
	
	public  String[] getColumns(){
		return columns;
	}
	
	public QueryParameters getParams(){
		return null;
	}

	@Override
	public int getRowCount() {
		if(count == -1){
			
			Session session = SpringUtil.getBeanOfType(Dao.class).getReadOnlySession();
			
			Criteria crit = session.createCriteria(Customer.class).add(Restrictions.eq("merchantUsername", MallUtil.getCurrentUser().getMerchant().getUser().getUsername()));
			List l = crit.setProjection(Projections.rowCount()).list();
			if(l.size() > 0){
				count = (Integer)l.get(0);
			}else{
				count = 0;
			}
			
		}
		return count;
	}
	
	

}
