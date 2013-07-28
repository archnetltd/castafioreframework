package org.castafiore.security.logs;

import java.util.List;
import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.table.EXPagineableTable;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.panel.EXPanel;

public class EXLogsTab extends EXContainer implements Event{

	public EXLogsTab(String name) {
		super(name, "Logs panel");
		setStyle("width", "700px");
		addChild(new EXLogSearch());
		addChild(new EXButton("search", "Search").addEvent(this, CLICK));
		EXPagineableTable ptable = new EXPagineableTable("pt", new EXTable("tt", new LogsModel()));
		addChild(ptable.setStyle("clear", "both"));
		
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		List<Log> logs = getDescendentOfType(EXLogSearch.class).search();
		EXPagineableTable ptable = getDescendentOfType(EXPagineableTable.class);
		
		EXTable table = ptable.getDescendentOfType(EXTable.class);
		
		LogsModel model = (LogsModel)table.getModel();
		model.setLogs(logs);
		ptable.refresh();

		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
