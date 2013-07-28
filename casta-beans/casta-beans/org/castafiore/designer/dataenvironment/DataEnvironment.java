package org.castafiore.designer.dataenvironment;

import java.io.Serializable;

/**
 * Main interface for loading datasource
 * @author Kureem Rossaye
 *
 */
public interface DataEnvironment extends Serializable{
	
	/**
	 * return the datasource names configured in context
	 * @return
	 */
	public String[] getDatasourceNames();
	
	/**
	 * Returns the fields for a particular datasource
	 * @param datasource
	 * @return
	 */
	public String[] getDatafields(String datasource);
	
	/**
	 * Returns the name datasource
	 * @param name
	 * @return
	 */
	public Datasource getDatasource(String name);

}
