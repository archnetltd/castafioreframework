package org.castafiore.accounting.ui;

import java.util.Map;

import org.castafiore.accounting.Account;
import org.castafiore.inventory.AbstractModel;
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
import org.castafiore.ui.ex.form.table.EXPagineableTable;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;

public class AccountCellRenderer implements CellRenderer, Event{

	@Override
	public Container getComponentAt(int row, int column, int page,
			TableModel model, EXTable table) {
		Account p = (Account)model.getValueAt(column, row, page);
		String cDir = ((AccountModel)model).getCashBookPath();
		Container result = new  EXContainer("span", "span");
		if(column == 0){
			return result.setText(p.getCode());
		}else if(column == 1){
			result = new EXContainer("span", "a").setAttribute("cashbookdir", cDir).setAttribute("href", "#").setAttribute("path", p.getAbsolutePath()).addEvent(this, Event.CLICK);
			return result.setText(p.getTitle());
		}else if(column == 2){
			return result.setText(p.getAccType());
		}else if(column == 3){
			return new EXIconButton("del", Icons.ICON_CLOSETHICK).setAttribute("path", p.getAbsolutePath()).addEvent(this, CLICK);
		}
		
		return result;
	}

	@Override
	public void onChangePage(Container result, int row, int column,
			int page, TableModel model, EXTable table) {
		
		Account p = (Account)model.getValueAt(column, row, page);
		String cDir = ((AccountModel)model).getCashBookPath();
		if(column == 0){
			 result.setText(p.getCode());
		}else if(column == 1){
			result = result.setAttribute("cashbookdir", cDir).setAttribute("path", p.getAbsolutePath());
			 result.setText(p.getTitle());
		}else if(column == 2){
			 result.setText(p.getAccType());
		}else if(column == 3){
			result.setAttribute("path", p.getAbsolutePath());
		}
		
		
	}
	
	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		try{
			if(container.getName().equalsIgnoreCase("del")){
				
				String path = container.getAttribute("path");
				File f = SpringUtil.getRepositoryService().getFile(path, Util.getRemoteUser());
				
				Directory parent = f.getParent();
				f.remove();
				parent.save();
				
				((AbstractModel)container.getAncestorOfType(EXTable.class).getModel()).refresh();
				container.getAncestorOfType(EXPagineableTable.class).refresh();
				
				return true;
			}
			
			BAccountForm form = new BAccountForm("BAccountForm", container.getAttribute("cashbookdir"));
			form.setAccount((Account)SpringUtil.getRepositoryService().getFile(container.getAttribute("path"), Util.getRemoteUser()));
			container.getAncestorOfType(PopupContainer.class).addPopup(form);
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
