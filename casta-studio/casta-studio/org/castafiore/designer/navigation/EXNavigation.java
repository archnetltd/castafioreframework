/**
 * 
 */
package org.castafiore.designer.navigation;

import org.castafiore.designer.model.NavigationDTO;
import org.castafiore.designer.portal.EXDesignablePortalContainer;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.layout.EXBorderLayoutContainer;
import org.castafiore.ui.ex.tree.EXTree;

/**
 * @author acer
 *
 */
public class EXNavigation extends EXBorderLayoutContainer{

	/**
	 * @param name
	 * @param treeNode
	 */
	public EXNavigation(String name, EXDesignablePortalContainer pc) {
		super(name);
		String[] ss = pc.getNavigationNames();
		
		for(String s : ss){
			Container c = new EXContainer(s,"button").setText(s);
			addChild(c,EXBorderLayoutContainer.LEFT);
		}
		
		try{
		NavigationDTO dto = pc.getNavigation("Default");
		
		
		EXTree tree = new EXTree("Default", new NavigationTreeNode(dto, null));
		addChild(tree,EXBorderLayoutContainer.CENTER);
		}catch(Exception e){
			throw new UIException(e);
		}
		
	}	
	

}
