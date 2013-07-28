package com.eliensons.ui.plans;

import org.castafiore.catalogue.Product;
import org.castafiore.shoppingmall.product.ui.EXProductEditCard;
import org.castafiore.ui.UIException;
import org.castafiore.ui.tabbedpane.EXTabPanel;

public class EXPlansEditCard extends EXProductEditCard{

	public EXPlansEditCard(String name) {
		super(name, false);
		// TODO Auto-generated constructor stub
	}
	
	public void setProduct(Product product){
		if(product == null){
			setAttribute("path", (String)null);
		}else
		{
			product.getCharacteristics();
			setAttribute("path", product.getAbsolutePath());
		}
		EXTabPanel tab = getDescendentOfType(EXTabPanel.class);
		try{
		tab.setModel(new PlanCardTabModel(product));
		}catch(Exception e){
			throw new UIException(e);
		}
	}
	

}
