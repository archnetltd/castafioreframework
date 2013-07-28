package org.castafiore.designer.dataenvironment;

import org.castafiore.designer.dataenvironment.Datasource;
import org.castafiore.ui.ex.dynaform.DynaForm;

/**
 * 
 * @author Kureem Rossaye
 *
 */
public interface DatasourceConfigForm extends DynaForm{
	
	/**
	 * Initializes the form with the specified datasource
	 * @param datasource
	 */
	public void setDatasource(Datasource datasource);
	
	/**
	 * Saves the underlying datasource and returns it
	 * @return
	 */
	public Datasource save();
	
	/**
	 * stops editing of underlying datasource and returns it
	 * @return
	 */
	public Datasource cancelEdit();
	
	/**
	 * checks if the underlying datasource is a new one, or an old one being edited
	 * @return
	 */
	public boolean isNew();

}
