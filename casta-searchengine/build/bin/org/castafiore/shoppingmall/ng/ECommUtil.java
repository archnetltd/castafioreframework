package org.castafiore.shoppingmall.ng;

import org.castafiore.catalogue.Product;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.wfs.Util;

public class ECommUtil {
	
	
	public static void showProductDetail(String productPath, Container me){
		Product p =  (Product)SpringUtil.getRepositoryService().getFile(productPath, Util.getRemoteUser());
		
		EXProductDetailNG pd = new EXProductDetailNG("detail");
		pd.setStyle("overflow-x", "hidden");
		pd.setProduct(p);
		
		EXPanel panel = new EXPanel("pd", "Product detail");
		panel.setBody(pd);
		panel.setTitle(p.getTitle());
		//pd.getParent().setStyle("margin", "0");
		PopupContainer pc =me.getAncestorOfType(PopupContainer.class);
		if(pc == null){
			pc = me.getRoot().getDescendentOfType(PopupContainer.class);
		}
		panel.setWidth(Dimension.parse("652px"));
		pc.addPopup(panel.setStyle("z-index", "3000"));
		panel.getDescendentByName("content").setAttribute("style", "height: auto; overflow: auto; width: auto; margin: 0px !important; font-weight: normal; padding: 0pt; min-height: 61px;");
		panel.setStyle("border", "none");
	}

}
