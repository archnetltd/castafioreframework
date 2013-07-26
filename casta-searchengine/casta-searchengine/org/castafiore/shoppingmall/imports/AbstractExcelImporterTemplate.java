package org.castafiore.shoppingmall.imports;

import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.castafiore.utils.StringUtil;
import org.springframework.core.io.Resource;


public abstract class AbstractExcelImporterTemplate extends AbstractImporterTemplate<Row>{

	private Resource excelFile;
	
	private int firstRow = 0;
	
	protected int _currentRowNum = -1;
	
	private String sheet;
	
	private Workbook _workbook = null;
	
	private Sheet _sheet = null;
	
	public String getSheet() {
		return sheet;
	}

	public void setSheet(String sheet) {
		this.sheet = sheet;
	}

	public int getFirstRow() {
		return firstRow;
	}

	public void setFirstRow(int firstRow) {
		this.firstRow = firstRow;
	}

	

	public Resource getExcelFile() {
		return excelFile;
	}

	public void setExcelFile(Resource excelFile) {
		this.excelFile = excelFile;
	}

	

	@Override
	public <T> T getNextSource() {
		init();
		if(hasNextSource()){
			_currentRowNum = _currentRowNum+1;
			return (T)_sheet.getRow(_currentRowNum-1);
			
		}else{
			throw new RuntimeException("end of file reached");
		}
	}

	@Override
	public boolean hasNextSource() {
		init();
		try{
		Row row =	_sheet.getRow(_currentRowNum);
		
		if(row == null){
			return false;
		}else{
			return true;
		}
		}catch(Exception e){
			return false;
		}
	}
	
	
	protected  void init(){
		if(_currentRowNum == -1){
			_currentRowNum = firstRow;
			try {
				_workbook =  new HSSFWorkbook(excelFile.getInputStream());
				if(StringUtil.isNotEmpty(sheet)){
					_sheet = _workbook.getSheet(sheet);
				}else{
					_sheet = _workbook.getSheetAt(0);
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	

}
