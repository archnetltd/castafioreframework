package com.eliensons.ui.reconciliation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.castafiore.shoppingmall.checkout.Order;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXAutoComplete;
import org.castafiore.ui.ex.form.EXCheckBox;
import org.castafiore.ui.ex.form.table.CellRenderer;
import org.castafiore.ui.ex.form.table.EXPagineableTable;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableModel;

import com.eliensons.ui.ElieNSonsUtil;



public class ReconciliationModel implements TableModel, CellRenderer, Event{
	//Container table = null;
	List<ReconcilationDTO> table = new ArrayList<ReconcilationDTO>();
	String[] colums = new String[]{"Date", "Acc No", "Customer", "Amount","Ref No",  "Comment", "Accepted"};
	
	List<Order> rows = new ArrayList<Order>();
	List<String> dict = new ArrayList<String>();
	
	public ReconciliationModel(List<ReconcilationDTO> table_, List<Order> allOrder) {
		super();
		this.table = table_;
		rows = allOrder;
		for(Order o : rows){
			dict.add(o.getCode());
		}
		Collections.sort(dict);
	}


	
	public void setOrder(Order order, int row){
		ReconcilationDTO dto = table.get(row);
		dto.setRefNumber(order.getCode());
		dto.setComment("Replaced with new order");
		dto.setOk(true);
		
	}
	
	public void refresh(List<ReconcilationDTO> toremove){
		table.removeAll(toremove);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public int getColumnCount() {
		return colums.length;
	}

	@Override
	public String getColumnNameAt(int index) {
		return colums[index];
	}

	@Override
	public int getRowCount() {
		return table.size();
	}

	@Override
	public int getRowsPerPage() {
		return 20;
	}

	@Override
	public Object getValueAt(int col, int row, int page) {
		int realRow = (page*getRowsPerPage()) + row;
		return table.get(realRow);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	//static String[] names = new String[]{"date", ""}
	@Override
	public Container getComponentAt(int row, int column, int page,
			TableModel model, EXTable table) {
		ReconcilationDTO td = (ReconcilationDTO)table.getModel().getValueAt(column, row, page);
		int realRow = (page*getRowsPerPage()) + row;
		String text = td.getDate();
		if(column == 0){
			text = td.getDate();
		}else if(column == 1){
			text=td.getAccountNumber();
		}else if(column == 2){
			text=td.getName();
		}else if(column == 3){
			text = td.getAmount();
		}else if(column == 4){
			text = td.getRefNumber();
		}else if(column == 5){
			text = td.getComment();
		}else {
			EXCheckBox cb = new EXCheckBox("",td.isOk());
			return cb.setAttribute("row", realRow + "");
		}
		 Container c = new EXContainer(colums[column], "div").setText(text).setAttribute("row", row + "").setAttribute("row", realRow + "").setAttribute("col", column + "");
		 if(column == 4){
			 c.addEvent(this, Event.CLICK);
		 }
		 return c;
	}

	@Override
	public void onChangePage(Container component, int row, int column,
			int page, TableModel model, EXTable table) {
		
		
		ReconcilationDTO td = (ReconcilationDTO)table.getModel().getValueAt(column, row, page);
		String text = td.getDate();
		if(column == 0){
			text = td.getDate();
		}else if(column == 1){
			text=td.getAccountNumber();
		}else if(column == 2){
			text=td.getName();
		}else if(column == 3){
			text = td.getAmount();
		}else if(column == 4){
			text = td.getRefNumber();
		}else if(column == 5){
			text = td.getComment();
		}else {
			EXCheckBox cb = (EXCheckBox)component;//new EXCheckBox("",td.isOk());
			cb.setChecked(td.isOk());
			return;
			
		}
		component.setText(text);
	}
	
	
	

	@Override
	public void ClientAction(ClientProxy container) {
		
		container.mask().makeServerRequest(this);
	}



	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		if(container instanceof EXAutoComplete){
			EXAutoComplete ac = (EXAutoComplete)container;
			String code = ac.getValue().toString();
			Order order = ElieNSonsUtil.searchFromCode(rows, code);
			if(order != null){
				setOrder(order, Integer.parseInt(container.getAttribute("row")));
				//container.getAncestorOfType(EXPagineableTable.class).refresh();
				Container parent = container.getParent();
				container.remove();
				parent.setText(code);
				parent.getParent().getParent().getDescendentByName(colums[5]).setText("Replaced with new order");
				parent.getParent().getParent().getDescendentOfType(EXCheckBox.class).setChecked(true);
				return true;
			}
		}else{
			String text = container.getText(false);
			container.setText("");
			container.addChild(new EXAutoComplete("", text,dict).addEvent(this, BLUR).setAttribute("row",container.getAttribute("row")));
			container.getEvents().clear();
			container.setRendered(false);
		}
		return true;
	}



	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}
	

}
