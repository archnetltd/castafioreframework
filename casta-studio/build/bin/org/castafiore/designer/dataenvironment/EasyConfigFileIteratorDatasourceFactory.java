package org.castafiore.designer.dataenvironment;




public class EasyConfigFileIteratorDatasourceFactory implements DatasourceFactory{

	@Override
	public void applyAttribute(Datasource d, String attributeName,
			String attributeValue) {
		
	} 

	@Override
	public DatasourceConfigForm getAdvancedConfig() {
		return new EasyConfigFileIteratorDatasourceConfigForm("EasyConfigFileIteratorDatasourceConfigForm");
	}

	@Override
	public String getIcon() {
		return "icons-2/fugue/icons/databases-relation.png";
	}

	@Override
	public Datasource getInstance() {
		return new EasyConfigFileIteratorDatasource("Datasource", getUniqueId());
	}

	@Override
	public String[] getRequiredAttributes() {
		return new String[]{"Directories","Type",  "SQL", "PageSize"};
	}

	@Override
	public DatasourceViewer getViewer() {
		return new EasyConfigFileIteratorDatasourceViewer("EasyConfigFileIteratorDatasourceViewer");
	}

	@Override
	public void refresh(Datasource d) {
		d.close();
		
	}

	@Override
	public String getUniqueId() {
		return "Datasource:simple";
	}

}
