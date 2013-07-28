package org.castafiore.shoppingmall.employee.ui.v2;

import java.util.Map;

import org.castafiore.searchengine.back.EXWindow;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.table.EXPagineableTable;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.toolbar.EXToolBar;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.Directory;

public class EXTimesheetV2 extends EXWindow implements Event{

	public EXTimesheetV2() {
		super("EXTimesheetV2", "Timesheet pointer device application");
		
		init();
		
		
		
		Container body = new EXContainer("", "div").addClass("ui-widget-content");
		
		SnapshotsTableModel model = new SnapshotsTableModel();
		
		EXTable table = new EXTable("snsn",model);
		table.setCellRenderer(model);
		EXPagineableTable ptable = new EXPagineableTable("", table);
		
		
		EXToolBar tb = new EXToolBar("");
		tb.addItem(new EXButton("import", "Import"));
		
		body.addChild(tb);
		
		
		body.addChild(ptable.setStyle("clear", "both"));
		
		setBody(body);
		getDescendentByName("import").addEvent(this, CLICK);
	}
	
	private void init(){
		if(!SpringUtil.getRepositoryService().itemExists("/root/users/" + Util.getLoggedOrganization() + "/timesheet2")){
			Directory dir = SpringUtil.getRepositoryService().getDirectory("/root/users/" + Util.getLoggedOrganization(), Util.getRemoteUser()).createFile("timesheet2", Directory.class);
			dir.save();
			
		}
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		if(container.getName().equalsIgnoreCase("import"))
			getAncestorOfType(PopupContainer.class).addPopup(new EXNewSnapshotForm("sn"));
		
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
