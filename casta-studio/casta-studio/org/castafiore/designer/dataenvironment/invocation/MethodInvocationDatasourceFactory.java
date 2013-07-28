package org.castafiore.designer.dataenvironment.invocation;

import org.castafiore.designer.dataenvironment.Datasource;
import org.castafiore.designer.dataenvironment.DatasourceConfigForm;
import org.castafiore.designer.dataenvironment.DatasourceFactory;
import org.castafiore.designer.dataenvironment.DatasourceViewer;
import org.castafiore.designer.dataenvironment.EasyConfigFileIteratorDatasourceViewer;

public class MethodInvocationDatasourceFactory implements DatasourceFactory{

	@Override
	public void applyAttribute(Datasource d, String attributeName,
			String attributeValue) {
		
		
	}

	@Override
	public DatasourceConfigForm getAdvancedConfig() {
		return new MethodInvocationDatasourceConfigForm("MethodInvocationDatasourceConfigForm");
	}

	@Override
	public String getIcon() {
		return "icons-2/fugue/icons/target.png";
	}

	@Override
	public Datasource getInstance() {
		return new MethodInvocationDatasource("Method Invocation", getUniqueId());
	}

	@Override
	public String[] getRequiredAttributes() {
		return new String[]{"Service", "Method", "ReturnType"};
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
		return "Datasource:MethodInvocation";
	}

}
