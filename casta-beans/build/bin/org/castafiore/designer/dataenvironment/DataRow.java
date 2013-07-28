package org.castafiore.designer.dataenvironment;

import java.io.Serializable;

/**
 * Acts as a wrapper around the real data row
 * @author acer
 *
 */
public interface DataRow extends Serializable{
	
	/**
	 * Returns the value of the specified field
	 * @param field
	 * @return
	 */
	public Object getValue(String field);
	
	/**
	 * Returns the dataset managing this datarow
	 * @return
	 */
	public DataSet getDataSet();

}
