package org.castafiore.shoppingmall.product.ui;

import java.util.List;

import org.castafiore.catalogue.Product;
import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.user.ui.EXAboutMe;
import org.castafiore.shoppingmall.user.ui.EXUploadExcel;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.layout.EXBorderLayoutContainer;
import org.castafiore.ui.ex.panel.EXOverlayPopupPlaceHolder;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.ui.ex.panel.Panel;
import org.castafiore.ui.ex.toolbar.EXToolBar;

public class EXProductsPanel extends EXBorderLayoutContainer implements EventDispatcher, PopupContainer, ProductContainerPanel{

	private boolean simple = false;
	
	public EXProductsPanel(String name, String title) {
		super(name);
		
		
		getChild("popupContainer").remove();
		EXToolBar tb = new EXToolBar("btnCtn");
		addChild(tb, TOP);
		addChild(new EXOverlayPopupPlaceHolder("popupContainer"));
		getContainer(CENTER).setStyle("vertical-align", "top").setStyle("padding", "0");
		getContainer(LEFT).setStyle("width", "0px").setStyle("padding", "0");
		getContainer(RIGHT).setStyle("padding", "0");
		getContainer(TOP).setStyle("padding", "0");
		//showProductList(Product.STATE_PUBLISHED);
	}
	public void setTitle(String title){
		getAncestorOfType(Panel.class).setTitle(title);
	}
	
	public boolean isSimple() {
		return simple;
	}
	public void setSimple(boolean simple) {
		this.simple = simple;
	}
	public void showProductList(int  state){
		
		if(getAncestorOfType(Panel.class) != null){
		if(state == Product.STATE_DRAFT)
			getAncestorOfType(Panel.class).setTitle("My Products and services (Draft)");
		else if(state == Product.STATE_PUBLISHED)
			getAncestorOfType(Panel.class).setTitle("My Products and services (Published)");
		else if(state == Product.STATE_DELETED)
			getAncestorOfType(Panel.class).setTitle("My Products and services (Deleted)");
		}
		
		EXToolBar tb = getDescendentOfType(EXToolBar.class);
		tb.getChildren().clear();
		
		tb.addChild(new EXButton("products","Products").addEvent(DISPATCHER, Event.CLICK));
		tb.addChild(new EXButton("newproduct","Create Product").addEvent(DISPATCHER, Event.CLICK));
		
		
		String plan = MallUtil.getCurrentMerchant().getPlan();
		if(!plan.equalsIgnoreCase("free")){
			tb.addChild(new EXButton("upload","Upload Products").addEvent(DISPATCHER, Event.CLICK));
			tb.addChild(new EXButton("reports","Reports").addEvent(DISPATCHER, Event.CLICK));
		}
		
		
		if(getDescendentOfType(EXProductList.class) == null){
			Container c =new EXContainer("leftButtons", "div").addClass("fieldset").setStyle("width", "80px").setStyle("float", "left").setStyle("padding", "9px");
			addChild(c, LEFT);
			
			c.addChild(new EXButton("draft","Draft").addEvent(DISPATCHER, Event.CLICK).setStyle("width", "80px").setStyle("margin", "12px 0"));
			c.addChild(new EXButton("published","Published").addEvent(DISPATCHER, Event.CLICK).setStyle("width", "80px").setStyle("margin", "12px 0"));
			c.addChild(new EXButton("bin","Deleted").addEvent(DISPATCHER, Event.CLICK).setStyle("width", "80px").setStyle("margin", "12px 0"));
			getContainer(LEFT).setStyle("vertical-align", "top");
			addChild(new EXProductList("").setStyle("width", "100%"), CENTER);
			
		}
		
		List<Product> products = MallUtil.getCurrentUser().getMerchant().getMyProducts(state);
		getDescendentOfType(EXProductList.class).setProducts(products);
		getDescendentOfType(EXProductList.class).setDisplay(true);
		getDescendentByName("leftButtons", LEFT).setDisplay(true);
		
		if(getDescendentOfType(EXProductEditCard.class) !=null){
			getDescendentOfType(EXProductEditCard.class).setStyle("display", "none");
		}
		
		if(getDescendentOfType(EXReportPanel.class) !=null){
			getDescendentOfType(EXReportPanel.class).setStyle("display", "none");
		}
		
		
	}
	
	public void showProductList(){
		EXToolBar tb = getDescendentOfType(EXToolBar.class);
		tb.getChildren().clear();
		
		tb.addChild(new EXButton("products","Products").addEvent(DISPATCHER, Event.CLICK));
		tb.addChild(new EXButton("newproduct","Create Product").addEvent(DISPATCHER, Event.CLICK));
		
		
		String plan = MallUtil.getCurrentMerchant().getPlan();
		if(!plan.equalsIgnoreCase("free")){
			tb.addChild(new EXButton("upload","Upload Products").addEvent(DISPATCHER, Event.CLICK));
			tb.addChild(new EXButton("reports","Reports").addEvent(DISPATCHER, Event.CLICK));
		}
		
		
		if(getDescendentOfType(EXProductList.class) == null){
			Container c =new EXContainer("leftButtons", "div").addClass("fieldset").setStyle("width", "80px").setStyle("float", "left").setStyle("padding", "9px");
			addChild(c, LEFT);
			
			c.addChild(new EXButton("draft","Draft").addEvent(DISPATCHER, Event.CLICK).setStyle("width", "80px"));
			c.addChild(new EXButton("published","Published").addEvent(DISPATCHER, Event.CLICK).setStyle("width", "80px"));
			c.addChild(new EXButton("bin","Deleted").addEvent(DISPATCHER, Event.CLICK).setStyle("width", "80px"));
			addChild(new EXProductList("").setStyle("width", "100%"), CENTER);
		}else{
			getDescendentOfType(EXProductList.class).setDisplay(true);
			getDescendentByName("leftButtons", LEFT).setDisplay(true);
		}
		
		if(getDescendentOfType(EXProductEditCard.class) !=null){
			getDescendentOfType(EXProductEditCard.class).setStyle("display", "none");
		}
		
		if(getDescendentOfType(EXReportPanel.class) !=null){
			getDescendentOfType(EXReportPanel.class).setStyle("display", "none");
		}
		
	}
	public void showProductEditCard(Product product){
		getAncestorOfType(Panel.class).setTitle("Create a new product");
		if(getDescendentOfType(EXProductEditCard.class)==null){
			EXProductEditCard card = new EXProductEditCard("editCard", simple);
			card.setProduct(product);
			addChild(card, CENTER);
		}else{
			getDescendentOfType(EXProductEditCard.class).setStyle("display", "block");
			getDescendentOfType(EXProductEditCard.class).setProduct(product);
			getDescendentByName("leftButtons", LEFT).setDisplay(true);
		}
		
		if(getDescendentOfType(EXProductList.class) !=null){
			getDescendentOfType(EXProductList.class).setStyle("display", "none");
			getDescendentByName("leftButtons", LEFT).setDisplay(false);
		}
		
		if(getDescendentOfType(EXReportPanel.class) !=null){
			getDescendentOfType(EXReportPanel.class).setStyle("display", "none");
		}
	}
	
	
	public void showReports(){
		getAncestorOfType(Panel.class).setTitle("Reports");
		
		if(getDescendentOfType(EXReportPanel.class)==null){
			EXReportPanel card = new EXReportPanel("editCard");
			addChild(card, CENTER);
		}else{
			getDescendentOfType(EXReportPanel.class).setDisplay(true);
		}
		
		if(getDescendentOfType(EXProductList.class) !=null){
			getDescendentOfType(EXProductList.class).setStyle("display", "none");
			getDescendentByName("leftButtons", LEFT).setDisplay(false);
		}
		
		if(getDescendentOfType(EXProductEditCard.class) !=null){
			getDescendentOfType(EXProductEditCard.class).setStyle("display", "none");
		}
	}
	
	@Override
	public void addPopup(Container popup) {
		getDescendentOfType(EXOverlayPopupPlaceHolder.class).addChild(popup);	
	}

	@Override
	public void executeAction(Container source) {
		if(source.getName().equalsIgnoreCase("draft")){
			showProductList(Product.STATE_DRAFT);
		}else if(source.getName().equalsIgnoreCase("published")){
			showProductList(Product.STATE_PUBLISHED);
		}else if(source.getName().equalsIgnoreCase("bin")){
			showProductList(Product.STATE_DELETED);
		}else if(source.getName().equalsIgnoreCase("newproduct")){
			showProductEditCard(null);
		}else if(source.getName().equalsIgnoreCase("upload")){
			addPopup(new EXUploadExcel("gfdgdfgdfgd").setStyle("z-index", "3003").setStyle("width", "500px"));
		}else if(source.getName().equals("reports")){
			showReports();
		}else if(source.getName().equalsIgnoreCase("products")){
			showProductList(Product.STATE_PUBLISHED);
		}
	}

}
