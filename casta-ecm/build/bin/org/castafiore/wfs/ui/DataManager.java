package org.castafiore.wfs.ui;

import java.io.Serializable;

/**
 * Manages Instances of {@link DataProvider} within an arbitrary context.<br>
 * @author kureem
 *
 */
public interface DataManager extends Serializable{
	
	/**
	 * Creates a provider for the specified supported class
	 * @param supportedClass
	 * @return
	 */
	public DataProvider<?> createProvider(Class<?> supportedClass);
	
	/**
	 * Returns a previously loaded data provider having the specified uniqueId
	 * @param uniqueId
	 * @return
	 */
	public DataProvider<?> getProvider(String uniqueId);
	
	public DataConsumer<?> createConsumer(Class<?> supportedClass);
	

}
