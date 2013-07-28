package org.castafiore.reconcilliation;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
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
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;

public class ReconciliationListCellRenderer implements CellRenderer, Event {

	@Override
	public Container getComponentAt(int row, int column, int page,
			TableModel model, EXTable table) {

		Directory dir = (Directory)model.getValueAt(column, row, page);
		
		if(column == 0){
			return new EXContainer("span", "span").setText(new SimpleDateFormat("dd/MM/yyyy").format(dir.getDateCreated().getTime()));
		}else if(column == 1){
			Container div = new EXContainer("div", "div");
			List<BinaryFile> bfs = dir.getFiles(BinaryFile.class).toList();
			for(BinaryFile bf : bfs){
				if(!bf.getName().startsWith("ongoing")){
					div.addChild(new EXContainer("a", "a").setAttribute("href", ResourceUtil.getDownloadURL("ecm", bf.getAbsolutePath())).setAttribute("target", "_blank").setText("Download Original"));
				
				}
			}
			
			return div;
			
		}else if(column == 2){
			EXIconButton icon = new EXIconButton("eval", Icons.ICON_ARROWREFRESH_1_N);
			icon.setAttribute("path", dir.getAbsolutePath());
			icon.addEvent(this, CLICK);
			return icon;
			
		}
		
		return null;
	}

	@Override
	public void onChangePage(Container component, int row, int column,
			int page, TableModel model, EXTable table) {
Directory dir = (Directory)model.getValueAt(column, row, page);
		
		if(column == 0){
			component.setText(new SimpleDateFormat("dd/MM/yyyy").format(dir.getDateCreated().getTime()));
		}else if(column == 1){
			component.getChildren().clear();
			component.setRendered(false);
			List<BinaryFile> bfs = dir.getFiles(BinaryFile.class).toList();
			for(BinaryFile bf : bfs){
				if(!bf.getName().startsWith("ongoing")){
					component.addChild(new EXContainer("a", "a").setAttribute("href", ResourceUtil.getDownloadURL("ecm", bf.getAbsolutePath())).setAttribute("target", "_blank").setText("Download"));
				
				}
			}
		}else if(column == 2){
			component.setAttribute("path", dir.getAbsolutePath());
			
		}
		
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		try{
		EXReconciliationPanel panel = container.getAncestorOfType(EXReconciliationPanel.class);
		String path = container.getAttribute("path");
		
		RepositoryService service = SpringUtil.getRepositoryService();
		Directory dir = service.getDirectory(path, Util.getRemoteUser());
		panel.open(dir, container);
		}catch(Exception e){
			throw new UIException(e);
		}
		return true;
		
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
