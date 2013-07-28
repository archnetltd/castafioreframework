package org.castafiore.shoppingmall.employee.ui.v2;


import java.util.Map;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.button.EXIconButton;
import org.castafiore.ui.ex.form.button.Icons;
import org.castafiore.ui.ex.form.table.CellRenderer;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.Article;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;

public class SnapshotsTableModel implements TableModel, CellRenderer, Event {

	private static String[] cols = new String[]{"Label", "Original", "Updates", "Actions"};

	
	private Directory root = SpringUtil.getRepositoryService().getDirectory("/root/users/" + Util.getLoggedOrganization() + "/timesheet2", Util.getRemoteUser());

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}
	
	public void init(){
		root = SpringUtil.getRepositoryService().getDirectory("/root/users/" + Util.getLoggedOrganization() + "/timesheet2", Util.getRemoteUser());
	}

	@Override
	public int getColumnCount() {
		return cols.length;
	}

	@Override
	public String getColumnNameAt(int index) {
		return cols[index];
	}

	@Override
	public int getRowCount() {
		return root.getFiles(Article.class).toList().size();
	}

	@Override
	public int getRowsPerPage() {
		return 10;
	}

	@Override
	public Object getValueAt(int col, int row, int page) {
		int rrow = (page*getRowCount()) + row;
		return root.getFiles(Article.class).get(rrow);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public Container getComponentAt(int row, int column, int page,
			TableModel model, EXTable table) {
		Article art = (Article)model.getValueAt(column, row, page);
		if(column == 0){
			return new EXContainer("", "label").setText(art.getTitle());
		}else if(column == 1){
			return new EXContainer("", "a").setText("Original").setAttribute("href",ResourceUtil.getDownloadURL("ecm",art.getFile("orig.xls", BinaryFile.class).getAbsolutePath())).setAttribute("target", "_blank");
		}else if(column == 2){
			
			Container c = new EXContainer("", "div");
			
			for(File d : art.getFiles(BinaryFile.class).toList()){
				if(!d.getName().equalsIgnoreCase("orig.xls")){
					c.addChild( new EXContainer("", "a").setText(d.getName()).setAttribute("href",ResourceUtil.getDownloadURL("ecm",d.getAbsolutePath())).setAttribute("target", "_blank").setStyle("display", "block")  );
				}
			}
			return c;
			
		}else if(column == 3){
			return new EXIconButton("generate", Icons.ICON_ARROWTHICK_1_NE).setAttribute("title", "Upload an update").setAttribute("path", art.getAbsolutePath()).addEvent(this, Event.CLICK);
		}else{
			return null;
		}
	}

	@Override
	public void onChangePage(Container component, int row, int column,
			int page, TableModel model, EXTable table) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		
		String path = container.getAttribute("path");
		
		container.getAncestorOfType(PopupContainer.class).addPopup(new EXSnapshotUpdateForm("", path));
		
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
