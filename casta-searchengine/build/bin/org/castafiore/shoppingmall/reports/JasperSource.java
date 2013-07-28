package org.castafiore.shoppingmall.reports;

import java.io.Serializable;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperReport;

public interface JasperSource extends Serializable{
	
	public JasperReport getReport();
	
	public String getFormat();
	
	public Map<String,Object> getParameters();
	
	public JRDataSource getDataSource();
	
	public String getFileName();
	

}
