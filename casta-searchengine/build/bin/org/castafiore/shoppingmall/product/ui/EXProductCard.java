package org.castafiore.shoppingmall.product.ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.castafiore.catalogue.Product;
import org.castafiore.searchengine.EXMall;
import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.ui.MallLoginSensitive;
import org.castafiore.shoppingmall.user.ui.EXMerchantCardLink;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXTextArea;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.iterator.FileIterator;
import org.castafiore.wfs.types.Comment;
import org.castafiore.wfs.types.Link;

public class EXProductCard extends EXXHTMLFragment implements EventDispatcher, MallLoginSensitive{
	
	private final static String[] ACTIONS = new String[]{"thumb-up.png", "thumb.png", "heart.png", "blue-document-list.png"};
	

	public EXProductCard(String name) {
		super(name, "templates/EXProductCard.xhtml");
		
		addClass("msr").addClass("productcard");
	
		addChild(new EXContainer("title","h3" ).addClass("title"));
		addChild(new EXContainer("slider", "img"));
		addChild(new EXContainer("thumbnails", "ul").addClass("span-13"));
		
		addChild(new EXContainer("price", "h3"));
		addChild(new EXContainer("views", "label"));
		
		Container thumbnails = getChild("thumbnails");
		for(int i = 0; i < 6; i++){
			thumbnails.addChild(new EXContainer("", "li").addChild(new EXContainer("img", "img").setAttribute("src", "blueprint/upload.gif").addEvent(DISPATCHER, Event.CLICK)));
		}
		addChild(new EXContainer("like", "div").addClass("like"));
		addChild(new EXContainer("nolike", "div").addClass("nolike"));
		addChild(new EXContainer("txtlike", "div"));
		addChild(new EXContainer("txtnolike", "div"));

		if(Util.getRemoteUser() != null){
			for(String action : ACTIONS ){
					addChild(new EXContainer(action.replace(".png", ""), "div").setStyle("background-image", "url(icons-2/fugue/icons/" +action + ")").addEvent(DISPATCHER, Event.CLICK));
			}
		}
		
		addChild(new EXContainer("shopping-basket", "div").setStyle("background-image", "url(icons-2/fugue/icons/shopping-basket.png)").addEvent(DISPATCHER, Event.CLICK));
		
		addChild(new EXMerchantCardLink("provider"));
		
		addChild(new EXContainer("description", "div").addClass("description"));
		
		addChild(new EXTextArea("commentinput").addClass("commentinput"));
		
		addChild(new EXContainer("addComment", "button").setText("Add Comment").setStyle("float", "left").setStyle("clear", "both").addEvent(DISPATCHER, Event.CLICK));
		
		addChild(new EXContainer("CommentList", "div").addClass("ProductCommentList"));
	}
	
	public void setProduct(Product product){
		setAttribute("path", product.getAbsolutePath());
		String title = "Item code :" + product.getCode() + " - " + product.getTitle();
		getChild("title").setText(title);
		getDescendentOfType(EXMerchantCardLink.class).setMerchantUsername(product.getProvidedBy());
		getChild("description").setText(product.getSummary());
		getChild("price").setText(product.getTotalPrice().toPlainString());
		getChild("views").setText("223 views");
		getDescendentOfType(EXTextArea.class).setValue("");
		
		double like = product.getLikeIt();
		double noLike = product.getDislikeIt();
		double total = like + noLike;
		if(total > 0){
			double percentlike = (like/total)*100;
			double percentNoLike = (noLike/total)*100;
			getChild("like").setStyle("width", percentlike + "px");
			getChild("nolike").setStyle("width", percentNoLike + "px");
		}else{
			getChild("like").setStyle("width", 0 + "px");
			getChild("nolike").setStyle("width", 0 + "px");
		}
		
		getChild("txtlike").setText(like + " likes");
		getChild("txtnolike").setText(noLike + " dislikes");
		
		FileIterator<Link> images = product.getImages();
		List<String> pathss = new ArrayList<String>(6);
		if(images != null){
			List<Link> lstImages = images.toList();
			
			for(int i = 0; i < lstImages.size(); i++){
				String path = lstImages.get(i).getUrl();
					pathss.add(path);
			}
		}
		
		if(pathss.size() > 0){
			getDescendentByName("slider").setDisplay(true);
			Container thumbnails = getDescendentByName("thumbnails");
			thumbnails.setDisplay(true);
			for(int i = 0; i < 6; i ++){
				if(pathss.size() > i){
					String path = pathss.get(i);
					if(i == 0){
						getDescendentByName("slider").setAttribute("src",  path);
					}
					thumbnails.getChildByIndex(i).getChildByIndex(0).setAttribute("src", path).setDisplay(true);
				}else{
					thumbnails.getChildByIndex(i).getChildByIndex(0).setDisplay(false);
				}
			}
		}else{
			getDescendentByName("thumbnails").setDisplay(false);
			getDescendentByName("slider").setDisplay(false);
		}
		
		getChild("CommentList").getChildren().clear();
		getChild("CommentList").setRendered(false);
		 FileIterator<Comment> comments = product.getComments();
		 if(comments != null){
			 while(comments.hasNext()){
				 Comment comment = comments.next();
				 
				 EXComment comm = new EXComment("");
				 comm.setComment(comment);
				 getChild("CommentList").addChild(comm);
			 }
		 }
		
	}
	
	
	
	
	public  Product getProduct(){
		return (Product)SpringUtil.getRepositoryService().getFile(getAttribute("path"), Util.getRemoteUser());
	}
	public void addComment(){
		 EXTextArea area = (EXTextArea)getDescendentByName("commentinput");
		 String comment = area.getValue().toString();
		 if(StringUtil.isNotEmpty(comment)){
			 Product product = getProduct();
			 Comment commen = product.createComment(Calendar.getInstance().getTime().getTime() + "");//new Comment();
			 commen.setTitle("Comment added by " + Util.getRemoteUser() + " on " + Calendar.getInstance().getTime().toString());
			
			 commen.setSummary(comment);
			 commen.setAuthor(Util.getRemoteUser());
			 commen.setOwner(Util.getRemoteUser());
			
			 product.save();
			 EXComment comm = new EXComment("");
			 comm.setComment(commen);
			 getChild("CommentList").addChildAt(comm, 0).setRendered(false);
		 }
	}
	
	//"Add to cart", "I like this", "I don't like this", "Add to favorite"
	
	public void executeAction(Container source){
		if(source.getName().equalsIgnoreCase("img")){
			getDescendentByName("slider").setAttribute("src", source.getAttribute("src"));
		}else if(source.getName().equalsIgnoreCase("addComment")){
			addComment();
		}else if(source.getName().equalsIgnoreCase("heart")){
			addToFavorit();
		}else if(source.getName().equalsIgnoreCase("thumb-up")){
			thumbUp();
		}else if(source.getName().equalsIgnoreCase("thumb")){
			thumbDown();
		}else if(source.getName().equalsIgnoreCase("shopping-basket")){
			addToCart(source);
		}
	}
	
	
	public void addToFavorit(){
		MallUtil.getCurrentUser().addToFavorite(getAttribute("path"));
	}
	
	
	public void thumbUp(){
		Product p = getProduct();
		p.thumbUp();
		p.save();
		//SpringUtil.getRepositoryService().update(p, p.getOwner());
	}
	
	
	public void thumbDown(){
		Product p = getProduct();
		p.thumbDown();
		p.save();
		
	}
	
	
	public void addToCart(Container source){
		Product p = getProduct();
		getAncestorOfType(EXMall.class).getDescendentOfType(EXMiniCarts.class).getMiniCart(p.getProvidedBy()).addToCart(p,1,source);
		//MallUtil.getCurrentUser().
	}

	@Override
	public void onLogin(String username) {
		for(String action : ACTIONS ){
			addChild(new EXContainer(action.replace(".png", ""), "div").setStyle("background-image", "url(icons-2/fugue/icons/" +action + ")").addEvent(DISPATCHER, Event.CLICK));
		}
		
	}
	

	
}
