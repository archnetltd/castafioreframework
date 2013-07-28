package org.castafiore.designable;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.castafiore.designer.marshalling.DesignableUtil;
import org.castafiore.shoppingmall.merchant.FinanceUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.button.EXIconButton;
import org.castafiore.ui.ex.form.button.Icons;
import org.castafiore.ui.ex.form.table.CellRenderer;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.utils.StringUtil;

public class CartDetailCellRenderer implements CellRenderer ,Event{

	@Override
	public Container getComponentAt(int row, int column, int page,
			TableModel model, EXTable table) {
		CartItem item = (CartItem)model.getValueAt(column, row, page);
		if(column == 0){
			return new EXContainer("", "img").setAttribute("width", "81").setAttribute("height", "81").setAttribute("src", item.getImg());
		}else if(column == 1){
			Container c = new EXContainer("", "div").addChild(new EXContainer("title", "h5").setStyle("margin", "5px 0").setText(item.getTitle())).addChild(new EXIconButton("del", Icons.ICON_CIRCLE_CLOSE).addEvent(this, CLICK).setStyle("width", "5px").setStyle("height", "5px").setAttribute("path", item.getAbsolutePath()));
			if(item.getOptions() != null){
				c.addChild(new EXIconButton("options", Icons.ICON_WRENCH).setAttribute("title", "View options").addEvent(this, CLICK).setStyle("width", "5px").setStyle("height", "5px").setAttribute("path", item.getAbsolutePath()));
			}
			return c;
		}else if(column == 2){
			return new EXContainer("price", "span").setText(StringUtil.toCurrency(FinanceUtil.getCurrentCurrency(),item.getTotalPrice()));
		}else if(column == 3){
			return new EXInput("qty",item.getQty().toPlainString()).setStyle("width", "30px").addEvent(this, BLUR).setAttribute("path", item.getAbsolutePath());
		}else{
			return new EXContainer("subTotal", "span").setText( StringUtil.toCurrency(FinanceUtil.getCurrentCurrency(),item.getTotalPrice().multiply(item.getQty())));
		}
	}

	@Override
	public void onChangePage(Container component, int row, int column,
			int page, TableModel model, EXTable table) {
//		CartItem item = (CartItem)model.getValueAt(column, row, page);
//		if(column == 0){
//			component.setAttribute("src", item.getImg());
//		}else if(column == 1){
//			component.getChildByIndex(0).setText(item.getProduct().getTitle());
//		}else if(column == 2){
//			component.setText(StringUtil.toCurrency(item.getProduct().getTotalPrice()));
//		}else if(column == 3){
//			((EXInput)component).setValue(item.getQty().toPlainString());
//			
//		}else{
//			component.setText( StringUtil.toCurrency(item.getProduct().getTotalPrice().multiply(item.getQty())));
//		}
		
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		String path = container.getAttribute("path");
		
		EXMiniCart cart =null;
		if(container.getAncestorOfType(EXEcommerce.class) != null)
			cart = container.getAncestorOfType(EXEcommerce.class).getDescendentOfType(EXMiniCart.class);
		else{
			cart = (EXMiniCart)container.getRoot().getDescendentById(container.getAncestorOfType(EXCartDetail.class).getAttribute("cartid"));
		}
		
		if(container instanceof EXInput){
			try{
			int value = Integer.parseInt(((EXInput)container).getValue().toString());
			cart.updateQty(path, new BigDecimal(value));
			}catch(Exception e){
				container.addClass("ui-state-error");
			}
		}else if(container.getName().equals("del")){
			cart.removeItem(path);
			cart.update();
		}else if(container.getName().equals("options")){
			
			if("open".equals(container.getAttribute("opened"))){
				container.getParent().getDescendentByName("pio").setDisplay(false);
				container.setAttribute("opened", "close");
				return true;
			}
			CartItem item = cart.getItem(path);
			String options  =item.getOptions();
			try{
			Container c  =DesignableUtil.buildContainer(new ByteArrayInputStream(options.getBytes()), false);
			List<StatefullComponent> fields = c.getDescendentOfType(EXDynaformPanel.class).getFields();
			if(fields.size() > 0){
				String s = "<br><label>";
				for(StatefullComponent stf : fields){
					String value = stf.getValue().toString();
					String label = stf.getAncestorByName("tr").getChildByIndex(0).getText();
					s = s + label + ":" + value + "</label><br></br><label>";
					
				}
				Container pio = container.getParent().getDescendentByName("pio");
				if(pio == null){
					pio = new EXContainer("pio", "p").setStyle("clear", "both");
					container.getParent().addChild(pio);
					
				}
				pio.setText(s).setDisplay(true);
				container.setAttribute("opened", "open");
				//container.getParent().addChild(new EXContainer("", "p").setText(s));
				return true;
			}
			//container.getAncestorOfType(PopupContainer.class).addPopup(c);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		container.getAncestorOfType(EXTable.class).setModel(new CartDetailModel(cart));
		container.getAncestorOfType(EXCartDetail.class).update(cart);
		return true;
		
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
