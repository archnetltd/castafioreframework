package org.castafiore.security.sessions;

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

public class SessionsManagerCellRenderer implements CellRenderer , Event{

	@Override
	public Container getComponentAt(int row, int column, int page,
			TableModel model, EXTable table) {
		
		LoggedSession s = (LoggedSession)model.getValueAt(column, row, page);
		
		if(column == 0){
			return new EXContainer("","label").setText(s.getUsername());
		}else if(column == 1){
			return new EXContainer("","label").setText(s.getIpAddress());
		}else if(column == 2){
			return new EXContainer("","label").setText(s.getFullName());
		}else if(column == 3){
			return new EXContainer("","label").setText(s.getSessionId());
		}else if(column == 4){
			return new EXIconButton("kill", Icons.ICON_CLOSE).setAttribute("title", "Kill session").setAttribute("sessionid", s.getSessionId()).addEvent(this, CLICK);
		}else{
			return null;
		}
		
	}

	@Override
	public void onChangePage(Container component, int row, int column,
			int page, TableModel model, EXTable table) {
LoggedSession s = (LoggedSession)model.getValueAt(column, row, page);
		
		if(column == 0){
			component.setText(s.getUsername());
		}else if(column == 1){
			component.setText(s.getIpAddress());
		}else if(column == 2){
			component.setText(s.getFullName());
		}else if(column == 3){
			component.setText(s.getSessionId());
		}else if(column == 4){
			component.setAttribute("sessionid", s.getSessionId()).addEvent(this, CLICK);
		}
		
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		String sessionId = container.getAttribute("sessionid");
		SpringUtil.getSecurityService().logout(sessionId);
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
