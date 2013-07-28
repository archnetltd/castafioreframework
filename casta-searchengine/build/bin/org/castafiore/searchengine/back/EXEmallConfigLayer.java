package org.castafiore.searchengine.back;

import org.castafiore.catalogue.Product;
import org.castafiore.shoppingmall.product.ui.EXProductsPanel;
import org.castafiore.shoppingmall.user.ui.EXShopSettings;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.ui.tabbedpane.EXTabPanel;
import org.castafiore.ui.tabbedpane.TabModel;
import org.castafiore.ui.tabbedpane.TabPanel;

public class EXEmallConfigLayer extends EXPanel implements TabModel{

	
	private String merchant_;
	public EXEmallConfigLayer(String name, String merchant) {
		super(name, "My company settings");
		this.merchant_ = merchant;
		EXTabPanel panel = new EXTabPanel("panel",this );
		setBody(panel);
		setStyle("z-index", "3000");
		setStyle("width", "900px");
		
		
		
	}
	@Override
	public int getSelectedTab() {
		
		return 0;
	}
	@Override
	public Container getTabContentAt(TabPanel pane, int index) {
		if(index == 1){
			EXShopSettings setting = new EXShopSettings();
			setting.fill();
			return setting;
		}else{
			EXProductsPanel c = new EXProductsPanel("products", "Product Panel");
			c.showProductList(Product.STATE_PUBLISHED);
			c.setSimple(true);
			return c;
			//return new EXProductsPanel("products", "Product Panel").showProductList(state);
		}
		
	}
	@Override
	public String getTabLabelAt(TabPanel pane, int index, boolean selected) {
		if(index == 1){
			return "Company settings";
		}else
			return "Products";
	}
	@Override
	public int size() {
		return 2;
	}

	

}
