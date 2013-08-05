package org.castafiore.ui.ex.button;

import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.navigation.EXMenu;

public class EXSplitButton extends EXContainer implements Button{

	public EXSplitButton(String name, EXButton button, EXMenu dropdown) {
		super(name, "div");
		EXButtonSet bs = new EXButtonSet("bst");
		bs.addItem(button);
		bs.addItem(new EXIconButton("arrow", null, Icons.ICON_TRIANGLE_1_S));
		addChild(bs);
		addChild(dropdown.setStyle("display", "none"));
		
		
		
	}

	@Override
	public void onReady(ClientProxy proxy) {
		
		super.onReady(proxy);
		String menuId = proxy.getDescendentOfType(EXMenu.class).getIdRef(); 
		ClientProxy mm = proxy.clone().getDescendentOfType(EXMenu.class).show(100);
		mm.appendJSFragment("event.stopPropagation();");
		String js  ="$( document ).click( function() {$('"+proxy.getDescendentOfType(EXMenu.class).getIdRef()+"').hide();});";
		
		proxy.getDescendentByName("arrow").click(mm);
		proxy.appendJSFragment(js);
		ClientProxy lis = new ClientProxy(menuId + " li").click(proxy.clone().getDescendentOfType(EXMenu.class).hide(100));
		proxy.mergeCommand(lis);
		
	}
	
	

}
