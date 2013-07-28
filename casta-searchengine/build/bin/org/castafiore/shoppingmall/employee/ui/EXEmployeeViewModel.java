package org.castafiore.shoppingmall.employee.ui;

import org.castafiore.shoppingmall.employee.Employee;
import org.castafiore.ui.Container;
import org.castafiore.ui.tabbedpane.TabModel;
import org.castafiore.ui.tabbedpane.TabPanel;

public class EXEmployeeViewModel implements TabModel {

	private final static String[] LABELS = new String[]{"Basic Info", "Address", "Finance"};
	
	private Employee  employee;
	
	
	
	public EXEmployeeViewModel(Employee emp) {
		super();
		this.employee = emp;
	}

	@Override
	public int getSelectedTab() {
		
		return 0;
	}

	@Override
	public Container getTabContentAt(TabPanel pane, int index) {
		if(index == 0){
			return new EXBasicInfoEmployeeForm("basic",employee);
		}else if(index == 1){
			return new EXAddressInfoEmployeeForm("address",employee);
		}else{
			return new EXFinanceInfoEmployeeForm("finance", employee);
		}
	}

	@Override
	public String getTabLabelAt(TabPanel pane, int index, boolean selected) {
		return LABELS[index];
	}

	@Override
	public int size() {
		return LABELS.length;
	}

}
