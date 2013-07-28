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
 package org.castafiore.catalogue.ui;


import java.util.List;
import java.util.Map;

import org.castafiore.catalogue.Product;
import org.castafiore.designer.designable.table.JSONTableModel;
import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.button.EXIconButton;
import org.castafiore.ui.ex.form.button.Icons;
import org.castafiore.ui.ex.form.table.Table;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.ui.ex.toolbar.EXToolBar;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.EventUtil;
import org.castafiore.utils.ResourceUtil;

public class EXCatalogue extends EXXHTMLFragment implements Table {
	
	private int currentPage = 0;
	//private int pageSize = 3;
	
	private List<Product> products = null;
	
	private TableModel model;

	public EXCatalogue(String name, List<Product> products) {
		super(name, ResourceUtil.getDownloadURL("classpath", "org/castafiore/catalogue/resources/EXCatalogue.xhtml"));
		addClass("EXCatalogue");
		setStyle("min-height", "200px");
		setAttribute("pagesize", "3");
		Container uiProducts = new EXContainer("products", "div");
		uiProducts.setStyle("overflow", "hidden");
		addChild(uiProducts);
		setProducts(products);
		addChild(new EXContainer("title", "span").setText("Featured products"));
		
		EXToolBar tb = new EXToolBar("buttons");
		
		tb.setStyleClass("");
		tb.addItem(new EXIconButton("next", Icons.ICON_ARROW_1_E));
		tb.addItem(new EXIconButton("previous", Icons.ICON_ARROW_1_W));
		tb.addClass("EXToolBar");
		
		addChild(tb);
		getDescendentByName("previous").setAttribute("ancestor", getClass().getName());
		getDescendentByName("previous").setAttribute("method", "previousPage");
		getDescendentByName("previous").addEvent(EventUtil.GENERIC_FORM_METHOD_EVENT, Event.CLICK).setStyle("float", "right");
		getDescendentByName("next").setAttribute("ancestor", getClass().getName()).setStyle("float", "right");
		getDescendentByName("next").setAttribute("method", "nextPage");
		getDescendentByName("next").addEvent(EventUtil.GENERIC_FORM_METHOD_EVENT, Event.CLICK);
		
	}
	
	public void setTitle(String title){
		getChild("title").setText(title);
	}
	
	
	public void showDetail(Product p){
		EXPanel panel = new EXPanel("pd", "Product detail");
		EXProductDetail pd = new EXProductDetail("detail");
		pd.setStyle("width", "780px");
		//pd.setStyle("height", "420px");
		pd.setStyle("overflow-x", "hidden");
		pd.setProduct(p);
		panel.setBody(pd);
		addChild(panel);
		panel.setWidth(Dimension.parse("803px"));
		//panel.setHeight(Dimension.parse("470px"));
	}
	
	public void setProducts(List<Product> products){
		this.products = products;
		refreshContents();
	}
	
	public int getPageSize(){
		return Integer.parseInt(getAttribute("pagesize"));
	}
	public int getMaxPages(){
		int pageSize = getPageSize();
		int size = products.size();
		int maxPages = 0;
		if(size <= pageSize){
			maxPages = 1;
		}else{
			maxPages = ((size - (size%pageSize))/pageSize) + 1;
		}
		
		return maxPages;
	}
	
	public void nextPage(){
		int maxPages = getMaxPages();
		if(currentPage == maxPages-1){
			//reset
			currentPage = 0;
		}else{
			currentPage++;
		}
		refreshContents();
	}
	
	public void previousPage(){
		if (currentPage == 0){
			currentPage = getMaxPages()-1;
		}else{
			currentPage--;
		}
		refreshContents();
	}
	
	public void refreshContents(){
		int pageSize = getPageSize();
		Container prods = getChild("products");
		prods.getChildren().clear();
		prods.setRendered(false);
		for(int i =0; i < pageSize; i ++){
			int index = (currentPage*pageSize) + i;
			if(index < products.size()){
				EXProduct  pro = new EXProduct(products.get(index));
				pro.setStyle("float", "left");
				prods.addChild(pro);
			}
		}
	}
	
	public final static Event SHOW_DETAIL = new Event(){

		public void ClientAction(ClientProxy container) {
			container.mask();
			container.makeServerRequest(this);
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			Product p = container.getAncestorOfType(EXProduct.class).getProduct();
			container.getAncestorOfType(EXCatalogue.class).showDetail(p);
			return true;
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			// TODO Auto-generated method stub
			
		}
		
	};

	public TableModel getModel() {
		return model;
	}


	public void setModel(TableModel model) {
		this.model = model;
		setAttribute("pagesize", "3");
		JSONTableModel jModel =  (JSONTableModel)model;
		setProducts(jModel.getData());
		
		
	}

	@Override
	public void changePage(int page) {
		this.currentPage = page;
		refreshContents();
		
	}

	@Override
	public int getCurrentPage() {
		return currentPage;
	}

	public int getPages()
	{
		int rows = model.getRowCount();

		int rPerPage = model.getRowsPerPage();

		int remainder = rows % rPerPage;

		int multiple = Math.round(rows / rPerPage);

		int pages = multiple;
 
		if (remainder != 0)
		{
			pages = pages + 1;
		}

		return pages;

	}

}
