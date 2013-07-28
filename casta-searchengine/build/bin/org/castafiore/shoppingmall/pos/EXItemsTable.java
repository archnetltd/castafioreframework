package org.castafiore.shoppingmall.pos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.castafiore.catalogue.Product;
import org.castafiore.designable.CartItem;
import org.castafiore.designable.EXMiniCart;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.button.EXIconButton;
import org.castafiore.ui.ex.form.button.Icons;
import org.castafiore.ui.ex.form.table.CellRenderer;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableModel;

public class EXItemsTable extends EXTable implements TableModel, CellRenderer, Event{

	private String[] labels = new String[]{"Code", "Title","Price","Quantity", "Total", "Action"};
	
	//private List<CartItem> items = new ArrayList<CartItem>();
	
	public EXItemsTable(String name) {
		super(name,null);
		setCellRenderer(this);
		addClass("ui-widget-content");
	}
	
	public void addItem(Product p){
		refresh();
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public int getColumnCount() {
		if(getAncestorOfType(EXMiniCart.class).getItems().size() ==0)
			return 1;
		
		return labels.length;
	}

	@Override
	public String getColumnNameAt(int index) {
		if(getAncestorOfType(EXMiniCart.class).getItems().size() ==0)
			return "Selected products";
		return labels[index];
	}

	@Override
	public int getRowCount() {
		if(getAncestorOfType(EXMiniCart.class).getItems().size() ==0)
			return 1;
		return getAncestorOfType(EXMiniCart.class).getItems().size();
	}

	@Override
	public int getRowsPerPage() {
		return 100;
	}

	@Override
	public Object getValueAt(int col, int row, int page) {
		if(getAncestorOfType(EXMiniCart.class).getItems().size() ==0)
			return new EXContainer("", "p").setText("Zero items selected for this order. Search a product from the right panel and select it to add it in the list here").setStyle("text-align", "center");
		return getAncestorOfType(EXMiniCart.class).getItems().get(row);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public Container getComponentAt(int row, int column, int page,
			TableModel model, EXTable table) {
		Object o = model.getValueAt(column, row, page);
		
		if(o instanceof Container){
			return (Container)o;
		}
		
		CartItem item = (CartItem)o;
		if(column == 0){
			return new EXContainer("code", "span").setText(item.getCode());
		}else if(column == 1){
			return new EXContainer("title", "span").setText(item.getTitle());
		}else if(column == 2){
			return new EXContainer("price", "span").setText(item.getPriceExcludingTax().toPlainString());
		}else if(column == 3){
			
			return new EXInput("qty", item.getQty().toPlainString()).addEvent(this, BLUR).setStyle("width", "30px").setAttribute("index", row + "");
		}else if(column == 4){
			return new EXContainer("total", "span").setText(item.getTotalPrice().toPlainString());
		}else{
			return new EXIconButton("delete", Icons.ICON_CIRCLE_PLUS).addEvent(this, CLICK).setAttribute("index", row + "");
		}
	}

	@Override
	public void onChangePage(Container component, int row, int column,
			int page, TableModel model, EXTable table) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		
		
		int index = Integer.parseInt(container.getAttribute("index"));
		CartItem item = getAncestorOfType(EXMiniCart.class).getItems().get(index);
		if(container.getName().equalsIgnoreCase("qty")){
			
			BigDecimal qty =new BigDecimal( container.getDescendentOfType(StatefullComponent.class).getValue().toString());
			getAncestorOfType(EXMiniCart.class).updateQty(item.getAbsolutePath(), qty);
			
		}
		else{
			getAncestorOfType(EXMiniCart.class).removeItem(item.getAbsolutePath());
			
		}
		//this.refresh();
		//getAncestorOfType(EXMiniCart.class).update();
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
