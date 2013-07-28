package org.castafiore.ecm.ui.fileexplorer.icon.dnd;

import java.util.Map;

import org.castafiore.ecm.ui.fileexplorer.Explorer;
import org.castafiore.ecm.ui.fileexplorer.FileExplorerUtil;
import org.castafiore.ecm.ui.fileexplorer.icon.ICon;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.wfs.futil;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;
import org.castafiore.wfs.types.Shortcut;

public class EXDropIconPanel extends EXDynaformPanel implements Event{

	
	private ICon component_;
	private ICon icon_;
	public EXDropIconPanel(String name, ICon component, ICon icon) {
		super(name, "Drop file");
		EXContainer msg = new EXContainer("", "span");
		msg.setText("What do you want to do with the file?");
		setBody(msg);
		addButton((EXButton)new EXButton("shortcut", "Shortcut").addEvent(this, Event.CLICK));
		addButton((EXButton)new EXButton("move", "Move").addEvent(this, Event.CLICK));
		addButton((EXButton)new EXButton("copy", "Copy").addEvent(this, Event.CLICK));
		addButton((EXButton)new EXButton("cancel", "Cancel").addEvent(CLOSE_EVENT, Event.CLICK));
		this.component_ = component;
		this.icon_ = icon;
	}
	
	
	
	
	
	public void shortcut(){
		File f = icon_.getFile();
		Directory dir = (Directory)component_.getFile();
		Shortcut sh = futil.getDirectory(dir.getAbsolutePath()).createFile(f.getName() + " (shortcut)", Shortcut.class);//  new Shortcut();
		sh.setReference(f.getAbsolutePath());
		sh.save();
		Explorer explorer = getAncestorOfType(Explorer.class);
		FileExplorerUtil.refreshNodeInTree(explorer, dir.getAbsolutePath());
		remove();
	}
	
	
	public void move(){
		File f = icon_.getFile();
		Directory sourceParent = f.getParent();
		
		Directory dir = (Directory)component_.getFile();
		f.moveTo(dir.getAbsolutePath());
		Explorer explorer = getAncestorOfType(Explorer.class);
		FileExplorerUtil.refreshNodeInTree(explorer, dir.getAbsolutePath());
		FileExplorerUtil.refreshNodeInTree(explorer, sourceParent.getAbsolutePath());
		icon_.setDisplay(false);
		remove();
	}
	
	
	public void copy(){
		File f = icon_.getFile();
		Directory sourceParent = f.getParent();
		
		Directory dir = (Directory)component_.getFile();
		f.copyTo(dir.getAbsolutePath());
		Explorer explorer = getAncestorOfType(Explorer.class);
		FileExplorerUtil.refreshNodeInTree(explorer, dir.getAbsolutePath());
		FileExplorerUtil.refreshNodeInTree(explorer, sourceParent.getAbsolutePath());
		remove();
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask(container.getAncestorOfType(EXPanel.class)).makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		try{
			getClass().getMethod(container.getName(), new Class[]{}).invoke(this, new Object[]{});
		}
		catch(Exception e){
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
