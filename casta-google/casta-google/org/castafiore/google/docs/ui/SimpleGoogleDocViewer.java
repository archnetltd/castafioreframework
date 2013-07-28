package org.castafiore.google.docs.ui;

import java.util.List;
import java.util.Map;

import org.castafiore.google.docs.GoogleDocAccount;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.dynaform.EXFieldSet;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXPassword;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.table.EXPagineableTable;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.ui.ex.panel.EXPanel;

import com.google.gdata.data.docs.DocumentListEntry;

public class SimpleGoogleDocViewer extends EXPanel implements Event{

	public SimpleGoogleDocViewer(String name) {
		
		super(name,  "Hello google docs!");
		setShowCloseButton(true);
		setDraggable(true);
		setShowFooter(true);
		EXFieldSet set = new EXFieldSet("fs", "Credentials", false);
		set.addField("Username :", new EXInput("username"));
		set.addField("Password :", new EXPassword("password"));
		//addChild(set);
		setBody(set);
		setStyle("width", "700px");
		
		getFooterContainer().addChild(new EXButton("Connect", "Connect").addEvent(this, Event.CLICK));
	}
	
	
	public void connect()throws Exception{
		GoogleDocAccount account = new GoogleDocAccount();
		String username =((StatefullComponent)getDescendentByName("username")).getValue().toString();
		String password =((StatefullComponent)getDescendentByName("password")).getValue().toString();
		account.setUsername(username);
		account.setPassword(password);
		List<DocumentListEntry> entries = account.getEntries(0, 0, null, null);
		EXTable table = getDescendentOfType(EXTable.class);
	
		
		
		TableModel model = new SimpleGoogleDocTableModel(entries);
		if(table == null){
			table = new EXTable("", model);
			table.setStyle("width", "100%");
			table.setCellRenderer(new SimpleGoogleDocCellRenderer());
			table.setRowDecorator(null);
			
			EXPagineableTable page = new EXPagineableTable("", table);
			getBodyContainer().addChild(page);
		}else{
			table.setModel(model);
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
			connect();
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
