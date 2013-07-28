package org.castafiore.designer.dataenvironment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.castafiore.ui.Dimension;
import org.castafiore.ui.ex.form.table.EXPagineableTable;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.ui.ex.panel.EXPanel;

public class EasyConfigFileIteratorDatasourceViewer extends EXPanel implements DatasourceViewer, TableModel{

	private DataSet d_;
	
	
	private DataRow tmp;
	
	public EasyConfigFileIteratorDatasourceViewer(String name) {
		super(name, "Datasource viewer");
		setWidth(Dimension.parse("1000px"));
	}

	@Override
	public DatasourceViewer setDatasource(Datasource datasource) {
		datasource.open();
		this.d_ = datasource.getDataSet();

		EXPagineableTable ptable = new EXPagineableTable("", new EXTable("sad",this));
		setBody(ptable);
			 
		return this;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public int getColumnCount() {
		return d_.getFields().length;
	}

	@Override
	public String getColumnNameAt(int index) {
		return d_.getFields()[index];
	}

	@Override
	public int getRowCount() {
		return d_.size();
	}

	@Override
	public int getRowsPerPage() {
		
		return 10;
	}

	@Override
	public Object getValueAt(int col, int row, int page) {
		try{
		String property = getColumnNameAt(col);
		if(col == 0){
			tmp = d_.next();
		}
		
		
		Object o = tmp.getValue(property);
		if(o instanceof Calendar){
			String s = new SimpleDateFormat("dd-MM-yyyy").format(((Calendar)o).getTime());
			if(s.length() > 10){
				s = s.substring(0,10) + "...";
			}
			return  s;
		}else if(o instanceof Date){
			String s =  new SimpleDateFormat("dd-MM-yyyy").format(((Date)o));
			if(s.length() > 10){
				s = s.substring(0,10) + "...";
			}
			return  s;
		}
		
		
		if(o == null){
			return "";
		}else{
		
			String s = o.toString();
			if(s.length() > 10){
				s = s.substring(0,10) + "...";
			}
			return  s;
		}
		
		}catch(Exception e){
			return "";
		}

	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return false;
	}

}
