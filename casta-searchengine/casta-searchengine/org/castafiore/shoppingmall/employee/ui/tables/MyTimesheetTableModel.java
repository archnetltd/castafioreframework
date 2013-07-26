package org.castafiore.shoppingmall.employee.ui.tables;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.employee.ui.ProjectTimesheetEntries;
import org.castafiore.shoppingmall.project.Project;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.wfs.iterator.FileIterator;

public class MyTimesheetTableModel implements TableModel{

	private List<ProjectTimesheetEntries> entries = new ArrayList<ProjectTimesheetEntries>();
	
	private Calendar startDate;
	
	private Calendar endDate;
	
	private int days = 0;
	
	private final static SimpleDateFormat ddmm = new SimpleDateFormat("dd-MM");
	
	private String userid;
	
	public MyTimesheetTableModel(Calendar startDate, Calendar endDate, String userid) {
		super();
		
		
		FileIterator<Project> projects = MallUtil.getCurrentMerchant().getProjects();
		this.startDate = startDate;
		this.endDate = endDate;
		this.userid = userid;
		while(projects.hasNext()){
			Project p = projects.next();
			entries.add(new ProjectTimesheetEntries(p.getName(), startDate, endDate,userid));
		}
		
		Calendar cstart = (Calendar)startDate.clone();
		
		while(true){
			if(cstart.getTimeInMillis() >= endDate.getTimeInMillis()){
				break;
			}
			
			
			cstart.add(Calendar.DATE, 1);
			days = days + 1;
			
		}

	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public int getColumnCount() {
		return days + 2;
	}

	@Override
	public String getColumnNameAt(int index) {
		if(index == 0){
			return "Id";
		}else if(index == 1){
			return "Item";
		}else{
			int amt = index -2;
			Calendar cstart = (Calendar)startDate.clone();
			cstart.add(Calendar.DATE, amt);
			return ddmm.format(cstart.getTime());
			
		}
	}

	@Override
	public int getRowCount() {
		return entries.size();
	}

	@Override
	public int getRowsPerPage() {
		return 20;
	}

	@Override
	public Object getValueAt(int col, int row, int page) {
		int rrow = (page*getRowsPerPage()) + row;
		
		return entries.get(rrow);
	}

	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	

}
