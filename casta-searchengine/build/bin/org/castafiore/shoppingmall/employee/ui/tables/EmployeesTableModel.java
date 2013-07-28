package org.castafiore.shoppingmall.employee.ui.tables;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.shoppingmall.employee.Employee;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.ui.ex.form.table.TableModel;

public class EmployeesTableModel implements TableModel{

	private final static String[] LABELS = new String[]{"Id", "Name", "Basic Salary"};
	
	private Merchant merchant_;
	
	
	private List<Employee> employees = new ArrayList<Employee>();
	
	
	public EmployeesTableModel(Merchant merchant) {
		super();
		merchant_ = merchant;
		
		employees = merchant.getEmployees().toList();
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public int getColumnCount() {
		return LABELS.length;
	}

	@Override
	public String getColumnNameAt(int index) {
		return LABELS[index];
	}

	@Override
	public int getRowCount() {
		return employees.size();
	}

	@Override
	public int getRowsPerPage() {
		return 20;
	}

	@Override
	public Object getValueAt(int col, int row, int page) {
		int r = (page*getRowsPerPage()) + row;
		Employee emp = employees.get(r);
		if(col == 0){
			return emp.getSubscriber();
		}else if(col == 1){
			return emp.getFirstName() + " " + emp.getLastName();
		}else{
			return emp.getBasicSalary();
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		
		return false;

	}
	
	

}
