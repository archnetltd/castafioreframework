package org.castafiore.shoppingmall.employee.ui.tables;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import org.castafiore.shoppingmall.employee.TimesheetEntry;
import org.castafiore.shoppingmall.employee.ui.ProjectTimesheetEntries;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.table.CellRenderer;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableModel;

public class MyTimesheetCellRenderer implements CellRenderer, Event{
	
	public final static SimpleDateFormat hhmm = new SimpleDateFormat("HH:mm");
	
	private String userid_;
	
	

	public MyTimesheetCellRenderer(String userid) {
		super();
		userid_ = userid;
		
	}

	@Override
	public Container getComponentAt(int row, int column, int page,
			TableModel model, EXTable table) {
		ProjectTimesheetEntries entry = (ProjectTimesheetEntries)model.getValueAt(column, row, page);
		MyTimesheetTableModel mo = (MyTimesheetTableModel)model;
		Calendar startDate = mo.getStartDate();
		if(column == 0){
			return  new EXContainer("", "label").setText((model.getRowsPerPage()*page)+ row + 1 + ""); 
		}else if(column == 1){
			return new EXContainer("", "label").setText(entry.getProject());
		}else {
			int amt = column-2;
			Calendar cstart = (Calendar)startDate.clone();
			cstart.add(Calendar.DATE, amt);
			
			TimesheetEntry entr = entry.getEntry(cstart);
			
			return new EXTimesheetEntry("", entr, userid_).setAttribute("time", cstart.getTimeInMillis() + "").setAttribute("project", entry.getProject());
		}
	}

	@Override
	public void onChangePage(Container component, int row, int column,
			int page, TableModel model, EXTable table) {
ProjectTimesheetEntries entry = (ProjectTimesheetEntries)model.getValueAt(column, row, page);
MyTimesheetTableModel mo = (MyTimesheetTableModel)model;
Calendar startDate = mo.getStartDate();
		if(column == 0){
			component.setText((model.getRowsPerPage()*page)+ row + 1 + ""); 
		}else if(column == 1){
			component.setText(entry.getProject());
		}else {
			int amt = column-2;
			Calendar cstart = (Calendar)startDate.clone();
			cstart.add(Calendar.DATE, amt);
			TimesheetEntry entr = entry.getEntry(cstart);
			((EXTimesheetEntry)component).setEntry(entr);
		}
		
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
