package org.castafiore.shoppingmall.product.ui.tab;

import org.castafiore.catalogue.Product;
import org.castafiore.shoppingmall.ng.v2.EXGeoCoding;
import org.castafiore.ui.Container;
import org.castafiore.ui.tabbedpane.TabModel;
import org.castafiore.ui.tabbedpane.TabPanel;

public class ProfocusProductModel implements TabModel{
	private static String[] LABELS = new String[]{"Main", "Images", "Categories", "Map"};
	protected Product product;
	
	
	
	
	public ProfocusProductModel(Product product) {
		super();
		this.product = product;
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
			return new EXImages().setProduct(product);
		}else if(index == 2){
			return new EXCategories().setProduct(product);
		}else{
			return new EXGeoCoding().setProduct(product);
			//return new EXOtherSettings().setProduct(product);
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
