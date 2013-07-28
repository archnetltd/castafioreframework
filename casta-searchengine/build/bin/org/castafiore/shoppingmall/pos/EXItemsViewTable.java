package org.castafiore.shoppingmall.pos;

import java.util.List;

import org.castafiore.shoppingmall.checkout.Order;
import org.castafiore.shoppingmall.checkout.SalesOrderEntry;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.table.CellRenderer;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableModel;

public class EXItemsViewTable extends EXTable implements TableModel, CellRenderer{
	
	private List<SalesOrderEntry> entries;
	
	private String[] labels = new String[]{"Code", "Title","Price","Quantity", "Total"};
	
	public EXItemsViewTable(String name, Order order) {
		super(name,null);
		setCellRenderer(this);
		addClass("ui-widget-content");	
		entries = order.getFiles(SalesOrderEntry.class).toList();
		setModel(this);
	}
	
	public void setPurchaseOrder(Order order){
		this.entries = order.getFiles(SalesOrderEntry.class).toList();
		refresh();
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
		return entries.size();
	}

	@Override
	public int getRowsPerPage() {
		return 100;
	}

	@Override
	public Object getValueAt(int col, int row, int page) {
		return entries.get(row);
		
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public Container getComponentAt(int row, int column, int page,
			TableModel model, EXTable table) {
		Object o = model.getValueAt(column, row, page);
		
		SalesOrderEntry item = (SalesOrderEntry)o;
		if(column == 0){
			return new EXContainer("code", "span").setText(item.getCode());
		}else if(column == 1){
			return new EXContainer("title", "span").setText(item.getTitle());
		}else if(column == 2){
			return new EXContainer("price", "span").setText( item.getPrice().toPlainString());
		}else if(column == 3){
			
			return new EXContainer("qty","span").setText(item.getQuantity().toPlainString());
		}else {
			return new EXContainer("total", "span").setText(item.getTotal().toPlainString());
		}
	}

	@Override
	public void onChangePage(Container component, int row, int column,
			int page, TableModel model, EXTable table) {
		// TODO Auto-generated method stub
		
	}
}
