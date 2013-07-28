package com.eliensons.ui.plans;

import org.castafiore.catalogue.Product;
import org.castafiore.shoppingmall.product.ui.tab.EXImages;
import org.castafiore.shoppingmall.product.ui.tab.EXMain;
import org.castafiore.shoppingmall.product.ui.tab.EXOptions;
import org.castafiore.shoppingmall.product.ui.tab.EXPrice;
import org.castafiore.shoppingmall.product.ui.tab.ProductCardTabModel;
import org.castafiore.ui.Container;
import org.castafiore.ui.tabbedpane.TabPanel;

public class PlanCardTabModel extends ProductCardTabModel{

	private static String[] LABELS = new String[]{"Main", "Price", "Images","Options"};
	//private Product product;
	public PlanCardTabModel(Product product)throws Exception {
		super(product, false);
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	
	

	
	@Override
	public int getSelectedTab() {
		return 0;
	}

	@Override
	public Container getTabContentAt(TabPanel pane, int index) {
		
		if(index == 0){
			return new EXMain().setProduct(product);
		}else if(index == 1){
			return new EXPrice().setProduct(product);
		}else if(index == 2){
			return new EXImages().setProduct(product);
		}else{
			return new EXOptions().setProduct(product);
		}
	}

	@Override
	public String getTabLabelAt(TabPanel pane, int index, boolean selected) {
		return  LABELS[index];
	}

	@Override
	public int size() {
		return LABELS.length;
	}
	

}
