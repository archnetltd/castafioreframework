package org.castafiore.easyui.grid;

import org.castafiore.easyui.buttons.LinkButton;
import org.castafiore.easyui.buttons.Menu;
import org.castafiore.easyui.buttons.MenuButton;
import org.castafiore.easyui.buttons.MenuItem;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXApplication;

public class ShowCase extends EXApplication{

	public ShowCase() {
		super("showcase");
		addGrid();
		
		//Container link = //new EXContainer("", "a").setAttribute("href", "#").setAttribute("data-options", "iconCls:'icon-search'").addClass("easyui-linkbutton");
		Container link = new LinkButton("sdfsd", "Cancel").setIconCls("icon-add");
		addChild(link);
		
		Menu menu = new Menu("");
		menu.addItem(new MenuItem("s", "First"));
		menu.addItem(new MenuItem("s", "Second"));
		menu.addItem(new MenuItem("s", "Third"));
		menu.addItem(new MenuItem("s", "Fourth"));
		menu.addItem(new MenuItem("s", "Fifth"));
		menu.addItem(new MenuItem("s", "Sixth").addSubMenu(new Menu("fsdf").addItem(new MenuItem("sdsd", "1.1th")).addItem(new MenuItem( "sdfs", "sdfsdfsdf"))));
		addChild(new MenuButton("asda", "sdfsdfs", menu).setIconCls("icon-add")); 
		
	}
	
	
	public void addGrid(){
		DataGridModel model = new DataGridModel() {
			
			@Override
			public int size() {
				return 200;
			}
			
			@Override
			public Object getValueAt(int row, int col) {
				return "Matrix value(" + col + "," + row + ")";
			}
		};
		
		
		DataGridColumnModel columns = new DataGridColumnModel() {
			
			@Override
			public int size() {
				return 5;
			}
			
			@Override
			public boolean isSortable(int index) {
				return true;
			}
			
			@Override
			public boolean isResizable(int index) {
				return true;
			}
			
			@Override
			public boolean isIdentityField(int index) {
				return index==0;
			}
			
			@Override
			public boolean isHidden(int index) {
				return false;
			}
			
			@Override
			public boolean isCheckBox(int index) {
				return index==4;
			}
			
			@Override
			public int getWidth(int index) {
				return 200;
			}
			
			@Override
			public String getTitle(int index) {
				return "Field " + index;
			}
			
			@Override
			public int getRowSpan(int index) {
				
				return 1;
			}
			
			@Override
			public String getOrder(int index) {
				return "asc";
			}
			
			@Override
			public String getName(int index) {
				return "field" + index;
			}
			
			@Override
			public String getHAlign(int index) {
				return "center";
			}
			
			@Override
			public String getAlign(int index) {
				return "center";
			}
		};
		
		DataGrid grid = new DataGrid("grid").setModel(model).setColumnModel(columns);
		grid.showFooter(true).showHeader(true).showPagination(true).showRowNumbers(true);
		grid.setFitColumns(true);
		addChild(grid);
	}

}
