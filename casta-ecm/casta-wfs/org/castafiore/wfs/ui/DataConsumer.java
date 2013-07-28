package org.castafiore.wfs.ui;

import org.castafiore.beans.CategorizeAble;
import org.castafiore.ui.Container;

/**
 *	Defines contract to consume data from a DataProvider.
 * @author kureem
 *
 * @param <T>
 */
public interface DataConsumer<T> extends Container, CategorizeAble{
	
	/**
	 * Returns the supported data type
	 * @return
	 */
	public Class<T> getSupportedType();
	
	/**
	 * Connects with the specified DataProvider.<br>
	 * Developers should implement this method in such a way, so as it can also be used to refresh this consumer
	 * @see DataManager
	 * @param provider
	 */
	public void connectWith(DataProvider<T> provider);
	
	
	

}
