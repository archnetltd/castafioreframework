package org.castafiore.designer.dataenvironment.excel;

import java.io.BufferedInputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.castafiore.designer.dataenvironment.DataSet;
import org.castafiore.designer.dataenvironment.Datasource;
import org.castafiore.ui.UIException;
import org.castafiore.utils.StringUtil;


public class ExcelFileDatasource implements Datasource{

	private Workbook workbook = null;
	
	private Map<String, String> attributes = new HashMap<String, String>(3);
	
	private String factoryId;
	
	private String name;
	
	private String log;
	
	
	public ExcelFileDatasource( String name,String factoryId) {
		super();
		this.factoryId = factoryId;
		this.name = name;
	}

	@Override
	public boolean cacheable() {
		return true;
	}

	@Override
	public Datasource close() {
		workbook = null;
		
		return this;
	}

	@Override
	public String getAttribute(String key) {
		return attributes.get(key);
	}

	@Override
	public String getFactoryId() {
		return factoryId;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isOpen() {
		return workbook != null;
	}

	@Override
	public Datasource open() {
		try{
			String path = getAttribute("Path");
			if(StringUtil.isNotEmpty(path))
				workbook = new HSSFWorkbook(new BufferedInputStream(new URL(path).openStream()));
			else
				log = log + ("<br>The path for this datasource has not been set");
			return this;
		}catch(Exception e){
			return this;
		}
	}

	@Override
	public Datasource reset() {
		return close().open();
	}

	@Override
	public Datasource setAttribute(String key, String value) {
		attributes.put(key, value);
		return this;
	}

	@Override
	public Datasource setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public DataSet getDataSet() {
		HSSFSheet s = (HSSFSheet)workbook.getSheet(getAttribute("Sheet"));
		return new ExcelFileDataSet(s,this);
	}

	@Override
	public String getLog() {
		return log;
	}

}
