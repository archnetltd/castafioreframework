package org.castafiore.designable;

import org.castafiore.ui.ex.form.table.TableModel;

public class CartDetailModel implements TableModel {
	
	private EXMiniCart cart; 

	private String[] labels = new String[]{"Item", "Title", "Unit price", "Qty", "Subtotal"};
	
	
	
	public CartDetailModel(EXMiniCart cart) {
		super();
		this.cart = cart;
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
		return cart.getItems().size();
	}

	@Override
	public int getRowsPerPage() {
		return getRowCount();
	}

	@Override
	public Object getValueAt(int col, int row, int page) {
		return cart.getItems().get(row);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return false;
	}

}
