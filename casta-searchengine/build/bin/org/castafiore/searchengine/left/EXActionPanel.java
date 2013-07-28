package org.castafiore.searchengine.left;

import org.castafiore.shoppingmall.ui.MallLoginSensitive;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.tree.TreeNode;

/**
 * 
 * @author kureem
 *
 */
public class EXActionPanel extends EXContainer implements MallLoginSensitive{

	public EXActionPanel(String name) {
		super(name, "div");
		addClass("mallactionpanel").addClass("first").addClass("span-4");
		//addBar("categories", "Categories", new CategoriesTreeNode(null));
	}
	
	
	public EXTreeBar getBar(String name){
		return (EXTreeBar)getDescendentByName(name);
	}
	
	public EXTreeBar addBar(String name,String title, MallTreeNode root){
		EXTreeBar bar = getBar(name);
		if(bar == null){
			bar = new EXTreeBar(name, title, root);
			addChild(bar);
		}
		showOnly(name);
		setDisplay(true);
		getParent().getChildByIndex(1).setDisplay(false);
		return bar;
	}
	
	public void showOnly(String barName){
		setDisplay(true);
		getParent().getChildByIndex(1).setDisplay(false);
		for(Container bar : getChildren()){
			if(bar.getName().equals(barName)){
				bar.setDisplay(true);
			}else{
				bar.setDisplay(false);
			}
				
		}
	}
	
	public EXActionPanel showHideBar(String name, boolean showHide){
		getBar(name).setDisplay(showHide);
		return this;
	}


	@Override
	public void onLogin(String username) {
		//add code for recent activities
		//or load user configurations
		
	}

}
