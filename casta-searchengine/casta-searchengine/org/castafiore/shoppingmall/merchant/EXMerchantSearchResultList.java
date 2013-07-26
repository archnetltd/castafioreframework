package org.castafiore.shoppingmall.merchant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.castafiore.searchengine.MallUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.js.JMap;

public class EXMerchantSearchResultList extends EXContainer implements Event{
	
	private int pageSize_ = 10;
	
	private int curPage_;
	
	private int pages = 1;

	public EXMerchantSearchResultList(String name) {
		super(name, "div");
		addChild(new EXContainer("noresult", "h6").setText("Your search did not return any result"));
		pageSize_  =10;
		
		Container toolBar = new EXContainer("toolBar", "div")
			.addChild(new EXContainer("nextPage", "button").setText(" ").addEvent(this, Event.CLICK))
			.addChild(new EXContainer("page", "div"))
			.addChild(new EXContainer("previousPage", "button").addEvent(this, Event.CLICK).setText(" ") );
		addChild(toolBar);
		Container result = new EXContainer("result", "div").addClass("shopSearchResults");
		
		
		addChild(result);
		for(int i = 0; i< pageSize_; i++){
			EXMerchantSearchResultItem item = new EXMerchantSearchResultItem("");
			result.addChild(item.setDisplay(false));
		}
		
	}

	
	
	private void nextPage(Container button){
		curPage_++;
		showSearchResults(getAttribute("searchTerm"), false);
		if(curPage_ == pages){
			button.setAttribute("move", "false");
		}else{
			button.setAttribute("move", "true");
		}
		
		Container prev = button.getParent().getChild("previousPage");
		if(curPage_ == 0){
			prev.setAttribute("move", "false");
		}else{
			prev.setAttribute("move", "true");
		}
	}
	
	
	
	private void previousPage(Container button){
		if(curPage_ > 0){
			curPage_--;
			showSearchResults(getAttribute("searchTerm"), false);
		}
		if(curPage_ == 0){
			button.setAttribute("move", "false");
		}else{
			button.setAttribute("move", "true");
		}
		
		Container next = button.getParent().getChild("nextPage");
		if(curPage_ == pages){
			next.setAttribute("move", "false");
		}else{
			next.setAttribute("move", "true");
		}
	}
	
	
	public void showSearchResults(String searchTerm){
		
		showSearchResults(searchTerm, true);
	}
	
	public void showSearchResults(String searchTerm, boolean isReset){
		
		
		setAttribute("searchTerm", searchTerm);

		List<Merchant> merchants = MallUtil.getCurrentMall().searchMerchants(searchTerm, curPage_, pageSize_);
		
		if(isReset){
			curPage_ = 0;
			getChild("noresult").setDisplay((merchants.size() == 0));
			int  count = MallUtil.getCurrentMall().countMerchants(searchTerm);
			if(searchTerm.equalsIgnoreCase("latest"))
				count = 10;
			if((count%pageSize_)==0){
				pages = count/pageSize_;
			}else{
				count = count -(count%pageSize_);
				pages = (count/pageSize_) + 1;
			}
			if(pages == 1){
				getChild("toolBar").setDisplay(false);
			}else{
				getChild("toolBar").setDisplay(true);
				getDescendentByName("nextPage").setAttribute("move", "true");
				getDescendentByName("previousPage").setAttribute("move", "false");
				
			}
		}
		
		
		List<EXMerchantSearchResultItem> items = new ArrayList<EXMerchantSearchResultItem>();
		
		
		if(!isReset && merchants.size() == 0){
			curPage_--;
			return;
		}
		for(Container c : getChild("result").getChildren()){
			if( c instanceof EXMerchantSearchResultItem){
				items.add((EXMerchantSearchResultItem)c);
			}
		}
		if(items.size() > merchants.size()){
			for(int i = 0; i < merchants.size(); i++){
				items.get(i).setMerchant(merchants.get(i));
				items.get(i).setDisplay(true);
			}
			for(int i = merchants.size(); i < items.size(); i++){
				items.get(i).setDisplay(false);
			}
		}else{
			for(int i = 0; i < items.size(); i++){
				items.get(i).setMerchant(merchants.get(i));
				items.get(i).setDisplay(true);
			}
//			for(int i = items.size(); i < merchants.size(); i++){
//				
//				EXMerchantSearchResultItem item = new EXMerchantSearchResultItem("");
//				item.setMerchant(merchants.get(i));
//				addChild(item);
//				//items.get(i).setDisplay(false);
//			}
		}
		getDescendentByName("page").setText((curPage_ + 1) + " / " + (pages));
	}



	public void executeAction(Container source) {
		if(source.getName().equalsIgnoreCase("previousPage")){
			previousPage(source);
		}else{
			nextPage(source);
		}
		
	}



	@Override
	public void ClientAction(ClientProxy container) {

		String direction = "left";
		
		if(container.getName().equals("previousPage")){
			direction = "right";	
		}
		
		ClientProxy prev = container.clone().mergeCommand(new ClientProxy(".exsi").addMethod("hide", "slide", new JMap().put("direction", direction))).makeServerRequest(this);
		container.IF(container.getAttribute("move").equal("false"), container.clone(), prev);
		
		
		
		
		
	}



	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		executeAction(container);
		return true;
	}



	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		String direction = "right";
		
		if(container.getName().equals("previousPage")){
			direction = "left";
		}
		container.mergeCommand( new ClientProxy(".exsi").addMethod("show", "slide", new JMap().put("direction", direction))  );
		
	}
}
