package org.castafiore.shoppingmall.ng;

import java.util.List;

import org.castafiore.catalogue.Product;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.panel.EXOverlayPopupPlaceHolder;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.castafiore.wfs.types.Article;

public class EXMallNG extends EXXHTMLFragment implements PopupContainer{

	public EXMallNG(String name) {
		super(name, "templates/ng/EXMallNG.xhtml");
		addChild(new EXOverlayPopupPlaceHolder("popupContainer"));
		
		List<Product> recent  = MallUtil.getCurrentMall().getRecentProducts(2);
		
		List<Article> arts = (List)SpringUtil.getRepositoryService().executeQuery(new QueryParameters().setEntity(Article.class).addSearchDir("/root/users/archnetltd"), Util.getRemoteUser());//.getDirectory("/root/users/archnetltd", Util.getRemoteUser()).getFiles(Article.class).toList();
		
		addChild(new EXLatestNews("latestNews", arts));
		
		addChild(new EXContainer("listCoupDeCoeur", "div").setStyle("width", "290px"));
		for(Product p : recent){
			getChild("listCoupDeCoeur").addChild(new EXProductItemNG("Red").setProduct(p));
		}
		
		addChild(new EXContainer("listMostSold", "div").setStyle("width", "290px"));
		for(Product p : recent){
			getChild("listMostSold").addChild(new EXProductItemNG("Blue").setProduct(p));
		}
		
		home();
		// TODO Auto-generated constructor stub
	}
	
	public void home(){
		EXCatalogueNG catalogue = getDescendentOfType(EXCatalogueNG.class);
		if(catalogue == null){
			catalogue = new EXCatalogueNG("catalogue");
			addChild(catalogue);
		}
		catalogue.search("recent", "Blue");
	}

	@Override
	public void addPopup(Container popup) {
		getChild("popupContainer").addChild(popup);
		
	}

}
