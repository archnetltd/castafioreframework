package org.castafiore.shoppingmall.crm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.button.EXIconButton;
import org.castafiore.ui.ex.form.button.Icons;
import org.castafiore.ui.ex.form.table.CellRenderer;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableColumnModel;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.ui.ex.layout.EXBorderLayoutContainer;
import org.castafiore.ui.js.JMap;
import org.castafiore.ui.js.Var;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.types.Directory;

public class SubscribersCategoryTableModel implements TableModel , CellRenderer, TableColumnModel, Event{
	
	private List<Directory> categories = new ArrayList<Directory>();
	
	public SubscribersCategoryTableModel(){
		categories = MallUtil.getCurrentMerchant().getSubscriptionCategories().toList();
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
		return "Categories";
	}

	@Override
	public int getRowCount() {
		return categories.size() ;
	}

	@Override
	public int getRowsPerPage() {
		return 10;
	}

	@Override
	public Object getValueAt(int col, int row, int page) {
		
			int index = ((getRowsPerPage()*page) + row );
			return categories.get(index);
		
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Container getComponentAt(int row, int column, int page,
			TableModel model, EXTable table) {
		Container span  = new EXSubscriptionCategory("");
		span.setStyle("cursor", "pointer");
		span.addEvent(new Event() {
			
			@Override
			public void Success(ClientProxy container, Map<String, String> request)
					throws UIException {
				
				
			}
			
			@Override
			public boolean ServerAction(Container container, Map<String, String> request)
					throws UIException {
				String cate = container.getAttribute("category");
				EXTable table = (EXTable)container.getAncestorOfType(EXBorderLayoutContainer.class).getDescendentOfType(EXTable.class, EXBorderLayoutContainer.CENTER);
				table.setModel(new SubscribersTableModel(cate));
				container.getParent().setStyle("background-color", "steelblue");
				container.getParent().setStyle("color", "white");
				container.setAttribute("sel", "true");
				for(Container c : container.getParent().getParent().getParent().getChildren()){
					if(!c.getId().equals(container.getParent().getParent().getId())){
						c.getChildByIndex(0).setStyle("background-color", "transparent").setStyle("color", "black");
						c.getChildByIndex(0).getChildByIndex(0).setAttribute("sel", "false");
					}
				}
				
				return true;
			}
			
			@Override
			public void ClientAction(ClientProxy container) {
				container.makeServerRequest(this);
				
			}
		}, Event.CLICK);
		Object o = model.getValueAt(column, row, page);
		
		span.setText(((Directory) o).getName()).setAttribute("category", ((Directory)o).getName());
		
		return span.addClass("cate");
		
	}

	@Override
	public void onChangePage(Container span, int row, int column,
			int page, TableModel model, EXTable table) {
		
		//
		Object o = model.getValueAt(column, row, page);
		
		span.setText(((Directory) o).getName()).setAttribute("category", ((Directory)o).getName());
		
		
	}

	@Override
	public EXContainer getColumnAt(int index, EXTable table, TableModel model) {
		EXContainer column = new EXContainer("" + index, "th");
		column.addClass("ui-widget-header");
		column.setText(model.getColumnNameAt(index));
		
		EXIconButton addNew = new EXIconButton("add", Icons.ICON_PLUS);
		column.addChild(addNew.setStyle("float", "right").setStyle("margin", "0 5px").setStyle("height", "5px").setStyle("padding", "5px").addEvent(this, Event.CLICK));
		return column;
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.appendJSFragment("var reply = prompt(\"Please enter a name for the category\", \"\");");
		container.makeServerRequest(new JMap().put("namerrrrrrrrrrr", new Var("reply")), this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		String name = request.get("namerrrrrrrrrrr");
		if(StringUtil.isNotEmpty(name)){
			Merchant merchant = MallUtil.getCurrentMerchant();
			merchant.addCategory(name);
			container.getAncestorOfType(EXTable.class).setModel(new SubscribersCategoryTableModel());
		}else{
			request.put("err", "Please enter a correct category");
		}
		//System.out.println(request);
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		if(request.containsKey("err")){
			container.alert(request.get("err"));
		}
		
	}

}
