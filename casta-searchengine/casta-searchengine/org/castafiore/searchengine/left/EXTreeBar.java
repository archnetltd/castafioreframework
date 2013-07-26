package org.castafiore.searchengine.left;

import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.ui.Container;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;

public class EXTreeBar extends EXContainer implements EventDispatcher{
	
	private MallTreeNode root_ = null;

	public EXTreeBar(String name, String title, MallTreeNode root) {
		super(name, "div");
		
		addClass(name);
		addClass("mallcatlist").addClass("malloptions");
		addChild(new EXContainer("title", "h5").setText(title));
		
		Container ul = new EXContainer("ul", "ul");
		addChild(ul);
		addChildren(root, ul);
		root_ = root;
	}
	
	
	public void addChildren(MallTreeNode parent, Container ul){
		if(!parent.isLeaf()){
			int count = parent.childrenCount();
			for(int i = 0; i < count; i++){
				MallTreeNode node = (MallTreeNode)parent.getNodeAt(i);
				Container c = node.getComponent();
				Container li = new EXContainer("", "li");
				if(i==0){
					li.addClass("ui-state-active");
				}
				ul.addChild(li);
				li.addChild(c);
				c.addEvent(DISPATCHER, Event.CLICK);
				if(!node.isLeaf()){
					Container nUl =new EXContainer("", "ul");
					li.addChild(nUl);
					addChildren(node, nUl);
				}
			}
		}
	}


	@Override
	public void executeAction(Container source) {
		
		for(Container c : source.getAncestorByName("ul").getChildren()){
			if(c.getDescendentById(source.getId()) != null){
				c.addClass("ui-state-active");
			}else{
				c.removeClass("ui-state-active");
			}
		}
//		if(selected != null){
//			selected.getParent().removeClass("ui-state-default");
//		}
//		source.getParent().addClass("ui-state-active");
//		selected = source;
//		ComponentUtil.iterateOverDescendentsOfType(this, Container.class, new ComponentVisitor() {
//			
//			@Override
//			public void doVisit(Container c) {
//				if(source.getId().equals(c.getId())){
//					source.addClass("selected");
//				}else{
//					
//					c.removeClass("selected");
//				}
//				
//			}
//		});
		root_.executeAction(source);
		
	}

}
