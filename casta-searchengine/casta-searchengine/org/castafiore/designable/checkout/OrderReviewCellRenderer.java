package org.castafiore.designable.checkout;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import org.castafiore.designable.CartItem;
import org.castafiore.designer.marshalling.DesignableUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.table.CellRenderer;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ComponentVisitor;
import org.castafiore.utils.StringUtil;

public class OrderReviewCellRenderer implements CellRenderer{

	private String getTitleWithOptions(CartItem item){
		try{
			final StringBuilder b = new StringBuilder(item.getTitle() + "<br></br>");
			if(item.getOptions() != null){
				Container c = DesignableUtil.buildContainer(new ByteArrayInputStream(item.getOptions().getBytes()), false);
			
				final Map<String, String> mptions = new HashMap<String, String>();
			
			
				ComponentUtil.iterateOverDescendentsOfType(c, StatefullComponent.class, new ComponentVisitor() {
					
					@Override
					public void doVisit(Container c) {
						StatefullComponent stf = (StatefullComponent)c;
						mptions.put(c.getName(), stf.getValue().toString());
						b.append(c.getName() + ":" + stf.getValue().toString() ).append("<br></br>");
					}
				});
			}
			return b.toString();
		}catch(Exception e){
			e.printStackTrace();
			return item.getTitle();
		}
		
	}
	
	
	@Override
	public Container getComponentAt(int row, int column, int page,
			TableModel model, EXTable table) {
		CartItem item = (CartItem)model.getValueAt(column, row, page);
		if(column == 0){
			return new EXContainer("", "span").setText(item.getCode());
		}else if(column == 1){
			
			String s = getTitleWithOptions(item);
			return new EXContainer("title", "span").setText(s);
			
			
			
		}else if(column == 2){
			return new EXContainer("price", "span").setText(StringUtil.toCurrency(item.getCurrency(),item.getTotalPrice()));
		}else if(column == 3){
			return new EXContainer("price", "span").setText(item.getQty().toPlainString());
		}else{
			return new EXContainer("subTotal", "span").setText( StringUtil.toCurrency(item.getCurrency(),item.getTotalPrice().multiply(item.getQty())));
		}
	}

	@Override
	public void onChangePage(Container component, int row, int column,
			int page, TableModel model, EXTable table) {
		
		CartItem item = (CartItem)model.getValueAt(column, row, page);
		if(column == 0){
			component.setText(item.getCode());
		}else if(column == 1){
			component.setText(getTitleWithOptions(item));
		}else if(column == 2){
			component.setText(StringUtil.toCurrency(item.getCurrency(),item.getTotalPrice()));
		}else if(column == 3){
			component.setText(item.getQty().toPlainString());
		}else{
			component.setText( StringUtil.toCurrency(item.getCurrency(),item.getTotalPrice().multiply(item.getQty())));
		}
	}

}
