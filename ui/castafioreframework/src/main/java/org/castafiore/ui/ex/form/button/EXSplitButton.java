package org.castafiore.ui.ex.form.button;

import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.js.JMap;
import org.castafiore.ui.menu.EXMenu;

public class EXSplitButton extends EXContainer implements Button{

	public EXSplitButton(String name, EXButton button, EXMenu dropdown) {
		super(name, "div");
		EXButtonSet bs = new EXButtonSet("bst");
		bs.addItem(button);
		bs.addItem(new EXIconButton("arrow", null, Icons.ICON_TRIANGLE_1_S));
		addChild(bs);
		addChild(dropdown.setAttribute("display", "none"));
		
		
		
	}

	@Override
	public void onReady(ClientProxy proxy) {
		
		super.onReady(proxy);
		ClientProxy mm = proxy.clone().getDescendentOfType(EXMenu.class).addMethod("show").addMethod("position", new JMap().put("my", "left top").put("at", "left botton").put("of", proxy.getIdRef()));
		String js  ="$( document ).one( \"click\", function() {$('"+proxy.getDescendentOfType(EXMenu.class).getIdRef()+"').hide();});";
		
		proxy.getDescendentByName("arrow").click(mm);
		proxy.appendJSFragment(js);
		
		
	}
	
	

}
