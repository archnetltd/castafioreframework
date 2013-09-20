package org.castafiore.ui.ex.button;

import org.castafiore.ui.EXContainer;
import org.castafiore.ui.button.Button;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.ex.navigation.JMenu;

public class EXSplitButton extends EXContainer implements Button{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EXSplitButton(String name, EXButton button, JMenu dropdown) {
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
		String menuId = proxy.getDescendentOfType(JMenu.class).getIdRef(); 
		ClientProxy mm = proxy.clone().getDescendentOfType(JMenu.class).show(100);
		mm.appendJSFragment("event.stopPropagation();");
		String js  ="$( document ).click( function() {$('"+proxy.getDescendentOfType(JMenu.class).getIdRef()+"').hide();});";
		
		proxy.getDescendentByName("arrow").click(mm);
		proxy.appendJSFragment(js);
		ClientProxy lis = new ClientProxy(menuId + " li").click(proxy.clone().getDescendentOfType(JMenu.class).hide(100));
		proxy.mergeCommand(lis);
		
	}
	
	

}
