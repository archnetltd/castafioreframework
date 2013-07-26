package org.castafiore.shoppingmall.product.ui.tab;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.catalogue.Product;
import org.castafiore.ecm.ui.finder.EXFinder;
import org.castafiore.ecm.ui.finder.EXFinder.SelectFileHandler;
import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.user.ShoppingMallUser;
import org.castafiore.shoppingmall.user.ShoppingMallUserManager;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.panel.EXOverlayPopupPlaceHolder;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.iterator.FileFilter;
import org.castafiore.wfs.iterator.FileIterator;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;
import org.castafiore.wfs.types.Link;

public class EXImages extends EXAbstractProductTabContent implements EventDispatcher,FileFilter, SelectFileHandler, PopupContainer{

	public EXImages() {
		super();
		addChild(new EXContainer("slider", "img").setAttribute("src", "blueprint/upload.gif").addEvent(DISPATCHER, Event.CLICK));
		addChild(new EXContainer("thumbnails", "ul").addClass("thumbnails").addClass("span-14"));
		Container thumbnails = getChild("thumbnails");
		String plan = MallUtil.getCurrentMerchant().getPlan();
		int maxImages= 6;
		if(plan.equalsIgnoreCase("free")){
			maxImages = 1;
		}
		for(int i = 0; i < maxImages; i++){
			thumbnails.addChild(new EXContainer("", "li").addChild(new EXContainer("img", "img").setAttribute("src", "blueprint/upload.gif").addEvent(DISPATCHER, Event.CLICK)));
		}
		
		addChild(new EXOverlayPopupPlaceHolder("popupContainer"));
	}

	@Override
	public void executeAction(Container source) {
		if(source.getName().equals("slider")){
			clickImage();
		}else{
			clickThumbnail(source);
		}
		
	}

	@Override
	public Container setProduct(Product product){
			refreshThumbnails(product);
			return this;
	}
	
	
	private void refreshThumbnails(Product product){
		
		Container thumbnails = getChild("thumbnails");
		List<Link> images = new ArrayList<Link>();
		if(product != null){
			FileIterator<Link> iterImages = product.getImages();
			if(iterImages != null)
				images = iterImages.toList();
		}
		String plan = MallUtil.getCurrentMerchant().getPlan();
		int maxImages= 6;
		if(plan.equalsIgnoreCase("free")){
			maxImages = 1;
		}
		for(int i = 0; i < maxImages; i ++){
			Container img = thumbnails.getChildByIndex(i).getChildByIndex(0);
			if(i == 0){
				clickThumbnail(img);
			}
			if(i < images.size()){
				Link next = images.get(i);
				img.setAttribute("src", next.getUrl()).setAttribute("path", next.getUrl());
			}else{
				img.setAttribute("src", "blueprint/upload.gif").setAttribute("path", (String)null);
			}	
		
		}
	}
	
	public void clickThumbnail(Container source){
		getChild("slider").setAttribute("src", source.getAttribute("src"));
		getChild("slider").setAttribute("tid", source.getId());
		
	}
	
	public void clickImage(){
		Container slider = getChild("slider");
		if(StringUtil.isNotEmpty(slider.getAttribute("tid"))){
			
		}
		EXFinder finder = new EXFinder("filder", this, this, "/root/users/" + Util.getRemoteUser() + "/Media" );
		getAncestorOfType(PopupContainer.class).addPopup(finder);
		
	}
	
	@Override
	public boolean accept(File file) {
		if(file.getClazz().equalsIgnoreCase(Directory.class.getName()) || file instanceof BinaryFile){
			return true;
		}
		return false;
	}


	@Override
	public void onSelectFile(File file, EXFinder finder) {
		if(file.getAbsolutePath() == null){
			Container img = getDescendentById( getChild("slider").getAttribute("tid"));
			img.setAttribute("src", file.getName());
		}else{
			Container img = getDescendentById( getChild("slider").getAttribute("tid"));
			img.setAttribute("src", ResourceUtil.getDownloadURL("ecm", file.getAbsolutePath())).setAttribute("path",file.getAbsolutePath());
		}
		
		//finder.remove();
		//parent.setRendered(false);

		
	}

	@Override
	public void fillProduct(Product product) {
		
		ShoppingMallUser user = MallUtil.getCurrentUser();
		List<Container> thumbnails = getChild("thumbnails").getChildren();
		int i = 0;
		for(Container li : thumbnails){
			String path = li.getChild("img").getAttribute("path");
			if(path != null){
				Link shortcut = product.createImage("Image " + (i+1), ResourceUtil.getDownloadURL("ecm", path));
				if(path.startsWith("castafiore/resource?spec="))
					shortcut.setUrl(path);
					
				//(name)//new Link();
				shortcut.makeOwner(user.getUser().getUsername());
			}
			i++;
		}
		
		
	}

	@Override
	public void addPopup(Container popup) {
		getChild("popupContainer").addChild(popup);
		
	}

}
