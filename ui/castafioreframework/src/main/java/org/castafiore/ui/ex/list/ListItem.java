package org.castafiore.ui.ex.list;

import javax.activation.UnsupportedDataTypeException;

import org.castafiore.ui.Container;
/**
 * Defines contract for a list item.<br>
 * A {@link ListItem} is a ui {@link Container}<br>
 * 
 * @author kureem
 *
 * @param <T>
 */
public interface ListItem<T> extends Container {
	
	/**
	 * Returns the data this liste Item is holding
	 * @return
	 */
	public T getData();
	
	/**
	 * Sets the data for this {@link ListItem}
	 * @param data
	 * @throws UnsupportedDataTypeException if data is not of correct expected type
	 */
	public void setData(T data)throws UnsupportedDataTypeException;
	
	/**
	 * Selects of un select this item
	 * @param selected
	 */
	public void setSelected(boolean selected);
	
	
	/**
	 * Checks if this {@link ListItem} is selected in this list
	 * @return
	 */
	public boolean isSelected();

}
