package org.castafiore.facebook.datasource;

import org.castafiore.designer.dataenvironment.Datasource;
import org.castafiore.designer.dataenvironment.DatasourceConfigForm;
import org.castafiore.designer.dataenvironment.DatasourceFactory;
import org.castafiore.designer.dataenvironment.DatasourceViewer;

public class FacebookDatasourceFactory implements DatasourceFactory{

	@Override
	public void applyAttribute(Datasource d, String attributeName,
			String attributeValue) {
		d.setAttribute(attributeName, attributeValue);
		d.reset();
		
	}

	@Override
	public DatasourceConfigForm getAdvancedConfig() {
		return new FacebookDatasourceConfigForm("FacebookDatasourceConfigForm");
	}

	@Override
	public String getIcon() {
		return "http://www.truenorth.org/Portals/0/siteImages/Facebook_icon.png";
	}

	@Override
	public Datasource getInstance() {
		return new FacebookDataSource("cloud:facebook","Facebook");
	}

	@Override
	public String[] getRequiredAttributes() {
		return new String[]{"AccessToken", "Type", "Query"};
	}

	@Override
	public DatasourceViewer getViewer() {
		return new FacebookDatasourceViewer("Facebookviewer");
	}

	@Override
	public void refresh(Datasource d) {
		d.reset();
		
	}

	@Override
	public String getUniqueId() {
		return "cloud:facebook";
	}

}
