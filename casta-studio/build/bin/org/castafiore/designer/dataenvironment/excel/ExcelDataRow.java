package org.castafiore.designer.dataenvironment.excel;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Cell;
import org.castafiore.designer.dataenvironment.DataRow;
import org.castafiore.designer.dataenvironment.DataSet;
import org.castafiore.ui.UIException;

public class ExcelDataRow implements DataRow{
	
	private HSSFRow row;
	
	private DataSet dataSet;
	
	

	public ExcelDataRow(HSSFRow row, DataSet dataSet) {
		super();
		this.row = row;
		this.dataSet = dataSet;
	}



	@Override
	public Object getValue(String field) {
		String fields[] = dataSet.getFields();
		int index = 0;
		for(String s : fields){
			if(s.equals(field)){
				Cell c = row.getCell(index);
				if(c.getCellType() == Cell.CELL_TYPE_BOOLEAN)
					return c.getBooleanCellValue();
				else if(c.getCellType() == Cell.CELL_TYPE_NUMERIC)
					return c.getNumericCellValue();
				else if(c.getCellType() == Cell.CELL_TYPE_STRING)
					return c.getStringCellValue();
				else if(c.getCellType() == Cell.CELL_TYPE_FORMULA)
					return "";
				else
					return null;
				
			}
			index++;
		}
		throw new UIException("cannot find field " + field + " in datasource " + getDataSet().getDatasource().getName() );
	}



	@Override
	public DataSet getDataSet() {
		return dataSet;
	}
	
	
	

}
