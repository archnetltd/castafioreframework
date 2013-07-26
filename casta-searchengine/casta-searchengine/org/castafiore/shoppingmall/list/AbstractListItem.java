package org.castafiore.shoppingmall.list;

import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXCheckBox;

public abstract class AbstractListItem extends EXContainer   {
	
	public AbstractListItem(String  name){
		super(name, "tr");
		//addChild(new EXContainer("", "td").setStyle("vertical-align", "top").addChild(new EXCheckBox("cb")));
	}
	
	
	public boolean isChecked() {
		return getDescendentOfType(EXCheckBox.class).isChecked();
	}
} 
