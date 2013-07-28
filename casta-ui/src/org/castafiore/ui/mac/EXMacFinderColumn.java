package org.castafiore.ui.mac;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.mac.events.OpenColumnEventHandler;
import org.castafiore.ui.mac.renderer.MacFinderColumnViewModel;

public class EXMacFinderColumn extends EXContainer {
	
	private MacFinderColumnViewModel viewModel;
	
	private OpenColumnEventHandler openColumnEventHandler;
	
	public EXMacFinderColumn(String name) {
		super(name, "div");
		addClass("ui-finder-column").addClass("ui-widget-content");
		
		Container finderContent = new EXContainer("finderContent", "div").addClass("ui-finder-content");
		addChild(finderContent);
		setStyle("display", "block").setStyle("left", "0").setStyle("right", "0").setStyle("overflow-y", "scroll").setStyle("z-index", "2").setStyle("visibility", "visible");
		setColumnBody(new EXContainer("content", "ol"));
	}
	
	private void setColumnBody(Container body){
		getChild("finderContent").getChildren().clear();
		getChild("finderContent").setRendered(false).addChild(body);
		
	}
	
	private Container  getColumnBody(){
		return getChild("finderContent").getChildByIndex(0);
	}

	public void refresh(){
		Container body = getColumnBody();
		body.getChildren().clear();
		body.setRendered(false);
		for(int i = 0; i < viewModel.size(); i ++){
			body.addChild(viewModel.getValueAt(i).addClass("ui-finder-list-item"));
		}
	}
	
	
	public MacFinderColumnViewModel getViewModel() {
		return viewModel;
	}

	public void setViewModel(MacFinderColumnViewModel viewModel) {
		this.viewModel = viewModel;
		refresh();
	}
	
	
	public void openUsing(Container caller){
		if(caller.getAncestorOfType(EXMacFinderColumn.class) == null){
			throw new UIException("This method should not be called that way");
		}
		
		if(caller.getAncestorOfType(EXMacFinderColumn.class).getId().equals(getId())){
			
			if(this.openColumnEventHandler != null){
				EXMacFinderColumn column = openColumnEventHandler.getColumn(caller);
				doOpenMacFinderColumn(column);
			}
			
			
		}else{
			throw new UIException("This caller " + caller.getName() + " is not functioning well. It is executing outside its context");
		}
	}
	
	protected void doOpenMacFinderColumn(EXMacFinderColumn column){
		Container parent = getParent();
		int index= parent.getChildren().indexOf(this);
		int size = parent.getChildren().size();
		
		List<String> idtoRemove = new ArrayList<String>();
 		if(index == (size-1)){
			//there is no remove
		}else{
			for(int i = (index+1); i < size; i++){
				idtoRemove.add(parent.getChildByIndex(i).getId());
			}
		}
		
 		for(String id : idtoRemove){
 			parent.getDescendentById(id).remove();
 		}
		getAncestorOfType(EXMacFinder.class).addColumn(column);
	}

	public OpenColumnEventHandler getOpenColumnEventHandler() {
		return openColumnEventHandler;
	}

	public void setOpenColumnEventHandler(
			OpenColumnEventHandler openColumnEventHandler) {
		this.openColumnEventHandler = openColumnEventHandler;
	}
	
	
	
	

}
