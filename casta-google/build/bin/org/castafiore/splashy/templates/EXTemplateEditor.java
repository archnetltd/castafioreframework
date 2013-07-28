package org.castafiore.splashy.templates;

import org.castafiore.catalogue.Product;
import org.castafiore.shoppingmall.product.ui.tab.ProductEditForm;
import org.castafiore.splashy.TemplatesUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.ex.dynaform.EXFieldSet;
import org.castafiore.ui.ex.form.EXUpload;

public class EXTemplateEditor extends EXFieldSet implements ProductEditForm{

	public EXTemplateEditor() {
		super("EXTemplateEditor", "Portal Creator", false);
		addField("Portal :", new EXUpload("portal"));
		
		
	}


	@Override
	public void fillProduct(Product product) {
		try{
		TemplatesUtil.uploadTemplate(getDescendentOfType(EXUpload.class).getFile(), product);
		}catch(Exception e){
			throw new UIException(e);
		}
	}


	@Override
	public Container setProduct(Product product) {
		return this;
	}

}
