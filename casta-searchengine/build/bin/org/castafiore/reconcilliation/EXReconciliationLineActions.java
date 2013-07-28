package org.castafiore.reconcilliation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXCheckBox;
import org.castafiore.ui.ex.form.table.EXTable;

public class EXReconciliationLineActions extends EXContainer implements Event{

	ReconcilationDTO dto;
	
	private List<Map<String,String>> all = new ArrayList<Map<String,String>>();
	
	public EXReconciliationLineActions(String name, ReconcilationDTO dto, List<Map<String,String>> all_) {
		super(name, "div");
		this.all = all_;
		setStyle("width", "20px");
		if(dto.isOk()){
			System.out.print("OKKO");
		}
		
		boolean ok = false;
		if(ReconciliationUtil.getFromFsNumber(dto.getRefNumber(), all) != null){
			ok = true;
		}
		addChild(new EXCheckBox("cb",ok).addEvent(this, CHANGE));
		//addChild(new EXIconButton("replace", Icons.ICON_REFRESH).setAttribute("title", "Reconcilation with an order").addEvent(this, CLICK));
	//	addChild(new EXIconButton("delete", Icons.ICON_CANCEL).setAttribute("title", "Delete this record").addEvent(this, CLICK));
		//addChild(new EXIconButton("duplicate", Icons.ICON_PLUS).setAttribute("title", "Duplication this record").addEvent(this, CLICK));
		this.dto = dto;
	}
	
	
	public void setDto(ReconcilationDTO dto){
		this.dto = dto;
		boolean ok = false;
		if(ReconciliationUtil.getFromFsNumber(dto.getRefNumber(), all) != null){
			ok = true;
		}
		getDescendentOfType(EXCheckBox.class).setChecked(ok);
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		EXTable table = container.getAncestorOfType(EXTable.class);
		ReconciliationModel model = (ReconciliationModel)table.getModel();
		if(container.getName().equalsIgnoreCase("replace")){
			//show list of orders
		}else if(container.getName().equalsIgnoreCase("delete")){
			List<ReconcilationDTO> torem = new ArrayList<ReconcilationDTO>();
			torem.add(dto);
			model.refresh(torem);
			table.refresh();
			
		}else if(container.getName().equalsIgnoreCase("duplicate")){
			int index = model.table.indexOf(dto);
			model.table.add(index, dto);
			table.refresh();
		}else if(container.getName().equalsIgnoreCase("cb")){
			dto.setOk(((EXCheckBox)container).isChecked());
			if(dto.isOk())
				dto.setStatus("Manual");
			else
				dto.setStatus("Unreconciled");
		}
			
		
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
