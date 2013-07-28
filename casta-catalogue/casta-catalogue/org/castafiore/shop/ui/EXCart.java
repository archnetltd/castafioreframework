/*
 * Copyright (C) 2007-2010 Castafiore
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */
 package org.castafiore.shop.ui;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.castafiore.catalogue.Product;
import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.EventUtil;
import org.castafiore.utils.ResourceUtil;

public class EXCart extends EXXHTMLFragment implements TableModel {
	
	private List<Product> products = new ArrayList<Product>();
	
	private Map<String, Integer> codeAmount = new HashMap<String, Integer>();
	
	private final static String[] COL_LABELS = new String[]{"CODE", "Name", "Price", "Amount", "Total"};

	
	public EXCart(String name) {
		super(name, ResourceUtil.getDownloadURL("classpath", "org/castafiore/catalogue/resources/EXCart.xhtml"));
		
		
		Container items = ComponentUtil.getContainer("items", "span", "Shopping cart (0 item)", null);
		addChild(items);
		EXPanel panel = new EXPanel("cartGrid", "My Cart");
		panel.setDisplay(false);
		EXTable table = new EXTable("cart", this);
		table.setWidth(Dimension.parse("100%"));
		panel.setWidth(Dimension.parse("746px"));
		panel.setStyle("position", "fixed");
		panel.setBody(table);
		
		addChild(panel);
		panel.setStyle("top", "300px");
		panel.setStyle("left", "275px");
		panel.setCloseButtonEvent(EXPanel.HIDE_EVENT);
		
		addEvent(EventUtil.GENERIC_FORM_METHOD_EVENT, Event.CLICK);
		setAttribute("ancestor", getClass().getName());
		setAttribute("method", "showDetail");
		
	}
	
	public void showDetail(){
		getChild("cartGrid").setDisplay(true);
	}
	
	
	public void addProduct(Product p){
		
		int amount = 1;
		if(codeAmount.containsKey(p.getCode())){
			amount = amount + codeAmount.get(p.getCode());
		}else{
			products.add(p);
			if(products.size() == 1){
				getChild("items").setText("Shopping cart (1 item)");
			}else{
				getChild("items").setText("Shopping cart ("+products.size()+" items)");
			}
		}
		
		codeAmount.put(p.getCode(), amount);
		getDescendentOfType(EXTable.class).refresh();
	}


	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}


	public int getColumnCount() {
		return COL_LABELS.length;
	}


	public String getColumnNameAt(int index) {
		return COL_LABELS[index];
	}


	public int getRowCount() {
		return products.size();
	}


	public int getRowsPerPage() {
		return 8;
	}


	public Object getValueAt(int col, int row, int page) {
		int index = (getRowsPerPage()*page) + row;
		Product p = products.get(index);
		if(col == 0){
			return p.getCode();
		}else if(col == 1){
			return p.getTitle();
		}else if(col == 2 ){
			return "Rs " + p.getTotalPrice();
		}else if(col == 3){
			return codeAmount.get(p.getCode());
		}else{
			return "Rs " + (p.getTotalPrice().multiply(new BigDecimal( codeAmount.get(p.getCode()))));
		}
	}


	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return false;
	}

}
