package org.castafiore.swing.sales.options;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.CellEditorListener;
import javax.swing.event.TableColumnModelListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.castafiore.swing.sales.OptionsBuilder;
import org.openswing.swing.client.ComboBoxControl;
import org.openswing.swing.table.client.GridController;

public class PlanOptionsPropertyGridController extends GridController implements TableModel, TableCellRenderer, TableColumnModel{

	
	private String plan ="A";
	
	
	
	Map<JLabel, JComboBox> data = new HashMap<JLabel, JComboBox>();
	List<JLabel> labels = new ArrayList<JLabel>();
	List<JComboBox> controls = new ArrayList<JComboBox>();
	
	public PlanOptionsPropertyGridController(String plan) {
		super();
		setPlan(plan);
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		
		this.plan = plan;
		init();
	}
	
	private void init(){
		try{
		//data = OptionsBuilder.loadData(plan);
		Iterator<JLabel> keys = data.keySet().iterator();
		while(keys.hasNext()){
			JLabel key = keys.next();
			labels.add(key);
			controls.add(data.get(key));
		}
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public String getColumnName(int columnIndex) {
		if(columnIndex == 0){
			return "Label";
		}else {
			return "Options";
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if(columnIndex == 0){
			return JLabel.class;
		}else{
			return ComboBoxControl.class;
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex ==1;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(columnIndex == 0){
			return labels.get(rowIndex);
		}else{
			return controls.get(rowIndex);
		}
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addColumn(TableColumn aColumn) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeColumn(TableColumn column) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveColumn(int columnIndex, int newIndex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setColumnMargin(int newMargin) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Enumeration<TableColumn> getColumns() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getColumnIndex(Object columnIdentifier) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TableColumn getColumn(int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getColumnMargin() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getColumnIndexAtX(int xPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalColumnWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setColumnSelectionAllowed(boolean flag) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getColumnSelectionAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int[] getSelectedColumns() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getSelectedColumnCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setSelectionModel(ListSelectionModel newModel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ListSelectionModel getSelectionModel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addColumnModelListener(TableColumnModelListener x) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeColumnModelListener(TableColumnModelListener x) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		// TODO Auto-generated method stub
		return null;
	}

	///////////////////////////////////////////////////////////

	

	

	
	
	

}
