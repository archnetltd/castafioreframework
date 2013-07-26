package org.castafiore.reconcilliation.matching;

import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.castafiore.reconcilliation.ReconciliationUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.button.EXIconButton;
import org.castafiore.ui.ex.form.button.Icons;
import org.castafiore.ui.ex.form.table.CellRenderer;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableModel;

public class MatchingModel implements TableModel, CellRenderer ,Event{

	
	String[] labels = new String[]{"Original entry", "Invoice", "Amount","Bank Name", "Account Number", "Name", "Action"};
	
	Sheet match;
	
	
	
	public MatchingModel() throws Exception{
		super();
		match = ReconciliationUtil.getMatchSheet();
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public int getColumnCount() {
		return labels.length;
	}

	@Override
	public String getColumnNameAt(int index) {
		return labels[index];
	}

	@Override
	public int getRowCount() {
		
		Row r = match.getRow(0);
		if(r== null){
			return 0;
		}
		int last = match.getLastRowNum();
		
		int roe  = r.getRowNum();
	
		return match.getLastRowNum()+1;
	}

	@Override
	public int getRowsPerPage() {
		return 10;
	}

	@Override
	public Object getValueAt(int col, int row, int page) {
		int rrow = row + (page*getRowsPerPage());
		Row r = match.getRow(rrow);
		return r;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		
		return false;
	}

	@Override
	public Container getComponentAt(int row, int column, int page,
			TableModel model, EXTable table) {
		Row r = (Row)model.getValueAt(column, row, page);
		
		if(column == 0){
			return new EXContainer("original", "span").setText(r==null?"": r.getCell(0).getStringCellValue());
		}else if(column == 1){
			return new EXContainer("invoice", "span").setText(r==null?"":r.getCell(6).getStringCellValue());
		}else if(column == 2){
			return new EXContainer("amount", "span").setText(r==null?"":r.getCell(7).getStringCellValue());
		}else if(column == 3){
			return new EXContainer("bank", "span").setText(r==null?"":r.getCell(8).getStringCellValue());
		}else if(column == 4){
			return new EXContainer("accountNumber", "span").setText(r==null?"":r.getCell(9).getStringCellValue());
		}else if(column == 5){
			return new EXContainer("name", "span").setText(r==null?"":r.getCell(10).getStringCellValue());
		}else{
			
			return new EXIconButton("delete", Icons.ICON_CIRCLESMALL_MINUS).setAttribute("title", "Delete rule").addEvent(this, Event.CLICK).setAttribute("row", r==null?"0":r.getRowNum() + "");
		}
	}

	@Override
	public void onChangePage(Container component, int row, int column,
			int page, TableModel model, EXTable table) {
		Row r = (Row)model.getValueAt(column, row, page);
		if(column == 0){
			component.setText(r==null?"":r.getCell(1).getStringCellValue());
		}else if(column == 1){
			component.setText(r==null?"":r.getCell(7).getStringCellValue());
		}else if(column == 2){
			component.setText(r==null?"":r.getCell(8).getStringCellValue());
		}else if(column == 3){
			component.setText(r==null?"":r.getCell(9).getStringCellValue());
		}else if(column == 4){
			component.setText(r==null?"":r.getCell(10).getStringCellValue());
		}else if(column == 5){
			component.setText(r==null?"":r.getCell(11).getStringCellValue());
		}else{
			component.setAttribute("row", r==null?"0":r.getRowNum() + "");
		}
		
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		try{
		int row = Integer.parseInt(container.getAttribute("row"));
		
		Row r = match.getRow(row);
		match.removeRow(r);
		Workbook wb = match.getWorkbook();
		
		ReconciliationUtil.saveMatchSheet(wb);
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
