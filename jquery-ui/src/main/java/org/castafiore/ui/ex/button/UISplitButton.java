package org.castafiore.ui.ex.button;

import org.castafiore.ui.EXContainer;
import org.castafiore.ui.button.Button;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.ex.navigation.UIMenu;

public class UISplitButton extends EXContainer implements Button{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UISplitButton(String name, UIButton button, UIMenu dropdown) {
		super(name, "div");
		UIButtonSet bs = new UIButtonSet("bst");
		bs.addItem(button);
		bs.addItem(new UIIconButton("arrow", null, Icons.ICON_TRIANGLE_1_S));
		addChild(bs);
		addChild(dropdown.setStyle("display", "none"));
		
		
		
	}

	@Override
	public void onReady(ClientProxy proxy) {
		
		super.onReady(proxy);
		String menuId = proxy.getDescendentOfType(UIMenu.class).getIdRef(); 
		ClientProxy mm = proxy.clone().getDescendentOfType(UIMenu.class).show(100);
		mm.appendJSFragment("event.stopPropagation();");
		String js  ="$( document ).click( function() {$('"+proxy.getDescendentOfType(UIMenu.class).getIdRef()+"').hide();});";
		
		proxy.getDescendentByName("arrow").click(mm);
		proxy.appendJSFragment(js);
		ClientProxy lis = new ClientProxy(menuId + " li").click(proxy.clone().getDescendentOfType(UIMenu.class).hide(100));
		proxy.mergeCommand(lis);
		
	}
	
	

}
