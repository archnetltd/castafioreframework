package org.castafiore.shoppingmall.merchant;

import java.util.Calendar;
import java.util.List;

import org.castafiore.catalogue.Product;
import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.cart.EXMerchantInfo;
import org.castafiore.shoppingmall.product.ui.EXComment;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXTextArea;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.iterator.FileIterator;
import org.castafiore.wfs.types.Comment;


public class EXMerchantCard extends EXXHTMLFragment implements EventDispatcher{

	public EXMerchantCard(String name) {
		super(name, "/templates/EXMerchantCard.xhtml");
		addClass("msr").addClass("merchantcard");
		addChild(new EXMerchantInfo("merchantInfo").setStyle("width", "495px"));
		addChild(new EXContainer("search", "button").addEvent(DISPATCHER, Event.CLICK));
		addChild(new EXInput("merchantSearchInput"));
		addChild(new EXContainer("searchResults", "div"));
		
		
		//addChild(new EXTextArea("commentText").addClass("commentinput"));
		//addChild(new EXContainer("addComment", "button").setText("Add Comment").addEvent(DISPATCHER, Event.CLICK).setStyle("clear", "both"));
		//addChild(new EXContainer("commentList", "div").addClass("ProductCommentList"));
	}
	
	public void addComment(){
		 EXTextArea area = (EXTextArea)getDescendentByName("commentText");
		 String comment = area.getValue().toString();
		 if(StringUtil.isNotEmpty(comment)){
			Merchant merchant = (Merchant)SpringUtil.getRepositoryService().getFile(getAttribute("path"), Util.getRemoteUser());
			Comment commen = merchant.createComment(Calendar.getInstance().getTime().getTime() + "");
			commen.setTitle("Comment added by " + Util.getRemoteUser() + " on " + Calendar.getInstance().getTime().toString());
			commen.setSummary(comment);
			commen.setAuthor(Util.getRemoteUser());
			commen.setOwner(Util.getRemoteUser());
			merchant.save();
			EXComment comm = new EXComment("");
			comm.setComment(commen);
			getChild("CommentList").addChildAt(comm, 0).setRendered(false);
		 }
	}
	
	public void setMerchant(Merchant merchant){
		setAttribute("path",merchant.getAbsolutePath());
		setAttribute("username", merchant.getUsername());
		getDescendentOfType(EXMerchantInfo.class).setMerchant(merchant);
		getChild("searchResults").getChildren().clear();
		getChild("searchResults").setRendered(false);
	}
	
	
	public void searchProducts(){
		String input = getDescendentOfType(EXInput.class).getValue().toString();
		String provider = getAttribute("username");
		
		if(StringUtil.isNotEmpty(input)){
			
		
			String searcTerm = "full:" + input + ",provider:" + provider;
			List<Product> products = MallUtil.getCurrentMall().searchProducts(searcTerm, 0, 9);
			Container searchs = getChild("searchResults");
			searchs.getChildren().clear();
			searchs.setRendered(false);
			for(Product p : products){
				EXProductResultItem item = new EXProductResultItem("");
				item.setProduct(p);
				searchs.addChild(item);
			}
		}
	}
	@Override
	public void executeAction(Container source) {
		if(source.getName().equalsIgnoreCase("subscribe")){
			//subscribe();
		}else if(source.getName().equals("addComment")){
			addComment();
		}else if(source.getName().equalsIgnoreCase("search")){
			searchProducts();
		}
	}
	
	

}
