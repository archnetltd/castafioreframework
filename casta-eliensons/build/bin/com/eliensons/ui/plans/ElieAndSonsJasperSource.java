package com.eliensons.ui.plans;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import org.castafiore.shoppingmall.checkout.Order;
import org.castafiore.shoppingmall.reports.JasperSource;
import org.castafiore.ui.UIException;

import com.eliensons.ui.ElieNSonsUtil;

public class ElieAndSonsJasperSource implements JasperSource {
	
	private JRDataSource datasource_;
	
	private Map<String,?> params_;
	
	public ElieAndSonsJasperSource(Order order){
		//Map<String,String> data = ElieNSonsUtil.getParams(order);
		//List<Map<String,?>> col = new ArrayList<Map<String,?>>(1);
		//col.add(data);
		//datasource_ = new JRMapCollectionDataSource(col);
		params_ = ElieNSonsUtil.getParams(order);
	}

	@Override
	public JasperReport getReport() {
		
		try {
			return (JasperReport)JRLoader.loadObject(new FileInputStream(new File("C:\\java\\groovy\\iReport-4.7.1\\iReport-4.7.1\\ireport\\samples\\Subreports\\master.jasper")));
		} catch (Exception e) {
			throw new UIException(e);
		}
	}

	@Override
	public String getFormat() {
		return "xhtml";
	}

	@Override
	public Map<String, Object> getParameters() {
		return (Map)params_;
	}

	@Override
	public JRDataSource getDataSource() {
		return datasource_;
	}

	@Override
	public String getFileName() {
		return "Contract.html";
	}
	
	

}
