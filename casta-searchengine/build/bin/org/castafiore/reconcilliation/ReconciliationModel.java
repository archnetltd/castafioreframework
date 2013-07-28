package org.castafiore.reconcilliation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.castafiore.shoppingmall.checkout.Order;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXLabel;
import org.castafiore.ui.ex.form.table.CellRenderer;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.ui.ex.panel.EXPanel;



public class ReconciliationModel implements TableModel, CellRenderer{
	public List<ReconcilationDTO> table = new ArrayList<ReconcilationDTO>();
	String[] columns = new String[]{"Line", "Actions"} ;
	public List<Map<String, String>> rows_ = new ArrayList<Map<String, String>>();
	
	private Sheet match;
	public ReconciliationModel(List<ReconcilationDTO> table_, List<Map<String, String>> allOrder)throws Exception {
		super();
		this.table = table_;
		rows_ = allOrder;
		match = ReconciliationUtil.getMatchSheet();
	}	
	
	
	public void updateStats(Container c){
		Container stats = c.getAncestorOfType(EXPanel.class).getDescendentByName("stats");
		//stats.getChildren().clear();
		//stats.setRendered(false);
		
		int automatic = 0;
		int unreconciled = 0;
		int match = 0;
		int total = 0;
		int manual =0;
		total = table.size();
		for(ReconcilationDTO dto : table){
			if(dto.getStatus().equalsIgnoreCase("New") || dto.getStatus().equalsIgnoreCase("Unreconciled")){
				unreconciled++;
			}else if(dto.getStatus().contains("Automatic")){
				automatic++;
			}else if(dto.getStatus().equalsIgnoreCase("Manual")){
				manual++;
			}else if(dto.getStatus().equalsIgnoreCase("Matched")){
				match++;
			}
		}
		
		stats.getChild("totalRecondReconc").setText("Total records : " + total);
		stats.getChild("automaticReconc").setText("Automatically reconciled : " + automatic);
		stats.getChild("matchingReconc").setText("Reconciled by matching : " + match);
		stats.getChild("manualReconc").setText("Reconciled manually : " + manual);
		stats.getChild("notReconc").setText("Unreconciled : " + unreconciled);
		
		
	}
	
	public String saveState(){
		StringBuilder b = new StringBuilder();
	
		for(ReconcilationDTO dto : table){
			b.append(dto.toString()).append( "\n");
		}
		
		return b.toString();
	}
	
	public void refresh(List<ReconcilationDTO> toremove){
		table.removeAll(toremove);
		try{
		match = ReconciliationUtil.getMatchSheet();
		}catch(Exception e){
			throw new UIException(e);
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public String getColumnNameAt(int index) {
		return columns[index];
	}

	@Override
	public int getRowCount() {
		return table.size();
	}

	@Override
	public int getRowsPerPage() {
		return 10;
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
	@Override
	public Container getComponentAt(int row, int column, int page,
			TableModel model, EXTable table) {
		ReconcilationDTO td = (ReconcilationDTO)table.getModel().getValueAt(column, row, page);		
		if(column == 0){
			EXReconciliationLine line = new EXReconciliationLine("line", td,match);
			return line;
		}else{
			return new EXReconciliationLineActions("act", td,rows_);
		}
		
	}

	@Override
	public void onChangePage(Container component, int row, int column,
			int page, TableModel model, EXTable table) {
		ReconcilationDTO td = (ReconcilationDTO)table.getModel().getValueAt(column, row, page);
		if(column == 0){
		((EXReconciliationLine)component).setDto(td);
		}else{
			((EXReconciliationLineActions)component).setDto(td);
		}
	}
	
}
