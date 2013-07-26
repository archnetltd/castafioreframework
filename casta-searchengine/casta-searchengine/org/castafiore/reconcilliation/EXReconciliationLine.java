package org.castafiore.reconcilliation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.castafiore.reconcilliation.matching.ReconciliationMatcher;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.EXGrid;
import org.castafiore.ui.ex.form.EXAutoComplete;
import org.castafiore.ui.ex.form.EXDatePicker;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.button.EXIconButton;
import org.castafiore.ui.ex.form.button.Icons;
import org.castafiore.ui.ex.form.table.EXPagineableTable;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.panel.EXPanel;

public class EXReconciliationLine extends EXContainer implements Event{
	
	private ReconcilationDTO dto_;
	private Sheet match_;

	public EXReconciliationLine(String name, ReconcilationDTO dto, Sheet matchsheet) {
		super(name, "div");
		this.dto_ = dto;
		EXGrid grid = new EXGrid("gr", 2,4);
		grid.setStyle("margin", "0");
		match_ = matchsheet;
		grid.getCell(0, 0).setStyle("width", "40%").setStyle("border-right", "none").addChild(new EXContainer("", "label").setText("Date :"));
		grid.getCell(0, 0).setStyle("border-right", "none").addChild(new EXContainer("date", "span").addEvent(this, CLICK).setStyle("cursor", "pointer").setText(dto.getDate()));
		
		grid.getCell(1, 0).setStyle("border-right", "none").setStyle("width", "60%").addChild(new EXContainer("", "label").setText("Invoice :"));
		grid.getCell(1, 0).setStyle("border-right", "none").addChild(new EXContainer("invoice", "span").addEvent(this, CLICK).setStyle("cursor", "pointer").setText(dto.getRefNumber()));
		
		grid.getCell(0, 1).setStyle("border-right", "none").setStyle("width", "40%").addChild(new EXContainer("", "label").setText("Bank Acc :"));
		grid.getCell(0, 1).setStyle("border-right", "none").addChild(new EXContainer("account", "span").addEvent(this, CLICK).setStyle("cursor", "pointer").setText(dto.getAccountNumber()));
		
		grid.getCell(1, 1).setStyle("border-right", "none").setStyle("width", "60%").addChild(new EXContainer("", "label").setText("Amount :"));
		grid.getCell(1, 1).setStyle("border-right", "none").addChild(new EXContainer("amount", "span").addEvent(this, CLICK).setStyle("cursor", "pointer").setText(dto.getAmount()));
		
		
		
		grid.getCell(0, 2).setStyle("border-right", "none").setStyle("width", "40%").addChild(new EXContainer("", "label").setText("Bank Name : "));
		grid.getCell(0, 2).setStyle("border-right", "none").addChild(new EXContainer("bank", "span").setText(dto.getBank()));
		
		
		
		grid.getCell(1, 2).setStyle("border-right", "none").setStyle("width", "60%").addChild(getActions(matchsheet));
		//grid.getCell(1, 2).setStyle("width", "60%").addChild(new EXContainer("", "label").setText("Comment : "));
		//grid.getCell(1, 2).addChild(new EXContainer("comment", "span").setText(dto.getComment()));
		
		grid.getCell(0, 3).setStyle("border-right", "none").setStyle("width", "100%").setAttribute("colspan", "2").setText("<label>Name :</label>" + dto.getName());
		grid.getCell(1, 3).remove();
		addChild(grid);
		
	
		
		
	}
	
	
	private Container getActions(Sheet match){
		Container c = new EXContainer("actions", "div");
		Container delete = new EXContainer("delete", "a").setAttribute("title", "Delete this record");
		Container duplicate = new EXContainer("duplicate", "a").setAttribute("title", "Duplicate this record");
		Container searchOrder = new EXContainer("searchOrder", "a").setAttribute("title", "Search possible order matching this record");
		Container matchingRule = new EXContainer("matchingRule", "a").setAttribute("title", "Create a matching rule for this record for later automatic reconcilation");
		delete.setText("<img src='icons-2/fugue/icons/cross-script.png'></img>").setStyle("padding", "5px").setStyle("cursor", "pointer");
		duplicate.setText("<img src='icons-2/fugue/icons/books.png'></img>").setStyle("padding", "5px").setStyle("cursor", "pointer");
		searchOrder.setText("<img src='icons-2/fugue/icons/binocular.png'></img>").setStyle("padding", "5px").setStyle("cursor", "pointer");
		matchingRule.setText("<img src='icons-2/fugue/icons/node-delete-child.png'></img>").setStyle("padding", "5px").setStyle("cursor", "pointer");
		c.addChild(delete.addEvent(this, CLICK));
		c.addChild(duplicate.addEvent(this, CLICK));
		c.addChild(searchOrder.addEvent(this, CLICK));
		c.addChild(matchingRule.addEvent(this, CLICK));
		
		String smatch = ReconciliationUtil.getMatch(match, dto_);
		if(smatch != null){
			dto_.setStatus("Matched");
			c.addChild(new EXContainer("matchc", "div").addChild(new EXContainer("", "label").setText("Match found : ")).addChild(new EXContainer("match", "span").setText(smatch)));
		}
		
		return c;
		
		
		//delete
		//duplicate
		//search order
		//perform matching
	}
	
	
	public void setDto(ReconcilationDTO dto){
		this.dto_ = dto;
		getDescendentByName("date").setText(dto.getDate());
		getDescendentByName("invoice").setText(dto.getRefNumber());
		getDescendentByName("amount").setText(dto.getAmount());
		getDescendentByName("bank").setText(dto.getBank());
		getDescendentByName("account").setText(dto.getAccountNumber());
		//getDescendentByName("comment").setText(dto.getComment());
		Container parent = getDescendentByName("actions").getParent();
		parent.getChild("actions").remove();
		parent.addChild(getActions(match_));
		getDescendentOfType(EXGrid.class).getCell(0, 3).setText("<label>Name :</label>" + dto.getName());
	}


	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}
	
	
	public void edit(Container c){
		
		List<Map<String,String>> allOrders = ((ReconciliationModel) c.getAncestorOfType(EXTable.class).getModel()).rows_;
		c.getEvents().clear();
		
		String name = c.getName();
		String txt = c.getText(false);
		c.setText("");
		EXInput input;
		if(c.getName().equalsIgnoreCase("date")){
			input = new EXDatePicker("input");
		}else if(c.getName().equalsIgnoreCase("invoice")){
			List<String> dict = new ArrayList<String>();
			for(Map<String,String> o : allOrders){
				dict.add(o.get("fsNumber"));
			}
			Collections.sort(dict);
			input = new EXAutoComplete("input", "", dict);
		}else{
			input = new EXInput("input");
		}
		c.addChild(input).addChild(new EXIconButton(name, Icons.ICON_DISK).setStyle("float", "right").addEvent(this, CLICK)).addChild(new EXIconButton("nok", Icons.ICON_CANCEL).setStyle("float", "right").addEvent(this, CLICK));
		c.getDescendentOfType(EXInput.class).setRawValue(txt);
	}
	
	
	public void cancel(Container source){
		Container cell = source.getParent();
		String txt = cell.getDescendentOfType(EXInput.class).getRawValue();
		cell.getChildren().clear();
		cell.setText(txt);
		cell.addEvent(this, CLICK);
		//setDto(dto_);
		
	}
	
	
	public void save(Container source){
		Container cell = source.getParent();
		String txt = cell.getDescendentOfType(EXInput.class).getRawValue();
		cell.getChildren().clear();
		cell.addEvent(this, CLICK);
		cell.setText(txt);
		
		
		List<Map<String,String>> allOrder = ((ReconciliationModel) source.getAncestorOfType(EXTable.class).getModel()).rows_;
		if(source.getName().equalsIgnoreCase("date")){
			dto_.setDate(txt);
		}else if(source.getName().equalsIgnoreCase("invoice")){
			dto_.setRefNumber(txt);
			Map<String,String> order = ReconciliationUtil.searchFromCode(allOrder, txt);
			if(order != null){
				dto_.setAmount(order.get("installment"));
				dto_.setComment("Updated from invoice " + txt);
				dto_.setOk(true);
				dto_.setBank(order.get("bankName"));
				dto_.setName(order.get("cfullName") + " " + order.get("cSurname"));
				dto_.setAccountNumber(order.get("accountNumber"));
				//dto_.setStatus("Manual");
				setDto(dto_);
			}
			
		}else if(source.getName().equalsIgnoreCase("account")){
			dto_.setAccountNumber(txt);
		}else if(source.getName().equalsIgnoreCase("amount")){
			dto_.setAmount(txt);
		}
		
	}


	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		EXTable table = container.getAncestorOfType(EXTable.class);
		ReconciliationModel model = (ReconciliationModel)table.getModel();
		if(container.getName().equalsIgnoreCase("searchOrder")){
			EXFastSearchOrder so = new EXFastSearchOrder("fs", dto_);
			getAncestorOfType(EXPanel.class).getChildByIndex(1).addChild(so);
			return true;
		}else if(container.getName().equalsIgnoreCase("delete")){
			List<ReconcilationDTO> torem = new ArrayList<ReconcilationDTO>();
			torem.add(dto_);
			model.refresh(torem);
			//table.refresh();
			table.getAncestorOfType(EXPagineableTable.class).refresh();
			model.updateStats(container);
			
			return true;
		}else if(container.getName().equalsIgnoreCase("duplicate")){
			int index = model.table.indexOf(dto_);
			model.table.add(index, dto_);
			table.getAncestorOfType(EXPagineableTable.class).refresh();
			model.updateStats(container);
			return true;
		}else if(container.getName().equalsIgnoreCase("matchingRule")){
			EXPanel panel = new EXPanel("mathn", "Reconcilation Matchning rules");
			panel.setStyle("width", "875px");
			panel.setBody(new ReconciliationMatcher("crec", dto_));
			panel.setStyle("z-index", "7000");
			
			getAncestorOfType(EXPanel.class).getChildByIndex(1).addChild(panel);
			return true;
		}
		
		
		
		if(container.getName().equalsIgnoreCase("nok")){
			cancel(container);
		}else{
			if(container instanceof EXIconButton){
				save(container);
			}else{
				edit(container);
			}
		}
		return true;
	}


	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
