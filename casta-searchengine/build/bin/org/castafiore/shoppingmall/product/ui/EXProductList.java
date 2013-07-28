package org.castafiore.shoppingmall.product.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.castafiore.catalogue.Product;
import org.castafiore.searchengine.EXSearchEngineApplication;
import org.castafiore.shoppingmall.ng.EXProductItemNG;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXAutoComplete;
import org.castafiore.ui.ex.form.EXCheckBox;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.table.Table;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.ui.js.JMap;
import org.castafiore.ui.js.Var;
import org.castafiore.utils.ResourceUtil;


public class EXProductList extends EXContainer implements Event , Table, TableModel{
	
	protected List<Product> completeList;

	public EXProductList(String name) {
		super(name, "table");
		addClass("ex-content");
		addStyleSheet(ResourceUtil.getDownloadURL("classpath", "org/castafiore/resource/table/table.css"));
		EXContainer head = new EXContainer("", "thead");
		addChild(head);
		EXContainer tr = new EXContainer("", "tr");
		head.addChild(tr);
//		if(getRoot() instanceof EXSearchEngineApplication)
//			tr.addChild(new EXContainer("", "th").setStyle("width", "15px").addChild(new EXCheckBox("selectAll")));
		tr.addChild(new EXContainer("code", "th").setStyle("width", "100px").setText("Code").addEvent(this, Event.CLICK));
		tr.addChild(new EXContainer("title", "th").setText("Title").addEvent(this, CLICK));
		tr.addChild(new EXContainer("qty", "th").setStyle("width", "100px").setStyle("text-align", "right").setText("Qty"));
		tr.addChild(new EXContainer("price", "th").setStyle("width", "100px").setStyle("text-align", "right").setText("Price"));
		EXContainer body = new EXContainer("body", "tbody");
		addChild(body);
		
		Container footer = new EXContainer("footer", "tfoot");
		footer.addChild(new EXContainer("TR", "tr").addChild(new EXContainer("pagincontrt", "td").setAttribute("colspan", "10").addChild(new EXContainer("pagincont", "div").setStyle("width", "600px"))));
		addChild(footer);
	}
	
	
	public void setProducts(List<Product> products){
		completeList = products;
		setModel(this);
	}
	
	
	public List<String> getDictionary(String type){
		List<String> result = new ArrayList<String>(completeList.size());
		
		for(Product p : completeList){
			if(type.equals("code")){
				result.add(p.getCode());
			}else{
				result.add(p.getTitle());
			}
		}
		
		Collections.sort(result);
		return result;
		
	}
	
	
	public void filterCode(String code){
		List<Product> subList = new ArrayList<Product>();
		for(Product p : completeList){
			if( p.getCode().toLowerCase().contains(code.toLowerCase())){
				subList.add(p);
			}
		}
		

		getChild("body").getChildren().clear();
		getChild("body").setRendered(false);
		
		for(Product product : subList){
			EXProductListItem item = new EXProductListItem("");
			item.setItem(product);
			int size = getChild("body").getChildren().size();
			if((size % 2) == 0){
				item.addClass("even");
			}else{
				item.addClass("odd");
			}
			getChild("body").addChild(item);
		}
	}
	
	public void filterTitle(String title){
		List<Product> subList = new ArrayList<Product>();
		for(Product p : completeList){
			if( p.getTitle().toLowerCase().contains(title.toLowerCase())){
				subList.add(p);
			}
		}
		

		getChild("body").getChildren().clear();
		getChild("body").setRendered(false);
		
		for(Product product : subList){
			EXProductListItem item = new EXProductListItem("");
			item.setItem(product);
			int size = getChild("body").getChildren().size();
			if((size % 2) == 0){
				item.addClass("even");
			}else{
				item.addClass("odd");
			}
			getChild("body").addChild(item);
		}
	}


	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}


	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		if(container instanceof EXInput){
			Container td = container.getParent();
			//td.getChildren().clear();
			container.setDisplay(false);
			td.addEvent(this, CLICK);
			td.setText(container.getAttribute("txt"));
			String val = ((EXInput)container).getValue().toString();
			if(container.getName().equals("code")){
				filterCode(val);
			}else {
				filterTitle(val);
			}
			
		}else{
			if(container.getChildren().size() == 0 || !container.getChildren().get(0).isVisible()){
				if(container.getChildren().size() == 0){
					
					container.addChild(new EXAutoComplete(container.getName(), "", getDictionary(container.getName())).setStyle("margin", "0").addEvent(this, BLUR).setAttribute("txt", container.getText()));
					container.setText("");
				}else{
					container.getChildren().get(0).setDisplay(true);
					container.setText("");
				}
				container.getEvents().clear();
				
				
			}else{
				container.getChildren().get(0).setDisplay(false);
				container.addEvent(this, Event.CLICK);
				
			}
			container.setRendered(false);
		}
		return true;
	}


	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		
		
	}


	private int currPage = 0;
	
	@Override
	public void changePage(int page) {
		this.currPage = page;
		
		int  start = page*getRowsPerPage();
		int end = start + getRowsPerPage();
		if(completeList.size() <= end){
			end = completeList.size();
		}
		
		getChild("body").getChildren().clear();
		getChild("body").setRendered(false);
		
		for(int i = start; i < end;i++){
			Product product = completeList.get(i);
			EXProductListItem item = new EXProductListItem("");
			item.setItem(product);
			int size = getChild("body").getChildren().size();
			if((size % 2) == 0){
				item.addClass("even");
			}else{
				item.addClass("odd");
			}
			getChild("body").addChild(item);
		}
		
	}


	@Override
	public int getCurrentPage() {
		return currPage;
	}


	@Override
	public TableModel getModel() {
		return this;
	}


	@Override
	public int getPages() {
		int total =getRowCount();
		int psize = getRowsPerPage();
		int diff = total % psize;
		if(total <= psize){
			return 1;
		}else{
			if(diff == 0){
				return total/psize;
			}else
				return (total/psize)+1;
		}
	}

	private void createPaginator(Table table){
		final int pages = table.getPages();
		getDescendentByName("pagincont").getChildren().clear();
		getDescendentByName("pagincont").setRendered(false);
		if(pages > 1){
			Container pagin = new EXContainer("pagin", "div").addClass("EXPageIterator");
			pagin.addChild(new EXContainer("mleft", "a").addClass("Icon LastTopPageIcon"));
			pagin.addChild(new EXContainer("oleft", "a").addClass("Icon LastPageIcon"));
			pagin.addChild(new EXContainer("p_wrap", "div").addClass("paginator_p_wrap").addClass("PageIteratorContainer").addChild(new EXContainer("paginator_p_bloc", "div").setStyle("text-align", "center").addClass("paginator_p_bloc")));
			
			pagin.addChild(new EXContainer("oright", "a").addClass("Icon NextPageIcon"));
			pagin.addChild(new EXContainer("mright", "a").addClass("Icon NextTopPageIcon"));
			getDescendentByName("pagincont").addChild(pagin);
			pagin.addEvent(new Event(){
	
				@Override
				public void ClientAction(ClientProxy container) {
					
					JMap options = new JMap();
					options.put("nbPages", pages-2);
					options.put("overBtnLeft", container.getDescendentByName("oleft").getIdRef());
					options.put("overBtnRight", container.getDescendentByName("oright").getIdRef());
					options.put("maxBtnLeft", container.getDescendentByName("mleft").getIdRef());
					options.put("maxBtnRight", container.getDescendentByName("mright").getIdRef());
					
					ClientProxy clickpage = container.clone();
					clickpage.makeServerRequest(new JMap().put("page", new Var("num")),this);
					
					options.put("onPageClicked", clickpage, "a", "num");
					
					
					ClientProxy callback = container.clone().addMethod("jPaginator", options);
					container.getScript("js1/jPaginator-min.js", callback);
					
				}
	
				@Override
				public boolean ServerAction(Container container,
						Map<String, String> request) throws UIException {
					int  page = Integer.parseInt(request.get("page"));
					
					container.getAncestorOfType(Table.class).changePage(page);
					return true;
				}
	
				@Override
				public void Success(ClientProxy container,
						Map<String, String> request) throws UIException {
					// TODO Auto-generated method stub
					
				}
				
			}, Event.READY);
		}
	}

	@Override
	public void setModel(TableModel model) {
		createPaginator(this);
		changePage(0);
		
	}


	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}


	@Override
	public int getColumnCount() {
		return 3;
	}


	@Override
	public String getColumnNameAt(int index) {
		return "";
	}


	@Override
	public int getRowCount() {
		return completeList.size();
	}


	@Override
	public int getRowsPerPage() {
		return 5;
	}


	@Override
	public Object getValueAt(int col, int row, int page) {
		return completeList.get((page*getRowsPerPage()) + row);
	}


	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return false;
	}

}
