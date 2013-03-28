/*
 * 
 */
package org.castafiore.ui.ex.form.list;

import org.castafiore.ui.Container;

public interface List<T> extends Container{
	
	public void addItem(ListItem<T> item);
	
	public ListItem<T> getItem(int index);
	
	public int getSize();
	
	

}
