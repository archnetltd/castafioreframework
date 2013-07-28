package org.castafiore.splashy.templates;

import java.util.List;

import org.castafiore.catalogue.Product;
import org.castafiore.splashy.TemplatesUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;

public class EXTemplates extends EXContainer{

	public EXTemplates(String name) {
		super(name,"div");
		addClass("container_12");
		List<Product> products = TemplatesUtil.getTemplates("Device", "Browser");
		int size = products.size();
		int mod = size %4;
		int rows = ((size - mod)/4);
		if(mod > 0){
			rows++;
		}
		int count = 0;
		for(int i = 0; i < rows; i++){
			Container wrapper = new EXContainer("", "div").addClass("wrapper").setStyle("margin-bottom", "24px");
		
			for(int j =0; j < 4;j++){
				if(count < size){
					wrapper.addChild(new EXTemplateItem("", products.get(count)));
					count++;
				}
			}
			addChild(wrapper);
		}
	}

}
