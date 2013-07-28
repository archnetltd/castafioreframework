package org.castafiore.inventory.suppliers;

import java.util.List;

import org.castafiore.inventory.AbstractModel;
import org.castafiore.persistence.Dao;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.spring.SpringUtil;
import org.castafiore.wfs.service.QueryParameters;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public  class SupplierModel extends AbstractModel{

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
			
			Criteria crit = session.createCriteria(Supplier.class).add(Restrictions.eq("merchantUsername", MallUtil.getCurrentUser().getMerchant().getUser().getUsername()));

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
