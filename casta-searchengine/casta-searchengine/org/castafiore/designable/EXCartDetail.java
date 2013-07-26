package org.castafiore.designable;

import java.util.Map;

import org.castafiore.designable.checkout.EXCheckout;
import org.castafiore.designable.checkout.EXOrderReview;
import org.castafiore.searchengine.EXSearchEngineApplication;
import org.castafiore.shoppingmall.merchant.FinanceUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.utils.StringUtil;

public class EXCartDetail extends AbstractXHTML implements Event, CurrencySensitive{

	public EXCartDetail(String name) {
		super(name);
		setStyle("margin-top", "30px");
		addChild(new EXContainer("ttsubTotal", "span"));
		
		addChild(new EXContainer("ttvat", "span"));
		
		addChild(new EXContainer("tttotal", "span"));
		
		addChild(new EXButton("checkout", "Proceed to checkout").setStyle("float", "right").addEvent(this, Event.CLICK));
		addChild(new EXButton("continueShopping", "Continue shopping").setStyle("float", "left").addEvent(this, Event.CLICK));
	}
	
	public void init(EXMiniCart miniCart){
		
		setAttribute("cartid", miniCart.getId());
		setAttribute("merchant",miniCart.getName());
		EXTable table = getDescendentOfType(EXTable.class);
		if(table == null){
			table = new EXTable("details", new CartDetailModel(miniCart));
			table.setCellRenderer(new CartDetailCellRenderer());
			addChild(table);
		}else{
			table.setModel(new CartDetailModel(miniCart));
		}
		
		double sTotal = miniCart.getSubTotal().doubleValue();
		double total = miniCart.getTotal().doubleValue();
		double vat = total - sTotal;
		getChild("ttsubTotal").setText(StringUtil.toCurrency(FinanceUtil.getCurrentCurrency(),sTotal));
		getChild("tttotal").setText(StringUtil.toCurrency(FinanceUtil.getCurrentCurrency(),total));
		getChild("ttvat").setText(StringUtil.toCurrency(FinanceUtil.getCurrentCurrency(),vat));
		
		if(miniCart.getItems().size() == 0){
			table.getChildren().get(1).addChild(new EXContainer("", "tr").addChild(new EXContainer("", "td").setAttribute("colspan", "5").setText("Your shopping cart is empty").setStyle("text-align", "center").setStyle("font-weight", "bold")));
			getChild("checkout").setDisplay(false);
		}else{
			getChild("checkout").setDisplay(true);
		}
	}
	
	
	public void update(EXMiniCart miniCart){
		setAttribute("cartid", miniCart.getId());
		setAttribute("merchant",miniCart.getName());
		double sTotal = miniCart.getSubTotal().doubleValue();
		double total = miniCart.getTotal().doubleValue();
		double vat = total - sTotal;
		getChild("ttsubTotal").setText(StringUtil.toCurrency(FinanceUtil.getCurrentCurrency(),sTotal));
		getChild("tttotal").setText(StringUtil.toCurrency(FinanceUtil.getCurrentCurrency(),total));
		getChild("ttvat").setText(StringUtil.toCurrency(FinanceUtil.getCurrentCurrency(),vat));
		EXTable table = getDescendentOfType(EXTable.class);
		if(miniCart.getItems().size() == 0){
			table.getChildren().get(1).addChild(new EXContainer("", "tr").addChild(new EXContainer("", "td").setAttribute("colspan", "5").setText("Your shopping cart is empty").setStyle("text-align", "center").setStyle("font-weight", "bold")));
			getChild("checkout").setDisplay(false);
		}else{
			getChild("checkout").setDisplay(true);
		}
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		EXEcommerce ecommerce = container.getAncestorOfType(EXEcommerce.class);
		
		if(container.getName().equals("checkout")){
			EXCheckout cat = null;
			if(ecommerce != null)
				cat =(EXCheckout)ecommerce.getPageOfType(EXCheckout.class);
			else
				cat = getRoot().getDescendentOfType(EXCheckout.class);
			if(cat == null){
				cat = new EXCheckout("");
				cat.setStyle("z-index", "4000");
				cat.setAttribute("merchant", getAttribute("merchant"));
				cat.setAttribute("cartid", getAttribute("cartid"));
				if(ecommerce == null){
					Container parent = getParent();
					parent.getChildren().clear();
					getAncestorOfType(PopupContainer.class).addPopup(cat);
				}else{
					ecommerce.getBody().addChild(cat);
				}
			}else{
				EXOrderReview rev = cat.getDescendentOfType(EXOrderReview.class);
				cat.setAttribute("merchant", getAttribute("merchant"));
				cat.setAttribute("cartid", getAttribute("cartid"));
				if(rev != null){
					if(ecommerce == null){
						rev.reInit((EXMiniCart)getRoot().getDescendentById(getAttribute("cartid")));
					}else{
						rev.reInit(ecommerce.getDescendentOfType(EXMiniCart.class));
					}
					
				}
			}
			
			if(ecommerce != null)
				ecommerce.showPage(EXCheckout.class);
		}else{
			if(ecommerce == null){
				Container parent = getParent();
				parent.getChildren().clear();
				parent.setRendered(false);
			}else{
				ecommerce.showPage(EXCatalogue.class);
			}
		}
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		if(container.getAncestorOfType(EXSearchEngineApplication.class) != null){
			container.getAncestorOfType(EXCartDetail.class).fadeOut(100);
		}
		
	}

	@Override
	public void changeCurrency() throws Exception {
		init(getAncestorOfType(EXEcommerce.class).getDescendentOfType(EXMiniCart.class));
		
	}

}

