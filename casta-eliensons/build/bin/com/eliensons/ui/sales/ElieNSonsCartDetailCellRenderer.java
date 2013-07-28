package com.eliensons.ui.sales;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Iterator;
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

public class ElieNSonsCartDetailCellRenderer implements CellRenderer{
	
	private String getTitleWithOptions(CartItem item){
		try{
			final StringBuilder b = new StringBuilder(item.getTitle() + "<br></br>");
			
			
			
			if(item.getOptions() != null){
				Map<String,String> opts = item.getOptionMap();
				Container c = DesignableUtil.buildContainer(new ByteArrayInputStream(item.getOptions().getBytes()), false);
			
				//final Map<String, String> mptions = new HashMap<String, String>();
				Iterator<String> ite = opts.keySet().iterator();
				while(ite.hasNext()){
					String key = ite.next();
					String val = opts.get(key);
					b.append(key + ":" + val ).append("<br></br>");
				}
			}
			
			b.append("<br></br>").append(item.getSummary()).append("<br></br>").append(item.getDetail());
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
		}else{
			component.setText( StringUtil.toCurrency(item.getCurrency(),item.getTotalPrice().multiply(item.getQty())));
		}
	}

}
