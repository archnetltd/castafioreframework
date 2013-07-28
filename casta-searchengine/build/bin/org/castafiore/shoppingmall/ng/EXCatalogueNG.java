package org.castafiore.shoppingmall.ng;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.castafiore.catalogue.Product;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.Catalogue;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;

import org.castafiore.ui.ex.form.table.Table;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.ui.js.JMap;
import org.castafiore.ui.js.Var;

import edu.emory.mathcs.backport.java.util.Collections;

public class EXCatalogueNG extends EXContainer implements Comparator<EXProductItemNG>, Catalogue, Table, TableModel{
	
	private List<String> notations = new ArrayList<String>();
	protected int curpage = 0;
	
	protected String skin;
	
	protected List<Product> data;
	public EXCatalogueNG(String name) {
		super(name, "div");
		addChild(new EXProductResultBarNG("resultBar"));
		addChild(new EXContainer("pagincont", "div").addClass("emallcatalogue"));
		createPaginator(this);
		addChild(new EXContainer("grid", "div"));
		// TODO Auto-generated constructor stub
	}
	
	private void createPaginator(Table table){
		final int pages = table.getPages();
		getChild("pagincont").getChildren().clear();
		getChild("pagincont").setRendered(false);
		if(pages > 1){
			Container pagin = new EXContainer("pagin", "div").addClass("EXPageIterator");
			pagin.addChild(new EXContainer("mleft", "a").addClass("Icon LastTopPageIcon"));
			pagin.addChild(new EXContainer("oleft", "a").addClass("Icon LastPageIcon"));
			pagin.addChild(new EXContainer("p_wrap", "div").addClass("paginator_p_wrap").setStyle("overflow", "visible").addClass("PageIteratorContainer").addChild(new EXContainer("paginator_p_bloc", "div").setStyle("width", "735px").addClass("ui-state-highlight").setStyle("text-align", "center").setStyle("padding", "3px 0").setStyle("overflow", "visible").addClass("paginator_p_bloc")));
			
			pagin.addChild(new EXContainer("oright", "a").addClass("Icon NextPageIcon"));
			pagin.addChild(new EXContainer("mright", "a").addClass("Icon NextTopPageIcon"));
			getChild("pagincont").addChild(pagin);
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
	
	
	public void setProducts(List<Product> products, String skin){
//		Container grid = getChild("grid");
//		grid.getChildren().clear();
//		grid.setRendered(false);
//		for(Product p : products){
//			grid.addChild(new EXProductItemNG(skin).setProduct(p).setStyle("float", "left").setStyle("width", "324px"));
//		}
//		getDescendentOfType(EXProductResultBarNG.class).setSkin(skin);
		
		this.data = products;
		this.skin = skin;
		setModel(this);
	
	}
	
	public void back(){
		if(notations.size() > 1){
			notations.remove(notations.size()-1);
			String notation =notations.remove(notations.size()-1);
			//String notation = notations.remove(notations.size()-2);
			search(notation,getAttribute("skin"));
		}
	}
	
	public void changePage(){
		search(notations.get(notations.size()-1),getAttribute("skin"));
	}
	public void sort(String by){
		setAttribute("sortBy", by);
		List items = getChild("grid").getChildren();
		
		Collections.sort(items, this);
		
		List items_ = new ArrayList();
		
		for(Object o : items){
			//getChild("grid").addChild(((Container)o).setRendered(false));
			items_.add(o);
		}
		getChild("grid").getChildren().clear();
		getChild("grid").setRendered(false);
		for(Object o : items_){
			getChild("grid").addChild(((Container)o).setRendered(false));
			//items_.add(o);
		}
	}
	protected void _search(String notation, String skin){
		if(notation.equals("recent")){
			setProducts(MallUtil.getCurrentMall().getRecentProducts(getDescendentOfType(EXProductResultBarNG.class).getPageSize()), skin);
		}else{
			setProducts(MallUtil.getCurrentMall().searchProducts(notation, 0,getDescendentOfType(EXProductResultBarNG.class).getPageSize() ), skin);
		}
	}
	public void search(String notation, String skin){
		if(skin == null){
			skin = "Blue";
		}
		notations.add(notation);
		_search(notation, skin);
		setAttribute("skin", skin);
	}


	@Override
	public int compare(EXProductItemNG o1, EXProductItemNG o2) {
		String sortBy = getAttribute("sortBy");
		if(sortBy.equals("title")){
			return o1.getTitle().compareTo(o2.getTitle());
		}else if(sortBy.equals("price")){
			return o1.getPrice().compareTo(o2.getPrice());
		}else if(sortBy.equals("date")){
			return o1.getDateCreate().compareTo(o2.getDateCreate());
		}
		return 0;
	}

	@Override
	public void changePage(int page) {
		this.curpage = page;
		int  start = page*getRowsPerPage();
		int end = start + getRowsPerPage();
		if(data.size() < end){
			end = data.size()-1;
		}
		Container grid = getChild("grid");
		grid.getChildren().clear();
		grid.setRendered(false);
		for(int i = start; i < end;i++){
			grid.addChild(new EXProductItemNG("").setProduct(data.get(i)).setStyle("float", "left").setStyle("width", "324px"));
		}
		
	}

	@Override
	public int getCurrentPage() {
		return curpage;
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
		
		return 1;
	}

	@Override
	public String getColumnNameAt(int index) {
		return "";
	}

	@Override
	public int getRowCount() {
		if(data == null)
			return 0;
		return data.size();
	}

	@Override
	public int getRowsPerPage() {
		
		return getDescendentOfType(EXProductResultBarNG.class).getPageSize();
	}

	@Override
	public Object getValueAt(int col, int row, int page) {
		return this.data.get((page*getRowsPerPage()) + row);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		
		return false;
	}

}
