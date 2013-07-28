package org.castafiore.community.ui.timetable;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.persistence.Dao;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.wfs.Util;
import org.hibernate.criterion.Restrictions;

public class TimeTableModel implements TableModel {

	private static String[] labels = new String[]{"Group", "Mon.", "Tue.", "Wed.", "Thu.", "Fri.", "Sat.", "Sun."};
	
	private List<TimeTable> times = new ArrayList<TimeTable>();
	
	
	public TimeTableModel() {
		super();
		 times = SpringUtil.getBeanOfType(Dao.class).getReadOnlySession().createCriteria(TimeTable.class).add(Restrictions.eq("organization", Util.getLoggedOrganization())).list();
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
		return times.size();
	}

	@Override
	public int getRowsPerPage() {
		return 25;
	}

	@Override
	public Object getValueAt(int col, int row, int page) {
		TimeTable t = times.get(row);
		if(col == 0){
			return t.getGrp();
		}else if(col ==1){
			return t.getMon();
		}else if(col == 2){
			return t.getTue();
		}else if(col == 3){
			return t.getWed();
		}else if(col == 4){
			return t.getThur();
		}else if(col == 5){
			return t.getFri();
		}else if(col == 6){
			return t.getSat();
		}else if(col == 7){
			return t.getSun();
		}else{
			return t;
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

}
