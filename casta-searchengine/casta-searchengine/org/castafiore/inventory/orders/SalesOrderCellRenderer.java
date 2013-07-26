package org.castafiore.inventory.orders;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import org.castafiore.shoppingmall.checkout.Order;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.table.CellRenderer;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableModel;

public class SalesOrderCellRenderer implements CellRenderer{

	@Override
	public Container getComponentAt(int row, int column, int page,
			TableModel model, EXTable table) {
		Order o = (Order)model.getValueAt(column, row, page);
		
		Container result = new EXContainer("", "span");
		if(column == 0){
			result.setText(o.getCode());
		}else if(column == 1){
			result.setText(new SimpleDateFormat("dd/MM/yyyy").format(o.getDateOfTransaction()));
		}else if(column == 2){
			result.setText(o.getTotal().multiply(new BigDecimal("0.85")).toString());
		}else if(column == 3){
			result.setText("15%");
		}else if(column == 4){
			result.setText(o.getTotal().toString());
		}
		return result;
	}

	@Override
	public void onChangePage(Container result, int row, int column,
			int page, TableModel model, EXTable table) {
		Order o = (Order)model.getValueAt(column, row, page);
		
		
		if(column == 0){
			result.setText(o.getCode());
		}else if(column == 1){
			result.setText(new SimpleDateFormat("dd/MM/yyyy").format(o.getDateOfTransaction()));
		}else if(column == 2){
			result.setText(o.getTotal().multiply(new BigDecimal("0.85")).toString());
		}else if(column == 3){
			result.setText("15%");
		}else if(column == 4){
			result.setText(o.getTotal().toString());
		}
		
		
	}

}
