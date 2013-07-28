package org.castafiore.designer.dataenvironment;

import java.util.Iterator;

import org.castafiore.Types;

public interface DataSet extends Iterator<DataRow>{
	
	
	/**
	 * Returns the datasource that created this dataset
	 * @return
	 */
	public Datasource getDatasource();
	
	/**
	 * moves the cursor one step before
	 * @return
	 */
	public DataRow previous();
	
	/**
	 * moves the cursor to the beginning
	 * @return
	 */
	public DataRow first();
	
	
	/**
	 * sends the cursor to the last record
	 * @return
	 */
	public DataRow last();
	
	
	/**
	 * Returns the current Object
	 * @return
	 */
	public DataRow get();
	
	
	/**
	 * Returns the data for the specified index
	 * @param index
	 * @return
	 */
	public DataRow get(int index);
	
	
	
	
	
	/**
	 * return the current Index
	 * @return
	 */
	public int currentIndex();
	
	
	/**
	 * returns the size of the list
	 * @return
	 */
	public int size();
	
	
	/**
	 * 
	 * @returns an array of string representing the fields in the datasource
	 */
	public String[] getFields();
	
	/**
	 * Returns the type for the specified field
	 * @param field
	 * @return
	 */
	public Types getType(String field);

}
