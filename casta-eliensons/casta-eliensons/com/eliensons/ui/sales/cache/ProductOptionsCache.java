package com.eliensons.ui.sales.cache;

import java.util.List;

import org.castafiore.catalogue.Product;
import org.castafiore.designer.marshalling.DesignableUtil;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.wfs.types.BinaryFile;

public class ProductOptionsCache extends EXContainer{

	public ProductOptionsCache() {
		super("ProductOptionsCache", "div");
		List<Product> products = MallUtil.getCurrentUser().getMerchant().getMyProducts(Product.STATE_PUBLISHED);
		
		for(Product p : products){
			Container c = addOption(p);
			if(c != null){
				addChild(c.setDisplay(false));
			}
		}
		
	}
	
	private Container addOption(Product product){
		try{
			BinaryFile bf = product.getOption();
			if(bf != null){
				Container c = DesignableUtil.buildContainer(bf.getInputStream(), false);
				c.setName(product.getName());
				c.setAttribute("productPath", product.getAbsolutePath());
				if(c.getDescendentOfType(EXDynaformPanel.class) != null && c.getDescendentOfType(EXDynaformPanel.class).getFields().size() > 0){
					c.getDescendentOfType(EXDynaformPanel.class).addButton(new EXButton("saveoptions", "Save"));
					c.getDescendentOfType(EXDynaformPanel.class).addButton(new EXButton("canceloptions", "Cancel"));
					
					c.setStyle("z-index", "3001");
					//c.getDescendentByName("saveoptions").setAttribute("qty", qty + "").addEvent(this, CLICK).setAttribute("path", product.getAbsolutePath());
					//c.getDescendentByName("canceloptions").addEvent(this, CLICK);
					
						//source.getAncestorOfType(PopupContainer.class).addPopup(c);
						//c.setStyle("visibility", "visible !important");
					
					return c;
				}
				return null;
				
			}
		}catch(Exception e ){
			e.printStackTrace();
			
		}
		return null;
	}

}
