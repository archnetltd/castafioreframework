package org.castafiore.designer.dataenvironment;

import java.io.Serializable;
import java.util.Iterator;

import org.castafiore.Types;
import org.castafiore.designable.portal.PortalContainer;

/**
 * 
 * Base interface for a datasource visual basic style
 * @author Rossaye Abdool Kureem
 *
 */
public interface Datasource extends Serializable{
	
	/**
	 * The name of the datasource<br>
	 * Should be unique per {@link PortalContainer}
	 * 
	 * @return
	 */
	public String getName();
	
	/**
	 * Sets the name of this datasource
	 * @return
	 */
	public Datasource setName(String name);
	
	/**
	 * returns the value of the specified attribute
	 * @param key
	 * @return
	 */
	public String getAttribute(String key);
	
	/**
	 * Sets the value for the specified attribute
	 * @param key
	 * @param value
	 * @return
	 */
	public Datasource setAttribute(String key, String value);
	
	/**
	 * Returns a unique factory id
	 * @return
	 */
	public String getFactoryId();
	
	
	
	
	
	/**
	 * returns the dataset
	 * @return
	 */
	public DataSet getDataSet();
	
	
	
	
	
	/**
	 * checks if the Datasource is cacheable
	 * @return
	 */
	public boolean cacheable();
	
	
	
	
	/**
	 * opens the datasource<br>
	 * If the datasource is already open, nothing happens<br>
	 * Will open the datasource only if closed
	 * @return
	 */
	public Datasource open();
	
	
	/**
	 * checks if this datasource is open
	 * @return
	 */
	public boolean isOpen();
	
	
	/**
	 * closes the datasource
	 * @return
	 */
	public Datasource close();
	
	
	/**
	 * Resets the datasource
	 * @return
	 */
	public Datasource reset();
	
	public String getLog();
	
	
	
	
	

}
