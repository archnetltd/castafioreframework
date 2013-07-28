package org.castafiore.designer.dataenvironment.excel;

import org.castafiore.designer.dataenvironment.Datasource;
import org.castafiore.designer.dataenvironment.DatasourceConfigForm;
import org.castafiore.designer.dataenvironment.DatasourceFactory;
import org.castafiore.designer.dataenvironment.DatasourceViewer;
import org.castafiore.designer.dataenvironment.EasyConfigFileIteratorDatasourceViewer;

public class ExcelDatasourceFactory implements DatasourceFactory{

	@Override
	public void applyAttribute(Datasource d, String attributeName,
			String attributeValue) {
		
		
	}

	@Override
	public DatasourceConfigForm getAdvancedConfig() {
		return new ExcelFileDatasourceConfigForm("ExcelFileDatasourceConfigForm");
	}

	@Override
	public String getIcon() {
		return "icons-2/ww/web_design_icon_set/icons/mime_xls.png";
	}

	@Override
	public Datasource getInstance() {
		return new ExcelFileDatasource("Excel sheet", getUniqueId());
	}

	@Override
	public String[] getRequiredAttributes() {
		return new String[]{"Path", "Sheet", "FirstRowField"};
	}

	@Override
	public DatasourceViewer getViewer() {
		return new EasyConfigFileIteratorDatasourceViewer("EasyConfigFileIteratorDatasourceViewer");
	}

	@Override
	public void refresh(Datasource d) {
		d.reset();
		
	}

	@Override
	public String getUniqueId() {
		return "Datasource:Excel";
	}

}
