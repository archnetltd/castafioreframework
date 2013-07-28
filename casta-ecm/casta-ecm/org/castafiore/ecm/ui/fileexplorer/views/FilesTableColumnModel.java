package org.castafiore.ecm.ui.fileexplorer.views;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.castafiore.ecm.ui.fileexplorer.EXFileExplorer;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXCheckBox;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableColumnModel;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ComponentVisitor;

public class FilesTableColumnModel implements TableColumnModel ,Event{

	@Override
	public EXContainer getColumnAt(int index, EXTable table, TableModel model) {
		
		 if(index == 2){
			 return  (EXContainer)new EXContainer("", "th").addChild(new EXCheckBox("cball").addEvent(this, Event.CLICK));
		 }else if(index == 0){
			 return (EXContainer)new EXContainer("order", "th").setText(model.getColumnNameAt(index)).addEvent(this, CLICK).setStyle("cursor", "pointer");
		 }else{
			 return (EXContainer)new EXContainer("", "th").setText(model.getColumnNameAt(index));
		 }
	}
	
	
	
	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container,
			Map<String, String> request) throws UIException {
		
		
		if(container.getName().equalsIgnoreCase("cball")){
			EXCheckBox cb = (EXCheckBox)container;
			final boolean ch = cb.isChecked();
			final List<String> selected = new ArrayList<String>();
			
			ComponentUtil.iterateOverDescendentsOfType(container.getAncestorOfType(EXTable.class), EXCheckBox.class, new ComponentVisitor() {
				
				@Override
				public void doVisit(Container c) {
					
					((EXCheckBox)c).setChecked(ch);
					if(ch)
						selected.add(c.getAttribute("path"));
					
				}
			});
			
			
			container.getAncestorOfType(EXFileExplorer.class).setSelectedFiles(selected.toArray(new String[selected.size()]));
		}else{
			EXTable table = container.getAncestorOfType(EXTable.class);
			
		}
		
		
		return true;
	}

	@Override
	public void Success(ClientProxy container,
			Map<String, String> request) throws UIException {
		// TODO Auto-generated method stub
		
	}

}
