package org.castafiore.shoppingmall.pos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.castafiore.catalogue.Product;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.dynaform.EXFieldSet;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.button.EXIconButton;
import org.castafiore.ui.ex.form.button.Icons;
import org.castafiore.ui.ex.form.table.CellRenderer;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.ui.ex.layout.EXBorderLayoutContainer;

public class EXSearchProductTable extends EXBorderLayoutContainer implements TableModel, CellRenderer, Event{
	
	private String[] labels = new String[]{"Code", "Title", "Price", "Select"};
	
	private List<Product> items = new ArrayList<Product>();
	
	private String merchant;

	public EXSearchProductTable(String name, String merchant) {
		super(name);
		this.merchant = merchant;
		for(int i =0; i <5;i++){
			getContainer(i).setStyle("padding", "0").setStyle("margin", "0").setStyle("vertical-align", "top");
		}
		EXFieldSet search = new EXFieldSet("searchfs", "Search Products", false);
		search.addField("Search : ", new EXInput("search"));
		addChild(search, TOP);
		search.getDescendentByName("search").addEvent(this, BLUR);
		
		EXTable list = new EXTable("list", this,this);
		addChild(list,CENTER);
		
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public int getColumnCount() {

		return labels.length;
	}

	@Override
	public String getColumnNameAt(int index) {
		return labels[index];
	}

	@Override
	public int getRowCount() {
		return items.size();
	}

	@Override
	public int getRowsPerPage() {
		return 10;
	}

	@Override
	public Object getValueAt(int col, int row, int page) {
		int rrow = (page*getRowsPerPage()) + row;
		return items.get(rrow);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		
		return false;
	}

	@Override
	public Container getComponentAt(int row, int column, int page,
			TableModel model, EXTable table) {
		int rrow = (page*getRowsPerPage()) + row;
		Product item = (Product)model.getValueAt(column, row, page);
		if(column == 0){
			return new EXContainer("code", "span").setText(item.getCode());
		}else if(column == 1){
			return new EXContainer("title", "span").setText(item.getTitle());
		}else if(column == 2){
			return new EXContainer("price", "span").setText(item.getPriceExcludingTax().toPlainString());
		}else{
			return new EXIconButton("select", Icons.ICON_CIRCLE_PLUS).addEvent(this, CLICK).setAttribute("index", rrow + "");
		}
	}

	@Override
	public void onChangePage(Container component, int row, int column,
			int page, TableModel model, EXTable table) {
		
		Product item = (Product)model.getValueAt(column, row, page);
		if(column == 0){
			component.setText(item.getCode());
		}else if(column == 1){
			component.setText(item.getTitle());
		}else if(column == 2){
			component.setText(item.getPriceExcludingTax().toPlainString());
		}else{
			component.setAttribute("index", row + "");
		}
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		if(container.getName().equalsIgnoreCase("select")){
			int index = Integer.parseInt(container.getAttribute("index"));
			
			Product p = items.get(index);
			
			
			container.getAncestorOfType(EXPointOfSales.class).addProduct(p);
		}else if(container.getName().equalsIgnoreCase("search")){
			String term = ((StatefullComponent)container).getValue().toString();
			List<Product> products = MallUtil.getCurrentMall().searchProducts("full:" +term, 0, getRowsPerPage());
			setAttribute("term", term);
			this.items = products;
			getDescendentOfType(EXTable.class).refresh();
		}
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

	
	
}
