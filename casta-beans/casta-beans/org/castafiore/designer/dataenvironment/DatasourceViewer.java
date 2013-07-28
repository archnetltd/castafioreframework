package org.castafiore.designer.dataenvironment;

import org.castafiore.ui.Container;

public interface DatasourceViewer extends Container {
	
	/**
	 * Initialises the viewer with the specified datasource
	 * @param d
	 * @return
	 */
	public DatasourceViewer setDatasource(Datasource d);

}
