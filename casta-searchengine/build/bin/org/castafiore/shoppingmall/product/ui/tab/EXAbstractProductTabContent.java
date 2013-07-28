package org.castafiore.shoppingmall.product.ui.tab;

import org.castafiore.catalogue.Product;
import org.castafiore.ui.Container;
import org.castafiore.ui.scripting.EXXHTMLFragment;

public abstract class EXAbstractProductTabContent extends EXXHTMLFragment implements ProductEditForm{

	public EXAbstractProductTabContent() {
		super("", "");
		setName( getClass().getSimpleName());
		setTemplateLocation("templates/product/" + getClass().getSimpleName().replace("EX", "") + ".xhtml");
		addClass("TabContent");
	}
	
	public abstract Container setProduct(Product product);
	
	
	public abstract void fillProduct(Product product);
	

}
