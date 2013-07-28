package org.castafiore.designer.dataenvironment.excel;

import java.util.Map;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.castafiore.Types;
import org.castafiore.designer.dataenvironment.DataRow;
import org.castafiore.designer.dataenvironment.DataSet;
import org.castafiore.designer.dataenvironment.Datasource;

public class ExcelFileDataSet implements DataSet {
	private int currentIndex = -1;

	private HSSFSheet sheet;

	private Datasource datasource;
	
	private Map<String, Types> fields = new ListOrderedMap();

	public ExcelFileDataSet(HSSFSheet sheet, Datasource datasource) {
		super();
		this.sheet = sheet;
		this.datasource = datasource;
	}

	@Override
	public int currentIndex() {
		return currentIndex;
	}

	@Override
	public DataRow first() {
		currentIndex = 0;
		return get();
	}

	@Override
	public DataRow get() {
		return new ExcelDataRow((HSSFRow) sheet.getRow(currentIndex), this);
	}

	@Override
	public DataRow get(int index) {
		currentIndex = index;
		return get();
	}

	@Override
	public String[] getFields() {
		
		HSSFWorkbook book = sheet.getWorkbook();
		
		String firstRow = datasource.getAttribute("FirstRowField");
		if (Boolean.parseBoolean(firstRow)) {

			Row r = sheet.getRow(0);
			
			for (int i = 0; i < r.getLastCellNum(); i++) {
				try {
					Cell c = r.getCell(i);
					fields.put(c.getStringCellValue(), Types.UNDEFINED);
				} catch (Exception e) {
					Cell c = r.getCell(i);
					fields.put("Undefined", Types.UNDEFINED);
				}
			}

		} else {
			Row r = sheet.getRow(0);
			int ff = 1;
			if (r == null) {
				while (r == null) {
					r = sheet.getRow(ff);
					ff++;
				}
			}

			for (int i = 0; i < r.getLastCellNum(); i++) {
				fields.put("Field " + i, Types.UNDEFINED);
			}
		}
		
		
		return fields.keySet().toArray(new String[fields.keySet().size()]);
	}

	@Override
	public Types getType(String field) {
		return fields.get(field);
	}

	@Override
	public DataRow last() {
		 currentIndex = sheet.getLastRowNum()-1;
		 return get();
	}

	@Override
	public DataRow previous() {
		currentIndex--;
		return get();
	}

	@Override
	public int size() {
		return sheet.getLastRowNum() + 1;
	}

	@Override
	public boolean hasNext() {
		return currentIndex < (sheet.getLastRowNum()-1);
	}

	@Override
	public DataRow next() {
		currentIndex--;
		return get();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException(
				"Remove is not supported. Datasource is readonly");

	}

	@Override
	public Datasource getDatasource() {
		return datasource;
	}

}
