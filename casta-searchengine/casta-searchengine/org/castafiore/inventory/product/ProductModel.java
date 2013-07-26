package org.castafiore.inventory.product;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.catalogue.Product;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.ui.ex.form.table.TableModel;

public class ProductModel implements TableModel{
	
	private int currentPage = -1;
	
	private int rowCount = -1;
	
	private List<Product> list = new ArrayList<Product>(10);
	
	private String[] columns = new String[]{"", "Code", "Title","Qty" ,"Price"};

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public String getColumnNameAt(int index) {
		return columns[index];
	}

	@Override
	public int getRowCount() {
		if(rowCount < 0){
			rowCount = MallUtil.getCurrentUser().getMerchant().countMyProduct(Product.STATE_DRAFT);
		}
		return rowCount;
	}

	@Override
	public int getRowsPerPage() {
		return 10;
	}
	
	public void fillPage(int page){
		if(currentPage != page){
			currentPage = page;
			List<Product> products = MallUtil.getCurrentUser().getMerchant().getMyProducts(Product.STATE_DRAFT, page*getRowsPerPage(), getRowsPerPage());
			list.clear();
			list.addAll(products);
		}
	}

	@Override
	public Object getValueAt(int col, int row, int page) {
		fillPage(page);
		return list.get(row);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

}
