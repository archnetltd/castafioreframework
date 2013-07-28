package org.castafiore.shoppingmall.product.ui.simple;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.castafiore.catalogue.Product;
import org.castafiore.resource.FileData;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.product.ui.tab.EXAbstractProductTabContent;
import org.castafiore.shoppingmall.user.ShoppingMallUser;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.EXGrid;
import org.castafiore.ui.ex.EXGrid.EXRow;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXUpload;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.button.EXIconButton;
import org.castafiore.ui.ex.form.button.Icons;
import org.castafiore.ui.ex.panel.Panel;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.iterator.FileIterator;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Link;

public class EXSimpleImageUpload extends EXAbstractProductTabContent implements Event{

	public EXSimpleImageUpload() {
		super();
		
		Container c = new EXContainer("dd", "div");
		addChild(c);
		EXGrid grid = new EXGrid("grid", 2, 0);
		
		EXButton add = new EXButton("add", "Add Images");
		add.addEvent(this, CLICK);
		c.addChild(add);
		c.addChild(grid);
	}

	@Override
	public void fillProduct(Product product) {
		ShoppingMallUser user = MallUtil.getCurrentUser();
		EXGrid grid = getDescendentOfType(EXGrid.class);
		int i = 0;
		for(Container tr : grid.getChildren()){
			String path = tr.getDescendentByName("i").getAttribute("src");
			if(path != null){
				Link shortcut = product.createImage("Image " + (i+1), ResourceUtil.getDownloadURL("ecm", path));
				shortcut.setUrl(path);
				shortcut.makeOwner(user.getUser().getUsername());
			}
			i++;
		}
	}

	@Override
	public Container setProduct(Product product) {
		if(product != null){
		FileIterator<Link> links = product.getImages();
		EXGrid grid = getDescendentOfType(EXGrid.class);
		for(Link l : links.toList()){
			addImage(l.getUrl(), grid);
		}
		}
		return this;
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}
	
	private void showUploadImages(){
		EXDynaformPanel panel = new EXDynaformPanel("addImage", "Add multiple images");
		panel.addField("Upload images", new EXUpload("upl"));
		EXButton save =  new EXButton("save", "Save images");
		save.addEvent(this, CLICK);
		EXButton cancel = new EXButton("cancel", "Cancel");
		cancel.addEvent(Panel.CLOSE_EVENT, Event.CLICK);
		panel.addButton(save);
		panel.addButton(cancel);
		panel.setStyle("width", "600px").setStyle("z-index", "3000");
		getAncestorOfType(PopupContainer.class).addPopup(panel);
	}
	
	
	private void saveUploaded(Container btn){
		EXUpload upl = btn.getAncestorOfType(EXDynaformPanel.class).getDescendentOfType(EXUpload.class);
		Object o = upl.getValue();
		if(o instanceof List){
			List<FileData> uploaded = (List<FileData>)o;
			addToGrid(uploaded);
		}else{
			List<FileData> uploaded = new ArrayList<FileData>(1);
			uploaded.add((FileData)o);
			addToGrid(uploaded);
		}
		btn.getAncestorOfType(EXDynaformPanel.class).setDisplay(false).remove();
		
	}
	
	private void addToGrid(List<FileData> files){
		EXGrid grid = getDescendentOfType(EXGrid.class);
		for(FileData f : files){
			
			BinaryFile bf = (BinaryFile)f;
			bf.putInto("/root/users/" + Util.getLoggedOrganization() + "/Media/Images");
			bf.save();
			String src = ResourceUtil.getDownloadURL("ecm", bf.getAbsolutePath());
			addImage(src, grid);
		}
	}
	
	private void addImage(String src, EXGrid grid){
		EXRow row = grid.addRow();
		Container img = new EXContainer("i", "img").setStyle("width", "128px").setStyle("padding", "6px").setAttribute("src", src);
		row.addInCell(0, img);
		row.addInCell(1, new EXIconButton("del", Icons.ICON_CIRCLE_MINUS).setAttribute("title", "Remove this image").addEvent(this, CLICK));
	}
	
	private void removeImg(Container btn){
		btn.getParent().getParent().remove();
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		if(container.getName().equalsIgnoreCase("del")){
			removeImg(container);
		}else if(container.getName().equalsIgnoreCase("add")){
			showUploadImages();
		}else if(container.getName().equalsIgnoreCase("save")){
			saveUploaded(container);
		}
		
		
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
