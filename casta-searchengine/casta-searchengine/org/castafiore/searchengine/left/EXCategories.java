package org.castafiore.searchengine.left;

import java.util.List;
import java.util.Map;

import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.product.ui.EXSearchProductLink;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.js.JMap;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.Directory;

public class EXCategories extends EXContainer{

	public EXCategories(String name) {
		super(name, "div");
		init();
	}
	
	public void addCategory(Directory category, boolean open){
		Container c =  new EXContainer(category.getAbsolutePath(), "div").addClass("EXCategory");
		addChild(c);
		Container title = getCategoryComponent(category).addClass("title");
		String attr = "false";
		if(open){
			attr = "true";
		}
		c.setAttribute("open", attr);
		title.setText(category.getName());
		c.addChild(title);
		
		if(open){
			List<Directory> subCategories = category.getFiles(Directory.class).toList();
			for(Directory sc : subCategories){
				c.addChild(getSubCategoryComponent(category, sc).addClass("EXSubCategory"));
			}
		}
		
		
	}
	
	
	public Container getSubCategoryComponent(Directory category, Directory subCategory){
		EXSearchProductLink link = new EXSearchProductLink("", "div");
		link.setText(subCategory.getName());
		String searchTerm = "cat:/" + category.getName() + "/" + subCategory.getName();
		link.setSearchTerm(searchTerm);
		return link;
	}
	
	public Container getCategoryComponent(Directory category) {
		
		EXContainer link = new EXContainer("", "h5");
		link.setText(category.getName());
		//String searchTerm = "cat:";
		
		
		//searchTerm = searchTerm  +"/"+ category.getName();
		//link.setSearchTerm(searchTerm);
		
		link.addEvent(OPEN_CLOSE, Event.CLICK);
		return link;
	}
	
	public void init(){
		Directory root = MallUtil.getCurrentMall().getRootCategory();
		List<Directory> categories = root.getFiles(Directory.class).toList();
		for(int i = 0; i < categories.size(); i ++){
			boolean open = i==0;
			addCategory(categories.get(i), open);
		}
		
		
	}
	
	public Container getOpenedCategory(){
		for(Container c : getChildren()){
			if(c.getAttribute("open").equalsIgnoreCase("true")){
				return c;
			}
		}
		return null;
	}
	public void hyDrateCategory(Container category){
		String scategory = category.getName();
		
		Directory fCategory = SpringUtil.getRepositoryService().getDirectory(scategory, Util.getRemoteUser());
		
		List<Directory> subCategories = fCategory.getFiles(Directory.class).toList();
		for(Directory sc : subCategories){
			category.addChild(getSubCategoryComponent(fCategory, sc).addClass("EXSubCategory").setDisplay(false));
		}
	}
	
	public void toggleCategory(Container category, boolean open){
		for(Container c : category.getChildren()){
			if(!c.getTag().equalsIgnoreCase("h5")){
				c.setDisplay(open);
				
			}
		}
	}
	
	
	private static Event OPEN_CLOSE = new Event(){

		@Override
		public void ClientAction(ClientProxy container) {
			container.makeServerRequest(this);
			
		}

		@Override
		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			boolean result = false;
			Container category = container.getParent();
			String open = category.getAttribute("open");
			EXCategories exCategories = container.getAncestorOfType(EXCategories.class);
			Container opened = exCategories.getOpenedCategory();
			if(open.equalsIgnoreCase("true")){
				//close this
				category.setAttribute("open", "false");
				request.put("close", category.getId());
			}else{
				//open this;
				category.setAttribute("open", "true");
				request.put("open", category.getId());
				if(category.getChildren().size() <=1){
					//needs hydration
					exCategories.hyDrateCategory(category);
					result = true;
					
				}else{
					exCategories.toggleCategory(category, true);
				}
				
				//close opened
				
				if(opened != null){
					opened.setAttribute("open", "false");
					exCategories.toggleCategory(opened, false);
					request.put("close", opened.getId());
				}
				
				
			}
			
			return result;
		}

		@Override
		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			ClientProxy animations = null;
			if(request.containsKey("open") && request.containsKey("close")){
				String selectorOpen = "#" + request.get("open") + " div";
				String selectorClose = "#" + request.get("close") + " div";
				
				animations = new ClientProxy(selectorOpen).fadeIn(100,new ClientProxy(selectorClose).fadeOut(100));
			}else if(request.containsKey("open")){
				String selectorOpen = "#" + request.get("open") + " div";
				animations = new ClientProxy(selectorOpen).fadeIn(100);
			}else if(request.containsKey("close")){
				
				String selectorClose = "#" + request.get("close") + " div";
				
				animations = new ClientProxy(selectorClose).fadeOut(100);
			}
			if(animations != null){
				container.addMethod("stop", new JMap()).mergeCommand(animations);
			}
			
		}
		
	};
	
	

}
